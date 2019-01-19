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
package com.steelypip.powerups.hydraxml;

import java.io.PrintWriter;
import java.io.Writer;

import com.steelypip.powerups.common.IndenterFactory;
import com.steelypip.powerups.common.NullIndenter;

/**
 * Prints out a HydraXML object to a {@link java.io.PrintWriter}. It can
 * be a simple-all-on-one-line or an elaborate pretty-printer depending
 * on the Indenter, and look like XML or JSON depending on the Theme that
 * is supplied.
 */
public class HydraXMLWriter extends ThemeableWriter< HydraXML > {

	public HydraXMLWriter( PrintWriter pw, IndenterFactory indenter_factory, Theme< HydraXML > theme ) {
		super( pw, indenter_factory, theme );
	}
	
	private static NullIndenter.Factory DEFAULT_INDENT_FACTORY = new NullIndenter.Factory();
	private static Theme< HydraXML > DEFAULT_THEME = new HydraXmlElementTheme();
	
	public HydraXMLWriter( PrintWriter pw ) {
		this( pw, DEFAULT_INDENT_FACTORY, DEFAULT_THEME );
	}
	
	public HydraXMLWriter( PrintWriter pw, IndenterFactory indenter_factory ) {
		super( pw, indenter_factory, DEFAULT_THEME );
	}
	
	public HydraXMLWriter( Writer w ) {
		this( new PrintWriter( w ) );
	}

}
