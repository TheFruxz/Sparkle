//[JET-Native](../../../../index.md)/[de.jet.library.console.interchange](../../index.md)/[ConsoleInterchange](../index.md)/[Builder](index.md)

# Builder

[jvm]\
data class [Builder](index.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), branches: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[ConsoleStructureBranch](../../-console-structure-branch/index.md)&gt;, content: (parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)?) : [Producible](../../../de.jet.library.tool.smart/-producible/index.md)&lt;[ConsoleInterchange](../index.md)&gt;

## Functions

| Name | Summary |
|---|---|
| [branch](branch.md) | [jvm]<br>fun [branch](branch.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), process: [ConsoleStructureBranch.Builder](../../-console-structure-branch/-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = { }): [ConsoleInterchange.Builder](index.md) |
| [content](content.md) | [jvm]<br>fun [content](content.md)(content: (parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)?): [ConsoleInterchange.Builder](index.md) |
| [plus](plus.md) | [jvm]<br>operator fun [plus](plus.md)(branch: [ConsoleStructureBranch](../../-console-structure-branch/index.md)): [ConsoleInterchange.Builder](index.md) |
| [produce](produce.md) | [jvm]<br>open override fun [produce](produce.md)(): [ConsoleInterchange](../index.md) |

## Properties

| Name | Summary |
|---|---|
| [branches](branches.md) | [jvm]<br>var [branches](branches.md): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[ConsoleStructureBranch](../../-console-structure-branch/index.md)&gt; |
| [content](content.md) | [jvm]<br>var [content](content.md): (parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)? = null |
| [identity](identity.md) | [jvm]<br>var [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [path](path.md) | [jvm]<br>var [path](path.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
