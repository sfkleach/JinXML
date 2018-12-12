# What is the standard JOM Element - JinXML Object Model?

## Overview

DESCRIBE THE AIMS OF THE STANDARD OBJECT MODEL - SAME AS THE STANDARD PUSH PARSER. EXPLAIN MULTIMAP.

The following classes and methods illustrate a _typical_ instantiation of the standard object model. 

### Terminology

The JOM presents a JOM-value as having a name, attributes and members. The attributes should
be thought of as a collection of key-value pairs and the members as a colleaction of selector-child pairs.


## class Element Declarative Methods

* Method ```getName() -> String``` - returns the name of the object, which is typically intended to be a type-name. 

* Method ```countAttributes() -> Int``` - returns the total number of key-value pairs in the 
object.

* Method ```getAttributesAsMultiMap( Boolean view = false, Boolean mutable = false ) -> MultiMap< String, String >``` - returns a multi-map representing the attributes of the object. If ```view``` is ```true``` then the multi-map is a mutable view onto the attributes and changes to the multi-map immediately affect the attributes. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```getAttributesStream() -> Stream< Maplet< String, String > >``` - returns 
all the attributes as an insensitive stream over shallowly immutable maplets.

* Method ```getValue( String key, Boolean reverse = false, Int position = 0, String? default = null ) -> String?``` - returns the value associated with the maplet with key ```key``` and position ```position```. If there is no such maplet then ```default``` is returned instead. If ```reverse``` is true then the position is taken to be ```getAttributeCount( key ) - 1 - position```.

* Method ```getFirstValue( String key, String? default = null ) -> String?``` - returns the first value associated with the maplet with key ```key```. If there is no such maplet then ```default``` is returned instead. 

* Method ```getLastValue( String key, String? default = null ) -> String?``` - returns the last value associated with the maplet with key ```key```. If there is no such maplet then ```default``` is returned instead. 

* Method ```countValues( String key ) -> Int``` - returns the number of attributes that share
the key.

* Method ```getValuesAsList( String key, Boolean view = false, Boolean mutable = false ) -> List< String >``` - returns all the values that are in maplets with key ```key```. If ```view``` is ```true``` then the multi-map is a mutable view onto the attributes and changes to the multi-map immediately affect the attributes. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```countMembers() -> Int``` - returns the total number of select-child pairs in the 
object.

* Method ```getMembersAsMultiMap() -> MultiMap< String, JOM >``` - returns a multi-map representing the members of the object. If ```view``` is ```true``` then the multi-map is a mutable view onto the members and changes to the multi-map immediately affect the membrs. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```getMemberStream() -> Stream< Maplet< String, JOM > >``` 

* Method ```getChild( String sel = "", Int position = 0, JOM? default = null ) -> JOM?``` - returns the object associated with the maplet with selector ```sel``` and position ```position```. If there is no such maplet then ```default``` is returned instead. If ```reverse``` is true then the position is taken to be ```countChildren( sel ) - 1 - position```.

* Method ```getFirstChild( String sel, String? default = null ) -> String?``` - returns the first child associated with the maplet with selector ```sel```. If there is no such maplet then ```default``` is returned instead. 

* Method ```getLastChild String sel, String? default = null ) -> String?``` - returns the last child associated with the maplet with selector ```sel```. If there is no such maplet then ```default``` is returned instead. 

* Method ```countChildren( String sel ) -> Int``` - returns the number of children that share
the selector ```sel```.

* Method ```getChildrenAsMultiMap( Boolean view = false, Boolean mutable = false ) -> MultiMap< String, String >``` - returns a multi-map representing the children of the object. If ```view``` is ```true``` then the multi-map is a mutable view onto the children and changes to the multi-map immediately affect the children. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.


* Method ```getChildrenAsList( String? key = "", Boolean view = false, Boolean mutable = false ) -> List< JOM >``` - returns all the children that are in maplets with key ```key```. If ```view``` is ```true``` then the multi-map is a mutable view onto the attributes and changes to the multi-map immediately affect the attributes. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```isIntValue() -> Boolean``` - returns true if the object represents an integer. This test is only required to check the name of the object.

* Method ```getIntValue( Int? default = null ) -> Int?``` - if the object represents an integer then this returns its integral value. If it is not an integer, the default should be returned. N.B. There is no size limit on integers in JSON and conforming implementations are required to support arbitrary-width integers. The majority of programming languages give small width integers special significance and they should provide special variants of getIntValue (e.g. getInt32Value, getInt64Value) for that purpose. If the integer value is out of range, returning the default value is an acceptable action in these variants.

* Method ```isFloatValue( Boolean strict = false ) -> Boolean``` - returns true if the object represents a floating-point number. This test is only required to check the name of the object.
If ```strict``` is false then integral values will return true for this test.

* Method ```getFloatValue( Boolean strict = false, Float? default = null ) -> Float?``` - if the object represents an float then this returns its value using the common representation for the host programming language. This will typically be ```double``` precision on today's machines. Libraries may provide variants (e.g. getFloat32Value, getFloatDoubleValue()) at their own discretion. Note that floating point numbers in the syntax have arbitrary precision but there is no requirement to retain this exact precision. If the value is not a Float then implementation should return the default value. If ```strict``` is false then integer values are allowed and automatically converted to a floating point value.

* Method ```isStringValue() -> Boolean```

* Method ```getStringValue( String? default = null ) -> String?```

* Method ```isBooleanValue() -> Boolean```

* Method ```getBooleanValue( Boolean? default = null ) -> Boolean?```

* Method ```isNullValue() -> Boolean```

* Method ```getNullValue( Null? default = null ) -> Null?```

### Mutators Methods

* Method ```setName( String name )```

* Method ```setAttributes( MultiMap< String, String > attributes )```

* Methid ```setValues( String key, Iterable< String > )```

* Method ```setValue( String key, Boolean reverse = false, Int position = 0, String value )```

* Method ```addFirstValue( String key, String value )```

* Method ```addLastValue( String key, String value )```

* Method ```removeFirstValue( String key, String? value = null ) -> String?```

* Method ```removeLastValue( String key, String? value = null ) -> String?```

* Method ```setMembers( MultiMap< String, JOM > members )```

* Method ```setChildren( String sel, Iterable< JOM > )```

* Method ```setChild( String sel, Boolean reverse = false, Int position = 0, JOM child )```

* Method ```addFirstChild( String sel, JOM child )```

* Method ```addLastChild( String sel, JOM child )```

* Method ```removeFirstChild( String sel, JOM? value = null ) -> JOM?```

* Method ```removeLastValue( String sel, JOM? value = null ) -> JOM?```

### Builder Related Methods

* Method ```toEventStream() -> Stream< PushParser.Event >``` - generates a stream of events that performs an immune 'walk' over the Element. The walk is insensitive in the sense that the stream it returns is immune to all updates to the Element that might occur while the walk is in-progress.

## Class Builder extends PushParser.Listener, Stream< Element >

### General Methods

* Factory Constructor ```newBuilder( Boolean mutable = false, Boolean allowQueuing = false ) -> Builder``` - returns a factory object that can be used to construct new JOM elements. If the flag ```mutable``` is true, then the new values are constructed to be (deeply) mutable, otherwise (deeply) immutable. If ```allowQueuing``` is false, then no events are accepted after a JOM-element is ready until the element is constructed by ```newInstance```, otherwise events are allowed.

* Method ```hasNext() -> Boolean``` - returns true if enough events have been received to construct an element.

* Method ```isInProgress() -> Boolean``` - return true if some events have been received but there are more open than close events. 

* Method ```next() -> Element``` - constructs the next JOM element if one is ready for construction, otherwise will raise an exception. Use ```this.hasNext()``` to determine whether it is safe to call this method.

* Method ```tryNext( Element? orElse = null )``` - if a JOM element is ready for construction it builds and returns it, otherwise it returns the value in ```orElse``` instead.

* Method ```snapshot() -> Element``` - all open states are automatically but temporarily completed;
if a JOM element is ready for construction after the auto-completion, it is constructed and the result will be returned, otherwise an exception is raised; the temporarily closed states are restored to their previous state. Use ```this.isInProgress()``` to check whether it is safe to call this method.

* Method ```trySnapshot( Element? orElse = null ) -> Element?``` - as for ```snapshot``` but never raises an exception but returns the value in```orElse``` instead.

* Method ```include( Element value, Boolean checkMutability = false )``` - adds and shares this JOM value into the in-progress build. The mutability of the included value is checked depending on the ```checkMutability``` flag. It is **not** an error to mix mutability this way but an occasionally important feature.

* Method ```reconstruct( Element value )``` - replays a series of events which would construct the Element supplied but does so inside the in-progress build. This is effectively the same as ```this.processEvents( value.toEventStream() )```
a deep copy.

* Method ```processEvent( PushParser.Event event )``` - processes a PushParser event using the appropriate method.

* Method ```processEvents( Stream< PushParser.Event > events )``` - processes a series of events, which is effectively the same as ```events.forEach( e =>> this.processEvent( e ) )```.

### Event Handling Methods

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
