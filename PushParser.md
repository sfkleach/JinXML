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

## A Standard for Push Parsers
This section describes a standard push parser for JinXML that we encourage people writing libraries
to fit into as best they can. This is so that developers, who are often working in multiple programming
languages, can rely on a basic set of features. We don't want to stop library developers from 
innovating (we couldn't anyway!) so this is deliberately phrased as an encouragement.

A standard push-parser:
 - Consumes a stream of characters and produces a _stream_ of events.
 - It must support an input consisting of multiple (zero or more) JinXML expressions on the input.
 - It must make an output event available as soon as possible i.e. it must not be necessary to consume 
   additional input characters before the output event is available.
 - Can provide the stream of events available as a stream e.g. iterator, enumerable, lazy list.
 - Can provide the stream of events as method calls on a standard-listener.
 - Can be advanced event-by-event.
 - Can be advanced JinXML expression-by-expression.
 - Can advance across the entire input.

Below we elaborate a _typical_ set of classes, functions and methods for meeting the above standard. The
standard does not require a library to follow these names or even the same classes and relationships.

### Class PushParser
* newPushParser( InputStream? input, bool singleReturn = true ) -> PushParser
* Method readEvent() -> Event
* Method readExpression() -> Stream<Event>
* Method readInput() -> Stream< Event >

### Class Listener
* newListener( PushParser pp ) -> Listener
* Method consumeEvent()
* Method consumeExpression()
* Method consumeInput()

## What are the standard events and listener methods?
Events and listeners follow an identical scheme. Note that events LiteralEvent and IdentifierEvent 
can only be generated when a custom configuration for the push parser is provided.

```sml
datatype Event =
  StartTagEvent( String? key ) |
  AttributeEvent( String key, String value, Boolean solo = true ) |
  EndTagEvent( String? key = null ) |
  StartArrayEvent( String? key = null ) |
  EndArrayEvent( String? key = null ) |
  StartObjectEvent( String? key = null ) |
  StartEntryEvent( String? key = null, Boolean solo = true ) |
  EndEntryEvent( String? key = null, Boolean solo = true ) |
  EndObjectEvent( String? key = null ) |
  IntEvent( String value ) |
  FloatEvent( String value ) |
  StringEvent( String value ) |
  BooleanEvent( String value ) |
  NullEvent( String value )
```

The listener methods follow the same pattern and naming convention as the events.

* Method ```startTagEvent( String? key = null )```
* Method ```attributeEvent( String key, String value, Boolean solo = true )```
* Method ```endTagEvent( String? key = null )```
* Method ```startArrayEvent( String? key = null )```
* Method ```endArrayEvent( String? key = null )```
* Method ```startObjectEvent( String? key = null )```
* Method ```startEntryEvent( String? key = null, Boolean solo = true )```
* Method ```endEntryEvent( String? key = null, Boolean solo = true )```
* Method ```endObjectEvent( String? key = null )```
* Method ```intEvent( String value )```
* Method ```floatEvent( String value )```
* Method ```stringEvent( String value )```
* Method ```booleanEvent( String value )```
* Method ```nullEvent( String value )```
