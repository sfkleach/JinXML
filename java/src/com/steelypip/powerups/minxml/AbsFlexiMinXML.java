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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.EmptyMap;

/**
 * This is a support class for FlexiMinXML that provides a 
 * layer for managing the allocation of the TreeMap and the
 * ArrayList.
 */
public abstract class AbsFlexiMinXML extends AbsMinXML {

	protected @NonNull String name;
	protected TreeMap< String, String > attributes;
	protected ArrayList< @NonNull MinXML > children;
	
	public AbsFlexiMinXML( @NonNull String name ) {
		this.name = name;
	}

	public @NonNull String getName() {
		return this.name;
	}

	@SuppressWarnings("null")
	public void setName( final @NonNull String name ) {
		this.name = name.intern();
	}

	@Override
	public Map< String, String > asMap() {
		if ( this.attributes == null ) {
			this.attributes = new TreeMap<>();
		}
		return this.attributes;
	}
		
	static Map< String, String > empty_map = new EmptyMap< String, @Nullable String >();

	@Override
	public Map< String, String > quickGetAttributes() {
		return this.attributes == null ? empty_map : this.attributes;
	}

	@Override
	public List< @NonNull MinXML > toList() {
		if ( this.children == null ) {
			this.children = new ArrayList< @NonNull MinXML >();
		}
		return this.children;
	}

}