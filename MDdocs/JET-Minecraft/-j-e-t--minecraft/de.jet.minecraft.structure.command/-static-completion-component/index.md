//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.structure.command](../index.md)/[StaticCompletionComponent](index.md)

# StaticCompletionComponent

[jvm]\
data class [StaticCompletionComponent](index.md)(completion: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, displayRequirement: (executor: CommandSender, parameters: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, completion: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, accessApproval: [Approval](../../de.jet.minecraft.tool.permission/-approval/index.md)?) : [CompletionComponent](../-completion-component/index.md)

## Functions

| Name | Summary |
|---|---|
| [completion](completion.md) | [jvm]<br>open override fun [completion](completion.md)(): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Properties

| Name | Summary |
|---|---|
| [accessApproval](access-approval.md) | [jvm]<br>open override val [accessApproval](access-approval.md): [Approval](../../de.jet.minecraft.tool.permission/-approval/index.md)? = null |
| [completion](completion.md) | [jvm]<br>val [completion](completion.md): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [displayRequirement](display-requirement.md) | [jvm]<br>open override val [displayRequirement](display-requirement.md): (executor: CommandSender, parameters: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, completion: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)? = null |
| [inputExpressionCheck](input-expression-check.md) | [jvm]<br>open override val [inputExpressionCheck](input-expression-check.md): ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [label](label.md) | [jvm]<br>open override val [label](label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
