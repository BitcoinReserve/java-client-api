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
 	Service not used by BitcoinReserve removed
 */
package nl.strohalm.cyclos.webservices.payments;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import nl.strohalm.cyclos.webservices.Permission;
import nl.strohalm.cyclos.webservices.ServiceOperation;

/**
 * Web service interfaces for payments
 * @author luis
 */
@WebService
public interface PaymentWebService {

    /**
     * Performs several payments under the same transaction payment, returning the status and the transfer data for each payment
     */
    @Permission({ ServiceOperation.DO_PAYMENT, ServiceOperation.RECEIVE_PAYMENT })
    @WebMethod
    List<PaymentResult> doBulkPayment(@WebParam(name = "params") List<PaymentParameters> params);

    /**
     * Performs a payment, returning the status and the transfer data
     */
    @Permission({ ServiceOperation.DO_PAYMENT, ServiceOperation.RECEIVE_PAYMENT })
    @WebMethod
    PaymentResult doPayment(@WebParam(name = "params") PaymentParameters params);

    /**
     * Check whether a payment would be executed, returning the status, without actually performing it
     */
    @Permission({ ServiceOperation.DO_PAYMENT, ServiceOperation.RECEIVE_PAYMENT })
    @WebMethod
    PaymentStatus simulatePayment(@WebParam(name = "params") PaymentParameters params);
}
