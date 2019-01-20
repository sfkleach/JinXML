package com.steelypip.powerups.jinxml;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.util.multimap.MultiMap;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.EmptyMutablePMMap;

public class FlexiElement implements Element {
	
	protected String name;
	protected PhoenixMultiMap< String, String > attributes = EmptyMutablePMMap.getInstance();
	protected PhoenixMultiMap< String, Element > links = EmptyMutablePMMap.getInstance();
	
	public FlexiElement( String _name ) {
		this.name = _name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int countAttributes() {
		return this.attributes.sizeEntries();
	}

	@Override
	public MultiMap< String, String > getAttributesAsMultiMap() {
		this.attributes 
		return null;
	}

	@Override
	public MultiMap< String, String > getAttributesAsMultiMap(
			boolean mutable ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiMap< String, String > getAttributesAsMultiMap( boolean view,
			boolean mutable ) {
		// TODO Auto-generated method stub
		return null;
	}

}
