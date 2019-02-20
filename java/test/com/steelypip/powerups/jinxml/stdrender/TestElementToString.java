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
	public void toString_OneMemberElement() {
		Element e = new FlexiElement( "test 1" );
		e.addLastChild( "", new FlexiElement( "test 2" ) );
		String actual = e.toString();
		assertEquals( "<'test 1'><'test 2'/></'test 1'>", actual );
	}
	
	
	

}
