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

package com.steelypip.powerups.json;

import java.util.Map;

/**
 * This class acts as a dictionary of String constants that are used to
 * translates JSON terminology into MinX structures. The KEYS variable
 * provides our default mapping. However it is perfectly straightforward to 
 * create custom translations if it is more convenient using the constructor.
 * 
 */
public class JSONKeywords {

	/**
	 * A constant that is used as the attribute used to mark the type
	 * of container ("array" or "object") for MinXSON parsing. If only
	 * parsing JSON it can be ignored.
	 */
	public final String TYPE;
	
	/** 
	 * A constant that is used as an attribute to mark the field name
	 * of components of objects. e.g. { "foo": 99 } turns into
	 * {@code <object><constant FIELD="foo" type="integer" value="99"/></object>}
	 */
	public final String FIELD;
	
	/**
	 * A constant that turns into the element name used to denote 
	 * all scalar values - numbers, booleans and null. e.g. true turns
	 * into {@code <CONSTANT type="boolean" value="true"/>}.
	 */
	public final String CONSTANT;

	/**
	 * A constant that denotes the type attribute of  
	 * all scalar values - numbers, booleans and null. e.g. true turns
	 * into {@code <constant CONSTANT_TYPE="boolean" value="true"/>}.
	 */
	public final String CONSTANT_TYPE;

	/**
	 * A constant that denotes the value attribute of  
	 * all scalar values - numbers, booleans and null. e.g. true turns
	 * into {@code <constant type="boolean" CONSTANT_VALUE="true"/>}.
	 */
	public final String CONSTANT_VALUE;
	
	/**
	 * A constant that denotes the value given to the type attribute   
	 * for floating point number. e.g. 3.14159 turns
	 * into {@code <constant type=FLOAT value="3.14159"/>}.
	 */	
	public final String FLOAT;
	
	/**
	 * A constant that denotes the value given to the type attribute   
	 * for whole numbers. e.g. -17 turns
	 * into {@code <constant type=INTEGER value="-17"/>}.
	 */	
	public final String INTEGER;
	
	
	/**
	 * A constant that denotes the value given to the type attribute   
	 * for strings. e.g. "foo" turns
	 * into {@code <constant type=STRING value="foo"/>}.
	 */	
	public final String STRING;

	/**
	 * A constant that denotes the value given to the type attribute   
	 * for booleans. e.g. false turns
	 * into {@code <constant type=BOOLEAN value="false"/>}.
	 */	
	public final String BOOLEAN;
	
	/**
	 * A constant that denotes the value given to the type attribute   
	 * for null. e.g. null turns
	 * into {@code <constant type=NULLEAN value="null"/>}.
	 */	
	public final String NULLEAN;
	
	/**
	 * A constant that denotes the value given to the element name   
	 * for arrays. e.g. [] turns
	 * into {@code <ARRAY/>}
	 */	
	public final String ARRAY;

	/**
	 * A constant that denotes the value given to the element name   
	 * for objects. e.g. {} turns
	 * into {@code <OBJECT/>}
	 */	
	public final String OBJECT;
	
	/**
	 * A constant that denotes the value given to the value attribute   
	 * for null. e.g. null turns
	 * into {@code <constant type="null" value=NULLEAN_NULL/>}.
	 */	
	public final String NULLEAN_NULL;
	
	/**
	 * A constant that denotes the value given to the value attribute   
	 * for true. e.g. true turns
	 * into {@code <constant type="boolean" value=BOOLEAN_TRUE/>}.
	 */	
	public final String BOOLEAN_TRUE;

	/**
	 * A constant that denotes the value given to the value attribute   
	 * for false. e.g. false turns
	 * into {@code <constant type="boolean" value=BOOLEAN_FALSE/>}.
	 */	
	public final String BOOLEAN_FALSE;
	
	/**
	 * A constant that denotes the value given to the element name   
	 * for identifiers - only relevant for MinXSON. e.g. x turns
	 * into {@code <ID name="x"/>}.
	 */	
	public final String ID;

	/**
	 * A constant that denotes the value given to the name attribute   
	 * for identifiers - only relevant for MinXSON. e.g. x turns
	 * into {@code <id ID_NAME="x"/>}.
	 */	
	public final String ID_NAME;
	
	private static String pick( Map< String, String > keys, String k, String otherwise ) {
		if ( keys == null ) return otherwise;
		final String first_choice = keys.get( k );
		return first_choice != null ? first_choice : otherwise;
	}
	
	/**
	 * Constructs a JSONKeywords translation table by overriding the 
	 * default values with values from the keys map. The valid keys of
	 * the map have identical spelling to the public members.
	 * @param keys a map of symbolic names to the strings to be used.
	 */
	public JSONKeywords( final Map< String, String > keys ) {
		 TYPE = pick( keys, "TYPE", "type" );
		 FIELD = pick( keys, "FIELD", "field" );
		 CONSTANT = pick( keys, "CONSTANT", "constant" );
		 CONSTANT_TYPE = pick( keys, "CONSTANT_TYPE", "type" );
		 CONSTANT_VALUE = pick( keys, "CONSTANT_VALUE", "value" );
		 FLOAT = pick( keys, "FLOAT", "float" );
		 INTEGER = pick( keys, "INTEGER", "integer" );
		 STRING = pick( keys, "STRING", "string" );
		 BOOLEAN = pick( keys, "BOOLEAN", "boolean" );
		 NULLEAN = pick( keys, "NULLEAN", "null" );
		 ARRAY = pick( keys, "ARRAY", "array" );
		 OBJECT = pick( keys, "OBJECT", "object" );
		 NULLEAN_NULL = pick( keys, "NULLEAN_NULL", "null" );
		 BOOLEAN_TRUE = pick( keys, "BOOLEAN_TRUE", "true" );
		 BOOLEAN_FALSE = pick( keys, "BOOLEAN_FALSE", "false" );
		 ID = pick( keys, "ID", "id" );
		 ID_NAME = pick( keys, "ID_NAME", "name" );
	}
	
	public static final JSONKeywords KEYS = new JSONKeywords( null );
}
