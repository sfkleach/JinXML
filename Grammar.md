# Grammar of JinXML

## EBNF
This is a complete single-levl grammar for JinXML in EBNF, together with railroad diagram. courtesy of the excellent Railroad Diagram Generator. Many of the non-terminal names are taken from the XML specification. Content that should be discarded is associated with D and is made up of commas, comments and whitespaces (S).

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
Reserved ::= Null | Boolean
Null ::= 'null'
Boolean ::= 'true' | 'false'
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

