package com.steelypip.powerups.hydraxml;

import static org.junit.Assert.*;

import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

public class TestFlexiHydraXML {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCopy() {
		HydraXML x = new FlexiHydraXML( "alpha" );
		x.addValue( "alpha-attr", "alpha-attr-value-0" );
		x.addValue( "alpha-attr", "alpha-attr-value-1" );
		HydraXML y = new FlexiHydraXML( "beta" );
		y.addValue( "beta-attr", "beta-attr-value-0" );
		y.addValue( "beta-attr", "beta-attr-value-1" );
		x.addChild( "alpha-child", y );
		HydraXML z = new FlexiHydraXML( x );
		assertEquals( x.valuesToList( "alpha-attr" ), z.valuesToList( "alpha-attr" ) );
		assertSame( 2, z.valuesToList( "alpha-attr" ).size() );
		z.clearAllAttributes();
		assertSame( 2, x.valuesToList( "alpha-attr" ).size() );
		assertSame( 0, z.valuesToList( "alpha-attr" ).size() );
		assertTrue( x.getChild( "alpha-child" ).hasName( "beta" ) );
		assertTrue( z.getChild( "alpha-child" ).hasName( "beta" ) );
		x.getChild( "alpha-child" ).setName( "gamma" );
		assertTrue( x.getChild( "alpha-child" ).hasName( "gamma" ) );
		assertTrue( z.getChild( "alpha-child" ).hasName( "gamma" ) );
		z.getChild( "alpha-child" ).setValue( "beta-attr", "reset" );
		assertEquals( "reset", x.getChild( "alpha-child" ).getValue( "beta-attr" ) );
	}

}
