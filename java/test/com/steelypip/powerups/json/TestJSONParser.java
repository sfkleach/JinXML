package com.steelypip.powerups.json;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.charrepeater.CharRepeater;
import com.steelypip.powerups.charrepeater.ReaderCharRepeater;
import com.steelypip.powerups.minxml.FlexiMinXMLBuilder;
import com.steelypip.powerups.minxml.MinXML;

public class TestJSONParser {

	@Before
	public void setUp() throws Exception {
	}

	MinXML parse( final String s ) {
		CharRepeater rep = new ReaderCharRepeater( new StringReader( s ) );
		JSONParser< MinXML > jp = new JSONParser< MinXML >( rep, new MinXMLBuilderJSONBuilder( new FlexiMinXMLBuilder() ) );
		return jp.read();
	}
	
	void equivalent( final String actual_input, final String expected_output ) {
		assertEquals( expected_output, parse( actual_input ).toString() );
	}
	
	
	@Test
	public void testInteger() {
		String s = "-789";
		assertEquals( "<constant type=\"integer\" value=\"" + s + "\"/>", parse( s ).toString() );
	}	

	@Test
	public void testFloat() {
		String s = "-0.15";
		assertEquals( "<constant type=\"float\" value=\"" + s + "\"/>", parse( s ).toString() );
	}	

	@Test
	public void testString() {
		equivalent( "\"abc\"", "<constant type=\"string\" value=\"abc\"/>" );
		equivalent( "\"\"", "<constant type=\"string\" value=\"\"/>" );
		equivalent( "\"\\n\"", "<constant type=\"string\" value=\"&#10;\"/>" );
		equivalent( "\"\\r\"", "<constant type=\"string\" value=\"&#13;\"/>" );
		equivalent( "\"\\t\"", "<constant type=\"string\" value=\"&#9;\"/>" );
		equivalent( "\"\\u0000\"", "<constant type=\"string\" value=\"&#0;\"/>" );
	}	

	@Test
	public void testKnownIdentifiers() {
		equivalent( "null", "<constant type=\"null\" value=\"null\"/>" );
		equivalent( "true", "<constant type=\"boolean\" value=\"true\"/>" );
		equivalent( "false", "<constant type=\"boolean\" value=\"false\"/>" );
	}
	
	@Test( expected=Alert.class )
	public void testOtherIdentifiers() {
		equivalent( "wibble", null );
	}
	
	@Test
	public void testArray() {
		equivalent( "[]", "<array/>" );
		equivalent( "[42]", "<array><constant type=\"integer\" value=\"42\"/></array>" );
		equivalent( "[42, false]", "<array><constant type=\"integer\" value=\"42\"/><constant type=\"boolean\" value=\"false\"/></array>" );
		equivalent( 
			"[42, [\"\"], false]", 
			"<array><constant type=\"integer\" value=\"42\"/><array><constant type=\"string\" value=\"\"/></array><constant type=\"boolean\" value=\"false\"/></array>" );
	}

	@Test
	public void testElement() {
		equivalent( "{}", "<object/>" );
		equivalent( "{\"xxx\":null}", "<object><constant field=\"xxx\" type=\"null\" value=\"null\"/></object>" );
		equivalent( 
			"{\"xxx\":null,\"\":99}", 
			"<object><constant field=\"xxx\" type=\"null\" value=\"null\"/><constant field=\"\" type=\"integer\" value=\"99\"/></object>" 
		);
	}
	
	@Test( expected=Alert.class )
	public void testBadArraySyntax1() {
		equivalent( "[,]", null );
	}
	
	@Test( expected=Alert.class )
	public void testBadArraySyntax2() {
		equivalent( "[99,]", null );
	}
	
	@Test( expected=Alert.class )
	public void testBadElementSyntax1() {
		equivalent( "{,}", null );
	}
	
	@Test( expected=Alert.class )
	public void testBadElementSyntax2() {
		equivalent( "{\"key\":99,}", null );
	}
	
	@Test
	public void testWhiteSpaceAfterAttribute() {
		equivalent(
			"{ \"foo\" : null }",
			"<object><constant field=\"foo\" type=\"null\" value=\"null\"/></object>"
		);	
	}
	
	/*@Test
	public void testPrettyPrint() {
		MinXML json_as_minxml = new MinXMLJSONParser( new StringReader( "[ 1, true ]" ) ).read();
		json_as_minxml.prettyPrint( new OutputStreamWriter( System.out ) );
	}*/
}
