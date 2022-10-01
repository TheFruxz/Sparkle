package de.fruxz.sparkle.extension.data

import de.fruxz.sparkle.tool.effect.CustomEffect
import org.bukkit.entity.LivingEntity

fun effectOf(action: (target: LivingEntity) -> Unit, async: Boolean = false) =
	object : CustomEffect {
		override val action: (target: LivingEntity) -> Unit = action
		override val async = async
	}