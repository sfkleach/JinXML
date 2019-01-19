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
package com.steelypip.powerups.common;

import java.util.AbstractList;
import java.util.Collection;

/**
 * Implements the special case of an empty immutable list. 
 * @param <T> the type yielded by the iterator (if it ever yielded any)
 */
public class EmptyList< T > extends AbstractList< T > {

	@Override
	public T get( final int index ) {
		throw new IndexOutOfBoundsException();
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean contains( Object o ) {
		return false;
	}

	@Override
	public boolean containsAll( Collection< ? > c ) {
		return false;
	}	
	
}
