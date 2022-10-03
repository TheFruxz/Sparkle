package de.fruxz.sparkle.framework.util.extension.effect

import de.fruxz.ascend.extension.time.inWholeMinecraftTicks
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * This class-object like function creates a [PotionEffect] with the given parameters, but has an already some default values.
 * @param type The type of the [PotionEffect]
 * @param durationTicks The duration of the [PotionEffect]
 * @param amplifier The amplifier of the [PotionEffect]
 * @param ambient Whether the [PotionEffect] is ambient or not
 * @param particles Whether the [PotionEffect] has particles or not
 * @param icon The icon of the [PotionEffect]
 * @see Duration
 * @return The created [PotionEffect]
 * @author Fruxz
 * @since 1.0
 */
fun PotionEffect(type: PotionEffectType, durationTicks: Int, amplifier: Int = 0, ambient: Boolean = true, particles: Boolean = true, icon: Boolean = true) =
    PotionEffect(
        /* type = */ type,
        /* duration = */ durationTicks,
        /* amplifier = */ amplifier,
        /* ambient = */ ambient,
        /* particles = */ particles,
        /* icon = */ icon
    )

/**
 * This class-object like function creates a [PotionEffect] with the given parameters, but has an already some default values.
 * It utilizes the kotlin [Duration] API to convert the given [duration] to the Minecraft ticks.
 * @param type The type of the [PotionEffect]
 * @param duration The duration of the [PotionEffect]
 * @param amplifier The amplifier of the [PotionEffect]
 * @param ambient Whether the [PotionEffect] is ambient or not
 * @param particles Whether the [PotionEffect] has particles or not
 * @param icon The icon of the [PotionEffect]
 * @see Duration
 * @return The created [PotionEffect]
 * @author Fruxz
 * @since 1.0
 */
fun PotionEffect(type: PotionEffectType, duration: Duration = 10.seconds, amplifier: Int = 0, ambient: Boolean = true, particles: Boolean = true, icon: Boolean = true) =
    PotionEffect(type, duration.inWholeMinecraftTicks.toInt(), amplifier, ambient, particles, icon)

/**
 * This function creates a [PotionEffect] with the given parameters, but has an already some default values.
 * This function also applies the [builder] to the created [PotionEffect] and returns the modified result.
 * @param type The type of the [PotionEffect]
 * @param duration The duration of the [PotionEffect]
 * @param amplifier The amplifier of the [PotionEffect]
 * @param ambient Whether the [PotionEffect] is ambient or not
 * @param particles Whether the [PotionEffect] has particles or not
 * @param icon The icon of the [PotionEffect]
 * @see Duration
 * @return The created [PotionEffect]
 * @author Fruxz
 * @since 1.0
 */
fun buildPotionEffect(type: PotionEffectType, duration: Duration = 10.seconds, amplifier: Int = 0, ambient: Boolean = true, particles: Boolean = true, icon: Boolean = true, builder: PotionEffect.() -> Unit) =
    PotionEffect(type, duration, amplifier, ambient, particles, icon).apply(builder)