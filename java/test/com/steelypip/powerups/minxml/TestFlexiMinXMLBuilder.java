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

public class TestFlexiMinXMLBuilder {
	
	FlexiMinXMLBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = new FlexiMinXMLBuilder();
	}

	@Test
	public void testBadMake() {
		assertNull( builder.build() );
	}

	@Test
	public void testMakeSimple() {
		builder.startTagOpen( "xxx" );
		builder.startTagClose( "xxx" );
		builder.endTag( "xxx" );
		MinXML x = builder.build();
		assertEquals( "xxx", x.getName() );
		assertEquals( 0, x.size() );
		assertEquals( 0, x.getAttributes().size() );
	}
	
	@Test
	public void testNested() {
		builder.startTagOpen( "xxx" );
		builder.put( "beta", "002" );
		builder.put( "alpha", "001" );
		builder.startTagClose( "xxx" );
		builder.startTagOpen( "yyy" );
		builder.startTagClose( "yyy" );
		builder.endTag( "yyy" );
		builder.startTagOpen( "zzz" );
		builder.startTagClose( "zzz" );
		builder.endTag( "zzz" );
		builder.endTag( "xxx" );
		MinXML x = builder.build();
		assertEquals( 
			"<xxx alpha=\"001\" beta=\"002\"><yyy/><zzz/></xxx>",
			x.toString()
		);
	}

}
