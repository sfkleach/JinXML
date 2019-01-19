package com.steelypip.powerups.fusion.jsonimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.CmpPair;
import com.steelypip.powerups.common.EmptyList;
import com.steelypip.powerups.common.EmptyMap;
import com.steelypip.powerups.common.EmptySet;
import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.fusion.Fusion;
import com.steelypip.powerups.fusion.LiteralConstants;


public class ObjectFusion implements Fusion, NullJSONFeatures, LiteralConstants {
	
	private Map< String, Fusion > map = new TreeMap<>();

	@Override
	public @NonNull String getInternedName() {
		return this.constTypeObject();
	}

	@Override
	public String getValue( String key ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public String getValue( String key, int index ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public String getValue( String key, String otherwise ) {
		return otherwise;
	}

	@Override
	public String getValue( String key, int index, String otherwise ) {
		return otherwise;
	}

	@Override
	public void setValue( String key, String value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateValue( String key, int index, String value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAllValues( String key, Iterable< String > values ) throws UnsupportedOperationException {
		if ( values.iterator().hasNext() ) {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void addValue( String key, String value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void removeValue( String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();				
	}

	@Override
	public void removeValue( String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();				
	}

	@Override
	public void clearAttributes( String key ) throws UnsupportedOperationException {		
	}

	@Override
	public void clearAllAttributes() throws UnsupportedOperationException {
	}

	@Override
	public int sizeAttributes() {
		return 0;
	}

	@Override
	public boolean hasNoAttributes() {
		return true;
	}

	@Override
	public boolean hasAttribute( String key ) {
		return false;
	}

	@Override
	public boolean hasValueAt( String key, int index ) {
		return false;
	}

	@Override
	public boolean hasAttribute( String key, String value ) {
		return false;
	}

	@Override
	public boolean hasAttribute( String key, int index, String value ) {
		return false;
	}

	@Override
	public int sizeKeys() {
		return 0;
	}

	@Override
	public boolean hasNoKeys() {
		return true;
	}

	@Override
	public int sizeValues( String key ) {
		return 0;
	}

	@Override
	public boolean hasSizeValues( String key, int n ) {
		return n == 0;
	}

	@Override
	public Set< String > keysToSet() {
		return new EmptySet<>();
	}

	@Override
	public List< Map.Entry< String, String > > attributesToList() {
		return new EmptyList<>();
	}

	@Override
	public List< String > valuesToList( String key ) {
		return new EmptyList<>();
	}

	@Override
	public Map< String, String > firstValuesToMap() {
		return new EmptyMap<>();
	}

	@Override
	public Map< Pair< String, Integer >, String > attributesToPairMap() {
		return new EmptyMap<>();
	}

	@Override
	public Fusion getChild() throws IllegalArgumentException {
		Fusion x = this.map.get( this.defaultField() );
		if ( x == null ) {
			throw new IllegalArgumentException();
		}
		return x;
	}

	@Override
	public Fusion getChild( int index ) throws IllegalArgumentException {
		Fusion x = this.map.get( this.defaultField() );
		if ( x == null || index != 0 ) {
			throw new IllegalArgumentException();
		} else {
			return x;
		}
	}

	@Override
	public Fusion getChild( String field ) throws IllegalArgumentException {
		Fusion x = this.map.get( field );
		if ( x == null ) {
			throw new IllegalArgumentException();
		}
		return x;
	}

	@Override
	public Fusion getChild( String field, int index ) throws IllegalArgumentException {
		Fusion x = this.map.get( field );
		if ( x == null || index != 0 ) {
			throw new IllegalArgumentException();
		}
		return x;
	}

	@Override
	public Fusion getChild( String field, Fusion otherwise ) {
		Fusion x = this.map.get( field );
		if ( x == null ) {
			return otherwise;
		}
		return x;
	}

	@Override
	public Fusion getChild( String field, int index, Fusion otherwise ) {
		Fusion x = this.map.get( field );
		if ( index != 0 ) {
			throw new IllegalArgumentException();
		}
		return x != null ? x : otherwise;
	}

	@Override
	public void setChild( String field, Fusion value ) throws UnsupportedOperationException {
		this.map.put( field, value );
	}

	@Override
	public void updateChild( String field, int index, Fusion value ) throws IllegalArgumentException, UnsupportedOperationException {
		if ( index == 0 ) {
			this.map.put( field, value );			
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void setAllChildren( String field, Iterable< Fusion > values ) throws UnsupportedOperationException {
		final Iterator< Fusion > iterator = values.iterator();
		if ( ! iterator.hasNext() ) {
			this.map.remove( field );
		} else {
			Fusion x = iterator.next();
			if ( ! iterator.hasNext() ) {
				this.map.put( field, x );
			} else {
				throw new UnsupportedOperationException();
			}
		}
	}

	@Override
	public void addChild( String field, Fusion value ) throws UnsupportedOperationException {
		if ( this.map.get( field ) == null ) {
			this.map.put( field, value );
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void addChild( Fusion value ) throws UnsupportedOperationException {
		this.addChild( this.defaultField(), value );
	}

	@Override
	public void removeChild( String field ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		if ( this.map.get( field ) != null ) {
			this.map.remove( field );
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void removeChild( String field, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		if ( index == 0 && this.map.get( field ) != null ) {
			this.map.remove( field );
		} else {
			throw new IndexOutOfBoundsException();
		}
		
	}

	@Override
	public void clearLinks( String field ) throws UnsupportedOperationException {
		this.map.remove( field );
	}

	@Override
	public void clearAllLinks() throws UnsupportedOperationException {
		this.map.clear();
	}

	@Override
	public boolean hasNoLinks() {
		return this.map.isEmpty();
	}

	@Override
	public int sizeLinks() {
		return this.map.size();
	}

	@Override
	public boolean hasLink( String field ) {
		return this.map.get( field ) != null;
	}

	@Override
	public boolean hasLink( String field, int index ) {
		return index == 0 && this.map.get( field ) != null;
	}

	@Override
	public boolean hasLink( String field, Fusion value ) {
		return value != null && value.equals( this.map.get( field ) );
	}

	@Override
	public boolean hasLink( String field, int index, Fusion value ) {
		return index == 0 && value != null && value.equals( this.map.get( field ) );
	}

	@Override
	public int sizeFields() {
		return this.map.size();
	}

	@Override
	public boolean hasNoFields() {
		return this.map.isEmpty();
	}

	@Override
	public int sizeChildren( String field ) {
		return this.map.get( field ) == null ? 0 : 1;
	}

	@Override
	public boolean hasNoChildren( String field ) {
		return this.map.get( field ) == null;
	}

	@Override
	public Set< String > fieldsToSet() {
		return this.map.keySet();
	}

	@Override
	public Iterable< Map.Entry< String, Fusion > > linksToIterable() {
		return new ArrayList<>( this.map.entrySet() );
	}

	@Override
	public List< Fusion > childrenToList( String field ) {
		return new ArrayList<>( this.map.values() );
	}

	@Override
	public Map< String, Fusion > firstChildrenToMap() {
		return new TreeMap<>( this.map );
	}

//	@Override
//	public StarMap< String, Fusion > linksToStarMap() {
//		return new TreeStarMap< String, Fusion >( this.map.entrySet() );
//	}

	@Override
	public Map< Pair< String, Integer >, Fusion > linksToPairMap() {
		final Map< Pair< String, Integer >, Fusion > result = new TreeMap<>();
		for ( Map.Entry< String, Fusion > entry : this.map.entrySet() ) {
			result.put( new CmpPair<String, Integer>( entry.getKey(), 0 ), entry.getValue() );
		}
		return result;
	}

	@Override
	public Iterator< Map.Entry< String, Fusion > > iterator() {
		final Iterator< Map.Entry< String, Fusion > > iterator = this.map.entrySet().iterator();
		return new Iterator< Map.Entry< String, Fusion > >() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Map.Entry< String, Fusion > next() {
				Map.Entry< String, Fusion > e = this.next();
				return new StdPair< String, Fusion >( e.getKey(), e.getValue() );
			}
			
		};
	}

	@Override
	public boolean isObject() {
		return true;
	}

	@Override
	public void freeze() {
	}
	
}
