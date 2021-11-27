//[JET-Native](../../../index.md)/[de.jet.library.tool.mutable](../index.md)/[Mutable](index.md)

# Mutable

[jvm]\
interface [Mutable](index.md)&lt;[T](index.md)&gt;

This interface is used to mark a class as a [Mutable](index.md) object.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
|  | <T> The type of the object as the value. |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [property](property.md) | [jvm]<br>abstract var [property](property.md): [T](index.md)<br>This value represents the current state of the value of the [Mutable](index.md) as a [T](index.md). |

## Inheritors

| Name |
|---|
| [FlexibleMutable](../-flexible-mutable/index.md) |

## Extensions

| Name | Summary |
|---|---|
| [turnFalse](../../de.jet.library.extension/turn-false.md) | [jvm]<br>fun [Mutable](index.md)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;.[turnFalse](../../de.jet.library.extension/turn-false.md)()<br>This functions sets the value of a [Mutable](index.md)<[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)> to false |
| [turnTrue](../../de.jet.library.extension/turn-true.md) | [jvm]<br>fun [Mutable](index.md)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;.[turnTrue](../../de.jet.library.extension/turn-true.md)()<br>This functions sets the value of a [Mutable](index.md)<[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)> to true |
