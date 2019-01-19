package com.steelypip.powerups.minxml;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.AbstractList;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.annotation.NonNull;

/**
 * BadMinXML objects are used to fill the role of sentinels.
 * They can't do anything except stand-in for a MinXML object
 * to satisfy the type-checking criteria.
 * 
 * @author steve
 *
 */
class BadMinXML extends AbstractList< @NonNull MinXML > implements MinXML {

	@Override
	public @NonNull String getName() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasName( String name ) {
		throw new IllegalStateException();
	}

	@Override
	public void setName( @NonNull String name ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public String getAttribute( String key ) {
		throw new IllegalStateException();
	}

	@Override
	public String getAttribute( String key, String value_otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAttribute() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAttribute( String key ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAttribute( String key, String value ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasntAttribute() {
		throw new IllegalStateException();
	}

	@Override
	public int sizeAttributes() {
		throw new IllegalStateException();
	}

	@Override
	public Iterable< String > keys() {
		throw new IllegalStateException();
	}

	@Override
	public Iterable< String > asMapKeys() {
		throw new IllegalStateException();
	}

	@Override
	public Iterable< Entry< String, String >> entries() {
		throw new IllegalStateException();
	}

	@Override
	public Map< String, String > getAttributes() {
		throw new IllegalStateException();
	}

	@Override
	public Map< String, String > asMap() {
		throw new IllegalStateException();
	}

	@Override
	public Iterable< Entry< String, String >> asMapEntries() {
		throw new IllegalStateException();
	}

	@Override
	public void putAttribute( String key, String value )
			throws UnsupportedOperationException {
		throw new IllegalStateException();	
	}

	@Override
	public void putAllAttributes( Map< String, String > map )
			throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void clearAttributes() throws UnsupportedOperationException {
		throw new IllegalStateException();	
	}

	@Override
	public void trimToSize() {
		throw new IllegalStateException();	}

	@Override
	public boolean isntEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void print( PrintWriter pw ) {
		throw new IllegalStateException();
	}

	@Override
	public void print( Writer w ) {
		throw new IllegalStateException();	}

	@Override
	public void prettyPrint( PrintWriter pw ) {
		throw new IllegalStateException();	
	}

	@Override
	public void prettyPrint( Writer w ) {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull MinXML shallowCopy() {
		throw new IllegalStateException();	
	}

	@Override
	public @NonNull MinXML deepCopy() {
		throw new IllegalStateException();	
	}

	@Override
	public @NonNull MinXML get( int index ) {
		throw new IllegalStateException();	
	}

	@Override
	public int size() {
		throw new IllegalStateException();	
	}


}