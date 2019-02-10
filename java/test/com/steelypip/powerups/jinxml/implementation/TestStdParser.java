package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Event;

public class TestStdParser {
	
	StdPushParser parser( String s ) {
		return new StdPushParser( new StringReader( s ) );
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
	}
	
	

}
