package com.steelypip.powerups.hydraxml;

public class StdLink< Field, ChildValue > implements Link< Field, ChildValue > {
	
	Field field;
	int fieldIndex;
	ChildValue child;
	
	public StdLink( Field field, int fieldIndex, ChildValue child ) {
		super();
		this.field = field;
		this.fieldIndex = fieldIndex;
		this.child = child;
	}

	public Field getField() {
		return field;
	}

	public int getFieldIndex() {
		return fieldIndex;
	}

	public ChildValue getChild() {
		return child;
	}
	
}
