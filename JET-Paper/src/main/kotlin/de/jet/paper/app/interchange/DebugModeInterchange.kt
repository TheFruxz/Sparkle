package de.jet.paper.app.interchange

import de.jet.paper.app.JetApp
import de.jet.paper.extension.display.notification
import de.jet.paper.structure.command.StructuredInterchange
import de.jet.paper.structure.command.completion.buildInterchangeStructure
import de.jet.paper.tool.display.message.Transmission
import de.jet.unfold.text
import net.kyori.adventure.text.format.NamedTextColor

class DebugModeInterchange : StructuredInterchange("debugmode", buildInterchangeStructure {

    branch {

        addContent("enable")

        concludedExecution {

            JetApp.debugMode = true

            text("Debug mode enabled")
                .color(NamedTextColor.GREEN)
                .notification(Transmission.Level.APPLIED, executor)

        }

    }

    branch {

        addContent("disable")

        concludedExecution {

            JetApp.debugMode = false

            text("Debug mode disabled")
                .color(NamedTextColor.RED)
                .notification(Transmission.Level.APPLIED, executor)

        }

    }

    branch {

        addContent("status")

        concludedExecution {

            text {

                text("The debug mode is currently set to ") {
                    color(NamedTextColor.GRAY)
                }
                text(JetApp.debugMode.toString()) {
                    color(if(JetApp.debugMode) NamedTextColor.GREEN else NamedTextColor.RED)
                }
                text("!") {
                    color(NamedTextColor.GRAY)
                }

            }.notification(Transmission.Level.INFO, executor).display()

        }

    }

})