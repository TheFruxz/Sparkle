//[JET-Minecraft](../../../../index.md)/[de.jet.minecraft.app.component.system](../../index.md)/[JetKeeperComponent](../index.md)/[KeeperService](index.md)

# KeeperService

[jvm]\
class [KeeperService](index.md)(vendor: [Identifiable](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[App](../../../de.jet.minecraft.structure.app/-app/index.md)&gt;) : [Service](../../../de.jet.minecraft.structure.service/-service/index.md)

## Functions

| Name | Summary |
|---|---|
| [boot](../../../de.jet.minecraft.structure.service/-service/boot.md) | [jvm]<br>open fun [boot](../../../de.jet.minecraft.structure.service/-service/boot.md)() |
| [shutdown](../../../de.jet.minecraft.structure.service/-service/shutdown.md) | [jvm]<br>open fun [shutdown](../../../de.jet.minecraft.structure.service/-service/shutdown.md)() |

## Properties

| Name | Summary |
|---|---|
| [controller](../../../de.jet.minecraft.structure.service/-service/controller.md) | [jvm]<br>open var [controller](../../../de.jet.minecraft.structure.service/-service/controller.md): [Tasky](../../../de.jet.minecraft.tool.timing.tasky/-tasky/index.md)? |
| [identity](../../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md) | [jvm]<br>open override val [identity](../../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Service](../../../de.jet.minecraft.structure.service/-service/index.md)&gt; |
| [isRunning](../../../de.jet.minecraft.structure.service/-service/is-running.md) | [jvm]<br>open val [isRunning](../../../de.jet.minecraft.structure.service/-service/is-running.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [key](../../../de.jet.minecraft.structure.service/-service/key.md) | [jvm]<br>open val [key](../../../de.jet.minecraft.structure.service/-service/key.md): NamespacedKey |
| [onCrash](../../../de.jet.minecraft.structure.service/-service/on-crash.md) | [jvm]<br>open val [onCrash](../../../de.jet.minecraft.structure.service/-service/on-crash.md): [Tasky](../../../de.jet.minecraft.tool.timing.tasky/-tasky/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStart](on-start.md) | [jvm]<br>open override val [onStart](on-start.md): [Tasky](../../../de.jet.minecraft.tool.timing.tasky/-tasky/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStop](../../../de.jet.minecraft.structure.service/-service/on-stop.md) | [jvm]<br>open val [onStop](../../../de.jet.minecraft.structure.service/-service/on-stop.md): [Tasky](../../../de.jet.minecraft.tool.timing.tasky/-tasky/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [process](process.md) | [jvm]<br>open override val [process](process.md): [Tasky](../../../de.jet.minecraft.tool.timing.tasky/-tasky/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [sectionLabel](../../../de.jet.minecraft.structure.service/-service/section-label.md) | [jvm]<br>open override val [sectionLabel](../../../de.jet.minecraft.structure.service/-service/section-label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [sectionLog](../../../de.jet.minecraft.tool.smart/-logging/section-log.md) | [jvm]<br>open val [sectionLog](../../../de.jet.minecraft.tool.smart/-logging/section-log.md): [Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [temporalAdvice](temporal-advice.md) | [jvm]<br>open override val [temporalAdvice](temporal-advice.md): [TemporalAdvice](../../../de.jet.minecraft.tool.timing.tasky/-temporal-advice/index.md) |
| [thisIdentity](this-identity.md) | [jvm]<br>open override val [thisIdentity](this-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [vendor](vendor.md) | [jvm]<br>open override val [vendor](vendor.md): [Identifiable](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[App](../../../de.jet.minecraft.structure.app/-app/index.md)&gt; |
| [vendorIdentity](../../../de.jet.minecraft.structure.service/-service/vendor-identity.md) | [jvm]<br>open override val [vendorIdentity](../../../de.jet.minecraft.structure.service/-service/vendor-identity.md): [Identity](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;out [App](../../../de.jet.minecraft.structure.app/-app/index.md)&gt; |
