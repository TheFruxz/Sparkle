package de.jet.paper.extension.paper

import de.jet.jvm.tool.smart.identification.Identity
import org.bukkit.entity.Entity

val Entity.identityObject: Identity<Entity>
	get() = Identity("$uniqueId")