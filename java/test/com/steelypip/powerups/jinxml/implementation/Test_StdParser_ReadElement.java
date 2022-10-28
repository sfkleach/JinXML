package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Event;
import com.steelypip.powerups.jinxml.Event.AttributeEvent;
import com.steelypip.powerups.jinxml.Event.StartTagEvent;
import com.steelypip.powerups.jinxml.stdparse.StdPushParser;

public class Test_StdParser_ReadElement {
	
	StdPushParser parser( String s ) {
		return new StdPushParser( new StringReader( s ), false );
	}
	
	Element element( String s ) {
		return parser( s ).readElement();
	}
	
	boolean isXMLStringSyntaxSupported() {
		return parser("").isXMLStringSyntaxSupported();
	}
	
	@Test
	public void readElement_Int() {
		StdPushParser p = this.parser( "99" );
		Element e = p.readElement();
		assertNotNull( e );
		assertTrue( e.isIntValue() );
		assertEquals( (Long)99L, (Long)e.getIntValue() );
	}

	@Test
	public void readArray_Empty() {
		StdPushParser p = this.parser( "[]" );
		Element e = p.readElement();
		assertEquals( "array", e.getName() );
		assertEquals( 0, e.countAttributes() );
		assertEquals( 0, e.countMembers() );
	}
	
	@Test
	public void readArray_SingleValue() {
		StdPushParser p = this.parser( "[0]" );
		Element e = p.readElement();
		assertEquals( "array", e.getName() );
		assertEquals( 0, e.countAttributes() );
		assertEquals( 1, e.countMembers() );
		Element zero = e.getFirstChild();
		assertEquals( "int", zero.getName() );
		assertEquals( "0", zero.getValue(  "value" ) );
		assertTrue( zero.isIntValue() );
	}
	
	@Test
	public void readArray_TwoValues() {
		StdPushParser p = this.parser( "[0, null;]" );
		Element e = p.readElement();
		assertEquals( "array", e.getName() );
		assertEquals( 0, e.countAttributes() );
		assertEquals( 2, e.countMembers() );
		Element zero = e.getFirstChild();
		assertEquals( "int", zero.getName() );
		assertEquals( "0", zero.getValue(  "value" ) );
		assertTrue( zero.isIntValue() );
		Element _null = e.getChild( 1 );
		assertEquals( "null", _null.getName() );
		assertTrue( _null.isNullValue() );
	}
	
	@Test
	public void readObject_Empty() {
		StdPushParser p = this.parser( "{}" );
		Element e = p.readElement();
		assertEquals( "object", e.getName() );
		assertEquals( 0, e.countAttributes() );
		assertEquals( 0, e.countMembers() );
	}
	
	@Test
	public void readObject_OneEntry() {
		StdPushParser p = this.parser( "{ foo: true }" );
		Element e = p.readElement();
		assertEquals( "object", e.getName() );
		assertEquals( 0, e.countAttributes() );
		assertEquals( 1, e.countMembers() );
		Element m1 = e.getChild( "foo" ); 
		assertEquals( "boolean", m1.getName() );
		assertEquals( "true", m1.getValue(  "value" ) );
		assertTrue( m1.isBooleanValue() );	
	}
	
	@Test
	public void readElement_EmptyTag() {
		StdPushParser p = this.parser( "<foo/>" );
		Element e = p.readElement();
		assertEquals( "foo", e.getName() );
		assertEquals( 0, e.countAttributes() );
		assertEquals( 0, e.countMembers() );
	}
	
	@Test
	public void readElement_OneEntry() {
		StdPushParser p = this.parser( "<foo> 3.0 </foo> }" );
		Element e = p.readElement();
		assertEquals( "foo", e.getName() );
		assertEquals( 0, e.countAttributes() );
		assertEquals( 1, e.countMembers() );
		Element m1 = e.getChild(); 
		assertEquals( "float", m1.getName() );
		assertEquals( "3.0", m1.getValue(  "value" ) );
		assertTrue( m1.isFloatValue() );	
	}
	
	@Test
	public void readElement_TwoMixedEntries() {
		StdPushParser p = this.parser( "<foo> 3.0, bar: {} </foo> }" );
		Element e = p.readElement();
		assertEquals( "foo", e.getName() );
		assertEquals( 0, e.countAttributes() );
		assertEquals( 2, e.countMembers() );
		Element m0 = e.getChild(); 
		assertEquals( "float", m0.getName() );
		assertEquals( "3.0", m0.getValue(  "value" ) );
		assertTrue( m0.isFloatValue() );
		
		Element m1 = e.getChild( "bar"); 
		assertEquals( "object", m1.getName() );
		assertEquals( 0, m1.countMembers() );
		assertTrue( m1.isObject() );
	}
	
	@Test
	public void readElement_XMLStyle_EmptyString() {
		String text = "''";
		Element x = element( text );
		assertTrue( x.isStringValue() );
		assertEquals( "", x.getStringValue() );
	}

	@Test
	public void readElement_XMLStyle_NonEmptyString() {
		String text = "'Mary had a little lamb'";
		Element x = element( text );
		assertTrue( x.isStringValue() );
		assertEquals( text.substring( 1, text.length() - 1 ), x.getStringValue() );
	}

	@Test
	public void readElement_XMLStyle_NonEmptyString_WithEscapes() {
		if (!isXMLStringSyntaxSupported()) return;
		String text = "'Fish &amp; Chips&\\n'";
		Element x = element( text );
		assertTrue( x.isStringValue() );
		assertEquals( "Fish & Chips\n", x.getStringValue() );
	}

	@Test
	public void readElement_XMLStyle_EscapeFlip() {
		if (!isXMLStringSyntaxSupported()) return;
		String text = "'&\\&apos;'";
		Element x = element( text );
		assertTrue( x.isStringValue() );
		assertEquals( "'", x.getStringValue() );
	}

	
	@Test
	public void readElement_JSONStyle_EmptyString() {
		String text = "\"\"";
		Element x = element( text );
		assertTrue( x.isStringValue() );
		assertEquals( "", x.getStringValue() );
	}

	@Test
	public void readElement_JSONStyle_NonEmptyString() {
		String text = "\"Mary had a little lamb\"";
		Element x = element( text );
		assertTrue( x.isStringValue() );
		assertEquals( text.substring( 1, text.length() - 1 ), x.getStringValue() );
	}

	@Test
	public void readElement_JSONStyle_NonEmptyString_WithEscapes() {
		String text = "\"Fish & Chips\\&#10;\"";
		Element x = element( text );
		assertTrue( x.isStringValue() );
		assertEquals( "Fish & Chips\n", x.getStringValue() );
	}

	@Test
	public void readElement_JSONStyle_EscapeFlip() {
		String text = "\"\\&\\r\"";
		Element x = element( text );
		assertTrue( x.isStringValue() );
		assertEquals( "\r", x.getStringValue() );
	}


}
