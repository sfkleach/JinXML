package com.steelypip.powerups.jinxml.stdmodel;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Itinerary;
import com.steelypip.powerups.jinxml.Visit;

public class StdVisit implements Visit {

	int nvisits = 0;
	int nadded = 0;
	int ndeferrals = 0;
	
	final Itinerary itinerary;
	final Element element;
	
	public StdVisit( Itinerary itinerary, Element element ) {
		super();
		this.itinerary = itinerary;
		this.element = element;
	}

	public void incrementAdded() { this.nadded += 1; }
	public void incrementVisits() { this.nvisits += 1; }
	public void decrementVisits() { this.nvisits -= 1; }
	public void incrementDeferrals() { this.ndeferrals += 1; }

	@Override
	public int countAdded() {
		return this.nadded;
	}

	@Override
	public int countAdded( boolean includeDeferrals ) {
		return this.nadded + ( includeDeferrals ? this.ndeferrals : 0 );
	}

	@Override
	public int countVisits() {
		return this.nvisits;
	}

	@Override
	public int countVisits( boolean includeDeferrals ) {
		return this.nvisits + ( includeDeferrals ? this.ndeferrals : 0 );
	}

	@Override
	public int countDeferrals() {
		return this.ndeferrals;
	}
	
	
}
