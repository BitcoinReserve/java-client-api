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
 	Methods not used by Bitcoin Reserve removed
 */
package nl.strohalm.cyclos.webservices.accounts;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import nl.strohalm.cyclos.webservices.Permission;
import nl.strohalm.cyclos.webservices.ServiceOperation;
import nl.strohalm.cyclos.webservices.model.AccountHistoryTransferVO;
import nl.strohalm.cyclos.webservices.model.MemberAccountVO;

/**
 * Web service interface for accounts
 * @author luis
 */
@WebService
public interface AccountWebService {

    /**
     * Returns the active accounts a member have
     */
    @Permission({ ServiceOperation.ACCOUNT_DETAILS, ServiceOperation.DO_PAYMENT, ServiceOperation.RECEIVE_PAYMENT })
    @WebMethod
    List<MemberAccountVO> getMemberAccounts();

    /**
     * Returns a single AccountHistoryTransferVO by id, or null when not found
     */
    @Permission(ServiceOperation.ACCOUNT_DETAILS)
    @WebMethod
    AccountHistoryTransferVO loadTransfer(@WebParam(name = "params") LoadTransferParameters params);

    /**
     * Searches for transfers and return the status for a given member account
     */
    @Permission(ServiceOperation.ACCOUNT_DETAILS)
    @WebMethod
    AccountHistoryResultPage searchAccountHistory(@WebParam(name = "params") AccountHistorySearchParameters params);

}
