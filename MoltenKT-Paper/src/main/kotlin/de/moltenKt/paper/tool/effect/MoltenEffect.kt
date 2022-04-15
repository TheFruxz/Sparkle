package de.moltenKt.paper.tool.effect

import de.moltenKt.paper.extension.paper.onlinePlayers
import org.bukkit.entity.LivingEntity

interface MoltenEffect {

	val action: (target: LivingEntity) -> Unit

	val async: Boolean

	fun display(target: LivingEntity) = action(target)

	fun broadcastPlayer() = onlinePlayers.forEach(action)

}