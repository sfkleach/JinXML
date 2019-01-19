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
package com.steelypip.powerups.hydranode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.CmpPair;
import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.EmptyMutablePMMap;

/**
 * This implementation provides a full implementation of all the
 * mandatory and optional methods of the Hydra interface. It aims
 * to strike a balance between fast access, update and reasonable
 * compactness in the most important cases. 
 * 
 * The in-place mutating multi-maps are used to provide implementations
 * that constantly adjust themselves to provide a good balance of
 * speed and compactness.
 */
public abstract class FlexiHydraNode< Key extends Comparable< Key >, AttrValue, Field extends Comparable< Field >, ChildValue > implements HydraNode< Key, AttrValue, Field, ChildValue > {
	
	protected @NonNull String name;
	protected PhoenixMultiMap< Key, AttrValue > attributes = EmptyMutablePMMap.getInstance();
	protected PhoenixMultiMap< Field, ChildValue > links = EmptyMutablePMMap.getInstance();
		
	//////////////////////////////////////////////////////////////////////////////////
	//	Constructors
	//////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs an element with a given name but no attributes or
	 * children.
	 * 
	 * @param name the name of the element
	 */
	@SuppressWarnings("null")
	public FlexiHydraNode( final String name ) {
		this.name = name.intern(); 
	}

	//////////////////////////////////////////////////////////////////////////////////
	//	Overrides that avoid allocating the TreeMap
	//////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void trimToSize() {
		this.attributes = this.attributes.trimToSize();
		this.links = this.links.trimToSize();
	}
	
	public void freeze() {
		this.attributes = this.attributes.freezeByPhoenixing();
		this.links = this.links.freezeByPhoenixing();
	}
		

	@Override
	public @NonNull String getInternedName() {
		return this.name;
	}


	@SuppressWarnings("null")
	@Override
	public void setName( @NonNull String x ) throws UnsupportedOperationException {
		this.name = x.intern();
	}

	@Override
	public AttrValue getValue( Key key ) throws IllegalArgumentException {
		return this.attributes.getOrFail( key );
	}

	@Override
	public AttrValue getValue( Key key, int index ) throws IllegalArgumentException {
		return this.attributes.getOrFail( key, index );
	}

	@Override
	public AttrValue getValue( Key key, AttrValue otherwise ) {
		return this.attributes.getElse( key, otherwise );
	}

	@Override
	public AttrValue getValue( Key key, int index, AttrValue otherwise ) {
		return this.attributes.getElse( key, index, otherwise );		
	}

	@Override
	public void setValue( Key key, AttrValue value ) throws UnsupportedOperationException {
		this.attributes = this.attributes.setSingletonValue( key, value );
	}

	@Override
	public void updateValue( Key key, int index, AttrValue value ) throws IllegalArgumentException, UnsupportedOperationException {
		this.attributes = this.attributes.updateValue( key, index, value );
	}

	@Override
	public void setAllValues( Key key, Iterable< AttrValue > values ) throws UnsupportedOperationException {
		this.attributes = this.attributes.setValues( key, values );
	}

	@Override
	public void addValue( Key key, AttrValue value ) throws UnsupportedOperationException {
		this.attributes = this.attributes.add( key, value );
	}

	@Override
	public void removeValue( Key key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		this.attributes = this.attributes.removeEntryAt( key, 0 );
	}

	@Override
	public void removeValue( Key key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		this.attributes = this.attributes.removeEntryAt( key, index );
	}

	@Override
	public void clearAllAttributes() throws UnsupportedOperationException {
		this.attributes = this.attributes.clearAllEntries();
	}

	@Override
	public void clearAttributes( Key key ) throws UnsupportedOperationException {
		this.attributes = this.attributes.removeEntries( key );
	}

	@Override
	public boolean hasNoAttributes() {
		return this.attributes.isEmpty();
	}

	@Override
	public boolean hasAttribute( Key key ) {
		return this.attributes.hasKey( key );
	}

	@Override
	public boolean hasValueAt( Key key, int index ) {
		return this.attributes.sizeEntriesWithKey( key ) > index;
	}

	@Override
	public boolean hasAttribute( Key key, AttrValue value ) {
		return this.attributes.hasEntry( key, value );
	}

	@Override
	public boolean hasAttribute( Key key, int index, AttrValue value ) {
		return this.attributes.hasEntry( key, index, value );
	}

	@Override
	public boolean hasOneValue( Key key ) {
		return this.attributes.sizeEntriesWithKey( key ) == 1;
	}

	@Override
	public int sizeAttributes() {
		return this.attributes.sizeEntries();
	}

	@Override
	public int sizeKeys() {
		return this.attributes.sizeKeys();	
	}

	@Override
	public boolean hasNoKeys() {
		return this.attributes.isEmpty();
	}

	@Override
	public int sizeValues( Key key ) {
		return this.attributes.sizeEntriesWithKey( key );
	}

	@Override
	public boolean hasSizeValues( Key key, int n ) {
		return this.attributes.sizeEntriesWithKey( key ) == n;
	}

	@Override
	public boolean hasNoValues( Key key ) {
		return this.attributes.sizeEntriesWithKey( key ) == 0;
	}
	
	@Override
	public List< Map.Entry< Key, AttrValue > > attributesToList() {
		return this.attributes.entriesToList();
	}

	@Override
	public List< AttrValue > valuesToList( Key key ) {
		return this.attributes.getAll( key );
	}

	@Override
	public Map< Key, AttrValue > firstValuesToMap() {
		final TreeMap< Key, AttrValue > sofar = new TreeMap<>();
		for ( Key k : this.attributes.keySet() ) {
			final List< AttrValue > list = this.attributes.getAll( k );
			if ( ! list.isEmpty() ) {
				sofar.put( k, list.get( 0 ) );
			}
		}
		return sofar;
	}

	@Override
	public Map< Pair< Key, Integer >, AttrValue > attributesToPairMap() {
		final TreeMap< Pair< Key, Integer >, AttrValue > sofar = new TreeMap<>();
		for ( Key k : this.attributes.keySet() ) {
			int n = 0;
			for ( AttrValue v : this.attributes.getAll( k ) ) {
				sofar.put( new CmpPair< Key, Integer >( k, n++ ), v );
			}
		}
		return sofar;
	}
	
	@Override
	public ChildValue getChild() throws IllegalArgumentException {
		return this.links.getOrFail( this.defaultField() );
	}
	
	@Override
	public ChildValue getChild( int index ) throws IllegalArgumentException {
		return this.links.getOrFail( this.defaultField(), index );
	}

	@Override
	public ChildValue getChild( Field field ) throws IllegalArgumentException {
		return this.links.getOrFail( field );
	}

	@Override
	public ChildValue getChild( Field field, int index ) throws IllegalArgumentException {
		return this.links.getOrFail( field, index );
	}

	@Override
	public ChildValue getChild( Field field, ChildValue otherwise ) {
		return this.links.getElse( field, otherwise );
	}

	@Override
	public ChildValue getChild( Field field, int index, ChildValue otherwise ) {
		return this.links.getElse( field, index, otherwise );
	}

	@Override
	public void setChild( Field field, ChildValue child ) throws UnsupportedOperationException {
		this.links = this.links.setSingletonValue( field, child );
	}

	@Override
	public void updateChild( Field field, int index, ChildValue child ) throws IllegalArgumentException, UnsupportedOperationException {
		this.links = this.links.updateValue( field, index, child );
	}
	

	@Override
	public void setAllChildren( Field field, Iterable< ChildValue > values ) throws UnsupportedOperationException {
		this.links = this.links.setValues( field, values );
	}

	@Override
	public void addChild( Field field, ChildValue child ) throws UnsupportedOperationException {
		this.links = this.links.add( field, child );
	}

	@Override
	public void removeChild( Field field ) throws UnsupportedOperationException, IllegalArgumentException {
		this.links = this.links.removeEntries( field );
	}

	@Override
	public void removeChild( Field field, int index ) throws UnsupportedOperationException, IllegalArgumentException {
		this.links = this.links.removeEntryAt( field, index );
	}

	@Override
	public void clearAllLinks() throws UnsupportedOperationException {
		this.links = this.links.clearAllEntries();
	}

	@Override
	public void clearLinks( Field field ) throws UnsupportedOperationException {
		this.links = this.links.removeEntries( field );
	}

	@Override
	public boolean hasNoLinks() {
		return this.links.isEmpty();
	}
	
	@Override
	public boolean hasLink( Field field ) {
		return this.links.hasKey( field );
	}

	@Override
	public boolean hasLink( Field field, int index ) {
		return this.links.sizeEntriesWithKey( field ) > index;
	}

	@Override
	public boolean hasLink( Field field, ChildValue child ) {
		return this.links.hasEntry( field, child );
	}

	@Override
	public boolean hasLink( Field field, int index, ChildValue child ) {
		return this.links.hasEntry( field, index, child );
	}

	@Override
	public boolean hasOneChild( Field field ) {
		return this.links.sizeEntriesWithKey( field ) == 1;
	}

	@Override
	public int sizeLinks() {
		return this.links.sizeEntries();
	}

	@Override
	public int sizeFields() {
		return this.links.sizeKeys();
	}

	@Override
	public boolean hasSizeFields( int n ) {
		return this.links.sizeKeys() == n;
	}

	@Override
	public boolean hasNoFields() {
		return this.links.isEmpty();
	}

	@Override
	public int sizeChildren( Field field ) {
		return this.links.sizeEntriesWithKey( field );
	}

	@Override
	public boolean hasSizeChildren( Field field, int n ) {
		return this.links.sizeEntriesWithKey( field ) == n;
	}

	@Override
	public boolean hasNoChildren( Field field ) {
		return this.links.sizeEntriesWithKey( field ) == 0;
	}

	@Override
	public List< ChildValue > childrenToList( Field field ) {
		return this.links.getAll( field );
	}

	@Override
	public Map< Field, ChildValue > firstChildrenToMap() {
		final Map< Field, ChildValue > sofar = new TreeMap<>();
		for ( Field k : this.links.keySet() ) {
			final List< ChildValue > list = this.links.getAll( k );
			if ( ! list.isEmpty() ) {
				sofar.put( k, list.get( 0 ) );
			}
		}
		return sofar;
	}

	@Override
	public Map< Pair< Field, Integer >, ChildValue > linksToPairMap() {
		final Map< Pair< Field, Integer >, ChildValue > sofar = new TreeMap<>();
		for ( Field k : this.links.keySet() ) {
			final List< ChildValue > list = this.links.getAll( k );
			int n = 0;
			for ( ChildValue child : list ) {
				final Pair< Field, Integer > p = new CmpPair< Field, Integer >( k, n++ );
				sofar.put( p, child );
			}			
		}
		return sofar;
	}
	

	@Override
	public Iterator< Map.Entry< Field, ChildValue > > iterator() {
		return this.links.iterator();	
	}

	@Override
	public List< Map.Entry< Field, ChildValue > > linksToIterable() {
		return this.links.entriesToList();
	}


	@Override
	public Set< Key > keysToSet() {
		return this.attributes.keySet();
	}

	@Override
	public Set< Field > fieldsToSet() {
		return this.links.keySet();
	}

	@Override
	public void addChild( ChildValue value ) throws UnsupportedOperationException {
		this.addChild( this.defaultField(), value );
	}

	
}
