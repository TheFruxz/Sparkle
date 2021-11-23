//[JET-Native](../../../index.md)/[de.jet.library.interchange](../index.md)/[InterchangeStructure](index.md)

# InterchangeStructure

[jvm]\
open class [InterchangeStructure](index.md)&lt;[T](index.md) : [InterchangeStructureBranch](../-interchange-structure-branch/index.md)&gt;(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), branches: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](index.md)&gt;) : [InterchangeStructureBranch](../-interchange-structure-branch/index.md), [Identifiable](../../de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[T](index.md)&gt;

## Functions

| Name | Summary |
|---|---|
| [getNearestBranchWithParameters](get-nearest-branch-with-parameters.md) | [jvm]<br>fun [getNearestBranchWithParameters](get-nearest-branch-with-parameters.md)(original: [Address](../../de.jet.library.tool.smart.positioning/-address/index.md)&lt;[T](index.md)&gt;): [Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[T](index.md), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? |
| [getStructureBranches](../-interchange-structure-branch/get-structure-branches.md) | [jvm]<br>fun &lt;[T](../-interchange-structure-branch/get-structure-branches.md) : [InterchangeStructureBranch](../-interchange-structure-branch/index.md)&gt; [getStructureBranches](../-interchange-structure-branch/get-structure-branches.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](../-interchange-structure-branch/get-structure-branches.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [branches](branches.md) | [jvm]<br>open override val [branches](branches.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](index.md)&gt; |
| [branchName](../-interchange-structure-branch/branch-name.md) | [jvm]<br>open val [branchName](../-interchange-structure-branch/branch-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identity](identity.md) | [jvm]<br>open override val [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.library.tool.smart.identification/-identifiable/identity-object.md) | [jvm]<br>open val [identityObject](../../de.jet.library.tool.smart.identification/-identifiable/identity-object.md): [Identity](../../de.jet.library.tool.smart.identification/-identity/index.md)&lt;[T](index.md)&gt; |
| [name](name.md) | [jvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [path](path.md) | [jvm]<br>open override val [path](path.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Inheritors

| Name |
|---|
| [ConsoleInterchange](../../de.jet.library.console.interchange/-console-interchange/index.md) |
