//[JET-Native](../../../index.md)/[de.jet.library.structure](../index.md)/[DataStructureDirectory](index.md)

# DataStructureDirectory

[jvm]\
interface [DataStructureDirectory](index.md) : [DataStructureItem](../-data-structure-item/index.md)

## Functions

| Name | Summary |
|---|---|
| [computeChildren](compute-children.md) | [jvm]<br>open fun &lt;[T](compute-children.md) : [DataStructureItem](../-data-structure-item/index.md)&gt; [computeChildren](compute-children.md)(holder: [DataStructureHolder](../-data-structure-holder/index.md)&lt;[T](compute-children.md)&gt;, onlyBase: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[DataStructureItem](../-data-structure-item/index.md)&gt; |
| [computeParent](../-data-structure-item/compute-parent.md) | [jvm]<br>open fun &lt;[T](../-data-structure-item/compute-parent.md) : [DataStructureItem](../-data-structure-item/index.md)&gt; [computeParent](../-data-structure-item/compute-parent.md)(holder: [DataStructureHolder](../-data-structure-holder/index.md)&lt;[T](../-data-structure-item/compute-parent.md)&gt;, onlyBase: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [T](../-data-structure-item/compute-parent.md) |

## Properties

| Name | Summary |
|---|---|
| [address](../../de.jet.library.tool.smart.positioning/-pathed/address.md) | [jvm]<br>open override val [address](../../de.jet.library.tool.smart.positioning/-pathed/address.md): [Address](../../de.jet.library.tool.smart.positioning/-address/index.md)&lt;[DataStructureItem](../-data-structure-item/index.md)&gt; |
| [addressString](../../de.jet.library.tool.smart.positioning/-addressable/address-string.md) | [jvm]<br>open val [addressString](../../de.jet.library.tool.smart.positioning/-addressable/address-string.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [existenceAssertion](existence-assertion.md) | [jvm]<br>open override val [existenceAssertion](existence-assertion.md): [DataStructureItem](../-data-structure-item/index.md).() -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [identity](../../de.jet.library.tool.smart.identification/-identifiable/identity.md) | [jvm]<br>abstract val [identity](../../de.jet.library.tool.smart.identification/-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.library.tool.smart.identification/-identifiable/identity-object.md) | [jvm]<br>open val [identityObject](../../de.jet.library.tool.smart.identification/-identifiable/identity-object.md): [Identity](../../de.jet.library.tool.smart.identification/-identity/index.md)&lt;[DataStructureItem](../-data-structure-item/index.md)&gt; |
| [isRootDirectory](is-root-directory.md) | [jvm]<br>open val [isRootDirectory](is-root-directory.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [path](../../de.jet.library.tool.smart.positioning/-pathed/path.md) | [jvm]<br>abstract val [path](../../de.jet.library.tool.smart.positioning/-pathed/path.md): [Address](../../de.jet.library.tool.smart.positioning/-address/index.md)&lt;[DataStructureItem](../-data-structure-item/index.md)&gt; |
| [pathParts](../../de.jet.library.tool.smart.positioning/-pathed/path-parts.md) | [jvm]<br>open val [pathParts](../../de.jet.library.tool.smart.positioning/-pathed/path-parts.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
