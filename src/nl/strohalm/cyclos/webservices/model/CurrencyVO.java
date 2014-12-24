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
package nl.strohalm.cyclos.webservices.model;

import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Currency VO for web services
 * @author luis
 */
@XmlType(name = "currency")
public class CurrencyVO extends EntityVO {
    private static final long serialVersionUID = -2918108402602549124L;
    private String            symbol;
    private String            name;
    private String            pattern;

    public String getName() {
        return name;
    }

    @JsonIgnore
    public String getPattern() {
        return pattern;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "CurrencyVO [name=" + name + ", pattern=" + pattern + ", symbol=" + symbol + "]";
    }

}
