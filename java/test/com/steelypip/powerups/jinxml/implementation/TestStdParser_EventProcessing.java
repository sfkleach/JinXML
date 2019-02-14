package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Event;

public class TestStdParser_EventProcessing {

	StdPushParser parser( String s ) {
		return new StdPushParser( new StringReader( s ), false );
	}
	
	@Test
	public void readEvent_Int() {
		StdPushParser p = this.parser( "99" );
		Event e = p.readEvent();
		assertTrue( e instanceof Event.IntEvent );
		assertNull( p.readEvent() );
	}
	
	@Test
	public void readEvent_IntExpanded() {
		StdPushParser p = new StdPushParser( new StringReader( "88" ), true );
		Event start = p.readEvent();
		assertTrue( start instanceof Event.StartTagEvent );
		Event addattr = p.readEvent();
		assertTrue( addattr instanceof Event.AttributeEvent );
		Event end = p.readEvent();
		assertTrue( end instanceof Event.EndTagEvent );
		assertNull( p.readEvent() );
	}
	
	@Test
	public void readEvent_XMLStyle_EmptyString() {
		StdPushParser p = this.parser( "''" );
		//TODO
	}

	@Test
	public void readEvent_XMLStyle_NonEmptyString_WithEscapes() {
		StdPushParser p = this.parser( "''" );
		//TODO
	}

	@Test( expected=RuntimeException.class )
	public void test_MisMatchedTags() {
		String text = "<foo></bar>";
		StdPushParser p = this.parser( text );
		Event e0 = p.readEvent();
		Event e1 = p.readEvent();
	}
	
}
