package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;

public class Test_FlexiElement_toString {

	@Test
	public void toString_int() {
		assertEquals( "99", Element.fromString( "99").toString() );
	}

	@Test
	public void toString_float() {
		assertEquals( "99.1", Element.fromString( "99.1").toString() );
	}

	@Test
	public void toString_JSONstring() {
		assertEquals( "\"foo\"", Element.fromString( "\"foo\"" ).toString() );
		assertEquals( "\"\\\"\"", Element.fromString( "\"\\\"\"" ).toString() );
		assertEquals( "\"\\n\\r\\b\\t\\f\"", Element.fromString( "\"\\n\\r\\b\\t\\f\"" ).toString() );
		assertEquals( "\"fish & chips\"", Element.fromString( "\"fish & chips\"" ).toString() );
		assertEquals( "\"Copyright \\u00A9\"", Element.fromString( "\"Copyright \u00A9\"" ).toString() );
		assertEquals( "[1,2,3]", Element.fromString( "[1,2,3]" ).toString() );
		assertEquals( "{left:\"right\"}", Element.fromString( "{left:\"right\"}" ).toString() );
	}

	@Test
	public void toString_XMLstring() {
		assertEquals( "<a b='foo'/>", Element.fromString( "<a b='foo'/>" ).toString() );
		assertEquals( "<a b='&quot;'/>", Element.fromString( "<a b='\"'/>" ).toString() );
		assertEquals( "\"\\n\\r\\b\\t\\f\"", Element.fromString( "'\\n\\r\\b\\t\\f'" ).toString() );
		assertEquals( "\"fish & chips\"", Element.fromString( "'fish &amp; chips'" ).toString() );
		assertEquals( "\"Copyright \\u00A9\"", Element.fromString( "'Copyright \u00A9'" ).toString() );
	}

	@Test
	public void toString_array() {
		assertEquals( "[9,9,9]", Element.fromString( "[ 9, 9, 9 ]" ).toString() );
	}

}
