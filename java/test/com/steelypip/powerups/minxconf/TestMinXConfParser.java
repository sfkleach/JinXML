package com.steelypip.powerups.minxconf;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import com.steelypip.powerups.minxml.MinXML;

public class TestMinXConfParser {

	@Test
	public void testReadEmpty() {
		MinXML m = new MinXConfParser( new StringReader( "" ) ).read();
		assertEquals( "<object/>", m.toString() );
	}
	
	@Test
	public void testReadBindings1() {
		MinXML m = new MinXConfParser( new StringReader( "foo:1, bar:2" ) ).read();
		assertEquals( "<object><constant field=\"foo\" type=\"integer\" value=\"1\"/><constant field=\"bar\" type=\"integer\" value=\"2\"/></object>", m.toString() );
	}
	
	@Test
	public void testReadBindings2() {
		MinXML m = new MinXConfParser( new StringReader( "foo:1\nbar:2" ) ).read();
		assertEquals( "<object><constant field=\"foo\" type=\"integer\" value=\"1\"/><constant field=\"bar\" type=\"integer\" value=\"2\"/></object>", m.toString() );
	}
	
	/* Added purely to check out the tutorial text.
	@Test
	public void fake() throws FileNotFoundException {
		MinXML m = new MinXConfParser( new FileReader( new File( "data.minxconf" ) ) ).read();
		m.prettyPrint( new PrintWriter( System.out ) );
		//assertEquals( "<object><constant field=\"foo\" type=\"integer\" value=\"1\"/><constant field=\"bar\" type=\"integer\" value=\"2\"/></object>", m.toString() );
	}*/
	
}
