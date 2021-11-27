package de.jet.paper.extension.paper

import de.jet.jvm.tool.smart.identification.Identity
import org.bukkit.command.CommandSender

val CommandSender.identityObject: Identity<CommandSender>
	get() = Identity(name)