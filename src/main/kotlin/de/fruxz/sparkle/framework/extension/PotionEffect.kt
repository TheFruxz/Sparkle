package de.fruxz.sparkle.framework.extension

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

fun PotionEffect.copy(
	type: PotionEffectType = this.type,
	amplifier: Int = this.amplifier,
	duration: Int = this.duration,
	ambient: Boolean = this.isAmbient,
	particles: Boolean = this.hasParticles(),
	icon: Boolean = this.hasIcon(),
) = PotionEffect(type, duration, amplifier, ambient, particles, icon)