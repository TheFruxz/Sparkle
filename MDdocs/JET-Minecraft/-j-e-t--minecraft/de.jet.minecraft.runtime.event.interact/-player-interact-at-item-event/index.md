//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.runtime.event.interact](../index.md)/[PlayerInteractAtItemEvent](index.md)

# PlayerInteractAtItemEvent

[jvm]\
data class [PlayerInteractAtItemEvent](index.md)(whoInteract: Player, item: [Item](../../de.jet.minecraft.tool.display.item/-item/index.md), material: Material, action: Action, origin: PlayerInteractEvent, isCancelled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), interactedBlock: Event.Result, interactedItem: Event.Result) : PlayerEvent, [JetPlayerInteractEvent](../-jet-player-interact-event/index.md), Cancellable

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [allowInteraction](../-jet-player-interact-event/allow-interaction.md) | [jvm]<br>open fun [JetPlayerInteractEvent](../-jet-player-interact-event/index.md).[allowInteraction](../-jet-player-interact-event/allow-interaction.md)() |
| [callEvent](index.md#-1071638799%2FFunctions%2F-726029290) | [jvm]<br>open fun [callEvent](index.md#-1071638799%2FFunctions%2F-726029290)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [denyInteraction](../-jet-player-interact-event/deny-interaction.md) | [jvm]<br>open fun [JetPlayerInteractEvent](../-jet-player-interact-event/index.md).[denyInteraction](../-jet-player-interact-event/deny-interaction.md)() |
| [getEventName](index.md#1147460734%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>open fun [getEventName](index.md#1147460734%2FFunctions%2F-726029290)(): @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getHandlers](get-handlers.md) | [jvm]<br>open override fun [getHandlers](get-handlers.md)(): HandlerList |
| [getPlayer](index.md#-1478213936%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>fun [getPlayer](index.md#-1478213936%2FFunctions%2F-726029290)(): @NotNullPlayer |
| [isAsynchronous](index.md#-706610981%2FFunctions%2F-726029290) | [jvm]<br>fun [isAsynchronous](index.md#-706610981%2FFunctions%2F-726029290)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isCancelled](is-cancelled.md) | [jvm]<br>open override fun [isCancelled](is-cancelled.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [setCancelled](set-cancelled.md) | [jvm]<br>open override fun [setCancelled](set-cancelled.md)(cancel: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [action](action.md) | [jvm]<br>val [action](action.md): Action |
| [interactedBlock](interacted-block.md) | [jvm]<br>open override var [interactedBlock](interacted-block.md): Event.Result |
| [interactedItem](interacted-item.md) | [jvm]<br>open override var [interactedItem](interacted-item.md): Event.Result |
| [item](item.md) | [jvm]<br>val [item](item.md): [Item](../../de.jet.minecraft.tool.display.item/-item/index.md) |
| [material](material.md) | [jvm]<br>val [material](material.md): Material |
| [origin](origin.md) | [jvm]<br>open override val [origin](origin.md): PlayerInteractEvent |
| [player](index.md#-8709326%2FProperties%2F-726029290) | [jvm]<br>val [player](index.md#-8709326%2FProperties%2F-726029290): Player |
| [whoInteract](who-interact.md) | [jvm]<br>val [whoInteract](who-interact.md): Player |
