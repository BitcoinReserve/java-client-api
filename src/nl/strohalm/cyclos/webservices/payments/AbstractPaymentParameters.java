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
	fromMemberPrincipalType, fromMember and toMemberPrincipalType removed

 */
package nl.strohalm.cyclos.webservices.payments;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Base parameters for payment-related operations
 * @author luis
 */
public abstract class AbstractPaymentParameters implements Serializable {

    private static final long serialVersionUID = 8695041183461321816L;

    private String            toMember;
    private BigDecimal        amount;
    private String            description;
    private String            currency;
    private String            traceNumber;

    private String            traceData;

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

	public String getToMember() {
	  return toMember;
	}
	
	/** 
	 * @param toMember Account number of the payment receiver 
	 */
	public void setToMember(final int toMember) {
	  this.toMember = "" + toMember;
	}
    
    /**
     * Optional.
     * @returns the data set by the client requesting/making a payment that will be attached to the ticket/transfer and sent back when the request
     * payment/notification is issued by Cyclos (likely to the same client).<br>
     * It depends on the client side then there is no guarantee of uniqueness between different clients.<br>
     * Note: <b>It has nothing to do with the traceNumber field in the parent class (used to tag transactions and query by these value).</b>
     */
    public String getTraceData() {
        return traceData;
    }

    public String getTraceNumber() {
        return traceNumber;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setTraceData(final String traceData) {
        this.traceData = traceData;
    }

    public void setTraceNumber(final String traceNumber) {
        this.traceNumber = traceNumber;
    }

    @Override
    public String toString() {
        return "AbstractPaymentParameters [traceData=" + traceData + ", amount=" + amount + ", currency=" + currency + ", description=" + description + ", toMember=" + toMember + ", traceNumber=" + traceNumber + "]";
    }

}
