package de.moltenKt.paper.app.interchange

import de.moltenKt.paper.app.MoltenApp
import de.moltenKt.paper.app.MoltenApp.Companion
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.structured.StructuredInterchange
import de.moltenKt.paper.tool.display.message.Transmission
import de.moltenKt.unfold.plus
import de.moltenKt.unfold.text
import net.kyori.adventure.text.format.NamedTextColor

internal class DebugModeInterchange : StructuredInterchange("debugmode", buildInterchangeStructure {

    branch {

        addContent("enable")

        concludedExecution {

            de.moltenKt.paper.app.MoltenApp.debugMode = true

            text("Debug mode enabled")
                .color(NamedTextColor.GREEN)
                .notification(Transmission.Level.APPLIED, executor)

        }

    }

    branch {

        addContent("disable")

        concludedExecution {

            de.moltenKt.paper.app.MoltenApp.debugMode = false

            text("Debug mode disabled")
                .color(NamedTextColor.RED)
                .notification(Transmission.Level.APPLIED, executor)

        }

    }

    branch {

        addContent("status")

        concludedExecution {

            text {

                this + text("The debug mode is currently set to ") {
                    color(NamedTextColor.GRAY)
                }
                this + text(MoltenApp.debugMode.toString()) {
                    color(if (MoltenApp.debugMode) NamedTextColor.GREEN else NamedTextColor.RED)
                }
                this + text("!") {
                    color(NamedTextColor.GRAY)
                }

            }.notification(Transmission.Level.INFO, executor).display()

        }

    }

})