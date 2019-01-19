package com.steelypip.powerups.fusion.jsonimpl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.CmpPair;
import com.steelypip.powerups.common.EmptyIterator;
import com.steelypip.powerups.common.EmptyList;
import com.steelypip.powerups.common.EmptyMap;
import com.steelypip.powerups.common.EmptySet;
import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.fusion.Fusion;
import com.steelypip.powerups.fusion.LiteralConstants;
import com.steelypip.powerups.fusion.StdJSONFeatures;

public abstract class AbsConstantFusion implements Fusion, StdJSONFeatures, LiteralConstants {
	
	abstract protected @NonNull String internedType();
	
	abstract protected @NonNull String literalValue();
	
	abstract protected void setValueAttribute( final String new_value );

	@Override
	public @NonNull String getInternedName() {
		return this.nameConstant();
	}

	@Override
	public String getValue( String key ) throws IllegalArgumentException {
		if ( this.keyType().equals( key ) ) {
			return this.internedType();
		} else if ( this.keyValue().equals( key ) ) {
			return this.literalValue();
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String getValue( String key, int index ) throws IllegalArgumentException {
		if ( this.keyType().equals( key ) ) {
			if ( index == 0 ) return this.internedType();
		} else if ( this.keyValue().equals( key ) ) {
			if ( index == 0 ) return this.literalValue();
		}
		throw new IllegalArgumentException();
	}

	@Override
	public String getValue( String key, String otherwise ) {
		if ( this.keyType().equals( key ) ) {
			return this.internedType();
		} else if ( this.keyValue().equals( key ) ) {
			return this.literalValue();
		} else {
			return otherwise;
		}
	}

	@Override
	public String getValue( String key, int index, String otherwise ) {
		if ( this.keyType().equals( key ) ) {
			if ( index == 0 ) return this.internedType();
		} else if ( this.keyValue().equals( key ) ) {
			if ( index == 0 ) return this.literalValue();
		}
		return otherwise;
	}

	@Override
	public void setValue( String key, String value ) throws UnsupportedOperationException {
		if ( this.keyValue().equals( key ) ) {
			this.setValueAttribute( value );
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void updateValue( String key, int index, String value ) throws IllegalArgumentException, UnsupportedOperationException {
		if ( this.keyValue().equals( key ) && index == 0 ) {
			this.setValueAttribute( value );
			return;
		} 
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAllValues( String key, Iterable< String > values ) throws UnsupportedOperationException {
		if ( this.keyValue().equals( key ) ) {
			Iterator< String > it = values.iterator();
			if ( it.hasNext() ) {
				String v = it.next();
				if ( ! it.hasNext() ) {
					this.setValueAttribute( v );
					return;
				}
			}
		}
		throw new UnsupportedOperationException();
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
		if ( key.equals( this.keyType() ) || key.equals( this.keyValue() ) ) {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void clearAllAttributes() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNoAttributes() {
		return false;
	}

	@Override
	public boolean hasAttribute( String key ) {
		return key.equals( this.keyType() ) || key.equals( this.keyValue() );
	}

	@Override
	public boolean hasValueAt( String key, int index ) {
		return index == 0 && this.hasAttribute( key );
	}

	@Override
	public boolean hasAttribute( String key, String value ) {
		if ( key.equals( this.keyType() ) ) {
			return this.internedType().equals( value );
		} else if ( key.equals( this.keyValue() ) ) {
			return this.literalValue().equals( value );
		} else {
			return false;
		}
	}

	@Override
	public boolean hasAttribute( String key, int index, String value ) {
		return index == 0 && this.hasAttribute(  key, value );
	}

	@Override
	public boolean hasOneValue( String key ) {
		return this.hasAttribute( key );
	}

	@Override
	public int sizeAttributes() {
		return 2;
	}

	@Override
	public int sizeKeys() {
		return 2;
	}

	@Override
	public boolean hasNoKeys() {
		return false;
	}

	@Override
	public int sizeValues( String key ) {
		return this.hasAttribute( key ) ? 1 : 0;
	}

	@Override
	public boolean hasSizeValues( String key, int n ) {
		return ( n == 0 || n == 1 ) && ( this.hasAttribute( key ) ? n == 1 : n == 0 );
	}

	@Override
	public boolean hasNoValues( String key ) {
		return ! this.hasAttribute( key );
	}

	@Override
	public Set< String > keysToSet() {
		final TreeSet< String > result = new TreeSet<>();
		result.add( this.keyType());
		result.add( this.keyValue() );
		return result;
	}

	@Override
	public List< Map.Entry< String, String > > attributesToList() {
		final List< Map.Entry< String, String > > attrs = new LinkedList<>();
		attrs.add( new StdPair<String, String>( this.keyType(),this.internedType() ) );
		attrs.add( new StdPair<String, String>( this.keyValue(), this.literalValue() ) );
		return attrs;
	}

	@Override
	public List< String > valuesToList( String key ) {
		if ( this.hasAttribute( key ) ) {
			List< String > values = new LinkedList<>();
			values.add( this.getValue( key ) );
			return values;
		} else {
			return new EmptyList<>();
		}
	}

	@Override
	public Map< String, String > firstValuesToMap() {
		final TreeMap< String, String > m = new TreeMap<>();
		m.put( this.keyType(), this.internedType() );
		m.put( this.keyValue(), this.literalValue() );
		return m;
	}

//	@Override
//	public StarMap< String, String > attributesToStarMap() {
//		final StarMap< String, String > m = new TreeStarMap<>();
//		m.add( this.keyType(), this.internedType() );
//		m.add( this.keyValue(), this.literalValue() );
//		return m;
//	}

	@Override
	public Map< Pair< String, Integer >, String > attributesToPairMap() {
		final TreeMap< Pair< String, Integer >, String > m = new TreeMap<>();
		m.put( new CmpPair< String, Integer >( this.keyType(), 0 ), this.internedType() );
		m.put( new CmpPair< String, Integer >( this.keyValue(), 0 ) , this.literalValue() );
		return m;
	}

	@Override
	public Fusion getChild() throws IllegalArgumentException {
		return null;
	}

	@Override
	public Fusion getChild( int index ) throws IllegalArgumentException {
		return null;
	}

	@Override
	public Fusion getChild( String field ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public Fusion getChild( String field, int index ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public Fusion getChild( String field, Fusion otherwise ) {
		return otherwise;
	}

	@Override
	public Fusion getChild( String field, int index, Fusion otherwise ) {
		return otherwise;
	}

	@Override
	public void setChild( String field, Fusion value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateChild( String field, int index, Fusion value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAllChildren( String field, Iterable< Fusion > values ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addChild( String field, Fusion value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addChild( Fusion value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeChild( String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeChild( String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearLinks( String key ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearAllLinks() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNoLinks() {
		return true;
	}

	@Override
	public boolean hasLink( String field ) {
		return false;
	}

	@Override
	public boolean hasLink( String field, int index ) {
		return false;
	}

	@Override
	public boolean hasLink( String field, Fusion value ) {
		return false;
	}

	@Override
	public boolean hasLink( String field, int index, Fusion value ) {
		return false;
	}

	@Override
	public boolean hasOneChild( String field ) {
		return false;
	}

	@Override
	public int sizeLinks() {
		return 0;
	}

	@Override
	public int sizeFields() {
		return 0;
	}

	@Override
	public boolean hasNoFields() {
		return true;
	}

	@Override
	public int sizeChildren( String field ) {
		return 0;
	}

	@Override
	public boolean hasSizeChildren( String field, int n ) {
		return n == 0;
	}

	@Override
	public boolean hasNoChildren( String field ) {
		return true;
	}

	@Override
	public Set< String > fieldsToSet() {
		return new EmptySet< String >();
	}

	@Override
	public Iterable< Map.Entry< String, Fusion > > linksToIterable() {
		return new EmptyList<>();
	}

	@Override
	public List< Fusion > childrenToList( String field ) {
		return new EmptyList< Fusion >();
	}

	@Override
	public Map< String, Fusion > firstChildrenToMap() {
		return new EmptyMap<>();
	}

	@Override
	public Map< Pair< String, Integer >, Fusion > linksToPairMap() {
		return new EmptyMap<>();
	}

	@Override
	public Iterator< Map.Entry< String, Fusion > > iterator() {
		return new EmptyIterator<>();
	}

	@Override
	public Iterator< Entry< String, Fusion > > linksIterator() {
		return new EmptyIterator< Map.Entry< String, Fusion > >();
	}

	@Override
	public void freeze() {
	}


}
