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

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

/**
 * MinXML is a cleaner, leaner, cut-down version of XML with only the
 * absolute essentials. An MinXML object is a single element that 
 * represents a tree of attributed labels. The MinXML interface 
 * defines a standard interface for all classes representing MinXML 
 * elements.
 * 
 * @author Stephen Leach
 */
public interface MinXML extends List< @NonNull MinXML > {
	/**
	 * Returns the name of a MinXML element. It is not 
	 * guaranteed that this is interned but it is guaranteed
	 * to be non-null.
	 * 
	 * @return the element name
	 */
	@NonNull String getName();
	
	/**
	 * Returns true if the name of the element is the same as
	 * given string. Equivalent to this.getName() == name.
	 * 
	 * @param name the name we are checking
	 * @return true if the element has that name
	 */
	boolean hasName( String name );
	
	/**
	 * Changes the name of the element to the given string. Note
	 * that an implementation of MinXML is not obliged to support
	 * this method and may throw an exception. The name may not be
	 * null, otherwise a {link RuntimeException} is thrown.
	 *  
	 * @param name the name we are setting
	 * @throws UnsupportedOperationException if the implementing class cannot support this method
	 */
	void setName( @NonNull String name ) throws UnsupportedOperationException;
		
	/**
	 * Gets the attribute value associated with a given key. If the
	 * element does not have an attribute of that name it returns null.
	 * 
	 * @param key the attribute key being looked up
	 * @return the associated value or null
	 */
	String getAttribute( String key );
	
	/**
	 * Gets the attribute value associated with a given key. If the
	 * element does not have an attribute of that name it returns 
	 * the suppiled value_otherwise.
	 * 
	 * @param key the attribute key being looked up
	 * @param value_otherwise the value to be returned if there is no matching attribute
	 * @return the associated value
	 */	
	String getAttribute( String key, String value_otherwise );
	
	/**
	 * Returns true if the element has any attributes, otherwise false.
	 * 
	 * @return true if the element has 0 or more attributes.
	 */
	boolean hasAttribute();
	
	/**
	 * Returns true if the element has an attribute with the given key.
	 * 
	 * @param key the attribute key
	 * @return true if the element has an attribute with that key
	 */
	boolean hasAttribute( String key );
	
	/**
	 * Returns true if the element has an attribute with the given key
	 * whose value is equal to the given value. 
	 * 
	 * @param key attribute being checked 
	 * @param value value being checked
	 * @return true if the element has a matching attribute
	 */
	boolean hasAttribute( String key, String value );
	
	/**
	 * Returns true if the element has no attributes, otherwise false.
	 * 
	 * @return has no attributes
	 */
	boolean hasntAttribute();
	
	/**
	 * Returns the number of attributes of the element. This is 
	 * guranteed to be non-negative. If there are more attributes than
	 * can be represented by a Java int, then a {@link RuntimeException} must 
	 * be raised.
	 *  
	 * @return number of attributes.
	 */
	int sizeAttributes();
	
	/**
	 * Returns a iterator for the set of keys of the attributes of an element, as if the
	 * attributes were implemented as a {@link Map}, which shares updates
	 * with the original element (when updates are allowed). If you want
	 * updates to be shared use {@link asMapKeys}.
	 *  
	 * @return iterator over keys
	 */
	Iterable< String > keys();
	
	/**
	 * Returns an iterator over the keys of an element that shares
	 * updates with the original element. 
	 * @return the iterator.
	 */
	Iterable< String > asMapKeys();
	

	
	/**
	 * Returns a iterator over the attributes of an element, as if the
	 * attributes were implemented as a {@link Map}, which shares updates
	 * with the original element (when updates are allowed). If you want
	 * updates to be shared use {@link asMapEntries}.
	 *  
	 * @return iterator over entries
	 */
	Iterable< Map.Entry< String, String > > entries();
	
	/**
	 * Returns a {@link Map} representing the attributes of an element that
	 * does not share updates with the original element. If you want updates
	 * to be shared use {@link asMap}.
	 * 
	 * @return map of attributes
	 */
	Map< String, String > getAttributes();
	
	/**
	 * Returns a {@link Map} that represents the attributes of an element
	 * that shares updates with the original element.
	 * @return the sharing map
	 */
	Map< String, String > asMap();
	
	/**
	 * Returns an iterator over the attributes of an element that shares
	 * updates with the original element. 
	 * @return the sharing iterator
	 */
	Iterable< Map.Entry< String, String > > asMapEntries();
	
	
	/**
	 * Puts an attribute key=value into the element. If no attribute of that 
	 * key already exists then one is created. Otherwise the value will be
	 * changed. A class implementing MinXML is not obliged to implement 
	 * putAttribute and may throw an {@link UnsupportedOperationException}.
	 *   
	 * @param key the attribute key
	 * @param value the attribute value
	 * @throws UnsupportedOperationException if the implementing class cannot support this method
	 */
	void putAttribute( String key, String value ) throws UnsupportedOperationException;
	
	/* 
	 * Copies over all the entries from a supplied Map< String, String > into the 
	 * attributes of this element. A class implementing MinXML is not obliged to implement 
	 * putAllAttributes and may throw an {@link UnsupportedOperationException}.
	 * 
	 * @param map the supplied set of string->string entries.
	 * @throws UnsupportedOperationException if the implementing class cannot support this method
	 */
	void putAllAttributes( Map< String, String > map ) throws UnsupportedOperationException;
	
	/**
	 * Removes all the attributes of the element. A class implementing MinXML is
	 * not obliged to implement clearAttributes and may throw an {@link UnsupportedOperationException}.
	 * 
	 * @throws UnsupportedOperationException if the implementing class cannot support this method
	 */
	void clearAttributes() throws UnsupportedOperationException;

	/**
	 * This method signals that there will be no further updates to an element, at least for a while, 
	 * and the implementation should consider this a good opportunity to compact the space used
	 * by this element and all child elements, including any shared elements. Implementations
	 * must be clear on whether any subsequent updates are allowed or forbidden. If subsequent updates
	 * are forbidden, the implementation must throw an {@link IllegalStateException}.
	 */
	void trimToSize();
	
	/**
	 * Returns true if the element has no children. It is the opposite of isEmpty().
	 * 
	 * @return has no children
	 */
	boolean isntEmpty();
	
	boolean equals( Object obj );
	
	/**
	 * Renders the element using the supplied {@link PrintWriter}. The rendering will
	 * not contain any newlines. This is the same as the string generated by toString(). 
	 * 
	 * @param pw the {@link PrintWriter} to use.
	 */
	void print( PrintWriter pw );
	
	/**
	 * Renders the element to the supplied {@link java.io.Writer}.
	 * 
	 * @param w the {@link Writer} to use.
	 */
	void print( Writer w );
	
	/**
	 * Renders the element using the supplied {@link PrintWriter} such that each start and 
	 * end tag are on their own line and the children indented. The output always finishes
	 * with a newline.
	 * 
	 * @param pw the {@link PrintWriter} to use.
	 */
	void prettyPrint( PrintWriter pw );
	
	/**
	 * Renders the element using the supplied {@link java.io.Writer} such that each start and 
	 * end tag are on their own line and the children indented. The output always finishes
	 * with a newline.
	 * 
	 * @param w the {@link Writer} to use.
	 */
	void prettyPrint( Writer w );
	

	
	/**
	 * shallowCopy makes a copy of the topmost node but shares the children. The
	 * implementation of the new node must be at least as general as the implementation
	 * of this node i.e. implement all the methods that do not raise 
	 * {@link java.lang.UnsupportedOperationException} 
	 * @return a shallow copy
	 */
	@NonNull MinXML shallowCopy();
	 
	/**
	 * deepCopy makes a copy of the topmost node and all the children. The
	 * implementation of the new nodes must be at least as general as the implementation
	 * of this node i.e. implement all the methods that do not raise 
	 * {@link java.lang.UnsupportedOperationException} 
	 * @return a deep copy
	 */
	@NonNull MinXML deepCopy();
}
