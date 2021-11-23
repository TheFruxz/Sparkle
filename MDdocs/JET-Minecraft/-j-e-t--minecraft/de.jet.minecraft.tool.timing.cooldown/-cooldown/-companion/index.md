//[JET-Minecraft](../../../../index.md)/[de.jet.minecraft.tool.timing.cooldown](../../index.md)/[Cooldown](../index.md)/[Companion](index.md)

# Companion

[jvm]\
object [Companion](index.md)

## Types

| Name | Summary |
|---|---|
| [CooldownSection](-cooldown-section/index.md) | [jvm]<br>object [CooldownSection](-cooldown-section/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getCooldown](get-cooldown.md) | [jvm]<br>fun [getCooldown](get-cooldown.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), section: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = CooldownSection.general()): [Cooldown](../index.md)? |
| [isCooldownRunning](is-cooldown-running.md) | [jvm]<br>fun [isCooldownRunning](is-cooldown-running.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), section: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = CooldownSection.general()): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [launchCooldown](launch-cooldown.md) | [jvm]<br>fun [launchCooldown](launch-cooldown.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ticks: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), section: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = CooldownSection.general()): [Cooldown](../index.md) |
