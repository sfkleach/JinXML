package com.steelypip.powerups.jinxml;

import static org.junit.Assert.*;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

import com.steelypip.powerups.jinxml.Event;

public class Test_Event {

	private static final @NonNull String SELECTOR = "selector";
	private static final @NonNull String ELEMENT_NAME = "elementName";

	@Test
	public void fromHandler() {
		Event e = Event.fromHandle( h-> h.startTagEvent( SELECTOR, ELEMENT_NAME ) ).findFirst().get();
		assertTrue( e instanceof Event.StartTagEvent );
		Event.StartTagEvent ste = (Event.StartTagEvent)e;
		assertEquals( ELEMENT_NAME, ste.getName() );
		assertEquals( SELECTOR, ste.getSelector() );
	}

}
