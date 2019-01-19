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

package com.steelypip.powerups.minxson;

import java.io.Reader;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.math.BigInteger;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.charrepeater.CharRepeater;
import com.steelypip.powerups.charrepeater.ReaderCharRepeater;
import com.steelypip.powerups.json.JSONKeywords;
import com.steelypip.powerups.minxml.FlexiMinXMLBuilder;
import com.steelypip.powerups.minxml.MinXML;
import com.steelypip.powerups.minxml.MinXMLBuilder;

/*
 * This class implements a parser for the MinXSON grammar, which is 
 * a hybrid of minimal XML and JSON. It has a number of non-standard
 * extensions that can be enabled.
 * 
 * This parser utilizes a MinXMLBuilder to generate MinXML objects. 
 * It can either be used to read individual expressions one by one off the input 
 * or it can be turned into an iterator and used in a loop. 
 */
public class MinXSONParser extends LevelTracker implements Iterable< MinXML > {
	
	private static final String TYPE_ATTRIBUTE_PREFIX = "@";
	private static final char FIELD_ATTRIBUTE_SUFFIX = ':';

	protected JSONKeywords json_keys = JSONKeywords.KEYS;
	private final CharRepeater cucharin;
	private MinXMLBuilder parent = null;
	
	private final TreeMap< String, String > extra_attributes = new TreeMap< String, String >();
	private boolean EMBEDDED_EXTENSION = false;
	private boolean TYPE_PREFIX_EXTENSION = false;
	private boolean TUPLE_EXTENSION = false;
	
	/**
	 * These extensions toggle optional features.
	 * 	Option 'A' switches on _A_ll extensions.
	 *  Option 'E' switches on the _E_mbedded extension.
	 *  Option 'T' switches on the _T_ype-prefix extension.
	 *  Option 'U' switches on the t_U_ple extension.
	 * @param extensions a character array encoding the set of extensions needed.
	 * @return the parser, used for method chaining 
	 */
	public MinXSONParser enableExtensions( char[] extensions ) {
		for ( char ch : extensions ) {
			boolean all = false;
			switch ( ch ) {
			case 'A': 
				all = true;
			case 'E': 
				EMBEDDED_EXTENSION = true; 
				if ( !all ) break;
			case 'T': 
				TYPE_PREFIX_EXTENSION = true; 
				if ( !all ) break;
			case 'U':
				TUPLE_EXTENSION = true;
				break;
			default:
				throw new Alert( "Unrecognised extension" ).culprit( "Extension flag", ch );
			}
		}
		return this;
	}
	
	public MinXSONParser( CharRepeater rep, MinXMLBuilder parent, char... extensions ) {
		this.parent = parent;
		this.cucharin = rep;
		this.enableExtensions( extensions );
	}

	public MinXSONParser( Reader rep, MinXMLBuilder parent, char... extensions ) {
		this( new ReaderCharRepeater( rep ), parent, extensions );
	}

	public MinXSONParser( final Reader rep, char... extensions ) {
		this( new ReaderCharRepeater( rep ), new FlexiMinXMLBuilder(), extensions );
	}
	

	private char nextChar() {
		return this.cucharin.nextChar();
	}
	
	private void discardChar( final int n ) {
		for ( int i = 0; i < n; i++ ) {
			this.cucharin.skipChar();
		}
	}
		
	private void discardChar() {
		this.cucharin.skipChar();
	}
		
	private char peekChar() {
		return this.cucharin.peekChar();
	}
	
	private char peekChar( final char default_char ) {
		return this.cucharin.peekChar( default_char );
	}
	
	private boolean hasNextExpression() {
		this.eatWhiteSpace();
		return this.cucharin.peekChar( '\0' ) == '<';
	}
	
	private void mustPeekChar( final char ch_want ) {
		if ( this.cucharin.hasNextChar() ) {
			final char ch_actual = this.cucharin.peekChar();
			if ( ch_actual != ch_want ) {
				throw new Alert( "Unexpected character" ).culprit( "Expected", ch_want ).culprit( "Actual", ch_actual );
			}
		} else {
			throw new Alert( "Unexpected end of stream" ).culprit( "Expected", ch_want );
		}		
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
	
	private int mustReadOneOf( final String t ) {
		final char ch = this.nextChar();
		final int n = t.indexOf( ch );
		if ( n == -1 ) {
			throw new Alert( "Unexpected character" ).culprit( "Expected one of", t ).culprit( "Received", ch );
		}
		return n;
	}
	
	private boolean tryReadChar( final char ch_want ) {
		if ( this.cucharin.isNextChar( ch_want ) ) {
			this.cucharin.skipChar();
			return true;
		} else {
			return false;
		}
	}
	
	private boolean	tryReadString( final String want ) {
		if ( this.cucharin.isNextString( want ) ) {
			this.discardChar( want.length() );
			return true;
		} else {
			return false;
		}
	}
	
	private boolean	tryPeekString( final String want ) {
		return this.cucharin.isNextString( want );
	}
	
	private void eatUpTo( final char stop_char ) {
		final char not_stop_char = ( stop_char != '\0' ? '\0' : '_' );
		while ( this.cucharin.nextChar( not_stop_char ) != stop_char ) {
		}		
	}
	
	private void eatWhiteSpace() {
		while ( this.cucharin.hasNextChar() ) {
			final char ch = this.cucharin.nextChar();
			if ( ch == '#' && this.peekChar( '\0' ) == '!' && this.isAtTopLevel() ) {
				//	Shebang - note that this is coded quite carefully to leave 
				//	the options open for other interpretations of #.
				this.eatUpTo( '\n' );
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
	
	private void startTagOpen( final String tag ) {
		this.parent.startTagOpen( tag );
		for ( Map.Entry< String, String > m : this.extra_attributes.entrySet() ) {
			this.parent.put( m.getKey(), m.getValue() );
		}
		this.extra_attributes.clear();
	}

	private void startTagClose( final String tag ) {
		this.parent.startTagClose( tag );
	}

	private void endTag( final String tag ) {
		this.parent.endTag( tag );
	}

	private void put( final String key, final String value ) {
		this.parent.put( key, value );
	}
	
	private static boolean isNameChar( final char ch ) {
		return Character.isLetterOrDigit( ch ) || ch == '-' || ch == '.';
	}
	
	private String readName() {
		final StringBuilder name = new StringBuilder();
		while ( this.cucharin.hasNextChar() ) {
			final char ch = this.cucharin.nextChar();
			if ( isNameChar( ch ) ) {
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
			if ( c == '/' || c == '>' || c == '[' || c == '{'  ) break;
			final String key = this.readName();
			
			this.eatWhiteSpace();
			final int n = this.mustReadOneOf( "=:" );
			this.eatWhiteSpace();
			final String value = n == 0 ? this.readAttributeValue() : this.readJSONStringText();
			this.put( key, value );
		}
	}
	
	private boolean readOneTag() {		
		if ( this.hasPendingTag() ) {
			final boolean was_in_element = this.isInElement();
			this.endTag( this.popTag() );
			if ( this.isntAtTopLevel() ) {
				this.setExpectingTerminator( was_in_element );
			}
			return true;
		} else if ( this.isExpectingTerminator() ) {
			this.consumeOptionalTerminator();
		}
		this.eatWhiteSpace();
		if ( this.cucharin.hasNextChar() ){
			this.readWithoutPending();
			return true;
		} else {
			return false;
		}
	}
	
	void consumeOptionalTerminator() {
		if ( this.isInParentheses() ) {
			this.eatWhiteSpace();
			this.mustPeekChar( ')' );
		}
		// End of input is a valid terminator!
		while ( this.cucharin.hasNextChar() ) {
			final char ch  = this.peekChar();
			if ( ch == ',' || ch == ';' ) {
				this.discardChar();
				break;
			} else if ( Character.isWhitespace( ch ) ) {
				this.discardChar();
				continue;
			} else if ( ch == '<' ) {
				//	No need for terminators between tags.
				if ( this.wasInElement() ) break;
				//	No need for a terminator if the next token is a end-tag.
				// 	If it is a start-tag it is illegal. 
				if ( this.tryPeekString( "</" ) || this.tryPeekString( "<?" ) || this.tryPeekString( "<!" ) ) {
					//	We accept a processing directive as a separator.
					break;
				}
				throw new Alert( "Missing separator before '<'" ).culprit( "At character", ch );
			} else if ( ch == ']' || ch == '}' || ch == ')' ) {
				break;
			} else {
				throw new Alert( "Unexpected character whilst looking for separator/terminator" ).culprit( "Character", ch );
			}
		}
		this.unsetExpectingTerminator();
	}

	private void discardXMLComment( final char ch ) {
		if ( ch == '!' ) {
			if ( this.cucharin.isNextChar( '-' ) ) {
				//	This section discards XML comments.
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
				//	This section discards the DTD compopnents. This is an 
				//	optional extension of the MinXSON language designed to make
				//	importing from XML less onerous.
				for (;;) {
					final char nch = this.nextChar();
					if ( nch == '>' ) break;
					if ( nch == '<' ) this.discardXMLComment( this.nextChar() );
				}
			}
		} else {
			//	This is responsible for consuming the Prolog <?xml version=.... ?>.
			//	Also processing instructions: <? PITarget .... ?>.  This is an 
			//	optional extension of the MinXSON language designed to make
			//	importing from XML less onerous.
			this.eatUpTo( '>' );
		}
	}

	
	static boolean charEndsAttributes( final char c ) {
		return c == '/' || c == '>' || c == '[' || c == '{' || c == '(';
	}

	private void readExtraAttributes( final String initial_key ) {
		String initial_value = this.readAttributeValue();
		this.extra_attributes.put( initial_key, initial_value );
		for (;;) {
			this.eatWhiteSpace();
			final char c = peekChar();
			if ( charEndsAttributes( c ) ) {
				break;
			}
			String key = this.readName();
			this.eatWhiteSpace();
			this.mustReadChar( '=' );
			this.eatWhiteSpace();
			final String value = readAttributeValue();
			this.extra_attributes.put(  key,  value  );
		}
	}
	
	private void readEndTag() {
		if ( this.isInElement() ) {
			this.discardChar();
			this.eatWhiteSpace();
			if ( this.tryReadChar( '>' ) ) {
				this.endTag( this.popTag( '\0' ) );
			} else {
				String end_tag = this.readName();
				this.eatWhiteSpace();
				this.mustReadChar( '>' );
				this.mustPopTag( end_tag );
				this.endTag( end_tag );
			}
			if ( this.isntAtTopLevel() ) {
				this.setExpectingTerminator( true );
			}
			return;
		} else {
			throw new Alert( "Unexpected end-tag" ).note( "Not inside an element" );
		}		
	}
	
	public void readNamelessStartTag( String name ) {
		//	We have attributes without an element name.
		//	The strategy is to read the attributes without processing them
		//	and then allow the next item to be processed as normal.
		this.readExtraAttributes( name );
		this.eatWhiteSpace();
		final char nch = nextChar();
		if ( ( nch == '[' ) || ( nch == '{' ) ) {
			final String tag = nch == '[' ? json_keys.ARRAY : json_keys.OBJECT;
			this.startTagOpen( tag );
			this.startTagClose( tag );
			this.pushTag( tag, nch == '[' ? ']' : '}', nch == '[' ? Context.InEmbeddedArray : Context.InEmbeddedObject );
			return;
		} else if ( nch == '(' ) {
			this.pushTag( "()", ')', Context.InEmbeddedParentheses );
			return;
		} else {
			throw new Alert( "Missing element name" );
		}
	}	
	
	private void normalStartTag( final String name ) {
		//	This section is a normal start/standalone tag.
		this.startTagOpen( name );
		
		this.processAttributes();
		this.startTagClose( name );
		
		this.eatWhiteSpace();
		final char nch = nextChar();
		if ( nch == '/' ) {
			//	It was a standalone tag.
			this.mustReadChar( '>' );
			this.pushPendingTag( name, '\0', Context.InElement );
		} else if ( nch == '>' ) {
			//	It was a start tag.
			this.pushTag( name, '\0', Context.InElement );
		} else if ( this.EMBEDDED_EXTENSION && nch == '[' ) {
			//	It is a standalone tag with embedded children.
			this.pushTag( name, ']', Context.InEmbeddedArray );
		} else if ( this.EMBEDDED_EXTENSION && nch == '{' ) {
			//	It is a standalone tag with embedded pairs.
			this.pushTag( name, '}', Context.InEmbeddedObject );
		} else {
			throw new Alert( "Invalid continuation" );
		}
	}

	
	private void readStartTag() {
		final String name = this.readName();
		this.eatWhiteSpace();
		if ( this.EMBEDDED_EXTENSION ) {
			if ( this.tryReadChar( '=' ) ) {
				readNamelessStartTag( name );
			} else if ( name.isEmpty() && this.tryReadChar( '[' ) ) {
				this.startTagOpen( json_keys.ARRAY );
				this.startTagClose( json_keys.ARRAY );
				this.pushTag( json_keys.ARRAY, ']', Context.InEmbeddedArray );
			} else if ( name.isEmpty() && this.tryReadChar( '{' ) ) {
				this.startTagOpen( json_keys.OBJECT );
				this.startTagClose( json_keys.OBJECT );
				this.pushTag( json_keys.OBJECT, '}', Context.InEmbeddedObject );
			} else if ( name.isEmpty() && this.tryReadChar( '(' ) ) {
				this.pushTag( "()", ')', Context.InEmbeddedParentheses );
			} else {
				this.normalStartTag( name );
			} 
		} else {
			this.normalStartTag( name );
		}
	}
	
	private void readTag() {
		this.discardChar();	//	Throw away leading <.
		this.eatWhiteSpace();
		final char ch = this.peekChar();
		if ( ch == '/' ) {
			this.readEndTag();
		} else if ( ch == '!' || ch == '?' ) {
			this.discardChar();
			this.discardXMLComment( ch );
			this.readOneTag();
			return;
		} else {
			this.readStartTag();
		}
	}
	
	void parseConstant( final String sofar, final String type ) {
		this.startTagOpen( json_keys.CONSTANT );
		this.put( json_keys.CONSTANT_TYPE, type );
		this.put( json_keys.CONSTANT_VALUE, sofar );
		this.startTagClose( json_keys.CONSTANT );
		this.pushPendingTag( json_keys.CONSTANT, '\0', Context.InAtom );
	}
	
	void readNumber() {
		boolean is_floating_point = false;
		StringBuilder sofar = new StringBuilder();
		boolean done = false;
		do {
			final char ch = this.peekChar( ' ' );
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
			this.discardChar();
		} while ( ! done );
		
		//	We have slightly different tests for floating point versus integers.
		final String s = sofar.toString();
		try {
			if ( is_floating_point ) {
				Double.parseDouble( s );
			} else {
				new BigInteger( s );
			}
			this.parseConstant( s, is_floating_point ? json_keys.FLOAT : json_keys.INTEGER );	
		} catch ( NumberFormatException e ) {
			throw new Alert( "Malformed number" ).culprit( "Bad number", sofar );
		}
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
	

	void readJSONString() {
		final String s = readJSONStringText();
		if ( 
			this.isInObject() && 
			( ! this.extra_attributes.containsKey( json_keys.FIELD ) )  
		) {
			this.eatWhiteSpace();
			this.mustReadChar( FIELD_ATTRIBUTE_SUFFIX );
			this.extra_attributes.put( json_keys.FIELD, s );
			this.readOneTag();
		} else {
			this.parseConstant( s, json_keys.STRING );
		}
	}

	public String readJSONStringText() {
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
	
	static boolean isStartOfNumber( final char ch ) {
		return Character.isDigit( ch ) || ch == '-' || ch == '+';
	}
	
	static boolean isIdentifierStart( final char ch ) {
		return Character.isLetter( ch ) || ch == '_';
	}
	
	static boolean isIdentifierContinuation( final char ch ) {
		return isNameChar( ch );
	}

	void parseId( final String sofar ) {
		this.startTagOpen( json_keys.ID );
		this.put( json_keys.ID_NAME, sofar );
		this.startTagClose( json_keys.ID );
		this.pushPendingTag( json_keys.ID, '\0', Context.InAtom );
	}
	
	void readIdentifier() {
		final String identifier = readIdentiferText();
		if ( this.isInObject() && ! this.extra_attributes.containsKey( json_keys.FIELD ) ) {
			this.eatWhiteSpace();
			this.mustReadChar( FIELD_ATTRIBUTE_SUFFIX );
			this.extra_attributes.put( json_keys.FIELD, identifier );
			this.readOneTag();
		} else if ( "true".equals( identifier ) || "false".equals( identifier ) ) {
			this.parseConstant( identifier, json_keys.BOOLEAN );
		} else if ( identifier.equals( "null" ) ) {
			this.parseConstant( identifier, json_keys.NULLEAN );
		} else {
			parseId( identifier );
		}
	}

	public String readIdentiferText() {
		StringBuilder sofar = new StringBuilder();
		for (;;) {
			final char ch = this.peekChar( ' ' );	// A character that's not part of an identifier.
			if ( isIdentifierContinuation( ch ) ) {
				this.discardChar();
				sofar.append( ch );
			} else {
				break;
			}
		}
		return sofar.toString();
	}	
	
	private void readArray() {
		this.discardChar();
		this.startTagOpen( json_keys.ARRAY );
		this.startTagClose( json_keys.ARRAY );
		this.pushTag( json_keys.ARRAY, ']', Context.InArray );
	}
	
	private void readObject() {
		this.discardChar();
		this.startTagOpen( json_keys.OBJECT );
		this.startTagClose( json_keys.OBJECT );
		this.pushTag( json_keys.OBJECT, '}', Context.InObject );
	}
	
	void readTypeTag() {
		final boolean is_string = this.cucharin.isNextChar( '"' ) || this.cucharin.isNextChar( '\'' );
		final String name = is_string ? this.readJSONStringText() : this.readName();
		this.extra_attributes.put( json_keys.TYPE, name );
		this.readWithoutPending();
	}

	private void readWithoutPending() {
		this.eatWhiteSpace();
		if ( ! this.cucharin.hasNextChar() ) return;
		final char ch = this.peekChar();
		if ( ch == '<' ) {
			this.readTag();
		} else if ( isStartOfNumber( ch ) ) {
			this.readNumber();
		} else if ( ch == '"' || ch == '\'' ) {
			this.readJSONString();
		} else if ( isIdentifierStart( ch ) ) {
			this.readIdentifier();
		} else if ( ch == '[' ) {
			this.readArray();
		} else if ( ch == '{' ) {
			this.readObject();
		} else if ( ch == '}' || ch == ']' ) {
			this.discardChar();
			final boolean was_in_element = this.isInEmbeddedContainer();
			if ( was_in_element ) {
				this.eatWhiteSpace();
				this.mustReadChar( '/' );
				this.mustReadChar( '>' );
			}
			this.endTag( this.popTag( ch ) );
			if ( this.isntAtTopLevel() ) {
				this.setExpectingTerminator( was_in_element );
			}
		} else if ( ch == ')'  ) {
			final boolean was_in_element = this.isInParentheses();
			if ( was_in_element ) {
				this.discardChar();
				this.eatWhiteSpace();
				this.mustReadChar( '/' );
				this.mustReadChar( '>' );
				this.dropTag();
				if ( this.isntAtTopLevel() ) {
					this.setExpectingTerminator( was_in_element );
				}
			}
		} else if ( this.TYPE_PREFIX_EXTENSION  && this.tryReadString( TYPE_ATTRIBUTE_PREFIX ) ) {
			this.readTypeTag();
		} else {
			throw new Alert( "Unexpected character" ).hint( "At the start of an item" ).culprit( "Character", ch );
		}		
	}
	
	/**
	 * Reads a MinXSON expression off the input and returns a MinXML object.
	 * 
	 * @return a MinXML object or null if the end of input has been reached.
	 */
	public MinXML read() { 
		while ( this.readOneTag() ) {
			if ( this.isAtTopLevel() ) break;
		}
		if ( ! this.isAtTopLevel() ) {
			throw new Alert( "Unexpected end of input" );
		}
		return parent.build();
	}

	/**
	 * Consume all whitespace, noting if there are any newlines. If we cannot find a 
	 * separator character then we accept a newline as a valid substitute by injecting
	 * a separator.
	 */
	private void injectSeparatorIfNeeded() {
		boolean seen_newline = false;
		while ( this.cucharin.hasNextChar() ) {
			final char ch = this.cucharin.nextChar();
			if ( ch == '\n' ) {
				seen_newline = true;
			} else if ( ! Character.isWhitespace( ch ) ) {
				this.cucharin.pushChar( ch );
				if ( seen_newline && ( ch != ',' && ch != ';' ) ) {
					this.cucharin.pushChar( ';' );
				}
				break;
			}
		}
	}
	
	public MinXML readBindings() {
		this.startTagOpen( json_keys.OBJECT );
		this.startTagClose( json_keys.OBJECT );
		this.pushTag( new Level( json_keys.OBJECT, '}', Context.InObject ) );
		
		while ( this.readOneTag() ) {
			if ( this.isAtLevel( 1 ) ) {
				this.injectSeparatorIfNeeded();
			}
		}
		
		this.endTag( json_keys.OBJECT );
		return parent.build();
	}
	
	/**
	 * This method returns an iterator which consumes the input
	 * stream and yields MinXML trees.
	 */
	public Iterator< MinXML > iterator() {
		return new Iterator< MinXML >() {

			@Override
			public boolean hasNext() {
				return MinXSONParser.this.hasNextExpression();
			}

			@Override
			public MinXML next() {
				return MinXSONParser.this.read();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}

}
