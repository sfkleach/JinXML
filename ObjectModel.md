# What is the standard JinXML Object Model?

## Overview

DESCRIBE THE AIMS OF THE STANDARD OBJECT MODEL - SAME AS THE STANDARD PUSH PARSER. EXPLAIN MULTIMAP.

The following classes and methods illustrate a _typical_ instantiation of the standard object model. 

## Helper Interface - MultiMap< T, U >
In this context, a multi-map is a map that allows a key to have several values, which are ordered. Note that all iterators are expected to be _insensitive_ to changes in the original multi-map, as if the iterator was based on a copy.

Each maplet in the multi-map is considered to have both _key_ and _position_. Maplets that have the same key are ordered and the position of a maplet refers to the number of predecessors with the same key.

MultiMaps return views that may be mutable or immutable. In this context an immutable view is one that does not support update operations. However the underlying multi-map may be mutated and the view will reflect that change i.e. _shallow_ immutability.

* Method ```getValuesAsList( T t, Boolean view = false, Boolean mutable = false ) -> List< U >``` - returns the values of the maplets with key t. If ```view``` is ```true``` then the list is a mutable view onto the multi-map and changes to the list immediately affect the multi-map. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```entriesIterator() -> Iterator< Maplet< T, U > >``` - returns an insensitive iterator over the immutable maplets of a multi-map. 

* Method ```keysIterator() -> Iterator< T >``` - returns an insensitive iterator over the distinct keys of the multi-map.
* Method ```asMapIterator( Boolean view = false, Boolean mutable = false ) -> Iterator< Maplet< T, List< U > > >``` - equivalent to ```this.keysIterator().compose( fn T t =>> this.getAsList( t, view: view, mutable: mutable ) endfn )```

* Method ```contains( T t, U? u = null ) -> Boolean``` - Equivalent to ```this.getAsList( t ).size() > 0```

* Method ```push( T t, U u )``` - equivalent to ```this.getAsList( t, view: true ).addLast( u )```

* Method ```pop( T t, U? u = null ) -> U?``` - Equivalent to ```this.getAsList( t, view: true ).popLast( u, default: null )```

* Method ```peek( T t, U? u = null ) -> U?``` - Returns the last value associated with t. If no such value exists then return u. Equivalent to ```this.getAsList( t ).getLast( u, default: null )```


## Main Interface - JOM

### Declarative Methods

* Method ```getName() -> String``` - returns the name of the object, which is typically intended to be a type-name. 

* Method ```attributesAsMultiMap( Boolean view = false, Boolean mutable = false ) -> MultiMap< String, String >``` - returns a multi-map representing the attributes of the object. If ```view``` is ```true``` then the multi-map is a mutable view onto the attributes and changes to the multi-map immediately affect the attributes. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```getAttributeValue( String key, Int position = 0, String? default = null ) -> String?``` - returns the value associated with the maplet with key ```key``` and position ```position```. If there is no such maplet then ```default``` is returned instead. 

* Method ```listAttributeValues( String key, Boolean view = false, Boolean mutable = false ) -> List< String >``` - returns all the values that are in maplets with key ```key```. If ```view``` is ```true``` then the multi-map is a mutable view onto the attributes and changes to the multi-map immediately affect the attributes. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```childrenAsMultiMap() -> MultiMap< String, JOM >``` - returns a multi-map representing the children of the object. If ```view``` is ```true``` then the multi-map is a mutable view onto the children and changes to the multi-map immediately affect the children. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```getChild( String key = "", Int position = 0, JOM? default = null ) -> JOM?``` - returns the object associated with the maplet with key ```key``` and position ```position```. If there is no such maplet then ```default``` is returned instead.

* Method ```listChildren( String? key = "", Boolean view = false, Boolean mutable = false ) -> List< JOM >``` - returns all the objects that are in maplets with key ```key```. If ```view``` is ```true``` then the multi-map is a mutable view onto the attributes and changes to the multi-map immediately affect the attributes. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```isIntValue() -> Boolean``` - returns true if the object represents an integer. This test is only required to check the name of the object.

* Method ```getIntValue( Int? default = null ) -> Int?``` - if the object represents an integer then this returns its integral value. N.B. There is no size limit on integers in JSON and conforming implementations are required to support arbitrary-width integers. The majority of programming languages give small width integers special significance and they should provide special variants of getIntValue (e.g. getInt32Value, getInt64Value) for that purpose. If the integer value is out of range, returning the default value is an acceptable action in these variants.

* Method ```isFloatValue() -> Boolean``` - returns true if the object represents a floating-point number. This test is only required to check the name of the object.

* Method ```getFloatValue( Float? default = null ) -> Float?``` - if the object represents an integer then this returns its value using the common representation for the host programming language. This will typically be ```double``` precision on today's machines. Libraries may provide variants (e.g. getFloat32Value, getFloatDoubleValue()) at their own discretion. Note that floating point numbers in the syntax have arbitrary precision but there is no requirement to retain this exact precision.

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
