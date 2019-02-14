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
package com.steelypip.powerups.charrepeater;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

public class TestCharRepeater {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEmpty() {
		CharRepeater c = new ReaderCharRepeater( new StringReader( "" ) );
		assertFalse( c.hasNextChar() );
	}
	
	@Test
	public void testPush() {
		CharRepeater c = new ReaderCharRepeater( new StringReader( "" ) );
		c.pushChar( 'x' );
		assertTrue( c.hasNextChar() );
		assertEquals( 'x', c.nextChar() );
		assertFalse( c.hasNextChar() );
	}

	@Test
	public void testNext() {
		CharRepeater c = new ReaderCharRepeater( new StringReader( "abc" ) );
		assertEquals( 'a', c.nextChar() );
		assertEquals( 'b', c.nextChar() );
		assertEquals( 'c', c.nextChar() );
		assertFalse( c.hasNextChar() );
	}
	
	@Test
	public void testPeek() {
		CharRepeater c = new ReaderCharRepeater( new StringReader( "abc" ) );
		assertEquals( 'a', c.peekChar() );
		assertEquals( 'a', c.peekChar() );
		c.skipChar();
		assertEquals( 'b', c.peekChar() );
		assertTrue( c.hasNextChar() );
	}
	
	

}
