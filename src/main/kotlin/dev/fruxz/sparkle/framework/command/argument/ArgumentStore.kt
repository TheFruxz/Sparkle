package dev.fruxz.sparkle.framework.command.argument

import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import dev.fruxz.sparkle.server.component.sandox.SandBoxManager

object ArgumentStore {

    val SANDBOX = buildArgument {
        parse {
            val name = it.readString()

            return@parse SandBoxManager[name] ?: throw SimpleCommandExceptionType { "Unknown or incomplete sandbox name" }.createWithContext(it)
        }
        suggest { _, builder ->

            SandBoxManager.sandboxes.keys.forEach { builder.suggest(it) }

            builder.buildFuture()
        }
    }

}