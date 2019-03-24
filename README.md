# JinXML [![Build Status](https://travis-ci.org/sfkleach/JinXML.svg?branch=master)](https://travis-ci.org/sfkleach/JinXML)

JSON in XML - a clean and practical fusion of JSON and minimal XML syntax. 

{:toc}
This is a test

## What Is It?

JinXML is an extension of JSON syntax that adds XML-like start, end and standalone tags that adds convenience and expressive power to JSON suitable for representing hierarchical or 'tree-like' data. 

_Read more_:
* [List of features](Features.md)
* [JinXML grammar](Grammar.md)
* [Implementations](Implementations.md)


## Example

Here's a simple example that fits in some of the features of JinXML.
```
<markers> 
    <marker>
        /* When a field has multiple values it's natural to use parentheses */
        name:       "Rixos The Palm Dubai",
        location:   [ 25.1212, 55.1535 ]
    </marker>
    <marker>
        // Commas can be omitted or swapped for semi-colons. 
        name:       'Shangri-La Hotel';
        location:   [ 25.2084 55.2719 ]
    </marker>
    <marker>
        <!-- Trailing commas are allowed. Also single-quotes, as in HTML. -->
        name:       "Grand Hyatt";
        location:   [ 25.2285, 55.3273, ]
    </marker>
</markers>
```

_Read more_:
* [List of examples](Examples.md)


## How much JSON is included?

All of it. Thanks to the minimalistic design of JSON it is well-suited to being extended: JinXML is a true superset of JSON.

## How much XML is included?

Not so much. JinXML is based on the minimal XML subset [MinXML](https://github.com/sfkleach/MinXML) that uses the three types of tags from XML:

* Start tags, with attributes.
* End tags.
* Empty (or "fused") tags, with attributes.

But it does not embrace the wider range of XML bells and whistles:

* Character data - not included (see below for explanation).
* Comments - allowed but any content is discarded.
* Processing instructions - allowed but treated as comments.
* Entities - only numerical entities and the HTML5 standard entities are recognised.
* Prologue, DTD or other schema - allowed but treated as comments.
* Character encoding - the API requires streams of decoded characters.

N.B. MinXML is a strict subset of both JinXML and XML.

## What is it good for?

As a notation, JinXML is well-suited to complex, hierarchical data where the lack of inter-operatability with third party applications isn't an issue. It's a bit more flexible and comfortable than either JSON or XML, as outlined below, but its real advantage is that the processing model is very simple. This is very helpful when working with complex data transformations e.g. representing domain specific languages.

_Read more_:
* [Tips for using JinXML](Tips.md)


## Why another notation?

The core of XML seems very simple: named elements with attributes and children. But as soon as you start writing programs to that use XML to represent data, you discover it is a surprisingly complicated format. Not only do you have to worry about extraneous features such as processing directives but also management issues such as validation against schemas -  and what format will the schema be supplied in? When all you want to do is represent data, you become engaged in complexities that aren’t relevant. And those complexities often leads to confusion e.g. which is better ```<temperature value="98.4"/>```, or ```<temperature>98.4</temperature>```?

Minimal XML showed that we could retain just the core of XML, using only start and end tags, and have something very useful and an exceptionally neat API. But it is verbose. The motivation behind JinXML was to cure this verbosity by adding in the syntax of JSON whilst retaining this nice API and, ideally, keep compatibility by making JinXML a strict superset of both.

JSON itself has niggling flaws. It proved successful in part because it strips away complications that aren't about the data and adds some syntactic richness to make representation both easier and more straightforward. But in some ways it is too stripped down, having no support for comments (self-description), lacks clarity on duplicate keys, fussy key and comma syntax, and the choice of primitive values seems somewhat arbitrary. This has spawned variants such as [Relaxed JSON](http://www.relaxedjson.org/), [Really Relaxed JSON](https://www.npmjs.com/package/really-relaxed-json), [JSON::Relaxed](https://metacpan.org/pod/JSON::Relaxed), [JSON5](https://json5.org) and [BSON](http://bsonspec.org/).

So JinXML also strives to take on JSON without requiring the syntactic clutter, taking its cues from XML. So commas are optional, keys do not need string quoting when they are XML names, and attributed elements are the natural generalisation of primitive values. 

Lastly, JinXML was not our first attempt at merging the two notations. JinXML was inspired by seeing how the design tension between arrays and objects could be resolved by shifting to multi-valued maps. This led to a nice interpretation of duplicate keys that makes relations (as opposed to functions) easier to represent. Surprisingly, at least to this writer, it was possible to implement this efficiently 

None of this would matter if JinXML was ugly and unreadable. Fortunately, knocking off the rough edges seems to have been beneficial. 


