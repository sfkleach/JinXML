package com.steelypip.powerups.jimxml;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.implementation.StdPushParser;

public class TestExamplesFromWiki {
		
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
		Element e = p.readElement();
		assertEquals( 3, e.countChildren() );
		assertEquals( "marker", e.getChild(1).getName() );
		assertTrue( e.getChild( 1 ).getChild( "location" ).isArray() );
		assertEquals( (Double)25.2084, e.getChild( 1 ).getChild( "location" ).getChild().getFloatValue() );
	}

	final static String example2 =
		"<person>\n" +
		"  firstName = \"John\" \n" +
		"  lastName = \"Smith\"\n" +
		"  isAlive = true\n" +
		"  age = 27        // Does the age of a person freeze at their time of death?\n" +
		"  address =       // See how to fix this 'stutter' in the next section.\n" +
		"  <address>\n" +
		"    streetAddress = \"21 2nd Street\"\n" +
		"    city = \"New York\"\n" +
		"    state = \"NY\"\n" +
		"    postalCode = \"10021-3100\"\n" +
		"  </&>\n" +
		"  children = []\n" +
		"  spouse = null  // Can you have multiple spouses?\n" +
		"</&>\n";
	
	@Test
	public void testExample2() {
		StdPushParser p = new StdPushParser( new StringReader( example2 ), false );
		Element e = p.readElement();
		assertEquals( 7, e.countMembers() );
		assertEquals( "person", e.getName() );
		assertEquals( "21 2nd Street", e.getChild( "address" ).getChild("streetAddress").getStringValue() );
	}
	
	final static String example3 = "{ & = <address/> }";

	@Test
	public void testExample3() {
		StdPushParser p = new StdPushParser( new StringReader( example3 ), false );
		Element e = p.readElement();
		assertEquals( 1, e.countMembers() );
		assertEquals( "address", e.getChild( "address" ).getName() );
	}
	
	final static String example4 = 
		"<person>\n" +
		"  firstName = \"John\" \n" +
		"  lastName = \"Smith\"\n" +
		"  isAlive = true\n" +
		"  age = 27        // Does the age of a person freeze at their time of death?\n" +
		"  address = <&>\n" +
		"    streetAddress = \"21 2nd Street\"\n" +
		"    city = \"New York\"\n" +
		"    state = \"NY\"\n" +
		"    postalCode = \"10021-3100\"\n" +
		"  </&>\n" +
		"</&>\n";

	@Test
	public void testExample4() {
		StdPushParser p = new StdPushParser( new StringReader( example4 ), false );
		Element e = p.readElement();
		assertEquals( 5, e.countMembers() );
		assertEquals( "address", e.getChild( "address" ).getName() );
	}	
	
}
