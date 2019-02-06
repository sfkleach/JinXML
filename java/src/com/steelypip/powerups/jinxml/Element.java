package com.steelypip.powerups.jinxml;

import java.math.BigInteger;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.steelypip.powerups.util.multimap.MultiMap;

public interface Element {
	
	/**
	 * Returns true if the element will refuse modifications.
	 * @return
	 */
	boolean isFrozen();

	/**
	 * Freezes the element, unless it is frozen already. Children are not affected.
	 */
	void freezeSelf();
	
	/**
	 * Freezes the element and all the children recursively.
	 */
	default void deepFreezeSelf() {
		this.freezeSelf();
		this.getMembersStream().forEach( e -> e.getValue().deepFreezeSelf() );
	}
	
	/**
	 * Returns a frozen element that compares as equal with the subject. If the
	 * element is already frozen it always returns itself. This is a shallow operation.
	 * @return a frozen element that is equal to the subject 
	 */
	Element freeze();	
	
	/**
	 * Returns a frozen element that compares as equal, all of whose children are
	 * also frozen.
	 * @return a frozen version of the subject
	 */
	Element deepFreeze();
	
	/**
	 * Returns a non-identical but equal shallow-copy of the element. All the
	 * children are shared.
	 * @return a copy that is mutable
	 */
	Element mutableCopy();
	
	/**
	 * Returns a non-identical but equal deep-copy of the element that is mutable. All 
	 * the children are deep and mutable copies as well.
	 * @return
	 */
	Element deepMutableCopy();
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Name
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * returns the name of the object, which is typically intended to be a type-name.
	 * @return the name of the object
	 */
	String getName();
	
	default boolean hasName( String name ) {
		return name != null && name.equals( this.getName() );
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Attributes
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * returns the total number of key-value attributes, including duplicates.
	 * @return number of key-value attributes
	 */
	int countAttributes();

	/**
	 * returns an iterator over the attribute entries of an element
	 * @return the iterator
	 */
	Stream< Entry< String, String > > getAttributesStream();
	
	/**
	 * Same as getAttributesAsMultiMap( false, false )
	 * @return 
	 */
	MultiMap< String, String > getAttributesAsMultiMap();
	/**
	 * Same as getAttributesAsMultiMap( false, mutable )
	 * @param mutable a flag indicating if the result is mutable or not
	 * @return a multi-map based on the attributes of the element 
	 */
	MultiMap< String, String > getAttributesAsMultiMap( boolean mutable );
	/**
	 * returns a multi-map representing the attributes of the object. 
	 * If view is true then the multi-map is a mutable view onto the attributes 
	 * and changes to the multi-map immediately affect the attributes. If view 
	 * is false then the list is a copy. The result is mutable or immutable 
	 * depending on the value of mutable
	 * @param view a flag indicating if the result is a view on the Element or a clean copy
	 * @param mutable a flag indicating if the result is mutable or not
	 * @return a multi-map based on the attributes of the element
	 */
	MultiMap< String, String > getAttributesAsMultiMap( boolean view, boolean mutable );

	/**
	 * Gets the first attribute value with given key , if such an attribute
	 * exists. If not, it returns null.  
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @return the attribute value
	 */
	String getValue( String key );
	
	/**
	 * Gets the first attribute value with given key , if such an attribute
	 * exists. If not, it returns the fallack value otherwise.  
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @param otherwise fallback value	 
	 * @return the attribute value
	 */
	String getValue( String key, String otherwise );
	
	/**
	 * Gets the attribute value with given key at a given position, if such an attribute
	 * exists. If not, it returns null.  
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @param position attributes with the same key are ordered
	 * @return the attribute value
	 */
	String getValue( String key, int position );
	
	/**
	 * Gets the attribute value with given key at a given position, if such an attribute
	 * exists. If not, it returns the fallack value otherwise.  
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @param position attributes with the same key are ordered
	 * @param otherwise fallback value
	 * @return the attribute value
	 */
	String getValue( String key, int position, String otherwise );
	
	/**
	 * Gets the attribute value with given key at a given position, if such an attribute
	 * exists. If not, it returns the fallack value otherwise.  
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @param reverse if true then the position indexes from the last entry, not the first
	 * @param position attributes with the same key are ordered
	 * @param otherwise fallback value
	 * @return the attribute value
	 */
	String getValue( String key, boolean reverse, int position, String otherwise );
	
	/**
	 * Gets the first attribute value with given key, if such an attribute
	 * exists. If not, it returns the fallack value otherwise.  
	 * @param key the key of the attribute entry to filter by, may not be null.
	 * @param otherwise fallback value
	 * @return the attribute value
	 */
	String getFirstValue( String key );

	/**
	 * Gets the first attribute value with given key, if such an attribute
	 * exists. If not, it returns null.  
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @return the attribute value
	 */
	String getFirstValue( String key, String otherwise );
	
	/**
	 * Gets the last attribute value with given key, if such an attribute
	 * exists. If not, it returns the fallack value otherwise.  
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @param otherwise fallback value
	 * @return the attribute value
	 */
	String getLastValue( String key );

	/**
	 * Gets the last attribute value with given key, if such an attribute
	 * exists. If not, it returns null.  
	 * @param key the key of the attribute entry to filter by, may not be null.
	 * @return the attribute value
	 */
	String getLastValue( String key, String otherwise );
	
	/**
	 * Retunrs the number of entries for a given key 
	 * @param key may not be null
	 * @return
	 */
	int countValues( String key );
	
	/**
	 * Given a key, return an ordered list of the values of entries with matching key. 
	 * @param key the given key, may not be null
	 * @param view a flag indicating whether the list is a dynamic view on the entries of the element
	 * @param mutable a flag indicating whether the returned list supports update operations
	 * @return the ordered list
	 */
	List< String > getValuesAsList( String key, boolean view, boolean mutable );

	/**
	 * Given a key, return an immutable ordered list of the values of entries with matching key.
	 * The list that is returned is independent of the element. Same as .getValuesAsList( key, false, false ) 
	 * @param key the given key, may not be null.
	 * @return the ordered list
	 */
	List< String > getValuesAsList( String key );

	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Members
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	/** 
	 * Returns the number of members (selector/child pairs) in the element
	 * @return number of members
	 */
	int countMembers();
	
	/*
	 * Returns the number of children for a given selector.
	 * @param selector a non-null selector
	 * @return number of children
	 */
	int countChildren( String selector  );
	
	/**
	 * Returns the number of children with the default selector (empty string)
	 * @return the number of children
	 */
	int countChildren(); 
	
	/**
	 * Same as getMembersAsMultiMap( false, false )
	 * @return 
	 */
	MultiMap< String, Element > getMembersAsMultiMap();
	/**
	 * Same as getMembersAsMultiMap( false, mutable )
	 * @param mutable a flag indicating if the result is mutable or not
	 * @return a multi-map based on the attributes of the element 
	 */
	MultiMap< String, Element > getMembersAsMultiMap( boolean mutable );
	/**
	 * returns a multi-map representing the members of the object. 
	 * If view is true then the multi-map is a mutable view onto the attributes 
	 * and changes to the multi-map immediately affect the attributes. If view 
	 * is false then the list is a copy. The result is mutable or immutable 
	 * depending on the value of mutable
	 * @param view a flag indicating if the result is a view on the Element or a clean copy
	 * @param mutable a flag indicating if the result is mutable or not
	 * @return a multi-map based on the attributes of the element
	 */
	MultiMap< String, Element > getMembersAsMultiMap( boolean view, boolean mutable );	

	/**
	 * returns an iterator over the member entries of an element
	 * @return the iterator
	 */
	Stream< Entry< String, Element > > getMembersStream();

	/**
	 * Gets the child from member with given selector at a given position, if such an member
	 * exists. If not, it returns the fallack value otherwise.  
	 * @param selector the non-null selector of the member entry to filter by
	 * @param reverse if true then the position indexes from the last entry, not the first
	 * @param position members with the same key are ordered
	 * @param otherwise fallback value
	 * @return the child
	 */
	Element getChild( String selector, boolean reverse, int position, Element otherwise );
		
	/**
	 * Gets the first child from member with given selector, if such an member
	 * exists. If not, it returns the fallack value otherwise.  
	 * @param selector the non-null selector of the member entry to filter by
	 * @param otherwise fallback value
	 * @return the child
	 */
	Element getChild( String selector, Element otherwise );
	
	/**
	 * Gets the first child from member with given selector, if such an member
	 * exists. If not, it returns null.  
	 * @param selector the non-null selector of the member entry to filter by
	 * @return the child
	 */
	default Element getChild( String selector ) {
		return this.getChild( selector, null );
	}
	
	/**
	 * Gets the child from member with given selector at a given position, if such an member
	 * exists. If not, it returns null.  
	 * @param selector the non-null selector of the member entry to filter by
	 * @param position members with the same key are ordered
	 * @return the child or null
	 */
	Element getChild( String selector, int position );
	
	/**
	 * Gets the first child from member with the empty selector, if such an member
	 * exists. If not, it returns the fallack value otherwise  
	 * @param otherwise fallback value
	 * @return the child
	 */
	Element getChild( Element otherwise );

	/**
	 * Gets the child from members with the empty selector at a given position, if such an member
	 * exists. If not, it returns null.  
	 * @param position members with the same key are ordered
	 * @return the child or null
	 */
	Element getChild( int position );

	/**
	 * Gets the first child with the empty selector, if such an member
	 * exists. If not, it returns null. Equivalent to this.getFirstChild().
	 * @return the child or null
	 */	
	Element getChild();
	
	Element getFirstChild( String selector, Element otherwise );
	Element getFirstChild( Element otherwise );
	Element getFirstChild();
	
	Element getLastChild( String selector, Element otherwise );
	Element getLastChild( Element otherwise );
	Element getLastChild();
	
	MultiMap< String, Element > getChildrenAsMultiMap();
	MultiMap< String, Element > getChildrenAsMultiMap( boolean mutable );
	MultiMap< String, Element > getChildrenAsMultiMap( boolean view, boolean mutable );

	List< Element > getChildrenAsList();
	List< Element > getChildrenAsList( String selector );
	List< Element > getChildrenAsList( boolean view, boolean mutable );
	List< Element > getChildrenAsList( String selector, boolean view, boolean mutable );
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Primitive Values
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	boolean isIntValue();
	Long getIntValue();
	Long getIntValue( boolean allowOutOfRange );
	Long getIntValue( Long otherwise );
	Long getIntValue( boolean allowOutOfRange, Long otherwise );
	BigInteger getBigIntValue();
	BigInteger getBigIntValue( boolean allowOutOfRange );
	BigInteger getBigIntValue( BigInteger otherwise );
	BigInteger getBigIntValue( boolean allowOutOfRange, BigInteger otherwise );
	
	boolean isFloatValue();
	Double getFloatValue();
	Double getFloatValue( Double otherwise );
	
	boolean isStringValue();
	String getStringValue();
	String getStringValue( String otherwise );
	
	boolean isBooleanValue();
	Boolean getBooleanValue();
	Boolean getBooleanValue( Boolean otherwise );
	
	boolean isNullValue();
	<T> T getNullValue();	
	<T> T getNullValue( T defaultValue );	
	
	/**
	Method isIntValue() -> Boolean - returns true if the object represents an integer. This test is only required to check the name of the object.
	Method getIntValue( Int? default = null ) -> Int? - if the object represents an integer then this returns its integral value. If it is not an integer, the default should be returned. N.B. There is no size limit on integers in JSON and conforming implementations are required to support arbitrary-width integers. The majority of programming languages give small width integers special significance and they should provide special variants of getIntValue (e.g. getInt32Value, getInt64Value) for that purpose. If the integer value is out of range, returning the default value is an acceptable action in these variants.
	Method isFloatValue( Boolean strict = false ) -> Boolean - returns true if the object represents a floating-point number. This test is only required to check the name of the object. If strict is false then integral values will return true for this test.
	Method getFloatValue( Boolean strict = false, Float? default = null ) -> Float? - if the object represents an float then this returns its value using the common representation for the host programming language. This will typically be double precision on today's machines. Libraries may provide variants (e.g. getFloat32Value, getFloatDoubleValue()) at their own discretion. Note that floating point numbers in the syntax have arbitrary precision but there is no requirement to retain this exact precision. If the value is not a Float then implementation should return the default value. If strict is false then integer values are allowed and automatically converted to a floating point value.
	Method isStringValue() -> Boolean
	Method getStringValue( String? default = null ) -> String?
	Method isBooleanValue() -> Boolean
	Method getBooleanValue( Boolean? default = null ) -> Boolean?
	Method isNullValue() -> Boolean
	Method getNullValue( Null? default = null ) -> Null?
	 */

	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Name - Imperative Methods
	/////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Sets the element name to a non-empty string.
	 * @param _name the new name, which may not be null
	 */
	void setName( String _name );	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Attributes - Imperative Methods
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Sets the attributes of an element to the values supplied  - all sharing
	 * the same key. Any previous attributes are removed.
	 * @param key the shared key
	 * @param values a set of values provided as an iterable
	 */
	void setValues( String key, Iterable< String > values );
	
	/**
	 * Sets the first attribute of an element with the given key to the given value
	 * the same key. 
	 * @param key the shared key
	 * @param value the value to add
	 */
	void setValue( String key, String value );
	
	//	TODO
	void setValue( String key, int position, String value );
	
	/**
	 * Adds an entry to the attributes of an element.
	 * @param key key of the entry being added
	 * @param value value of the entry being added
	 */
	void addLastValue( String key, String value );
	
	/**
	 * Adds an entry to the attributes of an element.
	 * This entry will be inserted before any attribute with the same key.
	 * @param key key of the entry being added
	 * @param value value of the entry being added
	 */
	void addFirstValue( String key, String value );

	String removeFirstValue( String key );
	String removeFirstValue( String key, String otherwise );
	
	String removeLastValue( String key );
	String removeLastValue( String key, String otherwise );
	
	/**
	 * Sets the attributes of an element sequentially to the entries of a multi-map
	 * preserving the order.
	 * @param attributes the multi-map containing the entries to add
	 */
	void setAttributes( MultiMap< String, String > attributes );	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Members - Imperative Methods
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	void setChild( String key, Element child  );
	
	void setChild( String key, int position, Element child  );
	
	void setChildren( String key, Iterable< Element > children );

	void addLastChild( String selector, Element child );
	
	void addFirstChild( String selector, Element child );
	
	default Element removeFirstChild() {
		return this.removeFirstChild( "", null );
	}
	default Element removeFirstChild( Element otherwise ) {
		return this.removeFirstChild( "", otherwise );
	}
	default Element removeFirstChild( String selector ) {
		return this.removeFirstChild( selector, null );
	}
	Element removeFirstChild( String selector, Element otherwise );
	
	default Element removeLastChild() {
		return this.removeLastChild( "", null );
	}
	default Element removeLastChild( Element otherwise ) {
		return this.removeLastChild( "", otherwise );
	}
	default Element removeLastChild( String selector ) {
		return this.removeLastChild( selector, null );		
	}
	Element removeLastChild( String selector, Element otherwise );
	
	/**
	 * Sets the members of an element sequentially to the entries of a multi-map
	 * preserving the order.
	 * @param members the multi-map containing the entries to add
	 */
	void setMembers( MultiMap< String, Element > members );

	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Builder-related Methods
	/////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns a stream of events, which can be replayed into to a builder. 
	 * @return the stream of events
	 */
	default Stream< Event > toEventStream() {
		//	TODO:
		throw new RuntimeException( "TO BE DONE" );
	}

}
