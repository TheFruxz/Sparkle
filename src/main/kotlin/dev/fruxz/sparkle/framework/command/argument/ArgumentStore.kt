package dev.fruxz.sparkle.framework.command.argument

import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import dev.fruxz.sparkle.server.component.sandox.SandBoxManager

object ArgumentStore {

    val SANDBOX = buildArgument {
        parse {
            val name = it.readString()

            return@parse SandBoxManager[name] ?: throw CommandSyntaxException(SimpleCommandExceptionType { "Unknown sandbox" }) { "Unknown sandbox" }
        }
    }

}