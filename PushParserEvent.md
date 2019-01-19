# Class PushParserEvent

```sml
datatype Event =
  StartTagEvent( String key ) |
  AttributeEvent( String key, String value, Boolean solo = true ) |
  EndTagEvent( String? key = null ) |
  StartArrayEvent() |
  EndArrayEvent() |
  StartObjectEvent() |
  StartEntryEvent( String? key = null, Boolean solo = true ) |
  EndEntryEvent() |
  EndObjectEvent() |
  IntEvent( String value ) |
  FloatEvent( String value ) |
  StringEvent( String value ) |
  BooleanEvent( String value ) |
  NullEvent( String value )
```