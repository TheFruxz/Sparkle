//[JET-Native](../../../index.md)/[de.jet.library.console](../index.md)/[ArgumentSyntax](index.md)

# ArgumentSyntax

[jvm]\
class [ArgumentSyntax](index.md)(syntaxVariables: [ArgumentSyntax.ConsoleSyntaxVariable](-console-syntax-variable/index.md))

## Types

| Name | Summary |
|---|---|
| [ActivatedConsoleSyntax](-activated-console-syntax/index.md) | [jvm]<br>class [ActivatedConsoleSyntax](-activated-console-syntax/index.md) |
| [ConsoleSyntaxVariable](-console-syntax-variable/index.md) | [jvm]<br>data class [ConsoleSyntaxVariable](-console-syntax-variable/index.md)(variableName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), optional: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), contentType: [ArgumentSyntax.ContentType](-content-type/index.md), check: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : [Identifiable](../../de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[ArgumentSyntax.ConsoleSyntaxVariable](-console-syntax-variable/index.md)&gt; |
| [ContentType](-content-type/index.md) | [jvm]<br>enum [ContentType](-content-type/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[ArgumentSyntax.ContentType](-content-type/index.md)&gt; |
| [SyntaxCheck](-syntax-check/index.md) | [jvm]<br>interface [SyntaxCheck](-syntax-check/index.md) |

## Functions

| Name | Summary |
|---|---|
| [buildUsedVariables](build-used-variables.md) | [jvm]<br>fun [buildUsedVariables](build-used-variables.md)(input: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [checkInputContent](check-input-content.md) | [jvm]<br>fun [checkInputContent](check-input-content.md)(input: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [checkInputContentWithFeedback](check-input-content-with-feedback.md) | [jvm]<br>fun [checkInputContentWithFeedback](check-input-content-with-feedback.md)(input: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [ArgumentSyntax.SyntaxCheck](-syntax-check/index.md) |
| [runWithSyntaxOrNotify](run-with-syntax-or-notify.md) | [jvm]<br>fun [runWithSyntaxOrNotify](run-with-syntax-or-notify.md)(input: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, code: [ArgumentSyntax.ActivatedConsoleSyntax](-activated-console-syntax/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
