package de.jet.minecraft.extension.paper

import de.jet.library.tool.smart.identification.Identity
import org.bukkit.command.CommandSender

val CommandSender.identityObject: Identity<CommandSender>
	get() = Identity(name)