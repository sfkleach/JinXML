package com.steelypip.powerups.common;

import java.util.Map;

public class CmpPair< T extends Comparable< T >, U extends Comparable< U > > extends StdPair< T, U > implements ComparablePair< T, U >, Map.Entry< T, U > {

	public CmpPair( T field1, U field2 ) {
		super( field1, field2 );
	}
	
	static public <C extends Comparable< C> > int nullSafeCompareTo( C left, C right ) {
		if ( left == null ) {
			return right == null ? 0 : 1;
		} else if ( right == null ) {
			return -1;
		} else {
			return left.compareTo( right );
		}
	}

	@Override
	public int compareTo( ComparablePair< T, U > that ) {
		if ( that == null ) return -1;
		int cmp1 = nullSafeCompareTo( this.getFirst(), that.getFirst() );
		if ( cmp1 != 0 ) return cmp1;
		return nullSafeCompareTo( this.getSecond(), that.getSecond() );
	}

}

