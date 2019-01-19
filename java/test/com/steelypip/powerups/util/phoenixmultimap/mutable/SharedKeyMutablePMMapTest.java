package com.steelypip.powerups.util.phoenixmultimap.mutable;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.SharedKeyMutablePMMap;

public class SharedKeyMutablePMMapTest {
	
	PhoenixMultiMap< String, String > pmmap;
	PhoenixMultiMap< String, String > pmmap1;
	
	@Before
	public void setUp() {
		this.pmmap = new SharedKeyMutablePMMap< String, String >( "sharedkey" );
	}

	@Test
	public void testClearAllEntries() {
		assertTrue( this.pmmap.isEmpty() );
		this.pmmap1 = this.pmmap.clearAllEntries();
		assertTrue( this.pmmap1.isEmpty() );
	}

	@Test
	public void testAdd() {
		this.pmmap1 = this.pmmap.add( "foo", "bar" );
		assertEquals( 1, this.pmmap1.sizeEntries() );
		assertTrue( this.pmmap1.hasEntry( "foo", 0, "bar" ) );
	}

	@Test
	public void testAddAll() {
		List< String > x = new ArrayList< String >();
		x.add( "bar" );
		this.pmmap1 = this.pmmap.addAll( "foo", x );
		assertEquals( 1, this.pmmap1.sizeEntries() );
		assertTrue( this.pmmap1.hasEntry( "foo", "bar" ) );
	}
	
	@Test
	public void testAddAllPMMAP_AddEmpty() {
		this.pmmap1 = this.pmmap.addAll( this.pmmap );
		assertTrue( this.pmmap1.isEmpty() );
	}

	@Test
	public void testAddAllPMMAP_AddOne() {
		PhoenixMultiMap< String, String > m = PhoenixMultiMap.newEmptyPhoenixMultiMap();
		m = m.add( "left", "right" );
		assertEquals( 1, m.sizeEntries() );
		this.pmmap1 = this.pmmap.addAll( m );
		assertEquals( 1, this.pmmap1.sizeEntries() );
	}

	@Test
	public void testRemoveEntry() {
		this.pmmap1 = this.pmmap.removeEntry( "foo", "bar" );
		assertTrue( this.pmmap1.isEmpty() );
	}

	@Test
	public void testRemoveEntryAt() {
		this.pmmap1 = this.pmmap.removeEntryAt( "alpha", 0 );
		assertTrue( this.pmmap1.isEmpty() );
	}

	@Test
	public void testRemoveEntries() {
		this.pmmap1 = this.pmmap.removeEntries( "alpha" );
		assertTrue( this.pmmap1.isEmpty() );
	}

	@Test
	public void testSetValues() {
		List< String > x = new ArrayList< String >();
		x.add( "bar" );
		x.add( "gort" );
		this.pmmap1 = this.pmmap.setValues( "foo", x );
		assertEquals( 2, this.pmmap1.sizeEntries() );
		assertTrue( this.pmmap1.hasEntry(  "foo", "bar" ) );
		assertTrue( this.pmmap1.hasEntry(  "foo", "gort" ) );
	}

	@Test
	public void testSetSingletonValue_NewEntry() {
		this.pmmap1 = this.pmmap.setSingletonValue( "foo", "value" );
		assertEquals( 1, this.pmmap1.sizeEntries() );
		assertTrue( this.pmmap1.hasEntry(  "foo", "value" ) );
	}

	@Test
	public void testUpdateValue() {
		this.pmmap1 = this.pmmap.setSingletonValue( "sharedkey", "value" );
		this.pmmap1 = this.pmmap.updateValue( "sharedkey", 0, "newvalue" );
		assertEquals( 1, this.pmmap1.sizeEntries() );
		assertTrue( this.pmmap1.hasEntry( "sharedkey", 0, "newvalue" ) );		
	}

	@Test(expected=IllegalArgumentException.class)
	public void testUpdateValueBadIndex() {
		this.pmmap1 = this.pmmap.updateValue( "foo", 1, "value" );	
	}

	@Test(expected=IllegalArgumentException.class)
	public void testUpdateValueBadKey() {
		this.pmmap1 = this.pmmap.updateValue( "bar", 1, "value" );	
	}

	@Test
	public void testFreezeByPhoenixing() {
		PhoenixMultiMap< String, String > x = this.pmmap.freezeByPhoenixing();
		assertEquals( x, this.pmmap );
		try {
			x.clearAllEntries();
			fail();
		} catch ( UnsupportedOperationException _ ) {
		}
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
		assertEquals( 1, this.pmmap.keySet().size() );
	}

	@Test
	public void testSizeEntries() {
		assertEquals( 0, this.pmmap.sizeEntries() );
	}

	@Test
	public void testSizeEntriesWithKey() {
		assertEquals( 0, this.pmmap.sizeEntriesWithKey( "foo" ) );
		this.pmmap1 = this.pmmap.add( "sharedkey", "99" );
		assertEquals( 1, this.pmmap1.sizeEntriesWithKey( "sharedkey" ) );
	}

	@Test
	public void testSizeKeys() {
		assertEquals( 0, this.pmmap.sizeKeys() );
		this.pmmap1 = this.pmmap.add( "sharedkey", "99" );
		assertEquals( 1, this.pmmap1.sizeKeys() );
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
