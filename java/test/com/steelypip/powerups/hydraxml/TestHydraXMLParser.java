package com.steelypip.powerups.hydraxml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.alert.Alert;

public class TestHydraXMLParser {

	@Before
	public void setUp() throws Exception {
	}


	private void checkSimpleElement( HydraXMLParser p ) {
		HydraXML item = p.readElement();
		assertNotNull( item );
		assertEquals( "foo", item.getInternedName() );
		assertTrue( item.hasNoAttributes() );
		assertTrue( item.hasNoLinks() );
		HydraXML end = p.readElement();
		assertNull( end );		
	}
	
	@Test
	public void minimalElement() {
		for ( String input : new String[] { "<foo></foo>", " < foo > </ foo > " } ) {
			StringReader rep = new StringReader( input );
			checkSimpleElement( new HydraXMLParser( rep ) );
		}
	}

	@Test
	public void minimalStandaloneElement() {
		StringReader rep = new StringReader( "<foo/>" );
		checkSimpleElement( new HydraXMLParser( rep ) );
	}

	@Test
	public void multipleElements() {
		StringReader rep = new StringReader( "<foo/><bar/>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item1 = p.readElement();
		assertTrue( item1 instanceof HydraXML );
		HydraXML item2 = p.readElement();
		assertTrue( item2 instanceof HydraXML );
		HydraXML end = p.readElement();
		assertNull( end );		
	}
	

	@Test
	public void attributeOnEndTag() {
		StringReader rep = new StringReader( "<foo></foo bar='88'>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
		HydraXML end = p.readElement();
		assertNull( end );		
	}
	
	@Test
	public void endTag() {
		StringReader rep = new StringReader( "<foo></>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item1 = p.readElement();
		assertTrue( item1 instanceof HydraXML );		
		HydraXML end = p.readElement();
		assertNull( end );		
	}
	

	@Test
	public void oneAttribute() {
		StringReader rep = new StringReader( "<foo bar='99'/>" );
		HydraXMLParser p= new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertNotNull( item );
		assertEquals( "foo", item.getInternedName() );
		assertSame( 1, item.sizeAttributes() );
		assertTrue( item.hasAttribute( "bar", "99" ) );
		assertTrue( item.hasNoLinks() );
		HydraXML end = p.readElement();
		assertNull( end );		
	}
	
	@Test( expected=Exception.class )
	public void repeatedBadAttribute() {
		StringReader rep = new StringReader( "<foo bar='99' bar='88'/>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
	}

	@Test
	public void repeatedOKAttribute() {
		StringReader rep = new StringReader( "<foo bar='99' bar+='88'/>" );
		HydraXMLParser p= new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertSame( "foo", item.getInternedName() );
		Set< String > key_set = item.keysToSet();
		assertEquals( 1, key_set.size() );
		assertTrue( key_set.contains( "bar" ) );
	}


	@Test
	public void nestedElements() {
		StringReader rep = new StringReader( "<bar><foo/></bar>" );
		HydraXMLParser p= new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertNotNull( item );
		assertEquals( "bar", item.getInternedName() );
		assertSame( 0, item.sizeAttributes() );
		assertSame( 1, item.sizeLinks() );
		HydraXML child = item.getChild();
		assertEquals( "foo", child.getName() );
		HydraXML end = p.readElement();
		assertNull( end );		
	}

	@Test
	public void linkedElements() {
		StringReader rep = new StringReader( "<bar>gort:<foo/><oof/></bar>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertNotNull( item );
		assertEquals( "bar", item.getInternedName() );
		assertSame( 0, item.sizeAttributes() );
		assertSame( 2, item.sizeLinks() );
		HydraXML child = item.getChild();
		assertEquals( "oof", child.getName() );
		HydraXML end = p.readElement();
		assertNull( end );		
	}
	
	@Test( expected = Exception.class )
	public void badDuplicateLinks() {
		StringReader rep = new StringReader( "<bar>gort:<foo/>gort:<oof/></bar>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
	}

	@Test
	public void okDuplicateLinks() {
		StringReader rep = new StringReader( "<bar>gort:<foo/>gort+:<oof/></bar>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertNotNull( item );
		assertEquals( "bar", item.getInternedName() );
		assertSame( 0, item.sizeAttributes() );
		assertSame( 2, item.sizeLinks() );
		assertSame( 2, item.sizeChildren( "gort" ) );
		HydraXML child0 = item.getChild( "gort" );
		assertEquals( "foo", child0.getName() );
		HydraXML child1 = item.getChild( "gort", 1 );
		assertEquals( "oof", child1.getName() );
		HydraXML end = p.readElement();
		assertNull( end );		
	}


	@Test( expected = Alert.class )
	public void badCommasForJSON() {
		StringReader rep = new StringReader( "<foo, bar='x'/>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
	}
	
	@Test
	public void numericEntitiesEscape() {
		StringReader rep = new StringReader( "<foo bar='&#32;'/>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertEquals( " ", item.getValue( "bar" ) );
	}
	
	@Test( expected = Alert.class )
	public void badNumericEntitiesEscape() {
		StringReader rep = new StringReader( "<foo bar='&#32zzz;'/>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
	}
	
	@Test( expected = Alert.class )
	public void badField() {
		StringReader rep = new StringReader( "<foo>wow$<bar/></foo>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
	}
	
	@Test( expected = Alert.class )
	public void badMultipleFields() {
		StringReader rep = new StringReader( "<foo>wow+:<bar/>wow+<gort/></foo>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
	}
	
	@Test( expected = Alert.class )
	public void badMultipleAttributes() {
		StringReader rep = new StringReader( "<foo wow+'y' />" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
	}
	
	@Test
	public void elementsWithStringsForNames() {
		for ( String input : new String[] { "<\"x y\"/>", "<'x y'/>" } ) {
			StringReader rep = new StringReader( input );
			HydraXMLParser p = new HydraXMLParser( rep );
			HydraXML item = p.readElement();
			assertSame( "x y", item.getInternedName() );
			assertNull( p.readElement() );
		}		
	}
	
	@Test
	public void doubleQuotes() {
		final String s = "<foo data:\"This has a newline\\n\"/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertEquals( "This has a newline\n", item.getValue( "data" ) );
		assertNull( p.readElement() );
	}
	
	@Test
	public void singleQuotes() {
		final String s = "<foo data:'This has an ampersand \\&amp;'/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertEquals( "This has an ampersand &", item.getValue( "data" ) );
		assertNull( p.readElement() );
	}
	
	@Test
	public void attributeEscapes1() {
		final String s = "<foo bar='&amp;'/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertEquals( "&", item.getValue( "bar" ) );
		assertNull( p.readElement() );
	}
	
	@Test
	public void attributeEscapes2() {
		final String s = "<foo bar:'&amp;'/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertEquals( "&amp;", item.getValue( "bar" ) );
		assertNull( p.readElement() );
	}
	
	@Test
	public void testEOLComment() {
		final String s = "// This is a test\n<foo/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertTrue( item.hasName( "foo" ) );
		assertNull( p.readElement() );
	}
	
	@Test
	public void testLongComment() {
		final String s = "/* This is a test */<foo/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertTrue( item.hasName( "foo" ) );
		assertNull( p.readElement() );
	}
	
	@Test
	public void testXMLStyleComment() {
		final String s = "<!-- This is a test --><foo/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertTrue( item.hasName( "foo" ) );
		assertNull( p.readElement() );
	}
	
	@Test
	public void testShebangStyleComment() {
		final String s = "#! This is a test\n <foo/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertTrue( item.hasName( "foo" ) );
		assertNull( p.readElement() );
	}
	
	@Test
	public void testIterator() {
		final String s = "<foo/><foo/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		int n = 0;
		for ( HydraXML x : p ) {
			n += 1;
			assertTrue( x.hasName( "foo" ) );
		}
		assertSame( 2, n );
	}
	
	@Test
	public void discardProlog() {
		final String s = "<! splatter ratter ><foo/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();		
		assertTrue( item.hasName( "foo" ) );
	}

	@Test
	public void testStringEscapeCharacters() {
		final String s = "<foo alpha:'\\'\\r\\t\\f\\b\\&eacute;\"\\a'/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();		
		assertEquals( "'\r\t\f\bé\"a", item.getValue( "alpha" ) );
	}

}
