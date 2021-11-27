//[JET-Native](../../index.md)/[de.jet.library.extension.collection](index.md)/[mapToDouble](map-to-double.md)

# mapToDouble

[jvm]\
fun &lt;[T](map-to-double.md)&gt; [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[T](map-to-double.md)&gt;.[mapToDouble](map-to-double.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)&gt;

Returns the [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)<[T](map-to-double.md)> as a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) where every element is turned to a [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) with the [toDouble](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/index.html) function, that is attached to a [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)-Object only containing the individuell content-entry of [this](../../../JET-Native/de.jet.library.extension.collection/index.md)

#### Return

the list of [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)s

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| T | the type of the original elements |
