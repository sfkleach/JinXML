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
package com.steelypip.powerups.jinxml.stdparse;

import java.io.Reader;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.charrepeater.CharRepeater;
import com.steelypip.powerups.charrepeater.ReaderCharRepeater;
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
public class TagParser extends TokeniserBaseClass {
	
	private static final char SINGLE_QUOTE = '\'';
	private static final char DOUBLE_QUOTE = '"';
	private final CharRepeater cucharin;
	private boolean expandLiteralConstants;
	final private LevelTracker level_tracker = new LevelTracker();  
		
	public TagParser( CharRepeater rep, boolean expandLiteralConstants ) {
		this.cucharin = rep;
		this.expandLiteralConstants = expandLiteralConstants;
	}

	public TagParser( Reader reader, boolean expandLiteralConstants ) {
		this.cucharin = new ReaderCharRepeater( reader );
		this.expandLiteralConstants = expandLiteralConstants;
	}
	
	
	/*************************************************************************
	* Implementation for the character stream processing
	*************************************************************************/

	public CharRepeater cucharin() {
		return this.cucharin;
	}

	/*************************************************************************
	* Delgation to LevelTracker
	*************************************************************************/
	
	public boolean isAtTopLevel() {
		return level_tracker.isAtTopLevel();
	}
	
	/*************************************************************************
	* Handling literal constants
	*************************************************************************/
	
	private void sendLiteralConstant( EventHandler handler, @NonNull String selector, @NonNull String typeName, @NonNull String value ) {
		handler.startTagEvent( selector, typeName );
		handler.attributeEvent( "value", value );
		handler.endTagEvent();		
	}


	private void sendIntEvent( EventHandler handler, @NonNull String selector, @NonNull String value ) {
		if ( this.expandLiteralConstants ) {
			this.sendLiteralConstant( handler, selector, "int", value );
		} else {
			handler.intEvent( selector, value );
		}
	}
	
	private void sendFloatEvent( EventHandler handler, @NonNull String selector, @NonNull String value ) {
		if ( this.expandLiteralConstants ) {
			this.sendLiteralConstant( handler, selector, "float", value );
		} else {
			handler.floatEvent( selector, value );
		}
	}
	
	private void sendNullEvent( EventHandler handler, @NonNull String selector, @NonNull String identifier ) {
		if ( this.expandLiteralConstants ) {
			this.sendLiteralConstant( handler, selector, "null", identifier );
		} else {
			handler.nullEvent( selector, identifier );
		}
	}

	private void sendBooleanEvent( EventHandler handler, @NonNull String selector, @NonNull String value ) {
		if ( this.expandLiteralConstants ) {
			this.sendLiteralConstant( handler, selector, "boolean", value );
		} else {
			handler.booleanEvent( selector, value );
		}
	}
	
	private void sendStringEvent( EventHandler handler, @NonNull String selector, @NonNull String value ) {
		if ( this.expandLiteralConstants ) {
			this.sendLiteralConstant( handler, selector, "string", value );
		} else {
			handler.stringEvent( selector, value );
		}
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
			final String value = this.gatherString();
			handler.attributeEvent( key, value, repeat_ok );
		}
	}

	private boolean readString( EventHandler handler, final @NonNull SelectorInfo selectorInfo ) {
		handler.stringEvent( selectorInfo.getSelector( "string" ), this.gatherString() );
		return true;
	}
	
	private boolean readNumber( EventHandler handler, final @NonNull SelectorInfo selectorInfo) {
		NumParser np = new NumParser( this );
		final @NonNull String number = np.gatherNumber();
		if ( np.isFloatingPoint() ) {
			sendFloatEvent( handler, selectorInfo.getSelector(), number );
		} else {
			sendIntEvent( handler, selectorInfo.getSelector(), number );
		}
		return true;
	}
	
	// Can only be invoked in a context where there is no explicit selector.
	private boolean handleStringOrIdentifier( EventHandler handler, final boolean is_identifier, final @NonNull String x ) {
		return is_identifier ? this.handleIdentifier( handler, SelectorInfo.DEFAULT, x ) : this.handleString( handler, SelectorInfo.DEFAULT, x );
	}
	

	
	private boolean handleString( EventHandler handler, SelectorInfo selectorInfo, @NonNull String s ) {
		this.sendStringEvent( handler, selectorInfo.getSelector( "string" ), s );
		return true;
	}

	private boolean handleIdentifier( EventHandler handler, @NonNull SelectorInfo selectorInfo, String identifier ) {
		switch ( identifier ) {
		case "null":
			this.sendNullEvent( handler, selectorInfo.getSelector(), identifier );
			return true;
		case "true":
		case "false":
			this.sendBooleanEvent( handler, selectorInfo.getSelector(), identifier );
			return true;
		default:
			throw new Alert( "Unrecognised identifier" ).culprit( "Identifier", identifier );
		}
	}
	

	private boolean readCoreTag( EventHandler handler, @NonNull SelectorInfo selectorInfo ) {
		if ( this.tryReadChar( '[' ) ) {
			this.level_tracker.pushArray();
			handler.startArrayEvent( selectorInfo.getSelector() );
			return true;
		} else if ( this.tryReadChar( '{' ) ) {
			this.level_tracker.pushObject();
			handler.startObjectEvent( selectorInfo.getSelector() );
			return true;
		} else if ( this.tryReadChar( '<' ) ) {
			
			// Deal with the situation that this is an end-tag.
			char ch = this.nextChar();
			if ( ch == '/' ) {
				this.eatWhiteSpace();
				if ( this.tryReadChar( '&' ) ) {
					this.eatWhiteSpace();
					this.mustReadChar( '>' );
					handler.endTagEvent( null );
					this.level_tracker.popElement( null );
				} else {
					final String end_tag = this.gatherNonEmptyName();
					this.processAttributes( handler );
					this.eatWhiteSpace();
					this.mustReadChar( '>' );
					handler.endTagEvent( end_tag );
					this.level_tracker.popElement( end_tag );
				}
				return true;
			} else {
				this.cucharin.pushChar( ch );
			
				// This is not an end-tag but a start-tag.
				this.eatWhiteSpace();
				final @NonNull String tag = (
					this.tryReadChar( '&' ) ?
					selectorInfo.getSelector() :
					this.gatherNameOrQuotedName()
				);
				
				handler.startTagEvent( selectorInfo.getSelector( tag ), tag );
				this.processAttributes( handler );
				
				this.eatWhiteSpace();					
				ch = nextChar();
				if ( ch == '/' ) {
					//	This is a standalone tag.
					this.mustReadChar( '>' );
					handler.endTagEvent( tag );
					return true;
				} else if ( ch == '>' ) {
					this.level_tracker.pushElement( tag );
					return true;
				} else {
					throw new Alert( "Invalid continuation" );
				}
			}
		} else if ( !this.cucharin.hasNextChar() ) {
			throw new Alert( "Unexpected end of file" ).culprit( "Expecting", "< or [ or { " );
		} else {
			throw new Alert( "Unexpected character" ).culprit( "Expecting", "< or [ or { " ).culprit( "Actually", this.peekChar( '\0' ) ); 
		}  
	}

	
	private boolean readLabelledTagOrSymbol( EventHandler handler, final boolean is_identifier, final String name ) {
		this.eatWhiteSpace();
		boolean plus = this.tryReadChar( '+' );
		boolean colon = this.tryReadChar( ':' ) || this.tryReadChar( '=' );
		if ( colon ) {
			boolean solo = !plus;
			this.eatWhiteSpace();
			return readUnlabelledTag( handler, new SelectorInfo( name, solo ) );
		} else if ( plus ) {
			//	:+ is not allowed - we want to raise the alarm.
			this.mustReadChar( ':' ); 	//	This will throw an exception (the one we want).
			throw Alert.unreachable();	//	So compiler doesn't complain.
		} else if ( name != null ) {
			return handleStringOrIdentifier( handler, is_identifier, name );
		} else {
			throw new Alert( "'&' used on its own, like an identifier, but that is not allowed" );
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
			this.level_tracker.popArray();
			handler.endArrayEvent();
			return true;
		} else if ( pch == '}' ) {
			this.mustReadChar( '}' );
			this.level_tracker.popObject();
			handler.endObjectEvent();
			return true;
		} else if ( Character.isLetter( pch ) ) {
			return this.handleIdentifier( handler, selectorInfo, this.gatherNonEmptyName() );
		} else {
			throw new Alert( "Unexpected character while reading tag or constant" ).culprit( "Character", pch );
		}
	}
	
	private boolean atFileStart = true;
	
	/**
	 * This is the core routine of the algorithm, which consumes a single tag from the
	 * input stream. Standalone tags are expanded internally into separate open and close
	 * tags.
	 * @param selectorInfo null if no selector has been read yet, otherwise the selector info.
	 * @return true if it read a tag, false at end of stream.
	 */
	public boolean readNextTag( EventHandler handler, @Nullable SelectorInfo selectorInfo ) {
		if ( this.atFileStart ) {
			this.eatShebang();
			this.atFileStart = false;
		}
		this.eatWhiteSpaceIncludingOneComma();
		if ( !this.cucharin.hasNextChar() ) {
			return false;
		} else {	
			final char pch = this.peekChar( '\0' );
			if ( Character.isLetter( pch ) && selectorInfo == null ) {
				return readLabelledTagOrSymbol( handler, true, this.gatherNonEmptyName() );
			} else if ( pch == '&' ) {
				this.nextChar();
				return readLabelledTagOrSymbol( handler, true, null );
			} else if ( ( pch == DOUBLE_QUOTE || pch == SINGLE_QUOTE ) && selectorInfo == null ) {
				return readLabelledTagOrSymbol( handler, false, this.gatherString() );
			} else {
				return readUnlabelledTag( handler, SelectorInfo.DEFAULT );
			}
		}
	}
	
	public boolean readNextTag( EventHandler handler ) {
		return this.readNextTag( handler, null );
	}

}
