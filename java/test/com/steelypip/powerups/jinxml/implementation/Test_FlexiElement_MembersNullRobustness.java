package com.steelypip.powerups.jinxml.implementation;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.stdmodel.FlexiElement;
import com.steelypip.powerups.util.multimap.MultiMap;


public class Test_FlexiElement_MembersNullRobustness {
	
	@Test( expected=Exception.class )
	public void setValues_StringNull() {
		FlexiElement e = new FlexiElement( "" );
		e.setChildren( "", null );
	}
	
	@Test( expected=Exception.class )
	public void setValues_StringListWithNull() {
		FlexiElement e = new FlexiElement( "" );
		List< Element > children = new LinkedList< Element >();
		children.add(  null );
		e.setChildren( "", children );
	}
	
	@Test( expected=Exception.class )
	public void setMembers_addNullNull() {
		FlexiElement e = new FlexiElement( "" );
		MultiMap< String, Element > members = MultiMap.newMultiMap();
		members.add( null, null );
		e.setMembers( members );
	}
	
	@Test( expected=Exception.class )
	public void setMembers_addStringNull() {
		FlexiElement e = new FlexiElement( "" );
		MultiMap< String, Element > members = MultiMap.newMultiMap();
		members.add( "", null );
		e.setMembers( members );
	}
	
	@Test( expected=Exception.class )
	public void setMembers_addNullString() {
		FlexiElement e = new FlexiElement( "" );
		MultiMap< String, Element > members = MultiMap.newMultiMap();
		members.add( null, new FlexiElement( "" ) );
		e.setMembers( members );
	}
	
	@Test( expected=Exception.class )
	public void getValues_Null() {
		FlexiElement e = new FlexiElement( "" );
		List< Element > values = e.getChildrenAsList( "", true, true );
		values.add( null );
	}
	
}
