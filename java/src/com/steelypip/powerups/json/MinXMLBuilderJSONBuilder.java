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

import com.steelypip.powerups.minxml.FlexiMinXMLBuilder;
import com.steelypip.powerups.minxml.MinXML;
import com.steelypip.powerups.minxml.MinXMLBuilder;

/**
 * This class implements the JSONBuilder interface and constructs MinXML
 * objects. The grammar of the constructed MinXML has a modest degree of 
 * customisation as the string constants are encapsulated in the JSONKeywords
 * translation table. 
 */
public class MinXMLBuilderJSONBuilder extends JSONBuildCounter implements JSONBuilder< MinXML > {
	
	protected MinXMLBuilder builder;
	protected String field_name = null;
	protected final JSONKeywords keywords;
	
	/**
	 * A convenience constructor that specialises the translation table to a
	 * standard set of keywords and the MinXML builder to a FlexiMinXML builder.
	 */
	public MinXMLBuilderJSONBuilder() {
		this( new FlexiMinXMLBuilder(), JSONKeywords.KEYS );
	}
	
	/**
	 * A convenience constructor that specialises the translation table to a
	 * standard set of keywords.
	 * @param builder the MinXML builder used to construct the internal tree
	 */
	public MinXMLBuilderJSONBuilder( final MinXMLBuilder builder ) {
		this( builder, JSONKeywords.KEYS );
	}
	
	/**
	 * Creates a JSONBuilder from a MinXML builder and a translation
	 * table. 
	 * @param builder the MinXML builder used to construct the internal tree
	 * @param keys the set of strings to embed in the final MinXML
	 */
	public MinXMLBuilderJSONBuilder( final MinXMLBuilder builder, final JSONKeywords keys ) {
		this.builder = builder;
		this.keywords = keys;
	}
	
	private void emitField() {
		if ( this.field_name != null ) {
			this.builder.put( keywords.FIELD, this.field_name );
		}		
		this.field_name = null;
	}
	
	private void emitConstant( final String type, final String value ) {
		this.increment();
		this.builder.startTagOpen( keywords.CONSTANT );
		this.builder.put( keywords.CONSTANT_TYPE, type );
		this.builder.put( keywords.CONSTANT_VALUE, value );
		this.emitField();
		this.builder.startTagClose( keywords.CONSTANT );
		this.builder.endTag( keywords.CONSTANT );		
		this.decrement();
	}

	@Override
	public void addNull() {
		this.emitConstant( keywords.NULLEAN, keywords.NULLEAN_NULL );
	}

	@Override
	public void addBoolean( final boolean value ) {
		this.emitConstant( keywords.BOOLEAN, value ? keywords.BOOLEAN_TRUE : keywords.BOOLEAN_FALSE );
	}

	@Override
	public void addInteger( final long value ) {
		this.emitConstant( keywords.INTEGER, "" + value );
	}

	@Override
	public void addInteger( final BigInteger value ) {
		this.emitConstant( keywords.INTEGER, "" + value );
	}

	@Override
	public void addFloat( double value ) {
		this.emitConstant( keywords.FLOAT, "" + value );
	}

	@Override
	public void addString( String value ) {
		this.emitConstant( keywords.STRING, value );
	}

	@Override
	public void startArray() {
		this.increment();
		this.builder.startTagOpen( keywords.ARRAY );
		this.emitField();
		this.builder.startTagClose( keywords.ARRAY );
	}

	@Override
	public void endArray() {
		this.builder.endTag( keywords.ARRAY );
		this.decrement();
	}

	@Override
	public void field( final String field ) {
		this.field_name = field;
	}

	@Override
	public void startObject() {
		this.increment();
		this.builder.startTagOpen( keywords.OBJECT );
		this.emitField();
		this.builder.startTagClose( keywords.OBJECT );
		
	}

	@Override
	public void endObject() {
		this.builder.endTag( keywords.OBJECT );
		this.decrement();
	}

	@Override
	public MinXML build() {
		this.buildCheck();
		return this.builder.build();
	}

}
