# Changelog

## [v2.0.0-dev, Unreleased](https://github.com/sfkleach/JinXML/tree/HEAD) (2022-10-28)

The main idea behind this version is reducing complexity and clutter. A few years on from the initial release, we have learned some lessons and made some small improvements.


### Single-quoted string literals

***Breaking change:*** Previously single-quoted string literals used HTML compatible escape sequences. But having different escape character sequences depending on the surrounding quotes was the most uncomfortable design decision. Three years on, trying to preserve as much XML compatibility as possible also seems less valuable. So now single and double-quotes are just interchangeable ways of introducing strings - as in languages such as Python.

    '&copy; Copyright 2022'  - no longer supported :-(
    '\&copy; Copyright 2022' - use this instead :-)


## [v1.0.0](https://github.com/sfkleach/JinXML/tree/v1.0.0) (2019-10-13)

Initial release of JinXML 1.0.0 with the following features:-

 * Strict superset of JSON
 * Duplicate keys supported
 * Unquoted keys
 * Equals as well as colon
 * Trailing and optional commas
 * End of line comments
 * Long comments
 * Double-quoted JSON string literals with HTML5 escapes
 * XML-like tags
 * Colon as well as equals
 * Optional tag names
 * Quoted element names and attribute keys
 * XML-like headers, comments and processing directives
 * Single quoted HTML compatible strings