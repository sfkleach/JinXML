package com.steelypip.powerups.fusion.jsonimpl;

import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.fusion.JSONFeatures;

public interface NullJSONFeatures extends JSONFeatures {

	@Nullable
	default public Long integerValue() {
		return null;
	}

	@Nullable
	default public Double floatValue() {
		return null;
	}

	@Nullable
	default public String stringValue() {
		return null;
	}

	@Nullable
	default public Boolean booleanValue() {
		return null;
	}

	default public boolean isNull() {
		return false;
	}

	default public boolean isArray() {
		return false;
	}

	default public boolean isObject() {
		return false;
	}

	@Override
	default boolean isJSONItem() {
		return true;
	}
	
}
