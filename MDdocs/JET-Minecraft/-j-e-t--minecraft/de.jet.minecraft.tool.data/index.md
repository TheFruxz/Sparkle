//[JET-Minecraft](../../index.md)/[de.jet.minecraft.tool.data](index.md)

# Package de.jet.minecraft.tool.data

## Types

| Name | Summary |
|---|---|
| [DataTransformer](-data-transformer/index.md) | [jvm]<br>data class [DataTransformer](-data-transformer/index.md)&lt;[SHELL](-data-transformer/index.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [CORE](-data-transformer/index.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;(toCore: [SHELL](-data-transformer/index.md).() -&gt; [CORE](-data-transformer/index.md), toShell: [CORE](-data-transformer/index.md).() -&gt; [SHELL](-data-transformer/index.md)) |
| [JetFile](-jet-file/index.md) | [jvm]<br>interface [JetFile](-jet-file/index.md) |
| [JetJsonFile](-jet-json-file/index.md) | [jvm]<br>interface [JetJsonFile](-jet-json-file/index.md) : [JetFile](-jet-file/index.md) |
| [JetPath](-jet-path/index.md) | [jvm]<br>data class [JetPath](-jet-path/index.md)(base: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Identifiable](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[JetPath](-jet-path/index.md)&gt; <br>The path used inside yaml files |
| [JetYamlFile](-jet-yaml-file/index.md) | [jvm]<br>interface [JetYamlFile](-jet-yaml-file/index.md) : [JetFile](-jet-file/index.md) |
| [Preference](-preference/index.md) | [jvm]<br>data class [Preference](-preference/index.md)&lt;[SHELL](-preference/index.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;(file: [JetFile](-jet-file/index.md), path: [Identifiable](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[JetPath](-jet-path/index.md)&gt;, default: [SHELL](-preference/index.md), useCache: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), readAndWrite: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), transformer: [DataTransformer](-data-transformer/index.md)&lt;[SHELL](-preference/index.md), out [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;, async: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), forceUseOfTasks: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), initTriggerSetup: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), inputType: [Preference.InputType](-preference/-input-type/index.md)?) : [Identifiable](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[Preference](-preference/index.md)&lt;[SHELL](-preference/index.md)&gt;&gt; |
