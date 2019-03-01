# class Element 

## Quick Note on Terminology

The object-model represents a Element as having a single name, attributes and members. The attributes should be thought of as a collection of key-value pairs and the members as a collection of selector-child pairs.

## Constructors and Friends

* Function ```newElement( String name ) -> Element``` - returns a new mutable element whose name is `name`.

* Function ```readElement( Input input, Boolean mutable = false ) -> Element``` - reads an element from character-based input. If `mutable` is true then the element returned is mutable, otherwise it is deeply immutable.

* Function ```readElementStream( Input input, Boolean mutable = false ) -> Stream< Element >``` - returns a stream of elements that dynamically draws content from the input as it is accessed. If `mutable` is true then the element returned is mutable, otherwise it is deeply immutable.

* Function ```fromString( String text, Boolean mutable = false ) -> Element``` - parses the string `text`. If `mutable` is true then the element returned is mutable, otherwise it is deeply immutable.

* Function ```newBuilder( Boolean mutable = false, Boolean allows_queuing = false ) -> Builder``` - returns a new [Builder](/Builder.md) using a default implementation. This is equivalent to `Builder.newBuilder( mutable, allows_queuing )`.

## Methods for Rendering (Printing) to an Output Stream

* Method ```toString()``` - renders the element recursively as a string using the same format as for `print`, for which see below.
* Method ```print( Output out )``` - renders the element recursively to the output stream `output`. 
  - The only whitespace used is a single space character to separate the parts of a tag
  - end-tags are repeated in full
  - inside tags single-quoted strings are used
  - between tags double-quote strings are used
  - elements are rendered using JSON syntax if no information is lost
  - children will be separated by a comma if they are printed in JSON format
* Method ```prettyPrint( Output output )``` - renders the element recursively to the output stream `output`.
  - elements always start on a new line
  - members are indented relative to their parents
  - elements always end with a new line

## Copying and Freezing

* Method ```freeze() -> Element``` - returns a frozen element that compares as equal with the subject. If the element is already frozen it must return itself. If the element is mutable then this is a shallow operation. 

* Method ```freezeSelf()``` - freezes the element.  If it is frozen already this method has no effect.  Children are not affected.

* Method ```deepFreeze() -> Element``` - returns a frozen element that compares as equal, all of whose children are also deep-frozen. If the original element was deep-frozen already, it must return itself.

* Method ```deepFreezeSelf()``` - freezes the element and all the children recursively.

* Method ```mutableCopy() -> Element``` - returns a mutable shallow copy of an element.

* Method ```deepMutableCopy() -> Element``` - returns a mutable deep copy of an element, which is mutable all the way down.



## Declarative Methods 

### Methods Relating to an Element's Name

* Method ```getName() -> String``` - returns the name of the object, which is typically intended to be a type-name. 

* Method ```hasName( String? name )``` - returns true if the object has name `name`, otherwise false. If name is `null` then `false` is always returned.

### Methods Relating to an Element's Attributes

* Method ```countAttributes() -> Int``` - returns the total number of key-value pairs in the element.

* Method ```attributes() -> AttributeIterable``` - returns a special iterable object of type [AttributeIterable](/AttributeIterable.md) for iteration over the attributes of an element. Each iteration/iteration is isolated from concurrent updates of the element. On each iteration an [Attribute](/Attribute.md) object is returned that can be used to access both the key and value of the attribute.

* Method ```getAttributesAsMultiMap( Boolean view = false, Boolean mutable = false ) -> MultiMap< String, String >``` - returns a multi-map representing the attributes of the object. If ```view``` is ```true``` then the multi-map is a mutable view onto the attributes and changes to the multi-map immediately affect the attributes. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

* Method ```getValue( String key, Boolean reverse = false, Int position = 0, String? default = null ) -> String?``` - returns the value associated with the maplet with key ```key``` and position ```position```. If there is no such maplet then ```default``` is returned instead. If ```reverse``` is true then the position is taken to be ```getAttributeCount( key ) - 1 - position```.

* Method ```getFirstValue( String key, String? default = null ) -> String?``` - returns the first value associated with the maplet with key ```key```. If there is no such maplet then ```default``` is returned instead. 

* Method ```getLastValue( String key, String? default = null ) -> String?``` - returns the last value associated with the maplet with key ```key```. If there is no such maplet then ```default``` is returned instead. 

* Method ```countValues( String key ) -> Int``` - returns the number of attributes that share
the key.

* Method ```getValuesAsList( String key, Boolean view = false, Boolean mutable = false ) -> List< String >``` - returns all the values that are in maplets with key ```key```. If ```view``` is ```true``` then the multi-map is a mutable view onto the attributes and changes to the multi-map immediately affect the attributes. If view is ```false``` then the list is a copy. The result is mutable or immutable depending on the value of ```mutable```.

### Methods Relating to an Element's Members

* Method ```countMembers() -> Int``` - returns the total number of select-child pairs in the object.

* Method ```members() -> MemberIterable``` - returns a special iterable object of type [MemberIterable](/MemberIterable.md) for iteration over the members of an element. Each iteration/iteration is isolated from concurrent updates of the element. On each iteration an [Member](/Member.md) object is returned that can be used to access both the selector and child of the member.

* Method ```getMembersAsMultiMap() -> MultiMap< String, Element >``` - returns a multi-map representing the members of the object. If `view` is `true` then the multi-map is a mutable view onto the members and changes to the multi-map immediately affect the members. If view is `false` then the list is a copy. The result is mutable or immutable depending on the value of `mutable`.

* Method ```getChild( String sel = "", Boolen reverse = false, Int position = 0, Element? default = null ) -> Element?``` - returns the object associated with the maplet with selector `sel and position `position`. If there is no such maplet then `default is returned instead. If `reverse` is true then the position is taken to be `countChildren( sel ) - 1 - position`.

* Method ```getFirstChild( String sel, String? default = null ) -> Element?``` - returns the first child associated with the maplet with selector ```sel```. If there is no such maplet then ```default``` is returned instead. 

* Method ```getLastChild String sel, String? default = null ) -> Element?``` - returns the last child associated with the maplet with selector ```sel```. If there is no such maplet then ```default``` is returned instead. 

* Method ```countChildren( String sel = "" ) -> Int``` - returns the number of children that share
the selector `sel`.

* Method ```getChildrenAsList( String? key = "", Boolean view = false, Boolean mutable = false ) -> List< Element >``` - returns all the children that are in maplets with key ```key```. If `view` is `true` then the multi-map is a mutable view onto the attributes and changes to the multi-map immediately affect the attributes. If view is `false` then the list is a copy. The result is mutable or immutable depending on the value of `mutable`.

### Methods Relating to JSON-types (numbers, booleans, arrays etc)

* Method ```isIntValue() -> Boolean``` - returns true if the object represents an integer, irrespective of whether or not it is in range of the preferred integer type.

* Method ```getIntValue( Boolean allowOutOfRange = false, Int? default = null ) -> Int?``` - For each host language there is a preferred integer type that should be the largest type  that works with the standard arithmetic operators. If the object represents an integer value that is in the range of this type then this returns the appropriate value, otherwise the default is returned. If it is an integer outside of the range of the host programming language then ```allowOutOfRange``` is used to determine if the default is returned (on true) or processing should be interrupted (e.g. by throwing an exception.)

* Method ```getBigIntValue( Boolean allowOutOfRange = false, BigInt? default = null ) -> BigInt?``` - if the object represents an integer value then it returns an unlimited precision integer, otherwise returns the default. If the host language does not support unlimited precision integers then this will behave the same as `getIntValue` and respect the `allowOutOfRange` flag. If the host language does support unlimited precision integers then the `allowOutOfRange` flag is ignored.

* Method ```isFloatValue( Boolean strict = false ) -> Boolean``` - returns true if the object represents a floating-point number. If `strict` is false then integral values will return true for this test.

* Method ```getFloatValue( Boolean strict = false, Float? default = null ) -> Float?``` - if the object represents an float then this returns its value using the common representation for the host programming language. This will typically be ```double``` precision on today's machines. Libraries may provide variants (e.g. getFloat32Value, getFloatDoubleValue()) at their own discretion. Note that floating point numbers in the syntax have arbitrary precision but there is no requirement to retain this exact precision. If the value is not a Float then implementation should return the default value. If ```strict``` is false then integer values are allowed and automatically converted to a floating point value.

* Method ```isStringValue() -> Boolean``` - returns true if the object represents a literal string. 

* Method ```getStringValue( String? default = null ) -> String?``` - returns the literal string value if the element represents a string, otherwise returns  the value of `default`.

* Method ```isBooleanValue() -> Boolean```- returns true if the object represents a literal string (i.e. true or false).

* Method ```getBooleanValue( Boolean? default = null ) -> Boolean?```- returns the literal boolean value if the element represents a boolean, otherwise returns the value `default`.

* Method ```isNullValue() -> Boolean``` - returns true if the object represents literal null (i.e. the literal `null`).

* Method ```getNullValue( Null? default = null ) -> Null?``` - returns `null` if the object represents null, otherwise returns the value `default`.

## Mutators Methods

### Mutators Related to an Element's Name

* Method ```setName( String name )``` - if the element is mutable, this method will set the name, otherwise an exception will be raised.

### Mutators Related to an Element's Attributes

* Method ```clearAttributes()``` - if the element is mutable will delete all the attributes from the element. If the element is frozen an exception will be raised.

* Method ```setAttributes( MultiMap< String, String > attributes )``` - if the element is mutable, this will effectively clear out all the existing attributes and replace them with the values from the multimap. If the element is frozen then an exception is raised.

* Method ```setValues( String key, Iterable< String > values )``` - if the element is mutable, this will effectively delete all attributes with the given `key` and replace them with attributes with key `key` and values drawn from the iterable `values`. If the element is frozen then an exception is raised.

* Method ```setValue( String key, Boolean reverse = false, Int position = 0, String value )``` - if the element is mutable, this will alter the value of the attribute with key `key` in position `position`. If `reverse` is true, the position is taken from the end of the attributes rather than the start. If the element is frozen then an exception is raised.

* Method ```addFirstValue( String key, String value )``` - adds a new attribute that will take the first position in the list of attributes with key `key`, provided the element is mutable. If the element is frozen then an exception is raised.

* Method ```addLastValue( String key, String value )```- adds a new attribute that will take the last position in the list of attributes with key `key`, provided the element is mutable. If the element is frozen then an exception is raised.

* Method ```removeFirstValue( String key, String? otherwise = null ) -> String?``` - removes the first attribute with key `key`, provided the element is mutable, returning its value. If there is no such attribute and the element is mutable then this method has no effect and `otherwise` is returned. If the element is frozen then an exception is raised.

* Method ```removeLastValue( String key, String? otherwise = null ) -> String?```- removes the last attribute with key `key`, provided the element is mutable,  returning its value. If there is no such attribute and the element is mutable then this method has no effect and `otherwise` is returned. If the element is frozen then an exception is raised.

### Mutators Related to an Element's Members

* Method ```clearMembers()``` - if the element is mutable will delete all the members from the element. If the element is frozen an exception will be raised.

* Method ```setMembers( MultiMap< String, Element > members )``` - if the element is mutable, this will effectively clear out all the existing members and replace them with the members from the multimap. If the element is frozen then an exception is raised.

* Method ```setChildren( String sel, Iterable< Element > children )```- if the element is mutable, this will effectively delete all members with the given selector `sel` and replace them with members with selector `sel` and children drawn from the iterable `children`. If the element is frozen then an exception is raised.

* Method ```setChild( String sel, Boolean reverse = false, Int position = 0, Element child )``` - if the element is mutable, this will alter the child of the member with selector `sel` in position `position`. If `reverse` is true, the position is taken from the end of the members rather than the start. If the element is frozen then an exception is raised.

* Method ```addFirstChild( String sel, Element child )``` - adds a new member that will take the first position in the list of members with selector `sel`, provided the element is mutable. If the element is frozen then an exception is raised.

* Method ```addLastChild( String sel, Element child )``` - adds a new member that will take the last position in the list of members with selector `sel`, provided the element is mutable. If the element is frozen then an exception is raised.

* Method ```removeFirstChild( String sel, Element? otherwise = null ) -> Element?``` - removes the first member with selector `sel`, provided the element is mutable, and returns the child of the removed value. If there is no such attribute and the element is mutable then this method has no effect and `otherwise` is removed. If the element is frozen then an exception is raised.

* Method ```removeLastChild( String sel, Element? otherwise = null ) -> Element?``` - removes the last member with selector `sel`, provided the element is mutable, and returns the child of the removed value. If there is no such attribute and the element is mutable then this method has no effect and `otherwise` is removed. If the element is frozen then an exception is raised.


## Builder Methods

* Method ```toEventStream() -> Stream< Event >``` - generates a stream of events that performs a 'walk' over the Element. The walk is guaranteed to be isolated from updates in the sense that the stream it returns is unaffected by any updates to the Element that might occur while the walk is in-progress.




