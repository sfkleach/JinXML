package com.steelypip.powerups.hydraxml;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;


public class TestHydraXMLWriter {

	@Before
	public void setUp() throws Exception {
	}
	
	private static HydraXML parse( String string ) {
		StringReader rep = new StringReader( string );
		HydraXMLParser p = new HydraXMLParser( rep );
		return p.readElement();
	}
	
	private static String render( final HydraXML fusion, String... options ) {
		final StringWriter w = new StringWriter();
		fusion.print( w, options );
		return w.toString();		
	}

	@Test
	public void testName() {
		HydraXML alpha = parse( "<alpha/>" );
		assertEquals( "<alpha/>", render( alpha ) );
	}

	@Test
	public void testAttributes() {
		HydraXML alpha = parse( "<alpha left='right'/>" );
		assertEquals( "<alpha left=\"right\"/>", render( alpha ) );
	}

	@Test
	public void testAttributeWithWideChar() {
		HydraXML alpha = parse( "<alpha left='é'/>" );
		assertEquals( "<alpha left=\"&#233;\"/>", render( alpha ) );
	}

	@Test
	public void testSeveralAttributes() {
		HydraXML alpha = parse( "<alpha left1='right1' left2='right2'/>" );
		assertEquals( "<alpha left1=\"right1\" left2=\"right2\"/>", render( alpha ) );
	}

	@Test
	public void testMultiValuedAttribute() {
		HydraXML alpha = parse( "<alpha left='right1' left+='right2'/>" );
		assertEquals( "<alpha left=\"right1\" left+=\"right2\"/>", render( alpha ) );
	}
	
	@Test( expected=Exception.class )
	public void badMultiValuedAttribute() {
		HydraXML alpha = parse( "<alpha left='right1' left='right2'/>" );
//		assertEquals( "<alpha left=\"right1\" left+=\"right2\"/>", render( alpha ) );
	}
	
	@Test
	public void testChild() {
		HydraXML alpha = parse( "<alpha><beta/></alpha>" );
		assertEquals( "<alpha><beta/></alpha>", render( alpha ) );
	}
	
	@Test
	public void testChildren() {
		HydraXML alpha = parse( "<alpha><beta b.left=\"b.right\"/><gamma/></alpha>" );
		assertEquals( "<alpha><beta b.left=\"b.right\"/><gamma/></alpha>", render( alpha ) );
	}

	@Test
	public void testFieldChild() {
		HydraXML alpha = parse( "<alpha>child:<beta b.left=\"b.right\"/></alpha>" );
		assertEquals( "<alpha>child:<beta b.left=\"b.right\"/></alpha>", render( alpha ) );
	}

	@Test
	public void testFieldChildren() {
		HydraXML alpha = parse( "<alpha>alt.child:<delta/>child:<beta/>child+:<gamma/></alpha>" );
		assertEquals( "<alpha>alt.child:<delta/>child:<beta/>child+:<gamma/></alpha>", render( alpha ) );
	}
	
	@Test
	public void testEscapes() {
		assertEquals( "<alpha data=\"&quot;\"/>", render( parse( "<alpha data='&quot;'/>" ) ) );
		assertEquals( "<alpha data=\"&apos;\"/>", render( parse( "<alpha data='&apos;'/>" ) ) );
		assertEquals( "<alpha data=\"&lt;\"/>", render( parse( "<alpha data='&lt;'/>" ) ) );
		assertEquals( "<alpha data=\"&gt;\"/>", render( parse( "<alpha data='&gt;'/>" ) ) );
		assertEquals( "<alpha data=\"&amp;\"/>", render( parse( "<alpha data='&amp;'/>" ) ) );
	}
	

}
