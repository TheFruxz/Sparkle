//[JET-Native](../../index.md)/[de.jet.library.extension.collection](index.md)/[mapToBoolean](map-to-boolean.md)

# mapToBoolean

[jvm]\
fun &lt;[T](map-to-boolean.md)&gt; [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[T](map-to-boolean.md)&gt;.[mapToBoolean](map-to-boolean.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;

Returns the [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)<[T](map-to-boolean.md)> as a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) where every element is turned to a [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) with the [toBoolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/index.html) function, that is attached to a [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)-Object only containing the individuell content-entry of [this](../../../JET-Native/de.jet.library.extension.collection/index.md)

#### Return

the list of [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)s

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| T | the type of the original elements |
