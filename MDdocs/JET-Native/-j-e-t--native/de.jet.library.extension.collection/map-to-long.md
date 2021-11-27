//[JET-Native](../../index.md)/[de.jet.library.extension.collection](index.md)/[mapToLong](map-to-long.md)

# mapToLong

[jvm]\
fun &lt;[T](map-to-long.md)&gt; [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[T](map-to-long.md)&gt;.[mapToLong](map-to-long.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)&gt;

Returns the [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)<[T](map-to-long.md)> as a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) where every element is turned to a [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) with the [toLong](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/index.html) function, that is attached to a [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)-Object only containing the individuell content-entry of [this](../../../JET-Native/de.jet.library.extension.collection/index.md)

#### Return

the list of [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)s

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| T | the type of the original elements |
