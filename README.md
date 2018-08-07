# JinXML

JSON in XML - a clean and practical fusion of JSON and minimal XML syntax. Recommended file extension .jinxml

## What Is It?

JinXML is an extension of JSON syntax that adds XML-like start, end and standalone tags that adds a convenience and expressive power to JSON suitable for representing hierarchical or 'tree-like' data. 

## Example

Here's a simple example that fits in some of the features of JinXML.
```
<markers> 
    <marker>
        /* When a field has multiple values it's natural to use parentheses */
        name:       "Rixos The Palm Dubai",
        location:   ( 25.1212, 55.1535 )
    </marker>
    <marker>
        // Commas can be omitted or swapped for semi-colons. 
        name:       'Shangri-La Hotel';
        location:   ( 25.2084 55.2719 )
    </marker>
    <marker>
        <!-- Trailing commas are allowed. Also single-quotes, as in HTML. -->
        name:       "Grand Hyatt";
        location:   ( 25.2285, 55.3273, )
    </marker>
</markers>
```

## How much JSON?

All of it. Thanks to the minimalistic design of JSON it is well-suited to being extended: JinXML is a true superset of JSON.

## How much XML?

Not so much. JinXML is based on the minimal XML subset [MinXML](https://github.com/sfkleach/MinXML) that uses the three types of tags from XML:

* Start tags, with attributes.
* End tags.
* Empty (or "fused") tags, with attributes.

But it does not embrace the wider range of XML bells and whistles:

* Character data - not included (see below for explanation).
* Comments - allowed but discarded.
* Processing instructions - allowed but discarded.
* Entities - only numerical entities and the HTML5 standard entities are recognised.
* Prologue, DTD or other schema - allowed but discarded
* Character encoding - the API requires streams of decoded characters.

N.B. MinXML is a strict subset of both JinXML and XML.

## Why?

The core of XML seems very simple: named elements with attributes and children. But as soon as you start writing programs to that use XML to represent data, you discover it is a surprisingly complicated format. Not only do you have to worry about extraneous features such as processing directives but also management issues such as validation against schemas -  and what format will the schema be supplied in? When all you want to do is represent data, you become engaged in complexities that aren’t relevant. And those complexities often leads to confusion e.g. which is better ```<temperature value="98.4"/>```, or ```<temperature>98.4</temperature>```?

JSON has proved successful in part because it strips away complications that aren't about the data and adds some syntactic richness to make representation both easier and more straightforward. But in some ways it is too stripped down, having so support for comments (self-description), lacks clarity on duplicate keys and the choice of primitive values seems somewhat arbitrary. This has spawned a cottage industry of variants such as [Relaxed JSON](http://www.relaxedjson.org/), [Really Relaxed JSON](https://www.npmjs.com/package/really-relaxed-json), [JSON::Relaxed](https://metacpan.org/pod/JSON::Relaxed) and [BSON](http://bsonspec.org/).

Minimal XML showed that we could retain just the core of XML, using only start and end tags, and have something very useful and an exceptionally neat API. But it is verbose. The motivation behind JinXML was to cure this verbosity by adding in the syntax of JSON whilst retaining this nice API and, ideally, keep compatibility by making JinXML a strict superset of both. Not only is this possible but surprisingly it adds multiple values in a natural way.



