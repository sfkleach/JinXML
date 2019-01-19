package com.steelypip.powerups.hydraxml;

public class FlexiHydraXMLBuilder extends AbsHydraXMLBuilder {
	
	private static HydraXMLFactory DEFAULT_FACTORY = new FlexiHydraXMLFactory();
	private HydraXMLFactory _factory;
	private boolean is_making_mutable;
	
	public FlexiHydraXMLBuilder( final boolean is_making_mutable ) {
		super();
		this.is_making_mutable = is_making_mutable;
	}
	
	@Override
	public boolean isMakingMutable() {
		return this.is_making_mutable;
	}

	public FlexiHydraXMLBuilder() {
		this( true );
	}

	@Override
	public HydraXMLFactory factory() {
		if ( this._factory == null ) {
			this._factory = DEFAULT_FACTORY;
		}
		return this._factory;
	}

	
}
