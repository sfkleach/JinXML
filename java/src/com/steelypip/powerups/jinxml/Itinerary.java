package com.steelypip.powerups.jinxml;

import java.util.Iterator;

import org.eclipse.jdt.annotation.Nullable;

public interface Itinerary extends Iterator< Visit > {

	public interface Item {

	}
	
	public interface Action extends Item {
		void act( Itinerary itn );
	}
	
	@Nullable Visit tryNext(); 

	void add( Element e );
	
	void addElementToStart( Element e );
	void addElementToEnd( Element e );
	void addActionToStart( Itinerary.Action a );
	void addActionToEnd( Itinerary.Action a );
	
	void defer( Visit v );
	void defer( Visit v, boolean startOrEnd );

}
