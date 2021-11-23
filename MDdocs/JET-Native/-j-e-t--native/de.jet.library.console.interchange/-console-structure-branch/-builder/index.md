//[JET-Native](../../../../index.md)/[de.jet.library.console.interchange](../../index.md)/[ConsoleStructureBranch](../index.md)/[Builder](index.md)

# Builder

[jvm]\
data class [Builder](index.md)(branchName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), branches: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[ConsoleStructureBranch](../index.md)&gt;, content: (parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)?) : [Producible](../../../de.jet.library.tool.smart/-producible/index.md)&lt;[ConsoleStructureBranch](../index.md)&gt;

## Functions

| Name | Summary |
|---|---|
| [branch](branch.md) | [jvm]<br>fun [branch](branch.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), process: [ConsoleStructureBranch.Builder](index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = { }): [ConsoleStructureBranch.Builder](index.md) |
| [content](content.md) | [jvm]<br>fun [content](content.md)(content: (parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)?): [ConsoleStructureBranch.Builder](index.md) |
| [plus](plus.md) | [jvm]<br>operator fun [plus](plus.md)(branch: [ConsoleStructureBranch](../index.md)): [ConsoleStructureBranch.Builder](index.md) |
| [produce](produce.md) | [jvm]<br>open override fun [produce](produce.md)(): [ConsoleStructureBranch](../index.md) |

## Properties

| Name | Summary |
|---|---|
| [branches](branches.md) | [jvm]<br>var [branches](branches.md): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[ConsoleStructureBranch](../index.md)&gt; |
| [branchName](branch-name.md) | [jvm]<br>var [branchName](branch-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [content](content.md) | [jvm]<br>var [content](content.md): (parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)? = null |
| [path](path.md) | [jvm]<br>var [path](path.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
