//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.app.component.feature](../index.md)/[KeyboardFeatureComponent](index.md)

# KeyboardFeatureComponent

[jvm]\
class [KeyboardFeatureComponent](index.md)(vendor: [App](../../de.jet.minecraft.structure.app/-app/index.md)) : [Component](../../de.jet.minecraft.structure.component/-component/index.md)

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |
| [RequestListener](-request-listener/index.md) | [jvm]<br>class [RequestListener](-request-listener/index.md)(vendor: [App](../../de.jet.minecraft.structure.app/-app/index.md)) : [EventListener](../../de.jet.minecraft.structure.app.event/-event-listener/index.md) |
| [RequestService](-request-service/index.md) | [jvm]<br>class [RequestService](-request-service/index.md)(vendor: [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[App](../../de.jet.minecraft.structure.app/-app/index.md)&gt;) : [Service](../../de.jet.minecraft.structure.service/-service/index.md) |

## Functions

| Name | Summary |
|---|---|
| [firstContactHandshake](../../de.jet.minecraft.structure.component/-component/first-contact-handshake.md) | [jvm]<br>fun [firstContactHandshake](../../de.jet.minecraft.structure.component/-component/first-contact-handshake.md)() |
| [register](register.md) | [jvm]<br>open override fun [register](register.md)()<br>Can be overwritten, no origin code! |
| [start](start.md) | [jvm]<br>open override fun [start](start.md)() |
| [stop](stop.md) | [jvm]<br>open override fun [stop](stop.md)() |

## Properties

| Name | Summary |
|---|---|
| [behaviour](../../de.jet.minecraft.structure.component/-component/behaviour.md) | [jvm]<br>open val [behaviour](../../de.jet.minecraft.structure.component/-component/behaviour.md): [Component.RunType](../../de.jet.minecraft.structure.component/-component/-run-type/index.md) |
| [canBeAutoStartToggled](../../de.jet.minecraft.structure.component/-component/can-be-auto-start-toggled.md) | [jvm]<br>val [canBeAutoStartToggled](../../de.jet.minecraft.structure.component/-component/can-be-auto-start-toggled.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [canBeStopped](../../de.jet.minecraft.structure.component/-component/can-be-stopped.md) | [jvm]<br>val [canBeStopped](../../de.jet.minecraft.structure.component/-component/can-be-stopped.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md) | [jvm]<br>open override val [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Component](../../de.jet.minecraft.structure.component/-component/index.md)&gt; |
| [isAutoStarting](../../de.jet.minecraft.structure.component/-component/is-auto-starting.md) | [jvm]<br>val [isAutoStarting](../../de.jet.minecraft.structure.component/-component/is-auto-starting.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isRunning](../../de.jet.minecraft.structure.component/-component/is-running.md) | [jvm]<br>val [isRunning](../../de.jet.minecraft.structure.component/-component/is-running.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [key](../../de.jet.minecraft.structure.component/-component/key.md) | [jvm]<br>val [key](../../de.jet.minecraft.structure.component/-component/key.md): NamespacedKey |
| [sectionLabel](../../de.jet.minecraft.structure.component/-component/section-label.md) | [jvm]<br>open override val [sectionLabel](../../de.jet.minecraft.structure.component/-component/section-label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md) | [jvm]<br>open val [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md): [Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [thisIdentity](this-identity.md) | [jvm]<br>open override val [thisIdentity](this-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [vendor](vendor.md) | [jvm]<br>open override val [vendor](vendor.md): [App](../../de.jet.minecraft.structure.app/-app/index.md) |
| [vendorIdentity](../../de.jet.minecraft.structure.component/-component/vendor-identity.md) | [jvm]<br>open override val [vendorIdentity](../../de.jet.minecraft.structure.component/-component/vendor-identity.md): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;out [App](../../de.jet.minecraft.structure.app/-app/index.md)&gt; |
