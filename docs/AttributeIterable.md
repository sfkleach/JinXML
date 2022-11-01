# Class AttributeIterable

* Method ```iterator() -> Iterable<Attribute>``` - returns an iterator over [Attribute](/Attribute.md)s. Can be called repeatedly to generate new iterators. Each iterator is isolated from concurrent updates to the element or anything else.

* Method ```with( ( Attribute -> Boolean ) ) -> AttributeIterable``` - returns a filtered AttributeIterable.

* Method ```uniqueKey() -> AttributeIterable``` - returns a filtered AttributeIterable where only the first attribute for each key comes through.