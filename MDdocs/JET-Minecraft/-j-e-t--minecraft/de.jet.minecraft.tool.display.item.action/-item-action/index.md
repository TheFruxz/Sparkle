//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.tool.display.item.action](../index.md)/[ItemAction](index.md)

# ItemAction

[jvm]\
interface [ItemAction](index.md)&lt;[T](index.md) : Event&gt;

## Properties

| Name | Summary |
|---|---|
| [action](action.md) | [jvm]<br>abstract var [action](action.md): [T](index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [async](async.md) | [jvm]<br>abstract var [async](async.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [cooldown](cooldown.md) | [jvm]<br>abstract var [cooldown](cooldown.md): [ActionCooldown](../-action-cooldown/index.md)? |
| [eventClass](event-class.md) | [jvm]<br>abstract val [eventClass](event-class.md): [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;[T](index.md)&gt; |
| [stop](stop.md) | [jvm]<br>abstract var [stop](stop.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Inheritors

| Name |
|---|
| [ItemClickAction](../-item-click-action/index.md) |
| [ItemInteractAction](../-item-interact-action/index.md) |
