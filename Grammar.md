# Grammar of JinXML

## EBNF
This is a complete single-level grammar for JinXML in EBN, as opposed to splitting off a tokenisation phase. This is illustrated with a railroad diagram, courtesy of the excellent [Railroad Diagram Generator](http://bottlecaps.de/rr/ui). Some of the important non-terminal names (NCName) are borrowed from the XML specification. Content that should be discarded is associated with D and is made up of commas, comments and whitespaces (S).

```
InitialJinXML ::= Shebang? JinXML
JinXML ::= Element | JSON | D JinXML
Element ::= StartTag (LeadsWithJSON | LeadsWithElement)? D? EndTag | FusedTag
LeadsWithJSON ::= JSON (D? LeadsWithElement)? | JSON (D LeadsWithJSON)?
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
Entry ::= EntryKey D? '+'? [:=] D? JinXML
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

![Shebang? JinXML](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/InitialJinXML.png "Root of the syntax tree")

__JinXML __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/JinXML.png "hover text")

__Element __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Element.png "hover text")

__LeadsWithJSON __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/LeadsWithJSON.png "hover text")

__LeadsWithElement __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/LeadsWithElement.png "hover text")

__StartTag __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/StartTag.png "hover text")

__EndTag __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/EndTag.png "hover text")

__FusedTag __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/FusedTag.png "hover text")

__ElementName __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/ElementName.png "hover text")

__Attribute __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Attribute.png "hover text")

__AttributeName __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/AttributeName.png "hover text")

__NCName __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/NCName.png "hover text")

__JSON __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/JSON.png "hover text")

__Variable __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Variable.png "hover text")

__Reserved __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Reserved.png "hover text")

__Identifier __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Identifier.png "hover text")

__Number __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Number.png "hover text")

__String __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/String.png "hover text")

__DoubleQuotedString __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/DoubleQuotedString.png "hover text")

__BEscape __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/BEscape.png "hover text")

__StringQuotedString __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/StringQuotedString.png "hover text")

__XEscape __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/XEscape.png "hover text")

__NamedCharacterReference __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/NamedCharacterReference.png "hover text")

__Hex __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Hex.png "hover text")

__Array __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Array.png "hover text")

__Object __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Object.png "hover text")

__Entry __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Entry.png "hover text")

__EntryKey __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/EntryKey.png "hover text")

__D __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/D.png "hover text")

__XComment __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/XComment.png "hover text")

__XOther __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/XOther.png "hover text")

__JComment __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/JComment.png "hover text")

__LongComment __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/LongComment.png "hover text")

__EoLComment __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/EoLComment.png "hover text")

__Shebang __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/Shebang.png "hover text")

__S __: _to be done_

![alt text](https://raw.githubusercontent.com/sfkleach/JinXML/master/grammar/images/S.png "hover text")


