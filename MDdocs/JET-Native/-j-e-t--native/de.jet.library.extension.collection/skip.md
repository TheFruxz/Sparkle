//[JET-Native](../../index.md)/[de.jet.library.extension.collection](index.md)/[skip](skip.md)

# skip

[jvm]\
fun [IntRange](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/-int-range/index.html).[skip](skip.md)(vararg ints: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;

Removes every element from this collection that is contained in the [ints](skip.md) collection.

#### Return

the elements of the range minus the elements of the [ints](skip.md) collection

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| ints | the collection of ints to remove |

[jvm]\
infix fun [IntRange](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/-int-range/index.html).[skip](skip.md)(int: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;

Removes the [int](skip.md) from this range.

#### Return

the elements of the range minus the [int](skip.md)

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| int | the int to remove |
