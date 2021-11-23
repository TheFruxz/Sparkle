//[JET-Native](../../../index.md)/[de.jet.library.structure](../index.md)/[DataStructureItem](index.md)

# DataStructureItem

[jvm]\
interface [DataStructureItem](index.md) : [Identifiable](../../de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[DataStructureItem](index.md)&gt; , [Pathed](../../de.jet.library.tool.smart.positioning/-pathed/index.md)&lt;[DataStructureItem](index.md)&gt;

## Functions

| Name | Summary |
|---|---|
| [computeParent](compute-parent.md) | [jvm]<br>open fun &lt;[T](compute-parent.md) : [DataStructureItem](index.md)&gt; [computeParent](compute-parent.md)(holder: [DataStructureHolder](../-data-structure-holder/index.md)&lt;[T](compute-parent.md)&gt;, onlyBase: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [T](compute-parent.md) |

## Properties

| Name | Summary |
|---|---|
| [address](../../de.jet.library.tool.smart.positioning/-pathed/address.md) | [jvm]<br>open override val [address](../../de.jet.library.tool.smart.positioning/-pathed/address.md): [Address](../../de.jet.library.tool.smart.positioning/-address/index.md)&lt;[DataStructureItem](index.md)&gt; |
| [addressString](../../de.jet.library.tool.smart.positioning/-addressable/address-string.md) | [jvm]<br>open val [addressString](../../de.jet.library.tool.smart.positioning/-addressable/address-string.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [existenceAssertion](existence-assertion.md) | [jvm]<br>open val [existenceAssertion](existence-assertion.md): [DataStructureItem](index.md).() -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [identity](../../de.jet.library.tool.smart.identification/-identifiable/identity.md) | [jvm]<br>abstract val [identity](../../de.jet.library.tool.smart.identification/-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.library.tool.smart.identification/-identifiable/identity-object.md) | [jvm]<br>open val [identityObject](../../de.jet.library.tool.smart.identification/-identifiable/identity-object.md): [Identity](../../de.jet.library.tool.smart.identification/-identity/index.md)&lt;[DataStructureItem](index.md)&gt; |
| [path](../../de.jet.library.tool.smart.positioning/-pathed/path.md) | [jvm]<br>abstract val [path](../../de.jet.library.tool.smart.positioning/-pathed/path.md): [Address](../../de.jet.library.tool.smart.positioning/-address/index.md)&lt;[DataStructureItem](index.md)&gt; |
| [pathParts](../../de.jet.library.tool.smart.positioning/-pathed/path-parts.md) | [jvm]<br>open val [pathParts](../../de.jet.library.tool.smart.positioning/-pathed/path-parts.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Inheritors

| Name |
|---|
| [DataStructureDirectory](../-data-structure-directory/index.md) |
