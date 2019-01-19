package com.steelypip.powerups.fusion.io;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.fusion.Fusion;

public class TestFusionWriter {

	@Before
	public void setUp() throws Exception {
	}
	
	private static Fusion parse( String string ) {
		StringReader rep = new StringReader( string );
		FusionParser p = new FusionParser( rep );
		return p.readElement();
	}
	
	private static String render( final Fusion fusion, String... options ) {
		final StringWriter w = new StringWriter();
		fusion.print( w, options );
		return w.toString();		
	}

	@Test
	public void testName() {
		Fusion alpha = parse( "<alpha/>" );
		assertEquals( "<alpha/>", render( alpha ) );
	}

	@Test
	public void testAttributes() {
		Fusion alpha = parse( "<alpha left='right'/>" );
		assertEquals( "<alpha left=\"right\"/>", render( alpha ) );
	}

	@Test
	public void testAttributeWithWideChar() {
		Fusion alpha = parse( "<alpha left='é'/>" );
		assertEquals( "<alpha left=\"&#233;\"/>", render( alpha ) );
	}

	@Test
	public void testSeveralAttributes() {
		Fusion alpha = parse( "<alpha left1='right1' left2='right2'/>" );
		assertEquals( "<alpha left1=\"right1\" left2=\"right2\"/>", render( alpha ) );
	}

	@Test
	public void testMultiValuedAttribute() {
		Fusion alpha = parse( "<alpha left='right1' left+='right2'/>" );
		assertEquals( "<alpha left=\"right1\" left+=\"right2\"/>", render( alpha ) );
	}
	
	@Test( expected=Exception.class )
	public void badMultiValuedAttribute() {
		Fusion alpha = parse( "<alpha left='right1' left='right2'/>" );
		assertEquals( "<alpha left=\"right1\" left+=\"right2\"/>", render( alpha ) );
	}
	
	@Test
	public void testChild() {
		Fusion alpha = parse( "<alpha><beta/></alpha>" );
		assertEquals( "<alpha><beta/></alpha>", render( alpha ) );
	}
	
	@Test
	public void testChildren() {
		Fusion alpha = parse( "<alpha><beta b.left=\"b.right\"/><gamma/></alpha>" );
		assertEquals( "<alpha><beta b.left=\"b.right\"/><gamma/></alpha>", render( alpha ) );
	}

	@Test
	public void testFieldChild() {
		Fusion alpha = parse( "<alpha>child:<beta b.left=\"b.right\"/></alpha>" );
		assertEquals( "<alpha>child:<beta b.left=\"b.right\"/></alpha>", render( alpha ) );
	}

	@Test
	public void testFieldChildren() {
		Fusion alpha = parse( "<alpha>alt.child:<delta/>child:<beta/>child+:<gamma/></alpha>" );
		assertEquals( "<alpha>alt.child:<delta/>child:<beta/>child+:<gamma/></alpha>", render( alpha ) );
	}

	@Test
	public void testLiteral() {
		Fusion alpha = parse( "99" );
		assertEquals( "<constant type=\"integer\" value=\"99\"/>", render( alpha, "--element" ) );
	}
	

}
