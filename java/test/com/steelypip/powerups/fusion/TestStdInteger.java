package com.steelypip.powerups.fusion;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.fusion.FlexiFusionFactory;
import com.steelypip.powerups.fusion.jsonimpl.IntegerFusion;

public class TestStdInteger {
	
	IntegerFusion zero, one;
	static FlexiFusionFactory INSTANCE = new FlexiFusionFactory();
	static @NonNull String CONSTANT = INSTANCE.nameConstant();
	static @NonNull String TYPE = INSTANCE.keyType();
	static @NonNull String VALUE = INSTANCE.keyValue();

	@Before
	public void setUp() throws Exception {
		 zero = new IntegerFusion( 0 );
		 one = new IntegerFusion( 1 );
	}

	@Test
	public void testGetName() {
		assertEquals( CONSTANT, this.zero.getName() );
	}

	@Test
	public void testGetInternedName() {
		assertSame( CONSTANT, this.zero.getInternedName() );
	}

	
	@Test
	public void testHasName() {
		String name = CONSTANT;
		assertTrue( this.zero.hasName( name ) );
		assertFalse( this.zero.hasName( "DingDong" ) );
		assertFalse( this.zero.hasName( null ) );
	}
	
	@Test( expected=UnsupportedOperationException.class )
	public void testSetName() {
		this.zero.setName( "Frogstar" );
	}
	
	@Test
	public void testTrimToSize() {
		//	No real idea how to test this. I will call it & if no errors, hurrah!
		this.zero.trimToSize();
	}
	
	@Test
	public void testGetValue() {
		assertEquals( INSTANCE.constTypeInteger(), this.zero.getValue( TYPE ) );
		assertEquals( "0", this.zero.getValue( VALUE ) );
		assertSame( 0L, this.zero.integerValue() );
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void badTestGetValue() {
		this.zero.getValue( "a3" );
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testGetValueWithIndex() {
		this.zero.getValue( "a1", 0 );
	}
	
	
	@Test
	public void testGetValueOtherwise() {
		assertEquals( INSTANCE.constTypeInteger(), this.zero.getValue( TYPE, null ) );
		assertNull( this.one.getValue( "a3", null ) );
	}
	

	@Test
	public void testGetValueOtherwiseWithIndex() {
		assertEquals( INSTANCE.constTypeInteger(), this.zero.getValue( TYPE, 0, null ) );
		assertNull( this.one.getValue( INSTANCE.keyType(), 1, null ) );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testBadSetValue() {
		this.one.setValue( TYPE, "boolean" );
	}

	@Test
	public void testSetValue() {
		this.one.setValue( INSTANCE.keyValue(), "99" );
		assertSame( 99L, this.one.integerValue());
	}

	@Test
	public void testSetValueWithIndex() {
		this.one.updateValue( VALUE, 0, "99" );
		assertSame( 99L, this.one.integerValue() );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void badTestSetValueWithIndex1() {
		this.one.updateValue( VALUE, 1, "99" );
	}

	
	@Test
	public void testSetValuesToList() {
		LinkedList< @NonNull String > different_implementation = new LinkedList<>();
		different_implementation.add( "99" );
		this.one.setAllValues( VALUE, different_implementation );
		assertEquals( "99", this.one.getValue( VALUE ) );
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testAddValue() {
		this.one.addValue( "A1", "Ninety nine" );
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testRemoveValue() {
		this.one.removeValue( "a2" );
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testRemoveValueWithIndex() {
		this.one.removeValue( "a2", 1 );
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testClearAllAttributes() {
		this.one.clearAllAttributes();
	}
	
	@Test
	public void testClearAttribute() {
		this.one.clearAttributes( "a2" );
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testBadClearAttribute() {
		this.one.clearAttributes( VALUE );
	}
	
	@Test
	public void testHasAnyAttributes() {
		assertTrue( this.one.hasAnyAttributes() );
	}
	
	@Test
	public void testHasAttribute() {
		assertTrue( this.one.hasAttribute( TYPE ) );
		assertTrue( this.one.hasAttribute( VALUE ) );
		assertFalse( this.one.hasAttribute( "a3" ) );
	}
	
	@Test
	public void testHasAttributeWithIndex() {
		assertTrue( this.one.hasValueAt( TYPE, 0 ) );
		assertFalse( this.one.hasValueAt( TYPE, 1 ) );
		assertTrue( this.one.hasValueAt( VALUE, 0 ) );
		assertFalse( this.one.hasValueAt( VALUE, 1 ) );
		assertFalse( this.one.hasValueAt( "a3", 0 ) );
	}
	
	@Test
	public void testHasAttributeWithValue() {
		assertTrue( this.one.hasAttribute( TYPE, INSTANCE.constTypeInteger() ) );
		assertFalse( this.one.hasAttribute( TYPE, "xxx" ) );
		assertTrue( this.one.hasAttribute( VALUE, "1" ) );
		assertFalse( this.one.hasAttribute( VALUE, "v2a" ) );
		assertFalse( this.one.hasAttribute( "a2", "xxx" ) );
	}
	
	@Test
	public void testHasAttributeWithIndexAndValue() {
		assertTrue( this.one.hasAttribute( TYPE, 0, INSTANCE.constTypeInteger() ) );
		assertFalse( this.one.hasAttribute( TYPE, 0, "xxx" ) );
		assertFalse( this.one.hasAttribute( TYPE, 1, "xxx" ) );
		assertTrue( this.zero.hasAttribute( VALUE, 0, "0" ) );
		assertFalse( this.zero.hasAttribute( VALUE, 1, "0" ) );
		assertFalse( this.zero.hasAttribute( "a2", 0, "xxx" ) );
		assertFalse( this.zero.hasAttribute( "a3", 0, "yyy" ) );
		assertFalse( this.zero.hasAttribute( "a3", 1, "yyy" ) );
	}
	
	@Test
	public void testHasSingleValue() {
		assertTrue( this.zero.hasOneValue( TYPE ) );
		assertFalse( this.zero.hasOneValue( "a2" ) );
	}
	
	@Test
	public void testSizeAttributes() {
		assertSame( 2, this.zero.sizeAttributes() );
		assertSame( 2, this.one.sizeAttributes() );
	}
	
	@Test
	public void testSizeKeys() {
		assertSame( 2, this.zero.sizeKeys() );
		assertSame( 2, this.one.sizeKeys() );
	}
	
	@Test
	public void testHasSizeKeys() {
		assertTrue( this.zero.hasSizeKeys( 2 ) );
		assertFalse( this.zero.hasSizeKeys( 1 ) );
		assertTrue( this.one.hasSizeKeys( 2 ) );
		assertFalse( this.one.hasSizeKeys( 1 ) );
	}
	
	@Test
	public void testHasNoKeys() {
		assertFalse( this.zero.hasNoKeys() );
	}
	
	@Test
	public void testHasKeys() {
		assertTrue( this.zero.hasAnyKeys() );
	}
	
	@Test
	public void testHasSizeValues() {
		assertTrue( this.zero.hasSizeValues( TYPE, 1 ) );
		assertTrue( this.zero.hasSizeValues( VALUE, 1 ) );
		assertTrue( this.zero.hasSizeValues( "a1", 0 ) );
		assertFalse( this.zero.hasSizeValues( TYPE, 0 ) );
		assertFalse( this.zero.hasSizeValues( VALUE, 0 ) );
		assertFalse( this.zero.hasSizeValues( "a1", 1 ) );
	}
	
	@Test
	public void testHasNoValues() {
		assertTrue( this.zero.hasNoValues( "a1" ) );
		assertFalse( this.zero.hasNoValues( TYPE ) );
		assertFalse( this.one.hasNoValues( VALUE ) );
		assertTrue( this.one.hasNoValues( "a3" ) );
	}
	
	@Test
	public void testHasValues() {
		assertFalse( this.zero.hasAnyValues( "a1" ) );
		assertTrue( this.zero.hasAnyValues( TYPE ) );
		assertTrue( this.one.hasAnyValues( VALUE ) );
		assertFalse( this.one.hasAnyValues( "a3" ) );
	}

	@Test
	public void testAttributesAsList() {
		final @NonNull List< Map.Entry< String, String > > elist = this.one.attributesToList();
		assertFalse( elist.isEmpty() );
		assertSame( 2, elist.size() );
	}

	@Test
	public void testValuesAsList() {
		final List< @NonNull String > blist = this.zero.valuesToList( "a1" );
		assertTrue( blist.isEmpty() );
		final List< @NonNull String > elist = this.one.valuesToList( TYPE );
		assertFalse( elist.isEmpty() );
		assertSame( 1, elist.size() );
	}

	@Test
	public void testFirstValuesAsMap() {
		final Map< @NonNull String, String > m1 = this.one.firstValuesToMap();
		assertFalse( m1.isEmpty() );
		assertSame( 2, m1.size() );
		assertTrue( m1.keySet().contains( TYPE ) );
	}


	@Test
	public void testAttributesAsPairMap() {
		final @NonNull Map< Pair< @NonNull String, @NonNull Integer >, String > m1 = this.one.attributesToPairMap();
		assertSame( 2, m1.size() );
	}

	@Test
	public void testHasAnyLinks() {
		assertFalse( this.one.hasAnyLinks() );

	}

	@Test( expected=UnsupportedOperationException.class )
	public void testAddLinks() {
		this.one.addChild( this.one );
	}

}
