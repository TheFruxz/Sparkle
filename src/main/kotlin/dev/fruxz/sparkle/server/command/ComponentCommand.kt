package dev.fruxz.sparkle.server.command

import dev.fruxz.ascend.extension.container.paged
import dev.fruxz.ascend.extension.math.ceilToInt
import dev.fruxz.sparkle.framework.command.annotations.Label
import dev.fruxz.sparkle.framework.command.context.BranchExecutionContext
import dev.fruxz.sparkle.framework.command.sparkle.BranchContent
import dev.fruxz.sparkle.framework.command.sparkle.Command
import dev.fruxz.sparkle.framework.modularity.component.ComponentManager
import dev.fruxz.sparkle.framework.ux.messaging.transmission
import dev.fruxz.stacked.extension.dye
import dev.fruxz.stacked.extension.dyeGray
import dev.fruxz.stacked.extension.newlines
import dev.fruxz.stacked.hover
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor

/*
/ component list
/ component enable      <name>
/ component disable     <name>
/ component restart     <name>
/ component info        <name>
/ component autostart   <name> enable
/ component autostart   <name> disable
 */

@Label("component")
class ComponentCommand : Command() {
    override fun configure() {

        fun BranchExecutionContext.viewPage(index: Int) {
            val components = ComponentManager.registered.toList().paged(10)


            text {

                this + text("<gray>The following components are registered: <yellow>(Page ${index+1} / ${components.pages})")

                newlines(2)

                with(ComponentManager) {
                    components.getPage(index).forEach {

                        val enabled = text("⏻")
                            .color(if (it.first.isRunning) NamedTextColor.GREEN else NamedTextColor.GRAY)
                            .clickEvent(ClickEvent.runCommand("/component %s %s".format(
                                if (it.first.isRunning) "disable" else "enable", // enable or disable
                                it.first.identity, // component identity
                            )))
                            .hoverEvent(text("Click to %s this component".format(
                                if (it.first.isRunning) "disable" else "enable")) // action: enable or disable
                            )

                        val autostart = text("⚡")
                            .color(if (it.first.isRunning) NamedTextColor.GREEN else NamedTextColor.GRAY)
                            .clickEvent(ClickEvent.runCommand("/component autostart %s %s".format(
                                it.first.identity.asString(), // component identity
                                if (it.first.isRunning) "disable" else "enable", // enable or disable
                            )))
                            .hoverEvent(text("Click to %s autostart for this component".format(
                                if (it.first.isRunning) "disable" else "enable" // action: enable or disable
                            )))

                        val experimental = text("☄")
                            .color(if (it.first.isExperimental) NamedTextColor.YELLOW else NamedTextColor.GRAY)
                            .hoverEvent(text("This component is experimental and may not work as expected"))

                        this@text + enabled + " " + autostart + " " + experimental + " <gray>•</gray> " + text(it.first.identity.asString()) dye NamedTextColor.YELLOW

                    }
                }

                newlines(2)

            }.transmission(performer).display()
        }

        branch("list") {
            execution {
                viewPage(0)
            }

            branch {
                content("page") { (1..(ComponentManager.registered.size.toDouble().div(10.0).ceilToInt())) }
                execution {
                    viewPage(get().toIntOrNull()?.minus(1) ?: 0)
                }
            }

        }

        branch("enable") {
            branch {
                content(BranchContent.offlineComponent())
                execution {
                    val component = get(BranchContent.offlineComponent())
                    val task = component.start()

                    text {
                        this + text("Enabling component ").dyeGray()
                        this + text(component.identity.asString()).dye(NamedTextColor.YELLOW).hover { component }
                        this + text("...").dyeGray()
                    }.transmission(performer).display()

                    if (task.await()) {
                        text("Component %s has been enabled".format(component.identity.asString()))
                            .dye(NamedTextColor.GREEN)
                            .transmission(performer)
                            .display()
                    } else {
                        text("Component %s is already enabled".format(component.identity.asString()))
                            .dye(NamedTextColor.YELLOW)
                            .transmission(performer)
                            .display()
                    }

                }
            }
        }

        branch("disable") {
            branch {
                content(BranchContent.runningComponent())
                execution {
                    val component = get(BranchContent.runningComponent())
                    val task = component.stop()

                    text {
                        this + text("Disabling component ").dyeGray()
                        this + text(component.identity.asString()).dye(NamedTextColor.YELLOW).hover { component }
                        this + text("...").dyeGray()
                    }.transmission(performer).display()

                    if (task.await()) {
                        text("Component %s has been disabled".format(component.identity.asString()))
                            .dye(NamedTextColor.GREEN)
                            .transmission(performer)
                            .display()
                    } else {
                        text("Component %s is already disabled".format(component.identity.asString()))
                            .dye(NamedTextColor.YELLOW)
                            .transmission(performer)
                            .display()
                    }

                }
            }
        }

        branch("restart") {
            branch {
                content(BranchContent.runningComponent())
                execution {
                    val component = get(BranchContent.runningComponent())
                    val task = component.stop()

                    text {
                        this + text("Restarting component ").dyeGray()
                        this + text(component.identity.asString()).dye(NamedTextColor.YELLOW).hover { component }
                        this + text("...").dyeGray()
                    }.transmission(performer).display()

                    task.await()

                    text("Component %s has been restarted".format(component.identity.asString()))
                        .dye(NamedTextColor.GREEN)
                        .transmission(performer)
                        .display()

                }
            }
        }

        branch("info") {
            branch {
                content(BranchContent.registeredComponent())
                execution {
                    val component = get(BranchContent.registeredComponent())

                    text("Component %s is %s".format(
                        component.identity.asString(),
                        if (component.isRunning) "enabled" else "disabled"
                    ))
                        .dye(NamedTextColor.GREEN)
                        .transmission(performer)
                        .display()

                }
            }
        }

        branch("autostart") {
            branch {
                content(BranchContent.registeredComponent())
                branch("enable") {
                    execution {
                        val component = getReversed(BranchContent.registeredComponent(), 1)

                        component.isAutoStarting = true

                        text("Component %s has been enabled for autostart".format(component.identity.asString()))
                            .dye(NamedTextColor.GREEN)
                            .transmission(performer)
                            .display()

                    }
                }
                branch("disable") {
                    execution {
                        val component = getReversed(BranchContent.registeredComponent(), 1)

                        component.isAutoStarting = false

                        text("Component %s has been disabled for autostart".format(component.identity.asString()))
                            .dye(NamedTextColor.GREEN)
                            .transmission(performer)
                            .display()

                    }
                }
            }
        }

    }
}