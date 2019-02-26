# What is the JinXML Object Model: Element and friends

## Overview

## A Template for Implementation rather than a standard

This section describes a object model in the form of a template for implementing JinXML that we encourage people writing libraries to fit into as best they can. This is so that developers, who are often working in multiple programming languages, can rely on a basic set of features. We don't want to stop library developers from innovating (we couldn't anyway!) so this is deliberately phrased as an encouragement.

Because of the variation between programming languages, such as the different core classes (streams/iterators, sequences/lists, maps/dictionaries and exceptions), we expect developers to be explicit about how they map from the classes of this template to the target language. And we expect this mapping to be consistent throughout the whole implementation.

## Core Classes

The following classes should appear in every instantiation of the JinXML object model. 

* [Element](Element.md) - a class representing a single JinXML value.
* [Builder](Builder.md) - a class for incrementally constructing Elements from an event stream.
* [PushParser](PushParser.md) - a class for generating an event stream.
* [Event](Event.md) - a class representing all the different event types that can be generated.

Helper classes that are likely to arise in most instantiations:

* [Attribute](Attribute.md) - represents a key-value pair for an iteration over attributes.
* [AttributeIterable](AttributeIterable.md) - a specialised support class for iteration over attributes.
* [Member](Member.md) - represents a selector-child pair for an iteration over members.
* [MemberIterable](MemberIterable.md) - a specialised support class for iteration over members.

## Example: Mapping into Java

