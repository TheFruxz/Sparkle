//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.structure.command](../index.md)/[CompletionComponent](index.md)

# CompletionComponent

[jvm]\
interface [CompletionComponent](index.md)

## Functions

| Name | Summary |
|---|---|
| [completion](completion.md) | [jvm]<br>abstract fun [completion](completion.md)(): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Properties

| Name | Summary |
|---|---|
| [accessApproval](access-approval.md) | [jvm]<br>abstract val [accessApproval](access-approval.md): [Approval](../../de.jet.minecraft.tool.permission/-approval/index.md)? |
| [displayRequirement](display-requirement.md) | [jvm]<br>abstract val [displayRequirement](display-requirement.md): (executor: CommandSender, parameters: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, completion: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)? |
| [inputExpressionCheck](input-expression-check.md) | [jvm]<br>abstract val [inputExpressionCheck](input-expression-check.md): (input: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ignoreCase: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [label](label.md) | [jvm]<br>abstract val [label](label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Inheritors

| Name |
|---|
| [StaticCompletionComponent](../-static-completion-component/index.md) |
| [VariableCompletionComponent](../-variable-completion-component/index.md) |
