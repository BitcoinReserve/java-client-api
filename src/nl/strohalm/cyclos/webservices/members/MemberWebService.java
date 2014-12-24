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
	Services not used by BitcoinReserve removed
	service methods loadByAccountNumber and loadByDisplayName added
 */
package nl.strohalm.cyclos.webservices.members;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import nl.strohalm.cyclos.webservices.Permission;
import nl.strohalm.cyclos.webservices.ServiceOperation;
import nl.strohalm.cyclos.webservices.model.MemberVO;


/**
 * Web service interface for members
 * @author luis
 */
@WebService
public interface MemberWebService {

    /**
     * Loads a member using it's account number 
     * @return <code>null</code> indicates a member with that account number does not exist
     */
    @Permission(ServiceOperation.MEMBERS)
    @WebMethod
    MemberVO loadByAccountNumber(@WebParam(name = "accountNumber") int  accountNumber);

    /**
     * Loads a member using it's Display Name
     * @return <code>null</code> indicates a member with that Display Name does not exit
     */
    @Permission(ServiceOperation.MEMBERS)
    @WebMethod
    MemberVO loadByDisplayName(@WebParam(name = "displayName") String displayName);
    
}
