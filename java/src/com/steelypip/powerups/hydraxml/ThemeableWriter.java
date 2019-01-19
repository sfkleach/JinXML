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

import com.steelypip.powerups.common.Indenter;
import com.steelypip.powerups.common.IndenterFactory;
import com.steelypip.powerups.common.NullIndenter;

/**
 * Prints out an object to a {@link java.io.PrintWriter}. It can
 * be a simple-all-on-one-line or an elaborate pretty-printer depending
 * on the Indenter, and look like XML or JSON depending on the Theme that
 * is supplied.
 */
public class ThemeableWriter< T > {

	final PrintWriter pw;
	Indenter indenter;
	Theme< T > theme;
	
	public ThemeableWriter( PrintWriter pw, IndenterFactory indenter_factory, Theme< T > theme ) {
		this.pw = pw;
		this.indenter = indenter_factory.newIndenter( pw );
		this.theme = theme;
	}
	
	private static NullIndenter.Factory DEFAULT_INDENT_FACTORY = new NullIndenter.Factory();
		
	public ThemeableWriter( PrintWriter pw, Theme< T > theme ) {
		this( pw, DEFAULT_INDENT_FACTORY, theme );
	}
	
	public ThemeableWriter( Writer w, Theme< T > theme ) {
		this( new PrintWriter( w ), theme );
	}
	
	PrintWriter getPrintWriter() {
		return this.pw;
	}
	
	public Indenter getIndenter() {
		return indenter;
	}
	
	public Theme< T > getTheme() {
		return theme;
	}

	public void setTheme( Theme< T > theme ) {
		this.theme = theme;
	}

	public void setIndenterFactory( IndenterFactory indenterf ) {
		this.indenter = indenterf.newIndenter( this.pw );
	}

	public void print( T x ) {
		if ( ! this.theme.tryRender( this, x ) ) {
			throw new IllegalStateException( "Canont render this item" );
		}
	}
	
	void renderString( final String v ) {
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
	}

	public void flush() {
		pw.flush();
	}

	public void print( char c ) {
		pw.print( c );
	}

	public void print( String s ) {
		pw.print( s );
	}

	public void print( long s ) {
		pw.print( s );
	}

	public void print( double s ) {
		pw.print( s );
	}

	public void print( boolean s ) {
		pw.print( s );
	}

}
