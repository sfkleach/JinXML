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
		Iterator< Map.Entry< String, String > > it = e.getAttributesIterator();
		for ( Map.Entry< String, String > e1 : (Iterable<Map.Entry<String, String>>) () -> it ) {
			
		}
		
	}
	
}
