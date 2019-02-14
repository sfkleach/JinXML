package com.steelypip.powerups.charrepeater;

/**
 * A marker interface that provides information about mutability.
 */
public interface Immutable {

	default boolean isMutable() { return false; }

}
