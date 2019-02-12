package com.steelypip.powerups.jinxml.implementation;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.charrepeater.CharRepeater;
import com.steelypip.powerups.jinxml.Lookup;

public abstract class TokeniserBaseClass {
	
	private static final char AMPERSAND = '&';
	private static final char LONG_COMMENT_2 = '*';
	private static final char LONG_COMMENT_1 = '/';
	private static final char FORWARD_SLASH = '/';
	private static final char SHEBANG2 = '!';
	private static final char SHEBANG1 = '#';
	private static final char BACK_SLASH = '\\';
	private static final char SINGLE_QUOTE = '\'';
	private static final char DOUBLE_QUOTE = '"';
	private static final char COMMA_AS_ITEM_SEPARATOR = ',';
	private static final char SEMICOLON_AS_ITEM_SEPARATOR = ';';
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
			if ( ( ch == COMMA_AS_ITEM_SEPARATOR || ch == SEMICOLON_AS_ITEM_SEPARATOR ) && comma_count < allow_max_comma ) {
				comma_count += 1;
			} else if ( ch == LONG_COMMENT_1 ) {
				final char nch = this.peekChar( '\0' );
				if ( nch == LONG_COMMENT_1 ) {
					this.eatUpTo( '\n' );
				} else if ( nch == LONG_COMMENT_2 ) {
					for (;;) {
						this.eatUpTo( LONG_COMMENT_2 );
						while ( this.tryReadChar( LONG_COMMENT_2 ) ) {
							//	skip.
						}
						if ( this.nextChar() == LONG_COMMENT_1 ) break;
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
		if ( pch == DOUBLE_QUOTE || pch == SINGLE_QUOTE ) {
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
		final Character c = Lookup.lookup( symbol );
		if ( c != null ) {
			return c;
		} else {
			throw new Alert( "Unexpected escape sequence after &" ).culprit( "Sequence", symbol );
		}
	}
	
	char readXMLStyleEscapeChar() {
		if ( this.tryReadChar( BACK_SLASH ) ) {
			return this.readJSONStyleEscapeChar();
		} else {
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
	}
	

	char readJSONStyleEscapeChar() {
		final char ch = this.nextChar();
		switch ( ch ) {
			case SINGLE_QUOTE:
			case DOUBLE_QUOTE:
			case FORWARD_SLASH:
			case BACK_SLASH:
				return ch;
			case 'n':
				return '\n';
			case 'r':
				return '\r';
			case 't':
				return '\t';
			case 'f':
				return '\f';
			case 'b':
				return '\b';
			case '&':
				return this.readXMLStyleEscapeChar();
			default:
				return ch;
		}
	}
	
	@SuppressWarnings("null")
	@NonNull String gatherString() {
		final StringBuilder attr = new StringBuilder();
		final char opening_quote_mark = this.nextChar();
		if ( opening_quote_mark != DOUBLE_QUOTE && opening_quote_mark != SINGLE_QUOTE ) throw new Alert( "Attribute value not quoted" ).culprit( "Character", opening_quote_mark );
		final boolean is_xml = opening_quote_mark == SINGLE_QUOTE;
		final char esc = is_xml ? AMPERSAND : BACK_SLASH;
		for (;;) {
			char ch = this.nextChar();
			if ( ch == opening_quote_mark ) break;
			if ( ch == esc ) {
				attr.append( is_xml ? this.readXMLStyleEscapeChar() : this.readJSONStyleEscapeChar() );
			} else {
				attr.append( ch );
			}
		}
		return attr.toString();
	}

	void eatShebang() {
		if ( this.peekChar( '\0' ) == SHEBANG1 && this.peekChar2( '\0' ) == SHEBANG2 ) {
			this.eatUpTo( '\n' );
		}
	}
	
}
