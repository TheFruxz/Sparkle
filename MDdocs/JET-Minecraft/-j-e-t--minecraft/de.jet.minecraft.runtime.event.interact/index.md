//[JET-Minecraft](../../index.md)/[de.jet.minecraft.runtime.event.interact](index.md)

# Package de.jet.minecraft.runtime.event.interact

## Types

| Name | Summary |
|---|---|
| [JetPlayerInteractEvent](-jet-player-interact-event/index.md) | [jvm]<br>interface [JetPlayerInteractEvent](-jet-player-interact-event/index.md) |
| [PlayerInteractAtBlockEvent](-player-interact-at-block-event/index.md) | [jvm]<br>data class [PlayerInteractAtBlockEvent](-player-interact-at-block-event/index.md)(whoInteract: Player, block: Block, material: Material, action: Action, origin: PlayerInteractEvent, isCancelled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), interactedBlock: Event.Result, interactedItem: Event.Result) : PlayerEvent, [JetPlayerInteractEvent](-jet-player-interact-event/index.md), Cancellable |
| [PlayerInteractAtItemEvent](-player-interact-at-item-event/index.md) | [jvm]<br>data class [PlayerInteractAtItemEvent](-player-interact-at-item-event/index.md)(whoInteract: Player, item: [Item](../de.jet.minecraft.tool.display.item/-item/index.md), material: Material, action: Action, origin: PlayerInteractEvent, isCancelled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), interactedBlock: Event.Result, interactedItem: Event.Result) : PlayerEvent, [JetPlayerInteractEvent](-jet-player-interact-event/index.md), Cancellable |
