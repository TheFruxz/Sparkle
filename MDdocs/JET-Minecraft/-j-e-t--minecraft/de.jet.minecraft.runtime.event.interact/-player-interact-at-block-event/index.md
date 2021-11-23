//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.runtime.event.interact](../index.md)/[PlayerInteractAtBlockEvent](index.md)

# PlayerInteractAtBlockEvent

[jvm]\
data class [PlayerInteractAtBlockEvent](index.md)(whoInteract: Player, block: Block, material: Material, action: Action, origin: PlayerInteractEvent, isCancelled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), interactedBlock: Event.Result, interactedItem: Event.Result) : PlayerEvent, [JetPlayerInteractEvent](../-jet-player-interact-event/index.md), Cancellable

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [allowInteraction](../-jet-player-interact-event/allow-interaction.md) | [jvm]<br>open fun [JetPlayerInteractEvent](../-jet-player-interact-event/index.md).[allowInteraction](../-jet-player-interact-event/allow-interaction.md)() |
| [callEvent](../-player-interact-at-item-event/index.md#-1071638799%2FFunctions%2F-726029290) | [jvm]<br>open fun [callEvent](../-player-interact-at-item-event/index.md#-1071638799%2FFunctions%2F-726029290)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [denyInteraction](../-jet-player-interact-event/deny-interaction.md) | [jvm]<br>open fun [JetPlayerInteractEvent](../-jet-player-interact-event/index.md).[denyInteraction](../-jet-player-interact-event/deny-interaction.md)() |
| [getEventName](../-player-interact-at-item-event/index.md#1147460734%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>open fun [getEventName](../-player-interact-at-item-event/index.md#1147460734%2FFunctions%2F-726029290)(): @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getHandlers](get-handlers.md) | [jvm]<br>open override fun [getHandlers](get-handlers.md)(): HandlerList |
| [getPlayer](../-player-interact-at-item-event/index.md#-1478213936%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>fun [getPlayer](../-player-interact-at-item-event/index.md#-1478213936%2FFunctions%2F-726029290)(): @NotNullPlayer |
| [isAsynchronous](../-player-interact-at-item-event/index.md#-706610981%2FFunctions%2F-726029290) | [jvm]<br>fun [isAsynchronous](../-player-interact-at-item-event/index.md#-706610981%2FFunctions%2F-726029290)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isCancelled](is-cancelled.md) | [jvm]<br>open override fun [isCancelled](is-cancelled.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [setCancelled](set-cancelled.md) | [jvm]<br>open override fun [setCancelled](set-cancelled.md)(cancel: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [action](action.md) | [jvm]<br>val [action](action.md): Action |
| [block](block.md) | [jvm]<br>val [block](block.md): Block |
| [interactedBlock](interacted-block.md) | [jvm]<br>open override var [interactedBlock](interacted-block.md): Event.Result |
| [interactedItem](interacted-item.md) | [jvm]<br>open override var [interactedItem](interacted-item.md): Event.Result |
| [material](material.md) | [jvm]<br>val [material](material.md): Material |
| [origin](origin.md) | [jvm]<br>open override val [origin](origin.md): PlayerInteractEvent |
| [player](../-player-interact-at-item-event/index.md#-8709326%2FProperties%2F-726029290) | [jvm]<br>val [player](../-player-interact-at-item-event/index.md#-8709326%2FProperties%2F-726029290): Player |
| [whoInteract](who-interact.md) | [jvm]<br>val [whoInteract](who-interact.md): Player |

## Extensions

| Name | Summary |
|---|---|
| [realAffectedBlock](../../de.jet.minecraft.extension.paper/real-affected-block.md) | [jvm]<br>val [PlayerInteractAtBlockEvent](index.md).[realAffectedBlock](../../de.jet.minecraft.extension.paper/real-affected-block.md): Location |
