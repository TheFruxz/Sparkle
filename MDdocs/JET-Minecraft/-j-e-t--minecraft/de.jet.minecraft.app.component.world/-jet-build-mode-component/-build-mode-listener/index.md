//[JET-Minecraft](../../../../index.md)/[de.jet.minecraft.app.component.world](../../index.md)/[JetBuildModeComponent](../index.md)/[BuildModeListener](index.md)

# BuildModeListener

[jvm]\
class [BuildModeListener](index.md)(vendor: [App](../../../de.jet.minecraft.structure.app/-app/index.md)) : [EventListener](../../../de.jet.minecraft.structure.app.event/-event-listener/index.md)

## Functions

| Name | Summary |
|---|---|
| [onBlockDestroy](on-block-destroy.md) | [jvm]<br>fun [onBlockDestroy](on-block-destroy.md)(event: BlockBreakEvent) |
| [onBlockInteract](on-block-interact.md) | [jvm]<br>fun [onBlockInteract](on-block-interact.md)(event: [PlayerInteractAtBlockEvent](../../../de.jet.minecraft.runtime.event.interact/-player-interact-at-block-event/index.md)) |
| [onBlockPlace](on-block-place.md) | [jvm]<br>fun [onBlockPlace](on-block-place.md)(event: BlockPlaceEvent) |

## Properties

| Name | Summary |
|---|---|
| [identity](../../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md) | [jvm]<br>open override val [identity](../../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[EventListener](../../../de.jet.minecraft.structure.app.event/-event-listener/index.md)&gt; |
| [listenerIdentity](../../../de.jet.minecraft.structure.app.event/-event-listener/listener-identity.md) | [jvm]<br>open val [listenerIdentity](../../../de.jet.minecraft.structure.app.event/-event-listener/listener-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [thisIdentity](../../../de.jet.minecraft.structure.app.event/-event-listener/this-identity.md) | [jvm]<br>open override val [thisIdentity](../../../de.jet.minecraft.structure.app.event/-event-listener/this-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [vendor](vendor.md) | [jvm]<br>open override val [vendor](vendor.md): [App](../../../de.jet.minecraft.structure.app/-app/index.md) |
| [vendorIdentity](../../../de.jet.minecraft.structure.app.event/-event-listener/vendor-identity.md) | [jvm]<br>open override val [vendorIdentity](../../../de.jet.minecraft.structure.app.event/-event-listener/vendor-identity.md): [Identity](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[App](../../../de.jet.minecraft.structure.app/-app/index.md)&gt; |
