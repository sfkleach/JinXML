# Grammar of JinXML

## EBNF
This is a complete single-level grammar for JinXML in EBN, as opposed to splitting off a tokenisation phase. This is illustrated with a railroad diagram, courtesy of the excellent [Railroad Diagram Generator](http://bottlecaps.de/rr/ui). Some of the important non-terminal names (NCName) are borrowed from the XML specification. Content that should be discarded is associated with D and is made up of commas, comments and whitespaces (S).

```
InitialJinXML ::= Shebang? JinXML
JinXML ::= Element | JSON | D JinXML
Element ::= StartTag (LeadsWithJSON | LeadsWithElement)? D? EndTag | FusedTag
LeadsWithJSON ::= EntryPrefix? JSON ((D? LeadsWithElement)|(D LeadsWithJSON))? |  EntryPrefix? LeadsWithElement
LeadsWithElement ::= Element (D? (LeadsWithJSON | LeadsWithElement))?
StartTag ::= '<' D? ElementName ( D Attributes)* D? '>'
EndTag ::= '</' D? ElementName D? '>' | '</' D? '>'
FusedTag ::= '<' D? ElementName D? Attributes D? '/' '>'
ElementName ::= NCName | '+' | String
Attribute ::= AttributeName D? ( '+'? [:=] D? String )
AttributeName ::= NCName | String
NCName ::= [http://www.w3.org/TR/xml-names/#NT-NCName]
JSON ::= Reserved | Variable | Number | String | Array | Object
Variable ::= Identifier - Reserved
Reserved ::= 'null' | 'true' | 'false'
Identifier ::= [a-zA-Z_] [a-zA-Z0-9_]*
Number ::= '-'? [0-9]+ ( '.' [0-9]+ )? ( ( 'e' | 'E' ) [0-9]+ )?
String ::= SingleQuotedString | DoubleQuotedString
DoubleQuotedString ::= '"' ([^"\]|BEscape)* '"' | "'" ([^'\]|BEscape)* "'"
BEscape ::= '\' ( ["'\/bfnrt] | 'u' Hex Hex Hex Hex | Reference )
StringQuotedString ::= "'" ([^&>"]|XEscape)* "'"
XEscape ::= '&' (NamedCharacterReference|'#' [0-9]+|'#x' Hex+|'\' BEscape)';'
NamedCharacterReference ::= [http://www.w3.org/TR/html5/syntax.html#named-character-references]
Hex ::= [0-9a-fA-F]
Array ::= '[' Children  ']'
Object ::= '{' D? ( Entry D?)* '}'
Entry ::= EntryPrefix JinXML
EntryPrefix ::= EntryKey D? '+'? [:=] D? 
EntryKey ::= Identifier | String
D ::= ( S | XComment | XOther | JComment | ',' )+
XComment ::= '<!--' ( [^-]* | '-'+ [^->] )* '-'* '-->' 
XOther ::= '<' [?!] [^>]* '>' 
JComment ::= LongComment | EoLComment
LongComment ::=  '/*' ( [^*] | '*'+ [^*/] )* '*'* '*/'
EoLComment ::= '//' [^#xA]* #xA
Shebang ::= '#!' [^#xA]* #xA
S ::= (#x20 | #x9 | #xD | #xA)+
```

## Railroad Diagram

__Initial JinXML__: Root of the grammar

![Image of InitialJinXML rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/InitialJinXML.png "Root of the syntax tree")

__JinXML__: JinXML is the non-terminal through which all recursion happens

![Image of JinXML rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/JinXML.png "JinXML is the non-terminal through which all recursion happens")

__Element__: Element are made up of tags

![Image of Element rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Element.png "Element are made up of tags")

__LeadsWithJSON__: This helps capture the mandatory spacing between JSON expressions

![Image of LeadsWithJSON rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/LeadsWithJSON.png "This helps capture the mandatory spacing between JSON expressions")

__LeadsWithElement__: This helps capture the mandatory spacing between JSON expressions

![Image of LeadsWithJSON rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/LeadsWithElement.png "This helps capture the mandatory spacing between JSON expressions")

__StartTag__: Must be paired with an EndTag

![Image of StartTag rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/StartTag.png "Must be paired with an EndTag")

__EndTag__: Must be paired with a StartTag

![Image of EndTag rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/EndTag.png "Must be paired with a StartTag")

__FusedTag__: Combines a start-and-end tag pair when there are no children

![Image of FusedTag rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/FusedTag.png "Combines a start-and-end tag pair when there are no children")

__ElementName__: Element names, attribute keys and object keys are almost identical - but '+' is allowed for element names.

![Image of ElementName rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/ElementName.png "Element names support + for defaulting")

__Attribute__: An attribute pairs up a name with a string value

![Image of Attribute rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Attribute.png "An attribute pairs up a name with a string value")

__AttributeName__: May be quoted or unquoted

![Image of AttributeName rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/AttributeName.png "May be quoted or unquoted")

__NCName__: Same as XML spec

![Image of NCName URL](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/NCName.png "Same as XML spec")

__JSON__: Denotes a JSON-styled expression

![Image of JSON rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/JSON.png "Denotes a JSON-styled expression")

__Variable__: looks like an unquoted identifier, excluded the JSON reserved identifiers.

![Image of Variable rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Variable.png "looks like an unquoted identifier, excluded the JSON reserved identifiers")

__Reserved__: JSON reserves null, true and false

![Image of Reserved rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Reserved.png "JSON reserves null, true and false")

__Identifier__: Supports underscore

![Image of Identifier rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Identifier.png "Supports underscore")

__Number__: As per JSON

![Image of Number rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Number.png "As per JSON")

__String__: Merges JSON-style and XML-style strings

![Image of String rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/String.png "Merges JSON-style and XML-style strings")

__DoubleQuotedString__: JSON-style string with enhanced escapes.

![Image of DoubleQuotedString rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/DoubleQuotedString.png "JSON-style string with enhanced escapes")

__BEscape__: Backslash based escape sequence

![Image of BEscape rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/BEscape.png "Backslash based escape sequence")

__StringQuotedString__: XML-style string with enhanced escapes.

![Image of StringQuotedString rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/StringQuotedString.png "XML-style string with enhanced escapes")

__XEscape__: Ampersand based escapes

![Image of XEscape rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/XEscape.png "hover text")

__NamedCharacterReference__: HTML5 based names for characters

![Image of NamedCharacterReference rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/NamedCharacterReference.png "HTML5 based names for characters")

__Hex__: Hex digits

![Image of Hex rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Hex.png "Hex digits")

__Array__: JSON-style array brackets

![Image of Array rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Array.png "JSON-style array brackets")

__Object__: JSON-style object brackets

![Image of Object rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Object.png "JSON-style object brackets")

__Entry__: Member of JSON-style object

![Image of Entry rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Entry.png "Member of JSON-style object")

__EntryPrefix__: Corresponds to "key =" in JSON.

![Image of EntryPrefix rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/EntryPrefix.png "Corresponds to 'key =' in JSON")

__EntryKey__: Same as attribute key

![Image of EntryKey rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/EntryKey.png "Same as attribute key")

__D__: Input that should be *D*iscarded

![Image of D rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/D.png "Input that should be discarded")

__XComment__: XML-style comments

![Image of XComment rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/XComment.png "XML-style comments")

__XOther__: Complex content rule for XML-style comment

![Image of XOther rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/XOther.png "Complex content rule for XML-style comment")

__JComment__: Javascript-like comments

![Image of JComment rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/JComment.png "Javascript-like comments")

__LongComment__: Javascript multi-line comments

![Image of LongComment rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/LongComment.png "Javascript multi-line comments")

__EoLComment__: Javascript end of line comment

![Image of EoLComment rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/EoLComment.png "Javascript end of line comment")

__Shebang__: Unix-style #! header

![Image of Shebang rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Shebang.png "Unix-style #! header")

__S__: White*s*pace to be discarded

![Image of S rule](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/S.png "Whitespace to be discarded")


