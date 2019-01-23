package com.steelypip.powerups.jinxml;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.util.multimap.MultiMap;
import com.steelypip.powerups.util.multimap.ViewPhoenixMultiMapAsMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.EmptyMutablePMMap;

public class FlexiElement implements Element {
	
	protected String name;
	protected PhoenixMultiMap< String, String > attributes = EmptyMutablePMMap.getInstance();
	protected PhoenixMultiMap< String, Element > links = EmptyMutablePMMap.getInstance();
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Constructors
	/////////////////////////////////////////////////////////////////////////////////////////////
		
	public FlexiElement( final String _name ) {
		this.name = Objects.requireNonNull( _name );
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
		this.attributes = this.attributes.setSingletonValue( key, value );
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
	public String getValue( @NonNull String key, boolean reverse, int position, String otherwise ) {
		return this.attributes.getElse( key, reverse, position, otherwise );
	}
	
	@Override
	public void addValue( @NonNull String key, @NonNull String value ) {
		this.attributes = this.attributes.add( key, value );
	}
	
}
