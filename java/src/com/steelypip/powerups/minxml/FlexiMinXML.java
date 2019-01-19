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
package com.steelypip.powerups.minxml;

import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.EmptyIterable;
import com.steelypip.powerups.common.EmptyIterator;
import com.steelypip.powerups.common.EmptyList;

/**
 * This implementation provides a full implementation of all the
 * mandatory and optional methods of the MinXML interface. It aims
 * to strike a balance between fast access, update and reasonable
 * compactness in the most important cases. A TreeMap is used to
 * track attributes and an ArrayList to track children; these provide
 * good performance at the expense of space. However, when there
 * are no attributes the TreeMap is not allocated and when there are
 * no children the ArrayList is not allocated; these two cases are
 * so common that the reduction in space is (typically) significant. 
 */
public class FlexiMinXML extends AbsFlexiMinXML {
	
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
	public FlexiMinXML( final @NonNull String name ) {
		//	We intern the name purely to help save space. Unfortunately we
		//	can't really take advantage of it in any other way.
		super( name.intern() ); 
	}
	
	private FlexiMinXML( final @NonNull MinXML element ) {
		super( element.getName() );
		this.putAllAttributes( element.asMap() );
		this.addAll( element );
	}

	public static MinXML fromString( final String input ) {
		return new MinXMLParser( new StringReader( input ) ).readElement();
	}
	
	/**
	 * Creates a copy of the top-level node of the given element.
	 * @param element the element to copy
	 * @return the copy
	 */
	public static @NonNull FlexiMinXML shallowCopy( @NonNull MinXML element ) {
		return new FlexiMinXML( element );
	}
		
	public static @NonNull FlexiMinXML deepCopy( @NonNull MinXML element ) {
		final FlexiMinXML result = new FlexiMinXML( element.getName() );
		for ( Map.Entry< String, String > e : element.entries() ) {
			result.putAttribute( e.getKey(), e.getValue() );
		}
		for ( MinXML kid : element ) {
			result.add( deepCopy( kid ) );
		}
		return result;
	}

	//////////////////////////////////////////////////////////////////////////////////
	//	Overrides that avoid allocating the TreeMap
	//////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void trimToSize() {
		if ( this.children != null ) this.children.trimToSize();
	}
	
	@Override
	public String getAttribute( final String key ) {
		return this.attributes == null ? null : super.getAttribute( key );
	}

	@Override
	public boolean hasAttribute() {
		return this.attributes != null && super.hasAttribute();
	}

	@Override
	public boolean hasAttribute( final String key ) {
		return this.attributes != null && super.hasAttribute( key );
	}

	@Override
	public boolean hasAttribute( final String key, final String value ) {
		return this.attributes == null ? value == null : super.hasAttribute( key, value );
	}

	@Override
	public boolean hasntAttribute() {
		return this.attributes == null || super.hasntAttribute();
	}

	@Override
	public Iterable< String > keys() {
		if ( this.attributes == null ) {
			return new EmptyList< String >();
		} else {
			return super.keys();
		}
	}

	@Override
	public int sizeAttributes() {
		return this.attributes == null ? 0 : super.sizeAttributes();
	}
	
	static Iterable< Map.Entry< String, String > > empty_entries_iterable = new EmptyIterable< Map.Entry< String, String > >();
	
	@Override
	public Iterable< Entry< String, String >> entries() {
		if ( this.attributes == null ) {
			return empty_entries_iterable;
		} else {
			return super.entries();
		}
	}	

	
	//////////////////////////////////////////////////////////////////////////////////
	//	Overrides that avoid allocating the ArrayList
	//////////////////////////////////////////////////////////////////////////////////

	@Override
	public void clear() {
		if ( this.attributes != null ) {
			super.clear();
		}
	}

	@Override
	public boolean contains( final Object o ) {
		return this.children != null && super.contains( o );
	}

	@Override
	public boolean containsAll( final Collection< ? > c ) {
		return this.children != null && super.containsAll( c );
	}

	@Override
	public int indexOf( final Object o ) {
		return this.children == null ? -1 : super.indexOf( o );
	}

	@Override
	public boolean isEmpty() {
		return this.children == null || this.children.isEmpty();
	}
	
	static Iterator< @NonNull MinXML > empty_iterator = new EmptyIterator< @NonNull MinXML >();

	@Override
	public Iterator< @NonNull MinXML > iterator() {
		if ( this.children == null ) {
			return empty_iterator;
		} else {
			return super.iterator();
		}
	}

	@Override
	public int lastIndexOf( final Object o ) {
		return this.children == null ? -1 : super.lastIndexOf( o );
	}

	@Override
	public int size() {
		return this.children == null ? 0 : super.size();
	}


	@Override
	public boolean isntEmpty() {
		return this.children == null && ! this.children.isEmpty();
	}
	
}
