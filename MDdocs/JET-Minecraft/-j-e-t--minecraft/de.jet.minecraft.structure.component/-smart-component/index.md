//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.structure.component](../index.md)/[SmartComponent](index.md)

# SmartComponent

[jvm]\
abstract class [SmartComponent](index.md)(vendor: [App](../../de.jet.minecraft.structure.app/-app/index.md), behaviour: [Component.RunType](../-component/-run-type/index.md)) : [Component](../-component/index.md)

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [component](component.md) | [jvm]<br>abstract fun [component](component.md)()<br>fun [component](component.md)(vararg component: [Component](../-component/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [firstContactHandshake](../-component/first-contact-handshake.md) | [jvm]<br>fun [firstContactHandshake](../-component/first-contact-handshake.md)() |
| [interchange](interchange.md) | [jvm]<br>fun [interchange](interchange.md)(vararg interchange: [Interchange](../../de.jet.minecraft.structure.command/-interchange/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [listener](listener.md) | [jvm]<br>fun [listener](listener.md)(vararg listener: [EventListener](../../de.jet.minecraft.structure.app.event/-event-listener/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [register](register.md) | [jvm]<br>override fun [register](register.md)()<br>Can be overwritten, no origin code! |
| [service](service.md) | [jvm]<br>fun [service](service.md)(vararg service: [Service](../../de.jet.minecraft.structure.service/-service/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [start](start.md) | [jvm]<br>override fun [start](start.md)() |
| [stop](stop.md) | [jvm]<br>override fun [stop](stop.md)() |

## Properties

| Name | Summary |
|---|---|
| [behaviour](behaviour.md) | [jvm]<br>open override val [behaviour](behaviour.md): [Component.RunType](../-component/-run-type/index.md) |
| [canBeAutoStartToggled](../-component/can-be-auto-start-toggled.md) | [jvm]<br>val [canBeAutoStartToggled](../-component/can-be-auto-start-toggled.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [canBeStopped](../-component/can-be-stopped.md) | [jvm]<br>val [canBeStopped](../-component/can-be-stopped.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [components](components.md) | [jvm]<br>val [components](components.md): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[Component](../-component/index.md)&gt; |
| [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md) | [jvm]<br>open override val [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Component](../-component/index.md)&gt; |
| [interchanges](interchanges.md) | [jvm]<br>val [interchanges](interchanges.md): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[Interchange](../../de.jet.minecraft.structure.command/-interchange/index.md)&gt; |
| [isAutoStarting](../-component/is-auto-starting.md) | [jvm]<br>val [isAutoStarting](../-component/is-auto-starting.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isRunning](../-component/is-running.md) | [jvm]<br>val [isRunning](../-component/is-running.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [key](../-component/key.md) | [jvm]<br>val [key](../-component/key.md): NamespacedKey |
| [listeners](listeners.md) | [jvm]<br>val [listeners](listeners.md): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[EventListener](../../de.jet.minecraft.structure.app.event/-event-listener/index.md)&gt; |
| [sectionLabel](../-component/section-label.md) | [jvm]<br>open override val [sectionLabel](../-component/section-label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md) | [jvm]<br>open val [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md): [Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [services](services.md) | [jvm]<br>val [services](services.md): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[Service](../../de.jet.minecraft.structure.service/-service/index.md)&gt; |
| [thisIdentity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/this-identity.md) | [jvm]<br>abstract val [thisIdentity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/this-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [vendor](vendor.md) | [jvm]<br>open override val [vendor](vendor.md): [App](../../de.jet.minecraft.structure.app/-app/index.md) |
| [vendorIdentity](../-component/vendor-identity.md) | [jvm]<br>open override val [vendorIdentity](../-component/vendor-identity.md): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;out [App](../../de.jet.minecraft.structure.app/-app/index.md)&gt; |

## Inheritors

| Name |
|---|
| [EssentialsComponent](../../de.jet.minecraft.app.component.essentials/-essentials-component/index.md) |
| [MarkingFeatureComponent](../../de.jet.minecraft.app.component.feature/-marking-feature-component/index.md) |
| [JetAssistiveInterchangesComponent](../../de.jet.minecraft.app.component.system/-jet-assistive-interchanges-component/index.md) |
| [JetKeeperComponent](../../de.jet.minecraft.app.component.system/-jet-keeper-component/index.md) |
| [JetBuildModeComponent](../../de.jet.minecraft.app.component.world/-jet-build-mode-component/index.md) |
