//[JET-Native](../../../index.md)/[de.jet.library.console.interchange](../index.md)/[ConsoleStructureBranch](index.md)

# ConsoleStructureBranch

[jvm]\
class [ConsoleStructureBranch](index.md)(branchName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), branches: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ConsoleStructureBranch](index.md)&gt;, content: (parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)?) : [InterchangeStructureBranch](../../de.jet.library.interchange/-interchange-structure-branch/index.md)

## Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | [jvm]<br>data class [Builder](-builder/index.md)(branchName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), branches: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[ConsoleStructureBranch](index.md)&gt;, content: (parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)?) : [Producible](../../de.jet.library.tool.smart/-producible/index.md)&lt;[ConsoleStructureBranch](index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [getStructureBranches](../../de.jet.library.interchange/-interchange-structure-branch/get-structure-branches.md) | [jvm]<br>fun &lt;[T](../../de.jet.library.interchange/-interchange-structure-branch/get-structure-branches.md) : [InterchangeStructureBranch](../../de.jet.library.interchange/-interchange-structure-branch/index.md)&gt; [getStructureBranches](../../de.jet.library.interchange/-interchange-structure-branch/get-structure-branches.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](../../de.jet.library.interchange/-interchange-structure-branch/get-structure-branches.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [branches](branches.md) | [jvm]<br>open override val [branches](branches.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ConsoleStructureBranch](index.md)&gt; |
| [branchName](branch-name.md) | [jvm]<br>open override val [branchName](branch-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [content](content.md) | [jvm]<br>val [content](content.md): (parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)? = null |
| [path](path.md) | [jvm]<br>open override val [path](path.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
