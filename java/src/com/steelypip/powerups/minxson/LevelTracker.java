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

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

import com.steelypip.powerups.alert.Alert;

class LevelTracker {
	/**
	 * The expecting_terminator flag tells us whether or not we should be
	 * checking for a separator/terminator character as our first activity.
	 */
	private boolean expecting_terminator = false;
	/**
	 * The was_in_element flag refines our expectation. If we are expecting
	 * a separator/terminator character, we need to know whether or not
	 * we're in the gap between two elements (i.e. between > and <). In that
	 * specific context, we'll permit the separator/terminator to be omitted.
	 */
	private boolean was_in_element = false;
	
	private boolean pending_end_tag = false;
	private ArrayDeque< Level > open_tags = new ArrayDeque< Level >();
	
	boolean isExpectingTerminator() {
		return this.expecting_terminator;
	}
	
	boolean wasInElement() {
		return this.expecting_terminator && this.was_in_element;
	}
	
	void setExpectingTerminator( final boolean was_in_element ) {
		this.expecting_terminator = true;
		this.was_in_element = was_in_element;
	}
	
	void unsetExpectingTerminator() {
		this.expecting_terminator = false;
		this.was_in_element = false;	//	Defensive.
	}
	
	boolean hasPendingTag() {
		return this.pending_end_tag;
	}

	boolean isAtTopLevel() {
		return this.open_tags.isEmpty();
	}
	
	boolean isAtLevel( final int n ) {
		return this.open_tags.size() == n;
	}

	boolean isntAtTopLevel() {
		return ! this.open_tags.isEmpty();
	}

	boolean hasExpected( char ch ) {
		return ! this.open_tags.isEmpty() && this.open_tags.getLast().hasExpected( ch );
	}

	Level topLevel() {
		try {
			return this.open_tags.getLast();
		} catch ( NoSuchElementException e ) {
			throw new Alert( "Internal error", e );
		}
	}
	
	void dropTag() {
		try {
			this.open_tags.removeLast();	
			this.pending_end_tag = false;
		} catch ( NoSuchElementException e ) {
			throw new Alert( "Internal error", e );
		}
	}

	String popTag( char actual ) {
		if ( actual != '\0' && this.topLevel().hasntExpected( actual ) ) {
			throw (
				new Alert( "Mismatched closing character" ).
				culprit( "Expected", this.topLevel().getExpected() ).
				culprit( "Actually", actual )
			);
		}
		return this.popTag();
	}

	String popTag() {
		final String t = this.topLevel().getTag();
		this.dropTag();
		return t;
	}

	void mustPopTag( String tag ) {
		if ( this.topLevel().hasntTag( tag ) ) {
			throw new Alert( "Closing tag does not match open tag" ).culprit( "Open tag was", this.open_tags.getLast().getTag() ).culprit( "Closing tag is", tag );
		}
		this.dropTag();		
	}

	void pushPendingTag( final String tag, final char expected, final Context context ) {
		this.open_tags.addLast( new Level( tag, expected, context ) );
		this.pending_end_tag = true;
	}

	void pushTag( final String tag, final char expected, final Context context ) {
		this.pushTag( new Level( tag, expected, context ) );
	}

	void pushTag( final Level level ) {
		this.open_tags.addLast( level );
		this.pending_end_tag = false;
	}

	boolean isInObject() {
		return ! this.open_tags.isEmpty() && this.open_tags.getLast().isInObject();
	}

	boolean isInElement() {
		return ! this.open_tags.isEmpty() && this.open_tags.getLast().isInElement();
	}

	boolean isInParentheses() {
		return ! this.open_tags.isEmpty() && this.open_tags.getLast().isInParentheses();
	}

	boolean isInEmbeddedContainer() {
		return ! this.open_tags.isEmpty() && this.open_tags.getLast().isInEmbeddedContainer();
	}
	
}