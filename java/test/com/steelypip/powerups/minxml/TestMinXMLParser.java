/**
 * Copyright Stephen Leach, 2014
 * This file is part of the MinXML for Java library.
 * 
 * MinXML for Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MinXML for Java.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */
package com.steelypip.powerups.minxml;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import com.steelypip.powerups.alert.Alert;

public class TestMinXMLParser {


	@Test
	public void testEmpty() {
		assertNull( new MinXMLParser( new StringReader( "" ) ).readElement() );
	}

	@Test
	public void testEmptyAsIterable() {
		int n = 0;
		for ( @SuppressWarnings("unused") MinXML m : new MinXMLParser( new StringReader( "" ) ) ) {
			n += 1;
		}
		assertEquals( 0, n );
	}
	
	@Test
	public void testNonEmpty() {
		MinXML m = new MinXMLParser( new StringReader( "<foo/>" ) ).readElement();
		assertNotNull( m );
		assertEquals( "foo", m.getName() );
		assertTrue( m.getAttributes().isEmpty() );
		assertTrue( m.isEmpty() );
	}
	
	@Test
	public void testAttributesEitherQuote() {
		MinXML m = new MinXMLParser( new StringReader( "<foo left='right' less=\"more\"/>" ) ).readElement();
		assertNotNull( m );
		assertEquals( "right", m.getAttribute( "left" ) );
		assertEquals( "more", m.getAttribute( "less" ) );
		assertTrue( m.isEmpty() );
	}
	
	@Test
	public void testNested() {
		MinXML m = new MinXMLParser( new StringReader( "<outer><foo left='right' less=\"more\"></foo></outer>" ) ).readElement();
		assertNotNull( m );
		assertEquals( 1, m.size() );
		assertEquals( "foo", m.get( 0 ).getName() );
		assertTrue( m.get( 0 ).isEmpty() );
	}
	
	@Test
	public void testComment() {
		MinXML m = new MinXMLParser( new StringReader( "<outer><!-- this is a comment --><foo left='right' less=\"more\"></foo></outer>" ) ).readElement();
		assertNotNull( m );
		assertEquals( 1, m.size() );
		assertEquals( "foo", m.get( 0 ).getName() );
		assertTrue( m.get( 0 ).isEmpty() );
	}
	
	@Test
	public void testCommentWithEmbeddedSigns() {
		MinXML m = new MinXMLParser( new StringReader( "<outer><!-- <- this -> is -> a <- comment <! --><foo left='right' less=\"more\"></foo></outer>" ) ).readElement();
		assertNotNull( m );
		assertEquals( 1, m.size() );
		assertEquals( "foo", m.get( 0 ).getName() );
		assertTrue( m.get( 0 ).isEmpty() );
	}
	
	@Test( expected=Alert.class )
	public void testBadComment() {
		new MinXMLParser( new StringReader( "<outer><!-- <- this --> is -> a <- bad comment <! --><foo left='right' less=\"more\"></foo></outer>" ) ).readElement();
	}
	
	@Test
	public void testPrologElison() {
		MinXML m = new MinXMLParser( new StringReader( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><xxx/>" ) ).readElement();
		assertNotNull( m );
		assertEquals( "xxx", m.getName() );
		assertTrue( m.isEmpty() );
	}
	
	@Test( expected=Alert.class ) 
	public void testForbiddenLessThan() {
		new MinXMLParser( new StringReader( "<xxx a='<'/>" ) ).readElement();
	}
	
	@Test( expected=Exception.class ) 
	public void testForbiddenAmpersand() {
		new MinXMLParser( new StringReader( "<xxx a='&'/>" ) ).readElement();
	}
	
	@Test( expected=Exception.class ) 
	public void testForbiddenDoubleInDouble() {
		new MinXMLParser( new StringReader( "<xxx a=\"\"\"/>" ) ).readElement();
	}
	
	@Test( expected=Exception.class ) 
	public void testForbiddenSingleInSingle() {
		new MinXMLParser( new StringReader( "<xxx a='\''/>" ) ).readElement();
	}
	
	@Test
	public void testAllowedGreaterThan() {
		assertNotNull( new MinXMLParser( new StringReader( "<xxx a='>'/>" ) ).readElement() );
	}
	
	@Test
	public void testAllowedSingleInDouble() {
		assertNotNull( new MinXMLParser( new StringReader( "<xxx a=\"'\"/>" ) ).readElement() );
	}
	
	@Test
	public void testAllowedDoubleInSingle() {
		assertNotNull( new MinXMLParser( new StringReader( "<xxx a='\"'/>" ) ).readElement() );		
	}
	
	@Test
	public void testAsIterable() {
		int n = 0;
		for ( @SuppressWarnings("unused") MinXML m :  new MinXMLParser( new StringReader( "<xxx/><yyy/><!-- woot --><zzz/>" ) ) ) {
			n += 1;
		}
		assertEquals( 3, n );
	}
	
	@Test
	public void testEscape() {
		MinXML m = new MinXMLParser( new StringReader( "<foo bar='&lt;&gt;&amp;&quot;&apos;'/>" ) ).readElement();
		assertEquals( "<>&\"'", m.getAttribute( "bar" ) );
	}
			
	@Test
	public void testPrintNumericEntities() {
		MinXML m = new MinXMLParser( new StringReader( "<foo bar='&#12;'/>" ) ).readElement();
		assertEquals( "<foo bar=\"&#12;\"/>", m.toString() );
	}
			
	@Test( expected=Exception.class )
	public void testUnfinished() {
		new MinXMLParser( new StringReader( "<foo>" ) ).readElement();
	}
	
	@Test
	public void testNestedDiscard() {
		MinXML m = (
			new MinXMLParser( new StringReader( 
				"<?xml version=\"1.0\" standalone=\"yes\" ?>\n" +
				"<!DOCTYPE author [\n" +
				"  <!ELEMENT author (#PCDATA)>\n" +
				"  <!ENTITY js \"Jo Smith\">\n" +
				"]>\n" +
				"<author name='Jo Smith'></author>"
			) ).readElement()
		);
		assertEquals( "author", m.getName() );
	}
			
}
