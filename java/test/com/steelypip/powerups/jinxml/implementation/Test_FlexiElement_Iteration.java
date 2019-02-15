package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Attribute;
import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Member;

public class Test_FlexiElement_Iteration {

	/**************************************************************************
	* Attributes
	**************************************************************************/

	public void attributes_OnEmpty( boolean freeze ) {
		Element e = new FlexiElement( "test" );
		e = freeze ? e.deepFreeze() : e;
		int count = 0;
		for ( @SuppressWarnings("unused") Attribute attr : e.attributes() ) {
			count += 1;
		}
		assertEquals( 0, count );
	}	
	
	@Test
	public void attributes_OnEmptyMutable() {
		this.attributes_OnEmpty( false );
	}
	
	@Test
	public void attributes_OnEmptyFrozen() {
		this.attributes_OnEmpty( true );
	}
	
	public void attributes_OnSingleAttribute( boolean freeze ) {
		Element e = new FlexiElement( "test" );
		e.addLastValue( "foo", "bar" );
		e = freeze ? e.deepFreeze() : e;
		int count = 0;
		for ( @SuppressWarnings("unused") Attribute attr : e.attributes() ) {
			count += 1;
		}
		assertEquals( 1, count );
	}
	
	@Test
	public void attributes_OnSingleAttributeMutable() {
		attributes_OnSingleAttribute( false );
	}
	
	@Test
	public void attributes_OnSingleAttributeFrozen() {
		attributes_OnSingleAttribute( true );
	}
	
	public void attributes_OnMultipleAttribute( boolean freeze ) {
		Element e = new FlexiElement( "test" );
		e.addLastValue( "fooA", "bar0" );
		e.addLastValue( "fooA", "bar1" );
		e.addLastValue( "fooB", "bar0" );
		e.addLastValue( "fooB", "bar1" );
		e = freeze ? e.deepFreeze() : e;
		int count = 0;
		for ( @SuppressWarnings("unused") Attribute attr : e.attributes() ) {
			count += 1;
		}
		assertEquals( 4, count );
	}

	@Test
	public void attributes_OnMultipleAttributeMutable() {
		attributes_OnMultipleAttribute( false );
	}

	@Test
	public void attributes_OnMultipleAttributeFrozen() {
		attributes_OnMultipleAttribute( true );
	}
	
	/**************************************************************************
	* Members
	**************************************************************************/

	public void members_OnEmpty( boolean freeze ) {
		Element e = new FlexiElement( "test" );
		e = freeze ? e.deepFreeze() : e;
		int count = 0;
		for ( @SuppressWarnings("unused") Member attr : e.members() ) {
			count += 1;
		}
		assertEquals( 0, count );
	}	
	
	@Test
	public void members_OnEmptyMutable() {
		this.members_OnEmpty( false );
	}
	
	@Test
	public void members_OnEmptyFrozen() {
		this.members_OnEmpty( true );
	}
	
	public void members_OnSingleMember( boolean freeze ) {
		Element e = new FlexiElement( "test" );
		e.addLastChild( "foo", new FlexiElement( "bar" ) );
		e = freeze ? e.deepFreeze() : e;
		int count = 0;
		for ( @SuppressWarnings("unused") Member attr : e.members() ) {
			count += 1;
		}
		assertEquals( 1, count );
	}
	
	@Test
	public void members_OnSingleMemberMutable() {
		members_OnSingleMember( false );
	}
	
	@Test
	public void members_OnSingleMemberFrozen() {
		members_OnSingleMember( true );
	}
	
	public void members_OnMultipleMember( boolean freeze ) {
		Element e = new FlexiElement( "test" );
		e.addLastChild( "fooA",  new FlexiElement( "bar0" ) );
		e.addLastChild( "fooA",  new FlexiElement( "bar1" ) );
		e.addLastChild( "fooB",  new FlexiElement( "bar0" ) );
		e.addLastChild( "fooB",  new FlexiElement( "bar1" ) );
		e = freeze ? e.deepFreeze() : e;
		int count = 0;
		for ( @SuppressWarnings("unused") Member attr : e.members() ) {
			count += 1;
		}
		assertEquals( 4, count );
	}

	@Test
	public void members_OnMultipleMemberMutable() {
		members_OnMultipleMember( false );
	}

	@Test
	public void members_OnMultipleMemberFrozen() {
		members_OnMultipleMember( true );
	}
	
}
