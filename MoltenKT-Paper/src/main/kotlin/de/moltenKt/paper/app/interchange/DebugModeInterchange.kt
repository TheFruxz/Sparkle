package de.moltenKt.paper.app.interchange

import de.moltenKt.paper.app.MoltenApp
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.structured.StructuredInterchange
import de.moltenKt.paper.tool.display.message.Transmission
import de.moltenKt.paper.tool.display.message.Transmission.Level.APPLIED
import de.moltenKt.unfold.plus
import de.moltenKt.unfold.text
import net.kyori.adventure.text.format.NamedTextColor

internal class DebugModeInterchange : StructuredInterchange("debugmode", buildInterchangeStructure {

    branch {

        addContent("enable")

        concludedExecution {

            MoltenApp.debugMode = true

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

            MoltenApp.debugMode = false

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
                    text(MoltenApp.debugMode.toString()) {
                        color(if (MoltenApp.debugMode) NamedTextColor.GREEN else NamedTextColor.RED)
                    },
                    text("!") {
                        color(NamedTextColor.GRAY)
                    }
                )

            }.notification(Transmission.Level.GENERAL, executor).display()

        }

    }

})