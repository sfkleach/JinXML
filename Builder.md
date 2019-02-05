# Class Builder extends PushParser.Listener, Iterator< Element >

## General Methods

* Factory Constructor ```newBuilder( Boolean mutable = false, Boolean allowQueuing = false ) -> Builder``` - returns a factory object that can be used to construct new Elements. If the flag ```mutable``` is true, then the new values are constructed to be (deeply) mutable, otherwise (deeply) immutable. If ```allowQueuing``` is false, then no events are accepted after an Element is ready until the element is constructed by ```newInstance```, otherwise events are allowed.

* Method ```hasNext() -> Boolean``` - returns true if enough events have been received to construct an element.

* Method ```isInProgress() -> Boolean``` - return true if some events have been received but there are more open than close events. 

* Method ```next() -> Element``` - constructs the next Element if one is ready for construction, otherwise will raise an exception. Use ```this.hasNext()``` to determine whether it is safe to call this method.

* Method ```tryNext( Element? orElse = null ) -> Element?``` - if an Element is ready for construction it builds and returns it, otherwise it returns the value in ```orElse``` instead.

* Method ```snapshot() -> Element``` - all open states are automatically but temporarily completed;
if an Element is ready for construction after the auto-completion, it is constructed and the result will be returned, otherwise an exception is raised; the temporarily closed states are restored to their previous state. Use ```this.isInProgress()``` to check whether it is safe to call this method.

* Method ```trySnapshot( Element? orElse = null ) -> Element?``` - as for ```snapshot``` but never raises an exception but returns the value in```orElse``` instead.

* Method ```include( Element value, Boolean checkMutability = false )``` - adds and shares this Element into the in-progress build. The mutability of the included value is checked depending on the ```checkMutability``` flag. It is **not** an error to mix mutability this way but an occasionally important feature.

* Method ```reconstruct( Element value )``` - replays a series of events which would construct the Element supplied but does so inside the in-progress build. This is effectively the same as ```this.processEvents( value.toEventIterator() )```
a deep copy.

* Method ```processEvent( PushParser.Event event )``` - processes a PushParser event using the appropriate method.

* Method ```processEvents( Iterator< PushParser.Event > events )``` - processes a series of events, which is effectively the same as ```events.forEach( e =>> this.processEvent( e ) )```.

## Event Handling Methods

* Method ```startTagEvent( String key )```
* Method ```attributeEvent( String key, String value, Boolean solo = true )```
* Method ```endTagEvent( String? key = null )```
* Method ```startArrayEvent()```
* Method ```endArrayEvent()```
* Method ```startObjectEvent()```
* Method ```startEntryEvent( String key = null, Boolean solo = true )```
* Method ```endEntryEvent()```
* Method ```endObjectEvent()```
* Method ```intEvent( String value )```
* Method ```floatEvent( String value )```
* Method ```stringEvent( String value )```
* Method ```booleanEvent( String value )```
* Method ```nullEvent( String value )```
