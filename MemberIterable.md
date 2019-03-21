# Class MemberIterable

* Method ```iterator() -> Iterable<Member>``` - returns an iterator over [Member](/Member.md)s. Can be called repeatedly to generate new iterators. Each iterator is isolated from concurrent updates to the element or anything else.

* Method ```with( ( Member -> Boolean ) ) -> MemberIterable``` - returns a filtered MemberIterable.

* Method ```uniqueSelector() -> MemberIterable``` - returns a filtered MemberIterable where only the first member for each selector comes through.