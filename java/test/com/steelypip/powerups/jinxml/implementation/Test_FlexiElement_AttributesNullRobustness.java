package com.steelypip.powerups.jinxml.implementation;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.steelypip.powerups.jinxml.stdmodel.FlexiElement;
import com.steelypip.powerups.util.multimap.MultiMap;


public class Test_FlexiElement_AttributesNullRobustness {

	
	@Test( expected=Exception.class )
	public void setValues_StringNull() {
		FlexiElement e = new FlexiElement( "" );
		e.setValues( "", null );
	}
	
	@Test( expected=Exception.class )
	public void setValues_StringListWithNull() {
		FlexiElement e = new FlexiElement( "" );
		List< String > values = new LinkedList< String >();
		values.add(  null );
		e.setValues( "", values );
	}
	
	@Test( expected=Exception.class )
	public void setAttributes_addNullNull() {
		FlexiElement e = new FlexiElement( "" );
		MultiMap< String, String > attributes = MultiMap.newMultiMap();
		attributes.add( null, null );
		e.setAttributes( attributes );
	}
	
	@Test( expected=Exception.class )
	public void setAttributes_addStringNull() {
		FlexiElement e = new FlexiElement( "" );
		MultiMap< String, String > attributes = MultiMap.newMultiMap();
		attributes.add( "", null );
		e.setAttributes( attributes );
	}
	
	@Test( expected=Exception.class )
	public void setAttributes_addNullString() {
		FlexiElement e = new FlexiElement( "" );
		MultiMap< String, String > attributes = MultiMap.newMultiMap();
		attributes.add( null, "" );
		e.setAttributes( attributes );
	}
	
	//  indirect setting through views
	@Test( expected=Exception.class )
	public void getAttributesAsMultiMap_NullNull() {
		FlexiElement e = new FlexiElement( "" );
		MultiMap< String, String > mm = e.getAttributesAsMultiMap();
		mm.add( null, null );
	}
	
	@Test( expected=Exception.class )
	public void getAttributesAsMultiMap_StringNull() {
		FlexiElement e = new FlexiElement( "" );
		MultiMap< String, String > mm = e.getAttributesAsMultiMap();
		mm.add( "", null );
	}
	
	@Test( expected=Exception.class )
	public void getAttributesAsMultiMap_NullString() {
		FlexiElement e = new FlexiElement( "" );
		MultiMap< String, String > mm = e.getAttributesAsMultiMap();
		mm.add( null, "" );
	}
	
	@Test( expected=Exception.class )
	public void getValues_Null() {
		FlexiElement e = new FlexiElement( "" );
		List< String > values = e.getValuesAsList( "", true, true );
		values.add( null );
	}
	
}
