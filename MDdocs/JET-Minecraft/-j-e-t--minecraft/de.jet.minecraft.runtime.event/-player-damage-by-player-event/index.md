//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.runtime.event](../index.md)/[PlayerDamageByPlayerEvent](index.md)

# PlayerDamageByPlayerEvent

[jvm]\
class [PlayerDamageByPlayerEvent](index.md)(attacked: Player, attacker: Player, isCancelled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : PlayerEvent, Cancellable

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [callEvent](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md#-1071638799%2FFunctions%2F-726029290) | [jvm]<br>open fun [callEvent](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md#-1071638799%2FFunctions%2F-726029290)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getEventName](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md#1147460734%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>open fun [getEventName](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md#1147460734%2FFunctions%2F-726029290)(): @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getHandlers](get-handlers.md) | [jvm]<br>open override fun [getHandlers](get-handlers.md)(): HandlerList |
| [getPlayer](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md#-1478213936%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>fun [getPlayer](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md#-1478213936%2FFunctions%2F-726029290)(): @NotNullPlayer |
| [isAsynchronous](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md#-706610981%2FFunctions%2F-726029290) | [jvm]<br>fun [isAsynchronous](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md#-706610981%2FFunctions%2F-726029290)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isCancelled](is-cancelled.md) | [jvm]<br>open override fun [isCancelled](is-cancelled.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [setCancelled](set-cancelled.md) | [jvm]<br>open override fun [setCancelled](set-cancelled.md)(cancel: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [attacked](attacked.md) | [jvm]<br>val [attacked](attacked.md): Player |
| [attacker](attacker.md) | [jvm]<br>val [attacker](attacker.md): Player |
| [player](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md#-8709326%2FProperties%2F-726029290) | [jvm]<br>val [player](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md#-8709326%2FProperties%2F-726029290): Player |
