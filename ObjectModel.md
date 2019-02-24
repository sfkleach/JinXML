# What is the standard JOM Element class - the JinXML Object Model?

## Overview

## A Standard for Push Parsers

This section describes a standard object model for JinXML that we encourage people writing libraries
to fit into as best they can. This is so that developers, who are often working in multiple programming languages, can rely on a basic set of features. We don't want to stop library developers from innovating (we couldn't anyway!) so this is deliberately phrased as an encouragement.

Because of the variation between programming languages, such as the different base classes (streams/iterators, sequences, maps and exceptions) are modelled, each implementation should endeavour to provide a consistent mapping from the object model described here into the target language. The description provided is intended to be closely aligned with Java.


## Classes

The following classes and methods illustrate a _typical_ instantiation of the standard object model. 

* [Element](Element.md) - a class representing a single JinXML value.
* [MultiMap](MultiMap.md) - a crucial helper class for Elements.
* [Builder](Builder.md) - used for incrementally constructing Elements from an event stream.
* [PushParser](PushParser.md) - generates an event stream.
* [PushParserEvent](PushParserEvent.md) - a class representing all the different event types that can be generated.

