package de.jet.library.extension.paper

import de.jet.library.tool.smart.Identity
import org.bukkit.command.CommandSender

val CommandSender.identityObject: Identity<CommandSender>
	get() = Identity(name)