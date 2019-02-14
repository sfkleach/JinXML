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
package com.steelypip.powerups.common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.common.NullIndenter.Factory;
import com.steelypip.powerups.io.StringPrintWriter;

public class TestNullIndenter {
	
	Factory factory;
	StringPrintWriter pw;
	NullIndenter indenter;

	@Before
	public void setUp() throws Exception {
		factory = new NullIndenter.Factory();
		pw = new StringPrintWriter();
		indenter = factory.newIndenter( pw );
	}

	@Test
	public void testNullIndenter() {
		indenter.tab();
		indenter.indent();
		indenter.newline();
		indenter.untab();
		assertEquals( "", pw.toString() );
	}

}
