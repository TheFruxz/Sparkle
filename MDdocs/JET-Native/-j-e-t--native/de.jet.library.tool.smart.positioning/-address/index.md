//[JET-Native](../../../index.md)/[de.jet.library.tool.smart.positioning](../index.md)/[Address](index.md)

# Address

[jvm]\
data class [Address](index.md)&lt;[T](index.md)&gt; : [Addressable](../-addressable/index.md)&lt;[T](index.md)&gt; , [Identifiable](../../de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[T](index.md)&gt; 

This class represents an address (inside a path-structure).

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| addressString | the address as string |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Returns the [addressString](address-string.md) |

## Properties

| Name | Summary |
|---|---|
| [address](address.md) | [jvm]<br>open override val [address](address.md): [Address](index.md)&lt;[T](index.md)&gt;<br>The addres of the object as a [Address](index.md) |
| [addressString](address-string.md) | [jvm]<br>open override val [addressString](address-string.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identity](identity.md) | [jvm]<br>open override val [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Represents the identity of the object as a [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html). |
| [identityObject](../../de.jet.library.tool.smart.identification/-identifiable/identity-object.md) | [jvm]<br>open val [identityObject](../../de.jet.library.tool.smart.identification/-identifiable/identity-object.md): [Identity](../../de.jet.library.tool.smart.identification/-identity/index.md)&lt;[T](index.md)&gt;<br>Represents the identity of the object as an [Identity](../../de.jet.library.tool.smart.identification/-identity/index.md) type [T](../../de.jet.library.tool.smart.identification/-identifiable/index.md). |
