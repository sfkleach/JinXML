package com.steelypip.powerups.jinxml.stdparse;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;

/*************************************************************************
* Helper class for tracking field info
*************************************************************************/

public class SelectorInfo {
	
	private String name;
	public boolean solo;
	
	public SelectorInfo( String name, boolean solo ) {
		this.name = name;
		this.solo = solo;
	}
	
	@SuppressWarnings("null")
	public @NonNull String getSelector( @NonNull String tagName ) {
		return this.name == null ? tagName : this.name;
	}
	
	@SuppressWarnings("null")
	public @NonNull String getSelector() {
		if ( this.name == null ) throw new Alert( "'&' cannot be used in this position" );
		return this.name;
	}
	
	static @NonNull SelectorInfo DEFAULT = new SelectorInfo( "", false );
	
}
