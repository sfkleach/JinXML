package com.steelypip.powerups.common;

import java.util.Map;

public interface Pair< T, U > extends Map.Entry< T, U > {

	T getFirst();

	void setFirst( T first );

	U getSecond();

	void setSecond( U second );
	
	default T getKey() { return this.getFirst(); }
	
	default U getValue() { return this.getSecond(); }
	
	@Override
	default U setValue( U value ) {
		U old_value = this.getSecond();
		this.setSecond( value );
		return old_value;
	}

}
