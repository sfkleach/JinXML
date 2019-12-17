package com.steelypip.powerups.jinxml.stdparse;

import java.util.ArrayDeque;

import org.eclipse.jdt.annotation.NonNull;

import static com.steelypip.powerups.jinxml.Element.*;

public class LevelTracker {
	
	static abstract class Context {
		public abstract void check( Context actual_context );
		public abstract String summary();
	}
	
	static class TagContext extends Context {
		private String category;
		
		public TagContext( @NonNull String category ) {
			super();
			this.category = category;
		}
		
		public String summary() {
			if ( this.category == null ) {
				return "anonymous closing tag";
			} else {
				return String.format( "closing tag '%s'", this.category );
			}
		}

		public void check( Context actual_context ) {
			if ( actual_context instanceof TagContext ) {
				String actual_category = ((TagContext)actual_context).category;
				if ( actual_category== null || this.category.equals( actual_category ) ) return;
			}
			throw (
				new IllegalStateException( 
					String.format( 
						"Expecting closing tag '%s' but found %s", 
						this.category,
						actual_context.summary()
					) 
				)  
			);
		}
	}
	
	static class LetBindingContext extends Context {
		public void check( Context actual_context ) {
			if ( actual_context instanceof LetBindingContext ) return;
			throw (
				new IllegalStateException( 
					String.format( 
						"Expecting keyword 'in' as part of let-binding but found {}", 
						actual_context.summary()
					) 
				)  
			);			
		}
		public String summary() {
			return "keyword in";
		}
	}
	static LetBindingContext LET_BINDING_CONTEXT = new LetBindingContext();
	
	static class LetBodyContext extends Context {
		public void check( Context actual_context ) {
			if ( actual_context instanceof LetBodyContext ) return;
			throw (
				new IllegalStateException( 
					String.format( 
						"Expecting end of let-expression but found %s", 
						actual_context.summary()
					) 
				)  
			);
		}
		public String summary() {
			return "end of let-expression";
		}
	}
	static LetBodyContext LET_BODY_CONTEXT = new LetBodyContext();
	
	final private ArrayDeque< Context > contexts_implementation = new ArrayDeque<>();

	public boolean isAtTopLevel() {
		return this.contexts_implementation.isEmpty();
	}
	
	public void pop( String actualCategory ) {
		Context expecting = this.contexts_implementation.removeLast();
		expecting.check( new TagContext( actualCategory ) );	
	}
	
	public void popElement( String tag ) {
		pop( tag );
	}
	
	public void pushElement( String tag ) {
		this.contexts_implementation.addLast( new TagContext( tag ) );
	}
	
	public void popArray() {
		pop( ARRAY_ELEMENT_NAME );
	}
	
	public void pushArray() {
		this.contexts_implementation.addLast( new TagContext( ARRAY_ELEMENT_NAME ) );
	}
	
	public void popObject() {
		pop( OBJECT_ELEMENT_NAME );
	}
	
	public void pushObject() {
		this.contexts_implementation.addLast( new TagContext( OBJECT_ELEMENT_NAME ) );
	}
		
	public void pushLetBindings() {
		this.contexts_implementation.addLast( LET_BINDING_CONTEXT );		
	}
	
	public void popLetBindings() {
		Context expecting = this.contexts_implementation.removeLast();
		expecting.check( LET_BINDING_CONTEXT );	
	}
	
	public void pushLetBody() {
		this.contexts_implementation.addLast( LET_BODY_CONTEXT );		
	}
	
	public void popLetBody() {
		Context expecting = this.contexts_implementation.removeLast();
		expecting.check( LET_BODY_CONTEXT );	
	}
	
}
