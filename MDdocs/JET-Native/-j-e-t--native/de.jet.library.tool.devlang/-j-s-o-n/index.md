//[JET-Native](../../../index.md)/[de.jet.library.tool.devlang](../index.md)/[JSON](index.md)

# JSON

[jvm]\
data class [JSON](index.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [DevLangObject](../-dev-lang-object/index.md)

This data class represents a chunk of JSON code stored inside the [value](value.md)

#### Author

Fruxz

#### Since

1.0

## Constructors

| | |
|---|---|
| [JSON](-j-s-o-n.md) | [jvm]<br>fun [JSON](-j-s-o-n.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) : [Constructable](../../de.jet.library.tool.base/-constructable/index.md)&lt;[JSON](index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [fromJson](from-json.md) | [jvm]<br>inline fun &lt;[T](from-json.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [fromJson](from-json.md)(): [T](from-json.md)<br>Converts the [value](value.md) to a [T](from-json.md) object using the [fromJson](from-json.md) function |

## Properties

| Name | Summary |
|---|---|
| [value](value.md) | [jvm]<br>open override val [value](value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
