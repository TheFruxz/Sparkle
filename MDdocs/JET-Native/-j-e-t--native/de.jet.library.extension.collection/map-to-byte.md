//[JET-Native](../../index.md)/[de.jet.library.extension.collection](index.md)/[mapToByte](map-to-byte.md)

# mapToByte

[jvm]\
fun &lt;[T](map-to-byte.md)&gt; [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[T](map-to-byte.md)&gt;.[mapToByte](map-to-byte.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Byte](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)&gt;

Returns the [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)<[T](map-to-byte.md)> as a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) where every element is turned to a [Byte](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html) with the [toByte](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/index.html) function, that is attached to a [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)-Object only containing the individuell content-entry of [this](../../../JET-Native/de.jet.library.extension.collection/index.md)

#### Return

the list of [Byte](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)s

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| T | the type of the original elements |
