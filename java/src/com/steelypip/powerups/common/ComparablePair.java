package com.steelypip.powerups.common;

public interface ComparablePair< T extends Comparable< T >, U extends Comparable< U > > extends Pair< T, U >, Comparable< ComparablePair< T, U > > {

}
