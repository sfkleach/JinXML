# What is the standard JinXML Object Model?

## Overview

DESCRIBE THE AIMS OF THE STANDARD OBJECT MODEL - SAME AS THE STANDARD PUSH PARSER

The following classes and methods illustrate a _typical_ instantiation of the standard object model.

## Class JOM

### Declarative Methods

* Method ```name() -> String```
* Method ```attributesCollection() -> MultiMap< String, String >```
* Method ```getAttribute( String key = 0, Int position = 0, String? default = null ) -> String?```
* Method ```allAttributes( String? key = null, Int position? = 0) -> List< String >```
* Method ```childrenCollection() -> MultiMap< String, JOM >```
* Method ```getChild( String key = "", Int position = 0, JOM? default = null ) -> JOM?```
* Method ```allChildren( String? key = null, Int? position = null ) -> List< JOM >```
* Method ```isIntValue() -> Boolean```
* Method ```getIntValue( Int? default = null ) -> Int?```
* Method ```isFloatValue() -> Boolean```
* Method ```getFloatValue( Float? default = null ) -> Float?```
* Method ```isStringValue() -> Boolean```
* Method ```getStringValue( String? default = null ) -> String?```
* Method ```isBooleanValue() -> Boolean```
* Method ```getBooleanValue( Boolean? default = null ) -> Boolean?```
* Method ```isNullValue() -> Boolean```
* Method ```getNullValue( Null? default = null ) -> Null?```

### Mutators 

* Method ```setName( String name )```
* Method ```setAttributesCollection( MultiMap< String, String > attributes )```
* Method ```setAttribute( String key = 0, Int position = 0, String? value = null, String padding = "" )```

## Class Builder

INSTANTIATE THE BUILDER METHODS
