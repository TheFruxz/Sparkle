//[JET-Native](../../index.md)/[de.jet.library.extension.data](index.md)/[fromJson](from-json.md)

# fromJson

[jvm]\
inline fun &lt;[T](from-json.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html).[fromJson](from-json.md)(): [T](from-json.md)

Tries to decode the given JSON string to an object type [T](from-json.md) using the Kotlinx serialization library's Json.decodeFromString function.

#### Return

The decoded object.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| T | The result type, which is the destination type. |
