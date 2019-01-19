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
package com.steelypip.powerups.hydranode;

import java.util.Map;

public interface HydraNode< Key extends Comparable< Key >, AttrValue, Field extends Comparable< Field >, ChildValue > 
extends 
	Named, 
	MultiAttributes< Key, AttrValue >, 
	MultiLinks< Field, ChildValue >, 
	Iterable< Map.Entry< Field, ChildValue > > 
{
	
	/**
	 * This method signals that there will be no further updates to an element, at least for a while, 
	 * and the implementation should consider this a good opportunity to compact the space used
	 * by this element and all child elements, including any shared elements. 
	 */
	default void trimToSize() {
		//	Skip.
	}
	
	boolean equals( Object obj );
	
}
