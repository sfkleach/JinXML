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
package com.steelypip.powerups.jinxml.implementation;

import java.io.Reader;
import java.util.ArrayDeque;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.charrepeater.CharRepeater;
import com.steelypip.powerups.charrepeater.ReaderCharRepeater;
import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.jinxml.Event;
import com.steelypip.powerups.jinxml.EventHandler;

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
public class PrototypeParser extends InputStreamProcessor implements LevelTracker  {
	
	private final CharRepeater cucharin;
	
	private String tag_name = null;	
	
	public PrototypeParser( CharRepeater rep ) {
		this.cucharin = rep;
	}

	public PrototypeParser( Reader reader ) {
		this.cucharin = new ReaderCharRepeater( reader );
	}
	
	/*************************************************************************
	* Implementation for the mixin LevelTracker
	*************************************************************************/
	
	final ArrayDeque< Context > contexts_implementation = new ArrayDeque<>();
	@Override

	public ArrayDeque< Context > contexts() {
		return this.contexts_implementation;
	}
	
	
	/*************************************************************************
	* Helper class for tracking field info
	*************************************************************************/

	static class SelectorInfo extends StdPair< String, Boolean > {
		
		public String name;
		public boolean solo;
		
		public SelectorInfo( String name, boolean solo ) {
			super( name, solo );
		}
		
		static @NonNull SelectorInfo DEFAULT = new SelectorInfo( "", false );
		
	}
	
	/*************************************************************************
	* Implementation for the character stream processing
	*************************************************************************/

	public CharRepeater cucharin() {
		return this.cucharin;
	}
	

	/*************************************************************************
	* Main Section
	*************************************************************************/

	
	private void processAttributes( EventHandler handler ) {
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
			handler.attributeEvent( key, value, repeat_ok );
		}
	}

	private boolean readString( EventHandler handler, final SelectorInfo selectorInfo ) {
		handler.stringEvent( selectorInfo.name, this.gatherString() );
		return true;
	}
	
	private String gatherNumber() {
		return new NumParser( this ).process();			
	}

	private boolean readNumber( EventHandler handler, final SelectorInfo selectorInfo) {
		final String number = this.gatherNumber();
		// TODO: this is clumsy in the extreme. Restructure.
		if ( ! number.matches( "[-+]?[0-9]+" ) ) {
			handler.floatEvent( selectorInfo.name, number );
		} else {
			handler.intEvent( selectorInfo.name, number );
		}
		return true;
	}
	
	// Can only be invoked in a context where there is no explicit selector.
	private boolean handleStringOrIdentifier( EventHandler handler, final boolean is_identifier, final String x ) {
		return is_identifier ? this.handleIdentifier( handler, SelectorInfo.DEFAULT, x ) : this.handleString( handler, SelectorInfo.DEFAULT, x );
	}

	private boolean handleIdentifier( EventHandler handler, SelectorInfo selectorInfo, String identifier ) {
		switch ( identifier ) {
		case "null":
			handler.nullEvent( selectorInfo.name, identifier );
			return true;
		case "true":
		case "false":
			handler.booleanEvent( selectorInfo.name, identifier );
			return true;
		default:
			throw new Alert( "Unrecognised identifier" ).culprit( "Identifier", identifier );
		}
	}
	
	private boolean handleString( EventHandler handler, SelectorInfo selectorInfo, String s ) {
		handler.stringEvent( selectorInfo.name, s );
		return true;
	}


	private boolean readCoreTag( EventHandler handler, @NonNull SelectorInfo selectorInfo ) {
		if ( this.tryReadChar( '[' ) ) {
			this.pushArray();
			handler.startArrayEvent( selectorInfo.name );
			return true;
		} else if ( this.tryReadChar( '{' ) ) {
			this.pushObject();
			handler.startObjectEvent( selectorInfo.name );
			return true;
		} else if ( this.tryReadChar( '<' ) ) {
			
			// Deal with the situation that this is an end-tag.
			char ch = this.nextChar();
			if ( ch == '/' ) {
				this.eatWhiteSpace();
				if ( this.tryReadChar( '>' ) ) {
					handler.endTagEvent( null );
				} else {
					final String end_tag = this.gatherName();
					this.processAttributes( handler );
					this.eatWhiteSpace();
					this.mustReadChar( '>' );
					handler.endTagEvent( end_tag );
				}
				this.popElement();
				return true;
			} else if ( ch == '!' || ch == '?' ) {
				this.eatComment( ch  );
				return this.readNextTag( handler, selectorInfo );
			} else {
				this.cucharin.pushChar( ch );
			}
			
			// This is not an end-tag but a start-tag.
			this.eatWhiteSpace();
			this.tag_name = this.gatherNameOrQuotedName();
			
			handler.startTagEvent( selectorInfo.name, this.tag_name );
			this.processAttributes( handler );
			
			this.eatWhiteSpace();					
			ch = nextChar();
			if ( ch == '/' ) {
				//	This is a standalone tag.
				this.mustReadChar( '>' );
				handler.endTagEvent( this.tag_name );
				this.popElement();
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

	
	private boolean readLabelledTagOrSymbol( EventHandler handler, final boolean is_identifier, final String name ) {
		this.eatWhiteSpace();
		boolean plus = this.tryReadChar( '+' );
		boolean colon = this.tryReadChar( ':' );
		if ( colon ) {
			boolean solo = !plus;
			this.eatWhiteSpace();
			return readUnlabelledTag( handler, new SelectorInfo( name, solo ) );
		} else if ( plus ) {
			//	:+ is not allowed - we want to raise the alarm.
			this.mustReadChar( ':' ); 	//	This will throw an exception (the one we want).
			throw Alert.unreachable();	//	So compiler doesn't complain.
		} else {
			return handleStringOrIdentifier( handler, is_identifier, name );
		}
	}
	
	private boolean readUnlabelledTag( EventHandler handler, @NonNull SelectorInfo selectorInfo ) {
		final char pch = this.peekChar( '\0' );
		if ( pch == '<' || pch == '[' || pch == '{' ) {
			return this.readCoreTag( handler, selectorInfo );
		} else if ( Character.isDigit( pch ) || pch == '+' || pch == '-' ) {
			return this.readNumber( handler, selectorInfo );
		} else if ( pch == '"' || pch == '\'' ) {
			return this.readString( handler, selectorInfo );
		} else if ( pch == ']' ) {
			this.mustReadChar( ']' );
			this.popArray();
			handler.endArrayEvent();
			return true;
		} else if ( pch == '}' ) {
			this.mustReadChar( '}' );
			this.popObject();
			handler.endObjectEvent();
			return true;
		} else if ( Character.isLetter( pch ) ) {
			return this.handleIdentifier( handler, selectorInfo, this.gatherName() );
		} else {
			throw new Alert( "Unexpected character while reading tag or constant" ).culprit( "Character", pch );
		}
	}
	
	/**
	 * This is the core routine of the algorithm, which consumes a single tag from the
	 * input stream. Standalone tags are expanded internally into separate open and close
	 * tags.
	 * @param selectorInfo null if no selector has been read yet, otherwise the selector info.
	 * @return true if it read a tag, false at end of stream.
	 */
	boolean readNextTag( EventHandler handler, SelectorInfo selectorInfo ) {
		this.eatWhiteSpaceIncludingOneComma();
		if ( !this.cucharin.hasNextChar() ) {
			return false;
		} else {	
			final char pch = this.peekChar( '\0' );
			if ( Character.isLetter( pch ) && selectorInfo == null ) {
				return readLabelledTagOrSymbol( handler, true, this.gatherName() );
			} else if ( ( pch == '"' || pch == '\'' ) && selectorInfo == null ) {
				return readLabelledTagOrSymbol( handler, false, this.gatherString() );
			} else {
				return readUnlabelledTag( handler, SelectorInfo.DEFAULT );
			}
		}
	}
	
	boolean readNextTag( EventHandler handler ) {
		return this.readNextTag( handler, null );
	}

	
//	/**
//	 * Read an element off the input stream or null if the stream is
//	 * exhausted.
//	 * @return the next element
//	 */
//	public Fusion readElement() {
//		while ( this.readNextTag( true, "", true ) ) {
//			if ( this.isAtTopLevel() ) break;
//		}
//		if ( ! this.isAtTopLevel() ) {
//			throw new Alert( "Unmatched tags due to encountering end of input" );
//		}
//		return handler.build();
//	}
//	
//	/**
//	 * Returns an iterator that reads elements off sequentially
//	 * from this parser.
//	 */
//	public Iterator< Fusion > iterator() {
//		return new Iterator< Fusion >() {
//
//			@Override
//			public boolean hasNext() {
//				return FusionParser.this.hasNext();
//			}
//
//			@Override
//			public Fusion next() {
//				return FusionParser.this.readElement();
//			}
//
//			@Override
//			public void remove() {
//				throw new UnsupportedOperationException();
//			}
//			
//		};
//	}

}
