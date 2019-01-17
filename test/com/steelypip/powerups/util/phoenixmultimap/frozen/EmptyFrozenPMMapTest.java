package com.steelypip.powerups.util.phoenixmultimap.frozen;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;

public class EmptyFrozenPMMapTest {
	
	PhoenixMultiMap< String, String > pmmap;
	
	@Before
	public void setUp() {
		this.pmmap = new EmptyFrozenPMMap< String, String >();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testClearAllEntries() {
		this.pmmap.clearAllEntries();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testAdd() {
		this.pmmap.add( "foo", "bar" );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testAddAll() {
		this.pmmap.addAll( null );
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testAddAllPMMAP() {
		this.pmmap.addAll( this.pmmap );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testRemoveEntry() {
		this.pmmap.removeEntry( "alpha", "beta" );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testRemoveEntryAt() {
		this.pmmap.removeEntryAt( "alpha", 0 );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testRemoveEntries() {
		this.pmmap.removeEntries( "alpha" );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testSetValues() {
		this.pmmap.setValues( "alpha", new ArrayList<>() );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testSetSingletonValue() {
		this.pmmap.setSingletonValue( "key", "value" );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testUpdateValue() {
		this.pmmap.updateValue( "key", 0, "value" );
	}

	@Test
	public void testFreezeByMutation() {
		PhoenixMultiMap< String, String > x = this.pmmap.freezeByPhoenixing();
		assertSame( x, this.pmmap );
	}

	@Test
	public void testHasEntryKV() {
		assertFalse( this.pmmap.hasEntry( "foo", "gort" ) );
	}

	@Test
	public void testHasEntryKIntV() {
		assertFalse( this.pmmap.hasEntry("foo", 0, "gort" ) );
	}

	@Test
	public void testHasKey() {
		assertFalse( this.pmmap.hasKey( "foo" ) );	
	}

	@Test
	public void testHasValue() {
		assertFalse( this.pmmap.hasValue( "foo" ) );	
	}

	@Test
	public void testEntriesToList() {
		assertTrue( this.pmmap.entriesToList().isEmpty() );	
	}

	@Test
	public void testGetAll() {
		assertTrue( this.pmmap.getAll( "foo" ).isEmpty() );
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetOrFailK() {
		this.pmmap.getOrFail( "foo" );
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetOrFailKInt() {
		this.pmmap.getOrFail( "foo", 0 );
	}

	@Test
	public void testIsEmpty() {
		assertTrue( this.pmmap.isEmpty() );
	}

	@Test
	public void testKeySet() {
		assertTrue( this.pmmap.keySet().isEmpty() );
	}

	@Test
	public void testSizeEntries() {
		assertEquals( 0, this.pmmap.sizeEntries() );
	}

	@Test
	public void testSizeEntriesWithKey() {
		assertEquals( 0, this.pmmap.sizeEntriesWithKey( "foo" ) );
	}

	@Test
	public void testSizeKeys() {
		assertEquals( 0, this.pmmap.sizeKeys() );
	}

	@Test
	public void testValuesList() {
		assertTrue( this.pmmap.valuesList().isEmpty() );
	}

	@Test
	public void testGetElseKV() {
		assertEquals( "gort", this.pmmap.getElse( "foo", "gort" ) );
	}

	@Test
	public void testGetElseKIntV() {
		assertEquals( "gort", this.pmmap.getElse( "foo", 0, "gort" ) );
	}

	@Test
	public void testEqualsObject() {
		assertTrue( this.pmmap.equals( this.pmmap ) );
	}

}
