//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.structure.service](../index.md)/[Service](index.md)

# Service

[jvm]\
interface [Service](index.md) : [VendorsIdentifiable](../../de.jet.minecraft.tool.smart/-vendors-identifiable/index.md)&lt;[Service](index.md)&gt; , [Logging](../../de.jet.minecraft.tool.smart/-logging/index.md)

## Functions

| Name | Summary |
|---|---|
| [boot](boot.md) | [jvm]<br>open fun [boot](boot.md)() |
| [shutdown](shutdown.md) | [jvm]<br>open fun [shutdown](shutdown.md)() |

## Properties

| Name | Summary |
|---|---|
| [controller](controller.md) | [jvm]<br>open var [controller](controller.md): [Tasky](../../de.jet.minecraft.tool.timing.tasky/-tasky/index.md)? |
| [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md) | [jvm]<br>open override val [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Service](index.md)&gt; |
| [isRunning](is-running.md) | [jvm]<br>open val [isRunning](is-running.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [key](key.md) | [jvm]<br>open val [key](key.md): NamespacedKey |
| [onCrash](on-crash.md) | [jvm]<br>open val [onCrash](on-crash.md): [Tasky](../../de.jet.minecraft.tool.timing.tasky/-tasky/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStart](on-start.md) | [jvm]<br>open val [onStart](on-start.md): [Tasky](../../de.jet.minecraft.tool.timing.tasky/-tasky/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStop](on-stop.md) | [jvm]<br>open val [onStop](on-stop.md): [Tasky](../../de.jet.minecraft.tool.timing.tasky/-tasky/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [process](process.md) | [jvm]<br>abstract val [process](process.md): [Tasky](../../de.jet.minecraft.tool.timing.tasky/-tasky/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [sectionLabel](section-label.md) | [jvm]<br>open override val [sectionLabel](section-label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md) | [jvm]<br>open val [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md): [Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [temporalAdvice](temporal-advice.md) | [jvm]<br>abstract val [temporalAdvice](temporal-advice.md): [TemporalAdvice](../../de.jet.minecraft.tool.timing.tasky/-temporal-advice/index.md) |
| [thisIdentity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/this-identity.md) | [jvm]<br>abstract val [thisIdentity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/this-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [vendor](../../de.jet.minecraft.tool.smart/-logging/vendor.md) | [jvm]<br>abstract val [vendor](../../de.jet.minecraft.tool.smart/-logging/vendor.md): [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[App](../../de.jet.minecraft.structure.app/-app/index.md)&gt; |
| [vendorIdentity](vendor-identity.md) | [jvm]<br>open override val [vendorIdentity](vendor-identity.md): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;out [App](../../de.jet.minecraft.structure.app/-app/index.md)&gt; |

## Inheritors

| Name |
|---|
| [RequestService](../../de.jet.minecraft.app.component.feature/-keyboard-feature-component/-request-service/index.md) |
| [KeeperService](../../de.jet.minecraft.app.component.system/-jet-keeper-component/-keeper-service/index.md) |
