//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.tool.display.item.action](../index.md)/[ItemInteractAction](index.md)

# ItemInteractAction

[jvm]\
data class [ItemInteractAction](index.md)(action: [PlayerInteractAtItemEvent](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), async: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), stop: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), cooldown: [ActionCooldown](../-action-cooldown/index.md)?, eventClass: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;[PlayerInteractAtItemEvent](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md)&gt;) : [ItemAction](../-item-action/index.md)&lt;[PlayerInteractAtItemEvent](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md)&gt;

## Properties

| Name | Summary |
|---|---|
| [action](action.md) | [jvm]<br>open override var [action](action.md): [PlayerInteractAtItemEvent](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [async](async.md) | [jvm]<br>open override var [async](async.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true |
| [cooldown](cooldown.md) | [jvm]<br>open override var [cooldown](cooldown.md): [ActionCooldown](../-action-cooldown/index.md)? = null |
| [eventClass](event-class.md) | [jvm]<br>open override val [eventClass](event-class.md): [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;[PlayerInteractAtItemEvent](../../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md)&gt; |
| [stop](stop.md) | [jvm]<br>open override var [stop](stop.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true |
