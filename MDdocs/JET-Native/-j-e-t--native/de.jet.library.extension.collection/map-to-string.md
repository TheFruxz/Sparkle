//[JET-Native](../../index.md)/[de.jet.library.extension.collection](index.md)/[mapToString](map-to-string.md)

# mapToString

[jvm]\
fun &lt;[T](map-to-string.md)&gt; [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[T](map-to-string.md)&gt;.[mapToString](map-to-string.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;

Returns the [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)<[T](map-to-string.md)> as a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) where every element is turned to a [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html). The backend is converting every element using the internal [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/index.html) method.

#### Return

the list of [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)s

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| T | the type of the original elements |
