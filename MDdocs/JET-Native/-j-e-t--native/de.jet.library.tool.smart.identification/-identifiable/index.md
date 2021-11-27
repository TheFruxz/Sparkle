//[JET-Native](../../../index.md)/[de.jet.library.tool.smart.identification](../index.md)/[Identifiable](index.md)

# Identifiable

[jvm]\
interface [Identifiable](index.md)&lt;[T](index.md)&gt;

This interface marks every object that can be identified using the [Identity](../-identity/index.md) object and an identity [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html).

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| T | the type of the object that is identifiable |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [identity](identity.md) | [jvm]<br>abstract val [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Represents the identity of the object as a [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html). |
| [identityObject](identity-object.md) | [jvm]<br>open val [identityObject](identity-object.md): [Identity](../-identity/index.md)&lt;[T](index.md)&gt;<br>Represents the identity of the object as an [Identity](../-identity/index.md) type [T](index.md). |

## Inheritors

| Name |
|---|
| [ConsoleSyntaxVariable](../../de.jet.library.console/-argument-syntax/-console-syntax-variable/index.md) |
| [InterchangeStructure](../../de.jet.library.interchange/-interchange-structure/index.md) |
| [DataStructureItem](../../de.jet.library.structure/-data-structure-item/index.md) |
| [Identity](../-identity/index.md) |
| [Address](../../de.jet.library.tool.smart.positioning/-address/index.md) |
