package de.fruxz.sparkle.framework.effect

import de.fruxz.sparkle.framework.extension.onlinePlayers
import org.bukkit.entity.LivingEntity

interface CustomEffect {

	val action: (target: LivingEntity) -> Unit

	val async: Boolean

	fun display(target: LivingEntity) = action(target)

	fun broadcastPlayer() = onlinePlayers.forEach(action)

}