//[JET-Native](../../index.md)/[de.jet.library.tool.smart.positioning](index.md)

# Package de.jet.library.tool.smart.positioning

## Types

| Name | Summary |
|---|---|
| [Address](-address/index.md) | [jvm]<br>data class [Address](-address/index.md)&lt;[T](-address/index.md)&gt; : [Addressable](-addressable/index.md)&lt;[T](-address/index.md)&gt; , [Identifiable](../de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[T](-address/index.md)&gt; <br>This class represents an address (inside a path-structure). |
| [Addressable](-addressable/index.md) | [jvm]<br>interface [Addressable](-addressable/index.md)&lt;[T](-addressable/index.md)&gt;<br>Interface for objects that can be addressed by a [Address](-address/index.md). |
| [Addressed](-addressed/index.md) | [jvm]<br>data class [Addressed](-addressed/index.md)&lt;[T](-addressed/index.md)&gt;(address: [Address](-address/index.md)&lt;[T](-addressed/index.md)&gt;, value: [T](-addressed/index.md))<br>A positioned element with an address. Kinda the same as a withIndex() in a list. |
| [Pathed](-pathed/index.md) | [jvm]<br>interface [Pathed](-pathed/index.md)&lt;[T](-pathed/index.md)&gt; : [Addressable](-addressable/index.md)&lt;[T](-pathed/index.md)&gt; <br>This interface represents an object, which has a specific path inside a pathed structure. |
