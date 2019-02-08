package com.steelypip.powerups.jinxml.implementation;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.jinxml.Builder;
import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.FlexiElement;

public class StdBuilder implements Builder {
	
	final static String ROOT_ELEMENT_NAME = "";
	final static String ROOT_CHILD_SELECTOR = "";
	final static String DEFAULT_SELECTOR = "";

	/**
	 * The root element holds onto the entire in-progress tree. It is never exposed.
	 * As elements are parsed from the input, they become a queue of children held by
	 * the root (all of which have the selector ROOT_CHILD_SELECTOR).
	 */
	protected Element root = new FlexiElement( ROOT_ELEMENT_NAME ); 

	
	/**
	 * The dump is a record of the enclosing context. Tags and entry-selectors are
	 * regarded as akin to nested parentheses and brackets - they have to match. Open-tags
	 * are represented by the element that they start and open-entries are represented by
	 * the selector-string.
	 */
	protected ArrayDeque< Object > dump = new ArrayDeque<>();
	
	// 	These two variables represent the current context. The selector is always defined
	//	to be the default to be used for new members added to the 
	protected String selector = ROOT_CHILD_SELECTOR;	
	protected Element focus = this.root;
	
//	private void pushSelector( String x ) {
//		this.dump.addLast( this.selector );
//		this.selector = x;
//	}
//
//	private void popSelector() {
//		try {
//			this.selector = (String)this.dump.removeLast();
//		} catch ( ClassCastException e ) {
//			throw new IllegalStateException();
//		}
//	}

	private void pushChild( Element x ) {
		this.dump.addLast( this.focus );
		this.dump.addLast( this.selector );
		this.selector = DEFAULT_SELECTOR;
		this.focus = x;
	}

	private void popChild() {
		try {
			this.selector = (String)this.dump.removeLast();
			this.focus = (Element)this.dump.removeLast();
		} catch ( NoSuchElementException e ) {
			throw new IllegalStateException( e );
		} catch (ClassCastException e ) {
			throw new IllegalStateException( e );
		}
	}

	@Override
	public Void startTagEvent( String selector, @NonNull String key ) {
		//	TODO: Check context
		final Element new_child = new FlexiElement( key );
		this.focus.addLastChild( selector, new_child );
		this.pushChild( new_child );
		return null;
	}

	@Override
	public Void attributeEvent( @NonNull String key, String value, boolean solo ) {
		//	TODO: use solo
		//	TODO: deal with non-nullity
		if ( this.dump.isEmpty() ) {
			throw new IllegalStateException( "Trying to add attributes outside of a tag" );
		} else {
			this.focus.addLastValue( key, value );
		}
		return null;
	}

	@Override
	public Void endTagEvent( String key ) {
		//	TODO: check balance, check match
		if ( key == null || this.focus.hasName( key ) ) {
			this.popChild();
		} else {
			throw new IllegalArgumentException( "Mismatched tag names: " + this.focus.getName() + " and " + key );
		}
		return null;
	}

	@Override
	public Void startArrayEvent( String selector ) {
		//	TODO: check state, set state.
		this.startTagEvent( selector, "array" );
		return null;
	}

	@Override
	public Void endArrayEvent() {
		// 	TODO: check state, set state.
		this.endTagEvent( "array" );
		return null;
	}

	@Override
	public Void startObjectEvent( String selector ) {
		//	TODO: check state, set state.
		this.startTagEvent( selector, "object" );
		return null;
	}

//	@Override
//	public Void startEntryEvent( String key, Boolean solo ) {
//		//	TODO: check state, set state.
//		//	TODO: sort out nullability
//		//	TODO: solo
//		if ( this.dump.isEmpty() ) {
//			throw new IllegalStateException( "startEntryEvent: Used outside of a pair of start/end tags" );
//		}
//		this.pushSelector( key );
//		return null;
//	}
//
//	@Override
//	public Void endEntryEvent() {
//		if ( this.dump.isEmpty() ) {
//			throw new IllegalStateException( "endEntryEvent: Used outside of a pair of start/end tags" );
//		}
//		this.popSelector();
//		return null;
//	}

	@Override
	public Void endObjectEvent() {
		this.endTagEvent( "object" );
		return null;
	}

	@Override
	public Void intEvent( String selector, String value ) {
		this.startTagEvent( selector, "int" );
		this.attributeEvent( "value", value );
		this.endTagEvent( "int" );
		return null;
	}

	@Override
	public Void floatEvent( String selector, String value ) {
		this.startTagEvent( selector,"float" );
		this.attributeEvent( "value", value );
		this.endTagEvent( "float" );
		return null;
	}

	@Override
	public Void stringEvent( String selector, String value ) {
		this.startTagEvent( selector, "string" );
		this.attributeEvent( "value", value );
		this.endTagEvent( "string" );
		return null;
	}

	@Override
	public Void booleanEvent( String selector, String value ) {
		this.startTagEvent( selector,"boolean" );
		this.attributeEvent( "value", value );
		this.endTagEvent( "boolean" );
		return null;
	}

	@Override
	public Void nullEvent( String selector, String value ) {
		this.startTagEvent( selector, "null" );
		this.attributeEvent( "value", value );
		this.endTagEvent( "null" );
		return null;
	}

	@Override
	public boolean hasNext() {
		final int n = this.root.countChildren();
		//	Adjust for in progress.
		return n > ( this.dump.isEmpty() ? 0 : 1 );
	}

	@Override
	public boolean isInProgress() {
		return ! this.dump.isEmpty();
	}

	@Override
	public Element next() {
		Element e = this.root.getFirstChild();
		if ( e == null ) {
			throw new IllegalStateException( "No next element available" );
		} else {
			this.root.removeFirstChild( "", null ); 
			return e;
		}
	}

	@Override
	public Element tryNext( Element otherwise ) {
		if ( this.hasNext() ) {
			return this.next();
		} else {
			return otherwise;
		}
	}

	@Override
	public Element snapshot() {
		final Element e = root.getFirstChild();
		if ( e != null ) {
			return e.deepMutableCopy();
		} else {
			throw new IllegalStateException( "No events processed, cannot take a snapshot" );
		}
	}

	@Override
	public Element trySnapshot( Element otherwise ) {
		final Element e = root.getFirstChild();
		return e != null ? e.deepMutableCopy() : otherwise;
	}

	@Override
	public void include( Element child, boolean checkMutability ) {
		// TODO: checkMutability
		this.focus.addLastChild( this.selector, child );
	}

}
