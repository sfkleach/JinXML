# Frequently Asked Questions

{:toc}

## Q: Why another notation?

This is definitely the most frequently asked question. JinXML adds some much needed features to JSON. The first is that you can use XML-like tags to assign types to objects directly, rather than indirectly via the referencing key e.g.

 - JinXML - `<point>x:1,y:-1</&>`, where the tag tells us that this a point.
 - JSON - `{"point":{"x":1,"y":-1}}`, where we construct an artificial outer object to encode the type.

This type-tagging works for arrays too:

 - `<timings>0.12, 0.46, 0.83, 0.96, 0.96, 0.20 </&>`

It also adds features that are common with other JSON-variants, such as unquoted keys, comments, and trailing commas that other people have found a need to add to JSON. And lastly it lets you evolve single-valued data into multi-valued data. For example, if you started with a book collection that looked like this:

```xml
<book.list>
    <book>title: "The Martian", author: "Andy Weir"</book>
    <book>title: "Neuromancer", author; "William Gibson"</book>
    <book>title: "The Space Merchants", author: "Frederik Pohl"</book>
</book.list>
```

but you then discover that some books have co-authors, you don't have to edit all the book entries to be array-values like you might in JSON:

```json
{ "book.list":[
    {"book": {"title": "The Martian", "author": ["Andy Weir"]}},
    {"book": {"title": "Neuromancer", "author": ["William Gibson"]}},
    {"book": {"title": "The Space Merchants", "author": ["Frederik Pohl", "Cyril M. Kornbluth"]}}
]}
```

instead you smoothly convert the exceptionally multi-valued field like this:

```xml
<book.list>
    <book>title: "The Martian", author: "Andy Weir"</book>
    <book>title: "Neuromancer", author; "William Gibson"</book&>
    <book>title: "The Space Merchants", author: "Frederik Pohl", author+: "Cyril M. Kornbluth"</book>
</book.list>
```

Importantly, the API (aka object model) allows this smooth transition in a clean and easy-to-learn way.

--- 

## Q: Is there a Pygments/Rouge/CodeRay syntax highlighter for JinXML?

Not yet. We will probably just do one to start with (probably Rouge for GitHub Pages integration) - let us know what you think. This would fit into the planned version 1.1 objectives.

## Q: Is there a JinXML extension for Visual Studio Code / Sublime / BBEdit / Notepad++?

Not yet /cry. Again, we would probably do just one to start (probably VS Code) & that would fit into version 1.1 of the roadmap too.

## Q: Is there a schema for JinXML like there is for JSON?

Not yet. But if you want to have a stab at it, you would be very welcome. If/when we get around to implementing this, it is unlikely to be like [JSON Schema](https://json-schema.org) because that looks complicated and, at the same time, weak. We do have a longer-term vision for JinXML and whatever it would need to fit into that - and at the moment it looks like the planned version 3 would be a good time to take this on.

--- 

## Q: How do you mark the end of a JSON value and the beginning of the next key if commas are optional and whitespace is discarded (ditto for JSON arrays)?

Whitespace is discarded as part of tokenisation but, as you might expect, is used to separate tokens. 

## Q: Can I also write `[1; 2; 3;]` (not that I’d want to)? 

A: Yes (and I wouldn't want to either). The semi-colon is added because (1) some JSON variants include it and we strive to be inclusive (2) when the members have propositional semantics it sometimes reads nicely and (3) it fits within the overarching vision of JinXML. 

Another example of when it makes sense to use semi-colons is when writing data with mixed record-and-array contents. For example:

```
<timings> By:'SFKL', On:'2019-03-24'; 0.12, 0.46, 0.83, 0.96, 0.96, 0.20; </&>
```
