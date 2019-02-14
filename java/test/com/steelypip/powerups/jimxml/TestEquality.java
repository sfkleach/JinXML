package com.steelypip.powerups.jimxml;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.implementation.FlexiElement;

public class TestEquality {
	
	@Test
	public void equals_EmptyElements() {
		Element e0 = new FlexiElement( "foo" );
		Element e1 = new FlexiElement( "foo" );
		assertTrue( e0.equals(  e1  ) );
	}
	
	@Test
	public void equals_Attributes() {
		Element e0 = new FlexiElement( "foo" );
		e0.addLastValue( "alpha", "1" );
		e0.addLastValue( "beta", "2" );
		e0.addLastValue( "alpha", "3" );
		Element e1 = new FlexiElement( "foo" );
		e1.addLastValue( "alpha", "1" );
		e1.addLastValue( "alpha", "3" );
		e1.addLastValue( "beta", "2" );
		assertTrue( e0.equals(  e1  ) );
		Element e2 = new FlexiElement( "foo" );
		e2.addLastValue( "alpha", "3" );
		e2.addLastValue( "alpha", "1" );
		e2.addLastValue( "beta", "2" );
		assertFalse( e0.equals(  e2  ) );
		assertFalse( e1.equals(  e2  ) );
	}
	
	@Test
	public void equals_Members() {
		Element e0 = new FlexiElement( "foo" );
		e0.addLastChild( "alpha", new FlexiElement( "1" ) );
		e0.addLastChild( "beta", new FlexiElement( "2" ) );
		e0.addLastChild( "alpha",new FlexiElement(  "3" ) );
		Element e1 = new FlexiElement( "foo" );
		e1.addLastChild( "alpha", new FlexiElement( "1" ) );
		e1.addLastChild( "alpha", new FlexiElement( "3" ) );
		e1.addLastChild( "beta", new FlexiElement( "2" ) );
		assertTrue( e0.equals(  e1  ) );
		Element e2 = new FlexiElement( "foo" );
		e2.addLastChild( "alpha", new FlexiElement( "3" ) );
		e2.addLastChild( "alpha", new FlexiElement( "1" ) );
		e2.addLastChild( "beta", new FlexiElement( "2" ) );
		assertFalse( e0.equals(  e2  ) );
		assertFalse( e1.equals(  e2  ) );
	}
	
}
