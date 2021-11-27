package de.jet.minecraft.extension.data

import de.jet.minecraft.tool.effect.JetEffect
import org.bukkit.entity.LivingEntity

fun effectOf(action: (target: LivingEntity) -> Unit, async: Boolean = false) =
	object : JetEffect {
		override val action: (target: LivingEntity) -> Unit = action
		override val async = async
	}