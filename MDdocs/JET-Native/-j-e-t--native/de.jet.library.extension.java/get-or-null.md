//[JET-Native](../../index.md)/[de.jet.library.extension.java](index.md)/[getOrNull](get-or-null.md)

# getOrNull

[jvm]\
fun &lt;[T](get-or-null.md)&gt; [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)&lt;[T](get-or-null.md)&gt;.[getOrNull](get-or-null.md)(): [T](get-or-null.md)?

Returns the value of the [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) or if it throws exceptions, it returns null instead.

#### Return

the value or null

#### Author

Fruxz

#### Since

1.0

[jvm]\
fun &lt;[T](get-or-null.md)&gt; [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)&lt;[T](get-or-null.md)&gt;.[getOrNull](get-or-null.md)(): [T](get-or-null.md)?

Returns the value of the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) or null if the optional throws a [NoSuchElementException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-no-such-element-exception/index.html)

#### Return

the value of the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) or null

#### Author

Fruxz

#### Since

1.0

[jvm]\

@[JvmName](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-name/index.html)(name = "nullableGetOrNullT")

fun &lt;[T](get-or-null.md)&gt; [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)&lt;[T](get-or-null.md)&gt;?.[getOrNull](get-or-null.md)(): [T](get-or-null.md)?

Returns the value of the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) or null if the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) is null or if the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) throws a [NoSuchElementException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-no-such-element-exception/index.html).

#### Return

the value of the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) or null

#### Author

Fruxz

#### Since

1.0
