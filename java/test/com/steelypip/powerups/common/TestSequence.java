package com.steelypip.powerups.common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class TestSequence {

	@Test
	public void testCount_Empty() {
		Sequence<Object> seq = Sequence.fromIterable( new ArrayList<Object>() );
		assertEquals( (Integer)0, seq.count( 1 ) );
		assertTrue( seq.hasAtLeast( 0 ) );
		assertFalse( seq.hasAtLeast( 1 ) );
		assertTrue( seq.isEmpty() );
	}

	@Test
	public void testCount_OneNonNull() {
		List< String > s = new ArrayList<String>();
		s.add( "one" );
		Sequence<String> seq = Sequence.fromIterable( s );
		assertEquals( (Integer)1, seq.count( 1 ) );
		assertTrue( seq.hasAtLeast( 1 ) );
		assertFalse( seq.hasAtLeast( 2 ) );
		assertFalse( seq.isEmpty() );
	}

	@Test
	public void testCount_OneNull() {
		List< String > s = new ArrayList<String>();
		s.add( null );
		Sequence<String> seq = Sequence.fromIterable( s );
		assertEquals( (Integer)1, seq.count( 1 ) );
		assertTrue( seq.hasAtLeast( 1 ) );
		assertFalse( seq.hasAtLeast( 2 ) );
		assertFalse( seq.isEmpty() );
	}

	@Test
	public void testMap_Empty() {
		List< String > L = new ArrayList<String>();
		Sequence<String> seq = Sequence.fromIterable( L ).map( x -> "x" + x );
		assertEquals( (Integer)0, seq.count( 1 ) );
		assertTrue( seq.hasAtLeast( 0 ) );
		assertFalse( seq.hasAtLeast( 1 ) );
		assertTrue( seq.isEmpty() );
	}

	@Test
	public void testMap_3Strings() {
		List< String > L = new ArrayList<String>();
		L.add( "foo" );
		L.add( "bar" );
		L.add( null );
		Sequence<String> seq = Sequence.fromIterable( L ).map( x -> "x" + x );
		assertEquals( (Integer)3, seq.count( 100 ) );
		assertTrue( seq.hasAtLeast( 3 ) );
		assertFalse( seq.hasAtLeast( 4 ) );
		assertFalse( seq.isEmpty() );
		{
			Iterator< String > it = seq.iterator();
			assertEquals( "xfoo", it.next() );
			assertEquals( "xbar", it.next() );
			assertEquals( "xnull", it.next() );
			assertFalse( it.hasNext() );
		}
	}

	@Test
	public void testFilter_3Strings() {
		List< String > L = new ArrayList<String>();
		L.add( "foo" );
		L.add( "bar" );
		L.add( null );
		Sequence<String> seq = Sequence.fromIterable( L ).filter( x -> x != null );
		assertEquals( (Integer)2, seq.count( 100 ) );
		assertTrue( seq.hasAtLeast( 2 ) );
		assertFalse( seq.hasAtLeast( 3 ) );
		assertFalse( seq.isEmpty() );
		{
			Iterator< String > it = seq.iterator();
			assertEquals( "foo", it.next() );
			assertEquals( "bar", it.next() );
			assertFalse( it.hasNext() );
		}
	}

}
