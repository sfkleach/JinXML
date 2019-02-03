package com.steelypip.powerups.jinxml;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;
import org.w3c.dom.ranges.RangeException;

import com.steelypip.powerups.util.multimap.MultiMap;
import com.steelypip.powerups.util.multimap.ViewPhoenixMultiMapAsMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.EmptyMutablePMMap;

public class FlexiElement implements Element {
	
	private static final @NonNull String DEFAULT_SELECTOR = "";
	protected String name;
	protected PhoenixMultiMap< String, String > attributes = EmptyMutablePMMap.getInstance();
	protected PhoenixMultiMap< String, Element > members = EmptyMutablePMMap.getInstance();
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Constructors
	/////////////////////////////////////////////////////////////////////////////////////////////
		
	public FlexiElement( final String _name ) {
		this.name = Objects.requireNonNull( _name );
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Mutability 
	/////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * attributes and links are made mutable/immutable in lockstep, so we only need to test one.
	 */
	// TODO - write unit tests
	public boolean isFrozen() {
		return this.attributes.isFrozen();
	}
	
	// TODO - write unit tests
	public Element freeze() {
		FlexiElement e = new FlexiElement( this.name );
		e.attributes = this.attributes.frozenCopyUnlessFrozen();
		e.members = this.members.frozenCopyUnlessFrozen();
		return e;
	}
	
	// TODO - write unit tests
	public Element freeze( boolean returnSelf ) {
		if ( returnSelf ) {
			this.attributes = this.attributes.freezeByPhoenixing();
			this.members = this.members.freezeByPhoenixing();
			return this;
		} else {
			return this.freeze();
		}
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Name
	/////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void setName( String _name ) {
		this.name = Objects.requireNonNull( _name );
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Attributes
	/////////////////////////////////////////////////////////////////////////////////////////////
		
	@Override
	public int countAttributes() {
		return this.attributes.sizeEntries();
	}
	
	@Override
	public Iterator< Map.Entry< String, String > > getAttributesIterator() {
		return this.attributes.frozenCopyUnlessFrozen().iterator();
	}
	
	@Override
	public void setAttributes( final MultiMap< String, String > attributes ) {
		this.attributes = this.attributes.clearAllEntries().addAllEntries( attributes.entriesToList() );
	}
	
	@Override
	public void setValue( String key, String value  ) {
		this.attributes = this.attributes.updateValue( key, 0, value );
	}
	
	@Override
	public void setValue( String key, int position, String value  ) {
		this.attributes = this.attributes.updateValue( key, position, value );
	}
	
	@Override
	public void setValues( String key, Iterable< String> values ) {
		this.attributes = this.attributes.clearAllEntries();
		for ( String v : values ) {
			this.attributes = this.attributes.add( key, v );
		}
	}

	@Override
	public void addLastValue( String key, String value ) {
		this.attributes = this.attributes.add( key, value );
	}
	
	static class PMMapMultiMap< K, V > extends ViewPhoenixMultiMapAsMultiMap< K, V > {
		
		PhoenixMultiMap< K, V > pmmap;
		
		PMMapMultiMap( PhoenixMultiMap< K, V > pmmap ) {
			this.pmmap = pmmap;
		}
		
		protected PhoenixMultiMap< K, V > accessPhoenixMultiMap() {
			return this.pmmap;
		}

		protected void updatePhoenixMultiMap( PhoenixMultiMap< K, V > pmm ) {
			this.pmmap = pmm;
		}
		
	}

	@Override
	public MultiMap< String, String > getAttributesAsMultiMap() {
		//	Neither a view nor mutable - so this is a frozen copy.
		return new PMMapMultiMap< String, String >( this.attributes.frozenCopyUnlessFrozen() );
	}

	@Override
	public MultiMap< String, String > getAttributesAsMultiMap( boolean mutable ) {
		if ( mutable ) {
			//	Not a view but mutable - so this is a mutable copy.
			return new PMMapMultiMap< String, String >( this.attributes.mutableCopy() );
		} else {
			//	Not a view and not mutable - frozen copy.
			return this.getAttributesAsMultiMap();
		}
	}
	
	static class MutableViewOntoAttributes extends ViewPhoenixMultiMapAsMultiMap< String, String > {
		
		FlexiElement element;
		
		MutableViewOntoAttributes( FlexiElement element ) {
			this.element = element;
		}
		
		protected PhoenixMultiMap< String, String > accessPhoenixMultiMap() {
			return this.element.attributes;
		}

		protected void updatePhoenixMultiMap( PhoenixMultiMap< String, String > pmm ) {
			this.element.attributes = pmm;
		}
		
	}

	static class FrozenViewOntoAttributes extends ViewPhoenixMultiMapAsMultiMap< String, String > {
		
		FlexiElement element;
		
		FrozenViewOntoAttributes( FlexiElement element ) {
			this.element = element;
		}
		
		protected PhoenixMultiMap< String, String > accessPhoenixMultiMap() {
			return this.element.attributes;
		}

		protected void updatePhoenixMultiMap( PhoenixMultiMap< String, String > pmm ) {
			throw new UnsupportedOperationException();
		}
		
	}

	@Override
	public MultiMap< String, String > getAttributesAsMultiMap( boolean view, boolean mutable ) {
		if ( view ) {
			if ( mutable ) {
				return new MutableViewOntoAttributes( this );
			} else {
				return new FrozenViewOntoAttributes( this );
			}
		} else {
			return this.getAttributesAsMultiMap( mutable ); 
		}
	}
	
	@Override
	public String getValue( @NonNull String key ) {
		return this.attributes.getElse( key, null );
	}
	
	@Override
	public String getValue( @NonNull String key, String otherwise ) {
		return this.attributes.getElse( key, otherwise );
	}
	
	@Override
	public String getValue( @NonNull String key, int position ) {
		return this.attributes.getElse( key, position, null );
	}
	
	@Override
	public String getValue( @NonNull String key, int position, String otherwise ) {
		return this.attributes.getElse( key, position, otherwise );
	}
	
	@Override
	public String getValue( final @NonNull String key, final boolean reverse, final int position, final String otherwise ) {
		return this.attributes.getElse( key, reverse, position, otherwise );
	}
	
	@Override
	public String getFirstValue( final @NonNull String key, final String otherwise ) {
		return this.attributes.getElse( key, otherwise );
	}
	
	@Override
	public String getFirstValue( final @NonNull String key ) {
		return this.attributes.getElse( key, null );
	}
	
	@Override
	public String getLastValue( final @NonNull String key, final String otherwise ) {
		int N = this.attributes.sizeEntriesWithKey( key );
		return this.attributes.getElse( key, N - 1, otherwise );
	}
	
	@Override
	public String getLastValue( final @NonNull String key ) {
		int N = this.attributes.sizeEntriesWithKey( key );
		return this.attributes.getElse( key, N - 1, null );
	}
	
	@Override
	public int countValues( final @NonNull String key ) {
		return this.attributes.sizeEntriesWithKey( key );
	}
	
	static class ValuesListView extends AbstractList< String > {
		
		@NonNull Element element;
		@NonNull String key;
		final boolean mutable;
		
		public ValuesListView( @NonNull Element element, @NonNull String key, boolean mutable ) {
			this.element = element;
			this.key = key;
			this.mutable = mutable;
		}

		@Override
		public String get( final int index ) {
			return this.element.getValue( this.key, index );
		}

		@Override
		public int size() {
			return this.element.countValues( this.key );
		}
		
		private void checkPermissionToUpdate() {
			if ( ! this.mutable ) throw new UnsupportedOperationException();
		}

		@Override
		public String set( int index, String value ) {
			this.checkPermissionToUpdate();
			final String old_value = this.element.getValue( this.key, index );
			this.element.setValue( this.key, index, value );
			return old_value;
		}

		@Override
		public String remove( int index ) {
			this.checkPermissionToUpdate();
			List< String > values_list = new ArrayList<>( this.element.getValuesAsList( this.key ) );
			String removed = values_list.remove( index );
			this.element.setValues( this.key, values_list );
			return removed;
		}

		@Override
		public void add( int index, String value ) {
			this.checkPermissionToUpdate();
			final int N = this.element.countValues( this.key );
			if ( index < N  ) {
				if ( index < 0 ) throw new IndexOutOfBoundsException();
				List< String > values_list = new ArrayList<>( this.element.getValuesAsList( this.key ) );
				values_list.add( index, value );
				this.element.setValues( this.key, values_list );
			} else if ( index > N ) {
				throw new IndexOutOfBoundsException();
			} else {
				this.element.addLastValue( this.key, value );
			}
		}
	
	}

	@Override
	public List< String > getValuesAsList( @NonNull String key, boolean view, boolean mutable ) {
		if ( !view ) {
			if ( !mutable ) {
				return this.getValuesAsList( key );
			} else {
				return new ArrayList<>( this.getValuesAsList( key ) );				
			}
		} else {
			return new ValuesListView( this, key, mutable );				
		}
	}

	public List< String > getValuesAsList( @NonNull String key ) {
		return this.attributes.getAll( key );	
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Links
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public int countMembers() {
		return this.members.sizeEntries();
	}
		
	@Override
	public MultiMap< String, Element > getMembersAsMultiMap() {
		//	Neither a view nor mutable - so this is a frozen copy.
		return new PMMapMultiMap<>( this.members.frozenCopyUnlessFrozen() );
	}

	@Override
	public MultiMap< String, Element > getMembersAsMultiMap( boolean mutable ) {
		if ( mutable ) {
			//	Not a view but mutable - so this is a mutable copy.
			return new PMMapMultiMap< String, Element >( this.members.mutableCopy() );
		} else {
			//	Not a view and not mutable - frozen copy.
			return this.getMembersAsMultiMap();
		}
	}
	
	static class MutableViewOntoMembers extends ViewPhoenixMultiMapAsMultiMap< String, Element > {
		
		FlexiElement element;
		
		MutableViewOntoMembers( FlexiElement element ) {
			this.element = element;
		}
		
		protected PhoenixMultiMap< String, Element > accessPhoenixMultiMap() {
			return this.element.members;
		}

		protected void updatePhoenixMultiMap( PhoenixMultiMap< String, Element > pmm ) {
			this.element.members = pmm;
		}
		
	}

	static class FrozenViewOntoMembers extends ViewPhoenixMultiMapAsMultiMap< String, Element > {
		
		FlexiElement element;
		
		FrozenViewOntoMembers( FlexiElement element ) {
			this.element = element;
		}
		
		protected PhoenixMultiMap< String, Element > accessPhoenixMultiMap() {
			return this.element.members;
		}

		protected void updatePhoenixMultiMap( PhoenixMultiMap< String, Element > pmm ) {
			throw new UnsupportedOperationException();
		}
		
	}

	@Override
	public MultiMap< String, Element > getMembersAsMultiMap( boolean view, boolean mutable ) {
		if ( view ) {
			if ( mutable ) {
				return new MutableViewOntoMembers( this );
			} else {
				return new FrozenViewOntoMembers( this );
			}
		} else {
			return this.getMembersAsMultiMap( mutable ); 
		}
	}	
	
	@Override
	public void addLastChild( @NonNull String selector, @NonNull Element e ) {
		this.members = this.members.add( selector, e );
		
	}
	
	@Override
	public int countChildren( @NonNull String selector  ) {
		return this.members.sizeEntriesWithKey( selector );
		
	}
	
	@Override
	public Iterator< Map.Entry< String, Element > > getMembersIterator() {
		return this.members.frozenCopyUnlessFrozen().iterator();
	}

	@Override
	public Element getChild( @NonNull String selector, boolean reverse, int position, Element otherwise ) {
		return this.members.getElse( selector, reverse, position, otherwise );
	}

	@Override
	public Element getChild( @NonNull String selector, Element otherwise ) {
		return this.members.getElse( selector, 0, otherwise );
	}

	@Override
	public Element getChild( @NonNull String selector, int position ) {
		return this.members.getElse( selector, position, null );
	}

	@Override
	public Element getChild( Element otherwise ) {
		return this.members.getElse( DEFAULT_SELECTOR, otherwise );
	}

	@Override
	public Element getChild( int position ) {
		return this.members.getElse( DEFAULT_SELECTOR, position, null );
	}

	@Override
	public Element getChild() {
		return this.members.getElse( DEFAULT_SELECTOR, null );
	}

	@Override
	public Element getFirstChild( @NonNull String selector, Element otherwise ) {
		return this.members.getElse( selector, otherwise );
	}

	@Override
	public Element getFirstChild( Element otherwise ) {
		return this.members.getElse( DEFAULT_SELECTOR, otherwise );
	}

	@Override
	public Element getFirstChild() {
		return this.members.getElse( DEFAULT_SELECTOR, null );
	}

	@Override
	public Element getLastChild( @NonNull String selector, Element otherwise ) {
		return this.members.getElse( selector, true, 0, otherwise );
	}

	@Override
	public Element getLastChild( Element otherwise ) {
		return this.members.getElse( DEFAULT_SELECTOR, true, 0, otherwise );
	}

	@Override
	public Element getLastChild() {
		return this.members.getElse( DEFAULT_SELECTOR, true, 0, null );
	}
	
}
