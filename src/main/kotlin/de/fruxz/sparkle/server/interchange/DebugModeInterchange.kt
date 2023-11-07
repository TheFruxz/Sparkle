package de.fruxz.sparkle.server.interchange

import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredInterchange
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance.Companion.APPLIED
import de.fruxz.sparkle.server.SparkleApp
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
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

            }.notification(TransmissionAppearance.GENERAL, executor).display()

        }

    }

})