package com.steelypip.powerups.jinxml.stdmodel;

import static com.steelypip.powerups.jinxml.Element.ARRAY_ELEMENT_NAME;
import static com.steelypip.powerups.jinxml.Element.BOOLEAN_ELEMENT_NAME;
import static com.steelypip.powerups.jinxml.Element.FLOAT_ELEMENT_NAME;
import static com.steelypip.powerups.jinxml.Element.INT_ELEMENT_NAME;
import static com.steelypip.powerups.jinxml.Element.NULL_ELEMENT_NAME;
import static com.steelypip.powerups.jinxml.Element.OBJECT_ELEMENT_NAME;
import static com.steelypip.powerups.jinxml.Element.ROOT_SELECTOR;
import static com.steelypip.powerups.jinxml.Element.STRING_ELEMENT_NAME;
import static com.steelypip.powerups.jinxml.Element.VALUE_KEY_FOR_LITERAL_CONSTANTS;

import java.util.NoSuchElementException;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.jinxml.Builder;
import com.steelypip.powerups.jinxml.Element;

public class StdBuilder implements Builder {

	protected Focus.RootFocus root = new Focus.RootFocus();
	// 	This variables represent the current context.  
	protected Focus focus = root;
	//	This variable represents the list of ancestor focii.
	protected Trail trail = new Trail();
	
	protected boolean mustCopyResults = false;
	
	/**
	 * This flag dictates whether or not the items that are constructed will be mutable or not. 
	 */
	protected boolean mutable_flag;
	
	/**
	 * This flag dictates whether or not the builder can accept events after an instance is
	 * ready.
	 */
	protected boolean allow_queuing;
	
	public StdBuilder( final boolean mutable_flag, final boolean allow_queuing ) {
		this.mutable_flag = mutable_flag;
		this.allow_queuing = allow_queuing;
	}

	private void pushChild( Focus x ) {
		this.trail.addLast( this.focus );
		this.focus = x;
	}

	private void popChild() {
		Focus current = this.focus;
		try {
			this.focus = this.trail.removeLast();
			this.focus.addFocus( current );
		} catch ( NoSuchElementException e ) {
			throw new IllegalStateException( e );
		} catch (ClassCastException e ) {
			throw new IllegalStateException( e );
		}
	}

	@Override
	public void startTagEvent( @NonNull String selector, @NonNull String key ) {
		if ( this.trail.isEmpty() && ! this.allow_queuing && this.focus.hasRootChildren()  ) {
			throw new Alert( "Trying to add a second element to a builder that does not allow queuing" ).culprit( "First element", this.focus.getElement().getChild( ROOT_SELECTOR, 0 ) );
		}
		this.pushChild( new Focus.ElementFocus( selector, new FlexiElement( key ) ) );
	}

	@Override
	public void attributeEvent( @NonNull String key, @NonNull String value, boolean solo ) {
		if ( solo && this.focus.hasKey( key ) ) {
			throw new Alert( "Adding second attribute with same key but marked as solo" ).culprit( "Key", key );
		} else {
			this.focus.addLastValue( key, value );
		}
	}

	@Override
	public void endTagEvent( String key ) {
		if ( key == null || this.focus.hasName( key ) ) {
			this.popChild();
		} else {
			throw(
				new Alert( "Mismatched tag names: {Name} and {Key}" ).
				culprit( "Name", this.focus.getName() ).
				culprit( "Key", key ).
				wrap( IllegalArgumentException.class )
			);
		}
	}	

	@Override
	public void startLetEvent( @NonNull String selector ) {
		if ( this.trail.isEmpty() && ! this.allow_queuing && this.focus.hasRootChildren()  ) {
			throw new Alert( "Trying to add a second element to a builder that does not allow queuing" ).culprit( "First element", this.focus.getElement().getChild( ROOT_SELECTOR, 0 ) );
		}
		this.pushChild( new Focus.LetFocus( selector ) );
	}
	
	@Override
	public void inLetEvent() {
		this.focus.seenInKeyword();
	}

	@Override
	public void endLetEvent() {
		if ( this.focus.isLetFocus() ) {
			this.popChild();
		} else {
			throw new Alert( "Mismatched tag/element brackets" );
		}
	}
	
	private Element lookup( @NonNull String identifier ) {
		Element e = this.focus.lookup( identifier );
		if ( e != null ) return e;
		for ( Focus f : this.trail ) {
			Element x = f.lookup( identifier );
			if ( x != null ) return x;
		}
		return null;
	}
	
	@Override 
	public void identifierEvent( @NonNull String selector, @NonNull String identifier ) {
		Element e = this.lookup( identifier );
		if ( e == null ) throw new IllegalStateException( String.format( "Cannot resolve reference to %s", identifier ) );
		this.focus.addFocus( new Focus.ElementFocus( selector, e ) );
	}

	@Override
	public void startArrayEvent( @NonNull String selector ) {
		this.startTagEvent( selector, ARRAY_ELEMENT_NAME );
	}

	@Override
	public void endArrayEvent() {
		this.endTagEvent( ARRAY_ELEMENT_NAME );
	}

	@Override
	public void startObjectEvent( @NonNull String selector ) {
		this.startTagEvent( selector, OBJECT_ELEMENT_NAME );
	}

	@Override
	public void endObjectEvent() {
		this.endTagEvent( OBJECT_ELEMENT_NAME );
	}

	@Override
	public void intEvent( @NonNull String selector, @NonNull String value ) {
		this.startTagEvent( selector, INT_ELEMENT_NAME );
		this.attributeEvent( VALUE_KEY_FOR_LITERAL_CONSTANTS, value );
		this.endTagEvent( INT_ELEMENT_NAME );
	}

	@Override
	public void floatEvent( @NonNull String selector, @NonNull String value ) {
		this.startTagEvent( selector, FLOAT_ELEMENT_NAME );
		this.attributeEvent( VALUE_KEY_FOR_LITERAL_CONSTANTS, value );
		this.endTagEvent( FLOAT_ELEMENT_NAME );
	}

	@Override
	public void stringEvent( @NonNull String selector, @NonNull String value ) {
		this.startTagEvent( selector, STRING_ELEMENT_NAME );
		this.attributeEvent( VALUE_KEY_FOR_LITERAL_CONSTANTS, value );
		this.endTagEvent( STRING_ELEMENT_NAME );
	}

	@Override
	public void booleanEvent( @NonNull String selector, @NonNull String value ) {
		this.startTagEvent( selector, BOOLEAN_ELEMENT_NAME );
		this.attributeEvent( VALUE_KEY_FOR_LITERAL_CONSTANTS, value );
		this.endTagEvent( BOOLEAN_ELEMENT_NAME );
	}

	@Override
	public void nullEvent( @NonNull String selector, @NonNull String value ) {
		this.startTagEvent( selector, NULL_ELEMENT_NAME );
		this.attributeEvent( VALUE_KEY_FOR_LITERAL_CONSTANTS, value );
		this.endTagEvent( NULL_ELEMENT_NAME );
	}

	@Override
	public boolean hasNext() {
		return this.root.hasNextItem();
	}

	@Override
	public boolean isInProgress() {
		return ! this.trail.isEmpty();
	}

	@Override
	public Element next( boolean mutable ) {
		Element e = this.root.nextItem();
		if ( this.mustCopyResults ) {
			return mutable ? e.deepMutableCopy() : e.deepFreeze();
		} else if ( mutable ) {
			return e;
		} else {
			e.deepFreezeSelf();
			return e;
		}
	}

	@Override
	public Element next() {
		return this.next( this.mutable_flag );
	}

	@Override
	public Element tryNext( Element otherwise, boolean mutable ) {
		if ( this.hasNext() ) {
			return this.next( mutable );
		} else {
			return otherwise;
		}
	}

	@Override
	public Element tryNext( Element otherwise ) {
		return this.tryNext( otherwise, this.mutable_flag );
	}

	@Override
	public void include( @NonNull String selector, Element child, boolean checkMutability ) {
		if ( checkMutability ) {
			//	Counter-intuitive test: use == because the two sides have exactly opposite meanings.
			if ( this.mutable_flag == child.isFrozen() ) {
				throw (
					new Alert( "Failed check for mutability consistency" ).
					culprit( "Element to be included", child ).
					culprit( "Builder mutability", mutable_flag )
				);
			}
		}
		// 	Importantly: inclusion means that not all nodes are freshly built. That means we must
		//	copy the results to avoid inadvertant sharing with pre-existing store.
		this.mustCopyResults = true;
		this.pushChild( new Focus.ElementFocus( selector, child ) );
		this.popChild();
	}


}
