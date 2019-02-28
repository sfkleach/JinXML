package com.steelypip.powerups.jinxml;

import static org.junit.Assert.*;

import org.junit.Test;

public class Test_Element_Freeze {

	@Test
	public void isFrozen_NotFrozen() {
		assertFalse( Element.newElement( "" ).isFrozen() );
	}

	@Test
	public void isFrozen_Frozen() {
		Element e = Element.newElement( "" ).freeze();
		assertTrue( e.isFrozen() );
		try {
			e.addLastChild( Element.newElement( "" ) );
			fail();
		} catch ( Exception _e ) {
		}
	}
	
	@Test
	public void freezeSelf() {
		Element e = Element.newElement( "" );
		e.freezeSelf();
		assertTrue(  e.isFrozen() );
	}

	@Test
	public void deepFreeze() {
		Element e = Element.newElement( "" );
		e.addLastValue( "name", "deepFreezeTest" );
		e.addLastChild( Element.newElement( "child" ) );
		e = e.deepFreeze();
		assertTrue( e.isFrozen() );
		assertTrue( e.getFirstChild().isFrozen() );
		try {
			e.getFirstChild().addFirstValue( "foo", "bar" );
			fail();
		} catch ( Exception _e ) {
		}
	}

	@Test
	public void deepFreezeSelf() {
		Element e = Element.newElement( "" );
		e.addLastValue( "name", "deepFreezeTest" );
		e.addLastChild( Element.newElement( "child" ) );
		e.deepFreezeSelf();
		assertTrue( e.isFrozen() );
		assertTrue( e.getFirstChild().isFrozen() );
		try {
			e.getFirstChild().addFirstValue( "foo", "bar" );
			fail();
		} catch ( Exception _e ) {
		}
	}

	@Test
	public void deepCopy() {
		Element e = Element.newElement( "" );
		e.addLastValue( "name", "deepFreezeTest" );
		e.addLastChild( Element.newElement( "child" ) );
		e.deepFreezeSelf();
		e = e.deepMutableCopy();
		assertFalse( e.isFrozen() );
		assertFalse( e.getFirstChild().isFrozen() );
		e.getFirstChild().addFirstValue( "foo", "bar" ); // Does not raise exception.
	}

}
