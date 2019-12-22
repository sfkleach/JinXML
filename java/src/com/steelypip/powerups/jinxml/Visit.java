package com.steelypip.powerups.jinxml;

public interface Visit extends Itinerary.Item {
	
	int countAdded();
	int countAdded( boolean includeDeferrals );
	int countVisits();
	int countVisits( boolean includeDeferrals );
	int countDeferrals();
	
	void incrementAdded();
	void incrementVisits();
	void decrementVisits();
	void incrementDeferrals();


}
