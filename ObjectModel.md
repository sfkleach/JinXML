# What is the standard JinXML Object Model?

## Overview

DESCRIBE THE AIMS OF THE STANDARD OBJECT MODEL - SAME AS THE STANDARD PUSH PARSER. EXPLAIN MULTIMAP.

The following classes and methods illustrate a _typical_ instantiation of the standard object model. 

## Class JOM

### Helper Interface - MultiMap< T, U >
In this context, a multi-map is a map that allows a key to have several values. Note that all iterators are expected to be _insensitive_ to changes in the original multi-map, as if the iterator was based on a copy.

* Method ```getValuesAsList( T t, Boolean view = false, Boolean mutable = false ) -> List< U >``` - returns the values of the maplets with key t. If ```view``` is ```true``` then the list is a mutable view onto the multi-map and changes to the list immediately affect the multi-map. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.
* Method ```entriesIterator() -> Iterator< Maplet< T, U > >``` - returns an insensitive iterator over the immutable maplets of a multi-map. 
* Method ```keysIterator() -> Iterator< T >``` - returns an insensitive iterator over the distinct keys of the multi-map.
* Method ```asMapIterator( Boolean view = false, Boolean mutable = false ) -> Iterator< Maplet< T, List< U > > >``` - equivalent to ```this.keysIterator().compose( fn T t =>> this.getAsList( t, view: view, mutable: mutable ) endfn )```
* Method ```contains( T t, U? u = null ) -> Boolean``` - Equivalent to ```this.getAsList( t ).size() > 0```
* Method ```push( T t, U u )``` - equivalent to ```this.getAsList( t, view: true ).addLast( u )```
* Method ```pop( T t, U? u = null ) -> U?``` - Equivalent to ```this.getAsList( t, view: true ).popLast( u, default: null )```
* Method ```peek( T t, U? u = null ) -> U?``` - Returns the last value associated with t. If no such value exists then return u. Equivalent to ```this.getAsList( t ).getLast( u, default: null )```

### Declarative Methods

* Method ```name() -> String``` - returns the name of the object, which is typically intended to be a type-name. 
* Method ```attributesAsCollection( Boolean view = false, Boolean mutable = false ) -> MultiMap< String, String >``` - returns a multi-map representing the attributes of the object. If ```view``` is ```true``` then the multi-map is a mutable view onto the attributes and changes to the multi-map immediately affect the attributes. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.
* Method ```getAttribute( String key = 0, Int position = 0, String? default = null ) -> String?```
* Method ```allAttributes( String? key = null, Int position? = 0) -> List< String >```
* Method ```childrenCollection() -> MultiMap< String, JOM >``` - returns a multi-map representing the children of the object. If ```view``` is ```true``` then the multi-map is a mutable view onto the children and changes to the multi-map immediately affect the children. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.
* Method ```getChild( String key = "", Int position = 0, JOM? default = null ) -> JOM?```
* Method ```allChildren( String? key = null, Int? position = null ) -> List< JOM >```
* Method ```isIntValue() -> Boolean```
* Method ```getIntValue( Int? default = null ) -> Int?```
* Method ```isFloatValue() -> Boolean```
* Method ```getFloatValue( Float? default = null ) -> Float?```
* Method ```isStringValue() -> Boolean```
* Method ```getStringValue( String? default = null ) -> String?```
* Method ```isBooleanValue() -> Boolean```
* Method ```getBooleanValue( Boolean? default = null ) -> Boolean?```
* Method ```isNullValue() -> Boolean```
* Method ```getNullValue( Null? default = null ) -> Null?```

### Mutators 

* Method ```setName( String name )```
* Method ```setAttributesCollection( MultiMap< String, String > attributes )```
* Method ```setAttribute( String key = 0, Int position = 0, String? value = null, String padding = "" )```

## Class Builder

INSTANTIATE THE BUILDER METHODS
