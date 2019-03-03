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
	public void toString_string() {
		assertEquals( "\"foo\"", Element.fromString( "\"foo\"" ).toString() );
		assertEquals( "\"\\\"\"", Element.fromString( "\"\\\"\"" ).toString() );
		assertEquals( "\"\\n\\r\"", Element.fromString( "\"\\n\\r\"" ).toString() );
		assertEquals( "\"fish & chips\"", Element.fromString( "\"fish & chips\"" ).toString() );
		assertEquals( "\"Copyright \\u00A9\"", Element.fromString( "\"Copyright \u00A9\"" ).toString() );
	}

	@Test
	public void toString_array() {
		assertEquals( "[9,9,9]", Element.fromString( "[ 9, 9, 9 ]" ).toString() );
	}

}
