package de.jet.paper.extension.data

import de.jet.paper.tool.effect.JetEffect
import org.bukkit.entity.LivingEntity

fun effectOf(action: (target: LivingEntity) -> Unit, async: Boolean = false) =
	object : JetEffect {
		override val action: (target: LivingEntity) -> Unit = action
		override val async = async
	}