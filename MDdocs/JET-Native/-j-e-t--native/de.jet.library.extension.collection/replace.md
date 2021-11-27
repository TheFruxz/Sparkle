//[JET-Native](../../index.md)/[de.jet.library.extension.collection](index.md)/[replace](replace.md)

# replace

[jvm]\
fun [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html).[replace](replace.md)(map: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;out [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?&gt;): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Replaces all occurrences of the given [Map.keys](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/keys.html) with the given [Map.values](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/values.html) in this string.

#### Return

the string with all occurrences of the given [Map.keys](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/keys.html) replaced by the given [Map.values](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/values.html)

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| map | the pairs of keys and values to replace |

[jvm]\
fun [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html).[replace](replace.md)(vararg pairs: [Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?&gt;): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

fun [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html).[replace](replace.md)(pairs: [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?&gt;&gt;): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Replaces all occurrences of the given [Pair.first](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/first.html) with the given [Pair.second](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/second.html) in this string.

#### Return

the string with all occurrences of the given [Pair.first](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/first.html) replaced by the given [Pair.second](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/second.html)

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| pairs | the pairs of keys and values to replace |
