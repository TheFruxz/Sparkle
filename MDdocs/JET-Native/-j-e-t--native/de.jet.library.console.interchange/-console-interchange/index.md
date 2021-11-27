//[JET-Native](../../../index.md)/[de.jet.library.console.interchange](../index.md)/[ConsoleInterchange](index.md)

# ConsoleInterchange

[jvm]\
class [ConsoleInterchange](index.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), branches: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ConsoleStructureBranch](../-console-structure-branch/index.md)&gt;, content: (parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)?) : [InterchangeStructure](../../de.jet.library.interchange/-interchange-structure/index.md)&lt;[ConsoleStructureBranch](../-console-structure-branch/index.md)&gt;

## Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | [jvm]<br>data class [Builder](-builder/index.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), branches: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[ConsoleStructureBranch](../-console-structure-branch/index.md)&gt;, content: (parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)?) : [Producible](../../de.jet.library.tool.smart/-producible/index.md)&lt;[ConsoleInterchange](index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [getNearestBranchWithParameters](index.md#348288064%2FFunctions%2F-1617893574) | [jvm]<br>fun [getNearestBranchWithParameters](index.md#348288064%2FFunctions%2F-1617893574)(original: [Address](../../de.jet.library.tool.smart.positioning/-address/index.md)&lt;[ConsoleStructureBranch](../-console-structure-branch/index.md)&gt;): [Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[ConsoleStructureBranch](../-console-structure-branch/index.md), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? |
| [getStructureBranches](../../de.jet.library.interchange/-interchange-structure-branch/get-structure-branches.md) | [jvm]<br>fun &lt;[T](../../de.jet.library.interchange/-interchange-structure-branch/get-structure-branches.md) : [InterchangeStructureBranch](../../de.jet.library.interchange/-interchange-structure-branch/index.md)&gt; [getStructureBranches](../../de.jet.library.interchange/-interchange-structure-branch/get-structure-branches.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](../../de.jet.library.interchange/-interchange-structure-branch/get-structure-branches.md)&gt; |
| [performInterchange](perform-interchange.md) | [jvm]<br>fun [performInterchange](perform-interchange.md)(input: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Properties

| Name | Summary |
|---|---|
| [branches](branches.md) | [jvm]<br>open override val [branches](branches.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ConsoleStructureBranch](../-console-structure-branch/index.md)&gt; |
| [branchName](../../de.jet.library.interchange/-interchange-structure-branch/branch-name.md) | [jvm]<br>open val [branchName](../../de.jet.library.interchange/-interchange-structure-branch/branch-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [content](content.md) | [jvm]<br>val [content](content.md): (parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)? = null |
| [identity](identity.md) | [jvm]<br>open override val [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Represents the identity of the object as a [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html). |
| [identityObject](../../de.jet.library.tool.smart.identification/-identifiable/identity-object.md) | [jvm]<br>open val [identityObject](../../de.jet.library.tool.smart.identification/-identifiable/identity-object.md): [Identity](../../de.jet.library.tool.smart.identification/-identity/index.md)&lt;[ConsoleStructureBranch](../-console-structure-branch/index.md)&gt;<br>Represents the identity of the object as an [Identity](../../de.jet.library.tool.smart.identification/-identity/index.md) type [T](../../de.jet.library.tool.smart.identification/-identifiable/index.md). |
| [name](../../de.jet.library.interchange/-interchange-structure/name.md) | [jvm]<br>val [name](../../de.jet.library.interchange/-interchange-structure/name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [path](path.md) | [jvm]<br>open override val [path](path.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
