package com.steelypip.powerups.jinxml.stdmodel;

import java.math.BigInteger;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.jinxml.Attribute;
import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Member;
import com.steelypip.powerups.util.multimap.MultiMap;
import com.steelypip.powerups.util.multimap.ViewPhoenixMultiMapAsMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.EmptyMutablePMMap;

public class FlexiElement implements Element {
	
	protected @NonNull String name;
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
	public void freezeSelf() {
		this.attributes = this.attributes.frozenCopyUnlessFrozen();
		this.members = this.members.frozenCopyUnlessFrozen();
	}
	
	// TODO - write unit tests
	public Element freeze() {
		if ( this.attributes.isFrozen() ) {
			return this;
		} else {
			FlexiElement e = new FlexiElement( this.getName() );
			e.attributes = this.attributes.frozenCopyUnlessFrozen();
			e.members = this.members.frozenCopyUnlessFrozen();
			return this;
		}
	}
	
	// TODO - write unit tests
	public Element deepFreeze() {
		List< StdPair< Map.Entry< String, Element >, Element> > pairs = this.getMembersStream().map( e -> new StdPair<>( e, e.getValue().deepFreeze() ) ).collect( Collectors.toList() );
		boolean no_need_to_copy = this.isFrozen() && pairs.stream().allMatch( p -> p.getFirst().getValue() == p.getSecond() );
		if ( no_need_to_copy ) {
			return this;
		} else {
			Element new_element = new FlexiElement( this.getName() );
			this.getAttributesStream().forEachOrdered( e -> new_element.addLastValue( e.getKey(), e.getValue() ) );
			pairs.forEach( e -> new_element.addLastChild( e.getFirst().getKey(), e.getSecond() ) );
			new_element.freeze();
			return new_element;
		}
	}
	
	public Element deepMutableCopy() {
		final Element new_element = new FlexiElement( this.getName() );
		this.getAttributesStream().forEachOrdered( e -> new_element.addLastValue( e.getKey(), e.getValue() ) );		
		this.getMembersStream().map( e -> new StdPair<>( e.getKey(), e.getValue().deepMutableCopy() ) ).forEachOrdered( e -> new_element.addLastChild( e.getKey(), e.getValue() ) );
		return new_element;
	}
	
	public Element mutableCopy() {
		final Element new_element = new FlexiElement( this.getName() );
		this.getAttributesStream().forEachOrdered( e -> new_element.addLastValue( e.getKey(), e.getValue() ) );		
		this.getMembersStream().forEachOrdered( e -> new_element.addLastChild( e.getKey(), e.getValue() ) );
		return new_element;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Name
	/////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public @NonNull String getName() {
		return this.name;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Attributes
	/////////////////////////////////////////////////////////////////////////////////////////////
		
	@Override
	public int countAttributes() {
		return this.attributes.sizeEntries();
	}
	
	@Override
	public Stream< Map.Entry< String, String > > getAttributesStream() {
		return this.attributes.frozenCopyUnlessFrozen().stream();
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
		return this.attributes.getElse( Objects.requireNonNull( key ), null );
	}
	
	@Override
	public String getValue( @NonNull String key, String otherwise ) {
		return this.attributes.getElse( Objects.requireNonNull( key ), otherwise );
	}
	
	@Override
	public String getValue( @NonNull String key, int position ) {
		return this.attributes.getElse( Objects.requireNonNull( key ), position, null );
	}
	
	@Override
	public String getValue( @NonNull String key, int position, String otherwise ) {
		return this.attributes.getElse( Objects.requireNonNull( key ), position, otherwise );
	}
	
	@Override
	public String getValue( final String key, final boolean reverse, final int position, final String otherwise ) {
		return this.attributes.getElse( Objects.requireNonNull( key ), reverse, position, otherwise );
	}
	
	@Override
	public String getFirstValue( final @NonNull String key, final String otherwise ) {
		return this.attributes.getElse( Objects.requireNonNull( key ), otherwise );
	}
	
	@Override
	public String getFirstValue( final @NonNull String key ) {
		return this.attributes.getElse( Objects.requireNonNull( key ), null );
	}
	
	@Override
	public String getLastValue( final @NonNull String key, final String otherwise ) {
		int N = this.attributes.sizeEntriesWithKey( key );
		return this.attributes.getElse( Objects.requireNonNull( key ), N - 1, otherwise );
	}
	
	@Override
	public String getLastValue( final @NonNull String key ) {
		int N = this.attributes.sizeEntriesWithKey( key );
		return this.attributes.getElse( Objects.requireNonNull( key ), N - 1, null );
	}
	
	@Override
	public int countValues( final @NonNull String key ) {
		return this.attributes.sizeEntriesWithKey( Objects.requireNonNull( key ) );
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
	public List< String > getValuesAsList( String key, boolean view, boolean mutable ) {
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

	@Override 
	public List< String > getValuesAsList( String key ) {
		return this.attributes.getAll( key );	
	}
	
	@Override
	public Iterable< Attribute > attributes() {
		if ( this.isFrozen() ) {
			return new Iterable< Attribute >() {
				@Override
				public Iterator< Attribute > iterator() {
					final Iterator< Map.Entry< String, String > > it = FlexiElement.this.attributes.iterator();
					return new Iterator< Attribute >() {
						@Override
						public boolean hasNext() {
							return it.hasNext();
						}
						@Override
						public Attribute next() {
							final Map.Entry< String, String > e = it.next();
							return new StdAttribute( e.getKey(), 0, e.getValue() );
						}
					};
				}
			};
		} else {
			final ArrayList< Attribute > list = new ArrayList<>();
			final Iterator< Map.Entry< String, String > > it = this.attributes.iterator();
			while ( it.hasNext() ) {
				final Map.Entry< String, String > e = it.next();
				list.add( new StdAttribute( e.getKey(), 0, e.getValue() ) );
			}
			return list;
		}
	}
	

	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Members
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
	public int countChildren( String selector  ) {
		return this.members.sizeEntriesWithKey( selector );
		
	}
	
	@Override
	public int countChildren() {
		return this.members.sizeEntriesWithKey( DEFAULT_SELECTOR );
		
	}
	
	@Override
	public Stream< Map.Entry< String, Element > > getMembersStream() {
		return this.members.frozenCopyUnlessFrozen().stream();
	}

	@Override
	public Element getChild( String selector, boolean reverse, int position, Element otherwise ) {
		return this.members.getElse( Objects.requireNonNull( selector ), reverse, position, otherwise );
	}

	@Override
	public Element getChild( String selector, Element otherwise ) {
		return this.members.getElse( Objects.requireNonNull( selector ), 0, otherwise );
	}

	@Override
	public Element getChild( String selector, int position ) {
		return this.members.getElse( Objects.requireNonNull( selector ), position, null );
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
	public Element getFirstChild( String selector, Element otherwise ) {
		return this.members.getElse( Objects.requireNonNull( selector ), otherwise );
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
	public Element getLastChild( String selector, Element otherwise ) {
		return this.members.getElse( Objects.requireNonNull( selector ), true, 0, otherwise );
	}

	@Override
	public Element getLastChild( Element otherwise ) {
		return this.members.getElse( DEFAULT_SELECTOR, true, 0, otherwise );
	}

	@Override
	public Element getLastChild() {
		return this.members.getElse( DEFAULT_SELECTOR, true, 0, null );
	}

	
	@Override
	public List< Element > getChildrenAsList( @NonNull String selector, boolean view, boolean mutable ) {
		if ( !view ) {
			if ( !mutable ) {
				return this.getChildrenAsList( Objects.requireNonNull( selector ) );
			} else {
				return new ArrayList<>( this.getChildrenAsList( selector ) );				
			}
		} else {
			return new ChildrenListView( this, Objects.requireNonNull( selector ), mutable );				
		}
	}

	@Override 
	public List< Element > getChildrenAsList( @NonNull String selector ) {
		return this.members.getAll( Objects.requireNonNull( selector ) );	
	}

	
	@Override 
	public List< Element > getChildrenAsList( boolean view, boolean mutable ) {
		return this.getChildrenAsList( DEFAULT_SELECTOR, view, mutable );	
	}

	
	@Override 
	public List< Element > getChildrenAsList() {
		return this.members.getAll( DEFAULT_SELECTOR );	
	}

	static class ChildrenListView extends AbstractList< Element > {
		
		@NonNull Element element;
		@NonNull String selector;
		final boolean mutable;
		
		public ChildrenListView( @NonNull Element element, @NonNull String selector, boolean mutable ) {
			this.element = element;
			this.selector = selector;
			this.mutable = mutable;
		}

		@Override
		public Element get( final int index ) {
			return this.element.getChild( this.selector, index );
		}

		@Override
		public int size() {
			return this.element.countChildren( this.selector );
		}
		
		private void checkPermissionToUpdate() {
			if ( ! this.mutable ) throw new UnsupportedOperationException();
		}

		@Override
		public Element set( int index, Element value ) {
			this.checkPermissionToUpdate();
			final Element old_value = this.element.getChild( this.selector, index );
			this.element.setChild( this.selector, index, value );
			return old_value;
		}

		@Override
		public Element remove( int index ) {
			this.checkPermissionToUpdate();
			List< Element > children = new ArrayList<>( this.element.getChildrenAsList( this.selector ) );
			Element removed = children.remove( index );
			this.element.setChildren( this.selector, children );
			return removed;
		}

		@Override
		public void add( int index, Element value ) {
			this.checkPermissionToUpdate();
			final int N = this.element.countChildren( this.selector );
			if ( index < N  ) {
				if ( index < 0 ) throw new IndexOutOfBoundsException();
				List< Element > children = new ArrayList<>( this.element.getChildrenAsList( this.selector ) );
				children.add( index, Objects.requireNonNull( value ) );
				this.element.setChildren( this.selector, children );
			} else if ( index > N ) {
				throw new IndexOutOfBoundsException();
			} else {
				this.element.addLastChild( this.selector, Objects.requireNonNull( value ) );
			}
		}
	
	}
	
	@Override
	public Iterable< Member > members() {
		if ( this.isFrozen() ) {
			return new Iterable< Member >() {
				@Override
				public Iterator< Member > iterator() {
					final Iterator< Map.Entry< String, Element > > it = FlexiElement.this.members.iterator();
					return new Iterator< Member >() {
						@Override
						public boolean hasNext() {
							return it.hasNext();
						}
						@Override
						public Member next() {
							final Map.Entry< String, Element > e = it.next();
							return new StdMember( e.getKey(), e.getValue() );
						}
					};
				}
			};
		} else {
			final ArrayList< Member > list = new ArrayList<>();
			final Iterator< Map.Entry< String, Element > > it = this.members.iterator();
			while ( it.hasNext() ) {
				final Map.Entry< String, Element > e = it.next();
				list.add( new StdMember( e.getKey(), e.getValue() ) );
			}
			return list;
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Name - Imperative Methods
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void setName( String _name ) {
		this.name = Objects.requireNonNull( _name );
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Attributes - Imperative Methods
	/////////////////////////////////////////////////////////////////////////////////////////////

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
	
	@Override
	public void addFirstValue( String key, String value ) {
		//	TODO: unit test
		final List< String > values = this.getValuesAsList( key, true, true );
		values.add( 0, value );		
	}	

	@Override
	public String removeFirstValue( String selector ) {
		//	TODO: unit test
		return this.removeFirstValue( selector, null );
	}
	
	@Override
	public String removeFirstValue( String selector, String otherwise ) {
		//	TODO: unit test
		final List< String > children = this.getValuesAsList( selector, true, true );
		return children.isEmpty() ? otherwise : children.remove( 0 );		
	}

	public String removeLastValue( String selector ) {
		//	TODO: unit test
		return this.removeLastValue( selector, null );
	}
	
	@Override
	public String removeLastValue( String selector, String otherwise ) {
		//	TODO: unit test
		final List< String > children = this.getValuesAsList( selector, true, true );
		if ( children.isEmpty() ) {
			return otherwise;
		} else {
			return children.remove( children.size() - 1 );
		}
	}

	@Override
	public void setAttributes( final MultiMap< String, String > attributes ) {
		this.attributes = this.attributes.clearAllEntries().addAllEntries( attributes.entriesToList() );
	}
	

	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Members - Imperative Methods
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void setChild( String key, Element child  ) {
		this.members = this.members.updateValue( key, 0, child );
	}
	
	@Override
	public void setChild( String key, int position, Element child  ) {
		this.members = this.members.updateValue( key, position, child );
	}
	
	@Override
	public void setChildren( String key, Iterable< Element > children ) {
		this.members = this.members.clearAllEntries();
		for ( Element v : children ) {
			this.members = this.members.add( key, v );
		}
	}

	@Override
	public void addLastChild( String selector, Element e ) {
		this.members = this.members.add( selector, e );
	}

	@Override
	public void addFirstChild( String selector, Element e ) {
		//	TODO: unit test
		final List< Element > children = this.getChildrenAsList( selector, true, true );
		children.add( 0, e );		
	}

	@Override
	public Element removeFirstChild( String selector, Element otherwise ) {
		//	TODO: unit test
		final List< Element > children = this.getChildrenAsList( Objects.requireNonNull( selector ), true, true );
		return children.isEmpty() ? otherwise : children.remove( 0 );		
	}

	@Override
	public Element removeLastChild( String selector, Element otherwise ) {
		//	TODO: unit test
		final List< Element > children = this.getChildrenAsList( Objects.requireNonNull( selector ), true, true );
		if ( children.isEmpty() ) {
			return otherwise;
		} else {
			return children.remove( children.size() - 1 );
		}
	}

	@Override
	public void setMembers( final MultiMap< String, Element > _members ) {
		this.members = this.members.clearAllEntries().addAllEntries( _members.entriesToList() );
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Primitive Values
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean isIntValue() {
		return this.hasName( INT_ELEMENT_NAME ) && this.attributes.hasKey( VALUE_KEY_FOR_LITERAL_CONSTANTS );
	}

	@Override
	public Long getIntValue() {
		return this.getIntValue( false, null );
	}

	@Override
	public Long getIntValue( boolean allowOutOfRange ) {
		return this.getIntValue( allowOutOfRange, null );
	}

	@Override
	public Long getIntValue( Long otherwise ) {
		return this.getIntValue( false, otherwise );
	}

	@Override
	public Long getIntValue( boolean allowOutOfRange, Long otherwise ) {
		if ( ! this.hasName( INT_ELEMENT_NAME ) ) return otherwise;
		String value = this.attributes.getElse( VALUE_KEY_FOR_LITERAL_CONSTANTS, null );
		if ( value == null ) return otherwise;
		try {
			return Long.parseLong( value );
		} catch ( NumberFormatException e ) {
			if ( value.matches( "[+-]?[0-9]+" ) ) {
				if ( allowOutOfRange ) {
					return otherwise;
				} else {
					throw e;
				}
			} else {
				return otherwise;
			}
		}
	}

	@Override
	public BigInteger getBigIntValue() {
		return this.getBigIntValue( null );
	}

	@Override
	public BigInteger getBigIntValue( boolean allowOutOfRange ) {
		return this.getBigIntValue( null );
	}

	@Override
	public BigInteger getBigIntValue( BigInteger otherwise ) {
		if ( ! this.hasName( INT_ELEMENT_NAME ) ) return otherwise;
		String value = this.attributes.getElse( VALUE_KEY_FOR_LITERAL_CONSTANTS, null );
		if ( value == null ) return otherwise;
		try {
			return new BigInteger( value );
		} catch ( NumberFormatException e ) {
			return otherwise;
		}
	}

	@Override
	public BigInteger getBigIntValue( boolean allowOutOfRange, BigInteger otherwise ) {
		return this.getBigIntValue( otherwise );
	}

	@Override
	public boolean isFloatValue() {
		if ( ! this.hasName( FLOAT_ELEMENT_NAME ) ) return false;
		String value = this.attributes.getElse( VALUE_KEY_FOR_LITERAL_CONSTANTS, null );
		if ( value == null ) return false;
		try {
			Double.parseDouble( value );
			return true;
		} catch ( NumberFormatException e ) {
			return false;
		}
	}

	@Override
	public Double getFloatValue() {
		return this.getFloatValue( null );
	}

	@Override
	public Double getFloatValue( Double otherwise ) {
		if ( ! this.hasName( FLOAT_ELEMENT_NAME ) ) return otherwise;
		String value = this.attributes.getElse( VALUE_KEY_FOR_LITERAL_CONSTANTS, null );
		if ( value == null ) return otherwise;
		try {
			return Double.parseDouble( value );
		} catch ( NumberFormatException e ) {
			return otherwise;
		}
	}

	@Override
	public boolean isStringValue() {
		return this.hasName( STRING_ELEMENT_NAME ) && this.attributes.hasKey( VALUE_KEY_FOR_LITERAL_CONSTANTS );
	}

	@Override
	public String getStringValue() {
		return this.getStringValue( null );
	}

	@Override
	public String getStringValue( String otherwise ) {
		return this.attributes.getElse( VALUE_KEY_FOR_LITERAL_CONSTANTS, otherwise );
	}

	@Override
	public boolean isBooleanValue() {
		return this.hasName( BOOLEAN_ELEMENT_NAME ) && this.attributes.hasKey( VALUE_KEY_FOR_LITERAL_CONSTANTS );
	}

	@Override
	public Boolean getBooleanValue() {
		return this.getBooleanValue( null );
	}

	@Override
	public Boolean getBooleanValue( Boolean otherwise ) {
		String b = this.attributes.getElse( VALUE_KEY_FOR_LITERAL_CONSTANTS, null );
		if ( b == null ) return otherwise;
		try {
			return Boolean.parseBoolean( b );
		} catch ( Exception e ) {
			return otherwise;
		}
	}
	
	@Override
	public boolean isNullValue() {
		return this.hasName( NULL_ELEMENT_NAME ) && this.attributes.hasKey( VALUE_KEY_FOR_LITERAL_CONSTANTS );
	}
	
	@SuppressWarnings("null")
	@Override
	public Void getNullValue() {
		return null;
	}
	
	@SuppressWarnings("null")
	@Override
	public <T> T getNullValue( T otherwise ) {
		return this.hasName( NULL_ELEMENT_NAME ) && this.attributes.hasKey( VALUE_KEY_FOR_LITERAL_CONSTANTS ) ? (T)null : otherwise;
	}
	
	@Override
	public boolean isObject()
	{
		return this.hasName( OBJECT_ELEMENT_NAME );
	}
	
	@Override
	public boolean isArray()
	{
		return this.hasName( ARRAY_ELEMENT_NAME );
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//	Equality
	/////////////////////////////////////////////////////////////////////////////////////////////

	public boolean equals( final Object object ) {
		if ( object == null ) return false;
		if ( ! ( object instanceof Element ) ) return false; 
		final Element that = (Element)object;
		if ( ! this.hasName( that.getName() ) ) return false;
		if ( ! this.getAttributesAsMultiMap( true, false ).equals( that.getAttributesAsMultiMap( true, false ) ) ) return false;
		if ( ! this.getMembersAsMultiMap( true, false ).equals( that.getMembersAsMultiMap( true, false ) ) ) return false;
		return true;
	}
	
}
