# Two-level Grammar of JinXML

## Overview
JinXML has a whitespace insensitive layout, which means that it is a good idea to split the syntax into two phases: a lower-level tokenisation phase and an upper level parsing phase. This page describes both levels for JinXML in [EBNF](https://en.wikipedia.org/wiki/Extended_Backus%E2%80%93Naur_form) and also illustrates the grammars with railroad diagrams, courtesy of the excellent [Railroad Diagram Generator](http://bottlecaps.de/rr/ui). 

## Upper-Level Grammar in EBNF, corresponds to parse phase
```
JinXML ::= Element | JSON
Element ::= StartTag ( ( Entry | JinXML ) Terminator? )* EndTag | FusedTag
StartTag ::= '<' ElementName Attribute* '>'
EndTag ::= '</' ElementName '>'
FusedTag ::= '<' ElementName Attribute* '/>'
ElementName ::= NCName | '&' | String
Attribute ::= FieldPrefix String
NCName ::= [http://www.w3.org/TR/xml-names/#NT-NCName]
JSON ::= Reserved | Number | String | Array | Object
Reserved ::= 'null' | 'true' | 'false'
Array ::= '[' ( JinXML Terminator? )*  ']'
Object ::= '{' ( Entry Terminator? )* '}'
Entry ::= ( NCName | String ) ( ':' | '=' | '+:' | '+=' ) JinXML 
Entry ::= '&' ( ':' | '=' | '+:' | '+=' ) Element
Terminator ::= ',' | ';'
```

The following side-conditions apply:
* ElementNames in paired tags must not differ, where ```&``` is considered to automatically match.
* ```&``` can only be used in a StartTag when its element appears on the right of an Entry.
* ```&``` can only be used as an Entry name when followed by a named StartTag (not ```&```).

## Top Level Grammar as Railroad Diagram

__JinXML__: JinXML is the non-terminal through which all recursion happens

![Image of JinXML rule](grammar/images/JinXML.png "JinXML is the non-terminal through which all recursion happens")

__Element__: Element are made up of tags

![Image of Element rule](grammar/images/Element.png "Element are made up of tags")

__StartTag__: Must be paired with an EndTag

![Image of StartTag rule](grammar/images/StartTag.png "Must be paired with an EndTag")

__EndTag__: Must be paired with a StartTag

![Image of EndTag rule](grammar/images/EndTag.png "Must be paired with a StartTag")

__FusedTag__: Combines a start-and-end tag pair when there are no children

![Image of FusedTag rule](grammar/images/FusedTag.png "Combines a start-and-end tag pair when there are no children")

__ElementName__: Element names, attribute keys and object keys are almost identical - but '+' is allowed for element names.

![Image of ElementName rule](grammar/images/ElementName.png "Element names support + for defaulting")

__Attribute__: An attribute pairs up a name with a string value

![Image of Attribute rule](grammar/images/Attribute.png "An attribute pairs up a name with a string value")

__NCName__: Same as XML spec

![Image of NCName URL](grammar/images/NCName.png "Same as XML spec")

__JSON__: Denotes a JSON-styled expression

![Image of JSON rule](grammar/images/JSON.png "Denotes a JSON-styled expression")

__Reserved__: JSON reserves null, true and false

![Image of Reserved rule](grammar/images/Reserved.png "JSON reserves null, true and false")

__Array__: JSON-style array brackets

![Image of Array rule](grammar/images/Array.png "JSON-style array brackets")

__Object__: JSON-style object brackets

![Image of Object rule](grammar/images/Object.png "JSON-style object brackets")

__Entry__: Member of JSON-style object

![Image of Entry rule](grammar/images/Entry.png "Member of JSON-style object")

__Terminator__: Optional comma or semi between members of arrays, objects or elements.

![Image of Terminator rule](grammar/images/Terminator.png "Optional comma or semi between members of arrays, objects or elements")



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
Discard ::= ( Whitespace | XComment | XOther | JComment )+
XComment ::= '<!--' ( [^-]* | '-'+ [^->] )* '-'* '-->' 
XOther ::= '<' [?!] [^>]* '>' 
JComment ::= LongComment | EoLComment
LongComment ::=  '/*' ( [^*] | '*'+ [^*/] )* '*'* '*/'
EoLComment ::= '//' [^#xA]* #xA
Whitespace ::= (#x20 | #x9 | #xD | #xA)+
Shebang ::= ('#!' [^#xA]* #xA)+
```

## Lower-Level Grammar for Tokenisation as Railroad Diagrams

__Reserved__: identifiers that play the role of literal constants

![Image of Reserved rule](grammar/images/Reserved.png)

__Numbers__: Only base 10 so far

![Image of Number rule](grammar/images/Number.png)

__Strings__: Single and double quoted strings and their symmetrical escape sequences

![Image of String rule](grammar/images/String.png)

__DoubleQuotedString__: JSON-like double-quoted strings

![Image of DoubleQuotedString rule](grammar/images/DoubleQuotedString.png)

__BEscape__: JSON-style Escapes

![Image of BEscape rule](grammar/images/BEscape.png)

__SingleQuotedString__: XML-like single-quoted strings

![Image of StringQuotedString rule](grammar/images/StringQuotedString.png)

__XEscape__: XML-style Escapes

![Image of XEscape rule](grammar/images/XEscape.png)

__NamedCharacterReference__: 

![Image of NamedCharacterReference rule](grammar/images/NamedCharacterReference.png)

__Hex__: Hex Characters

![Image of Hex rule](grammar/images/Hex.png)

__Discards__: Tokens to be discarded

![Image of Discard rule](grammar/images/Discard.png)

__XComment__: XML-style comment

![Image of XComment rule](grammar/images/XComment.png)

__XOther__: Other XML-content to be discarded

![Image of XOther rule](grammar/images/XOther.png)

__JComment__: JSON-style comment

![Image of JComment rule](grammar/images/JComment.png)

__LongComment__: Multi-line Javascript like comment

![Image of LongComment rule](grammar/images/LongComment.png)

__EoLComment__: End of Line Javascript style comment

![Image of EoLComment rule](grammar/images/EoLComment.png)

__Whitespace__:

![Image of Whitespace rule](grammar/images/Whitespace.png)

__Shebang__:

![Image of Shebang rule](grammar/images/Shebang.png)
