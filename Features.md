# List of Features in JinXML

* [Strict superset of JSON](#strict-superset-of-json)
* [Duplicate keys supported](#duplicate-keys-supported)
* [Unquoted keys](#unquoted-keys)
* [Equals as well as colon](#equals-as-well-as-colon)
* [Trailing and optional commas](#trailing-and-optional-commas)
* [End of line comments](#end-of-line-comments)
* [Long comments](#long-comments)
* [Double-quoted JSON string literals with HTML5 escapes](#double-quoted-json-string-literals-with-HTML5-escapes)
* [XML-like tags](#xml-like-tags)
* [Colon as well as equals](#colon-as-well-as-equals)
* [Optional tag names](#optional-tag-names)
* [Quoted element names and attribute keys](#quoted-element-names-and-attribute-keys)
* [XML-like headers, comments and processing directives](#xml-like-headers-comments-and-processing-directives)
* [Single-quoted XML string literals with HTML5 escapes](#single-quoted-xml-string-literals-with-html5-escapes)

## Strict superset of JSON
This is self-explanatory: a JinXML parser can read any normal JSON input. In other words nothing in JinXML breaks normal JSON usage. And the output of such a parse has the expected representation. Note that [objects with duplicate keys](https://dzone.com/articles/duplicate-keys-in-json-objects) are not considered to be 'normal' JSON. JinXML supports multiple values properly and hence does not behave the same way as abnormal JSON - see below for details.

## Duplicate keys supported
JSON expressions such as ```{ "size": 8, "size": 19 }``` meet the [JSON grammar](https://json.org) but are disallowed by most parsers. The internet standard [RFC 7159](https://tools.ietf.org/html/rfc7159) uses the infamous ["SHOULD"](https://www.ietf.org/rfc/rfc2119.txt) to say that keys are not actually required to be unique. JinXML won't allow this as it stands.

However, a JinXML parser allows duplicates _when explicitly permitted_ using the ```+:``` (and ```+=```) syntax. The ```+:``` sign turns off any checks for this child being a duplicate key. So the above example would be permitted if it was written as:
```
{ "size"+: 8, "size"+: 19 }
```
By contrast, the ```:``` sign cannot be used for the same key more than once - and it cannot come after ```+:``` has been used for that key. So this is OK:
```
{ "size": 8, "size"+: 19 }
```

But what does it mean if a key occurs multiple times in an object? It means that it has all of those values - duplicates are not removed and order of insertion of the values is preserved. So in our running example, "size" has two values 8 and 19 in that order. And in this case, "name" has values "Steve", "Stephen" and "Steve".
```
{ "size": 8, "size"+: 19, "name": "Steve", "name"+: "Stephen", "name"+: "Steve" }
```

> :notebook: Design note: the support for duplicate keys is needed, so that elements unify arrays and maps.

## Unquoted keys
Object keys always occur in well-defined syntactic contexts and hence do not need to be string-quoted, provided that they aren't too exotic. To keep the grammar simple, unquoted keys have to follow the same rules as XML element and attribute names (i.e. they are an [XML Name](https://www.w3.org/TR/xml/#NT-Name)). So the above example could be written as shown below, which is noticeably more readable.
```
{ size: 8, size+: 19, name: "Steve", name+: "Stephen", name+: "Steve" }
```

> Aside: Unquoted keys is quite a popular extension to JSON e.g. [JSON5](http://json5.org). Note that unquoted values, as in [Relaxed JSON](https://github.com/phadej/relaxed-json) is not permitted; identifiers are reserved for future extensions of JinXML.

> :notebook: Design note: future plans include let bindings to user-defined identifiers, so keys must be identified as distinct from identifiers at parse time.

## Equals as well as colon
The ```=``` separator is an alternative to ```:``` and ```+=``` is an alternative to ```+:```. There is no significance to the choice and a parser and/or application may not process them differently.

Example:
```
{ size=8, size+=19, name="Steve", name+="Stephen", name+="Steve" }
```

> :notebook: Design note: this arises from the desire to unify element attributes and string valued objects.

## Trailing and optional commas
Commas act as breaks between tokens but are entirely optional in JinXM. A comma may appear between or after JinXML terms but multiple commas are not allowed. All the following are permitted. 
```
{ size: 8 size+: 19 }    // Comma omitted
{ size: 8, size+: 19 }   // Comma as separator
{ size: 8 size+: 19, }   // Comma as terminator
{ size: 8, size+: 19, }  // Commas as separator and terminator
```

> :notebook: Design note: this is the most direct way to unify the syntactic behaviour of comma-free XML with Relaxed JSON while preserving much of their visual appeal.

## Semi-colons as alternatives to commas
Semi-colons are acceptable alternatives to commas for separating/terminating JinXML expressions. Between or after an expression there be a maximum of a single comma and/or semicolon.
```
{ size: 8, size+: 19; }   // Allowed
[ true, false,;]          // Not allowed
```

> :notebook: Design note: when terminating a expression a semi-colon can be more readable than a trailing comma and some of the relaxed JSONs have taken this route. It does not conflict with any of the features on the roadmap, so it is included despite having only modest justification.

## End of line comments
End-of-line comments are started with a pair of slashes and are discarded.
```
// Could do with a different running example.
{
  // This comes from Wikipedia - note there's no indication of the type this represents.
  "firstName": "John", 
  "lastName": "Smith",
  "isAlive": true,
  "age": 27, // Does the age of a person freeze at their time of death?
  "address": {
    "streetAddress": "21 2nd Street",
    "city": "New York",
    "state": "NY",
    "postalCode": "10021-3100"
  },
  "children": [],
  "spouse": null  // Can you have multiple spouses?
}
```

## Long comments
Long comments start with ```/*``` and are closed by the next occurence of ```*/```, in other words they do not nest. 
```
/* The way to comment several lines in a row.
*/
<data> length: 8, length: 19 </data>
```

> :notebook: Design note: Although I prefer nestable long comments, non-nesting long comments was chosen to correspond to Javascript. Using them to uncomment code is unsound practice, even when they nest.

## Double-quoted JSON string literals with HTML5 escapes
JSON string literals have a relatively small number of escapes ```\n```, ```\r``` and so on. JinXML significantly extends the range of supported escapes through recognising ```\&``` as introducing an HTML5 character entity code. For example:
```
{ copyright = "\&copy; Copyright Stephen Leach, 2018" }
```

> :notebook: Design note: The clash in escape conventions for string literals is the most awkward aspect of unifying JSON and MinXML.

## XML-like tags
Just as in XML, there are three kinds of tags. Start and end tags are paired and enclose a series of expressions and their names must match. The pair together is called an element.

A start tag has a name and a series of zero or more attributes and looks like this:
```
<NAME NAME=STRING NAME=STRING ...>
```
Attributes are name/value pairs separated by one of ```=``` or ```+=```. The difference between ```=``` or ```+=``` is that ```=``` does not allow duplicate attribute-keys but ```+=``` does. The restriction that ```=``` imposes is on a per-element, not just per tag. \[n.b. in the next section we also add the synonyms ```:``` and ```+:```.]

An element can enclose selector-child members, exactly like a JSON object. e.g.
```
<programme>
    origin: "BBC",
    series: "Doctor Who",
    episode: 201245,
    timeslot: "19:00, Saturday 03, March 2345"
</programme>
```

Or it can enclose children, exactly like a JSON array e.g.
```
<data> 1, 2, "buckle my shoe" </data>
```

Or an element can enclose both members and children at the same time e.g.
```
<data>
  firstEncountered: "Kindergarten";
  1, 2, "buckle my shoe";
</data>
```


Standalone tags are simply a notational convenience for a start-tag that is immediately followed by an end-tag i.e. has no children. They are written like this:
```
<NAME NAME=STRING NAME=STRING ... />
```
A parser may treat the standalone tag as a start tag with all the attributes immediately followed by an end tag.

## Colon as well as equals
The ```:``` separator is an alternative to ```=``` and ```+:``` is an alternative to ```+=```. There is no significance to the choice and a parser and/or application may not process them differently.

Example:
```
<data col:"8" col+="19" />
```

> :notebook: Design note: As noted above, we're unifying the syntax for element attributes with string-valued objects.

## Optional tag names
An end tag may substitute the special symbol ```&``` for the name of the element. 

Example:
```
<person>
  firstName = "John" 
  lastName = "Smith"
  isAlive = true
  age = 27        // Does the age of a person freeze at their time of death?
  address =       // See how to fix this 'stutter' in the next section.
  <address>
    streetAddress = "21 2nd Street"
    city = "New York"
    state = "NY"
    postalCode = "10021-3100"
  </&>
  children = []
  spouse = null  // Can you have multiple spouses?
</&>
```

A relatively common use case is to want to use the same name for object-key and element-name. To improve readability, in this specific case, both start and end tag names can be defaulted.
```
<person>
  firstName = "John" 
  lastName = "Smith"
  isAlive = true
  age = 27        // Does the age of a person freeze at their time of death?
  address = <&>
    streetAddress = "21 2nd Street"
    city = "New York"
    state = "NY"
    postalCode = "10021-3100"
  </&>
  ...
</&>
```

This is the only situation in which both the start and end tags of a matched pair can omit the element name. And because it applies to both the start and end tag, it is also the only situation a standalone tag may have its name omitted.

```
{
  length = 0
  data = <&/>
}
```

It is also legitimate to use ```&``` to default an attribute key. In this case it picks up the
value of the immediately following tag. N.B. If the value does not begin with a tag, it is a syntax error.
```
{
  & = <address/>
  & = []                  // Not allowed!
}
```




:notebook: Design note: these decisions are geared up to simplifying the task of generating large-scale JinXML. I would have preferred to have used ```*``` rather than ```&``` but this would have clashed with long comments.

## Quoted element names and attribute keys
Both element-names and attribute-keys may be quoted using string-literal syntax. This makes it possible to use non-standard identifiers. Here is a simple example:
```
<"left field" "and/or"="operator"> 34, 98 </"left field">
```

:notebook: Design note: the intention is to unify the name-space of element-names, attribute-keys and object-keys.

## XML-like headers, comments and processing directives
XML comments of the form ```<!--``` to ```-->``` are supported in the sense that their content must be discarded. Similarly the ```<?xml``` header at the start of every valid XML document is discarded and all xml processing directives as well.

N.B. No processor should respond to their content i.e. pragmas hidden in discarded are official no-nos. Comments can be replaced by one-or-more spaces without changing the meaning in any way.

:notebook: Design note: This is a low-priority item but slightly improves the range of XML data that can be accommodated without change. Processing directives may be accommodated in a later revision and we do not want to cramp our style in the intermin. And the idea of embedding character encoding in the XML header is downright horrible & will never be resurrected.

## Single-quoted (XML) string literals with JSON escapes
In JinXML, single and double quoted strings are mostly interchangeable - but single-quoted strings are primarily intended to help bring across HTML data. They each use different conventions to introduce escape sequences. Double-quoted strings use ```\``` to start escape sequences but single-quoted strings use ```&```. 

In this regard, single-quoted strings are very like XML attribute values (which may be single or double-quoted). However, JinXML extends the escape sequences to allow ```&\``` to switch to using the escape sequences of JSON.

For example:
```
<greeting text='Hello, world!&\n'/>
```

N.B. There is no automatic line-ending conversion in JinXML, such as MacOS \r to Unix \n or PC \r\n, and there never will be. JinXML is all about recording data, not corrupting it with dodgy heuristics.
