package com.steelypip.powerups.jinxml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.steelypip.powerups.jinxml.stdparse.StdPushParser;

public class TestToEventStream {
	
	final static String example1 = 
			"<markers>\n" + 
			"    <marker>\n" +
			"        /* When a field has multiple values it's natural to use parentheses */\n" +
			"        name:       \"Rixos The Palm Dubai\",\n" +
			"        location:   [ 25.1212, 55.1535 ]\n" +
			"    </marker>\n" +
			"    <marker>\n" +
			"        // Commas can be omitted or swapped for semi-colons.\n" + 
			"        name:       'Shangri-La Hotel';\n" +
			"        location:   [ 25.2084 55.2719 ]\n" +
			"    </marker>\n" +
			"    <marker>\n" +
			"        <!-- Trailing commas are allowed. Also single-quotes, as in HTML. -->\n" +
			"        name:       \"Grand Hyatt\";\n" +
			"        location:   [ 25.2285, 55.3273, ]\n" +
			"    </marker>\n" +
			"</markers>\n";

	@Test
	public void testExample1() {
		StdPushParser p = new StdPushParser( new StringReader( example1 ), false );
		final Element element0 = p.readElement();
		final Builder builder = Builder.newBuilder();
		element0.toEventStream().forEach( e -> builder.handleEvent( e ) );
		assertTrue( builder.hasNext() );
		Element element1 = builder.next();
		assertFalse( builder.hasNext() );
		assertEquals( element0, element1 );
	}

	@Test
	public void test_SimpleEventStream() {
		StdPushParser p = new StdPushParser( new StringReader( "<foo/>" ), false );
		final Element element = p.readElement();
		List< Event > events = element.toEventStream().collect( Collectors.toList() );
		assertTrue( events.get( 0 ) instanceof Event.StartTagEvent );
		assertTrue( events.get( 1 ) instanceof Event.EndTagEvent );
	}

}
