package com.steelypip.powerups.jinxml.implementation;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.jinxml.Builder;
import com.steelypip.powerups.jinxml.Element;

public class StdBuilder implements Builder {
	
	final static @NonNull String ROOT_ELEMENT_NAME = "";
	final static @NonNull String ROOT_CHILD_SELECTOR = "";
	final static @NonNull String DEFAULT_SELECTOR = "";

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
	protected @NonNull String selector = ROOT_CHILD_SELECTOR;	
	protected Element focus = this.root;

	private void pushChild( Element x ) {
		this.dump.addLast( this.focus );
		this.dump.addLast( this.selector );
		this.selector = DEFAULT_SELECTOR;
		this.focus = x;
	}

	private void popChild() {
		try {
			Object popped = this.dump.removeLast();
			this.selector = Objects.requireNonNull( (String)popped );
			this.focus = (Element)this.dump.removeLast();
		} catch ( NoSuchElementException e ) {
			throw new IllegalStateException( e );
		} catch (ClassCastException e ) {
			throw new IllegalStateException( e );
		}
	}

	@Override
	public void startTagEvent( @NonNull String selector, @NonNull String key ) {
		//	TODO: Check context
		final Element new_child = new FlexiElement( key );
		this.focus.addLastChild( selector, new_child );
		this.pushChild( new_child );
	}

	@Override
	public void attributeEvent( @NonNull String key, @NonNull String value, boolean solo ) {
		//	TODO: use solo
		//	TODO: deal with non-nullity
		if ( this.dump.isEmpty() ) {
			throw new IllegalStateException( "Trying to add attributes outside of a tag" );
		} else {
			this.focus.addLastValue( key, value );
		}
	}

	@Override
	public void endTagEvent( String key ) {
		//	TODO: check balance, check match
		if ( key == null || this.focus.hasName( key ) ) {
			this.popChild();
		} else {
			throw new IllegalArgumentException( "Mismatched tag names: " + this.focus.getName() + " and " + key );
		}
	}

	@Override
	public void startArrayEvent( @NonNull String selector ) {
		//	TODO: check state, set state.
		this.startTagEvent( selector, "array" );
	}

	@Override
	public void endArrayEvent() {
		// 	TODO: check state, set state.
		this.endTagEvent( "array" );
	}

	@Override
	public void startObjectEvent( @NonNull String selector ) {
		//	TODO: check state, set state.
		this.startTagEvent( selector, "object" );
	}


	@Override
	public void endObjectEvent() {
		this.endTagEvent( "object" );
	}

	@Override
	public void intEvent( @NonNull String selector, @NonNull String value ) {
		this.startTagEvent( selector, "int" );
		this.attributeEvent( "value", value );
		this.endTagEvent( "int" );
	}

	@Override
	public void floatEvent( @NonNull String selector, @NonNull String value ) {
		this.startTagEvent( selector,"float" );
		this.attributeEvent( "value", value );
		this.endTagEvent( "float" );
	}

	@Override
	public void stringEvent( @NonNull String selector, @NonNull String value ) {
		this.startTagEvent( selector, "string" );
		this.attributeEvent( "value", value );
		this.endTagEvent( "string" );
	}

	@Override
	public void booleanEvent( @NonNull String selector, @NonNull String value ) {
		this.startTagEvent( selector,"boolean" );
		this.attributeEvent( "value", value );
		this.endTagEvent( "boolean" );
	}

	@Override
	public void nullEvent( @NonNull String selector, @NonNull String value ) {
		this.startTagEvent( selector, "null" );
		this.attributeEvent( "value", value );
		this.endTagEvent( "null" );
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
