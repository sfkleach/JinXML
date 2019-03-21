# MultiMap< K, V > - a helper interface

A multi-map is a map that allows a key to have several values, which are ordered. This interface
is used heavily in the JinXML object-model. From that viewpoint, it is a helper definition - 
although MultiMaps are very useful in their own right.

Each maplet in the multi-map is considered to have both _key_ and _position_. Maplets that have the same key are ordered and the position of a maplet refers to the number of predecessors with the same key.

MultiMap methods typically return either a complete new copy, with no shared state, or a view 
whose state is tied to the MultiMap. Both copies and views may be mutable or immutable. 
In this context an immutable copy or view is one that does not support update operations. 
However, an immutable view will still reflect changes to the original Multi-Map 
i.e. _shallow_ immutability. 

Importantly all iterators are expected to be _immune_ to changes in the original multi-map, 
as if the iterator was based on a shallow copy made at the time the iterator was instantiated.
This makes it safe to modify the multi-map while iterating over it.

* Method ```getValuesAsList( K k, Boolean view = false, Boolean mutable = false ) -> List< V >``` - returns the values of the maplets with key k. If ```view``` is ```true``` then the list is a mutable view onto the multi-map and changes to the list immediately affect the multi-map. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```entriesIterator() -> Iterator< Maplet< K, V > >``` - returns an immune iterator over shallowly immutable maplets of a multi-map. 

* Method ```keysIterator() -> Iterator< K >``` - returns an immune iterator over the distinct keys of the multi-map.

* Method ```asMap( Boolean view = false, Boolean mutable = false ) -> Map< K, List< V > >``` - returns a map from keys to an ordered list of values.  If view is ```false``` then the map and 
the contained lists are copies. If view is ```true``` then the maps and contained lists are 
all dynamic views. The map and contained lists are mutable or immutable depending on the value of 
```mutable```. An iterator of this map is immune.

* Method ```asMapIterator( Boolean view = false, Boolean mutable = false ) -> Iterator< Maplet< K, List< V > > >``` - returns an immune iterator that iterates over shallowly-immutable maplets.
The flag ```view``` determines if the lists are copies or views onto the original MultiMap and the
flag ```mutable``` determines if the list is mutable.

* Method ```contains( K k ) -> Boolean``` - Equivalent to ```this.countOf( k ).size() > 0```

* Method ```countOf( K k ) -> Int``` - Equivalent to ```this.getValuesAsList( k ).size()```

* Method ```countAll() -> Int``` - The total number of entries in the multi-map.

* Method ```addFirst( K k, V v )``` - equivalent to ```this.getAsList( k, view: true ).addFirst( v )```

* Method ```addLast( K k, V v )``` - equivalent to ```this.getAsList( k, view: true ).addLast( v )```

* Method ```removeFirst( K k, V? v = null ) -> V?``` - Equivalent to ```this.getValuesAsList( k, view: true ).removeFirst( default: v )```

* Method ```removeLast( K k, V? v = null ) -> V?``` - Equivalent to ```this.getValuesAsList( k, view: true ).removeLast( default: v )```

* Method ```getFirst( K k, V? v = null ) -> V?``` - Returns the last value associated with k. If no such value exists then return v. Equivalent to ```this.getValuesAsList( k ).getFirst( default: v )```

* Method ```setFirst( K k, V v )``` - sets the last value associated with k. If no such maplet exists then create one. Equivalent to ```this.getValuesAsList( k ).setFirst( v )```

* Method ```getLast( K k, V? v = null ) -> V?``` - Returns the last value associated with k. If no such value exists then return v. Equivalent to ```this.getValuesAsList( k ).getLast( default: v )```

* Method ```setFirst( K k, V v )``` - sets the last value associated with k. If no such maplet exists then create one. Equivalent to ```this.getValuesAsList( k ).setLast( v )```

