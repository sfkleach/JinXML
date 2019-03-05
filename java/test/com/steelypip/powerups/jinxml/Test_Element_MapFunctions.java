package com.steelypip.powerups.jinxml;

import static org.junit.Assert.*;

import org.junit.Test;

public class Test_Element_MapFunctions {

	@Test
	public void mapChildren_FilterOutAll() {
		Element before = Element.fromString( TestExamplesFromWiki.example1 );
		Element before_copy = before.deepFreeze();
		assertEquals( before_copy, before );
		Element after = before.mapChildren( x -> null );
		assertEquals( before_copy, before );
		assertTrue( after.hasNoMembers() );
	}

	@Test
	public void mapMembers_FilterOutAll() {
		Element before = Element.fromString( TestExamplesFromWiki.example1 );
		Element before_copy = before.deepFreeze();
		assertEquals( before_copy, before );
		Element after = before.mapMembers( x -> null );
		assertEquals( before_copy, before );
		assertTrue( after.hasNoMembers() );
	}

	@Test
	public void mapValues_FilterOutAll() {
		Element before = Element.fromString( "<hasAttrs left='right' right='wrong'/>" );
		Element before_copy = before.deepFreeze();
		assertEquals( before_copy, before );
		Element after = before.mapValues( x -> null );
		assertEquals( before_copy, before );
		assertTrue( after.hasNoAttributes() );
		assertNotEquals( before_copy, after  );
	}

	@Test
	public void mapAttributes_FilterOutAll() {
		Element before = Element.fromString( "<hasAttrs left='right' right='wrong'/>" );
		Element before_copy = before.deepFreeze();
		assertEquals( before_copy, before );
		Element after = before.mapAttributes( x -> null );
		assertEquals( before_copy, before );
		assertTrue( after.hasNoAttributes() );
		assertNotEquals( before_copy, after  );
	}

}
