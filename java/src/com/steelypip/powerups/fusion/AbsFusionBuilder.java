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
package com.steelypip.powerups.fusion;

import java.util.LinkedList;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.alert.Alert;

/**
 * This implementation of MinXMLBuilder is maximally flexible:
 * <ul>
 *	<li>It accepts null element names in startTagOpen, startTagClose and endTag.
 *	<li>The startTagClose method may be omitted in the build sequence.
 *	<li>After build is called, the builder can be reused.
 * </ul>
 */
public abstract class AbsFusionBuilder implements FusionBuilder {
	
	public abstract FusionFactory factory();
	public abstract boolean isMakingMutable();
		
	static class PreLink {
		
		private String field;
		private @NonNull Fusion current;
		private Boolean allow_repeats;
		
		public PreLink( FusionFactory impl, String field, String name, Boolean allow_repeats ) {
			this( impl.newMutableElementFusion( name != null ? name : "" ), field, name, allow_repeats );
		}
		
		public PreLink( @NonNull Fusion x, String field, String name, Boolean allow_repeats ) {
			this.field = field;
			this.current = x;
			this.allow_repeats = allow_repeats;
		}
		
		public Boolean getAllowRepeats() {
			return this.allow_repeats;
		}
		
		public void setAllowRepeats( Boolean allow_repeats ) {
			this.allow_repeats = allow_repeats;
		}

		public String getField() {
			return field;
		}

		public void setField( String field ) {
			this.field = field;
		}

		public @NonNull Fusion getCurrent() {
			return current;
		}
		
		@SuppressWarnings("null")
		public @NonNull String getNonNullField() {
			return this.field != null ? this.field : "";
		}
		
	}
	
	private @NonNull PreLink current_link = new PreLink( this.factory(), "DUMMY FIELD", "DUMMY_NODE", null );
	private final LinkedList< @NonNull PreLink > link_stack = new LinkedList<>();
	//private final Conventions litf = new StdConventions();
	
	private @NonNull Fusion current() {
		return this.current_link.getCurrent();
	}

	@Override
	public void startTag( final String field, final String name, Boolean allow_repeats ) {
		link_stack.addLast( current_link );
		this.current_link = new PreLink( this.factory(), field, name, allow_repeats );
	}

	@Override
	public void startTag( final String field, final String name ) {
		link_stack.addLast( current_link );
		this.current_link = new PreLink( this.factory(), field, name, true );
	}

	@Override
	public void startTag( final String name ) {
		this.startTag( "", name );
	}

	@Override
	public void startTag() {
		this.startTag( null, null );
	}

	@Override
	public void add( @NonNull String key, @NonNull String value ) {
		this.current().addValue( key, value );
	}
	
	@Override
	public void addNew( @NonNull String key, @NonNull String value ) {
		if ( ! this.current().hasAttribute( key ) ) {
			this.current().addValue( key, value );
		} else {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public void addInteger( final long number ) {
		this.addChild( this.factory().newIntegerFusion( number ) );
	}
	
	@Override
	public void addInteger( final String field, final long number ) {
		this.addChild( field, this.factory().newIntegerFusion( number ) );
	}
	
	@Override
	public void addFloat( final double number ) {
		this.addChild( this.factory().newFloatFusion( number ) );
	}
	
	@Override
	public void addFloat( final String field, final double number ) {
		this.addChild( field, this.factory().newFloatFusion( number ) );
	}
	
	@Override
	public void addString( final @Nullable String string ) {
		if ( string == null ) {
			this.addNull();
		} else {
			this.addChild( this.factory().newStringFusion( string ) );
		}
	}
	
	@Override
	public void addString( final String field, final @Nullable String string ) {
		if ( string == null ) {
			this.addNull( field );
		} else {
			this.addChild( field, this.factory().newStringFusion( string ) );
		}
	}
	
	@Override
	public void addBoolean( final boolean bool ) {
		this.addChild( this.factory().newBooleanFusion( bool ) );
	}
	
	@Override
	public void addBoolean( final String field, final boolean bool ) {
		this.addChild( field, this.factory().newBooleanFusion( bool ) );
	}
	
	@Override 
	public void addChild( final @Nullable Fusion x ) {
		if ( x == null ) {
			this.addNull();
		} else {
			this.current_link.getCurrent().addChild( x );
		}
	}
	
	@Override 
	public void addChild( final String field, final @Nullable Fusion x ) {
		if ( x == null ) {
			this.addNull( field );
		} else {
			this.current_link.getCurrent().addChild( field, x );
		}
	}
	
	@Override
	public void addNull() {
		this.current_link.getCurrent().addChild( this.factory().newNullFusion() );
	}
	
	@Override
	public void addNull( final String field ) {
		this.current_link.getCurrent().addChild( field, this.factory().newNullFusion() );
	}
	
	@Override
	public void bindName( final @Nullable String name ) {
		if ( name != null ) {
			if ( this.current().hasName( "" ) ) {
				this.current().setName( name );
			} else if ( name != null && ! this.current().hasName( name ) ) {
				throw new Alert( "Mismatched tags" ).culprit( "Expected", this.current().getName() ).culprit( "Actual", name );				
			}
		}		
	}
	
	@Override
	public void bindField( final @Nullable String field ) {
		if ( field != null ) {
			if ( this.current_link.getField() == null ) {
				this.current_link.setField( field );
			} else if ( field != null && ! this.current_link.getField().equals( field ) ) {
				throw new Alert( "Mismatched fields" ).culprit( "Expected", this.current_link.getField() ).culprit( "Actual", field );				
			}
		}
	}

	@Override
	public void bindAllowRepeats( final Boolean allow_repeats ) {
		if ( this.current_link.getAllowRepeats() == null ) {
			this.current_link.setAllowRepeats( allow_repeats );
		} else if ( allow_repeats != null && ! this.current_link.getAllowRepeats().equals(  allow_repeats ) ) {
			throw new Alert( "Mismatched unique constraint" );							
		}
	}
	
	@Override
	public void endTag( String name ) {
		this.bindName( name );
		this.endTag();
	}
	

	@Override
	public void endTag( String field, String name ) {
		this.bindField( field );
		this.endTag( name );
	}

	@Override
	public void endTag( String field, String name, Boolean allow_repeats ) {
		this.bindField( field );
		this.bindAllowRepeats( allow_repeats );
		this.endTag( name );
	}

	@Override
	public void endTag() {
		final PreLink b2 = link_stack.removeLast();
		final String field = this.current_link.getNonNullField();
		Boolean allowed = this.current_link.getAllowRepeats();
		if ( allowed == null ) {
			allowed = true;
		}
		final boolean repeat_check = allowed || ! b2.getCurrent().hasLink( field );
		if ( ! repeat_check ) {
			throw new Alert( "Duplicate 'unique' field found" ).culprit( "Field", field );
		}
		final Fusion child = this.current_link.getCurrent();
		if ( !this.isMakingMutable() ) {
			child.freeze();
		}
		b2.getCurrent().addChild( field, child );
		this.current_link = b2;
	}
	
	@Override 
	public void startArray( String field ) {
		link_stack.addLast( current_link );
		this.current_link = new PreLink( this.factory().newMutableArrayFusion(), field, "", false );
		
	}
		
	@Override 
	public void endArray( String field ) {
		this.bindField( field );
		this.endTag();
	}

	@Override 
	public void startObject( String field ) {
		link_stack.addLast( current_link );
		this.current_link = new PreLink( this.factory().newMutableObjectFusion(), field, "", false );
		
	}
	
	@Override 
	public void endObject( String field ) {
		this.bindField( field );
		this.endTag();
	}

	@Override
	public Fusion build() {
		if ( this.current().hasNoFields() ) {
			return null;
		} else {
			Fusion result = this.current().getChild();
			this.current().clearAllLinks();
			return result;
		}
	}

	
}
