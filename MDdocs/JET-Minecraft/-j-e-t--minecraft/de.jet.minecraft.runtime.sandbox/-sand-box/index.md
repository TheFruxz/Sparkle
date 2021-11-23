//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.runtime.sandbox](../index.md)/[SandBox](index.md)

# SandBox

[jvm]\
data class [SandBox](index.md)(vendor: [App](../../de.jet.minecraft.structure.app/-app/index.md), thisIdentity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), creationTime: [Calendar](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.timing.calendar/-calendar/index.md), creationLocation: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), process: [SandBoxInteraction](../-sand-box-interaction/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) : [VendorsIdentifiable](../../de.jet.minecraft.tool.smart/-vendors-identifiable/index.md)&lt;[SandBox](index.md)&gt; , [Logging](../../de.jet.minecraft.tool.smart/-logging/index.md)

## Functions

| Name | Summary |
|---|---|
| [execute](execute.md) | [jvm]<br>fun [execute](execute.md)(executor: CommandSender, parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList()) |

## Properties

| Name | Summary |
|---|---|
| [creationLocation](creation-location.md) | [jvm]<br>val [creationLocation](creation-location.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [creationTime](creation-time.md) | [jvm]<br>val [creationTime](creation-time.md): [Calendar](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.timing.calendar/-calendar/index.md) |
| [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md) | [jvm]<br>open override val [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[SandBox](index.md)&gt; |
| [process](process.md) | [jvm]<br>val [process](process.md): [SandBoxInteraction](../-sand-box-interaction/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [sectionLabel](section-label.md) | [jvm]<br>open override val [sectionLabel](section-label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md) | [jvm]<br>open val [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md): [Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [thisIdentity](this-identity.md) | [jvm]<br>open override val [thisIdentity](this-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [vendor](vendor.md) | [jvm]<br>open override val [vendor](vendor.md): [App](../../de.jet.minecraft.structure.app/-app/index.md) |
| [vendorIdentity](vendor-identity.md) | [jvm]<br>open override val [vendorIdentity](vendor-identity.md): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[App](../../de.jet.minecraft.structure.app/-app/index.md)&gt; |
