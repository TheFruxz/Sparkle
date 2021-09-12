package de.jet.minecraft.tool.effect

import de.jet.library.extension.paper.onlinePlayers
import org.bukkit.entity.LivingEntity

interface JetEffect {

	val action: (target: LivingEntity) -> Unit

	val async: Boolean

	fun display(target: LivingEntity) = action(target)

	fun broadcastPlayer() = onlinePlayers.forEach(action)

}