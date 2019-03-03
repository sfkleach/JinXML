package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.jinxml.Attribute;
import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Member;
import com.steelypip.powerups.jinxml.stdmodel.FlexiElement;
import com.steelypip.powerups.util.multimap.MultiMap;

public class Test_FlexiElement {
	
	Element attrs_empty;
	Element attrs_one;
	Element attrs_three;
	Element members_empty;
	Element members_one;
	Element members_three;
	
	@SuppressWarnings("null")
	@Before
	public void Setup() {
		attrs_empty = new FlexiElement( "EMPTY" );
		
		attrs_one = new FlexiElement( "ONE" );
		attrs_one.addLastValue( "one", "1" );
		
		attrs_three = new FlexiElement( "THREE" );
		attrs_three.addLastValue( "one", "1.1" );
		attrs_three.addLastValue( "two", "2.1" );
		attrs_three.addLastValue( "two", "2.2" );
		attrs_three.addLastValue( "three", "3.1" );
		attrs_three.addLastValue( "three", "3.2" );
		attrs_three.addLastValue( "three", "3.3" );
		
		members_empty = new FlexiElement( "ZERO" );
		
		members_one = new FlexiElement( "ONE" );
		members_one.addLastChild( "one", new FlexiElement("ONE 1.1") );
		
		members_three = new FlexiElement( "THREE" );
		members_three.addLastChild( "one", new FlexiElement("THREE 1.1") );
		members_three.addLastChild( "two", new FlexiElement("THREE 2.1") );
		members_three.addLastChild( "two", new FlexiElement("THREE 2.2") );
		members_three.addLastChild( "three", new FlexiElement("THREE 3.1") );
		members_three.addLastChild( "three", new FlexiElement("THREE 3.2") );
		members_three.addLastChild( "three", new FlexiElement("THREE 3.3") );
		
	}

	@Test
	public void getName_Valid() {
		String name = "foo";
		Element e = new FlexiElement( name );
		assertEquals( name, e.getName() );
	}
	
	@Test
	public void hasName_Valid() {
		String name = "foo";
		Element e = new FlexiElement( name );
		assertTrue( e.hasName( name ) );
	}
	
	@Test
	public void countAttributes_None() {
		Element e = new FlexiElement( "" );
		assertEquals( 0, e.countAttributes() );
		assertTrue( e.hasNoAttributes() );
		assertTrue( ! e.hasAnyAttributes() );
	}

	@Test
	public void countAttributes_oneItemUsingMultiMap() {
		Element e = new FlexiElement( "" );
		MultiMap< String, String > m = e.getAttributesAsMultiMap( true, true );
		m.add( "left", "right" );
		assertEquals( 1, e.countAttributes() );
	}
	
	@Test
	public void countAttributes_twoItemsUsingSetAttributes() {
		MultiMap< String, String > m = MultiMap.newMultiMap();
		m.add( "left", "right" );
		m.add( "foo", "bar" );
		Element e = new FlexiElement( "" );
		e.setAttributes( m ); 
		assertEquals( 2, e.countAttributes() );
	}
	
	@Test
	public void countAttributes_threeItemsUsingSetValues() {
		List< String > m = new ArrayList<>();
		m.add( "left" );
		m.add( "right" );
		m.add( "foo" );
		Element e = new FlexiElement( "" );
		e.setValues( "key", m ); 
		assertEquals( 3, e.countAttributes() );
	}
	
	@Test
	public void countAttribute_fourItemsUsingAddLastValue() {
		Element e = new FlexiElement( "" );
		e.addLastValue( "left", "right" );
		e.addLastValue( "left", "right" );
		e.addLastValue( "left", "right" );
		e.addLastValue( "left", "right" );
		assertEquals( 4, e.countAttributes() );
	}
	
	@Test
	public void getAttributesStream() {
		Element e = new FlexiElement( "" );
		e.addLastValue( "one", "1" );
		e.addLastValue( "two", "2" );
		e.addLastValue( "three", "3" );
		e.addLastValue( "four", "4" );
		Stream< Attribute > it = e.getAttributesStream();
		long count = ( 
			it.map( 
				n -> {
				if ( "one".equals( n.getKey() ) ) {
					assertEquals( "1", n.getValue() );
				} else if ( "two".equals( n.getKey() ) ) {
					assertEquals( "2", n.getValue() );
				} else if ( "three".equals( n.getKey() ) ) {
					assertEquals( "3", n.getValue() );
				} else if ( "four".equals( n.getKey() ) ) {
					assertEquals( "4", n.getValue() );
				} else {
					fail();
				}
				return 1;
				}
			)
		).count();
		assertEquals( 4, count );
	}
	
	@Test
	public void getValue_onEmptyElement() {
		Element e = new FlexiElement( "this is a name" );
		assertNull( e.getValue( "" ) );
		assertNull( e.getValue( "foo" ) );
	}
	
	@Test
	public void getValue_onSingleAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.addLastValue( "foo", "bar" );		
		assertNull( e.getValue( "" ) );
		assertEquals( "bar", e.getValue( "foo" ) );
	}
	
	@Test
	public void getValue_onTwoAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.addLastValue( "foo", "bar" );		
		e.addLastValue( "gort", "trog" );		
		assertNull( e.getValue( "" ) );
		assertEquals( "bar", e.getValue( "foo" ) );
		assertEquals( "trog", e.getValue( "gort" ) );
	}
	
	@Test
	public void getValue_otherwise_onEmptyElement() {
		Element e = new FlexiElement( "this is a name" );
		assertNull( e.getValue( "", null ) );
		assertEquals( "bar", e.getValue( "foo", "bar" ) );
	}
	
	@Test
	public void getValue_otherwise_onSingleAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.addLastValue( "foo", "bar" );		
		assertEquals( "no", e.getValue( "", "no" ) );
		assertEquals( "bar", e.getValue( "foo", null ) );
	}
	
	@Test
	public void getValue_otherwise_onTwoAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.addLastValue( "foo", "bar" );		
		e.addLastValue( "gort", "trog" );		
		assertEquals( "no", e.getValue( "", "no" ) );
		assertEquals( "bar", e.getValue( "foo", null ) );
		assertEquals( "trog", e.getValue( "gort", "99" ) );
		assertNull( e.getValue( "wiffle", null ) );
	}
	
	@Test
	public void getValue_position_onEmptyElement() {
		Element e = new FlexiElement( "this is a name" );
		assertNull( e.getValue( "", 0 ) );
		assertNull( e.getValue( "foo", 1 ) );
	}
	
	@Test
	public void getValue_position_onSingleAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.addLastValue( "foo", "bar" );		
		assertNull( e.getValue( "", 0 ) );
		assertEquals( "bar", e.getValue( "foo", 0 ) );
		assertNull( e.getValue( "foo", 1 ) );
	}
	
	@Test
	public void getValue_position_onTwoAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.addLastValue( "foo", "bar" );		
		e.addLastValue( "foo", "trog" );		
		assertNull( e.getValue( "", 0 ) );
		assertEquals( "bar", e.getValue( "foo", 0 ) );
		assertEquals( "trog", e.getValue( "foo", 1 ) );
		assertNull( e.getValue( "wiffle", 1 ) );
	}
	
	@Test 
	public void getFirstValue_onEmptyElement() {
		Element e = new FlexiElement( "this is a name" );
		assertEquals( "result", e.getFirstValue( "", "result" ) );
	}
	
	@Test 
	public void getFirstValue_onSingleAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.addLastValue( "foo", "bar" );		
		assertEquals( "result", e.getFirstValue( "", "result" ) );
		assertEquals( "bar", e.getFirstValue( "foo", "result" ) );
	}
	
	@Test 
	public void getFirstValue_onTwoAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.addLastValue( "foo", "bar" );		
		e.addLastValue( "foo", "trog" );		
		assertEquals( "result", e.getFirstValue( "", "result" ) );
		assertEquals( "bar", e.getFirstValue( "foo", "result" ) );
	}
	
	//	getLastValue( String key, String? default = null ) -> String?
	@Test 
	public void getLastValue_onEmptyElement() {
		Element e = new FlexiElement( "this is a name" );
		assertEquals( "result", e.getLastValue( "", "result" ) );
	}
	
	@Test 
	public void getLastValue_onSingleAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.addLastValue( "foo", "bar" );		
		assertEquals( "result", e.getLastValue( "", "result" ) );
		assertEquals( "bar", e.getLastValue( "foo", "result" ) );
	}
	
	@Test 
	public void getLastValue_onTwoAttributeElement() {
		Element empty = new FlexiElement( "this is a name" );
		empty.addLastValue( "foo", "bar" );		
		empty.addLastValue( "foo", "trog" );		
		assertEquals( "result", empty.getLastValue( "", "result" ) );
		assertEquals( "trog", empty.getLastValue( "foo", "result" ) );
	}
	
	@Test 
	public void countValues() {
		assertEquals( 0, attrs_three.countValues( "zero" ) );
		assertEquals( 1, attrs_three.countValues( "one" ) );
		assertEquals( 2, attrs_three.countValues( "two" ) );
		assertEquals( 3, attrs_three.countValues( "three" ) );
	}
	
	@Test 
	public void getValuesAsList_OnEmpty() {
		//	Act
		List< String > values = attrs_empty.getValuesAsList( "zero" );
		//	Assert
		assertTrue( values.isEmpty() );
		//	Act
		attrs_empty.addLastValue( "zero", "0" );
		//	Assert
		assertTrue( values.isEmpty() );
		//	Act & Assert
		try {
			values.add( "NOT ALLOWED" );
			fail();
		} catch ( UnsupportedOperationException ex ) {
		}
	}
	
	@Test 
	public void getValuesAsList_MutableOnEmpty() {
		//	Act
		List< String > values = attrs_empty.getValuesAsList( "zero", false, true );
		//	Assert
		assertTrue( values.isEmpty() );
		//	Act
		attrs_empty.addLastValue( "zero", "0" );
		//	Assert
		assertTrue( values.isEmpty() );
		//	Act & Assert
		values.add( "ALLOWED" );
	}
	
	@Test 
	public void getValuesAsList_MutableViewOnEmpty() {
		//	Act
		List< String > values = attrs_empty.getValuesAsList( "zero", true, true );
		//	Assert
		assertTrue( values.isEmpty() );
		//	Act
		attrs_empty.addLastValue( "zero", "0A" );
		//	Assert
		assertEquals( 1, values.size() );
		//	Arrange
		values.add( "0B" );
		values.add( "0D" );
		values.add( 2, "0C" );
		//  Assert
		assertEquals( 4, attrs_empty.countValues( "zero" ) );
		assertEquals( "0C", attrs_empty.getValue( "zero", 2 ) );
		assertEquals( "0D", attrs_empty.getValue( "zero", 3 ) );
	}
	
	@Test 
	public void getValuesAsList_OnSingle() {
		//	Act
		List< String > values = attrs_one.getValuesAsList( "one" );
		//	Assert
		assertEquals( 1, values.size() );
		assertEquals( "1", values.get( 0 ) );
		//	Act
		attrs_empty.addLastValue( "one", "junk" );
		//	Assert
		assertEquals( "1", values.get( 0 ) );
		//	Act & Assert
		try {
			values.add( "NOT ALLOWED" );
			fail();
		} catch ( UnsupportedOperationException ex ) {
		}		
	}
	
	@Test 
	public void getValuesAsList_OnThree() {
		//	Act
		List< String > values = attrs_three.getValuesAsList( "three" );
		//	Assert
		assertEquals( 3, values.size() );
		assertEquals( "3.2", values.get( 1 ) );
		//	Act
		attrs_empty.addLastValue( "three", "junk" );
		//	Assert
		assertEquals( "3.2", values.get( 1 ) );
		//	Act & Assert
		try {
			values.add( "NOT ALLOWED" );
			fail();
		} catch ( UnsupportedOperationException ex ) {
		}		
	}
	
	@Test 
	public void getValuesAsList_MutableOnThree() {
		//	Act
		List< String > values = attrs_three.getValuesAsList( "three", false, true );
		//	Assert
		assertEquals( 3, values.size() );
		assertEquals( "3.2", values.get( 1 ) );
		//	Act
		attrs_empty.addLastValue( "three", "junk" );
		//	Assert
		assertEquals( "3.2", values.get( 1 ) );
		//	Act & Assert
		values.add( "NOT ALLOWED" );
	}
	
	@Test 
	public void getValuesAsList_MutableViewOnThree() {
		//	Act
		List< String > values = attrs_three.getValuesAsList( "three", true, true );
		//	Assert
		assertEquals( 3, values.size() );
		assertEquals( "3.2", values.get( 1 ) );
		//	Act
		attrs_three.addLastValue( "three", "3.4" );
		//	Assert
		assertEquals( "3.4", values.get( 3 ) );
		//	Act & Assert
		values.add( "ALLOWED" );
	}
	
	@Test 
	public void getValuesAsList_ImmutableViewOnThree() {
		//	Act
		List< String > values = attrs_three.getValuesAsList( "three", true, false );
		//	Assert
		assertEquals( 3, values.size() );
		assertEquals( "3.2", values.get( 1 ) );
		//	Act
		attrs_three.addLastValue( "three", "3.4" );
		//	Assert
		assertEquals( "3.4", values.get( 3 ) );
		//	Act & Assert
		try {
			values.add( "NOT ALLOWED" );
			fail();
		} catch ( UnsupportedOperationException ex ) {
		}
	}

	@Test
	public void countMembers_None() {
		assertEquals( 0, this.members_empty.countMembers() );
		assertTrue( this.members_empty.hasNoAttributes() );
		assertTrue( ! this.members_empty.hasAnyAttributes() );
	}
	
	@Test
	public void hasSelector_None() {
		assertTrue( ! this.members_empty.hasSelector( "foo" ) );
		assertTrue( this.members_one.hasSelector( "one" ) );
	}
	
	

	@Test
	public void countMembers_OneItemUsingMultiMap() {
		MultiMap< String, Element > m = this.members_empty.getMembersAsMultiMap( true, true );
		m.add( "left", this.members_one );
		assertEquals( 1, this.members_empty.countMembers() );
	}
		
	@Test
	public void countMembers_ThreeItemsUsingSetValues() {
		assertEquals( 3, this.members_three.countChildren( "three" ) );
	}	

	@Test
	public void getMembersIterator_OnThree() {
		List< Member > list = new ArrayList<>();
		members_three.getMembersStream().forEach( list::add );
		assertEquals( 6, list.size() ); 
		
		{
			List< Element > a = list.stream().filter( e -> "one".equals( e.getSelector() ) ).map( e -> e.getChild() ).collect( Collectors.toList() );
			assertEquals( 1, a.size() );
			assertEquals( "THREE 1.1", a.get( 0 ).getName() );
		}
		
		{
			List< Element > a = list.stream().filter( e -> "two".equals( e.getSelector() ) ).map( e -> e.getChild() ).collect( Collectors.toList() );
			assertEquals( 2, a.size() );
			assertEquals( "THREE 2.1", a.get( 0 ).getName() );
			assertEquals( "THREE 2.2", a.get( 1 ).getName() );
		}
		
		{
			List< Element > a = list.stream().filter( e -> "three".equals( e.getSelector() ) ).map( e -> e.getChild() ).collect( Collectors.toList() );
			assertEquals( 3, a.size() );
			assertEquals( "THREE 3.1", a.get( 0 ).getName() );
			assertEquals( "THREE 3.2", a.get( 1 ).getName() );
			assertEquals( "THREE 3.3", a.get( 2 ).getName() );
		}
		
	}
	
	@Test
	public void getChild_onEmptyElement() {
		assertNull( members_empty.getChild() );
		assertNull( members_empty.getChild( "foo", null ) );
	}
	
	@Test
	public void getChild_onSingleAttributeElement() {
		assertNull( members_one.getChild() );
		assertEquals( "ONE 1.1", members_one.getChild( "one", null ).getName() );
	}
	
	@Test
	public void getChild_otherwise_onSingleAttributeElement() {
		assertEquals( "X", members_one.getChild( "", new FlexiElement( "X" ) ).getName() );
		assertEquals( "ONE 1.1", members_one.getChild( "one", null ).getName() );
	}
	
	@Test
	public void getChild_position_onEmptyElement() {
		assertNull( members_empty.getChild( "one", 1 ) );
	}
	
	@Test
	public void getChild_position_onSingleAttributeElement() {
		assertNull( members_one.getChild( "", 0 ) );
		assertEquals( "ONE 1.1", members_one.getChild( "one", 0 ).getName() );
		assertNull( members_one.getChild( "one", 1 ) );
	}
	
	@Test
	public void getChild_position_OnThreeAttributeElement() {		
		assertNull( members_three.getChild( "", 0 ) );
		assertEquals( "THREE 2.1", members_three.getChild( "two", 0 ).getName() );
		assertEquals( "THREE 2.2", members_three.getChild( "two", 1 ).getName() );
		assertNull( members_three.getChild( "wiffle", 1 ) );
	}

	@Test 
	public void getFirstChild_OnEmptyElement() {
		String name = "Stuff";
		assertEquals( name, members_empty.getFirstChild( "", new FlexiElement( name ) ).getName() );
	}
	
	@Test 
	public void getFirstChild_OnSingleAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		assertEquals( "this is a name", members_one.getFirstChild( "", e ).getName() );
		assertEquals( "ONE 1.1", members_one.getFirstChild( "one", e ).getName() );
	}
	
	@Test 
	public void getFirstChild_OnTwoAttributeElement() {
		Element e = new FlexiElement( "getFirstChild_OnTwoAttributeElement" );		
		assertEquals( "getFirstChild_OnTwoAttributeElement", members_three.getFirstChild( "", e ).getName() );
		assertEquals( "THREE 2.1", members_three.getFirstChild( "two", e ).getName() );
	}
	
	@Test 
	public void getLastChild_OnEmptyElement() {
		Element e = new FlexiElement( "getLastChild_OnEmptyElement" );
		assertEquals( "getLastChild_OnEmptyElement", e.getLastChild( "", e ).getName() );
	}
	
	@Test 
	public void getLastChild_OnSingleAttributeElement() {
		Element e = new FlexiElement( "getLastChild_OnSingleAttributeElement" );
		assertEquals( "getLastChild_OnSingleAttributeElement", members_one.getLastChild( "", e ).getName() );
		assertEquals( "ONE 1.1", members_one.getLastChild( "one", e ).getName() );
	}
	
	@Test 
	public void getLastChild_OnThreeAttributeElement() {
		Element d = new FlexiElement( "getLastChild_OnThreeAttributeElement" );		
		assertEquals( "getLastChild_OnThreeAttributeElement", members_three.getLastChild( "", d ).getName() );
		assertEquals( "THREE 2.2", members_three.getLastChild( "two", d ).getName() );
	}
		
	
	@Test
	public void addFirstValue_ToEmpty() {
		Element d = new FlexiElement( "addFirstValue_ToEmpty" );		
		d.addFirstValue( "key", "1" );
		d.addFirstValue( "key", "2" );
		assertEquals( "2", d.getFirstValue( "key" ) );
		assertEquals( "1", d.getLastValue( "key" ) );

	}
	
	@Test
	public void addFirstChild_ToEmpty() {
		Element d = new FlexiElement( "addFirstChild_ToEmpty" );		
		d.addFirstChild( "key", Element.newElement( "1" ) );
		d.addFirstChild( "key", Element.newElement( "2" ) );
		assertEquals( "2", d.getFirstChild( "key" ).getName() );
		assertEquals( "1", d.getLastChild( "key" ).getName() );

	}
	
	@Test
	public void removeFirstChild_ToNonEmpty() {
		Element d = new FlexiElement( "removeFirstChild_ToNonEmpty" );		
		d.addFirstChild( "key", Element.newElement( "1" ) );
		d.addFirstChild( "key", Element.newElement( "2" ) );
		Element v = d.removeFirstChild( "key" );
		assertEquals( "2", v.getName() );
		assertEquals( "1", d.getLastChild( "key" ).getName() );
		assertEquals( 1, d.countMembers() );
	}
	
	@Test
	public void removeFirstValue_ToNonEmpty() {
		Element d = new FlexiElement( "removeFirstValue_ToNonEmpty" );		
		d.addFirstValue( "key", "1" );
		d.addFirstValue( "key", "2" );
		String v = d.removeFirstValue( "key" );
		assertEquals( "2", v );
		assertEquals( "1", d.getLastValue( "key" ) );
		assertEquals( 1, d.countAttributes() );
	}
	
	@Test
	public void removeFirstValue_WithOtherwise_ToNonEmpty() {
		Element d = new FlexiElement( "removeFirstValue_WithOtherwise_ToNonEmpty" );		
		d.addFirstValue( "key", "1" );
		d.addFirstValue( "key", "2" );
		String v = d.removeFirstValue( "not_a_key", "quark" );
		assertEquals( "quark", v );
	}

	@Test
	public void removeFirstChild_WithOtherwise_ToNonEmpty() {
		Element d = new FlexiElement( "removeFirstChild_WithOtherwise_ToNonEmpty" );		
		d.addFirstChild( "key", Element.newElement( "1" ) );
		d.addFirstChild( "key", Element.newElement( "2" ) );
		Element v = d.removeFirstChild( "not_a_key", Element.newElement( "quark" ) );
		assertEquals( "quark", v.getName() );
	}

	@Test
	public void removeLastValue_ToNonEmpty() {
		Element d = new FlexiElement( "addFirstValue_ToEmpty" );		
		d.addFirstValue( "key", "1" );
		d.addFirstValue( "key", "2" );
		d.addFirstValue( "key", "3" );
		String v3 = d.removeFirstValue( "key" );
		String v1 = d.removeLastValue( "key" );
		String quark = d.removeLastValue( "not_a_key", "quark" );
		assertEquals( "quark", quark );
		assertEquals( "1", v1 );
		assertEquals( "3", v3 );
		assertEquals( "2", d.getLastValue( "key" ) );
		assertEquals( 1, d.countAttributes() );
	}
	
	@Test
	public void removeLastChild_ToNonEmpty() {
		Element d = new FlexiElement( "removeLastChild_ToNonEmpty" );		
		d.addFirstChild( "key", Element.newElement( "1" ) );
		d.addFirstChild( "key", Element.newElement( "2" ) );
		d.addFirstChild( "key", Element.newElement( "3" ) );
		Element v3 = d.removeFirstChild( "key" );
		Element v1 = d.removeLastChild( "key" );
		Element quark = d.removeLastChild( "not_a_key", Element.newElement( "quark" ) );
		assertEquals( "quark", quark.getName() );
		assertEquals( "1", v1.getName() );
		assertEquals( "3", v3.getName() );
		assertEquals( "2", d.getLastChild( "key" ).getName() );
		assertEquals( 1, d.countMembers() );
	}
	

}
