package dev.fruxz.sparkle.framework.ux.effect.potion

import dev.fruxz.ascend.extension.time.inWholeMinecraftTicks
import dev.fruxz.sparkle.framework.ux.effect.EffectDsl
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@EffectDsl
fun PotionEffect(
    type: PotionEffectType,
    durationTicks: Int,
    amplifier: Int = 0,
    ambient: Boolean = true,
    particles: Boolean = true,
    icon: Boolean = true,
): PotionEffect = PotionEffect(
    type,
    durationTicks,
    amplifier,
    ambient,
    particles,
    icon
)

@EffectDsl
fun PotionEffect(
    type: PotionEffectType,
    duration: Duration = 10.seconds,
    amplifier: Int = 0,
    ambient: Boolean = true,
    particles: Boolean = true,
    icon: Boolean = true,
): PotionEffect = PotionEffect(
    type,
    duration.inWholeMinecraftTicks.toInt(),
    amplifier,
    ambient,
    particles,
    icon
)

fun buildPotionEffect(
    type: PotionEffectType,
    duration: Duration = 10.seconds,
    amplifier: Int = 0,
    ambient: Boolean = true,
    particles: Boolean = true,
    icon: Boolean = true,
    builder: PotionEffect.() -> Unit,
): PotionEffect = PotionEffect(
    type,
    duration,
    amplifier,
    ambient,
    particles,
    icon
).apply(builder)