# Standard Push Parser

## What is a push parser?
A push-parser consumes an input stream and generates a stream of events that can be used to recognise 
the input and construct a model of the input. Typically the events are driven from a relatively small
window onto the input stream, so that the memory consumption of a push parser is very modes. This means 
that such a push parser is suitable for analysing very large data sets as its memory footprint stays small
relative to the size of input.

The event stream is typically realised by the push-parser being passed a 'listener' objects whose 
methods correspond to the different type of events. As the push-parser progresses, for each event it
calls the corresponding method of the listener. These methods will have side-effects, so that the
listener can incrementally build the output.

## What are the events for JinXML's standard push-parser

JinXML defines a standard listener interface, which supports the following methods, all returning nothing. 
By and large these are self-explanatory, although both ```AddIdentifier``` and ```AddLiteral``` need motivation.

* Method StartTag( String? key )
* Method Attribute( String key, String Value, Boolean solo = true )
* Method EndTag( String? key )
* Method StartArray( String key )
* Method EndArray()
* Method StartObject()
* Method StartEntry( String key, Boolean solo = true )
* Method EndEntry()
* Method EndObject()
* Method AddLiteral( String value )
* Method AddInt( String value )
* Method AddFloat( String value )
* Method AddString( String value )
* Method AddIdentifier( String value )
* Method AddBoolean( String value )
* Method AddNull( String value )
