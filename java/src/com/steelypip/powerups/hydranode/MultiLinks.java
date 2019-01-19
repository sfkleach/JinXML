package com.steelypip.powerups.hydranode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.steelypip.powerups.common.Pair;

//import com.steelypip.powerups.common.Pair;
//import com.steelypip.powerups.util.StarMap;

/**
 * A kind of multi-map where:
 * 		- An entry is called a Link
 * 		- the field part is called the Field
 * 		- the value part is called the Child
 */
public interface MultiLinks< Field extends Comparable< Field >, Value > {
	
	Field defaultField();   
		
	Value getChild() throws IllegalArgumentException;
	
	Value getChild( int index ) throws IllegalArgumentException;
	
	Value getChild( Field field ) throws IllegalArgumentException;
	
	Value getChild( Field field, int index ) throws IllegalArgumentException;
	
	Value getChild( Field field, Value otherwise );
	
	Value getChild( Field field, int index, Value otherwise );

	void setChild( Field field, Value value ) throws UnsupportedOperationException;
	
	void updateChild( Field field, int index, Value value ) throws IllegalArgumentException, UnsupportedOperationException;
	
	void setAllChildren( Field field, Iterable< Value > values ) throws UnsupportedOperationException;
	
	void addChild( Field field, Value value ) throws UnsupportedOperationException;
	
	void addChild( Value value ) throws UnsupportedOperationException;
	
	void removeChild( Field key ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	
	void removeChild( Field key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	
	void clearLinks( Field key ) throws UnsupportedOperationException;
	
	void clearAllLinks() throws UnsupportedOperationException;

	boolean hasNoLinks();
	
	default boolean hasAnyLinks() {
		return ! this.hasNoLinks();
	}
	
	int sizeLinks();
	
	default boolean hasSizeLinks( int n ) {
		return this.sizeLinks() == n;
	}
	
	boolean hasLink( Field field );
	
	boolean hasLink( Field field, int index );
	
	boolean hasLink( Field field, Value value );
	
	boolean hasLink( Field field, int index, Value value );
	
	default boolean hasOneChild( Field field ) {
		return this.hasSizeChildren( field, 1 );
	}
	
	int sizeFields();
	
	default boolean hasSizeFields( int n ) {
		return this.sizeFields() == n;
	}
	
	boolean hasNoFields();
	
	default boolean hasAnyFields() {
		return ! this.hasNoFields();
	}
	
	int sizeChildren( Field field );
	
	default boolean hasSizeChildren( Field field, int n ) {
		return this.sizeChildren( field ) == n;
	}
	
	boolean hasNoChildren( Field field );
	
	default boolean hasChildren( Field field ) {
		return ! this.hasNoChildren( field );
	}

	Set< Field > fieldsToSet();
	
	default List< Map.Entry< Field, Value > > linksToList() {
		final List< Map.Entry< Field, Value > > list = new ArrayList<>();
		for ( Map.Entry< Field, Value > e : list ) {
			list.add( e );
		}
		return list;
	}
	
	default Iterator< Map.Entry< Field, Value > > linksIterator() {
		return this.linksToIterable().iterator();
	}
	
	Iterable< Map.Entry< Field, Value > > linksToIterable();
	
	List< Value > childrenToList( Field field );

	Map< Field, Value > firstChildrenToMap();

	Map< Pair< Field, Integer >, Value > linksToPairMap();
	
}
