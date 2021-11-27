//[JET-Native](../../index.md)/[de.jet.library.extension.collection](index.md)/[withMap](with-map.md)

# withMap

[jvm]\
fun &lt;[I](with-map.md), [O](with-map.md), [T](with-map.md) : [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[I](with-map.md)&gt;&gt; [T](with-map.md).[withMap](with-map.md)(action: [I](with-map.md).() -&gt; [O](with-map.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[O](with-map.md)&gt;

Maps a [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) to a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) using the [map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/index.html) function, which is the same as [map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/index.html) a [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) with the [with](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/index.html) function inside.

#### Return

the mapped [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| action | the action in the 'this' perspective |

[jvm]\
fun &lt;[I](with-map.md), [O](with-map.md)&gt; [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[I](with-map.md)&gt;.[withMap](with-map.md)(action: [I](with-map.md).() -&gt; [O](with-map.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[O](with-map.md)&gt;

Maps a [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html) to a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) using the [map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/index.html) function, which is the same as [map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/index.html) a [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html) with the [with](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/index.html) function inside.

#### Return

the mapped [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| action | the action in the 'this' perspective |
