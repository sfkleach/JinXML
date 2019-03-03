package com.steelypip.powerups.jinxml;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.NullIndenter;
import com.steelypip.powerups.common.StdIndenter;
import com.steelypip.powerups.io.StringPrintWriter;
import com.steelypip.powerups.jinxml.stdmodel.FlexiElement;
import com.steelypip.powerups.jinxml.stdmodel.InOrderTraversal;
import com.steelypip.powerups.jinxml.stdmodel.StdBuilder;
import com.steelypip.powerups.jinxml.stdparse.StdPushParser;
import com.steelypip.powerups.jinxml.stdrender.ElementWriter;
import com.steelypip.powerups.jinxml.stdrender.JSONTheme;
import com.steelypip.powerups.jinxml.stdrender.StartEndTagTheme;
import com.steelypip.powerups.util.multimap.MultiMap;

public interface Element {
	
	final static @NonNull String DEFAULT_SELECTOR = "";
	final static @NonNull String ROOT_SELECTOR = DEFAULT_SELECTOR;
	final static @NonNull String ROOT_ELEMENT_NAME = "";
	
	static final @NonNull String OBJECT_ELEMENT_NAME = "object";
	static final @NonNull String ARRAY_ELEMENT_NAME = "array";
	static final @NonNull String NULL_ELEMENT_NAME = "null";
	static final @NonNull String BOOLEAN_ELEMENT_NAME = "boolean";
	static final @NonNull String STRING_ELEMENT_NAME = "string";
	static final @NonNull String VALUE_KEY_FOR_LITERAL_CONSTANTS = "value";
	static final @NonNull String INT_ELEMENT_NAME = "int";
	static final @NonNull String FLOAT_ELEMENT_NAME = "float";

	
	/**
	 * Returns true if the element will refuse modifications.
	 * @return true if the element will refuse modifications, else false
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
		this.getMembersStream().forEach( a -> a.getChild().deepFreezeSelf() );
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
	 * @return deep-copy of the element
	 */
	Element deepMutableCopy();
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Name
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * returns the name of the object, which is typically intended to be a type-name.
	 * @return the name of the object
	 */
	@NonNull String getName();
	
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
	
	default boolean hasAnyAttributes() {
		return this.countAttributes() > 0;
	}

	default boolean hasNoAttributes() {
		return this.countAttributes() == 0;
	}

	/**
	 * returns an iterator over the attribute entries of an element
	 * @return the iterator
	 */
	Stream< Attribute > getAttributesStream();
	
	/**
	 * Same as getAttributesAsMultiMap( false, false )
	 * @return a multi-map based on the attributes of the element
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
	
	default Set< String > keysToSet() {
		return this.getAttributesAsMultiMap().keySet();
	}

	/**
	 * Gets the first attribute value with given key , if such an attribute
	 * exists. If not, it returns null.  
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @return the attribute value
	 */
	String getValue( @NonNull String key );
	
	/**
	 * Gets the first attribute value with given key , if such an attribute
	 * exists. If not, it returns the fallack value otherwise.  
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @param otherwise fallback value	 
	 * @return the attribute value
	 */
	String getValue( @NonNull String key, String otherwise );
	
	/**
	 * Gets the attribute value with given key at a given position, if such an attribute
	 * exists. If not, it returns null.  
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @param position attributes with the same key are ordered
	 * @return the attribute value
	 */
	String getValue( @NonNull String key, int position );
	
	/**
	 * Gets the attribute value with given key at a given position, if such an attribute
	 * exists. If not, it returns the fallack value otherwise.  
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @param position attributes with the same key are ordered
	 * @param otherwise fallback value
	 * @return the attribute value
	 */
	String getValue( @NonNull String key, int position, String otherwise );
	
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
	 * @return the attribute value
	 */
	String getFirstValue( @NonNull String key );

	/**
	 * Gets the first attribute value with given key, if such an attribute
	 * exists. If not, it returns otherwise.
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @param otherwise the value to return if no such attribute is present
	 * @return the attribute value
	 */
	String getFirstValue( @NonNull String key, String otherwise );
	
	/**
	 * Gets the last attribute value with given key, if such an attribute
	 * exists. If not, it returns the fallack value otherwise.  
	 * @param key the key of the attribute entry to filter by, may not be null
	 * @return the attribute value
	 */
	String getLastValue( @NonNull String key );

	/**
	 * Gets the last attribute value with given key, if such an attribute
	 * exists. If not, it returns null.  
	 * @param key the key of the attribute entry to filter by, may not be null.
	 * @param otherwise fallback value
	 * @return the attribute value
	 */
	String getLastValue( @NonNull String key, String otherwise );
	
	/**
	 * Returns the number of entries for a given key 
	 * @param key may not be null
	 * @return the number of attributes
	 */
	int countValues( @NonNull String key );
	
	default boolean hasKey( @NonNull String key ) {
		return this.countValues( key ) > 0;
	}
	
	/**
	 * Given a key, return an ordered list of the values of entries with matching key. 
	 * @param key the given key, may not be null
	 * @param view a flag indicating whether the list is a dynamic view on the entries of the element
	 * @param mutable a flag indicating whether the returned list supports update operations
	 * @return the ordered list
	 */
	List< String > getValuesAsList( @NonNull String key, boolean view, boolean mutable );

	/**
	 * Given a key, return an immutable ordered list of the values of entries with matching key.
	 * The list that is returned is independent of the element. Same as .getValuesAsList( key, false, false ) 
	 * @param key the given key, may not be null.
	 * @return the ordered list
	 */
	List< String > getValuesAsList( @NonNull String key );
	
	/**
	 * Return a result that can be used to generate an iterator over the attributes of the
	 * element. It is provided for the efficient iteration over the attributes of an element. 
	 * @return iterable for the attributes.
	 */
	Iterable< Attribute > attributes();

	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Members
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	/** 
	 * Returns the number of members (selector/child pairs) in the element
	 * @return number of members
	 */
	int countMembers();
	
	default boolean hasAnyMembers() {
		return this.countMembers() > 0;
	}
	
	default boolean hasNoMembers() {
		return this.countMembers() == 0;
	}
	
	/*
	 * Returns the number of children for a given selector.
	 * @param selector a non-null selector
	 * @return number of children
	 */
	int countChildren( @NonNull String selector );
	
	/**
	 * Returns the number of children with the default selector (empty string)
	 * @return the number of children
	 */
	int countChildren(); 
	
	default boolean hasSelector( @NonNull String selector ) {
		return this.countChildren( selector ) > 0;
	}
	
	/**
	 * Same as getMembersAsMultiMap( false, false )
	 * @return a multi-map based on the attributes of the element
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
	
	default Set< String > selectorsToSet() {
		return this.getMembersAsMultiMap().keySet();
	}

	/**
	 * returns an iterator over the member entries of an element
	 * @return the iterator
	 */
	Stream< Member > getMembersStream();

	/**
	 * Gets the child from member with given selector at a given position, if such an member
	 * exists. If not, it returns the fallack value otherwise.  
	 * @param selector the non-null selector of the member entry to filter by
	 * @param reverse if true then the position indexes from the last entry, not the first
	 * @param position members with the same key are ordered
	 * @param otherwise fallback value
	 * @return the child
	 */
	Element getChild( @NonNull String selector, boolean reverse, int position, Element otherwise );
		
	/**
	 * Gets the first child from member with given selector, if such an member
	 * exists. If not, it returns the fallack value otherwise.  
	 * @param selector the non-null selector of the member entry to filter by
	 * @param otherwise fallback value
	 * @return the child
	 */
	Element getChild( @NonNull String selector, Element otherwise );
	
	/**
	 * Gets the first child from member with given selector, if such an member
	 * exists. If not, it returns null.  
	 * @param selector the non-null selector of the member entry to filter by
	 * @return the child
	 */
	default Element getChild( @NonNull String selector ) {
		return this.getChild( selector, null );
	}
	
	/**
	 * Gets the child from member with given selector at a given position, if such an member
	 * exists. If not, it returns null.  
	 * @param selector the non-null selector of the member entry to filter by
	 * @param position members with the same key are ordered
	 * @return the child or null
	 */
	Element getChild( @NonNull String selector, int position );
	
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
	
	Element getFirstChild( @NonNull String selector, Element otherwise );
	default Element getFirstChild( @NonNull String selector ) { return this.getFirstChild( selector, null ); }
	Element getFirstChild( Element otherwise );
	Element getFirstChild();
	
	Element getLastChild( @NonNull String selector, Element otherwise );
	default Element getLastChild( @NonNull String selector ) { return this.getLastChild( selector, null ); }
	Element getLastChild( Element otherwise );
	Element getLastChild();
	
	List< Element > getChildrenAsList();
	List< Element > getChildrenAsList( @NonNull String selector );
	List< Element > getChildrenAsList( boolean view, boolean mutable );
	List< Element > getChildrenAsList( @NonNull String selector, boolean view, boolean mutable );
	
	/**
	 * Return a result that can be used to generate an iterator over the members of the
	 * element. It is provided for the efficient iteration over the members of an element.
	 * Frozen elements are likely to be much more efficient than mutable elements because
	 * of the guarantee that once created they are insensitive to mutation.
	 * @return iterable for the members.
	 */
	Member.Iterable members();


	
	
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
	Void getNullValue();	
	<T> T getNullValue( T defaultValue );	
	
	boolean isObject();
	boolean isArray();
	
	default boolean isJSONItem() {
		switch ( this.getName() ) {
		case ARRAY_ELEMENT_NAME:
		case OBJECT_ELEMENT_NAME:
		case BOOLEAN_ELEMENT_NAME:
		case INT_ELEMENT_NAME:
		case FLOAT_ELEMENT_NAME:
		case NULL_ELEMENT_NAME:
		case STRING_ELEMENT_NAME:
			return true;
		default:
			return false;
		}
	}


	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Name - Imperative Methods
	/////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Sets the element name to a non-empty string.
	 * @param _name the new name, which may not be null
	 */
	void setName( @NonNull String _name );	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Attributes - Imperative Methods
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Sets the attributes of an element to the values supplied  - all sharing
	 * the same key. Any previous attributes are removed.
	 * @param key the shared key
	 * @param values a set of values provided as an iterable
	 */
	void setValues( @NonNull String key, Iterable< String > values );
	
	/**
	 * Sets the first attribute of an element with the given key to the given value
	 * the same key. 
	 * @param key the shared key
	 * @param value the value to add
	 */
	void setValue( @NonNull String key, @NonNull String value );
	
	/**
	 * Sets the n-th attribute of an element with the given key to the given value
	 * the same key. 
	 * @param key the shared key
	 * @param position the index of the attribute to update
	 * @param value the value to add
	 */
	void setValue( @NonNull String key, int position, @NonNull String value );
	
	/**
	 * Adds an entry to the attributes of an element.
	 * @param key key of the entry being added
	 * @param value value of the entry being added
	 */
	void addLastValue( @NonNull String key, @NonNull String value );
	
	/**
	 * Adds an entry to the attributes of an element.
	 * This entry will be inserted before any attribute with the same key.
	 * @param key key of the entry being added
	 * @param value value of the entry being added
	 */
	void addFirstValue( @NonNull String key, @NonNull String value );

	/**
	 * Removes the first attribute with the given key. If no attributes with the
	 * given key exist then no change is made.
	 * @param key the given key
	 * @return the value of the attribute that was removed or null if there was no attribute.
	 */
	String removeFirstValue( @NonNull String key );

	/**
	 * Removes the first attribute with the given key. If no attributes with the
	 * given key exist then no change is made.
	 * @param key the given key
	 * @param otherwise the value to return if no attributes with the given key exist
	 * @return the value of the attribute that was removed (or otherwise)
	 */
	String removeFirstValue( @NonNull String key, String otherwise );
	
	/**
	 * Removes the lastattribute with the given key. If no attributes with the
	 * given key exist then no change is made.
	 * @param key the given key
	 * @return the value of the attribute that was removed or null if there was no attribute.
	 */
	String removeLastValue( @NonNull String key );

	/**
	 * Removes the last attribute with the given key. If no attributes with the
	 * given key exist then no change is made.
	 * @param key the given key
	 * @param otherwise the value to return if no attributes with the given key exist
	 * @return the value of the attribute that was removed (or otherwise)
	 */
	String removeLastValue( @NonNull String key, String otherwise );
	
	/**
	 * Sets the attributes of an element sequentially to the entries of a multi-map
	 * preserving the order.
	 * @param attributes the multi-map containing the entries to add
	 */
	void setAttributes( MultiMap< String, String > attributes );
	
	/**
	 * Removes all the attributes of an element.
	 */
	void clearAttributes();
	
	/**
	 * Removes all the attributes of an element with keys that are equal to
	 * the given key
	 * @param key the given key
	 */
	void clearValues( @NonNull String key );
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Members - Imperative Methods
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Sets the child of the first member with the given key to be the given child. If
	 * no such member exists an exception is raised. 
	 * @param key the given key
	 * @param child the given child
	 */
	void setChild( @NonNull String key, Element child  );
	
	/**
	 * Sets the n-th child of the first member with the given key to be the given child. If
	 * no such member exists an exception is raised. 
	 * @param key the given key
	 * @param position specifies the n-th position
	 * @param child the given child
	 */
	void setChild( @NonNull String key, int position, Element child  );

	/**
	 * Effectively removes all the members with the given key and replaces them
	 * with a new list of members with the given key and children draw from 
	 * children. Order is preserved.
	 * @param key the given key
	 * @param children the sequence of elements that will form the children of the new elements in order
	 */
	void setChildren( @NonNull String key, Iterable< Element > children );

	/**
	 * Adds a new member with the given selector and child, which will be the
	 * last of the members with that selector.
	 * @param selector the given selector
	 * @param child the given child
	 */
	void addLastChild( @NonNull String selector, Element child );

	/**
	 * Adds a new member with the default selector ("") and child, which will be the
	 * last of the members with that selector.
	 * @param child the given child
	 */
	void addLastChild( Element child );
	
	/**
	 * Adds a new member with the given selector and child, which will be the
	 * first of the members with that selector.
	 * @param selector the given selector
	 * @param child the given child
	 */
	void addFirstChild( @NonNull String selector, Element child );
	
	/**
	 * Adds a new member with the default selector ("") and child, which will be the
	 * first of the members with that selector.
	 * @param child the given child
	 */
	void addFirstChild( Element child );
	
	/**
	 * Removes the first member with the default selector ("") and returns the child
	 * of the member that was removed. If no such members 
	 * are present in the element, no change is made and null is returned.
	 * @return the child of the removed member or null
	 */
	default Element removeFirstChild() {
		return this.removeFirstChild( "", null );
	}
	
	/**
	 * Removes the first member with the default selector ("") and returns the child
	 * of the member that was removed. If no such members 
	 * are present in the element, return otherwise.
	 * @param otherwise the element to return if no member was removed
	 * @return the child of the removed member or otherwise
	 */
	default Element removeFirstChild( Element otherwise ) {
		return this.removeFirstChild( "", otherwise );
	}

	/**
	 * Removes the first member with the given selector and returns the child
	 * of the member that was removed. If no such members 
	 * are present in the element, no change is made and null is returned.
	 * @param selector the given selector
	 * @return the child of the removed member or null
	 */
	default Element removeFirstChild( @NonNull String selector ) {
		return this.removeFirstChild( selector, null );
	}

	/**
	 * Removes the first member with the given selector and returns the child
	 * of the member that was removed. If no such members 
	 * are present in the element, return otherwise.
	 * @param selector the given selector
	 * @param otherwise the element to return if no member was removed
	 * @return the child of the removed member or otherwise
	 */
	Element removeFirstChild( @NonNull String selector, Element otherwise );
	
	/**
	 * Removes the last member with the default selector ("") and returns the child
	 * of the member that was removed. If no such members 
	 * are present in the element, no change is made and null is returned.
	 * @return the child of the removed member or null
	 */
	default Element removeLastChild() {
		return this.removeLastChild( "", null );
	}
	
	/**
	 * Removes the last member with the default selector ("") and returns the child
	 * of the member that was removed. If no such members 
	 * are present in the element, return otherwise.
	 * @param otherwise the element to return if no member was removed
	 * @return the child of the removed member or otherwise
	 */
	default Element removeLastChild( Element otherwise ) {
		return this.removeLastChild( "", otherwise );
	}
	
	/**
	 * Removes the last member with the given selector and returns the child
	 * of the member that was removed. If no such members 
	 * are present in the element, no change is made and null is returned.
	 * @param selector the given selector
	 * @return the child of the removed member or null
	 */
	default Element removeLastChild( @NonNull String selector ) {
		return this.removeLastChild( selector, null );		
	}
	
	/**
	 * Removes the last member with the given selector and returns the child
	 * of the member that was removed. If no such members 
	 * are present in the element, return otherwise.
	 * @param selector the given selector
	 * @param otherwise the element to return if no member was removed
	 * @return the child of the removed member or otherwise
	 */
	Element removeLastChild( @NonNull String selector, Element otherwise );
	
	/**
	 * Sets the members of an element sequentially to the entries of a multi-map
	 * preserving the order.
	 * @param members the multi-map containing the entries to add
	 */
	void setMembers( MultiMap< String, Element > members );
	
	/**
	 * Removes all the members from an element.
	 */
	void clearMembers();
	
	/**
	 * Removes all the members that have the given selector.
	 * @param selector the given selector
	 */
	void clearChildren( @NonNull String selector );

	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Builder-related Methods
	/////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns a stream of events, which can be replayed into to a builder. 
	 * @return the stream of events
	 */
	default Stream< Event > toEventStream() {
		final InOrderTraversal t = new InOrderTraversal( this );
		return(
			StreamSupport.stream( 
				new Spliterators.AbstractSpliterator< Event >( Long.MAX_VALUE, Spliterator.ORDERED ) {
					@Override
					public boolean tryAdvance( Consumer< ? super Event > action ) {
						final Event e = t.getEvent();
						if ( e == null ) {
							return false;
						} else {
							action.accept( e );
							return true;
						}
					}
				},
				false
			)
		);
	}


	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Rendering
	/////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Renders the element using the supplied {@link PrintWriter}. The rendering will
	 * not contain any newlines. This is the same as the string generated by toString(). 
	 * 
	 * @param pw the {@link PrintWriter} to use.
	 * @param options options that affect the print style
	 */
	default void print( PrintWriter pw, String ... options ) {
		ElementWriter fw = new ElementWriter( pw );
		for ( String c : options ) {
			switch ( c ) {
			case "--pretty":
				fw.setIndenterFactory( new StdIndenter.Factory() );
				break;
			case "--plain":
				fw.setIndenterFactory( new NullIndenter.Factory() );
				break;
			case "--json":
				fw.setTheme( new JSONTheme().compose( new StartEndTagTheme() ) );
				break;
			case "--element":
				fw.setTheme( new StartEndTagTheme() );
				break;
			default:
				throw new RuntimeException( "Unknown option" );
			}
		}
		fw.print( this );
	}

	
	/**
	 * Renders the element to the supplied {@link java.io.Writer}.
	 * 
	 * @param w the {@link Writer} to use.
	 * @param options flags that alter the printing style 
	 */
	default void print( Writer w, String ... options ) {
		this.print( new PrintWriter( w ), options );
	}
	
	/**
	 * Renders the element using the supplied {@link PrintWriter} such that each start and 
	 * end tag are on their own line and the children indented. The output always finishes
	 * with a newline.
	 * 
	 * @param pw the {@link PrintWriter} to use.
	 * @param options options that modify the print style
	 */
	default void prettyPrint( PrintWriter pw, String ... options ) {
		new ElementWriter( pw, new StdIndenter.Factory(), new StartEndTagTheme() ).print( this );
	}
	
	/**
	 * Renders the element using the supplied {@link java.io.Writer} such that each start and 
	 * end tag are on their own line and the children indented. The output always finishes
	 * with a newline.
	 * 
	 * @param w the {@link Writer} to use.
	 */
	default void prettyPrint( Writer w ) {
		new ElementWriter( new PrintWriter( w, true ), new StdIndenter.Factory(), new StartEndTagTheme() ).print( this );
	}
	
	/**
	 * A version of toString that supports the optional printing flags.  
	 * @param options --pretty or --plain 
	 * @return a string version of the element
	 */
	default String toString( String... options ) {
		final StringPrintWriter pw = new StringPrintWriter();
		this.print( pw, options );
		return pw.toString();
	}	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Convenience methods & convenience methods
	/////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a new mutable element with a given element-name. A default
	 * implementation is chosen.
	 * @param name the element name
	 * @return a new mutable element
	 */
	static Element newElement( final @NonNull String name ) {
		return new FlexiElement( name );
	}
	
	/** 
	 * Creates a new Builder object using a default implementation. The elements
	 * returned by this builder are immutable.
	 * @return the builder object
	 */
	static Builder newBuilder() {
		return new StdBuilder( false, true );
	}
	
	/**
	 * Creates a new Builder object using a default implementation. The elements
	 * returned by this builder are mutable depending on the parameter mutable.
	 * @param mutable if true then constructed elements are mutable, else immutable.
	 * @return the builder object.
	 */
	static Builder newBuilder( final boolean mutable ) {
		return new StdBuilder( mutable, true );
	}
	
	/**
	 * Creates a new Builder object using a default implementation. The elements
	 * returned by this builder are mutable depending on the parameter mutable.
	 * If allows_queuing is false then newly constructed elements must be retrieved 
	 * before the next element can be constructed.
	 * @param mutable mutable if true then constructed elements are mutable, else immutable.
	 * @param allows_queuing allows arbitary building and dequeing to happen.
	 * @return the new builder
	 */
	static Builder newBuilder( final boolean mutable, final boolean allows_queuing ) {
		return new StdBuilder( mutable, allows_queuing );
	}
	
	/**
	 * Consumes characters from a reader and parses them to create a new immutable element.
	 * @param reader the input stream to parse
	 * @return the immutable element constructed
	 */
	static Element readElement( Reader reader ) {
		PushParser pp = new StdPushParser( reader, false );
		return pp.readElement();
	}
	
	/**
	 * Returns a stream-of-elements that, when consumed, reads immutable elements from a 
	 * reader.
	 * @param reader the input to be parsed
	 * @return a stream of elements that drives the reader on-demand
	 */
	static Stream< Element > readElementStream( Reader reader ) {
		PushParser pp = new StdPushParser( reader, false );
		return pp.readElementStream();
	}
	
	/**
	 * Parses a string to return an element.
	 * @param input the input to draw from
	 * @return the newly constructed element
	 */
	static Element fromString( final String input ) {
		return readElement( new StringReader( input ) );
	}
	
}
