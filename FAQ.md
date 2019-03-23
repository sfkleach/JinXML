Frequently Asked Questions

Q: Why another notation?

A1: This is definitely the most frequently asked question. JinXML performs the same role as JSON, XML, YAML (etc) but is a more direct and easy-to-use notation for the common case of a hierarchy of typed-objects. Don't write '<point><int>1</int><int>-1</int></point>` (clumsy) or `{"point":[1,-1]}` (decoding required) when you can write `<point>1,-1</point>` which is easy to write, easy to read and easy to use in code.

A2: The historical answer is that I started by wanting an augmented version of JSON that included naming the type of objects. XML start/end tags were familiar and fitted the bill, so rather than write `{ "int list": [1,2,3] }` I could write `<int.list>1, 2, 3</&>`. At the same time I wanted to preserve the really clean API that I had found for a reduced XML variant I had been using for several years. This segued into a design quest to blend the XML syntax into JSON as naturally as possible _and_ have a uniform API for the JSON and XML components. And that design iteration went on for about 3 years until I ended up with a version I genuinely liked.

--- 

Q: How do you mark the end of a JSON value and the beginning of the next key if commas are optional and whitespace is discarded (ditto for JSON arrays)?

A: Whitespace is discarded during tokenisation but is used to separate tokens. 

---

Q: Can I also write `[1; 2; 3;]` (not that I’d want to)? 

A: Yes (and I wouldn't want to either). The semi-colon is added because (1) some JSON variants include it and we strive to be inclusive (2) when the members have propositional semantics it sometimes reads nicely and (3) it fits within the overarching vision of JinXML. And, yes, I used the 'v' word. 


