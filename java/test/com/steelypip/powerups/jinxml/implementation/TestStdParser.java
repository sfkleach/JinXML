package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Event;

public class TestStdParser {
	
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
	public void readElement_Int() {
		StdPushParser p = this.parser( "99" );
		Element e = p.readElement();
		assertNotNull( e );
		assertTrue( e.isIntValue() );
		assertEquals( (Long)99L, (Long)e.getIntValue() );
	}
	
	@Test
	public void readEvent_IntExpanded() {
		StdPushParser p = new StdPushParser( new StringReader( "88" ), true );
		Event start = p.readEvent();
		Event addattr = p.readEvent();
		Event end = p.readEvent();
		assertNull( p.readEvent() );
	}

}
