# Class PushParser and Handler - the Standard Push Parser interface

## What is a Push Parser?

A push-parser consumes an input stream and generates a series of events that can be used to recognise the input and construct a model of the input. Typically the events are driven from a relatively small section of the input stream, so that the memory consumption of a push parser is very modest. This means  that such a push parser is suitable for analysing very large data sets as its memory footprint stays small relative to the size of input.

The event series is typically realised by the push-parser being passed a 'handler' objects whose 
methods correspond to the different type of events. As the push-parser progresses, for each event it
calls the corresponding method of the handler. These methods will have side-effects, so that the
handler can incrementally build the output.

## A Standard for Push Parsers
This section describes a standard push parser for JinXML that we encourage people writing libraries
to fit into as best they can. This is so that developers, who are often working in multiple programming languages, can rely on a basic set of features. We don't want to stop library developers from innovating (we couldn't anyway!) so this is deliberately phrased as an encouragement.

A standard push-parser:
 - Consumes a stream of characters and produces a _stream_ of events.
 - It must support an input consisting of multiple (zero or more) JinXML expressions on the input.
 - It must make an output event available as soon as possible i.e. it must not be necessary to consume 
   additional input characters before the output event is available.
 - Can provide the stream of events available as a stream e.g. iterator, enumerable, (Java) stream.
 - Can provide the stream of events as method calls on a standard-handler.
 - Can be advanced event-by-event.
 - Can be advanced JinXML expression-by-expression.
 - Can advance across the entire input.

Below we elaborate a _typical_ set of classes, functions and methods for meeting the above standard. The standard does not require a library to follow these names or even the same classes and relationships.

## Class PushParser

The PushParser wraps an input-stream in order to produce a stream of events. The input-stream is consumed on demand by the read-methods.

### Constructor method

* Constructor `newPushParser( InputStream input, Bool singleReturn = true ) -> PushParser` - returns a PushParser based on the input stream. If `singleReturn` is true then the input stream *must* consist of a single JinXML element. As soon as the implementation detects that this is not true it must raise an exception and this must be no later than reading the end of input or encountering a non-whitespace character after the end of the first JinXML element.

### Reader methods

* Method `readEvent( Event? orElse = null ) -> Event?` - consumes just enough of the input to determine the next event. If the end of input has been reached then `orElse` is returned.
* Method `readExpression() -> Iterator< Event >`  - creates an iterator that, when advanced, will incrementally consume just enough of the input stream to produce the events of a single JinXML value. If the input is exhausted during this process an exception is raised and if the input is not well-formed then an exception is raised.
* Method `readInput() -> Iterator< Event >` - creates an iterator that, when advanced, will incrementally consume the entire input stream and generates the events associated with that. An exception is raised if the input is not well-formed, meaning that it must be the events associated with a series of JinXML elements.

### Push-to-handler methods

* Method `sendEvent( EventHandler handler )` - consumes just enough of the input to determine the send the next event to the handler. If the end of input has been reached no event is sent.
* Method `sentExpression( EventHandler handler )` - consumes just enough of the input stream to produce the events of a single JinXML value. If the input is exhausted during this process an exception is raised and if the input is not well-formed then an exception is raised.
* Method `sendInput( EventHandler handler )` - consumes the entire input stream and generates the events associated with that and sends them to the handler. An exception is raised if the input is not well-formed, meaning that it must be the events associated with a series of JinXML elements.


## Abstract Class EventHandler

Events and handler-methods follow an identical scheme with the handler methods follow the same pattern and naming convention as the events.

### Main

* Method `handleEvent() -> Bool` - reads the next event, if it exists, and runs the appropriate handler-methods on the result and return true. If there is no next event then it simply returns false.


### Handler Events

* Method `startTagEvent( String key )`
* Method `attributeEvent( String key, String value, Boolean solo = true )`
* Method `endTagEvent( String? key = null )`
* Method `startArrayEvent()`
* Method `endArrayEvent()`
* Method `startObjectEvent()`
* Method `startEntryEvent( String key = null, Boolean solo = true )`
* Method `endEntryEvent()`
* Method `endObjectEvent()`
* Method `intEvent( String value )`
* Method `floatEvent( String value )`
* Method `stringEvent( String value )`
* Method `booleanEvent( String value )`
* Method `nullEvent( String value )`
