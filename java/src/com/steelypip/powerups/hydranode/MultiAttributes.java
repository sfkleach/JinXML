package com.steelypip.powerups.hydranode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.steelypip.powerups.common.Pair;
//import com.steelypip.powerups.util.StarMap;

/**
 * A kind of multi-map where:
 * 		- An entry is called an Attribute
 * 		- the key part is called the Key
 * 		- the value part is called the Value
 */
public interface MultiAttributes< Key extends Comparable< Key >, Value > {	
	
	/**
	 * Gets the first attribute value associated with a given key. If
	 * this does not have an attribute of that name it throws an
	 * IllegalArgumentException.
	 * 
	 * @param key the attribute key being looked up
	 * @return the associated value or null
	 * @throws IllegalArgumentException
	 */
	Value getValue( Key key ) throws IllegalArgumentException;

	/**
	 * Gets the attribute value at position index associated with a given key. 
	 * If this does not have an attribute of that name or the index
	 * is out of bouds it throws an IllegalArgumentException.
	 * 
	 * @param key the attribute key being looked up
	 * @return the associated value or null
	 * @throws IllegalArgumentException
	 */	
	Value getValue( Key key, int index ) throws IllegalArgumentException;

	/**
	 * Gets the first attribute value associated with a given key. If
	 * this does not have an attribute of that name it returns 
	 * the supplied value_otherwise.
	 * 
	 * @param key the attribute key being looked up
	 * @param value_otherwise the value to be returned if there is no matching attribute
	 * @return the associated value
	 */	
	Value getValue( Key key, Value otherwise );

	/**
	 * Gets the attribute at position index value associated with a given key. If this
	 * does not have an attribute of that name or the index is out of bounds then it
	 * returns the supplied value_otherwise.
	 * 
	 * @param key the attribute key being looked up
	 * @param value_otherwise the value to be returned if there is no matching attribute
	 * @return the associated value
	 */	
	Value getValue( Key key, int index, Value otherwise );

	/**
	 * Sets the attribute at position index for the value associated with the given key. If this
	 * does not have an attribute of that name or the index is out of bounds then it
	 * throws an IllegalArgumentException.
	 * 
	 * @param key the attribute key being looked up
	 * @param index the index to be updated
	 * @return the new value to replace the old value at the index position
	 */	
	void updateValue( Key key, int index, Value value ) throws IllegalArgumentException, UnsupportedOperationException;

	/**
	 * Discards any existing entries with the same key and inserts a single key-value pair.
	 * 
	 * @param key the attribute key being changed
	 * @param value the new value
	 * @throws UnsupportedOperationException if this is immutable.
	 */	
	void setValue( Key key, Value value ) throws UnsupportedOperationException;
	
	/**
	 * From a key-value Map.Entry, discards any existing entries with the same key and inserts a single key-value pair.
	 * 
	 * @param e the key-value entry
	 * @throws UnsupportedOperationException if this is immutable.
	 */	
	default void setValue( Map.Entry< Key, Value > e ) throws UnsupportedOperationException {
		this.setValue( e.getKey(), e.getValue() );
	}
	
	/**
	 * Discards any existing attributes associated with key and replaces them with a new
	 * set of attributes.
	 * @param key the key to be updated.
	 * @param values the set of attribute values
	 * @throws UnsupportedOperationException if this is immutable.
	 */
	void setAllValues( Key key, Iterable< Value > values ) throws UnsupportedOperationException;
	
	/**
	 * Iterates over map-entries and setting key-value pairs, discarding any existing attributes associated with key.
	 * If there are repetitions of the key, the last update is the one that takes effect.
	 * @param entries the set of key-value pairs.
	 * @throws UnsupportedOperationException if this is immutable.
	 */
	default void setAllValues( Iterable< Map.Entry< Key, Value > > entries ) throws UnsupportedOperationException {
		for ( Map.Entry< Key, Value > e : entries ) {
			this.setValue( e );
		}
	}
	
	/**
	 * Adds a new key-value pair onto the end of the existing pairs.
	 * @param key the key 
	 * @param value the value
	 * @throws UnsupportedOperationException if this is immutable.
	 */
	void addValue( Key key, Value value ) throws UnsupportedOperationException;

	/**
	 * Adds a new key-value pair onto the end of the existing pairs using 
	 * a Map.Entry to supply the key and value.
	 * @param e the key-value pair 
	 * @throws UnsupportedOperationException if this is immutable.
	 */
	default void addValue( Map.Entry< Key, Value > e ) throws UnsupportedOperationException {
		this.addValue( e.getKey(), e.getValue() );
	}

	/**
	 * Iterates over map-entries and setting key-value pairs, adding them into the multi-map.
	 * @param entries the set of key-value pairs.
	 * @throws UnsupportedOperationException if this is immutable.
	 */
	default void addAllValues( Iterable< Map.Entry< Key, Value > > entries ) throws UnsupportedOperationException {
		for ( Map.Entry< Key, Value > e : entries ) {
			this.addValue( e );
		}
	}
	
	/**
	 * Removes the first key-value pair with a matching key. 
	 * @param key the key to match.
	 * @throws UnsupportedOperationException if there is no pair to remove.
	 * @throws IndexOutOfBoundsException if this is immutable.
	 */
	void removeValue( Key key ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	
	/**
	 * Removes the Nth key-value pair with a matching key, where N = index.
	 * @param key the key to match
	 * @param index the index within the matching pairs
	 * @throws UnsupportedOperationException if there is no pair to remove.
	 * @throws IndexOutOfBoundsException if this is immutable.
	 */
	void removeValue( Key key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	
	/**
	 * Removes all key-value pairs with matching key.
	 * @param key the key to match.
	 * @throws UnsupportedOperationException if this is immutable.
	 */
	void clearAttributes( Key key ) throws UnsupportedOperationException;
	
	/**
	 * Removes all key-value pairs.
	 * @throws UnsupportedOperationException if this is immutable.
	 */
	void clearAllAttributes() throws UnsupportedOperationException;

	/**
	 * Returns the number of attributes i.e. the number of key-value pairs.
	 * @return the number of key-value pairs.
	 */
	int sizeAttributes();
	
	/**
	 * Tests whether or not this has exactly n attributes
	 * @param n the number to test against.
	 * @return true if there are exactly n attributes
	 */
	default boolean hasSizeAttributes( int n ) {
		return this.sizeAttributes() == n;
	}
	

	/**
	 * Returns true if this has at least one attribute. Synonymous
	 * with this.sizeAttributes() > 0
	 * 
	 * @return true if this has one or more attributes.
	 */
	default boolean hasAnyAttributes() {
		return ! this.hasNoAttributes();
	}
	
	/**
	 * Tests whether or not this has no attributes
	 * @return true if there are no attributes.
	 */
	boolean hasNoAttributes();
	
	/**
	 * Returns true if this has an attribute with the given key and
	 * value.
	 * 
	 * @param key the attribute key
	 * @param value the attribute value
	 * @return true if this has an attribute with key @key and value @value
	 */
	boolean hasAttribute( Key key );
	
	/**
	 * Returns true if this has at least index+1 attributes with the given key,
	 * implying this.hasValue( key, index ) is true.
	 * 
	 * @param key the attribute key
	 * @param index the position being looked for
	 * @return true if this has at least index+1 attributes with key @key 
	 */
	boolean hasValueAt( Key key, int index );
	
	/**
	 * Returns true if this has an attribute with the given key and
	 * value.
	 * 
	 * @param key the attribute key
	 * @param value the attribute value
	 * @return true if this has an attribute with key @key and value @value
	 */
	boolean hasAttribute( Key key, Value value );
	
	/**
	 * Returns true if this the Nth occurence of an attribute with the given key has
	 * value @value, where N = index + 1. 
	 * 
	 * @param key the attribute key
	 * @param value the attribute value
	 * @return true if this has an attribute with key @key and value @value
	 */
	boolean hasAttribute( Key key, int index, Value value );
	
	/**
	 * Returns true if there is exactly one attribute whose key is @key (i.e. not zero or
	 * multiple attributes).
	 * 
	 * @param key the attribute key
	 * @return true if the element has an attribute with key @key and value @value
	 */	
	default boolean hasOneValue( Key key ) {
		return this.hasSizeValues( key, 1 );		
	}
	
	/**
	 * Returns the number of distinct keys.
	 * @return the number of distinct keys.
	 */
	int sizeKeys();
	
	/**
	 * Returns true if there are n distinct keys. Synonymous with
	 * this.sizeKeys() == n
	 * @param n
	 * @return true if there are n distinct keys, else false.
	 */
	default boolean hasSizeKeys( final int n ) {
		return this.sizeKeys() == n;
	}

	/**
	 * Returns true if there no keys. Synonymous with
	 * this.sizeKeys() == 0 and also synonymous with this.hasNoAttributes().
	 * @param n
	 * @return true if there no keys (and hence no attributes)
	 */
	boolean hasNoKeys();

	/**
	 * Returns true if there are keys. Synonymous with
	 * this.sizeKeys() > 0 and also synonymous with this.hasAttributes().
	 * @param n
	 * @return true if there is one or more keys and hence one or more attributes.
	 */
	default boolean hasAnyKeys() {
		return ! this.hasNoKeys();
	}
	
	/** 
	 * Returns the number of key-value pairs with matching key.
	 * @param key
	 * @return the number of key-value pairs
	 */
	int sizeValues( Key key );
	
	/** 
	 * Tests the number of key-value pairs with matching key.
	 * @param key the key to be matched
	 * @param n the number expected
	 * @return true if the number is exactly n
	 */
	boolean hasSizeValues( Key key, int n );
	
	/** 
	 * Tests if number of key-value pairs with matching key is zero.
	 * @param key the key to be matched
	 * @return true if there are no matching values.
	 */
	default boolean hasNoValues( Key key ) {
		return ! this.hasAttribute( key );
	}
	
	/** 
	 * Tests if number of key-value pairs with matching key is positive (i.e. >0).
	 * @param key the key to be matched
	 * @return true if there is at least 1 pair with a matching key.
	 */
	default boolean hasAnyValues( Key key ) {
		return this.hasAttribute( key );
	}

	/**
	 * Returns the set of key of all key-value pairs as a set.
	 * @return the set of keys.
	 */
	Set< Key > keysToSet();
	
	/**
	 * Returns the set of key-value pairs as a list of map entries.
	 * @return a list of entries
	 */
	List< Map.Entry< Key, Value > > attributesToList();
	
	/**
	 * Returns an iterable for the set of key-value pairs .
	 * @return an iterable over the key-value pairs.
	 */
	default Iterable< Map.Entry< Key, Value > > attributesToIterable() {
		return this.attributesToList();
	}
	
	/**
	 * Returns an iterator over the list of attribute entries.
	 */
	default Iterator< Map.Entry< Key, Value > > attributesIterator() {
		return this.attributesToIterable().iterator();
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	List< Value > valuesToList( Key key );

	Map< Key, Value > firstValuesToMap();

	Map< Pair< Key, Integer >, Value > attributesToPairMap();
	
}
