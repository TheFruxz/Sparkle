//[JET-Native](../../../../index.md)/[de.jet.library.console](../../index.md)/[ArgumentSyntax](../index.md)/[ConsoleSyntaxVariable](index.md)

# ConsoleSyntaxVariable

[jvm]\
data class [ConsoleSyntaxVariable](index.md)(variableName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), optional: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), contentType: [ArgumentSyntax.ContentType](../-content-type/index.md), check: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : [Identifiable](../../../de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[ArgumentSyntax.ConsoleSyntaxVariable](index.md)&gt;

## Functions

| Name | Summary |
|---|---|
| [checkVariableContent](check-variable-content.md) | [jvm]<br>fun [checkVariableContent](check-variable-content.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ArgumentSyntax.SyntaxCheck](../-syntax-check/index.md) |

## Properties

| Name | Summary |
|---|---|
| [check](check.md) | [jvm]<br>val [check](check.md): ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [contentType](content-type.md) | [jvm]<br>val [contentType](content-type.md): [ArgumentSyntax.ContentType](../-content-type/index.md) |
| [identity](identity.md) | [jvm]<br>open override val [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Represents the identity of the object as a [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html). |
| [identityObject](../../../de.jet.library.tool.smart.identification/-identifiable/identity-object.md) | [jvm]<br>open val [identityObject](../../../de.jet.library.tool.smart.identification/-identifiable/identity-object.md): [Identity](../../../de.jet.library.tool.smart.identification/-identity/index.md)&lt;[ArgumentSyntax.ConsoleSyntaxVariable](index.md)&gt;<br>Represents the identity of the object as an [Identity](../../../de.jet.library.tool.smart.identification/-identity/index.md) type [T](../../../de.jet.library.tool.smart.identification/-identifiable/index.md). |
| [optional](optional.md) | [jvm]<br>val [optional](optional.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [variableName](variable-name.md) | [jvm]<br>val [variableName](variable-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
