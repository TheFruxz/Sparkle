package dev.fruxz.sparkle.framework.command.argument

import com.mojang.brigadier.arguments.ArgumentType
import dev.fruxz.brigadikt.domain.ArgumentTypeBuilder
import org.bukkit.command.CommandSender

fun <T> buildArgumentType(builder: ArgumentTypeBuilder<T, CommandSender>.() -> Unit): ArgumentType<T> =
    ArgumentTypeBuilder<T, CommandSender>().apply(builder).produce()
