# List of Features in JinXML

* [Strict superset of JSON](#strict-superset-of-json)
* [Duplicate keys supported](#duplicate-keys-supported)
* [Unquoted keys](#unquoted-keys)
* [Trailing and optional commas](#trailing-and-optional-commas)
* [End of line comments](#end-of-line-comments)
* [Long comments](#long-comments)
* [XML-like tags](#xml-like-tags)
* [XML-like comments](#xml-like-comments)
* [Single-quoted XML string literals with HTML5 escapes](#single-quoted-xml-string-literals-with-html5-escapes)
* [Double-quoted JSON string literals escapes extended with HTML5 character entities](#double-quoted-json-string-literals-escapes-extended-with-html5-character-entities)

## Strict superset of JSON
This is self-explanatory: a JinXML parser can read any normal JSON input. In other words nothing in JinXML breaks normal JSON usage. And the output of such a parse has the expected representation. [Objects with duplicate keys](https://dzone.com/articles/duplicate-keys-in-json-objects) are not normal JSON and JinXML will not behave the same way - see below for details.

## Duplicate keys supported
JSON expressions such as ```{ "size": 8, "size": 19 }``` meet the [JSON grammar](https://json.org) but are disallowed by most parsers. The internet standard [RFC 7159](https://tools.ietf.org/html/rfc7159) uses the infamous ("SHOULD")[https://www.ietf.org/rfc/rfc2119.txt] to say that keys are not actually required to be unique. JinXML won't allow this as it stands.

However, a JinXML parser allows duplicates _when explicitly permitted_ using the ```+:``` (and ```+=```) syntax. The ```+:``` sign turns off any checks for this child being a duplicate key. So the above example would be permitted if it was written as:
```
{ "size"+: 8, "size"+: 19 }
```
By contrast, the ```:``` sign cannot be used for the same key more than once. But once is fine - so both of these are OK:
```
{ "size"+: 8, "size": 19 }
{ "size": 8, "size"+: 19 }
```

But what does it mean if a key occurs multiple times in an object? It means that it has all of those values - duplicates are not removed and order of insertion of the values is preserved. So in our running example, "size" has two values 8 and 19 in that order. And in this case, "name" has values "Steve", "Stephen" and "Steve".
```
{ "size": 8, "size"+: 19, "name": "Steve", "name"+: "Stephen", "name"+: "Steve" }
```

## Unquoted keys
Object keys always occur in well-defined syntactic contexts and hence do not need to be string-quoted, provided that they aren't too exotic. To keep the grammar simple, unquoted keys have to follow the same rules as XML element and attribute names (i.e. they are an [XML Name](https://www.w3.org/TR/xml/#NT-Name)). So the above examples could be written as shown below - and is noticeably more readable.
```
{ size: 8, size+: 19, name: "Steve", name+: "Stephen", name+: "Steve" }
```

> Aside: Unquoted keys is quite a popular extension to JSON e.g. [JSON5](http://json5.org). Note that unquoted values, as in [Relaxed JSON](https://github.com/phadej/relaxed-json) is not permitted; identifiers are reserved for future extensions of JinXML.

## Trailing and optional commas

## End of line comments

## Long comments

## XML-like tags

## XML-like comments

## Single-quoted (XML) string literals with HTML5 escapes

## Double-quoted JSON string literals escapes extended with HTML5 character entities
