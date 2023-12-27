package dev.fruxz.sparkle.framework.command.argument

import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import dev.fruxz.ascend.extension.tryOrNull
import dev.fruxz.sparkle.framework.system.*
import dev.fruxz.sparkle.framework.ux.effect.sound.SoundLibrary
import dev.fruxz.sparkle.framework.ux.messaging.Transmission
import dev.fruxz.sparkle.server.component.sandox.SandBoxManager
import org.bukkit.Material
import org.bukkit.entity.EntityType
import java.util.*

object ArgumentStore {

    // Bukkit

    val ONLINE_PLAYER = buildArgumentType {
        parse {
            val name = it.readString()

            return@parse playerOrNull(name) ?: tryOrNull { playerOrNull(UUID.fromString(name)) } ?: throw SimpleCommandExceptionType { "Unknown player" }.createWithContext(it)
        }
        suggest { _, builder ->

            onlinePlayers.forEach { builder.suggest(it.name) }

            builder.buildFuture()
        }
    }

    val OFFLINE_PLAYER = buildArgumentType {
        parse {
            val name = it.readString()

            return@parse offlinePlayerIfCachedOrNull(name) ?: tryOrNull { offlinePlayer(UUID.fromString(name)) } ?: throw SimpleCommandExceptionType { "Unknown player" }.createWithContext(it)
        }
        suggest { _, builder ->

            offlinePlayers.forEach { builder.suggest(it.name) }

            builder.buildFuture()
        }
    }

    val MATERIAL = buildArgumentType {
        parse {
            val name = it.readString()

            return@parse Material.matchMaterial(name) ?: throw SimpleCommandExceptionType { "Unknown or incomplete material name" }.createWithContext(it)
        }
        suggest { _, builder ->

                Material.entries.forEach { builder.suggest(it.key().asString()) }

                builder.buildFuture()
        }
    }

    val ENTITY_TYPE = buildArgumentType {
        parse {
            val name = it.readString()

            return@parse tryOrNull { EntityType.valueOf(name.split(":").last().uppercase()) } ?: throw SimpleCommandExceptionType { "Unknown or incomplete entity type name" }.createWithContext(it)
        }
        suggest { _, builder ->

            EntityType.entries.forEach { builder.suggest(it.key().asString()) }

            builder.buildFuture()
        }
    }

    val LIBRARY_SOUND = buildArgumentType {
        parse {
            val name = it.readString()

            return@parse tryOrNull { SoundLibrary.valueOf(name.uppercase()) } ?: throw SimpleCommandExceptionType { "Unknown or incomplete library sound name" }.createWithContext(it)
        }
        suggest { _, builder ->

            SoundLibrary.entries.forEach { builder.suggest(it.name) } // TODO add feature to register custom sounds of every other plugin and use keys to identify them

            builder.buildFuture()
        }
    }

    val TRANSMISSION_THEME = buildArgumentType {
        parse {
            val name = it.readString()

            return@parse tryOrNull { Transmission.Theme.Default.valueOf(name.uppercase()) } ?: throw SimpleCommandExceptionType { "Unknown or incomplete transmission theme name" }.createWithContext(it)
        }
        suggest { _, builder ->

            Transmission.Theme.Default.entries.forEach { builder.suggest(it.name) }

            builder.buildFuture()
        }
    }

    val SANDBOX = buildArgumentType {
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