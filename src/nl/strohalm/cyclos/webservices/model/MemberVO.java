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
	images and customFields removed
 */
package nl.strohalm.cyclos.webservices.model;

import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Member data for web services
 * @author luis
 */
@XmlType(name = "member")
public class MemberVO extends EntityVO {
	private static final long serialVersionUID = 6233962543563278489L;

    private String            name;
    private int               accountNumber;
    private Long              groupId;

    @JsonIgnore
    public Long getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setUsername(final int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(" (username=" + accountNumber + ", name=" + name);
        buffer.append(")");
        return buffer.toString();
    }

}
