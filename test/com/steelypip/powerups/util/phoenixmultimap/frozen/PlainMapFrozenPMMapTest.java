package com.steelypip.powerups.util.phoenixmultimap.frozen;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.PlainMapMutablePMMap;

public class PlainMapFrozenPMMapTest {
	
	PhoenixMultiMap< String, String > pmmap;
	PhoenixMultiMap< String, String > pmmap1;
	
	@Before
	public void setUp() {
		PlainMapMutablePMMap< String, String > m = new PlainMapMutablePMMap< String, String >();
		m = (PlainMapMutablePMMap< String, String >) m.add( "key0", "value0" );
		m = (PlainMapMutablePMMap< String, String >) m.add( "key1", "value0" );
		this.pmmap = new PlainMapFrozenPMMap<>( m );
	}

	@Test( expected=UnsupportedOperationException.class )
	public void testClearAllEntries() {
		assertFalse( this.pmmap.isEmpty() );
		this.pmmap1 = this.pmmap.clearAllEntries();
		assertTrue( this.pmmap1.isEmpty() );
	}

	@Test( expected=UnsupportedOperationException.class )
	public void testAdd() {
		this.pmmap1 = this.pmmap.add( "foo", "bar" );
		assertEquals( 2, this.pmmap1.sizeEntries() );
		assertTrue( this.pmmap1.hasEntry( "foo", 0, "bar" ) );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testAddAll() {
		List< String > x = new ArrayList< String >();
		x.add( "bar" );
		this.pmmap1 = this.pmmap.addAll( "foo", x );
		assertEquals( 2, this.pmmap1.sizeEntries() );
		assertTrue( this.pmmap1.hasEntry( "foo", "bar" ) );
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testAddAllPMMAP_AddEmpty() {
		this.pmmap1 = this.pmmap.addAll( PhoenixMultiMap.newEmptyPhoenixMultiMap() );
		assertFalse( this.pmmap1.isEmpty() );
		assertEquals( 1, this.pmmap1.sizeEntries() );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testAddAllPMMAP_AddOne() {
		PhoenixMultiMap< String, String > m = PhoenixMultiMap.newEmptyPhoenixMultiMap();
		m = m.add( "left", "right" );
		assertEquals( 1, m.sizeEntries() );
		this.pmmap1 = this.pmmap.addAll( m );
		assertEquals( 2, this.pmmap1.sizeEntries() );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testRemoveEntry() {
		this.pmmap1 = this.pmmap.removeEntry( "key0", "value0" );
		assertTrue( this.pmmap1.isEmpty() );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testRemoveEntryAt() {
		this.pmmap1 = this.pmmap.removeEntryAt( "alpha", 0 );
		assertEquals( 1, this.pmmap1.sizeEntries() );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testRemoveEntries_Match() {
		this.pmmap1 = this.pmmap.removeEntries( "key0" );
		assertTrue( this.pmmap1.isEmpty() );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testRemoveEntries_NoMatch() {
		this.pmmap1 = this.pmmap.removeEntries( "foo" );
		assertEquals( 1, this.pmmap1.sizeEntries() );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testSetValues() {
		//	Arrange
		List< String > x = new ArrayList< String >();
		x.add( "bar" );
		x.add( "gort" );
		//	Act
		this.pmmap1 = this.pmmap.setValues( "foo", x );
		//	Assert
		assertEquals( 3, this.pmmap1.sizeEntries() );
		assertTrue( this.pmmap1.hasEntry(  "foo", "bar" ) );
		assertTrue( this.pmmap1.hasEntry(  "foo", "gort" ) );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testSetSingletonValue_NewEntry() {
		this.pmmap1 = this.pmmap.setSingletonValue( "foo", "value" );
		assertEquals( 2, this.pmmap1.sizeEntries() );
		assertEquals( 1, this.pmmap1.sizeEntriesWithKey( "foo" ) );
		assertTrue( this.pmmap1.hasEntry(  "foo", "value" ) );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testUpdateValue() {
		this.pmmap1 = this.pmmap.updateValue( "foo", 0, "value" );
		assertEquals( 1, this.pmmap1.sizeEntries() );
		assertTrue( this.pmmap1.hasEntry( "foo", 0, "value" ) );		
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testUpdateValueBadIndex() {
		this.pmmap1 = this.pmmap.updateValue( "foo", 1, "value" );	
	}

	@Test(expected=UnsupportedOperationException.class)
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
		List< Map.Entry<  String, String > > list = this.pmmap.entriesToList();
		assertFalse( list.isEmpty() );
		assertEquals( 2, list.size() );
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
		assertFalse( this.pmmap.isEmpty() );
	}

	@Test
	public void testKeySet() {
		assertEquals( 2, this.pmmap.keySet().size() );
	}

	@Test
	public void testSizeEntries() {
		assertEquals( 2, this.pmmap.sizeEntries() );
	}

	@Test
	public void testSizeEntriesWithKey() {
		assertEquals( 0, this.pmmap.sizeEntriesWithKey( "foo" ) );
		assertEquals( 1, this.pmmap.sizeEntriesWithKey( "key0" ) );
	}

	@Test
	public void testSizeKeys() {
		assertEquals( 2, this.pmmap.sizeKeys() );
	}

	@Test
	public void testValuesList() {
		assertEquals( 2, this.pmmap.valuesList().size() );
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
