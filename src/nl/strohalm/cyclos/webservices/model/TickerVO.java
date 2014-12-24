/*
    This file is part of Bitcoin Reserve (www.bitcoinreserve.ch).

    This file is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This file is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this file; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */

package nl.strohalm.cyclos.webservices.model;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

/** Ticker for web services */
public class TickerVO extends EntityVO {
	/** */ private static final long serialVersionUID = -7915966398007260096L;
	
	private float ticker;
	private Calendar timestamp;
	
	/**@return USD to BTC exchange rate */
	public float getTicker() {
		return ticker;
	}
	
	public void setTicker(float ticker) {
		this.ticker = ticker;
	}
	
	/**@return UTC date and time this Ticker was calculated in ISO-8601 format.*/
	public Calendar getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}
	
	/**Formats ticker as "$x,xxx.xx / BTC" 
	 * @return ticker value in NumberFormat.getCurrencyInstance(Locale.US) / BTC */
	public String toString() {
		return NumberFormat.getCurrencyInstance(Locale.US).format(ticker) + " / BTC";
	}
}
