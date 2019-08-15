package com.steelypip.powerups.common;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class TestSingletonIterator {

	@Test
	public void testSingletonIterator() {
		Iterator< String > x = new SingletonIterator( "foo" );
		assertTrue( x.hasNext() );
		assertEquals( "foo", x.next() );
		assertFalse( x.hasNext() );
	}

}
