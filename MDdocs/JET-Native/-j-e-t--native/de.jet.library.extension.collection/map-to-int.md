//[JET-Native](../../index.md)/[de.jet.library.extension.collection](index.md)/[mapToInt](map-to-int.md)

# mapToInt

[jvm]\
fun &lt;[T](map-to-int.md)&gt; [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[T](map-to-int.md)&gt;.[mapToInt](map-to-int.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;

Returns the [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)<[T](map-to-int.md)> as a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) where every element is turned to a [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) with the [toInt](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/index.html) function, that is attached to a [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)-Object only containing the individuell content-entry of [this](../../../JET-Native/de.jet.library.extension.collection/index.md)

#### Return

the list of [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)s

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| T | the type of the original elements |
