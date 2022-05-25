package de.moltenKt.paper.app.component.app

import de.moltenKt.core.extension.container.page
import de.moltenKt.core.extension.container.replaceVariables
import de.moltenKt.core.extension.math.limitTo
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.extension.display.message
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.structure.app.cache.CacheDepthLevel
import de.moltenKt.paper.structure.command.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset
import de.moltenKt.paper.structure.command.completion.isNotRequired
import de.moltenKt.paper.structure.command.live.InterchangeAccess
import de.moltenKt.paper.tool.display.message.Transmission
import de.moltenKt.unfold.extension.asStyledComponents
import de.moltenKt.unfold.hover
import de.moltenKt.unfold.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextDecoration

internal class AppInterchange : StructuredInterchange("app", buildInterchangeStructure {

    /*
    /app list
    /app @ <app|app#component> stop
    /app @ <app|app#component> start
    /app @ <app|app#component> restart
    /app @ <app> cache info|clear (<level>)
    /app @ <app|app#component> info
    */

    branch {

        addContent("list")

        fun displayPage(executor: InterchangeExecutor, page: Int) {

            val paged = MoltenCache.registeredApps.page(page - 1, 9)

            val message = buildList {

                add("<gray>List of all running apps <yellow>(Page [p1] of [p2])<gray>:".replaceVariables(
                    "p1" to page,
                    "p2" to paged.pages,
                ))

                paged.content.forEach { app ->
                    add("<gray> ${app.name}")
                }

            }.asStyledComponents.notification(Transmission.Level.INFO, executor).display()

        }

        concludedExecution {

            displayPage(executor, 1)

        }

        branch {

            addContent(CompletionAsset.LONG)

            concludedExecution {
                val page = getInput(restrictiveAsset = CompletionAsset.LONG).limitTo(Int.MIN_VALUE.toLong()..Int.MAX_VALUE.toLong()).toInt()

                displayPage(executor, page)

            }

        }

    }

    branch {

        addContent("at", "@")

        branch {

            addContent(CompletionAsset.APP)

            branch {

                addContent("start")

                concludedExecution {
                    val targetApp = getInput(slot = 1, restrictiveAsset = CompletionAsset.APP)

                    text("Starting the app '${targetApp.appLabel}'...")
                        .color(NamedTextColor.GRAY)
                        .hoverEvent(text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        .message(executor).display()

                    targetApp.requestStart()

                    text {
                        text("Successfully") {
                            style(Style.style(NamedTextColor.GREEN, TextDecoration.BOLD))
                        }
                        text(" started the '") {
                            color(NamedTextColor.GRAY)
                        }
                        text(targetApp.appLabel) {
                            color(NamedTextColor.GOLD)
                            hover {
                                text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY)
                            }
                        }
                        text("' component!") {
                            color(NamedTextColor.GRAY)
                        }
                    }.notification(Transmission.Level.APPLIED, executor).display()

                }

            }

            branch {

                addContent("stop")

                concludedExecution {
                    val targetApp = getInput(slot = 1, restrictiveAsset = CompletionAsset.APP)

                    text("Stopping the app '${targetApp.appLabel}'...")
                        .color(NamedTextColor.GRAY)
                        .hoverEvent(text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        .message(executor).display()

                    targetApp.requestStop()

                    text {
                        text("Successfully") {
                            style(Style.style(NamedTextColor.GREEN, TextDecoration.BOLD))
                        }
                        text(" stopped the '") {
                            color(NamedTextColor.GRAY)
                        }
                        text(targetApp.appLabel) {
                            color(NamedTextColor.GOLD)
                            hover {
                                text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY)
                            }
                        }
                        text("' component!") {
                            color(NamedTextColor.GRAY)
                        }
                    }.notification(Transmission.Level.APPLIED, executor).display()

                }

            }

            branch {

                addContent("restart")

                concludedExecution {
                    val targetApp = getInput(slot = 1, restrictiveAsset = CompletionAsset.APP)

                    text("Restarting the app '${targetApp.appLabel}'...")
                        .color(NamedTextColor.GRAY)
                        .hoverEvent(text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        .message(executor).display()

                    targetApp.requestStop()
                    targetApp.requestStart()

                    text {
                        text("Successfully") {
                            style(Style.style(NamedTextColor.GREEN, TextDecoration.BOLD))
                        }
                        text(" restarted the '") {
                            color(NamedTextColor.GRAY)
                        }
                        text(targetApp.appLabel) {
                            color(NamedTextColor.GOLD)
                            hover {
                                text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY)
                            }
                        }
                        text("' component!") {
                            color(NamedTextColor.GRAY)
                        }
                    }.notification(Transmission.Level.APPLIED, executor).display()

                }

            }

            branch {

                addContent("info")

                concludedExecution {
/*
                         * - Displayname
                         * - Identity
                         * - Active since
                         * - Amount of Components
                         * - Amount of Interchanges
                         * - Amount of Services
                         * - Amount of Sandboxes
                         * - (maybe) Amount of Scope-Jobs
                         */

                    "requested info!"
                        .notification(Transmission.Level.APPLIED, executor).display()

                }

            }

            branch {

                addContent("clear-cache")

                fun cacheClear(interchangeAccess: InterchangeAccess, level: CacheDepthLevel) {
                    val targetApp = interchangeAccess.getInput(slot = 1, restrictiveAsset = CompletionAsset.APP)

                    text("Starting cache-clear of app '${targetApp.appLabel}'...")
                        .color(NamedTextColor.GRAY)
                        .hoverEvent(text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        .message(interchangeAccess.executor).display()

                    debugLog("${interchangeAccess.executor.name} is clearing cache for '${targetApp.identity}' at level '$level'...")

                    targetApp.appCache.dropEverything(level)

                    debugLog("${interchangeAccess.executor.name} cleared cache for '${targetApp.identity}' at level '$level'!")

                    text {

                        text("Successfully") {
                            color(NamedTextColor.GREEN)
                        }

                        text(" cleared cache for '") {
                            color(NamedTextColor.GRAY)
                        }

                        text(targetApp.appLabel) {
                            color(NamedTextColor.GOLD)
                            hoverEvent(Component.text("App-Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        }

                        text("' at level '") {
                            color(NamedTextColor.GRAY)
                        }

                        text("$level") {
                            color(NamedTextColor.YELLOW)
                        }

                        text("'!") {
                            color(NamedTextColor.GRAY)
                        }

                    }.notification(Transmission.Level.APPLIED, interchangeAccess.executor)
                        .display()

                }

                concludedExecution {

                    cacheClear(this, CacheDepthLevel.CLEAR)

                }

                branch {

                    isNotRequired()

                    addContent(CompletionAsset.CACHE_DEPTH_LEVEL)

                    concludedExecution {
                        val level = getInput(restrictiveAsset = CompletionAsset.CACHE_DEPTH_LEVEL)

                        cacheClear(this, level)

                    }

                }

            }

        }

        branch {

            addContent(CompletionAsset.COMPONENT)

            branch {

                addContent("start")

                concludedExecution {
                    val component = getInput(1, CompletionAsset.COMPONENT)

                    text("Starting the component '${component.identity}'...")
                        .color(NamedTextColor.GRAY)
                        .message(executor).display()

                    component.requestStart()

                    text {
                        text("Successfully") {
                            style(Style.style(NamedTextColor.GREEN, TextDecoration.BOLD))
                        }
                        text(" stopped the '") {
                            color(NamedTextColor.GRAY)
                        }
                        text(component.identity) {
                            color(NamedTextColor.GOLD)
                        }
                        text("' component!") {
                            color(NamedTextColor.GRAY)
                        }
                    }.notification(Transmission.Level.APPLIED, executor).display()

                }

            }

            branch {

                addContent("stop")

                concludedExecution {
                    val component = getInput(1, CompletionAsset.COMPONENT)

                    text("Stopping the component '${component.identity}'...")
                        .color(NamedTextColor.GRAY)
                        .message(executor).display()

                    component.requestStop()

                    text {
                        text("Successfully") {
                            style(Style.style(NamedTextColor.GREEN, TextDecoration.BOLD))
                        }
                        text(" stopped the '") {
                            color(NamedTextColor.GRAY)
                        }
                        text(component.identity) {
                            color(NamedTextColor.GOLD)
                        }
                        text("' component!") {
                            color(NamedTextColor.GRAY)
                        }
                    }.notification(Transmission.Level.APPLIED, executor).display()

                }

            }

            branch {

                addContent("restart")

                concludedExecution {
                    val component = getInput(1, CompletionAsset.COMPONENT)

                    text("Restarting the component '${component.identity}'...")
                        .color(NamedTextColor.GRAY)
                        .message(executor).display()

                    component.requestStop()
                    component.requestStart()

                    text {
                        text("Successfully") {
                            style(Style.style(NamedTextColor.GREEN, TextDecoration.BOLD))
                        }
                        text(" restarted the '") {
                            color(NamedTextColor.GRAY)
                        }
                        text(component.identity) {
                            color(NamedTextColor.GOLD)
                        }
                        text("' component!") {
                            color(NamedTextColor.GRAY)
                        }
                    }.notification(Transmission.Level.APPLIED, executor).display()

                }

            }

            branch {

                addContent("info")

                concludedExecution {

                    /*
                    - Name
                    - Identity (MoltenKT:Service)
                    - Active since
                    - Status
                    - Auto-Start behavior
                    - Amount of interchanges
                    - Amount of components
                    - Amount of services
                    - Amount of sandboxes
                     */

                    "requested info!"
                        .notification(Transmission.Level.APPLIED, executor).display()

                }

            }

        }

    }

})