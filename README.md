Bitcoin Reserve Java SOAP Client
================================

This software enables your application to send SOAP messages to Bitcoin Reserve 
and receive responses. It requires credentials for an active BitcoinReserve.ch Business Account
 obtainable through the [website](https://bitcoinreserve.ch).  

Requirements
------------
Java 6 or higher.  
3rd party packages available in the classpath: commons-lang-2.5.jar, cxf-2.6.1.jar, 
jackson-all-1.9.6.jar, neethi-3.0.2.jar, wsdl4j-1.6.2.jar, xml-resolver-1.2.jar, and xmlschema-core-2.0.2.jar.
Copies available in the /lib folder.


Usage
-----

	// Use Bitcoin Reserve's Testnet server
    BitcoinReserveClient client = new BitcoinReserveClient();
    client.setServerRootUrl(BitcoinReserveClient.TESTNET_SERVER);
    
    // Supply your credentials
    client.setUser(yourAccountNumber);
    client.setApiKey(yourApiKey);
    
    // Test connectivity and credentials by querying the current exchange rate
    System.out.println(client.ticker());
    
This version of the client currently supports:  

- ticker  
- searchAccountHistory  
- loadTransfer  
- getMemberAccounts  
- loadByDisplayName  
- loadByAccountNumber    
- doPayment  
- simulatePayment  
- doBulkPayment  
- generateTicket  
- getTicket  
- expireTicket  
  
  

Please check the server's current WSDL specification for inconsistencies: \[[Testnet](https://testnet.bitcoinreserve.ch/services)] or \[[Production](https://bitcoinreserve.ch/services)]. 
    

Additional Information
----------------------

See a [live demonstration in PHP](https://testnet.bitcoinreserve.ch/demo)  
This project is based on [Cyclos3's](http://www.cyclos.org/cyclos3/features/) 
[Web Services](http://www.cyclos.org/wiki/index.php/Web_services)  


