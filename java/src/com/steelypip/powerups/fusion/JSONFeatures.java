package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.Nullable;

public interface JSONFeatures {

	@Nullable Long integerValue();
	default boolean isInteger() { return this.integerValue() != null; }
	default long integerValue( long otherwise ) { Long n = this.integerValue(); return n != null ? n : otherwise; }
	
	@Nullable Double floatValue();
	default boolean isFloat() { return this.floatValue() != null; }
	default double floatValue( double otherwise ) { Double n = this.floatValue(); return n != null ? n : otherwise; }

	@Nullable String stringValue();
	default boolean isString() { return this.stringValue() != null; }
	default String stringValue( String otherwise ) { String n = this.stringValue(); return n != null ? n : otherwise; }
	
	@Nullable Boolean booleanValue();
	default boolean isBoolean() { return this.booleanValue() != null; }
	default boolean booleanValue( boolean otherwise ) { Boolean n = this.booleanValue(); return n != null ? n : otherwise; }
	
	boolean isNull();
	boolean isArray();
	boolean isObject();
	
	boolean isJSONItem();
	
}
