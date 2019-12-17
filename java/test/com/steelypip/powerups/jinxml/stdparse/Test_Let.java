package com.steelypip.powerups.jinxml.stdparse;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import com.steelypip.powerups.common.Sequence;
import com.steelypip.powerups.jinxml.Attribute;
import com.steelypip.powerups.jinxml.Element;

public class Test_Let {

	@Test
	public void test() {
		Element e = Element.fromString( "let x = 1 in x endlet" );
		assertTrue( e.isIntValue() );	
	}

	@Test
	public void test2() {
		Element e = Element.fromString( "let x = 1 y = 2 in [ x, y ] endlet" );
		assertTrue( e.isArray() );	
		assertEquals( 2, e.countMembers() );	
		assertEquals( 1, (long)e.getChild( 0 ).getIntValue() );	
		assertEquals( 2, (long)e.getChild( 1 ).getIntValue() );	
	}

	@Test
	public void testIdentity() {
		Element e = Element.fromString( "let x = <foo/> in [ x, <foo/>, x ] endlet" );
		assertTrue( e.isArray() );	
		assertEquals( 3, e.countMembers() );	
		assertEquals( e.getChild( 0 ), e.getChild( 2 ) );	
		assertEquals( e.getChild( 0 ), e.getChild( 1 ) );	
		assertSame( e.getChild( 0 ), e.getChild( 2 ) );	
		assertNotSame( e.getChild( 0 ), e.getChild( 1 ) );	
	}

	@Test
	public void testCascade() {
		//	Incidentally tests the treatment of semi-colons, colons and repeated bindings to the same variable.
		Element e = Element.fromString( "let x = <bar/>; x: [ x, <foo/>, x ]; in x endlet" );
		assertTrue( e.isArray() );	
		assertEquals( 3, e.countMembers() );
		assertEquals( "bar", e.getChild(0).getName() );
		assertEquals( "foo", e.getChild(1).getName() );
		assertEquals( "bar", e.getChild(2).getName() );
		assertEquals( e.getChild( 0 ), e.getChild( 2 ) );	
		assertNotEquals( e.getChild( 0 ), e.getChild( 1 ) );	
		assertSame( e.getChild( 0 ), e.getChild( 2 ) );	
		assertNotSame( e.getChild( 0 ), e.getChild( 1 ) );			
	}
	
}
