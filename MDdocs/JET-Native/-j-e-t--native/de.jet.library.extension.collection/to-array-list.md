//[JET-Native](../../index.md)/[de.jet.library.extension.collection](index.md)/[toArrayList](to-array-list.md)

# toArrayList

[jvm]\
fun &lt;[T](to-array-list.md), [C](to-array-list.md) : [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[T](to-array-list.md)&gt;&gt; [C](to-array-list.md).[toArrayList](to-array-list.md)(): [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)&lt;[T](to-array-list.md)&gt;

# C.toArrayList()

##  Info

This function creates a new [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)<[T](to-array-list.md)> object, which contains the elements of the Collection<[T](to-array-list.md)>[C](to-array-list.md) (**this**-object)

##  Use

This function can be easily used to get a set or list to an ArrayList, but you can also use ArrayList(yourList), that is the same, what is also used in this function!

##  Base

This function is globally available through the whole JET-Native API and beyond!

This function creates a new [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html) object of containing-type [T](to-array-list.md), obtained from the Collection [C](to-array-list.md), which has also the containing-type [T](to-array-list.md). This [toArrayList](to-array-list.md) function is attached as an extension function to the [C](to-array-list.md) object, which is based on all [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) type [T](to-array-list.md)!

#### Author

Fruxz (@TheFruxz)

#### Since

1.0-BETA-5 (preview)

## Parameters

jvm

| | |
|---|---|
| T | the inner containing data type |
| C | the actual base collection, which will be transformed |

[jvm]\
fun &lt;[T](to-array-list.md)&gt; [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;out [T](to-array-list.md)&gt;.[toArrayList](to-array-list.md)(): [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)&lt;[T](to-array-list.md)&gt;

# Array&lt;out T&gt;.toArrayList()

##  Info

This function creates a new [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)<[T](to-array-list.md)> object, which contains the elements of the Array[T](to-array-list.md)> (**this**-object)

##  Use

This function can be easily used tzo get an array to an ArrayList, but you can also use ArrayList(yourArray.toList()), that is the same, what is also used in this function!

##  Base

This function is globally available through the whole JET-Native API and beyond!

This function creates a new [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html) object of containing-type [T](to-array-list.md), obtained from the Array[T](to-array-list.md)>. This [toArrayList](to-array-list.md) function is attached as an extension function to the Array[T](to-array-list.md)> object.

#### Author

Fruxz (@TheFruxz)

#### Since

1.0-BETA-5 (preview)

## Parameters

jvm

| | |
|---|---|
| T | the inner containing data type of both, input [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html) and output [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html) |
