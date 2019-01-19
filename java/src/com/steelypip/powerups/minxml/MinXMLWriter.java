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

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

import com.steelypip.powerups.common.Indenter;
import com.steelypip.powerups.common.IndenterFactory;
import com.steelypip.powerups.common.NullIndenter;

/**
 * Prints out a MinXML object to a {@link java.io.PrintWriter}. It can
 * be a simple-all-on-one-line or an elaborate pretty-printer depending
 * on the Indenter that is supplied.
 */
public class MinXMLWriter {

	final PrintWriter pw;
	final Indenter indenter;
	
	public MinXMLWriter( PrintWriter pw, IndenterFactory indenter_factory ) {
		this.pw = pw;
		this.indenter = indenter_factory.newIndenter( pw );
	}
	
	private static NullIndenter.Factory FACTORY = new NullIndenter.Factory();
	
	public MinXMLWriter( PrintWriter pw ) {
		this( pw, FACTORY );
	}
	
	public MinXMLWriter( Writer w ) {
		this( new PrintWriter( w ) );
	}
	
	public void print( MinXML x ) {
		this.indenter.indent();
		pw.print( "<" );
		pw.print( x.getName() );
		for ( Map.Entry< String, String > key_value : x.entries() ) {
			pw.print( " " );
			pw.print( key_value.getKey() );
			pw.print( "=\"" );
			final String v = key_value.getValue();
			for ( int n = 0; n < v.length(); n++ ) {
				final char ch = v.charAt( n );
				if ( ch == '"' ) {
					pw.print( "&quot;" );
				} else if ( ch == '\'' ) {
					pw.print(  "&apos;" );
				} else if ( ch == '<' ) {
					pw.print( "&lt;" );
				} else if ( ch == '>' ) {
					pw.print( "&gt;" );
				} else if ( ch == '&' ) {
					pw.print( "&amp;" );
				} else if ( ' ' <= ch && ch <= '~' ) {
					pw.print( ch );
				} else {
					pw.print( "&#" );
					pw.print( (int)ch );
					pw.print( ';' );
				}
			}
			pw.print( "\"" );
		}
		if ( x.isEmpty() ) {
			pw.print( "/>" );
			indenter.newline();
		} else {
			pw.print( ">" );
			indenter.newline();
			indenter.tab();
			for ( MinXML y : x ) {
				this.print( y );
			}
			indenter.untab();
			indenter.indent();
			pw.format( "</%s>", x.getName() );
			indenter.newline();
		}
	}

}
