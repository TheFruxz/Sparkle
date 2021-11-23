//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.structure.component](../index.md)/[Component](index.md)

# Component

[jvm]\
abstract class [Component](index.md)(vendor: [App](../../de.jet.minecraft.structure.app/-app/index.md), behaviour: [Component.RunType](-run-type/index.md)) : [VendorsIdentifiable](../../de.jet.minecraft.tool.smart/-vendors-identifiable/index.md)&lt;[Component](index.md)&gt; , [Logging](../../de.jet.minecraft.tool.smart/-logging/index.md)

## Types

| Name | Summary |
|---|---|
| [RunType](-run-type/index.md) | [jvm]<br>enum [RunType](-run-type/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Component.RunType](-run-type/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [firstContactHandshake](first-contact-handshake.md) | [jvm]<br>fun [firstContactHandshake](first-contact-handshake.md)() |
| [register](register.md) | [jvm]<br>open fun [register](register.md)()<br>Can be overwritten, no origin code! |
| [start](start.md) | [jvm]<br>abstract fun [start](start.md)() |
| [stop](stop.md) | [jvm]<br>abstract fun [stop](stop.md)() |

## Properties

| Name | Summary |
|---|---|
| [behaviour](behaviour.md) | [jvm]<br>open val [behaviour](behaviour.md): [Component.RunType](-run-type/index.md) |
| [canBeAutoStartToggled](can-be-auto-start-toggled.md) | [jvm]<br>val [canBeAutoStartToggled](can-be-auto-start-toggled.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [canBeStopped](can-be-stopped.md) | [jvm]<br>val [canBeStopped](can-be-stopped.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md) | [jvm]<br>open override val [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Component](index.md)&gt; |
| [isAutoStarting](is-auto-starting.md) | [jvm]<br>val [isAutoStarting](is-auto-starting.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isRunning](is-running.md) | [jvm]<br>val [isRunning](is-running.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [key](key.md) | [jvm]<br>val [key](key.md): NamespacedKey |
| [sectionLabel](section-label.md) | [jvm]<br>open override val [sectionLabel](section-label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md) | [jvm]<br>open val [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md): [Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [thisIdentity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/this-identity.md) | [jvm]<br>abstract val [thisIdentity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/this-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [vendor](vendor.md) | [jvm]<br>open override val [vendor](vendor.md): [App](../../de.jet.minecraft.structure.app/-app/index.md) |
| [vendorIdentity](vendor-identity.md) | [jvm]<br>open override val [vendorIdentity](vendor-identity.md): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;out [App](../../de.jet.minecraft.structure.app/-app/index.md)&gt; |

## Inheritors

| Name |
|---|
| [KeyboardFeatureComponent](../../de.jet.minecraft.app.component.feature/-keyboard-feature-component/index.md) |
| [SmartComponent](../-smart-component/index.md) |
