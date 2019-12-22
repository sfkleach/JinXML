## Introduction to Itinerary(s), Automation(s) and Visit(s)

The traveral of a tree-of-elements is relatively simple. But the introduction of 
`let` expressions allowed elements to form a directed-acyclic graph, which means
that a traveral could revisit the same node twice. (And we plan to support directed
cyclic graphs in the near future too.) Itineraries allow us to cope with these
more complicated situations.

An Itinerary acts like an iterator over Visits, where Visits identify an Element
being visited, a Path to the Element, the number of times that the Element has 
already been visited and the number of times the visit has been deferred. 

The sequence of visits in an Itinerary can be generated automatically or visits
can be added under programmer control. And actions can be interspersed between 
visits, so that side-effects can be made to happen in the appropriate sequence.

Automation works by intercepting adding Elements to the start or end of an 
Itinerary. It can then translate that into adding a Visit or an Action onto
the Itinerary.

# class DequeItinerary
An deque-itinerary maintains an ordered queue of items which are Visits or Actions. The
order of items in the queue are what determines the iteration order. Items can be
added either to the front or back of the queue.

## Constructor

* Function ```newDequeItinerary( Boolean includeRevisits = false ) -> DequeItinerary``` - returns a positional itinerary that will only revisit the same element twice if `includeRevisits` was true. It will have no automation.

* Function ```newDepthFirstItinerary( Boolean includeRevisits = false ) -> Itinerary``` - returns an  positional itinerary with automation. When elements are added to the list, they are wrapped as actions that, when run, will add visits to the children (as an automation) ahead of visits to the element itself. It will only revisit the same element twice if `includeRevisits` was true.

* Function ```newBreadthFirstItinerary( Boolean includeRevisits = false ) -> Itinerary``` - returns a positional itinerary with automation. When elements are added to the list, they are wrapped as actions that, when run, will add a visit to itself ahead of visits to the children (as an automation). It will only revisit the same element twice if `includeRevisits` was true.

## Iteration

* Method ```iterator() -> Iterator< Visit >``` - returns an iterator that repeatedly yields the next visit in the itinerary. If the start of the Itinerary is an action, this is removed and run. This may insert new items at the start of the iterinary, so this process is repeated until the start is a visit or the itinerary is empty. 

* Method ```hasNext() -> Boolean``` - runs all leading actions until either the itinerary has a visit at the start or it is empty, returning `true` if it is non-empty else `false`.

* Method ```next() -> Visit``` - runs all leading actions until either the itinerary has a visit at the start or it is empty, returning the Visit if it is non-empty else raises an exception.

* Method ```tryNext() -> Visit?```- runs all leading actions until either the itinerary has a visit at the start or it is empty, returning the Visit if it is non-empty else `null`.

## Adding Visits and Actions (which inherit from a common interface e.g. ItineraryItem)

* Method ```add( Element... e )``` - runs the automation for adding to the (remaining) itinerary i.e. i.e. `automation.add( e, this )`. If includeRevisits is disabled then an attempt to revisit an element has no effect.

* Method ```addElementsToStart( Element... e )``` - adds a an element as a Visit to the start of the (remaining) itinerary. If includeRevisits is disabled then an attempt to revisit an element has no effect.

* Method ```addElementsToEnd( Element... e )``` - adds a an element as a Visit to the end of the (remaining) itinerary. If includeRevisits is disabled then an attempt to revisit an element has no effect.

* Method ```addActionsToStart( Action... a )``` - adds an action to the start of the (remaining) itinerary. 

* Method ```addActionsToEnd( Action... a )``` - adds an action to the end of the (remaining) itinerary.

* Method ```defer( Visit v, Boolean startOrEnd = false )``` - adds a Visit to the start/end of the (remaining) itinerary, depending on whether the startOrEnd flag is true/false and bumps the deferred count. 

## Modifying Dynamic Behaviour

* Method ```setAutomation( Automation a ) -> Itinerary``` - sets the automation and returns the modified Itinerary.

* Method ```setIncludeRevisits( Boolean includeRevisits ) -> Itinerary``` - sets the `includeRevisits` flag and returns the modified Itinerary.


# abstract class Automation

* Abstract method ```add( Element e, Itinerary itn )``` - adds an element `e` to an itinerary `itb`. The automation is at liberty to add visits and actions to achieve depth-first, breadth-first or even priority-first searches. 

* Automation ```DEPTH_FIRST``` - adds function that adds an action that queues the children and then the parent.
* Automation ```BREADTH_FIRST``` - adds function that adds the parent and queues the children. 

# class Visit

## Accessors

* Method ```getItinerary() -> Itinerary``` returns the parent itinerary.

* Method ```countVisits( Boolean includeDeferrals = false ) -> Int``` - how many times this Visit has been returned by the Itinerary, including deferred visits if `includeDeferrals` is true, otherwise excluding them.

* Method ```countAdded( Boolean includeDeferrals = false ) -> Int``` - returns the number of times this visit was 'added' to the itinerary, which is greater than or equal to the number of Visits. If the `includeRevisits` is disabled then the countVisits will never be bigger than 1 but the countAdded will be the number of times that the element was added.

* Method ```countDeferrals() -> Int``` - how many times this Visit has been deferred. A deferred visit will decrement the number of visits and the number of adds and increment the number of deferrals.

## Retrieving other Visits

* Method ```getVisit( Element e, Visit orElse = null ) -> Visit?``` - returns the Visit associated with element `e` if it has been created, otherwise returns `orElse`.

* Method ```getVisitsToParents() -> Set<Visit>``` - returns a set of distinct visits that have already been created whose elements are parents of this visit's element. N.B. There may be unvisited elements who are parental elements but these will not be included.

## Deferral

* Method ```defer( Boolean startOrEnd = false )``` - same as `this.getItinerary().defer( this, startOrEnd )`

## Results

* Method ```setResult( Object? result )``` - this sets the result and also adds to the daughtersResult list.

* Method ```getResult() -> Optional< Object? >``` - this returns a non-empty optional if the result has been set, otherwise it returns an empty optional.

* Method ```getResult( Element e ) -> Optional< Object? >``` - returns the result associated with visit to element `e` if the visit exists, otherwise returns an empty-optional.

