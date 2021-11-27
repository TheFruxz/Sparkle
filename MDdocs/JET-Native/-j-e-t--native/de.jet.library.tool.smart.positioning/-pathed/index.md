//[JET-Native](../../../index.md)/[de.jet.library.tool.smart.positioning](../index.md)/[Pathed](index.md)

# Pathed

[jvm]\
interface [Pathed](index.md)&lt;[T](index.md)&gt; : [Addressable](../-addressable/index.md)&lt;[T](index.md)&gt; 

This interface represents an object, which has a specific path inside a pathed structure.

#### Author

Fruxz

#### Since

1.0

## Properties

| Name | Summary |
|---|---|
| [address](address.md) | [jvm]<br>open override val [address](address.md): [Address](../-address/index.md)&lt;[T](index.md)&gt;<br>This is the path of this object, which represents the address of the object inside the pathed structure. |
| [addressString](../-addressable/address-string.md) | [jvm]<br>open val [addressString](../-addressable/address-string.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The address of the object as a [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html). |
| [path](path.md) | [jvm]<br>abstract val [path](path.md): [Address](../-address/index.md)&lt;[T](index.md)&gt;<br>This is the path of this object, which represents the address of the object inside the pathed structure. |
| [pathParts](path-parts.md) | [jvm]<br>open val [pathParts](path-parts.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>This value represents the different parts of the paths of this object. |

## Inheritors

| Name |
|---|
| [DataStructureItem](../../de.jet.library.structure/-data-structure-item/index.md) |
