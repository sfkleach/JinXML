# List of Features in JinXML

* [Strict superset of JSON](#strict-superset-of-json)
* Duplicate keys supported
* Unquoted keys
* Trailing and optional commas
* End of line comments
* Long comments
* XML-like tags
* XML-like comments
* Single-quoted XML string literals with HTML5 escapes
* [Double-quoted JSON string literals escapes extended with HTML5 character entities](#double-quoted-json-string-literals-escapes-extended-with-html5-character-entities)

## Strict superset of JSON
This is self-explanatory: a JinXML parser can read any standard JSON input. In other words nothing in JinXML breaks standard JSON syntax. 

Detail: The output such a parse is almost always the expected embedding of JSON in JinXML. The one exception is that the [description of JSON syntax](https://json.org) is silent on whether duplicate object keys are permitted and some JSON parsers allow them but only take one of the values. A JinXML parser will always take both values.

## Duplicate keys supported



## Unquoted keys

## Trailing and optional commas

## End of line comments

## Long comments

## XML-like tags

## XML-like comments

## Single-quoted (XML) string literals with HTML5 escapes

## Double-quoted JSON string literals escapes extended with HTML5 character entities
