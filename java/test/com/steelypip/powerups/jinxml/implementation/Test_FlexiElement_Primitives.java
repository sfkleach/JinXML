package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.stdmodel.FlexiElement;

public class Test_FlexiElement_Primitives {
	
	Element intElement;
	Element floatElementNoDP;
	Element floatElementWithDP;
	Element stringElement;
	
	@Before
	public void Setup() {
		intElement = Element.newIntValue( "12345678901234567890" );
		
		floatElementNoDP = Element.newFloatValue( "-456" );
		
		floatElementWithDP = Element.newFloatValue( "-456.5" );

		stringElement = Element.newStringValue( "abc" );
	}
	
	@Test( expected=Exception.class )
	public void setIntValue_InvalidElement() {
		stringElement.setIntValue( "123" );
	}
	
	@Test( expected=Exception.class )
	public void setFloatValue_InvalidElement() {
		stringElement.setFloatValue( "123" );
	}
	
	@Test
	public void setStringValue_Valid() {
		stringElement.setStringValue( "x y z" );
		assertEquals( "x y z", stringElement.getStringValue() );
	}
	
	@Test//( expected=Exception.class )
	public void setIntValue_Valid() {
		intElement.setIntValue( "123" );
		assertEquals( (Long)123L, intElement.getIntValue() );
	}
	
	@Test( expected=Exception.class )
	public void setStringValue_InvalidElement() {
		intElement.setStringValue( "foo" );
	}
	
	@Test( expected=Exception.class )
	public void setBooleanValue_InvalidElement() {
		intElement.setBooleanValue( "foo" );
	}
	
	@Test( expected=NumberFormatException.class )
	public void setIntValue_BadFormat() {
		intElement.setIntValue( "abc" );
	}
	
	@Test
	public void getIntValue_AllowOverflow() {
		assertTrue( intElement.isIntValue() );
		long n = intElement.getIntValue( true, 0L );
		assertEquals( 0L, n );
		intElement.setValue( "value", "123" );
		assertTrue( 123L == intElement.getIntValue() );
	}

	@Test( expected=NumberFormatException.class)
	public void getIntValue_BadFormat() {
		final Element ie = Element.newElement( Element.INT_ELEMENT_NAME );
		ie.addLastValue( Element.VALUE_KEY_FOR_LITERAL_CONSTANTS, "abc" );
		assertTrue( ie.isIntValue() );
		ie.getIntValue( true, 0L );
	}

	@Test( expected=NumberFormatException.class )
	public void getIntValue_ForbidOverflow() {
		//  Assert
		assertTrue( intElement.isIntValue() );
		intElement.getIntValue( false, 0L ); // will overflow
	}

	@Test
	public void getBigIntValue_NoOverflow() {
		assertTrue( intElement.isIntValue() );
		BigInteger n = intElement.getBigIntValue( true, BigInteger.ONE );
		assertEquals( new BigInteger( "12345678901234567890" ), n );
		intElement.setValue( "value", "123x" );
		assertEquals(  BigInteger.ONE, intElement.getBigIntValue( BigInteger.ONE ) );
	}

	@Test
	public void isInttValue_Simple() {
		assertTrue( intElement.isIntValue() );
		assertFalse( floatElementNoDP.isIntValue() );
		assertFalse( floatElementWithDP.isIntValue() );
	}
	
	@Test
	public void isFloatValue_Simple() {
		assertFalse( intElement.isFloatValue() );
		assertTrue( floatElementNoDP.isFloatValue() );
		assertTrue( floatElementWithDP.isFloatValue() );
	}
	
	@Test
	public void getFloatValue_Simple() {
		Double d = floatElementNoDP.getFloatValue();
		assertEquals( new Double( -456.0 ), d );
	}

	@Test
	public void getFloatValue_WithDecimalPoint() {
		Double d = floatElementWithDP.getFloatValue();
		assertEquals( new Double( -456.5 ), d );
	}

}
