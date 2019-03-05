package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Builder;
import com.steelypip.powerups.jinxml.Element;

import org.junit.Before;

public class Test_FlexiElement_Lists {
	
	Element element_with_kids;

	@Before
	public void setup() {
		Builder b = Element.newBuilder( "Test_FlexiElement_Lists" );
		b.include( Element.newArray() );
		b.include( Element.newIntValue( 456L ) );
		element_with_kids = b.newElement();
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void getChildrenAsList_ReturnsImmutableList() {
		List< Element > kids = element_with_kids.getChildrenAsList();
		kids.clear();
	}

}
