package de.fruxz.sparkle.server.interchange

import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredInterchange
import de.fruxz.sparkle.framework.util.extension.visual.notification
import de.fruxz.sparkle.framework.util.visual.message.Transmission
import de.fruxz.sparkle.framework.util.visual.message.Transmission.Level.APPLIED
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import net.kyori.adventure.text.format.NamedTextColor

internal class DebugModeInterchange : StructuredInterchange("debugmode", buildInterchangeStructure {

    branch {

        addContent("enable")

        concludedExecution {

            SparkleApp.debugMode = true

            text {
                this + text("The ") {
                    color(NamedTextColor.GRAY)
                }
                this + text("DebugMode") {
                    color(NamedTextColor.YELLOW)
                }
                this + text(" got ") {
                    color(NamedTextColor.GRAY)
                }
                this + text("enabled") {
                    color(NamedTextColor.GOLD)
                }
                this + text("!") {
                    color(NamedTextColor.GRAY)
                }
            }.notification(APPLIED, executor).display()

        }

    }

    branch {

        addContent("disable")

        concludedExecution {

            SparkleApp.debugMode = false

            text {
                this + text("The ") {
                    color(NamedTextColor.GRAY)
                }
                this + text("DebugMode") {
                    color(NamedTextColor.YELLOW)
                }
                this + text(" got ") {
                    color(NamedTextColor.GRAY)
                }
                this + text("disabled") {
                    color(NamedTextColor.GOLD)
                }
                this + text("!") {
                    color(NamedTextColor.GRAY)
                }
            }.notification(APPLIED, executor).display()

        }

    }

    branch {

        addContent("status")

        concludedExecution {

            text {

                append(
                    text("The debug mode is currently set to ") {
                        color(NamedTextColor.GRAY)
                    },
                    text(SparkleApp.debugMode.toString()) {
                        color(if (SparkleApp.debugMode) NamedTextColor.GREEN else NamedTextColor.RED)
                    },
                    text("!") {
                        color(NamedTextColor.GRAY)
                    }
                )

            }.notification(Transmission.Level.GENERAL, executor).display()

        }

    }

})