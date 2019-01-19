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

import java.io.Reader;
import java.util.Iterator;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.charrepeater.CharRepeater;
import com.steelypip.powerups.charrepeater.ReaderCharRepeater;

/**
 * This class wraps various types of input stream to
 * create a MinXML parser. An optional MinXMLBuilder can
 * be supplied, which gives control over the MinXML
 * implementation that is actually constructed.
 * 
 * MinXML elements are read off the stream using
 * readElement. Alternatively you can simply iterate 
 * over the parser.
 */
public class MinXMLParser implements Iterable< MinXML > {
	
	private int level = 0;
	private final CharRepeater cucharin;
	
	private boolean pending_end_tag = false;
	private MinXMLBuilder parent = null;
	private String tag_name = null;	
	
	public MinXMLParser( CharRepeater rep, MinXMLBuilder parent ) {
		this.parent = parent;
		this.cucharin = rep;
	}

	/**
	 * Constructs a parser from a {@link java.io.Reader} and a
	 * MinXMLBuilder. 
	 * @param reader the input source
	 * @param builder used to construct the MinXML objects
	 */
	public MinXMLParser( Reader reader, MinXMLBuilder builder ) {
		this.parent = builder;
		this.cucharin = new ReaderCharRepeater( reader );
	}

	public MinXMLParser( final Reader rep ) {
		this.parent = new FlexiMinXMLBuilder();
		this.cucharin = new ReaderCharRepeater( rep );
	}

	private char nextChar() {
		return this.cucharin.nextChar();
	}
		
	private char peekChar() {
		return this.cucharin.peekChar();
	}
	
	/**
	 * Reads ahead on the input stream to determine if there are more 
	 * MinXML expressions.
	 * @return true if there are, else false
	 */
	public boolean hasNext() {
		this.eatWhiteSpace();
		return this.cucharin.peekChar( '\0' ) == '<';
	}
	
	private void mustReadChar( final char ch_want ) {
		if ( this.cucharin.isNextChar( ch_want ) ) {
			this.cucharin.skipChar();
		} else {
			if ( this.cucharin.hasNextChar() ) {
				throw new Alert( "Unexpected character" ).culprit( "Wanted", "" + ch_want ).culprit( "Received", "" + this.cucharin.peekChar() );
			} else {
				throw new Alert( "Unexpected end of stream" );
			}			
		}
	}
	
	private void eatUpTo( final char stop_char ) {
		final char not_stop_char = ( stop_char != '\0' ? '\0' : '_' );
		while ( this.cucharin.nextChar( not_stop_char ) != stop_char ) {
		}		
	}
	
	private void eatComment( final char ch ) {
		if ( ch == '!' ) {
			if ( this.cucharin.isNextChar( '-' ) ) {
				this.cucharin.skipChar();
				this.mustReadChar( '-' );
				int count_minuses = 0;
				for (;;) {
					final char nch = this.nextChar();
					if ( nch == '-' ) {
						count_minuses += 1;
					} else if ( nch == '>' && count_minuses >= 2 ) {
						break;
					} else {
						if ( count_minuses >= 2 ) {
							throw new Alert( "Invalid XML comment" ).hint( "Detected -- within the body of comment" ).culprit( "Character following --", (int)nch );
						}
						count_minuses = 0;
					}
				}
			} else {
				for (;;) {
					final char nch = this.nextChar();
					if ( nch == '>' ) break;
					if ( nch == '<' ) this.eatComment( this.nextChar() );
				}
			}
		} else {
			this.eatUpTo( '>' );
		}
	}
	
	private void eatWhiteSpace() {
		while ( this.cucharin.hasNextChar() ) {
			final char ch = this.cucharin.nextChar();
			if ( ! Character.isWhitespace( ch ) ) {
				this.cucharin.pushChar( ch );
				return;
			}
		}
	}
	
	private static boolean is_name_char( final char ch ) {
		return Character.isLetterOrDigit( ch ) || ch == '-' || ch == '.';
	}
	
	private String readName() {
		final StringBuilder name = new StringBuilder();
		while ( this.cucharin.hasNextChar() ) {
			final char ch = this.cucharin.nextChar();
			if ( is_name_char( ch ) ) {
				name.append( ch );
			} else {
				this.cucharin.pushChar( ch );
				break;
			}
		}
		return name.toString();
	}
	
	private String readEscapeContent() {
		final StringBuilder esc = new StringBuilder();
		for (;;) {
			final char ch = this.nextChar();
			if ( ch == ';' ) break;
			esc.append( ch );
			if ( esc.length() > 4 ) {
				throw new Alert( "Malformed escape" ).culprit( "Sequence", esc );
			}
		}
		return esc.toString();
	}
	
	private char entityLookup( final String symbol ) {
		if ( "lt".equals( symbol ) ) {
			return '<';
		} else if ( "gt".equals( symbol ) ) {
			return '>';
		} else if ( "amp".equals(  symbol  ) ) {
			return '&';
		} else if ( "quot".equals( symbol ) ) {
			return '"';
		} else if ( "apos".equals(  symbol ) ) {
			return '\'';
		} else {
			throw new Alert( "Unexpected escape sequence after &" ).culprit( "Sequence", symbol );
		}		
	}
	
	private char readEscape() {
		final String esc = this.readEscapeContent();
		if ( esc.length() >= 2 && esc.charAt( 0 ) == '#' ) {
			try {
				final int n = Integer.parseInt( esc.toString().substring( 1 ) );
				return (char)n;
			} catch ( NumberFormatException e ) {
				throw new Alert( "Unexpected numeric sequence after &#", e ).culprit( "Sequence", esc );
			}
		} else {
			return this.entityLookup( esc );
		}
		
	}
	
	private String readAttributeValue() {
		final StringBuilder attr = new StringBuilder();
		final char q = this.nextChar();
		if ( q != '"' && q != '\'' ) throw new Alert( "Attribute value not quoted" ).culprit( "Character", q );
		for (;;) {
			char ch = this.nextChar();
			if ( ch == q ) break;
			if ( ch == '&' ) {
				attr.append( this.readEscape() );
			} else {
				if ( ch == '<' ) {
					throw new Alert( "Forbidden character in attribute value" ).hint( "Use an entity reference" ).culprit( "Character", ch );
				}
				attr.append( ch );
			}
		}
		return attr.toString();
	}	
	
	
	private void processAttributes() {
		for (;;) {
			this.eatWhiteSpace();
			char c = peekChar();
			if ( c == '/' || c == '>' ) break;
			final String key = this.readName();
			
			this.eatWhiteSpace();
			this.mustReadChar( '=' );
			this.eatWhiteSpace();
			final String value = this.readAttributeValue();
			this.parent.put( key, value );
		}
	}
	

	
	private boolean read() {
		
		if ( this.pending_end_tag ) {
			this.parent.endTag( this.tag_name );
			this.pending_end_tag = false;
			this.level -= 1;
			return true;
		}
			
		this.eatWhiteSpace();
		
		if ( !this.cucharin.hasNextChar() ) {
			return false;
		}
		
		this.mustReadChar( '<' );
			
		char ch = this.nextChar();
		
		if ( ch == '/' ) {
			final String end_tag = this.readName();
			this.eatWhiteSpace();
			this.mustReadChar( '>' );
			this.parent.endTag( end_tag );
			this.level -= 1;
			return true;
		} else if ( ch == '!' || ch == '?' ) {
			this.eatComment( ch  );
			return this.read();
		} else {
			this.cucharin.pushChar( ch );
		}
		
		this.tag_name = this.readName();
		
		this.parent.startTagOpen( this.tag_name );
		this.processAttributes();
		this.parent.startTagClose( this.tag_name );
		
		this.eatWhiteSpace();
				
		ch = nextChar();
		if ( ch == '/' ) {
			this.mustReadChar( '>' );
			this.pending_end_tag = true;
			this.level += 1;
			return true;
		} else if ( ch == '>' ) {
			this.level += 1;
			return true;
		} else {
			throw new Alert( "Invalid continuation" );
		}
				
	}
	
	/**
	 * Read an element off the input stream or null if the stream is
	 * exhausted.
	 * @return the next element
	 */
	public MinXML readElement() {
		while ( this.read() ) {
			if ( this.level == 0 ) break;
		}
		if ( this.level != 0 ) {
			throw new Alert( "Unmatched tags due to encountering end of input" );
		}
		return parent.build();
	}
	
	/**
	 * Returns an iterator that reads elements off sequentially
	 * from this parser.
	 */
	public Iterator< MinXML > iterator() {
		return new Iterator< MinXML >() {

			@Override
			public boolean hasNext() {
				return MinXMLParser.this.hasNext();
			}

			@Override
			public MinXML next() {
				return MinXMLParser.this.readElement();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}

}
