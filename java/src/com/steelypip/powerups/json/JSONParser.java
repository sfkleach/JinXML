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

package com.steelypip.powerups.json;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.charrepeater.CharRepeater;
/**
 * This class implements a JSON parser on top of a JSONBuilder driver.
 * As the parser consumes JSON expressions it makes appropriate calls on
 * the driver to incrementally construct a result.
 * 
 * @param <T> the result type returned by the underlying builder
 */
public class JSONParser< T > {
	
	private static final char NON_NUMERIC_CHAR = ' ';
	private static final char NON_WHITESPACE_CHAR = '_';
	
	private final JSONBuilder< T > builder;
	private final CharRepeater cucharin; 

	public JSONParser( final CharRepeater rep, JSONBuilder< T > builder ) {
		this.builder = builder;
		this.cucharin = rep;
	}
	
	private void eatWhitespace() {
		while ( Character.isWhitespace( this.cucharin.peekChar( NON_WHITESPACE_CHAR ) ) ) {
			this.cucharin.skipChar();
		}
	}
	
	private void readNumber() {
		boolean is_floating_point = false;
		StringBuilder sofar = new StringBuilder();
		boolean done = false;
		do {
			final char ch = this.cucharin.peekChar( NON_NUMERIC_CHAR );
			switch ( ch ) {
				case '-':
				case '+':
					break;
				case '.':
					is_floating_point = true;
					break;
				default:
					if ( ! Character.isDigit( ch ) ) {
						done = true;
					}
					break;
			}
			if ( done ) break;
			sofar.append( ch );
			this.cucharin.skipChar();
		} while ( ! done );
		
		//	We have slightly different tests for floating point versus integers.
		final String s = sofar.toString();
		try {
			if ( is_floating_point ) {
				this.builder.addFloat( Double.parseDouble( s ) );
			} else {
				this.builder.addInteger( Long.parseLong( s ) );
			}
		} catch ( NumberFormatException e ) {
			throw new Alert( "Malformed number" ).culprit( "Bad number", sofar );
		}
	}
	
	static boolean isIdentifierContinuation( final char ch ) {
		return Character.isLetterOrDigit( ch ) || ch == '_';
	}
	
	private String readIdentiferText() {
		StringBuilder sofar = new StringBuilder();
		for (;;) {
			final char ch = this.cucharin.peekChar( ' ' );	// A character that's not part of an identifier.
			if ( isIdentifierContinuation( ch ) ) {
				this.cucharin.skipChar();
				sofar.append( ch );
			} else {
				break;
			}
		}
		return sofar.toString();
	}	
	
	private void readIdentifier() {
		final String identifier = readIdentiferText();
		if ( "null".equals( identifier ) ) {
			this.builder.addNull();
		} else if ( "true".equals( identifier ) ) {
			this.builder.addBoolean( true );
		} else if ( "false".equals( identifier ) ) {
			this.builder.addBoolean( false );
		} else {
			throw new Alert( "Unrecognised identifier" ).culprit(  "Identifier", identifier );
		}
	}
	
	private void read4HexDigits( final StringBuilder sofar ) {
		int code = 0;
		for ( int i = 0; i < 4; i++ ) {
			code <<= 8;
			final char ch = this.cucharin.nextChar();
			if ( Character.isDigit( ch ) ) {
				code |= ( ch - '0' );
			} else if ( 'A' <= ch && 'F' <= ch ) {
				code |= ( ch - 'A' + 10 );
			} else if ( 'a' <= ch && 'f' <= ch ) {
				code |= ( ch - 'a' + 10 );
			} else {
				throw new Alert( "Hex digit required" ).culprit( "Character", ch );
			}
		}
		sofar.append( (char)code );
	}
	
	private void readEscapeChar( final StringBuilder sofar ) {
		final char ch = this.cucharin.nextChar();
		switch ( ch ) {
			case '"':
			case '/':
			case '\\':
				sofar.append(  ch  );
				break;
			case 'n':
				sofar.append( '\n' );
				break;
			case 'r':
				sofar.append( '\r' );
				break;
			case 't':
				sofar.append( '\t' );
				break;
			case 'f':
				sofar.append( '\f' );
				break;
			case 'b':
				sofar.append( '\b' );
				break;
			case 'u':
				this.read4HexDigits( sofar );
				break;
			default:
				throw new Alert( "Invalid characters starting escape sequence" ).culprit( "Character", ch );
		}
	}
	
	private String readStringText() {
		final char quote_char = this.cucharin.nextChar();
		StringBuilder sofar = new StringBuilder();
		boolean done = false;
		
		while( ! done ) {
			final char ch = this.cucharin.nextChar();
			switch ( ch ) {
				case '"':
				case '\'':
					if ( ch == quote_char ) {
						done = true;
					} else {
						sofar.append( ch );
					}
					break;
				case '\\':
					this.readEscapeChar( sofar );
					break;
				default:
					sofar.append( ch );
					break;
			}
		}
		return sofar.toString();
	}
	
	private void readString() {
		this.builder.addString( this.readStringText() );
	}
	
	private void readArray() {
		this.builder.startArray();
		this.cucharin.skipChar();	//	Discard the '['
		if ( this.cucharin.peekChar() != ']' ) {
			this.readExpression();
			for (;;) {
				this.eatWhitespace();
				final char ch = this.cucharin.nextChar();
				if ( ch == ']' ) break;
				if ( ch != ',' ) {
					throw new Alert( "Missing separator" ).hint( "Expected ','" ).culprit( "Found", ch );
				}
				this.readExpression();
			}
		}
		this.builder.endArray();
	}
	
	private void readField() {
		if ( this.cucharin.peekChar() != '"' ) {
			throw new Alert( "Object field not a string" ).hint( "Must begin with a '\"' mark" ).culprit( "Found", this.cucharin.peekChar() );
		}
		this.builder.field( this.readStringText() );
	}
	
	private void readObject() {
		this.builder.startObject();
		this.cucharin.skipChar();	//	Discard the '{'
		if ( this.cucharin.peekChar() != '}' ) {
			this.eatWhitespace();
			this.readField();
			this.eatWhitespace();
			if ( this.cucharin.nextChar() != ':' ) {
				throw new Alert( "Missing ':' in object" );
			}
			this.readExpression();
			for (;;) {
				this.eatWhitespace();
				final char ch = this.cucharin.nextChar();
				if ( ch == '}' ) break;
				if ( ch != ',' ) {
					throw new Alert( "Missing separator" ).hint( "Expected ','" ).culprit( "Found", ch );
				}
				this.readField();
				this.eatWhitespace();
				if ( this.cucharin.nextChar() != ':' ) {
					throw new Alert( "Missing ':' in object" );
				}
				this.readExpression();
			}
		}
		this.builder.endObject();
	}
	
	private void readExpression() {
		this.eatWhitespace();
		final char ch = this.cucharin.peekChar();
		if ( Character.isDigit( ch ) || ch == '-' || ch == '+' ) {
			this.readNumber();
		} else if ( Character.isLetter( ch ) ) {
			this.readIdentifier();
		} else if ( ch == '"' ) {
			this.readString();
		} else if ( ch == '[' ) {
			this.readArray();
		} else if ( ch == '{' ) {
			this.readObject();
		} else {
			throw new Alert( "Unexpected character" ).culprit( "Character", ch );
		}
	}
	
	/**
	 * Consumes a single JSON expression.
	 * @return the object built by the underlying builder.
	 */
	public T read() {
		this.readExpression();
		return this.builder.build();
	}

}
