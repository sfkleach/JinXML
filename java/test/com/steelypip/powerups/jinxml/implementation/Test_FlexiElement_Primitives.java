package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.implementation.FlexiElement;

public class Test_FlexiElement_Primitives {
	
	Element intElement;
	Element floatElementNoDP;
	Element floatElementWithDP;
	
	@Before
	public void Setup() {
		intElement = new FlexiElement( "int" );
		intElement.addLastValue( "value", "12345678901234567890" );
		
		floatElementNoDP = new FlexiElement( "float" );
		floatElementNoDP.addLastValue( "value", "-456" );
		
		floatElementWithDP = new FlexiElement( "float" );
		floatElementWithDP.addLastValue( "value", "-456.5" );
	}
	
	@Test
	public void getIntValue_Overflow() {
		assertTrue( intElement.isIntValue() );
		long n = intElement.getIntValue( true, 0L );
		assertEquals( 0L, n );
		intElement.setValue( "value", "123" );
		assertTrue( 123L == intElement.getIntValue() );
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
