//[JET-Native](../../index.md)/[de.jet.library.extension.collection](index.md)/[withForEach](with-for-each.md)

# withForEach

[jvm]\
fun &lt;[O](with-for-each.md), [T](with-for-each.md) : [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[O](with-for-each.md)&gt;&gt; [T](with-for-each.md).[withForEach](with-for-each.md)(action: [O](with-for-each.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))

[forEach](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/index.html) a [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) but instead of a 'it' lambda it uses a 'this' lambda, which is the same as [forEach](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/index.html) a [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) with the [with](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/index.html) function inside.

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
fun &lt;[O](with-for-each.md)&gt; [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[O](with-for-each.md)&gt;.[withForEach](with-for-each.md)(action: [O](with-for-each.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))

[forEach](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/index.html) a [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html) but instead of a 'it' lambda it uses a 'this' lambda, which is the same as [forEach](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/index.html) a [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html) with the [with](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/index.html) function inside.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| action | the action in the 'this' perspective |
