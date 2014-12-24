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
	toSystem, fromSystem removed, credentials changed to transactionPwd
 */
package nl.strohalm.cyclos.webservices.payments;

import java.util.List;

import nl.strohalm.cyclos.utils.ObjectHelper;
import nl.strohalm.cyclos.webservices.model.FieldValueVO;

import org.apache.commons.lang.ArrayUtils;

/**
 * Parameters for making a new payment
 * @author luis
 */
public class PaymentParameters extends AbstractPaymentParameters {
    private static final long  serialVersionUID = 8364312939644892473L;
    private Long               transferTypeId;
    private String             transactionPwd;
    private List<FieldValueVO> customValues;
    /**
     * A list of field's internal names indicating the payer's FieldValueVOs to be returned for a successful payment.<br>
     * NOTE: This list will be taken into account ONLY for an incoming WS request through an unrestricted WS Client.
     * @see {@link nl.strohalm.cyclos.webservices.payments.PaymentResult} (<code>getTransfer().getFromMember().getFields()</code>)
     */
    private List<String>       fromMemberFieldsToReturn;

    /**
     * A list of field's internal names indicating the receiver's FieldValueVOs to be returned for a successful payment.<br>
     * NOTE: This list will be taken into account ONLY for an incoming WS request through an unrestricted WS Client.
     * @see {@link nl.strohalm.cyclos.webservices.payments.PaymentResult} (<code>getTransfer().getMember().getFields()</code>)
     */
    private List<String>       toMemberFieldsToReturn;

    /**
     * If true the member's account status will be set in the payment result.
     */
    private Boolean            returnStatus     = false;

    public String getTransactionPwd() {
        return transactionPwd;
    }

    public List<FieldValueVO> getCustomValues() {
        return customValues;
    }

    public List<String> getFromMemberFieldsToReturn() {
        return fromMemberFieldsToReturn;
    }

    public boolean getReturnStatus() {
        return ObjectHelper.valueOf(returnStatus);
    }

    public List<String> getToMemberFieldsToReturn() {
        return toMemberFieldsToReturn;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransactionPwd(final String credentials) {
        this.transactionPwd = credentials;
    }

    public void setCustomValues(final List<FieldValueVO> customValues) {
        this.customValues = customValues;
    }

    public void setFromMemberFieldsToReturn(final List<String> fromMemberFieldsToReturn) {
        this.fromMemberFieldsToReturn = fromMemberFieldsToReturn;
    }

    public void setReturnStatus(final boolean returnStatus) {
        this.returnStatus = returnStatus;
    }

    public void setToMemberFieldsToReturn(final List<String> toMemberFieldsToReturn) {
        this.toMemberFieldsToReturn = toMemberFieldsToReturn;
    }


    public void setTransferTypeId(final Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    @Override
    public String toString() {
        return "PaymentParameters [credentials=****" + ", customValues=" + customValues + ", fromMemberFields= " + ArrayUtils.toString(fromMemberFieldsToReturn) + ", toMemberFields= " + ArrayUtils.toString(toMemberFieldsToReturn) + ", returnStatus=" + returnStatus + ", transferTypeId=" + transferTypeId + ", " + super.toString() + "]";
    }
}
