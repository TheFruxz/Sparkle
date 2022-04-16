package de.moltenKt.paper.extension.effect

import de.moltenKt.core.extension.time.inWholeMinecraftTicks
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun PotionEffect(type: PotionEffectType, durationTicks: Int, amplifier: Int = 0, ambient: Boolean = true, particles: Boolean = true, icon: Boolean = true) =
    PotionEffect(
        /* type = */ type,
        /* duration = */ durationTicks,
        /* amplifier = */ amplifier,
        /* ambient = */ ambient,
        /* particles = */ particles,
        /* icon = */ icon
    )
fun PotionEffect(type: PotionEffectType, duration: Duration = 10.seconds, amplifier: Int = 0, ambient: Boolean = true, particles: Boolean = true, icon: Boolean = true) =
    PotionEffect(type, duration.inWholeMinecraftTicks.toInt(), amplifier, ambient, particles, icon)