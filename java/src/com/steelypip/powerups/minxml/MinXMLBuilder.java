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
package com.steelypip.powerups.minxml;

/**
 * This interface linearises the construction of a MinXML
 * tree. Start tags are constructed with at least two calls:
 * startTagOpen and startTagClose. In between these two calls
 * there can be any number of puts, which set up the 
 * attributes.
 * 
 * Note that startTagOpen, startTagClose and endTag all
 * take the element name as a parameter. When using this 
 * interface with an unknown implementation the same (equals) 
 * element name should be supplied for all three calls that 
 * construct a particular element.
 * 
 * However an implementation may elect to allow the
 * element name to be null on one or more of these calls
 * (or even all!). All non-null values should be
 * the same and that must be the final value of the 
 * element name.
 *
 */
public interface MinXMLBuilder {
	
	/**
	 * This method should be called to begin the construction of
	 * a start-tag with a particular element name. An implementation
	 * may insist on the name being non-null or may permit it to
	 * be supplied later.
	 * 
	 * After this method, the next builder method should be put
	 * or startTagClose. 
	 * 
	 * @param name the name of the element to be constructed (or null). 
	 */
	void startTagOpen( String name );
	
	/**
	 * This method adds the attribute key=value to the start tag
	 * that is under construction. The builder method starTagOpen must
	 * have been the immediately previous method.
	 * @param key the attribute key 
	 * @param value the attribute value
	 */
	void put( String key, String value );
	
	/**
	 * This method finishes the construction of the current start tag.
	 * It may be followed by a call to endTag or startTagOpen. If the
	 * tag name is not-null it must agree with the previous value. If
	 * the previous value was null then it automatically is in agreement.
	 * 
	 * An implementation may choose to make startTagClose optional,
	 * implicitly closing it when the next startTagOpen is invoked.
	 * 
	 * @param name the name of the element to be constructed (or null)
	 */
	void startTagClose( String name );
	
	/**
	 * This method finishes the construction of the current element.
	 * If the tag-name is non-null then it must be in agreement with the
	 * previous value. If the previous value is null then it is automatically
	 * in agreement. 
	 * 
	 * @param name the name of the element to be constructed (or null) 
	 */
	void endTag( String name );
	
	/**
	 * This method returns the constructed tree. An implementation may
	 * elect to make the builder reusable again.
	 *  
	 * @return the constructed tree
	 */
	MinXML build(); 
}
