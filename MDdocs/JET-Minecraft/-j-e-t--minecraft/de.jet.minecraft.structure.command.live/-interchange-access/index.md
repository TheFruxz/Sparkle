//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.structure.command.live](../index.md)/[InterchangeAccess](index.md)

# InterchangeAccess

[jvm]\
data class [InterchangeAccess](index.md)(vendor: [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[App](../../de.jet.minecraft.structure.app/-app/index.md)&gt;, executorType: [InterchangeExecutorType](../../de.jet.minecraft.structure.command/-interchange-executor-type/index.md), executor: CommandSender, interchange: [Interchange](../../de.jet.minecraft.structure.command/-interchange/index.md), label: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) : [Logging](../../de.jet.minecraft.tool.smart/-logging/index.md)

## Types

| Name | Summary |
|---|---|
| [ValidationData](-validation-data/index.md) | [jvm]<br>data class [ValidationData](-validation-data/index.md)(access: [InterchangeAccess](index.md)) |

## Functions

| Name | Summary |
|---|---|
| [inputLength](input-length.md) | [jvm]<br>fun [inputLength](input-length.md)(checkIf: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [inputParameter](input-parameter.md) | [jvm]<br>fun [inputParameter](input-parameter.md)(slot: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [interchangeLog](interchange-log.md) | [jvm]<br>fun [interchangeLog](interchange-log.md)(level: [Level](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Level.html) = Level.INFO, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [checkParameter](check-parameter.md) | [jvm]<br>val [checkParameter](check-parameter.md): [InterchangeAccess.ValidationData](-validation-data/index.md) |
| [executor](executor.md) | [jvm]<br>val [executor](executor.md): CommandSender |
| [executorType](executor-type.md) | [jvm]<br>val [executorType](executor-type.md): [InterchangeExecutorType](../../de.jet.minecraft.structure.command/-interchange-executor-type/index.md) |
| [inputLength](input-length.md) | [jvm]<br>val [inputLength](input-length.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [interchange](interchange.md) | [jvm]<br>val [interchange](interchange.md): [Interchange](../../de.jet.minecraft.structure.command/-interchange/index.md) |
| [label](label.md) | [jvm]<br>val [label](label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [parameters](parameters.md) | [jvm]<br>val [parameters](parameters.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [sectionLabel](section-label.md) | [jvm]<br>open override val [sectionLabel](section-label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md) | [jvm]<br>open val [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md): [Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [vendor](vendor.md) | [jvm]<br>open override val [vendor](vendor.md): [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[App](../../de.jet.minecraft.structure.app/-app/index.md)&gt; |
