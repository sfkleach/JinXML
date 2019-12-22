package com.steelypip.powerups.jinxml.stdmodel;

import java.util.ArrayDeque;
import java.util.IdentityHashMap;
import java.util.Iterator;

import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Itinerary;
import com.steelypip.powerups.jinxml.Visit;


public class DequeItinerary implements Itinerary {

	final ArrayDeque< Itinerary.Item > queue = new ArrayDeque<>();
	final IdentityHashMap< Element, Visit > visit_map = new IdentityHashMap<>();

	@Override
	public boolean hasNext() {
		for (;;) {
			if ( queue.isEmpty() ) {
				return false;
			} else if ( queue.getFirst() instanceof Visit ) {
				return true;
			} else {
				Action action = (Action)queue.removeFirst();
				action.act( this );
			}
		}
	}

	@Override
	public Visit next() {
		if ( this.hasNext() ) {
			return (Visit)this.queue.removeFirst();
		} else {
			GOT HERE
		}
	}
	
	@Override
	public @Nullable Visit tryNext() {
		//	TODO: complete this
		return null;
	}

	@Override
	public void add( Element e ) {
		// TODO Auto-generated method stub
		throw new Alert( "Not implemented yet" );
	}

	@Override
	public void addElementToStart( Element e ) {
		Visit v = visit_map.get( e );
		if ( v == null ) {
			v = new StdVisit( this, e );
			visit_map.put( e, v );		
		}	
		this.queue.addFirst( v );
		v.incrementAdded();
	}

	@Override
	public void addElementToEnd( Element e ) {
		Visit v = visit_map.get( e );
		if ( v == null ) {
			v = new StdVisit( this, e );
			visit_map.put( e, v );		
		}	
		this.queue.addLast( v );
		v.incrementAdded();	
	}

	@Override
	public void addActionToStart( Action a ) {
		this.queue.addFirst( a );
	}

	@Override
	public void addActionToEnd( Action a ) {
		this.queue.addLast( a );		
	}

	@Override
	public void defer( Visit v ) {
		this.defer( v, false );
	}

	@Override
	public void defer( Visit v, boolean startVsEnd ) {
		if ( startVsEnd ) {
			this.queue.addFirst( v );
		} else {
			this.queue.addLast( v );
		}
		v.incrementDeferrals();
		v.decrementVisits();
		//	We do not have to adjust #adds since the increment/decrement cancel out.
	}
	
	
}
