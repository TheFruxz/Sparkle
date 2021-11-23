//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.app.component.feature](../index.md)/[MarkingFeatureComponent](index.md)

# MarkingFeatureComponent

[jvm]\
class [MarkingFeatureComponent](index.md)(vendor: [App](../../de.jet.minecraft.structure.app/-app/index.md)) : [SmartComponent](../../de.jet.minecraft.structure.component/-smart-component/index.md)

## Functions

| Name | Summary |
|---|---|
| [component](component.md) | [jvm]<br>open override fun [component](component.md)()<br>fun [component](../../de.jet.minecraft.structure.component/-smart-component/component.md)(vararg component: [Component](../../de.jet.minecraft.structure.component/-component/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [firstContactHandshake](../../de.jet.minecraft.structure.component/-component/first-contact-handshake.md) | [jvm]<br>fun [firstContactHandshake](../../de.jet.minecraft.structure.component/-component/first-contact-handshake.md)() |
| [interchange](../../de.jet.minecraft.structure.component/-smart-component/interchange.md) | [jvm]<br>fun [interchange](../../de.jet.minecraft.structure.component/-smart-component/interchange.md)(vararg interchange: [Interchange](../../de.jet.minecraft.structure.command/-interchange/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [listener](../../de.jet.minecraft.structure.component/-smart-component/listener.md) | [jvm]<br>fun [listener](../../de.jet.minecraft.structure.component/-smart-component/listener.md)(vararg listener: [EventListener](../../de.jet.minecraft.structure.app.event/-event-listener/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [register](../../de.jet.minecraft.structure.component/-smart-component/register.md) | [jvm]<br>override fun [register](../../de.jet.minecraft.structure.component/-smart-component/register.md)()<br>Can be overwritten, no origin code! |
| [service](../../de.jet.minecraft.structure.component/-smart-component/service.md) | [jvm]<br>fun [service](../../de.jet.minecraft.structure.component/-smart-component/service.md)(vararg service: [Service](../../de.jet.minecraft.structure.service/-service/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [start](../../de.jet.minecraft.structure.component/-smart-component/start.md) | [jvm]<br>override fun [start](../../de.jet.minecraft.structure.component/-smart-component/start.md)() |
| [stop](../../de.jet.minecraft.structure.component/-smart-component/stop.md) | [jvm]<br>override fun [stop](../../de.jet.minecraft.structure.component/-smart-component/stop.md)() |

## Properties

| Name | Summary |
|---|---|
| [behaviour](../../de.jet.minecraft.structure.component/-smart-component/behaviour.md) | [jvm]<br>open override val [behaviour](../../de.jet.minecraft.structure.component/-smart-component/behaviour.md): [Component.RunType](../../de.jet.minecraft.structure.component/-component/-run-type/index.md) |
| [canBeAutoStartToggled](../../de.jet.minecraft.structure.component/-component/can-be-auto-start-toggled.md) | [jvm]<br>val [canBeAutoStartToggled](../../de.jet.minecraft.structure.component/-component/can-be-auto-start-toggled.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [canBeStopped](../../de.jet.minecraft.structure.component/-component/can-be-stopped.md) | [jvm]<br>val [canBeStopped](../../de.jet.minecraft.structure.component/-component/can-be-stopped.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [components](../../de.jet.minecraft.structure.component/-smart-component/components.md) | [jvm]<br>val [components](../../de.jet.minecraft.structure.component/-smart-component/components.md): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[Component](../../de.jet.minecraft.structure.component/-component/index.md)&gt; |
| [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md) | [jvm]<br>open override val [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Component](../../de.jet.minecraft.structure.component/-component/index.md)&gt; |
| [interchanges](../../de.jet.minecraft.structure.component/-smart-component/interchanges.md) | [jvm]<br>val [interchanges](../../de.jet.minecraft.structure.component/-smart-component/interchanges.md): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[Interchange](../../de.jet.minecraft.structure.command/-interchange/index.md)&gt; |
| [isAutoStarting](../../de.jet.minecraft.structure.component/-component/is-auto-starting.md) | [jvm]<br>val [isAutoStarting](../../de.jet.minecraft.structure.component/-component/is-auto-starting.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isRunning](../../de.jet.minecraft.structure.component/-component/is-running.md) | [jvm]<br>val [isRunning](../../de.jet.minecraft.structure.component/-component/is-running.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [key](../../de.jet.minecraft.structure.component/-component/key.md) | [jvm]<br>val [key](../../de.jet.minecraft.structure.component/-component/key.md): NamespacedKey |
| [listeners](../../de.jet.minecraft.structure.component/-smart-component/listeners.md) | [jvm]<br>val [listeners](../../de.jet.minecraft.structure.component/-smart-component/listeners.md): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[EventListener](../../de.jet.minecraft.structure.app.event/-event-listener/index.md)&gt; |
| [sectionLabel](../../de.jet.minecraft.structure.component/-component/section-label.md) | [jvm]<br>open override val [sectionLabel](../../de.jet.minecraft.structure.component/-component/section-label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md) | [jvm]<br>open val [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md): [Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [services](../../de.jet.minecraft.structure.component/-smart-component/services.md) | [jvm]<br>val [services](../../de.jet.minecraft.structure.component/-smart-component/services.md): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[Service](../../de.jet.minecraft.structure.service/-service/index.md)&gt; |
| [thisIdentity](this-identity.md) | [jvm]<br>open override val [thisIdentity](this-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [vendor](../../de.jet.minecraft.structure.component/-smart-component/vendor.md) | [jvm]<br>open override val [vendor](../../de.jet.minecraft.structure.component/-smart-component/vendor.md): [App](../../de.jet.minecraft.structure.app/-app/index.md) |
| [vendorIdentity](../../de.jet.minecraft.structure.component/-component/vendor-identity.md) | [jvm]<br>open override val [vendorIdentity](../../de.jet.minecraft.structure.component/-component/vendor-identity.md): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;out [App](../../de.jet.minecraft.structure.app/-app/index.md)&gt; |
