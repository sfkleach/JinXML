package com.steelypip.powerups.hydraxml;

import com.steelypip.powerups.common.Pair;

public interface Link< Field, Child > extends Pair< Field, Child > {
	
	Field getField();
	int getFieldIndex();
	Child getChild();

	default Field getFirst() {
		return this.getField();
	}
	
	default Child getSecond() {
		return this.getChild();
	}
	
	default void setFirst( Field x ) {
		throw new UnsupportedOperationException();
	}
	
	default void setSecond( Child x ) {
		throw new UnsupportedOperationException();
	}
}

