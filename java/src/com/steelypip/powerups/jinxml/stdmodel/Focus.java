package com.steelypip.powerups.jinxml.stdmodel;

import static com.steelypip.powerups.jinxml.Element.ROOT_ELEMENT_NAME;
import static com.steelypip.powerups.jinxml.Element.ROOT_SELECTOR;

import java.util.Map;
import java.util.TreeMap;

import com.steelypip.powerups.jinxml.Element;

public abstract class Focus {

	public abstract String getSelector();
	
	public abstract Element getElement();

	public abstract boolean hasRootChildren();

	public abstract void addFocus( Focus recent );

	public void addLastValue( String key, String value ) {
		throw new IllegalStateException( "Not inside tag but trying to key/value" );
	}
	public boolean hasKey( String key ) {
		throw new IllegalStateException( "Not inside tag but trying to checl key" );
	}
	public boolean hasName( String name ) {
		throw new IllegalStateException( "Not inside tag but trying to check the name" );
	}
	public String getName() {
		throw new IllegalStateException( "Not inside tag but trying to get the name" );
	}

	
	public Element lookup( String name ) { 
		return null;
	}
	
	public void seenInKeyword() {
		throw new IllegalStateException( "Trying to set seen-in but not inside a let-expression" );
	}
	
	public boolean isLetFocus() {
		return false;
	}
	
	public static class LetFocus extends Focus {
		protected String selector;
		boolean seenInKeyword = false;
		Map< String, Element > bindings = new TreeMap<>();
		Element value = null;
		
		public LetFocus( String selector ) {
			this.selector = selector;
		}		
		
		@Override
		public String getSelector() {
			return this.selector;
		}
		
		@Override 
		public void seenInKeyword() {
			this.seenInKeyword = true;
		}
		
		@Override
		public Element getElement() {
			if ( this.value != null ) return this.value;
			throw new IllegalStateException( "Let expression without a body" );
		}
		
		@Override
		public boolean hasRootChildren() {
			return false;
		}
		
		@Override
		public void addFocus( Focus recent ) { 
			if ( this.seenInKeyword ) {
				this.value = recent.getElement();
			} else {
				this.bindings.put( recent.getSelector(), recent.getElement() );
			}
		}

		@Override 
		public Element lookup( String name ) {
			return this.bindings.get( name );
		}

		@Override
		public boolean isLetFocus() {
			return true;
		}
	}
	
	public static class ElementFocus extends Focus {
		protected Element element;
		protected String selector;
		
		public ElementFocus( String selector, Element element ) {
			this.selector = selector;
			this.element = element;
		}
		
		@Override
		public String getSelector() {
			return this.selector;
		}
		
		@Override
		public Element getElement() {
			return this.element;
		}
		
		@Override
		public boolean hasRootChildren() {
			return this.element.countChildren( ROOT_SELECTOR ) > 0;
		}
		
		@Override
		public void addFocus( Focus recent ) {
			this.element.addLastChild( recent.getSelector(), recent.getElement() );
		}
		
		@Override
		public void addLastValue( String key, String value ) {
			this.element.addLastValue( key, value );
		}
		
		@Override
		public boolean hasKey( String key ) {
			return this.element.hasKey( key );
		}
		
		@Override
		public boolean hasName( String name ) {
			return this.element.hasName( name );
		}
		
		@Override
		public String getName() {
			return this.element.getName();
		}
	}

	
	public static class RootFocus extends Focus {
		protected Element root_element = new FlexiElement( ROOT_ELEMENT_NAME );
		
		public RootFocus() {
		}
		
		@Override
		public String getSelector() {
			throw new UnsupportedOperationException( "Trying to take selector of root element" );
		}
		
		@Override
		public Element getElement() {
			return this.root_element;
		}
		
		@Override
		public boolean hasRootChildren() {
			return this.root_element.countChildren( ROOT_SELECTOR ) > 0;
		}
		
		@Override
		public void addFocus( Focus recent ) {
			this.root_element.addLastChild( ROOT_SELECTOR, recent.getElement() );
		}
		
		public Element nextItem() {
			Element e = this.root_element.getFirstChild();
			if ( e == null ) {
				throw new IllegalStateException( "No next element available" );
			} else {
				this.root_element.removeFirstChild( ROOT_SELECTOR, null ); 
			}
			return e;
		}
		
		public boolean hasNextItem() {
			return this.root_element.hasAnyMembers();
		}
	}

}
