//[JET-Native](../../../index.md)/[de.jet.library.tool.smart.identification](../index.md)/[Identity](index.md)

# Identity

[jvm]\
data class [Identity](index.md)&lt;[T](index.md)&gt;(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Identifiable](../-identifiable/index.md)&lt;[T](index.md)&gt; 

This data class represents an identity, which should be (in the most cases) unique inside the [T](index.md) environment.

This data class helps to easily identify and compare objects.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| T | The owner type of the identity. |
| identity | The identity itself. |

## Constructors

| | |
|---|---|
| [Identity](-identity.md) | [jvm]<br>fun [Identity](-identity.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Functions

| Name | Summary |
|---|---|
| [change](change.md) | [jvm]<br>fun &lt;[O](change.md)&gt; [change](change.md)(): [Identity](index.md)&lt;[O](change.md)&gt;<br>This function returns this but changes the [T](index.md) type from the [Identity](index.md) to a [O](change.md) type, without changing the [identity](identity.md) itself. |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Returns the [identity](identity.md) (in the form of a [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [identity](identity.md) | [jvm]<br>open override val [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../-identifiable/identity-object.md) | [jvm]<br>open val [identityObject](../-identifiable/identity-object.md): [Identity](index.md)&lt;[T](index.md)&gt;<br>Represents the identity of the object as an [Identity](index.md) type [T](../-identifiable/index.md). |
