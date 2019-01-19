/**
 * Copyright Stephen Leach, 2014
 * This file is part of the MinXML for Java library.
 * 
 * MinXML for Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MinXML for Java.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */

package com.steelypip.powerups.minxson;

import java.security.InvalidParameterException;

class Level {
	
	private String tag;
	private char expected;	//	If a particular character is expected, '\0' if not.
	private Context context;
	
	public Level( final String tag, final char expected, final Context context ) {
		super();
		if ( tag == null ) throw new InvalidParameterException();
		this.tag = tag;
		this.expected = expected;
		this.context = context;
	}
	
	public boolean isInObject() { return this.context == Context.InObject || this.context == Context.InEmbeddedObject; }
	public boolean isInElement() { return this.context == Context.InElement; }
	public boolean isInParentheses() { return this.context == Context.InEmbeddedParentheses; }
	public boolean isInEmbeddedContainer() { return this.context == Context.InEmbeddedArray || this.context == Context.InEmbeddedObject; }
	public boolean hasTag( final String t ) { return this.tag.equals( t ); }
	public boolean hasntTag( final String t ) { return ! this.tag.equals( t ); }
	public String getTag() { return this.tag; }
	public char getExpected() { return this.expected; }
	public boolean hasExpected( char ch ) { return this.expected != '\0'; }
	public boolean hasntExpected( char ch ) { return this.expected == '\0'; }
	
}