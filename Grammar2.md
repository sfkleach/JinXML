# Two-level Grammar of JinXML

## Overview
JinXML has a whitespace insensitive layout, which means that it is a good idea to split the syntax into two phases: a lower-level tokenisation phase and an upper level parsing phase. This page describes both levels for JinXML in [EBNF](https://en.wikipedia.org/wiki/Extended_Backus%E2%80%93Naur_form) and also illustrates the grammars with railroad diagrams, courtesy of the excellent [Railroad Diagram Generator](http://bottlecaps.de/rr/ui). 

## Upper-Level Grammar in EBNF, corresponds to parse phase
```
JinXML ::= Element | JSON
Element ::= StartTag ( Entry* | JinXML* ) EndTag | FusedTag
StartTag ::= '<' ( ElementName Attribute* )? '>'
EndTag ::= '</' ( ElementName Attribute* )? '>'
FusedTag ::= '<' ( ElementName Attribute* )? '/>'
ElementName ::= NCName | '&' | String
Attribute ::= FieldPrefix String
NCName ::= [http://www.w3.org/TR/xml-names/#NT-NCName]
JSON ::= Reserved | Number | String | Array | Object
Reserved ::= 'null' | 'true' | 'false'
Array ::= '[' JinXML*  ']'
Object ::= '{' Entry* '}'
Entry ::= FieldPrefix JinXML
FieldPrefix ::= ( NCName | String ) ( ':' | '=' | '+:' | '+=' )
```

## Top Level Grammar as Railroad Diagram

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

__FieldPrefix__: Corresponds to ```key =``` in JSON or inside a tag, quoted or unquoted.

![Image of AttributeName rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/AttributeName.png "May be quoted or unquoted")

![Image of FIeldPrefix rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/FieldPrefix.png "Corresponds to 'key ='")

## Lower-Level Grammar for Tokenisation in EBNF, corresponds lexical analysis phase
Note that Shebang sequences may only occur at the start of a stream. 

```
Reserved ::= 'null' | 'true' | 'false'
Number ::= '-'? [0-9]+ ( '.' [0-9]+ )? ( ( 'e' | 'E' ) [0-9]+ )?
String ::= SingleQuotedString | DoubleQuotedString
DoubleQuotedString ::= '"' ([^"\]|BEscape)* '"' | "'" ([^'\]|BEscape)* "'"
BEscape ::= '\' ( ["'\/bfnrt] | 'u' Hex Hex Hex Hex | Reference )
StringQuotedString ::= "'" ([^&>"]|XEscape)* "'"
XEscape ::= '&' (NamedCharacterReference|'#' [0-9]+|'#x' Hex+|'\' BEscape)';'
NamedCharacterReference ::= [http://www.w3.org/TR/html5/syntax.html#named-character-references]
Hex ::= [0-9a-fA-F]
Discard ::= ( Whitespace | XComment | XOther | JComment | ',' )+
XComment ::= '<!--' ( [^-]* | '-'+ [^->] )* '-'* '-->' 
XOther ::= '<' [?!] [^>]* '>' 
JComment ::= LongComment | EoLComment
LongComment ::=  '/*' ( [^*] | '*'+ [^*/] )* '*'* '*/'
EoLComment ::= '//' [^#xA]* #xA
Whitespace ::= (#x20 | #x9 | #xD | #xA)+
Shebang ::= ('#!' [^#xA]* #xA)+
```

## Lower-Level Grammar for Tokenisation as Railroad Diagrams

__Reserved__: identifiers that play the role of literal constants.

![Image of Reserved rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/Reserved.png)

__Numbers__: Only base 10 so far.

![Image of Number rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/Number.png)

__Strings__: Single and double quoted strings and their symmetrical escape sequences

![Image of String rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/String.png)

![Image of DoubleQuotedString rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/DoubleQuotedString.png)

![Image of BEscape rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/BEscape.png)

![Image of StringQuotedString rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/StringQuotedString.png)

![Image of XEscape rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/XEscape.png)

![Image of NamedCharacterReference rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/NamedCharacterReference.png)

![Image of Hex rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/Hex.png)

__Discards__: Tokens to be discarded

![Image of Discard rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/Discard.png)

![Image of XComment rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/XComment.png)

![Image of XOther rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/XOther.png)

![Image of JComment rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/JComment.png)

![Image of LongComment rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/LongComment.png)

![Image of EoLComment rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/EoLComment.png)

![Image of Whitespace rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/Whitespace.png)

![Image of Shebang rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar2/images/Shebang.png)
