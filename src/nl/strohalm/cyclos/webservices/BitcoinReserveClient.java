/*
    This file is a modified version of Cyclos (www.cyclos.org).
    A project of the Social Trade Organisation (www.socialtrade.org).

    Cyclos is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Cyclos is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cyclos; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

This file was modified by BitcoinReserve.ch on Dec 12, 2014:  
	changed class name CyclosWebServicesClient
	hard coded URLs to secure.bitcoinreserve.ch and  testnet.bitcoinreserve.ch added 
	removed service proxies not used by BitcoinReserve
 */
package nl.strohalm.cyclos.webservices;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.ws.soap.SOAPFaultException;

import nl.strohalm.cyclos.webservices.accounts.AccountHistoryResultPage;
import nl.strohalm.cyclos.webservices.accounts.AccountHistorySearchParameters;
import nl.strohalm.cyclos.webservices.accounts.AccountWebService;
import nl.strohalm.cyclos.webservices.accounts.LoadTransferParameters;
import nl.strohalm.cyclos.webservices.members.MemberWebService;
import nl.strohalm.cyclos.webservices.model.AccountHistoryTransferVO;
import nl.strohalm.cyclos.webservices.model.MemberAccountVO;
import nl.strohalm.cyclos.webservices.model.MemberVO;
import nl.strohalm.cyclos.webservices.model.TickerVO;
import nl.strohalm.cyclos.webservices.model.WebShopTicketVO;
import nl.strohalm.cyclos.webservices.payments.PaymentParameters;
import nl.strohalm.cyclos.webservices.payments.PaymentResult;
import nl.strohalm.cyclos.webservices.payments.PaymentStatus;
import nl.strohalm.cyclos.webservices.payments.PaymentWebService;
import nl.strohalm.cyclos.webservices.webshop.GenerateWebShopTicketParams;
import nl.strohalm.cyclos.webservices.webshop.WebShopWebService;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.BusFactory;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.auth.DefaultBasicAuthSupplier;

/**
 * Generates proxies to the various web services and forwards User requests to these proxies
 * 
 * @author luis
 */
public class BitcoinReserveClient implements Serializable {

	/** Can be used for searching account type by currency */
	public static final String BTC = "BTC";
	/** Can be used for searching account type by currency */
	public static final String USD = "USD";
	
	/** Hardcoded URL of the Bitcoin Reserve Testnet server */
	public static final String TESTNET_SERVER = "https://testnet.bitcoinreserve.ch";
	/** Hardcoded URL of the main Bitcoin Reserve server */
	public static final String PRODUCTION_SERVER = "https://secure.bitcoinreserve.ch";
	/** relative remote url of currently available services list */
	private static final String SERVICES_SUFFIX = "/services";
	
    private static final long                  serialVersionUID = 8877667897548825737L;
    private static final Map<Class<?>, String> SERVICES;
    static {
        final Map<Class<?>, String> services = new HashMap<Class<?>, String>();
        services.put(MemberWebService.class, "members");
        services.put(WebShopWebService.class, "webshop");
        services.put(AccountWebService.class, "account");
        services.put(PaymentWebService.class, "payment");
        SERVICES = Collections.unmodifiableMap(services);
    }

    private transient Map<Class<?>, Object> cachedProxies     = new HashMap<Class<?>, Object>();

    private String                          serverRootUrl;
    private String                          username;
    private String                          apiKey;

    /**Indicates whether that the hostname given in the HTTPS URL will be checked against the service's 
     * Common Name (CN) given in its certificate during SOAP client requests NOT recommended for production time */
    private boolean                         disableCNCheck;

    /**Used to connect to servers with self-signed certificates (not issued by a CA) You can set this to true or set the following system properties (-D):
     * <ul><li>javax.net.ssl.trustStore (the path to the keystore containing the certificate)
     *     <li>javax.net.ssl.trustStorePassword (the keystore password to open the keystore)</ul>*/
    private boolean                         trustAllCerts;

    /**Set the read timeout value in milliseconds. 
     * A timeout of zero is interpreted as an infinite timeout Defaults to 60000 ms */
    private long                            readTimeout       = 30000L;

    /**Set the connection timeout value in milliseconds. 
     * A timeout of zero is interpreted as an infinite timeout Defaults to 60000 ms*/
    private long                            connectionTimeout = 30000L;

    /**
     * Empty constructor
     */
    public BitcoinReserveClient() {
    	setServerRootUrl(PRODUCTION_SERVER);
    }

    /**Constructs the factory with the given server root url
     * @param serverRootUrl  See {@link #PRODUCTION_SERVER} or {@link #TESTNET_SERVER} */
    public BitcoinReserveClient(final String serverRootUrl) {
        setServerRootUrl(serverRootUrl);
    }

    /**Constructs the factory with the server root url and credentials
     * @param serverRootUrl BitcoinReserve Server URL
     * @param user your BitcoinReserve account number
     * @param apiKey your API key as issued by the BitcoinReserve server. see {@link #setApiKey(String)
     */
    public BitcoinReserveClient(final String serverRootUrl, final int user, final String apiKey) {
        this(serverRootUrl);
        setUser(user);
        setApiKey(apiKey);
    }

    /**
     * Sets the server root url
     */
    public void setServerRootUrl(final String serverRootUrl) {
        this.serverRootUrl = StringUtils.trimToNull(serverRootUrl);
        // Remove the trailing slash, if any
        if (this.serverRootUrl != null && this.serverRootUrl.endsWith("/")) {
            this.serverRootUrl = this.serverRootUrl.substring(0, this.serverRootUrl.length() - 1);
        }
        invalidateCache();
    }

    /**
     * @param user your Bitcoin Reserve account number
     */
    public void setUser(final int user) {
        this.username = Integer.toString(user);
        invalidateCache();
    }

    /**
     * Sets the API Key.<br/> 
     * (retrieved from the BitcoinReserve.ch website in the WebClient API section) 
     */
    public void setApiKey(final String apiKey) {
        this.apiKey = StringUtils.trimToNull(apiKey);
        invalidateCache();
    }

    /**@return the current Exchange Rate
     * @throws SOAPFaultException if invalid credentials are supplied, remote server is down, etc */
    public TickerVO ticker() {
    	return proxyFor(WebShopWebService.class).ticker();
    }
    
    /**@param params specify how the search is to be conducted 
     * @return A page of results for the search parameters.*/
    public AccountHistoryResultPage searchAccountHistory(AccountHistorySearchParameters params) {
    	return proxyFor(AccountWebService.class).searchAccountHistory(params);
    }
    
    /**Load a particular transfer owned by the account
     * @param params
     * @return A Bitcoin Reserve transaction data object (or throws SOAPFaultException)
     * @throws SOAPFaultException if, for example, invalid credentials were supplied, transfer doesn't 
     * 		exist, or the transfer is not associated with the {@link #setUser(int) account}.
     */
    public AccountHistoryTransferVO loadTransfer(LoadTransferParameters params) {
    	return proxyFor(AccountWebService.class).loadTransfer(params);
    }

    /**
     * @return a list of accounts for the given {@link #setUser(int) user}, typically a BTC and a USD account
     * @throws SOAPFaultException if invalid credentials are supplied
     */
    public List<MemberAccountVO> getMemberAccounts() {
    	return proxyFor(AccountWebService.class).getMemberAccounts();
    }
    
    /**
     * @param displayName
     * @return a MemberVO for the given display name or <code>null</code> if not found
     * @throws SOAPFaultException on connectivity or credentials issues  
     */
    public MemberVO loadByDisplayName(String displayName) {
    	return proxyFor(MemberWebService.class).loadByDisplayName(displayName);
    }

    /**
     * @param accountNumber
     * @return a MemberVO for the given account number or <code>null</code> if not found
     * @throws SOAPFaultException on connectivity or credentials issues  
     */
    public MemberVO loadByAccountNumber(int accountNumber) {
    	return proxyFor(MemberWebService.class).loadByAccountNumber(accountNumber);
    }

    /**Make a payment to another member.<br/>
     * Note: [Allow outgoing payments] must be checked in your WebClient API profile
     * @param params
     * @return The result (check @link {@link PaymentResult#getStatus()} for success/failure)
     * @throws SOAPFaultException on connectivity or credentials issues or payments are disabled
     */
    public PaymentResult doPayment(PaymentParameters params) {
    	return proxyFor(PaymentWebService.class).doPayment(params);
    }

    /**Simulate a payment to another member.<br/>
     * Note: [Allow outgoing payments] must be checked in your WebClient API profile
     * @param params
     * @return Check the result for success/failure before continuing to an actual {@link #doPayment(PaymentParameters) payment}
     * @throws SOAPFaultException on connectivity or credentials issues or payments are disabled
     */
    public PaymentStatus simulatePayment(PaymentParameters params) {
    	return proxyFor(PaymentWebService.class).simulatePayment(params);
    }

    /**Make a series of payments to other member(s).<br/>
     * Note: [Allow outgoing payments] must be checked in your WebClient API profile
     * @param params
     * @return A result for each payment (check @link {@link PaymentResult#getStatus()} for success/failure)
     * @throws SOAPFaultException on connectivity or credentials issues or payments are disabled
     */
    public List<PaymentResult> doBulkPayment(List<PaymentParameters> params) {
    	return proxyFor(PaymentWebService.class).doBulkPayment(params);
    }

    /**
     * @param ticketId
     * @return a WebShopTicketVO data object for a generated ticket ID (or null if the ticket ID is not found)
     * @throws SOAPFaultException on connectivity or credentials issues 
     */
    public WebShopTicketVO getTicket(String ticketId) {
    	return proxyFor(WebShopWebService.class).getTicket(ticketId);
    }

    /**Force a given ticket to be invalid (non-payable)
     * @param ticketId
     * @return <code>false</code> if the ticketId could not be found or successfully expired 
     * @throws SOAPFaultException on connectivity or credentials issues
     */
    public boolean expireTicket(String ticketId) {
    	return proxyFor(WebShopWebService.class).expireTicket(ticketId);
    }
    
    /**Create a Web Shop ticket for the given params
     * @param params
     * @return the ticketID (use {@link #getTicket(String) to retrieve details for the ticket}
     * @throws SOAPFaultException on connectivity or credentials issues
     */
    public String generateTicket(GenerateWebShopTicketParams params) {
    	return proxyFor(WebShopWebService.class).generateTicket(params);
    }
    
    /**Creates a proxy for the given interface or returns the cached version
     * @param serviceInterface The type bound to the service interface (WebShopWebService.class, PaymentWebService.class,
     * 		AccountWebService.class or MemberWebService.class)
     * @return The proxy for the given interface*/
    @SuppressWarnings("unchecked")
    public synchronized <T> T proxyFor(final Class<T> serviceInterface) {
        // Check for a cached instance
        final Object cached = cachedProxies.get(serviceInterface);
        if (cached != null) {
            return (T) cached;
        }
        // Cache miss. Create the proxy
        final String url = resolveUrlFor(serviceInterface);
        if (url == null) {
            throw new IllegalStateException("Cannot resolve url for service " + serviceInterface.getName() + " for server root url " + serverRootUrl);
        }

        // Create a proxy factory
        final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(serviceInterface);
        factory.setAddress(url);

        // Create the proxy
        final Object proxy = factory.create();

        final Client client = ClientProxy.getClient(proxy);
        final HTTPConduit http = (HTTPConduit) client.getConduit();

        // If the username / password are set, use them
        if (username != null || apiKey != null) {
            final AuthorizationPolicy authorization = new AuthorizationPolicy();
            authorization.setUserName(username);
            authorization.setPassword(apiKey);

            http.setAuthorization(authorization);
            http.setAuthSupplier(new DefaultBasicAuthSupplier());
        }

        http.setTlsClientParameters(getTLSClientParameters());
        http.getClient().setConnectionTimeout(connectionTimeout);
        http.getClient().setReceiveTimeout(readTimeout);

        // The proxy is ready. Store it on the cache
        cachedProxies.put(serviceInterface, proxy);

        return (T) proxy;
    }

    /**Set the connection timeout value in milliseconds. 
     * A timeout of zero is interpreted as an infinite timeout. Defaults to 30000 ms */
    public void setConnectionTimeout(final long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**@return the currently configured Connection Timeout setting (in milliseconds)*/
    public long getConnectionTimeout() {
        return connectionTimeout;
    }
    
    /**Set the read timeout value in milliseconds. 
     * A timeout of zero is interpreted as an infinite timeout. Defaults to 30000 ms*/
    public void setReadTimeout(final long readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**@return The currently configured Read Timeout setting (in milliseconds) */
    public long getReadTimeout() {
        return readTimeout;
    }

    private TLSClientParameters getTLSClientParameters() {
        final TLSClientParameters tlsCP = new TLSClientParameters();
        if (trustAllCerts) {
            final TrustManager[] myTrustStoreKeyManagers = getTrustManagers();
            tlsCP.setTrustManagers(myTrustStoreKeyManagers);
        }
        tlsCP.setDisableCNCheck(disableCNCheck);
        return tlsCP;
    }

    private TrustManager[] getTrustManagers() {
        final TrustManager[] trustManagers = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
            }
            @Override
            public void checkServerTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
            }
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        } };

        return trustManagers;
    }

    /** Resolves the service url for the given interface */
    private String resolveUrlFor(final Class<?> serviceInterface) {
        final String service = SERVICES.get(serviceInterface);
        if (serverRootUrl == null || service == null) {
            throw new IllegalArgumentException("Unknown web service interface: " + serviceInterface.getName());
        }
        return serverRootUrl + "/services/" + service;
    }

    /** Invalidate the cache proxies */
    private void invalidateCache() {
        cachedProxies.clear();
    }
    
    /** Destroy client proxies and shutdown communications Bus */
    public void shutdown() {
        for (final Object proxy : cachedProxies.values()) {
            try {
                final Client client = ClientProxy.getClient(proxy);
                client.destroy();
            } catch (final Exception e) {
                // Ignore
            }
        }
        cachedProxies.clear();
        BusFactory.getDefaultBus().shutdown(true);
    }
    
	public static Class<?> serviceInterfaceForName(final String name) {
    	for (final Map.Entry<Class<?>, String> entry : SERVICES.entrySet()) {
    		if (entry.getValue().equals(name)) {
    			return entry.getKey();
    		}
    	}
    	return null;
    }

    /** For testing -- default is false. (this should NOT be set <code>true</code> against Bitcoin Reserve's certificate) */ 
    @SuppressWarnings("unused")
	private void setDisableCNCheck(final boolean disableCNCheck) {
    	this.disableCNCheck = disableCNCheck;
    }

    /** For testing -- default is false. (this should NOT be set <code>true</code> against Bitcoin Reserve's certificate) */
    @SuppressWarnings("unused")
	private void setTrustAllCerts(final boolean trustAllCerts) {
    	this.trustAllCerts = trustAllCerts;
    }

    /**Some web services may be disabled.  Visit the URL returned by this method to view
     * services and methods currently accepted and being processed by Bitcoin Reserve. 
     * @throws MalformedURLException  A possible indication that the provided 
     * {@link #setServerRootUrl(String) serverRootUrl} is invalid  
     */
    @SuppressWarnings("unused")
	private URL getEnabledServicesListUrl() throws MalformedURLException {
    	if (serverRootUrl == null) {
    		throw new MalformedURLException("Please setServerRootUrl()");
    	}
    	return new URL(serverRootUrl + SERVICES_SUFFIX);
    }
 
}
