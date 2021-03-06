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

 */
package nl.strohalm.cyclos.webservices.accounts;

import java.io.Serializable;

public class LoadTransferParameters implements Serializable {

    private static final long serialVersionUID = 3521471602244397136L;
    private String            transactionPwd;
    private Long              transferId;

    public String getTransactionPwd() {
        return transactionPwd;
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransactionPwd(final String credentials) {
        this.transactionPwd = credentials;
    }

    public void setTransferId(final Long transferId) {
        this.transferId = transferId;
    }

    @Override
    public String toString() {
        return "LoadTransferParameters [credentials=****" + ", transferId=" + transferId + "]";
    }

}
