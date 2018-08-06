# JinXML

JSON in XML - a fusion of JSON and minimal XML syntax; clean and effective.

## What Is It?

JinXML is an extension of JSON syntax that adds XML-like start, end and standalone tags that adds a convenience and expressive power to JSON suitable for representing hierarchical or 'tree-like' data. 

## Example

Here's the first example from https://json.org/example.html in JinXML:

```
<glossary>
    title: "example glossary",
    <GlossDiv>
        title: "S",
        <GlossList>
            <GlossEntry>
                ID: "SGML",
                SortAs: "SGML",
                GlossTerm: "Standard Generalized Markup Language",
                Acronym: "SGML",
                Abbrev: "ISO 8879:1986",
                <GlossDef>
                    para: "A meta-markup language, used to create markup languages such as DocBook.",
                    GlossSeeAlso: [ "GML", "XML" ]
                </GlossDef>
                GlossSee: "markup"
            </GlossEntry>
        </GlossList>
    </GlossDiv>
</glossary>
```



## How much JSON?

All of it. Thanks to the minimalistic design of JSON it is well-suited to being extended: JinXML is a true superset of JSON.

## How much XML?

JinXML is based on the minimal XML subset [MinXML](https://github.com/sfkleach/MinXML) that uses the three types of tags from XML:

* Start tags, with attributes.
* End tags.
* Empty (or "fused") tags, with attributes.

It does not embrace the wider range of XML bells and whistles:

* Character data - not included (see below for explanation).
* Comments - allowed but discarded.
* Processing instructions - allowed but discarded.
* Entities - only numerical entities and the HTML5 standard entities are recognised.
* Prologue, DTD or other schema - allowed but discarded
* Character encoding - the API requires streams of decoded characters.

N.B. MinXML is a strict subset of both JinXML and XML.

## Why?

JinXML is syntax neutral. By “syntax neutral” we mean that we have a no-frills data format that can be processed with reasonable ease in a very wide variety of programming languages, can be read by programmers and, at a pinch, written as well. We mean that it is free of features that strongly favour people from one particular background. In other words, we have aimed to make it accessible to a very wide range of people, without bias, to the best of our judgement.

So what was the motivation behind stripping XML down to create JinXML? XML was a good starting point because it is designed to be machine processable, has become very widely known and there are a lot of languages providing XML support out of the box. But there were two basic reasons that strongly pushed in the direction of no-frills.

Firstly, as soon as you start writing programs to that use XML to represent data, you realise it is a surprisingly complicated format. Not only do you have to worry about extraneous features such as processing directives but also management issues such as validation against schemas (and what format will the schema be in?) When all you want to do is represent data, you become engaged in complexities that aren’t relevant - such as CDATA blocks.
