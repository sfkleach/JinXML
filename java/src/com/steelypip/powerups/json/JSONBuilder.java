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

import java.math.BigInteger;

/**
 * JSONBuilder provides an builder pattern interface for JSON 
 * parsers to drive as they consume a JSON expression. Once the
 * entire expression is consumed the build method is invoked to
 * return a result. The type of the result depends entirely on
 * the implementation, which allows different parsers to construct
 * different results. 
 * @param <T> the type that is yielded by the parser 
 */
public interface JSONBuilder< T > {
	
	/**
	 * This method should be invoked when the parser consumes a
	 * JSON expression 'null'.
	 */
	void addNull();
	
	/**
	 * This method should be invoked when the parser consumes a
	 * JSON expression 'true' or 'false'.
	 * @param value true if the expression was 'true', else false.
	 */
	void addBoolean( boolean value );
	
	
	/**
	 * This method should be invoked when the parser consumes a
	 * JSON integer value. JSON does not impose a limit on the
	 * size of integers, so arguably this should not take a long
	 * but a BigInteger. However, the difference in performance 
	 * and compactness between longs and BigIntegers is so great
	 * and the use-cases where BigIntegers are needed are so rare,
	 * we separate the two.
	 * @param value the integer value as a signed 64-bit integer.
	 */
	void addInteger( long value );
	
	/**
	 * This method should preferably be invoked when the parser consumes
	 * a JSON integer value that is too large to fit in a long.
	 * @param num the value as a big-integer
	 */
	void addInteger( BigInteger num );
	
	/** 
	 * This method should be invoked when the parser consumes a
	 * JSON floating point value.  
	 * @param value the floating point value
	 */
	void addFloat( double value );
	
	/**
	 * This method should be invoked when the parser consumes a
	 * JSON string.  
	 * @param value the string
	 */
	void addString( String value );
	
	/**
	 * This method should be invoked when the parser consumes the
	 * start of an array i.e. '['.
	 */
	void startArray();

	/**
	 * This method should be invoked when the parser consumes the
	 * end of an array i.e. ']'.
	 */
	void endArray();
	
	/**
	 * This method should be invoked when the parser consumes the
	 * field name of an object ahead of a ':'.
	 * @param field name of the field
	 */
	void field( String field );
	
	/**
	 * This method should be invoked when the parser consumes the
	 * start of an object i.e. '{'.
	 */
	void startObject();

	/**
	 * This method should be invoked when the parser consumes the
	 * end of an object i.e. '}'.
	 */
	void endObject();
	
	/**
	 * This method will return a value representing the JSON tree
	 * consumed so far by the parser. If it is called prematurely,
	 * before an entire balanced tree is consumed, the implementation
	 * must throw an exception. It must also throw an exception if
	 * more than one tree is consumed. The exception should preferably
	 * inherit from {@link JSONBuildFailedException}.
	 * @return a value representing the tree
	 */
	T build() throws JSONBuildFailedException;
}
