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
package com.steelypip.powerups.hydraxml;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * This interface linearises the construction of a HydraXML
 * tree. 
 */
public interface HydraXMLBuilder {

	/**
	 * This method should be called to begin the construction of
	 * a start-tag with a particular element name. When the 
	 * element is linked to the parent element, the field is 
	 * used for the link.
	 * 
	 * Name and field may be null, 
	 * in which case they must be supplied as non-null later.
	 * If allow_repeats is specified, repeats are allowed. 
	 * 
	 * @param name the name of the element to be constructed (or null).
	 * @param field the field to be used for the link to the parent (or null) 
	 * @param allow_repeats 
	 */
	void startTag( String field, String name, Boolean allow_repeats ) throws NullPointerException;
	void startTag( String field, String name ) throws NullPointerException;

	
	
	/**
	 * Shorthand for this.startTagOpen( name, null )
	 * 
	 * @param name the name of the element to be constructed (or null). 
	 */
	void startTag( String name );
	
	/**
	 * This method constructs a startTag with unknown name, field and
	 * repeat field. These can be added at any point between the startTag
	 * and endTag calls using the bindXXX methods.
	 */
	void startTag();
	
	/**
	 * This method adds the attribute key=value to the start tag
	 * that is under construction. 
	 * 
	 * @param key the attribute key 
	 * @param value the attribute value
	 */
	void add( @NonNull String key, @NonNull String value );
	
	/**
	 * This method adds the attribute key=value to the start tag
	 * that is under construction. There must be no previous
	 * attributes with the same key.
	 * 
	 * @param key the attribute key
	 * @param value the attribute value
	 */
	void addNew( @NonNull String key, @NonNull String value );
	
	/**
	 * This method adds the attribute key=value to the start tag
	 * that is under construction. This must be the first
	 * use of that attribute key. The boolean allow_repeats determines
	 * whether or not there may already be an attribute with the
	 * same key.
	 */
	default void add( @NonNull String key, @NonNull String value, boolean allow_repeats ) {
		if ( allow_repeats ) {
			this.add( key, value );
		} else {
			this.addNew( key, value );
		}
	}

	/**
	 * This method adds a child to the element under construction.
	 * The field name defaults to the value of defaultKey() for the 
	 * element under construction - which will normally be "".
	 */
	void addChild( @Nullable HydraXML x ); 
	void addChild( String field, @Nullable HydraXML x ); 

	
	/**
	 * This method may be called at any time between startTag and 
	 * endTag in order to provide the name
	 * the currently constructing element. If the value of
	 * name is null, this method has no effect.
	 * 
	 * @param name the name of the element to be constructed (or null)
	 */
	void bindName( String name );
	
	/**
	 * This method may be called at any time between startTag and 
	 * endTag in order to provide the field
	 * of the currently constructing element. If the value
	 * of field is null, this method has no effect.
	 * 
	 * When the element is linked to the parent element, the field is 
	 * used for the link.
	 * 
	 * @param name the name of the element to be constructed (or null)
	 */
	void bindField( String field );
	
	/**
	 * This method may be called at any time between startTag and 
	 * endTag in order to provide the allow_repeats. If it is specified, 
	 * a repeated field is permitted. If the value is null the method
	 * has no effect.
	 * 
	 * @param name the name of the element to be constructed (or null)
	 */
	void bindAllowRepeats( Boolean allow_repeats );
	
	/**
	 * This method finishes the construction of the current element.
	 * If the tag-name is non-null then it must be in agreement with the
	 * previous value. If the previous value is null then it is automatically
	 * in agreement. 
	 * 
	 * @param name the name of the element to be constructed (or null)
	 * @param field the field name under which the constructed element will be linked.
	 * @param allow_repeats true if it is OK if there are multiple elements added under this name. 
	 */
	void endTag( String field, String name, Boolean allow_repeats );

	/**
	 * This method finishes the construction of the current element.
	 * If the tag-name is non-null then it must be in agreement with the
	 * previous value. If the previous value is null then it is automatically
	 * in agreement. 
	 * 
	 * @param field the field name under which the constructed element will be linked.
	 * @param name the name of the element to be constructed (or null) 
	 */
	void endTag( String field, String name );

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
	 * This method finishes the construction of the current element.
	 * 
	 * @param name the name of the element to be constructed (or null) 
	 */
	void endTag();
	
	/**
	 * This method returns the constructed tree. An implementation may
	 * elect to make the builder reusable again.
	 *  
	 * @return the constructed tree
	 */
	HydraXML build();

}
