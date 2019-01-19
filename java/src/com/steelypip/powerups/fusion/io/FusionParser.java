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
package com.steelypip.powerups.fusion.io;

import java.io.Reader;
import java.util.Iterator;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.charrepeater.CharRepeater;
import com.steelypip.powerups.charrepeater.ReaderCharRepeater;
import com.steelypip.powerups.fusion.Fusion;
import com.steelypip.powerups.fusion.FusionBuilder;
import com.steelypip.powerups.hydraxml.LevelTracker;
import com.steelypip.powerups.minxson.Lookup;

/**
 * This class wraps various types of input stream to
 * create a Fusion parser. An optional FusionBuilder can
 * be supplied, which gives control over the Fusion
 * implementation that is actually constructed.
 * 
 * Fusion elements are read off the stream using
 * readElement. Alternatively you can simply iterate 
 * over the parser.
 */
public class FusionParser extends LevelTracker implements Iterable< Fusion > {
	
	private static final int MAX_CHARACTER_ENTITY_LENGTH = 32;

	private final CharRepeater cucharin;
	
	private boolean pending_end_tag = false;
	private FusionBuilder builder = null;
	private String tag_name = null;	
	
	/**
	 * Constructs a parser from a {@link com.steelypip.powerups.charrepeater.CharRepeater} and a
	 * FusionBuilder. Character repeaters are streams with unlimited pushback.
	 *  
	 * @param rep the input source
	 * @param builder used to construct the Fusion objects
	 */
	public FusionParser( CharRepeater rep, FusionBuilder builder ) {
		this.builder = builder;
		this.cucharin = rep;
	}

	/**
	 * Constructs a parser from a {@link java.io.Reader} and a
	 * FusionBuilder.
	 *  
	 * @param reader the input source
	 * @param builder used to construct the Fusion objects
	 */
	public FusionParser( Reader reader, FusionBuilder builder ) {
		this.builder = builder;
		this.cucharin = new ReaderCharRepeater( reader );
	}

	/**
	 * Constructs a parser from a {@link java.io.Reader} and 
	 * supplies a default builder that optimises for JSON-like
	 * structures.
	 *  
	 * @param reader the input source
	 */
	public FusionParser( final Reader rep ) {
		this.builder = new JSONFusionBuilder();
		this.cucharin = new ReaderCharRepeater( rep );
	}

	private char nextChar() {
		return this.cucharin.nextChar();
	}
	
	private void skipChar() {
		this.cucharin.skipChar();
	}
	
	private boolean tryReadChar( final char ch_want ) {
		final boolean read = this.cucharin.isNextChar( ch_want );		
		if ( read ) {
			this.cucharin.skipChar();
		}
		return read;
	}
		
	private char peekChar() {
		return this.cucharin.peekChar();
	}
	
	private char peekChar( char ch ) {
		return this.cucharin.peekChar( ch );
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
	
	private void eatWhiteSpace( final int allow_max_comma ) {
		int comma_count = 0;
		while ( this.cucharin.hasNextChar() ) {
			final char ch = this.cucharin.nextChar();
			if ( ch == '#' && this.peekChar( '\0' ) == '!' && this.isAtTopLevel() ) {
				//	Shebang - note that this is coded quite carefully to leave 
				//	the options open for other interpretations of #.
				this.eatUpTo( '\n' );
			} else if ( ch == ',' && comma_count < allow_max_comma ) {
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
					this.cucharin.pushChar( ch );
					return;
				}
			} else if ( ! Character.isWhitespace( ch ) ) {
				this.cucharin.pushChar( ch );
				return;
			}
		}
	}
	
	private void eatWhiteSpace(){
		this.eatWhiteSpace( 0 );
	}
	
	private void eatWhiteSpaceIncludingOneComma() {
		this.eatWhiteSpace( 1 );
	}
	
	private static boolean is_name_char( final char ch ) {
		return Character.isLetterOrDigit( ch ) || ch == '-' || ch == '.';
	}
	
	@SuppressWarnings("null")
	private @NonNull String gatherName() {
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
	
	private @NonNull String gatherNameOrQuotedName() {
		final char pch = this.peekChar( '\0' ); 
		if ( pch == '"' || pch == '\'' ) {
			return this.gatherString();
		} else {
			return this.gatherName();
		}
	}
	
	private String readEscapeContent() {
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
	
	private char entityLookup( final String symbol ) {
		Character c = Lookup.lookup( symbol );
		if ( c != null ) {
			return c;
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
	
	@SuppressWarnings("null")
	private @NonNull String gatherXMLAttributeValue() {
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
			final @NonNull String key = this.gatherNameOrQuotedName();
			
			this.eatWhiteSpace();
			final boolean repeat_ok = this.tryReadChar( '+' );
			final char nch = this.nextChar();
			this.eatWhiteSpace();
			if ( nch != '=' && nch != ':' ) {
				throw new Alert( "Expected = or :" ).culprit( "Received", nch );
			}
			final String value = nch == '=' ? this.gatherXMLAttributeValue() : this.gatherString();
			this.builder.add( key, value, repeat_ok );
		}
	}
	
	/**
	 * This is the core routine of the algorithm, which consumes a single tag from the
	 * input stream. Standalong tags are expanded internally into separate open and close
	 * tags.
	 * @return true if it read a tag, false at end of stream.
	 */
	private boolean readNextTag( boolean allow_field, String field, boolean accept_repeat_field ) {
		
		if ( this.pending_end_tag ) {
			this.builder.endTag( this.tag_name );
			this.pending_end_tag = false;
			this.popElement();
			return true;
		}
			
		this.eatWhiteSpaceIncludingOneComma();
		
		if ( !this.cucharin.hasNextChar() ) {
			return false;
		}
		
		final char pch = this.peekChar( '\0' );
		if ( Character.isLetter( pch ) ) {
			return readLabelledTagOrSymbol( true, this.gatherName() );
		} else if ( pch == '"' || pch == '\'' ) {
			return readLabelledTagOrSymbol( false, this.gatherString() );
		} else {
			return readUnlabelledTag( field, accept_repeat_field );
		}
	}
	
	private boolean readLabelledTagOrSymbol( final boolean is_identifier, final String field ) {
		boolean accept_repeat_field = false;
		this.eatWhiteSpace();
		boolean plus = this.tryReadChar( '+' );
		boolean colon = this.tryReadChar( ':' );
		if ( colon ) {
			if ( plus ) {
				accept_repeat_field = true;
			}
			this.eatWhiteSpace();
			return readUnlabelledTag( field, accept_repeat_field );
		} else if ( plus ) {
			//	:+ is not allowed - we want to raise the alarm.
			this.mustReadChar( ':' ); 	//	This will throw an exception (the one we want).
			throw Alert.unreachable();	//	So compiler doesn't complain.
		} else {
			return handleStringOrIdentifier( is_identifier, field );
		}
	}
	
	private boolean readUnlabelledTag( String field, boolean accept_repeat_field ) {
		final char pch = this.peekChar( '\0' );
		if ( pch == '<' || pch == '[' || pch == '{' ) {
			return this.readCoreTag( field, accept_repeat_field );
		} else if ( Character.isDigit( pch ) || pch == '+' || pch == '-' ) {
			return this.readNumber( field );
		} else if ( pch == '"' || pch == '\'' ) {
			return this.readString( field );
		} else if ( pch == ']' ) {
			this.mustReadChar( ']' );
			this.popArray();
			this.builder.endArray( "" );
			return true;
		} else if ( pch == '}' ) {
			this.mustReadChar( '}' );
			this.popObject();
			this.builder.endObject( "" );
			return true;
		} else if ( Character.isLetter( pch ) ) {
			return this.handleIdentifier( this.gatherName() );
		} else {
			throw new Alert( "Unexpected character when read tag or constant" ).culprit( "Character", pch );
		}
	}
	
	private boolean readCoreTag( String field, boolean accept_repeat_field ) {	
		if ( this.tryReadChar( '[' ) ) {
			this.pushArray();
			this.builder.startArray( field );
			return true;
		} else if ( this.tryReadChar( '{' ) ) {
			this.pushObject();
			this.builder.startObject( field );
			return true;
		} else if ( this.tryReadChar( '<' ) ) {
			char ch = this.nextChar();
			if ( ch == '/' ) {
				this.eatWhiteSpace();
				if ( this.tryReadChar( '>' ) ) {
					this.builder.endTag();
				} else {
					final String end_tag = this.gatherName();
					this.processAttributes();
					this.eatWhiteSpace();
					this.mustReadChar( '>' );
					this.builder.endTag( end_tag );
				}
				this.popElement();
				return true;
			} else if ( ch == '!' || ch == '?' ) {
				this.eatComment( ch  );
				return this.readNextTag( false, field, accept_repeat_field );
			} else {
				this.cucharin.pushChar( ch );
			}
			
			this.eatWhiteSpace();
			this.tag_name = this.gatherNameOrQuotedName();
			
			this.builder.startTag( field, this.tag_name, accept_repeat_field );
			this.processAttributes();
			
			this.eatWhiteSpace();					
			ch = nextChar();
			if ( ch == '/' ) {
				this.mustReadChar( '>' );
				this.pending_end_tag = true;
				this.pushElement();
				return true;
			} else if ( ch == '>' ) {
				this.pushElement();
				return true;
			} else {
				throw new Alert( "Invalid continuation" );
			}
		} else {
			//	TODO, not correct error message at end of file.
			throw new Alert( "Unexpected character" ).culprit( "Expecting", "< or [ or { " ).culprit( "Actually", this.peekChar( '\0' ) ); 
		}  
	}
	


	@SuppressWarnings("null")
	private @NonNull String gatherString() {
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
	
	private boolean readString( final String field ) {
		this.builder.addString( field, this.gatherString() );
		return true;
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
	
//	private void acceptNextChar( final StringBuilder b ) {
//		b.append( this.nextChar() );		
//	}
	
	static class NumParser {
		FusionParser fparser;
		
		StringBuilder b = new StringBuilder();
		
		int radix = 10;
		boolean floating_point = false;
		
		public NumParser( FusionParser fparser ) {
			super();
			this.fparser = fparser;
		}
		
		Number process() {
			while ( this.processChar( fparser.peekChar( '\0' ) ) ) {
			}
			return convertStringToNumber( radix, floating_point, b.toString() );			
		}
		
		boolean processChar( char pch ) {
			switch ( pch ) {
			case '.':
				return processDot( pch );
			case '-':
			case '+':
				return processSign( pch );
			case '0':
				return processZero( pch );
			default:
				return processOthers( pch );
			}
		}

		void acceptNextChar( char x ) {
			this.b.append( this.fparser.nextChar() );
		}
		
		boolean isDigit( char ch ) {
			if ( radix == 10 ) {
				return Character.isDigit( ch );
			} else if ( radix == 16 ) {
				return Character.isDigit( ch ) || ( "ABCDEF".indexOf( ch ) != -1 );
			} else if ( radix == 2 ) {
				return ch == '0' || ch == '1';
			} else {
				throw new Alert( "Invalid digit" ).culprit(  "Radix", this.radix ).culprit( "Digit", ch );
			}
		}

		private boolean processOthers( char pch ) {
			if ( this.isDigit( pch ) ) {
				this.acceptNextChar( pch );
				return true;
			} else {
				return false;
			}
		}

		private boolean processZero( char pch ) {
			if ( b.length() == 0 || b.length() == 1 && ( "+-".indexOf( b.charAt( 0 ) ) != -1 ) ) {
				this.fparser.skipChar();
				radix = fparser.tryReadChar( 'b' ) ? 2 : this.fparser.tryReadChar( 'x' ) ? 16 : 10;
				if ( radix == 10 ) {
					b.append( '0' );
				}
			} else {
				this.acceptNextChar( pch );
			}
			return true;
		}

		private boolean processSign( char pch ) {
			if ( b.length() == 0 ) {
				this.acceptNextChar( pch );
				return true;
			} else {
				return false;
			}
		}

		private boolean processDot( char pch ) {
			if ( floating_point ) {
				return false;
			} else {
				floating_point = true;
				this.acceptNextChar( pch );
				return true;
			}
		}
		
	}
	
	private static Number convertStringToNumber( int radix, boolean floating_point, final String numString ) {
		try {
			if ( floating_point ) {
				if ( radix == 10 ) {
					return Double.parseDouble( numString );
				} else {
					throw new Alert( "Floating points with non-decimal radix not supported yet" ).culprit( "Radix", radix ).culprit( "Number", numString );
				}
			} else {
				return Long.parseLong( numString, radix );
			}
		} catch ( NumberFormatException e ) {
			throw new Alert( "Malformed number" ).culprit( "Bad number", numString );
		}
	}
	
	private Number gatherNumber() {
		return new NumParser( this ).process();			
	}

	
	private boolean readNumber( final String field ) {
		final Number number = this.gatherNumber();
		if ( number instanceof Double ) {
			this.builder.addFloat( field, (Double)number );
		} else {
			this.builder.addInteger( field, (Long)number );
		}
		return true;
	}
	
	private boolean handleStringOrIdentifier(  final boolean is_identifier, final String x ) {
		return is_identifier ? this.handleIdentifier( x ) : this.handleString( x );
	}


	private boolean handleIdentifier( String identifier ) {
		switch ( identifier ) {
		case "null":
			this.builder.addNull();
			return true;
		case "true":
		case "false":
			this.builder.addBoolean( Boolean.parseBoolean( identifier ) );
			return true;
		default:
			throw new Alert( "Unrecognised identifier" ).culprit( "Identifier", identifier );
		}
	}
	
	private boolean handleString( String s ) {
		this.builder.addString( s );
		return true;
	}
	
	/**
	 * Read an element off the input stream or null if the stream is
	 * exhausted.
	 * @return the next element
	 */
	public Fusion readElement() {
		while ( this.readNextTag( true, "", true ) ) {
			if ( this.isAtTopLevel() ) break;
		}
		if ( ! this.isAtTopLevel() ) {
			throw new Alert( "Unmatched tags due to encountering end of input" );
		}
		return builder.build();
	}
	
	/**
	 * Returns an iterator that reads elements off sequentially
	 * from this parser.
	 */
	public Iterator< Fusion > iterator() {
		return new Iterator< Fusion >() {

			@Override
			public boolean hasNext() {
				return FusionParser.this.hasNext();
			}

			@Override
			public Fusion next() {
				return FusionParser.this.readElement();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}

}
