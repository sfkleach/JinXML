package com.steelypip.powerups.jimxml;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.FlexiElement;
import com.steelypip.powerups.util.multimap.MultiMap;

public class TestFlexiElement {

	@Test( expected=NullPointerException.class )
	public void getName_invalid() {
		//	Act
		new FlexiElement( null );
	}

	@Test
	public void getName_valid() {
		String name = "foo";
		Element e = new FlexiElement( name );
		assertEquals( name, e.getName() );
	}
	
	@Test
	public void countAttributes_none() {
		Element e = new FlexiElement( "" );
		assertEquals( 0, e.countAttributes() );
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
	public void getAttributesIterator() {
		Element e = new FlexiElement( "" );
		e.addLastValue( "one", "1" );
		e.addLastValue( "two", "2" );
		e.addLastValue( "three", "3" );
		e.addLastValue( "four", "4" );
		int count = 0;
		Iterator< Map.Entry< String, String > > it = e.getAttributesIterator();
		for ( Map.Entry< String, String > n : (Iterable<Map.Entry<String, String>>) () -> it ) {
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
			count += 1;
		}
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
		e.setValue( "foo", "bar" );		
		assertNull( e.getValue( "" ) );
		assertEquals( "bar", e.getValue( "foo" ) );
	}
	
	@Test
	public void getValue_onTwoAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.setValue( "foo", "bar" );		
		e.addValue( "gort", "trog" );		
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
		e.setValue( "foo", "bar" );		
		assertEquals( "no", e.getValue( "", "no" ) );
		assertEquals( "bar", e.getValue( "foo", null ) );
	}
	
	@Test
	public void getValue_otherwise_onTwoAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.setValue( "foo", "bar" );		
		e.addValue( "gort", "trog" );		
		assertEquals( "no", e.getValue( "", "no" ) );
		assertEquals( "bar", e.getValue( "foo", null ) );
		assertEquals( "trog", e.getValue( "gort", "99" ) );
		assertNull( e.getValue( "wiffle", null ) );
	}
	
	@Test
	public void getValue_position_onEmptyElement() {
		Element e = new FlexiElement( "this is a name" );
		assertNull( e.getValue( "", 0 ) );
		assertNull( "bar", e.getValue( "foo", 1 ) );
	}
	
	@Test
	public void getValue_position_onSingleAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.setValue( "foo", "bar" );		
		assertNull( e.getValue( "", 0 ) );
		assertEquals( "bar", e.getValue( "foo", 0 ) );
		assertNull( e.getValue( "foo", 1 ) );
	}
	
	@Test
	public void getValue_position_onTwoAttributeElement() {
		Element e = new FlexiElement( "this is a name" );
		e.setValue( "foo", "bar" );		
		e.addValue( "foo", "trog" );		
		assertNull( e.getValue( "", 0 ) );
		assertEquals( "bar", e.getValue( "foo", 0 ) );
		assertEquals( "trog", e.getValue( "foo", 1 ) );
		assertNull( e.getValue( "wiffle", 1 ) );
	}
	
}
