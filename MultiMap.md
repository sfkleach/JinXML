# MultiMap< K, V > - a helper interface

A multi-map is a map that allows a key to have several values, which are ordered. This interface
is used heavily in the JinXML object-model. From that viewpoint, it is a helper definition - 
although MultiMaps are very useful in their own right.

Each maplet in the multi-map is considered to have both _key_ and _position_. Maplets that have the same key are ordered and the position of a maplet refers to the number of predecessors with the same key.

MultiMap methods typically return either a complete new copy, with no shared state, or a view 
whose state is tied to the MultiMap. Both copies and views may be mutable or immutable. 
In this context an immutable copy or view is one that does not support update operations. 
However, an immutable view will still reflect changes to the original Multi-Map 
i.e. _shallow_ immutability. Importantly all iterators are expected to be _insensitive_ to 
changes in the original multi-map, as if the iterator was based on a shallow copy. 


* Method ```getValuesAsList( K k, Boolean view = false, Boolean mutable = false ) -> List< V >``` - returns the values of the maplets with key k. If ```view``` is ```true``` then the list is a mutable view onto the multi-map and changes to the list immediately affect the multi-map. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```entriesIterator() -> Iterator< Maplet< K, V > >``` - returns an insensitive iterator over shallowly immutable maplets of a multi-map. 

* Method ```keysIterator() -> Iterator< K >``` - returns an insensitive iterator over the distinct keys of the multi-map.

* Method ```asMapIterator( Boolean view = false, Boolean mutable = false ) -> Iterator< Maplet< K, List< V > > >``` - returns an insensitive iterator that iterates over shallowly-immutable maplets.
The flag ```view``` determines if the lists are copies or views onto the original MultiMap and the
flag ```mutable``` determines if the list is mutable.

* Method ```contains( K k ) -> Boolean``` - Equivalent to ```this.getValuesAsList( k ).size() > 0```

* Method ```push( K k, V v )``` - equivalent to ```this.getAsList( k, view: true ).addLast( v )```

* Method ```pop( K k, V? v = null ) -> V?``` - Equivalent to ```this.getValuesAsList( k, view: true ).popLast( default: v )```

* Method ```peek( K k, V? v = null ) -> V?``` - Returns the last value associated with t. If no such value exists then return v. Equivalent to ```this.getValuesAsList( k ).getLast( default: v )```
