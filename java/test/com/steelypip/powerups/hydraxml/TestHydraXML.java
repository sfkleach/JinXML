package com.steelypip.powerups.hydraxml;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

public class TestHydraXML {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testShallowCopy() {
		StringReader rep = new StringReader( "<foo alpha='beta'/>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML foo0 = p.readElement();
		HydraXML foo1 = foo0.shallowCopy();
		assertEquals( foo0.getName(), foo1.getName() );
		assertEquals( "beta", foo0.getValue( "alpha" ) );
	}

}
