package dev.fruxz.sparkle.server.command

import dev.fruxz.sparkle.framework.command.annotations.Description
import dev.fruxz.sparkle.framework.command.annotations.Label
import dev.fruxz.sparkle.framework.command.annotations.Usage
import dev.fruxz.sparkle.framework.command.sparkle.BranchContent
import dev.fruxz.sparkle.framework.command.sparkle.Command
import dev.fruxz.sparkle.framework.system.sparklePrefix
import dev.fruxz.sparkle.framework.ux.messaging.transmission
import dev.fruxz.sparkle.server.LocalSparklePlugin
import org.bukkit.entity.Entity

@Label("sparkle")
@Description("A command for everything sparkle")
@Usage("/sparkle debug <on|off>")
internal class SparkleCommand : Command() {
    override fun configure() {

        branch {

            content("debug")
            branch {
                content("on")
                execution {
                    LocalSparklePlugin.debugMode = true
                    reply("Debug mode is now on!")
                }
            }

            branch {
                content("off")
                execution {
                    LocalSparklePlugin.debugMode = false
                    reply("Debug mode is now off!")
                }
            }

        }

        branch {
            content("playLibrarySound")
            branch {
                content(BranchContent.librarySound())
                execution {
                    val sound = get(BranchContent.librarySound())
                    (executor as? Entity)?.let { entity ->
                        sound.play(entity)
                    }
                }
            }
        }

        branch {
            content("sendTransmission")
            branch {
                content("none")
                content(BranchContent.transmissionTheme())
                execution {
                    val theme = when {
                        get(0) == "none" -> null
                        else -> get(BranchContent.transmissionTheme())
                    }

                    "This is a Transmission Test"
                        .transmission(executor, theme = theme)
                        .sparklePrefix()
                        .display()

                }
            }

        }

    }
}