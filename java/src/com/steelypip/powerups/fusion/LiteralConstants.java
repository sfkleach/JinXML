package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

/**
 * This interface provides overrideable values for creating 
 * element names and attributes of literal constants
 * 
 * Must return INTERNED values for fast comparison.
 */
public interface LiteralConstants {
	
	default @NonNull String nameConstant() { return "constant"; }
	default @NonNull String keyValue() { return "value"; }
	default @NonNull String keyType() { return "type"; }
	default @NonNull String constTypeBoolean() { return "boolean"; }
	default @NonNull String constTypeNull() { return "null"; }
	default @NonNull String constValueNull() { return "null"; }
	default @NonNull String constTypeString() { return "string"; }
	default @NonNull String constTypeFloat()  { return "float"; }
	default @NonNull String constTypeInteger()  { return "integer"; }
	default @NonNull String constValueTrueOrFalse( boolean b ) { return b ? "true" : "false"; }
	default @NonNull String constTypeArray() { return "array"; }
	default @NonNull String constTypeObject() { return "object"; }
			
}