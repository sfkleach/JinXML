package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.stdmodel.FlexiElement;

public class Test_FlexiElement_JSON_Values {
	
	Element intElement0;
	Element intElement1;
	Element floatElementFromDouble;
	Element floatElementNoDP;
	Element floatElementWithDP;
	Element stringElement;
	Element booleanElement0;
	Element booleanElement1;
	Element nullElement;
	Element objectElement;
	Element arrayElement;
	
	@Before
	public void Setup() {
		intElement0 = Element.newIntValue( "12345678901234567890" );
		intElement1 = Element.newIntValue( new BigInteger( "12345678901234567890" ) );
		booleanElement0 = Element.newBooleanValue( "true" );
		booleanElement1 = Element.newBooleanValue( true );
		floatElementFromDouble = Element.newFloatValue( 1.0 );
		floatElementNoDP = Element.newFloatValue( "-456" );
		floatElementWithDP = Element.newFloatValue( "-456.5" );
		stringElement = Element.newStringValue( "abc" );
		nullElement = Element.newNullValue();
		arrayElement = Element.newArray();
		objectElement = Element.newObject();
	}
	
	@Test
	public void newIntValue_Equals() {
		assertEquals( intElement0, intElement1 );
	}
	
	@Test
	public void newFloatValue_FromDouble() {
		assertEquals( (Double)1.0, floatElementFromDouble.getFloatValue() );
	}
	
	
	@Test
	public void newNullValue_OK() {
		assertTrue( nullElement.isNullValue() );
	}
	
	@Test
	public void newArrayValue_OK() {
		assertTrue( arrayElement.isArray() );
	}
	
	@Test
	public void newObjectValue_OK() {
		assertTrue( objectElement.isObject() );
	}
	
	@Test
	public void newBooleanValue_OK() {
		assertTrue( booleanElement0.getBooleanValue() );
		assertTrue( booleanElement1.getBooleanValue() );
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
		intElement0.setIntValue( "123" );
		assertEquals( (Long)123L, intElement0.getIntValue() );
	}
	
	@Test( expected=Exception.class )
	public void setStringValue_InvalidElement() {
		intElement0.setStringValue( "foo" );
	}
	
	@Test( expected=Exception.class )
	public void setBooleanValue_InvalidElement() {
		intElement0.setBooleanValue( "foo" );
	}
	
	@Test( expected=NumberFormatException.class )
	public void setIntValue_BadFormat() {
		intElement0.setIntValue( "abc" );
	}
	
	@Test
	public void getIntValue_AllowOverflow() {
		assertTrue( intElement0.isIntValue() );
		long n = intElement0.getIntValue( true, 0L );
		assertEquals( 0L, n );
		intElement0.setValue( "value", "123" );
		assertTrue( 123L == intElement0.getIntValue() );
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
		assertTrue( intElement0.isIntValue() );
		intElement0.getIntValue( false, 0L ); // will overflow
	}

	@Test
	public void getBigIntValue_NoOverflow() {
		assertTrue( intElement0.isIntValue() );
		BigInteger n = intElement0.getBigIntValue( true, BigInteger.ONE );
		assertEquals( new BigInteger( "12345678901234567890" ), n );
		intElement0.setValue( "value", "123x" );
		assertEquals(  BigInteger.ONE, intElement0.getBigIntValue( BigInteger.ONE ) );
	}

	@Test
	public void isInttValue_Simple() {
		assertTrue( intElement0.isIntValue() );
		assertFalse( floatElementNoDP.isIntValue() );
		assertFalse( floatElementWithDP.isIntValue() );
	}
	
	@Test
	public void isFloatValue_Simple() {
		assertFalse( intElement0.isFloatValue() );
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
