package de.jet.minecraft.extension.paper

import de.jet.library.tool.smart.identification.Identity
import org.bukkit.entity.Entity

val Entity.identityObject: Identity<Entity>
	get() = Identity("$uniqueId")