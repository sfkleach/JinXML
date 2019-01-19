package com.steelypip.powerups.fusion.jsonimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.CmpPair;
import com.steelypip.powerups.common.EmptyList;
import com.steelypip.powerups.common.EmptyMap;
import com.steelypip.powerups.common.EmptySet;
import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.fusion.Fusion;
import com.steelypip.powerups.fusion.LiteralConstants;


public class ArrayFusion implements Fusion, NullJSONFeatures, LiteralConstants {
	
	List< Fusion > children = new ArrayList<>(); 
	
	public ArrayFusion( Fusion... children ) {
		for ( Fusion child : children ) {
			this.children.add(  child );
		}
	}

	public ArrayFusion( Collection< ? extends Fusion > children ) {
		this.children.addAll( children );
	}

	public ArrayFusion( Iterable< ? extends Fusion > children ) {
		for ( Fusion child : children ) {
			this.children.add( child );
		}
	}

	@Override
	public String getValue( String key ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getValue( String key, int index ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
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
	}

	@Override
	public void removeValue( String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
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
	public boolean hasSizeAttributes( int n ) {
		return n == 0;
	}

	@Override
	public boolean hasAnyAttributes() {
		return false;
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
	public boolean hasOneValue( String key ) {
		return false;
	}

	@Override
	public int sizeKeys() {
		return 0;
	}

	@Override
	public boolean hasSizeKeys( int n ) {
		return n == 0;
	}

	@Override
	public boolean hasNoKeys() {
		return true;
	}

	@Override
	public boolean hasAnyKeys() {
		return false;
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
	public boolean hasNoValues( String key ) {
		return true;
	}

	@Override
	public boolean hasAnyValues( String key ) {
		return false;
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
	public boolean isArray() {
		return true;
	}


	@Override
	public Fusion getChild() throws IllegalArgumentException {
		try {
			return this.children.get( 0 );
		} catch ( IndexOutOfBoundsException _e ) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Fusion getChild( final int index ) throws IllegalArgumentException {
		try {
			return this.children.get( index );
		} catch ( IndexOutOfBoundsException _e ) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Fusion getChild( final String field ) throws IllegalArgumentException {
		if ( field.equals( this.defaultField() ) ) {
			return this.getChild();
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Fusion getChild( String field, int index ) throws IllegalArgumentException {
		if ( field.equals( this.defaultField() ) ) {
			return this.getChild( index );
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Fusion getChild( String field, Fusion otherwise ) {
		try {
			if ( field.equals( this.defaultField() ) ) {
				return this.children.get( 0 );
			} else {
				return otherwise;
			}
		} catch ( IndexOutOfBoundsException _e ) {
			return otherwise;
		}
	}

	@Override
	public Fusion getChild( String field, int index, Fusion otherwise ) {
		try {
			if ( field.equals( this.defaultField() ) ) {
				return this.children.get( index );
			} else {
				return otherwise;
			}
		} catch ( IndexOutOfBoundsException _e ) {
			return otherwise;
		}
	}

	@Override
	public void setChild( String field, Fusion value ) throws UnsupportedOperationException {
		if ( field.equals( this.defaultField() ) ) {
			try {
				this.children.set( 0, value );
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException( _e );				
			}
		} else {
			throw new IllegalArgumentException();			
		}
	}

	@Override
	public void updateChild( String field, int index, Fusion value ) throws IllegalArgumentException, UnsupportedOperationException {
		if ( field.equals( this.defaultField() ) ) {
			try {
				this.children.set( index, value );
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException( _e );				
			}
		} else {
			throw new IllegalArgumentException();			
		}
	}

	@Override
	public void setAllChildren( String field, Iterable< Fusion > values ) throws UnsupportedOperationException {
		if ( field.equals( this.defaultField() ) ) {
			try {
				this.children.clear();
				for ( Fusion child : values ) {
					this.children.add( child );
				}
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException( _e );				
			}
		} else {
			throw new IllegalArgumentException();			
		}
	}

	@Override
	public void addChild( String field, Fusion value ) throws UnsupportedOperationException {
		if ( field.equals( this.defaultField() ) ) {
			this.children.add( value );
		} else {
			throw new IllegalArgumentException();			
		}
	}

	@Override
	public void addChild( Fusion value ) throws UnsupportedOperationException {
		this.children.add( value );
	}

	@Override
	public void removeChild( String field ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		if ( field.equals( this.defaultField() ) ) {
			this.children.remove( 0 );
		} else {
			throw new IllegalArgumentException();						
		}
	}

	@Override
	public void removeChild( String field, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		if ( field.equals( this.defaultField() ) ) {
			this.children.remove( index );
		} else {
			throw new IllegalArgumentException();						
		}
	}

	@Override
	public void clearLinks( String field ) throws UnsupportedOperationException {
		if ( field.equals( this.defaultField() ) ) {
			this.children.clear();
		}
	}

	@Override
	public void clearAllLinks() throws UnsupportedOperationException {
		this.children.clear();
	}

	@Override
	public boolean hasNoLinks() {
		return this.children.isEmpty();
	}

	@Override
	public boolean hasAnyLinks() {
		return ! this.children.isEmpty();
	}

	@Override
	public int sizeLinks() {
		return this.children.size();
	}

	@Override
	public boolean hasSizeLinks( final int n ) {
		return this.children.size() == n;
	}

	@Override
	public boolean hasLink( String field ) {
		if ( field.equals( this.defaultField() ) ) {
			return ! this.children.isEmpty();
		} else {
			return false;
		}
	}

	@Override
	public boolean hasLink( String field, int index ) {
		if ( field.equals( this.defaultField() ) ) {
			return 0 <= index && index < this.children.size(); 
		} else {
			return false;
		}
	}

	@Override
	public boolean hasLink( String field, Fusion value ) {
		if ( field.equals( this.defaultField() ) ) {
			return this.children.contains( value ); 
		} else {
			return false;
		}
	}

	@Override
	public boolean hasLink( String field, int index, Fusion value ) {
		if ( field.equals( this.defaultField() ) ) {
			try {
				return this.children.get( index ).equals( value );
			} catch ( IndexOutOfBoundsException _e ) {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean hasOneChild( String field ) {
		if ( field.equals( this.defaultField() ) ) {
			return this.children.size() == 1;
		} else {
			return false;
		}
	}

	@Override
	public int sizeFields() {
		return this.children.isEmpty() ? 0 : 1;
	}

	@Override
	public boolean hasSizeFields( int n ) {
		return this.children.isEmpty() ? n == 0 : n == 1;
	}

	@Override
	public boolean hasNoFields() {
		return this.children.isEmpty();
	}

	@Override
	public boolean hasAnyFields() {
		return ! this.children.isEmpty();
	}

	@Override
	public int sizeChildren( String field ) {
		if ( field.equals( this.defaultField() ) ) {
			return this.children.size();
		} else {
			return 0;
		}
	}

	@Override
	public boolean hasSizeChildren( String field, int n ) {
		if ( field.equals( this.defaultField() ) ) {
			return this.children.size() == n;
		} else {
			return 0 == n;
		}
	}

	@Override
	public boolean hasNoChildren( String field ) {
		return this.hasSizeChildren( field, 0 );
	}

	@Override
	public boolean hasChildren( String field ) {
		return ! this.hasSizeChildren( field, 0 );
	}

	@Override
	public Set< String > fieldsToSet() {
		if ( this.children.isEmpty() ) {
			return new EmptySet<>();
		} else {
			Set< String > set = new TreeSet<>();
			set.add( this.defaultField() );
			return set;
		}
	}

	@Override
	public Iterable< Map.Entry< String, Fusion > > linksToIterable() {
		final List< Map.Entry< String, Fusion > > list = new ArrayList<>();
		for ( Fusion f : this.children ) {
			list.add( new StdPair< String, Fusion >( this.defaultField(), f ) );
		}
		return list; 
	}

	@Override
	public List< Fusion > childrenToList( String field ) {
		if ( this.defaultField().equals( field ) ) {
			return new ArrayList< Fusion >( this.children );
		} else {
			return new EmptyList<>();
		}
	}

	@Override
	public Map< String, Fusion > firstChildrenToMap() {
		final Map< String, Fusion > map = new TreeMap<>();
		if ( ! this.children.isEmpty() ) {
			map.put( this.defaultField(), this.children.get( 0 ) );
		}
		return map;
	}

//	@Override
//	public StarMap< String, Fusion > linksToStarMap() {
//		final StarMap< String, Fusion > smap = new TreeStarMap<>();
//		for ( Fusion child : this.children ) {
//			smap.add( this.defaultField(), child );
//		}
//		return smap;
//	}

	@Override
	public Map< Pair< String, Integer >, Fusion > linksToPairMap() {
		final Map< Pair< String, Integer >, Fusion > map = new TreeMap<>();
		int n = 0;
		for ( Fusion child : this.children ) {
			map.put( new CmpPair<>( this.defaultField(), n++ ), child );
		}		
		return map;
	}

	@Override
	public @NonNull String getInternedName() {
		return this.constTypeArray();
	}

	@Override
	public Iterator< Map.Entry< String, Fusion > > iterator() {
		final Iterator< Fusion > iterator = this.children.iterator();
		final String def = this.defaultField();
		
		return new Iterator< Map.Entry< String, Fusion > >() {
			
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Pair< String, Fusion > next() {
				return new StdPair< String, Fusion >( def, iterator.next() );
			}
			
		};
	}

	@Override
	public void freeze() {
	}
	
}
