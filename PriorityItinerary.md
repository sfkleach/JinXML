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

# class PriorityItinerary
An itinerary maintains an ordered queue of items which are Visits or Actions. The
order of items 

However the programmer can elect to retrieve items in priority order by setting
the ``byPriority`` flag. Each item has a priority, which is either explicitly 
assigned or calculated on demand. When this flag is set, it does not matter whether
items are inserted at the front or back of the queue, they will always be retrieved
in priority order.


## Constructor

* Function ```newPriorityItinerary( Boolean includeRevisits = false, DefaultPriority dpriority = DEFAULT_PRIORITY_ZERO ) -> PriorityItinerary``` - returns a priority-based itinerary that will only revisit the same element twice if `includeRevisits` was true. It will have no automation.

## Iteration

* Method ```iterator() -> Iterator< Visit >``` - returns an iterator that repeatedly yields the next visit in the itinerary. If the start of the Itinerary is an action, this is removed and run. This may insert new items at the start of the iterinary, so this process is repeated until the start is a visit or the itinerary is empty. 

* Method ```hasNext() -> Boolean``` - runs all leading actions until either the itinerary has a visit at the start or it is empty, returning `true` if it is non-empty else `false`.

* Method ```next() -> Visit``` - runs all leading actions until either the itinerary has a visit at the start or it is empty, returning the Visit if it is non-empty else raises an exception.

* Method ```tryNext() -> Visit?```- runs all leading actions until either the itinerary has a visit at the start or it is empty, returning the Visit if it is non-empty else `null`.

## Adding Visits and Actions (which inherit from a common interface e.g. ItineraryItem)

* Method ```add( Element... e )``` - runs the automation for adding to the (remaining) itinerary i.e. i.e. `automation.add( e, this )`. If preferFront is true then they will be inserted before any previous items with equal priority, otherwise after. If includeRevisits is disabled then an attempt to revisit an element has no effect.

* Method ```addElement( Element e, Float? priority = null, Boolean? preferFront = true )``` - adds an element `e` as a Visit to the (remaining) itinerary with the given priority. If the priority is null then the default-priority is used to assign a priority. If preferFront is true then they will be inserted before any previous items with equal priority, otherwise after. If includeRevisits is disabled then an attempt to revisit an element has no effect.

* Method ```addAction( Action... a, Float? priority = null, Boolean? preferFront = true )``` - adds an action to the itinerary with the given priority. If the priority is null then the default-priority is used to assign a priority. If preferFront is true then they will be inserted before any previous items with equal priority, otherwise after. If includeRevisits is disabled then an attempt to revisit an element has no effect.

* Method ```deferVisit( Visit v, Float? priority = null, Boolean? preferFront = true )``` - adds a Visit to the (remaining) itinerary with the given priority and bumps the deferred count. Note that this would mainly be useful when further elements are added ahead of it. If the priority is null then the default-priority is used to assign a priority. If preferFront is true then they will be inserted before any previous items with equal priority, otherwise after. If includeRevisits is disabled then an attempt to revisit an element has no effect.

## Modifying Dynamic Behaviour

* Method ```setAutomation( Automation a ) -> Itinerary``` - sets the automation and returns the modified Itinerary.

* Method ```setIncludeRevisits( Boolean includeRevisits ) -> Itinerary``` - sets the `includeRevisits` flag and returns the modified Itinerary.


# abstract class DefaultPriority

* Method ```visitPriority( Visit v ) -> Float?``` - returns the priority of a Visit `v`. 

* Method ```actionPriority( Action a ) -> Float?``` - returns the priority of an Action `a`. 

* Function ```newFixedPriority( Float n = 0.0 ) -> DefaultPriority``` - returns a DefaultPriority that always assigns items the same priority of `n`.

# abstract class Automation

* Abstract method ```add( Element e, Itinerary itn )``` - adds an element `e` to an itinerary `itb`. The automation is at liberty to add visits and actions to achieve depth-first, breadth-first or even priority-first searches. 

* Automation ```DEPTH_FIRST``` - adds function that adds an action that queues the children and then the parent.
* Automation ```BREADTH_FIRST``` - adds function that adds the parent and queues the children. 

# class Visit

## Accessors

* Method ```motherVisit() -> Visit?``` - returns the visit that was current when this visit was added to the itinerary.

* Method ```daughtersReturned() -> List<Object>``` - returns a list of objects returned by daughter visits.

* Method ```countVisits() -> Int``` - how many times this Visit has been returned by the Itinerary, excluding deferrals.

* Method ```countDeferrals() -> Int``` - how many times this Visit has been deferred.

* Method ```deferToStart( Float? priority = null s)``` - 
