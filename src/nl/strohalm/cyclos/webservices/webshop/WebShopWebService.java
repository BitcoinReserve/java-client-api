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

This file was modified by BitcoinReserve.ch on Dec 12, 2014:  web method ticker and expire added

 */
package nl.strohalm.cyclos.webservices.webshop;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import nl.strohalm.cyclos.webservices.Permission;
import nl.strohalm.cyclos.webservices.ServiceOperation;
import nl.strohalm.cyclos.webservices.model.TickerVO;
import nl.strohalm.cyclos.webservices.model.WebShopTicketVO;

/**
 * Web service interface for webshop access
 * @author luis
 */
@WebService
public interface WebShopWebService {

	/**@return the current ticker */
    @Permission(ServiceOperation.WEBSHOP)
    @WebMethod
    TickerVO ticker();
    
    /**
     * Generates a ticket using the specified parameters
     */
    @Permission(ServiceOperation.WEBSHOP)
    @WebMethod
    String generateTicket(@WebParam(name = "params") GenerateWebShopTicketParams params);

    /**
     * @return a ticket object, using it's String value
     */
    @Permission(ServiceOperation.WEBSHOP)
    @WebMethod
    WebShopTicketVO getTicket(@WebParam(name = "ticket") String ticket);
    
    /**Expires a given ticket
     * @param ticket the ticket ID
     * @return true if the ticket was found and successfully expired */ 
    @Permission(ServiceOperation.WEBSHOP)
    @WebMethod
    boolean expireTicket(@WebParam(name = "ticket") String ticket);
    
}
