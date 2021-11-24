//[JET-Native](../../index.md)/[de.jet.library.extension.java](index.md)/[getOrDefault](get-or-default.md)

# getOrDefault

[jvm]\
fun &lt;[T](get-or-default.md)&gt; [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)&lt;[T](get-or-default.md)&gt;.[getOrDefault](get-or-default.md)(default: [T](get-or-default.md)): [T](get-or-default.md)

Returns the value of the [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) or if it throws exceptions, it returns the [default](get-or-default.md) instead.

#### Return

the value or [default](get-or-default.md)

#### Author

Fruxz

#### Since

1.0

[jvm]\
fun &lt;[T](get-or-default.md)&gt; [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)&lt;[T](get-or-default.md)&gt;.[getOrDefault](get-or-default.md)(default: [T](get-or-default.md)): [T](get-or-default.md)

Returns the value of the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) or the [default](get-or-default.md) parameter if the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) throws a [NoSuchElementException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-no-such-element-exception/index.html)

#### Return

the value of the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) or [default](get-or-default.md)

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| default | the default value that gets returned if the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) throws the [NoSuchElementException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-no-such-element-exception/index.html) |

[jvm]\

@[JvmName](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-name/index.html)(name = "nullableGetOrDefaultT")

fun &lt;[T](get-or-default.md)&gt; [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)&lt;[T](get-or-default.md)&gt;?.[getOrDefault](get-or-default.md)(default: [T](get-or-default.md)): [T](get-or-default.md)

Returns the value of the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) or the [default](get-or-default.md) parameter if the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) is null or if the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) throws a [NoSuchElementException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-no-such-element-exception/index.html).

#### Return

the value of the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) or [default](get-or-default.md)

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| default | the default value that gets returned if null or if the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) throws the [NoSuchElementException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-no-such-element-exception/index.html) |
