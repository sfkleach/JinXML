# Grammar of JinXML

## EBNF
This is a complete single-level grammar for JinXML in EBN, as opposed to splitting off a tokenisation phase. This is illustrated with a railroad diagram, courtesy of the excellent [Railroad Diagram Generator](http://bottlecaps.de/rr/ui). Some of the important non-terminal names (NCName) are borrowed from the XML specification. Content that should be discarded is associated with D and is made up of commas, comments and whitespaces (S).

```
JinXML ::= Element | JSON
Element ::= StartTag ( EntryPrefix? JinXML )* EndTag | FusedTag
StartTag ::= '<' ElementName Attribute*  '>'
EndTag ::= '</' ElementName Attribute* '>'
FusedTag ::= '<' ElementName Attribute* '/>'
ElementName ::= NCName | '+' | String
Attribute ::= AttributeName ( [:=] | '+:' | '+=' ) String
AttributeName ::= NCName | String
NCName ::= [http://www.w3.org/TR/xml-names/#NT-NCName]
JSON ::= Reserved | Number | String | Array | Object
Reserved ::= 'null' | 'true' | 'false'
Array ::= '[' JinXML*  ']'
Object ::= '{' Entry* '}'
Entry ::= EntryPrefix JinXML
EntryPrefix ::= (Identifier | String) ( [:=] | '+:' | '+=' )
```

## Railroad Diagram

__JinXML__: JinXML is the non-terminal through which all recursion happens

![Image of JinXML rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/JinXML.png "JinXML is the non-terminal through which all recursion happens")

__Element__: Element are made up of tags

![Image of Element rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/Element.png "Element are made up of tags")

__StartTag__: Must be paired with an EndTag

![Image of StartTag rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/StartTag.png "Must be paired with an EndTag")

__EndTag__: Must be paired with a StartTag

![Image of EndTag rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/EndTag.png "Must be paired with a StartTag")

__FusedTag__: Combines a start-and-end tag pair when there are no children

![Image of FusedTag rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/FusedTag.png "Combines a start-and-end tag pair when there are no children")

__ElementName__: Element names, attribute keys and object keys are almost identical - but '+' is allowed for element names.

![Image of ElementName rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/ElementName.png "Element names support + for defaulting")

__Attribute__: An attribute pairs up a name with a string value

![Image of Attribute rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/Attribute.png "An attribute pairs up a name with a string value")

__AttributeName__: May be quoted or unquoted

![Image of AttributeName rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/AttributeName.png "May be quoted or unquoted")

__NCName__: Same as XML spec

![Image of NCName URL](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/NCName.png "Same as XML spec")

__JSON__: Denotes a JSON-styled expression

![Image of JSON rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/JSON.png "Denotes a JSON-styled expression")

__Reserved__: JSON reserves null, true and false

![Image of Reserved rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/Reserved.png "JSON reserves null, true and false")

__Array__: JSON-style array brackets

![Image of Array rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/Array.png "JSON-style array brackets")

__Object__: JSON-style object brackets

![Image of Object rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/Object.png "JSON-style object brackets")

__Entry__: Member of JSON-style object

![Image of Entry rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/Entry.png "Member of JSON-style object")

__EntryPrefix__: Corresponds to "key =" in JSON.

![Image of EntryPrefix rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/EntryPrefix.png "Corresponds to 'key =' in JSON")

