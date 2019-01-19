package com.steelypip.powerups.fusion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.common.Pair;


public class TestFlexiFusion {
	
	private static final @NonNull String EXAMPLE = "example";
	private static final @NonNull String BASE_NAME = "foo";
	FlexiFusion base;
	FlexiFusion example;
	

	@Before
	public void setUp() throws Exception {
		this.base = new FlexiFusion( BASE_NAME );
		this.example = new FlexiFusion( EXAMPLE );
		this.example.setValue( "a1", "v1" );
		this.example.setValue( "a2", "v2" );
		this.example.addValue( "a2", "v2a" );
	}

	@Test
	public void testGetName() {
		assertEquals( BASE_NAME, this.base.getName() );
	}

	@Test
	public void testGetInternedName() {
		assertSame( BASE_NAME.intern(), this.base.getInternedName() );
	}

	@Test
	public void testHasName() {
		assertTrue( this.example.hasName( EXAMPLE ) );
		assertFalse( this.example.hasName( "DingDong" ) );
		assertFalse( this.example.hasName( null ) );
	}
	
	@Test
	public void testSetName() {
		assertTrue( this.base.hasName( BASE_NAME ) );
		this.base.setName( EXAMPLE );
		assertFalse( EXAMPLE == BASE_NAME );
		assertTrue( this.base.hasName(  EXAMPLE ) );
	}
	
	@Test
	public void testTrimToSize() {
		//	No real idea how to test this. I will call it & if no errors, hurrah!
		this.example.trimToSize();
	}
	
	@Test
	public void testGetValue() {
		assertEquals( "v1", this.example.getValue( "a1" ) );
		assertEquals( "v2", this.example.getValue( "a2" ) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void badTestGetValue() {
		assertEquals( "v2", this.example.getValue( "a3" ) );
	}
	
	@Test
	public void testGetValueWithIndex() {
		assertEquals( "v1", this.example.getValue( "a1", 0 ) );
		assertEquals( "v2", this.example.getValue( "a2", 0 ) );
		assertEquals( "v2a", this.example.getValue( "a2", 1 ) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void badTestGetValueWithIndex1() {
		this.example.getValue( "a3", 0 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void badTestGetValueWithIndex2() {
		this.example.getValue( "a1", 1 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void badTestGetValueWithIndex3() {
		this.example.getValue( "a1", -1 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void badTestGetValueWithIndex4() {
		this.example.getValue( "a2", 3 );
	}
	
	@Test
	public void testGetValueOtherwise() {
		assertEquals( "v1", this.example.getValue( "a1" ) );
		assertEquals( "v2", this.example.getValue( "a2" ) );
		assertNull( this.example.getValue( "a3", null ) );

	}

	@Test
	public void testGetValueOtherwiseWithIndex() {
		assertEquals( "v1", this.example.getValue( "a1", 0 ) );
		assertEquals( "v2", this.example.getValue( "a2", 0 ) );
		assertEquals( "v2a", this.example.getValue( "a2", 1 ) );
		assertEquals( "xxx", this.example.getValue( "a3", 0, "xxx" ) );
		assertNull( this.example.getValue( "a1", 1, null ) );
		assertNull( this.example.getValue( "a1", -1, null ) );
		assertNull( this.example.getValue( "a2", 3, null ) );
	}

	@Test
	public void testSetValue() {
		this.example.setValue( "a3", "v3" );
		assertEquals( "v3", this.example.getValue( "a3" ) );
		this.example.setValue( "a2", "v2x" );
		assertEquals( "v2x", this.example.getValue( "a2" ) );
	}

	@Test
	public void testSetValueWithIndex() {
//		this.example.updateValue( "a3", 0, "v3" );
//		assertEquals( "v3", this.example.getValue( "a3" ) );
		this.example.updateValue( "a2", 0, "v2x" );
		assertEquals( "v2x", this.example.getValue( "a2" ) );
//		this.example.updateValue( "a1", 1, "v1a" );
//		assertEquals( "v1a", this.example.getValue( "a1", 1 ) );
		this.example.updateValue( "a2", 1, "v2ax" );
		assertEquals( "v2ax", this.example.getValue( "a2", 1 ) );
	}

	@Test(expected=IllegalArgumentException.class)
	public void badTestSetValueWithIndex1() {
		this.example.updateValue( "a3", 1, "v3" );
	}

	@Test(expected=IllegalArgumentException.class)
	public void badTestSetValueWithIndex2() {
		this.example.updateValue( "a2", -1, "v3" );
	}
	
	@Test
	public void testSetValuesToList() {
		LinkedList< @NonNull String > different_implementation = new LinkedList<>();
		different_implementation.add( "Ninety nine" );
		this.base.setAllValues( "A1", different_implementation );
		assertEquals( "Ninety nine", this.base.getValue( "A1" ) );
	}

	//addValue( @NonNull String key, @NonNull String value )
	@Test
	public void testAddValue() {
		this.base.addValue( "A1", "Ninety nine" );
		assertEquals( "Ninety nine", this.base.getValue( "A1" ) );
		this.example.addValue( "a1", "v1a" );
		assertEquals( "v1a", this.example.getValue( "a1", 1 ) );
	}
	
	@Test
	public void testRemoveValue() {
		this.example.removeValue( "a2" );
		assertEquals( "v2a", this.example.getValue( "a2" ) );
		this.example.removeValue( "a1" );
		assertFalse( this.example.hasAttribute( "a1" ) );
		assertTrue( this.example.hasAttribute( "a2" ) );
	}
	
	@Test
	public void testRemoveValueWithIndex() {
		this.example.removeValue( "a2", 1 );
		assertEquals( "v2", this.example.getValue( "a2" ) );
		assertSame( 1, this.example.sizeValues( "a2" ) );
	}
	
	@Test
	public void testClearAllAttributes() {
		assertTrue( this.example.hasAnyAttributes() );
		this.example.clearAllAttributes();
		assertFalse( this.example.hasAnyAttributes() );
	}
	
	@Test
	public void testClearAttribute() {
		assertTrue( this.example.hasAttribute( "a1" ) );
		assertTrue( this.example.hasAttribute( "a2" ) );
		this.example.clearAttributes( "a2" );
		assertTrue( this.example.hasAttribute( "a1" ) );
		assertFalse( this.example.hasAttribute( "a2" ) );
	}
	
	@Test
	public void testHasAnyAttributes() {
		assertTrue( this.example.hasAnyAttributes() );
		assertFalse( this.base.hasAnyAttributes() );
	}
	
	@Test
	public void testHasAttribute() {
		assertTrue( this.example.hasAttribute( "a1" ) );
		assertTrue( this.example.hasAttribute( "a2" ) );
		assertFalse( this.example.hasAttribute( "a3" ) );
	}
	
	@Test
	public void testHasAttributeWithIndex() {
		assertTrue( this.example.hasValueAt( "a1", 0 ) );
		assertFalse( this.example.hasValueAt( "a1", 1 ) );
		assertTrue( this.example.hasValueAt( "a2", 0 ) );
		assertTrue( this.example.hasValueAt( "a2", 1 ) );
		assertFalse( this.example.hasValueAt( "a2", 2 ) );
		assertFalse( this.example.hasAttribute( "a3" ) );
	}
	
	@Test
	public void testHasAttributeWithValue() {
		assertTrue( this.example.hasAttribute( "a1", "v1" ) );
		assertFalse( this.example.hasAttribute( "a1", "xxx" ) );
		assertTrue( this.example.hasAttribute( "a2", "v2" ) );
		assertTrue( this.example.hasAttribute( "a2", "v2a" ) );
		assertFalse( this.example.hasAttribute( "a2", "xxx" ) );
		assertFalse( this.example.hasAttribute( "a3", "yyy" ) );
	}
	
	
	@Test
	public void testHasAttributeWithIndexAndValue() {
		assertTrue( this.example.hasAttribute( "a1", 0, "v1" ) );
		assertFalse( this.example.hasAttribute( "a1", 0, "xxx" ) );
		assertFalse( this.example.hasAttribute( "a1", 1, "xxx" ) );
		assertTrue( this.example.hasAttribute( "a2", 0, "v2" ) );
		assertFalse( this.example.hasAttribute( "a2", 1, "v2" ) );
		assertFalse( this.example.hasAttribute( "a2", 0, "v2a" ) );
		assertTrue( this.example.hasAttribute( "a2", 1, "v2a" ) );
		assertFalse( this.example.hasAttribute( "a2", 0, "xxx" ) );
		assertFalse( this.example.hasAttribute( "a3", 0, "yyy" ) );
		assertFalse( this.example.hasAttribute( "a3", 1, "yyy" ) );
	}
	
	@Test
	public void testHasSingleValue() {
		assertTrue( this.example.hasOneValue( "a1" ) );
		assertFalse( this.example.hasOneValue( "a2" ) );
		assertFalse( this.example.hasOneValue( "a3" ) );
	}
	
	@Test
	public void testSizeAttributes() {
		assertSame( 0, this.base.sizeAttributes() );
		assertSame( 3, this.example.sizeAttributes() );
	}
	
	@Test
	public void testSizeKeys() {
		assertSame( 0, this.base.sizeKeys() );
		assertSame( 2, this.example.sizeKeys() );
	}
	
	@Test
	public void testHasSizeKeys() {
		assertTrue( this.base.hasSizeKeys( 0 ) );
		assertFalse( this.base.hasSizeKeys( 1 ) );
		assertTrue( this.example.hasSizeKeys( 2 ) );
		assertFalse( this.example.hasSizeKeys( 1 ) );
	}
	
	@Test
	public void testHasNoKeys() {
		assertTrue( this.base.hasNoKeys() );
		assertFalse( this.example.hasNoKeys() );
	}
	
	@Test
	public void testHasKeys() {
		assertFalse( this.base.hasAnyKeys() );
		assertTrue( this.example.hasAnyKeys() );
	}
	
	@Test
	public void testHasSizeValues() {
		assertTrue( this.base.hasSizeValues( "a1", 0 ) );
		assertFalse( this.example.hasSizeValues( "a1", 0 ) );
		assertTrue( this.example.hasSizeValues( "a1", 1 ) );
		assertTrue( this.example.hasSizeValues( "a2", 2 ) );
		assertTrue( this.example.hasSizeValues( "a3", 0 ) );
		assertFalse( this.example.hasSizeValues( "a3", 1 ) );
	}
	
	@Test
	public void testHasNoValues() {
		assertTrue( this.base.hasNoValues( "a1" ) );
		assertFalse( this.example.hasNoValues( "a1" ) );
		assertFalse( this.example.hasNoValues( "a2" ) );
		assertTrue( this.example.hasNoValues( "a3" ) );
	}
	
	@Test
	public void testHasValues() {
		assertFalse( this.base.hasAnyValues( "a1" ) );
		assertTrue( this.example.hasAnyValues( "a1" ) );
		assertTrue( this.example.hasAnyValues( "a2" ) );
		assertFalse( this.example.hasAnyValues( "a3" ) );
	}

	@Test
	public void testAttributesAsList() {
		final List< Map.Entry< String, String > > blist = this.base.attributesToList();
		assertTrue( blist.isEmpty() );
		final List< Map.Entry< String, String > > elist = this.example.attributesToList();
		assertFalse( elist.isEmpty() );
		assertSame( 3, elist.size() );
	}

	@Test
	public void testValuesAsList() {
		final List< String > blist = this.base.valuesToList( "a1" );
		assertTrue( blist.isEmpty() );
		final List< String > elist = this.example.valuesToList( "a1" );
		assertFalse( elist.isEmpty() );
		assertSame( 1, elist.size() );
	}

	@Test
	public void testFirstValuesAsMap() {
		final Map< String, String > m1 = this.base.firstValuesToMap();
		assertTrue( m1.isEmpty() );
		final Map< String, String > m2 = this.example.firstValuesToMap();
		assertSame( 2, m2.size() );
	}

	@Test
	public void testAttributesAsPairMap() {
		final Map< Pair< String, Integer >, String > m1 = this.base.attributesToPairMap();
		assertTrue( m1.isEmpty() );
		final Map< Pair< String, Integer >, String > m2 = this.example.attributesToPairMap();
		assertSame( 3, m2.size() );
	}
	
	@Test
	public void testGetChild() {
//		this.example.getChild( )
	}
	
	@Test
	public void testHasAnyLinks() {
		assertFalse( this.base.hasAnyLinks() );
		this.example.addChild( this.base );
		assertTrue( this.example.hasAnyLinks() );
	}
	
}
