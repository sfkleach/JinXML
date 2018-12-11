# MultiMap< T, U > - a helper interface

A multi-map is a map that allows a key to have several values, which are ordered. This interface
is used heavily in the JinXML object-model. From that viewpoint, it is a helper definition - 
although MultiMaps are very useful in their own right.

Each maplet in the multi-map is considered to have both _key_ and _position_. Maplets that have the same key are ordered and the position of a maplet refers to the number of predecessors with the same key.

MultiMaps return views that may be mutable or immutable. In this context an immutable view is one that does not support update operations. However the underlying multi-map may be mutated and the view will reflect that change i.e. _shallow_ immutability.
Note that all iterators are expected to be _insensitive_ to changes in the original multi-map, as if the iterator was based on a copy. 


* Method ```getValuesAsList( T t, Boolean view = false, Boolean mutable = false ) -> List< U >``` - returns the values of the maplets with key t. If ```view``` is ```true``` then the list is a mutable view onto the multi-map and changes to the list immediately affect the multi-map. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```entriesIterator() -> Iterator< Maplet< T, U > >``` - returns an insensitive iterator over the immutable maplets of a multi-map. 

* Method ```keysIterator() -> Iterator< T >``` - returns an insensitive iterator over the distinct keys of the multi-map.
* Method ```asMapIterator( Boolean view = false, Boolean mutable = false ) -> Iterator< Maplet< T, List< U > > >``` - equivalent to ```this.keysIterator().compose( fn T t =>> this.getAsList( t, view: view, mutable: mutable ) endfn )```

* Method ```contains( T t, U? u = null ) -> Boolean``` - Equivalent to ```this.getAsList( t ).size() > 0```

* Method ```push( T t, U u )``` - equivalent to ```this.getAsList( t, view: true ).addLast( u )```

* Method ```pop( T t, U? u = null ) -> U?``` - Equivalent to ```this.getAsList( t, view: true ).popLast( u, default: null )```

* Method ```peek( T t, U? u = null ) -> U?``` - Returns the last value associated with t. If no such value exists then return u. Equivalent to ```this.getAsList( t ).getLast( u, default: null )```
