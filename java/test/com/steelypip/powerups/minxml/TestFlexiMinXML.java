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

import org.junit.Before;
import org.junit.Test;

public class TestFlexiMinXML {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBasic() {
		FlexiMinXML x = new FlexiMinXML( "xxx" );
		assertEquals( "xxx", x.getName() );
		assertEquals( 0, x.getAttributes().size() );
		x.putAttribute( "alpha", "001" );
		assertEquals( 1, x.getAttributes().size() );
		assertEquals( "001", x.getAttribute( "alpha" ) );
		assertTrue( x.isEmpty() );
		assertEquals( 0, x.size() );
		x.add( new FlexiMinXML( "yyy" ) );
		x.add( new FlexiMinXML( "zzz" ) );
		assertEquals( 2, x.size() );
		assertEquals( "yyy", x.get( 0 ).getName() );
	}
	
	@Test
	public void testPrintingEmpty() {
		FlexiMinXML x = new FlexiMinXML( "xxx" );
		assertEquals( "<xxx/>", x.toString() );
	}

	@Test
	public void testPrintingAttributes() {
		FlexiMinXML x = new FlexiMinXML( "xxx" );
		x.putAttribute( "alpha", "001" );
		x.putAttribute( "beta", "002" );
		assertEquals( "<xxx alpha=\"001\" beta=\"002\"/>", x.toString() );
	}

	@Test
	public void testPrintingNonAscii() {
		FlexiMinXML x = new FlexiMinXML( "xxx" );
		x.putAttribute( "nonascii", "\u00FF" );
		assertEquals( "<xxx nonascii=\"&#255;\"/>", x.toString() );
	}

	@Test
	public void testPrintingSpecialCharacters() {
		FlexiMinXML x = new FlexiMinXML( "xxx" );
		x.putAttribute( "special", "<>&'\"" );
		assertEquals( "<xxx special=\"&lt;&gt;&amp;&apos;&quot;\"/>", x.toString() );
	}

	@Test
	public void testPrintingChildren() {
		FlexiMinXML x = new FlexiMinXML( "xxx" );
		x.add( new FlexiMinXML( "yyy" ) );
		x.add( new FlexiMinXML( "zzz" ) );
		assertEquals( "<xxx><yyy/><zzz/></xxx>", x.toString() );
	}

}
