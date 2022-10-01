package de.fruxz.sparkle.extension.paper

import de.fruxz.sparkle.extension.interchange.InterchangeExecutor
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

/**
 * This computational value is used to cast the [InterchangeExecutor] to a [Player]
 * @author Fruxz
 * @since 1.0
 */
val <T : InterchangeExecutor> T.asPlayer: Player
    get() = this as Player

/**
 * This computational value is used to cast the [InterchangeExecutor] to a [Player]
 * or null
 * @author Fruxz
 * @since 1.0
 */
val <T : InterchangeExecutor> T.asPlayerOrNull: Player?
    get() = this as? Player

/**
 * This computational value is used to cast the [InterchangeExecutor] to a [ConsoleCommandSender]
 * @author Fruxz
 * @since 1.0
 */
val <T : InterchangeExecutor> T.asConsole: ConsoleCommandSender
    get() = this as ConsoleCommandSender

/**
 * This computational value is used to cast the [InterchangeExecutor] to a [ConsoleCommandSender]
 * or null
 * @author Fruxz
 * @since 1.0
 */
val <T : InterchangeExecutor> T.asConsoleOrNull: ConsoleCommandSender?
    get() = this as? ConsoleCommandSender