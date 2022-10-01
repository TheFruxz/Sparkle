package de.fruxz.sparkle.app.interchange

import de.fruxz.sparkle.extension.display.notification
import de.fruxz.sparkle.structure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.structure.command.structured.StructuredInterchange
import de.fruxz.sparkle.tool.display.message.Transmission
import de.fruxz.sparkle.tool.display.message.Transmission.Level.APPLIED
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import net.kyori.adventure.text.format.NamedTextColor

internal class DebugModeInterchange : StructuredInterchange("debugmode", buildInterchangeStructure {

    branch {

        addContent("enable")

        concludedExecution {

            de.fruxz.sparkle.app.SparkleApp.debugMode = true

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

            de.fruxz.sparkle.app.SparkleApp.debugMode = false

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
                    text(de.fruxz.sparkle.app.SparkleApp.debugMode.toString()) {
                        color(if (de.fruxz.sparkle.app.SparkleApp.debugMode) NamedTextColor.GREEN else NamedTextColor.RED)
                    },
                    text("!") {
                        color(NamedTextColor.GRAY)
                    }
                )

            }.notification(Transmission.Level.GENERAL, executor).display()

        }

    }

})