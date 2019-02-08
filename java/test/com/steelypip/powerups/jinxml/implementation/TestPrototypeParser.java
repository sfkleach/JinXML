package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Event;

public class TestPrototypeParser {
	
	private PrototypeParser< Event > makeParser( String input ) {
		PrototypeParser< Event > pp = new PrototypeParser< Event >( new StringReader( input ), new ConstructingEventHandler() );
		pp.capturing = true;
		return pp;
	}

	@Test
	public void test() {
		PrototypeParser< Event > pp = makeParser( "99" );
		pp.readNextTag();
		Event e0 = pp.buffer.removeFirst();
		assertTrue( pp.buffer.isEmpty() );
		assertTrue( e0 instanceof Event.IntEvent );
		assertEquals( "99", ((Event.IntEvent)e0).getValue() );
	}
	
	

}
