package com.steelypip.powerups.jinxml.implementation;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.charrepeater.CharRepeater;
import com.steelypip.powerups.jinxml.Lookup;

public abstract class InputStreamProcessor {
	
	final static int MAX_CHARACTER_ENTITY_LENGTH = 32;
	
	abstract CharRepeater cucharin();
	
	char nextChar() {
		return this.cucharin().nextChar();
	}
	
	char nextChar( char ch ) {
		return this.cucharin().nextChar( ch );
	}
	
	void skipChar() {
		this.cucharin().skipChar();
	}
	
	void pushChar( char ch ) {
		this.cucharin().pushChar( ch );
	}
	
	char peekChar2( char otherwise ) {
		final char ch1 = this.nextChar( otherwise );
		final char ch2 = this.peekChar( otherwise );
		this.pushChar( ch1 );
		return ch2;
	}
	
	char peekChar() {
		return this.cucharin().peekChar();
	}
	
	char peekChar( char ch ) {
		return this.cucharin().peekChar( ch );
	}
	
	boolean isNextChar( char ch_want ) {
		return this.cucharin().isNextChar( ch_want );
	}
	
	boolean hasNextChar() {
		return this.cucharin().hasNextChar();
	}
	
	boolean tryReadChar( final char ch_want ) {
		final boolean read = this.isNextChar( ch_want );		
		if ( read ) {
			this.cucharin().skipChar();
		}
		return read;
	}
		

	
	/**
	 * Reads ahead on the input stream to determine if there are more 
	 * MinXML expressions.
	 * @return true if there are, else false
	 */
	boolean hasNext() {
		this.eatWhiteSpace();
		return this.peekChar( '\0' ) == '<';
	}
	
	void mustReadChar( final char ch_want ) {
		if ( this.isNextChar( ch_want ) ) {
			this.skipChar();
		} else {
			if ( this.hasNextChar() ) {
				throw new Alert( "Unexpected character" ).culprit( "Wanted", "" + ch_want ).culprit( "Received", "" + this.peekChar() );
			} else {
				throw new Alert( "Unexpected end of stream" );
			}			
		}
	}
	
	void eatUpTo( final char stop_char ) {
		final char not_stop_char = ( stop_char != '\0' ? '\0' : '_' );
		while ( this.nextChar( not_stop_char ) != stop_char ) {
		}		
	}
	
	void eatComment( final char ch ) {
		if ( ch == '!' ) {
			if ( this.isNextChar( '-' ) ) {
				this.skipChar();
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
	
	void eatWhiteSpace( final int allow_max_comma ) {
		int comma_count = 0;
		while ( this.hasNextChar() ) {
			final char ch = this.nextChar();
			if ( ch == ',' && comma_count < allow_max_comma ) {
				comma_count += 1;
			} else if ( ch == '/' ) {
				final char nch = this.peekChar( '\0' );
				if ( nch == '/' ) {
					this.eatUpTo( '\n' );
				} else if ( nch == '*' ) {
					for (;;) {
						this.eatUpTo( '*' );
						while ( this.tryReadChar( '*' ) ) {
							//	skip.
						}
						if ( this.nextChar() == '/' ) break;
					}
				} else {
					this.pushChar( ch );
					return;
				}
			} else if ( ! Character.isWhitespace( ch ) ) {
				this.pushChar( ch );
				return;
			}
		}
	}
	
	void eatWhiteSpace(){
		this.eatWhiteSpace( 0 );
	}
	
	void eatWhiteSpaceIncludingOneComma() {
		this.eatWhiteSpace( 1 );
	}
	
	boolean is_name_char( final char ch ) {
		return Character.isLetterOrDigit( ch ) || ch == '-' || ch == '.';
	}
	
	@SuppressWarnings("null")
	@NonNull String gatherName() {
		final StringBuilder name = new StringBuilder();
		while ( this.hasNextChar() ) {
			final char ch = this.nextChar();
			if ( is_name_char( ch ) ) {
				name.append( ch );
			} else {
				this.pushChar( ch );
				break;
			}
		}
		return name.toString();
	}
	
	@NonNull String gatherNameOrQuotedName() {
		final char pch = this.peekChar( '\0' ); 
		if ( pch == '"' || pch == '\'' ) {
			return this.gatherString();
		} else {
			return this.gatherName();
		}
	}
	
	String readEscapeContent() {
		final StringBuilder esc = new StringBuilder();
		for (;;) {
			final char ch = this.nextChar();
			if ( ch == ';' ) break;
			esc.append( ch );
			if ( esc.length() > MAX_CHARACTER_ENTITY_LENGTH ) {
				throw new Alert( "Malformed escape" ).culprit( "Sequence", esc );
			}
		}
		return esc.toString();
	}
	
	char entityLookup( final String symbol ) {
		Character c = Lookup.lookup( symbol );
		if ( c != null ) {
			return c;
		} else {
			throw new Alert( "Unexpected escape sequence after &" ).culprit( "Sequence", symbol );
		}
	}
	
	char readEscape() {
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
	
	@SuppressWarnings("null")
	@NonNull String gatherXMLAttributeValue() {
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
	

	@SuppressWarnings("null")
	@NonNull String gatherString() {
		final char quote_char = this.nextChar();
		StringBuilder sofar = new StringBuilder();
		boolean done = false;
		while( ! done ) {
			final char ch = this.nextChar();
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

	void readEscapeChar( final StringBuilder sofar ) {
		final char ch = this.nextChar();
		switch ( ch ) {
			case '\'':
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
			case '&':
				sofar.append( this.readEscape() );
				break;
			default:
				sofar.append( ch );
				break;
		}
	}

	void eatShebang() {
		if ( this.peekChar( '\0' ) == '#' && this.peekChar2( '\0' ) == '!' ) {
			this.eatUpTo( '\n' );
		}
	}
	
}
