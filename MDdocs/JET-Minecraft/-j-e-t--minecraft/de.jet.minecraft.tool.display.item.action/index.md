//[JET-Minecraft](../../index.md)/[de.jet.minecraft.tool.display.item.action](index.md)

# Package de.jet.minecraft.tool.display.item.action

## Types

| Name | Summary |
|---|---|
| [ActionCooldown](-action-cooldown/index.md) | [jvm]<br>data class [ActionCooldown](-action-cooldown/index.md)(ticks: [ULong](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-u-long/index.html), type: [ActionCooldownType](-action-cooldown-type/index.md)) |
| [ActionCooldownType](-action-cooldown-type/index.md) | [jvm]<br>enum [ActionCooldownType](-action-cooldown-type/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[ActionCooldownType](-action-cooldown-type/index.md)&gt; |
| [ItemAction](-item-action/index.md) | [jvm]<br>interface [ItemAction](-item-action/index.md)&lt;[T](-item-action/index.md) : Event&gt; |
| [ItemClickAction](-item-click-action/index.md) | [jvm]<br>data class [ItemClickAction](-item-click-action/index.md)(action: InventoryClickEvent.() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), async: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), stop: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), cooldown: [ActionCooldown](-action-cooldown/index.md)?, eventClass: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;InventoryClickEvent&gt;) : [ItemAction](-item-action/index.md)&lt;InventoryClickEvent&gt; |
| [ItemInteractAction](-item-interact-action/index.md) | [jvm]<br>data class [ItemInteractAction](-item-interact-action/index.md)(action: [PlayerInteractAtItemEvent](../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), async: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), stop: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), cooldown: [ActionCooldown](-action-cooldown/index.md)?, eventClass: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;[PlayerInteractAtItemEvent](../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md)&gt;) : [ItemAction](-item-action/index.md)&lt;[PlayerInteractAtItemEvent](../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md)&gt; |
