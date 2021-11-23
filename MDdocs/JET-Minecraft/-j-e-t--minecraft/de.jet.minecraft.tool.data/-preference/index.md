//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.tool.data](../index.md)/[Preference](index.md)

# Preference

[jvm]\
data class [Preference](index.md)&lt;[SHELL](index.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;(file: [JetFile](../-jet-file/index.md), path: [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[JetPath](../-jet-path/index.md)&gt;, default: [SHELL](index.md), useCache: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), readAndWrite: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), transformer: [DataTransformer](../-data-transformer/index.md)&lt;[SHELL](index.md), out [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;, async: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), forceUseOfTasks: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), initTriggerSetup: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), inputType: [Preference.InputType](-input-type/index.md)?) : [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[Preference](index.md)&lt;[SHELL](index.md)&gt;&gt;

## Parameters

jvm

| | |
|---|---|
| inputType | null if automatic |

## Constructors

| | |
|---|---|
| [Preference](-preference.md) | [jvm]<br>fun &lt;[SHELL](index.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [Preference](-preference.md)(file: [JetFile](../-jet-file/index.md), path: [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[JetPath](../-jet-path/index.md)&gt;, default: [SHELL](index.md), useCache: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, readAndWrite: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, transformer: [DataTransformer](../-data-transformer/index.md)&lt;[SHELL](index.md), out [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; = DataTransformer.empty(), async: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, forceUseOfTasks: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, initTriggerSetup: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, inputType: [Preference.InputType](-input-type/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [InputType](-input-type/index.md) | [jvm]<br>enum [InputType](-input-type/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Preference.InputType](-input-type/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [editContent](edit-content.md) | [jvm]<br>fun [editContent](edit-content.md)(process: [SHELL](index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [insertFromString](insert-from-string.md) | [jvm]<br>fun [insertFromString](insert-from-string.md)(string: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [reset](reset.md) | [jvm]<br>fun [reset](reset.md)() |
| [transformer](transformer.md) | [jvm]<br>fun &lt;[CORE](transformer.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [transformer](transformer.md)(transformer: [DataTransformer](../-data-transformer/index.md)&lt;[SHELL](index.md), [CORE](transformer.md)&gt;): [Preference](index.md)&lt;[SHELL](index.md)&gt;<br>fun &lt;[CORE](transformer.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [transformer](transformer.md)(toCore: [SHELL](index.md).() -&gt; [CORE](transformer.md), toShell: [CORE](transformer.md).() -&gt; [SHELL](index.md)): [Preference](index.md)&lt;[SHELL](index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [async](async.md) | [jvm]<br>var [async](async.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [content](content.md) | [jvm]<br>var [content](content.md): [SHELL](index.md) |
| [default](default.md) | [jvm]<br>val [default](default.md): [SHELL](index.md) |
| [file](file.md) | [jvm]<br>val [file](file.md): [JetFile](../-jet-file/index.md) |
| [forceUseOfTasks](force-use-of-tasks.md) | [jvm]<br>var [forceUseOfTasks](force-use-of-tasks.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [identity](identity.md) | [jvm]<br>open override val [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Preference](index.md)&lt;[SHELL](index.md)&gt;&gt; |
| [initTriggerSetup](init-trigger-setup.md) | [jvm]<br>var [initTriggerSetup](init-trigger-setup.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true |
| [inputType](input-type.md) | [jvm]<br>var [inputType](input-type.md): [Preference.InputType](-input-type/index.md)? = null |
| [isSavedInFile](is-saved-in-file.md) | [jvm]<br>val [isSavedInFile](is-saved-in-file.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [path](path.md) | [jvm]<br>val [path](path.md): [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[JetPath](../-jet-path/index.md)&gt; |
| [readAndWrite](read-and-write.md) | [jvm]<br>val [readAndWrite](read-and-write.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true |
| [transformer](transformer.md) | [jvm]<br>var [transformer](transformer.md): [DataTransformer](../-data-transformer/index.md)&lt;[SHELL](index.md), out [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; |
| [useCache](use-cache.md) | [jvm]<br>val [useCache](use-cache.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
