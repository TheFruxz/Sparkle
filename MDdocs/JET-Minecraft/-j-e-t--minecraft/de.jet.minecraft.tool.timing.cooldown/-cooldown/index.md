//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.tool.timing.cooldown](../index.md)/[Cooldown](index.md)

# Cooldown

[jvm]\
data class [Cooldown](index.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ticks: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[Cooldown](index.md)&gt;

## Constructors

| | |
|---|---|
| [Cooldown](-cooldown.md) | [jvm]<br>fun [Cooldown](-cooldown.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ticks: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-number/index.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [startCooldown](start-cooldown.md) | [jvm]<br>fun [startCooldown](start-cooldown.md)() |
| [stopCooldown](stop-cooldown.md) | [jvm]<br>fun [stopCooldown](stop-cooldown.md)() |

## Properties

| Name | Summary |
|---|---|
| [destination](destination.md) | [jvm]<br>val [destination](destination.md): [Calendar](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.timing.calendar/-calendar/index.md) |
| [identity](identity.md) | [jvm]<br>open override val [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Cooldown](index.md)&gt; |
| [remainingTime](remaining-time.md) | [jvm]<br>val [remainingTime](remaining-time.md): [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html) |
| [ticks](ticks.md) | [jvm]<br>val [ticks](ticks.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

## Extensions

| Name | Summary |
|---|---|
| [isOver](../../de.jet.minecraft.extension.timing/is-over.md) | [jvm]<br>val [Cooldown](index.md)?.[isOver](../../de.jet.minecraft.extension.timing/is-over.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
