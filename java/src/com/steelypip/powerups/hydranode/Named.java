package com.steelypip.powerups.hydranode;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public interface Named {
	
	/**
	 * Returns the name of a MinXML element. It is not 
	 * guaranteed that this is interned but it is guaranteed
	 * to be non-null.
	 * 
	 * @return the element name
	 */
	default public @NonNull String getName() { return this.getInternedName(); }
	
	/**
	 * Returns the interned name of a MinXML element. Guaranteed to be a non-null
	 * interned string.
	 * 
	 * @return interned version of the element name
	 */
	public @NonNull String getInternedName();
	
	
	/**
	 * Returns true if the name of the element is equal to the
	 * given string. Equivalent to this.getName().equals( name ). Note
	 * that although the name may never be null, we allow
	 * testing against null for convenience even though that
	 * test will always be false.
	 * 
	 * @param name the name we are checking
	 * @return true if the element has that name
	 */
	default boolean hasName( @Nullable String name ) { return this.getName().equals( name ); }
	
	/**
	 * Changes the name of the element to the given string. Note
	 * that an implementation of MinXML is not obliged to support
	 * this method and may throw an exception. The name may not be
	 * null, otherwise a {link RuntimeException} is thrown.
	 *  
	 * @param name the name we are setting
	 * @throws UnsupportedOperationException if the implementing class cannot support this method
	 */
	default public void setName( @NonNull String x ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	
	
}
