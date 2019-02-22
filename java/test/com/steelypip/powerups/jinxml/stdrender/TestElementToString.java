package com.steelypip.powerups.jinxml.stdrender;

import static org.junit.Assert.*;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.stdmodel.FlexiElement;

public class TestElementToString {

	@Test
	public void toString_EmptyElement() {
		Element e = new FlexiElement( "test" );
		String actual = e.toString();
		assertEquals( "<test/>", actual );
	}
	
	@Test
	public void toString_OneAttributeElement() {
		Element e = new FlexiElement( "test" );
		e.addLastValue( "name", "value" );
		String actual = e.toString();
		assertEquals( "<test name='value'/>", actual );
	}
	
	@Test
	public void toString_OneAttrNeedingEscapeElement() {
		Element e = new FlexiElement( "test" );
		e.addLastValue( "name", "fish & chips" );
		String actual = e.toString();
		assertEquals( "<test name='fish &amp; chips'/>", actual );
	}
	
	@Test
	public void toString_DupAttrElement() {
		Element e = new FlexiElement( "test" );
		e.addLastValue( "tag", "fish & chips" );
		e.addLastValue( "tag", "meal" );
		String actual = e.toString();
		assertEquals( "<test tag='fish &amp; chips' tag+='meal'/>", actual );
	}
	
	@Test
	public void toString_OneMemberElement() {
		Element e = new FlexiElement( "test 1" );
		e.addLastChild( "", new FlexiElement( "test 2" ) );
		String actual = e.toString();
		assertEquals( "<'test 1'><'test 2'/></'test 1'>", actual );
	}
	
	@Test
	public void toString_OneMemberElementWithSelector() {
		Element e = new FlexiElement( "test 1" );
		e.addLastChild( "foo", new FlexiElement( "test 2" ) );
		String actual = e.toString();
		assertEquals( "<'test 1'>foo:<'test 2'/></'test 1'>", actual );
	}
	
	@Test
	public void toString_OneMemberElementWithSelectorNeedingEscape() {
		Element e = new FlexiElement( "test 1" );
		e.addLastChild( "foo bar", new FlexiElement( "test 2" ) );
		String actual = e.toString();
		assertEquals( "<'test 1'>\"foo bar\":<'test 2'/></'test 1'>", actual );
	}
	
	@Test
	public void toString_TwoSelector() {
		Element e = new FlexiElement( "test1" );
		e.addLastChild( "foo", new FlexiElement( "test2" ) );
		e.addLastChild( "bar", new FlexiElement( "test3" ) );
		String actual = e.toString();
		assertEquals( "<test1>bar:<test3/>foo:<test2/></test1>", actual );
	}
	
	@Test
	public void toString_TwoDupAttrSelector() {
		Element e = new FlexiElement( "test1" );
		e.addLastChild( "foo", new FlexiElement( "test2" ) );
		e.addLastChild( "foo", new FlexiElement( "test3" ) );
		String actual = e.toString();
		assertEquals( "<test1>foo:<test2/>foo+:<test3/></test1>", actual );
	}
	
	@Test
	public void toString_NullValue() {
		Element e = new FlexiElement( "null" );
		e.addLastValue( "value", "null" );
		String actual = e.toString();
		assertEquals( "null", actual );
	}
	
	@Test
	public void toString_BooleanValueTrue() {
		Element e = new FlexiElement( "boolean" );
		e.addLastValue( "value", "true" );
		String actual = e.toString();
		assertEquals( "true", actual );
	}
	
	@Test
	public void toString_BooleanValueFalse() {
		Element e = new FlexiElement( "boolean" );
		e.addLastValue( "value", "false" );
		String actual = e.toString();
		assertEquals( "false", actual );
	}
	
	@Test
	public void toString_StringValue() {
		Element e = new FlexiElement( "string" );
		e.addLastValue( "value", "Mary had little lamb" );
		String actual = e.toString();
		assertEquals( "\"Mary had little lamb\"", actual );
	}
	
	@Test
	public void toString_IntValue() {
		Element e = new FlexiElement( "int" );
		e.addLastValue( "value", "-99" );
		String actual = e.toString();
		assertEquals( "-99", actual );
	}
	
	@Test
	public void toString_FloatValue() {
		Element e = new FlexiElement( "float" );
		e.addLastValue( "value", "-99.0" );
		String actual = e.toString();
		assertEquals( "-99.0", actual );
	}
	
	
	@Test
	public void toString_PrettyPrint_Simple() {
		Element e0 = new FlexiElement( "foo" );
		Element e1 = new FlexiElement( "bar" );
		e0.addLastChild( e1 );
		String actual = e0.toString( "--pretty" );
		String expected = ( 
				"<foo>\n" +
				"    <bar/>\n" +
				"</foo>\n"
		);
		assertEquals( expected, actual );
	}

	@Test
	public void toString_EmptyArray() {
		Element e = Element.fromString( "[]" );
		assertTrue( e.hasName( Element.ARRAY_ELEMENT_NAME ) );
		assertTrue( e.hasNoAttributes() );
		assertTrue( e.hasNoMembers() );
		assertEquals( "[]", e.toString() );
	}
	
	@Test
	public void toString_NonEmptyArray() {
		Element e = Element.fromString( "[ 1, 2, 3 ]" );
		assertTrue( e.hasName( Element.ARRAY_ELEMENT_NAME ) );
		assertTrue( e.hasNoAttributes() );
		assertEquals( 3, e.countMembers() );
		assertEquals( "[1,2,3]", e.toString() );
	}
	
	@Test
	public void toString_EmptyObject() {
		Element e = Element.fromString( "{}" );
		assertTrue( e.hasName( Element.OBJECT_ELEMENT_NAME ) );
		assertTrue( e.hasNoAttributes() );
		assertTrue( e.hasNoMembers() );
		assertEquals( "{}", e.toString() );
	}
	
	@Test
	public void toString_NonEmptyObject() {
		Element e = Element.fromString( "{ foo: 1, bar: 2 }" );
		assertTrue( e.hasName( Element.OBJECT_ELEMENT_NAME ) );
		assertTrue( e.hasNoAttributes() );
		assertEquals( 2, e.countMembers() );
		assertEquals( "{bar:2,foo:1}", e.toString() );
	}
	
}
