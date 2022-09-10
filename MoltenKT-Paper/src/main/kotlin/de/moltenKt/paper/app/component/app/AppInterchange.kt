package de.moltenKt.paper.app.component.app

import de.moltenKt.core.extension.container.mapToString
import de.moltenKt.core.extension.container.page
import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.extension.math.limitTo
import de.moltenKt.paper.Constants.ENTRIES_PER_PAGE
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.extension.display.message
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.structure.app.cache.CacheDepthLevel
import de.moltenKt.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset
import de.moltenKt.paper.structure.command.completion.isNotRequired
import de.moltenKt.paper.structure.command.live.InterchangeAccess
import de.moltenKt.paper.structure.command.structured.StructuredInterchange
import de.moltenKt.paper.tool.display.message.Transmission
import de.moltenKt.unfold.buildComponent
import de.moltenKt.unfold.extension.asStyledComponents
import de.moltenKt.unfold.hover
import de.moltenKt.unfold.plus
import de.moltenKt.unfold.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit

internal class AppInterchange : StructuredInterchange("app", buildInterchangeStructure {

    branch {

        addContent("list")

        fun displayPage(executor: InterchangeExecutor, page: Int) {

            val paged = MoltenCache.registeredApps.page(page - 1, ENTRIES_PER_PAGE)

            buildComponent {

                this + text("List of all running apps") {
                    plus(NamedTextColor.GRAY)
                }
                this + text(" (Page $page of ${paged.pages})") {
                    plus(NamedTextColor.YELLOW)
                    hover {
                        text("${paged.content.size} App" + (if (paged.content.size == 1) "" else "s") + " listed") {
                            plus(NamedTextColor.GRAY)
                        }
                    }
                }
                this + text(":") {
                    plus(NamedTextColor.GRAY)
                }

                this + Component.newline() + text("⏻ Power; ⌘ Naggable; ⏹ API-Compatible") {
                    plus(NamedTextColor.GRAY)
                }

                this + Component.newline() + Component.newline()

                paged.content.forEach { app ->
                    this + text(if (app.isEnabled) "⏻" else "⭘") {
                        plus(if (app.isEnabled) NamedTextColor.GREEN else NamedTextColor.GRAY)
                        hover {
                            buildComponent {
                                this + text("Power-Indicator: ") {
                                    plus(Style.style(NamedTextColor.BLUE, TextDecoration.BOLD))
                                }
                                this + Component.newline()
                                this + text("Indicates, if the plugin is currently enabled & running") {
                                    plus(NamedTextColor.YELLOW)
                                }
                                this + Component.newline() + Component.newline()
                                this + text("CLICK").style(Style.style(NamedTextColor.GREEN, TextDecoration.BOLD)) +
                                        text(" to toggle the state of the app") {
                                            plus(NamedTextColor.GRAY)
                                        }
                            }
                        }
                        clickEvent(ClickEvent.suggestCommand(
                            if (app.isEnabled) "/app @ ${app.key()} stop" else "/app @ ${app.key()} start"
                        ))
                    }
                    this + text(" ⌘") {
                        color(if (app.isNaggable) NamedTextColor.GREEN else NamedTextColor.GRAY)
                        hover {
                            buildComponent {
                                this + text("Naggable-Indicator: ") {
                                    plus(Style.style(NamedTextColor.BLUE, TextDecoration.BOLD))
                                }
                                this + Component.newline()
                                this + text("Indicates, if we can still nag to the logs about things") {
                                    plus(NamedTextColor.YELLOW)
                                }
                            }
                        }
                    }
                    this + text(" ⏹") {
                        plus(if (Bukkit.getMinecraftVersion().startsWith("" + app.description.apiVersion)) NamedTextColor.GREEN else NamedTextColor.GRAY)
                        hover {
                            buildComponent {
                                this + text("Compatibility-Indicator: ") {
                                    plus(Style.style(NamedTextColor.BLUE, TextDecoration.BOLD))
                                }
                                this + Component.newline()
                                this + text("Indicates, if the plugins target version is compatible with the current server version") {
                                    plus(NamedTextColor.YELLOW)
                                }
                                this + Component.newline()
                                this + Component.newline() + text("Apps target version: ") {
                                    plus(NamedTextColor.GRAY)
                                } + text(app.description.apiVersion ?: "None") {
                                    plus(NamedTextColor.GREEN)
                                }
                                this + Component.newline() + text("Server version: ") {
                                    plus(NamedTextColor.GRAY)
                                } + text(Bukkit.getMinecraftVersion()) {
                                    plus(NamedTextColor.GREEN)
                                }

                            }
                        }
                    }
                    this + text(" | ") {
                        plus(NamedTextColor.DARK_GRAY)
                    }
                    this + text(app.label) {
                        plus(NamedTextColor.YELLOW)
                        hover {
                            buildComponent {
                                this + text("Identity: ") {
                                    plus(Style.style(NamedTextColor.BLUE, TextDecoration.BOLD))
                                }
                                this + Component.newline()
                                this + text("The label is used to display the app in lists and information, the identity is used to identify the app in the system") {
                                    plus(NamedTextColor.YELLOW)
                                }
                                this + Component.newline()
                                this + Component.newline() + text("Label: ") {
                                    plus(NamedTextColor.GRAY)
                                } + text(app.label) {
                                    plus(NamedTextColor.GREEN)
                                }
                                this + Component.newline() + text("Identity: ") {
                                    plus(NamedTextColor.GRAY)
                                } + text(app.key().asString()) {
                                    plus(NamedTextColor.GREEN)
                                }
                            }
                        }
                    }
                    this + text(" ") {
                        plus(NamedTextColor.GRAY)
                    }
                    this + text(app.description.version) {
                        plus(NamedTextColor.GRAY)
                    }
                    this + text(" → ") {
                        plus(NamedTextColor.GRAY)
                    }
                    this + text(app.description.apiVersion ?: "None") {
                        plus(NamedTextColor.GRAY)
                    }
                    this + Component.newline()
                }

                this + Component.newline()

            }.notification(Transmission.Level.INFO, executor).display()

        }

        concludedExecution {

            displayPage(executor, 1)

        }

        branch {

            addContent(
                CompletionAsset<Long>(
                    vendor = system,
                    thisIdentity = "appPage",
                    refreshing = true,
                    supportedInputType = listOf(InterchangeStructureInputRestriction.LONG),
                    generator = {
                        (1..ceilToInt(MoltenCache.registeredApps.size.toDouble() / ENTRIES_PER_PAGE)).mapToString()
                    }
                )
            )

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

                    text("Starting the app '${targetApp.label}'...")
                        .color(NamedTextColor.GRAY)
                        .hoverEvent(text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        .message(executor).display()

                    targetApp.requestStart()

                    text {
                        this + text("Successfully") {
                            style(Style.style(NamedTextColor.GREEN, TextDecoration.BOLD))
                        }
                        this + text(" started the '") {
                            color(NamedTextColor.GRAY)
                        }
                        this + text(targetApp.label) {
                            color(NamedTextColor.GOLD)
                            hover {
                                text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY)
                            }
                        }
                        this + text("' component!") {
                            color(NamedTextColor.GRAY)
                        }
                    }.notification(Transmission.Level.APPLIED, executor).display()

                }

            }

            branch {

                addContent("stop")

                concludedExecution {
                    val targetApp = getInput(slot = 1, restrictiveAsset = CompletionAsset.APP)

                    text("Stopping the app '${targetApp.label}'...")
                        .color(NamedTextColor.GRAY)
                        .hoverEvent(text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        .message(executor).display()

                    targetApp.requestStop()

                    text {
                        this + text("Successfully") {
                            style(Style.style(NamedTextColor.GREEN, TextDecoration.BOLD))
                        }
                        this + text(" stopped the '") {
                            color(NamedTextColor.GRAY)
                        }
                        this + text(targetApp.label) {
                            color(NamedTextColor.GOLD)
                            hover {
                                text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY)
                            }
                        }
                        this + text("' component!") {
                            color(NamedTextColor.GRAY)
                        }
                    }.notification(Transmission.Level.APPLIED, executor).display()

                }

            }

            branch {

                addContent("restart")

                concludedExecution {
                    val targetApp = getInput(slot = 1, restrictiveAsset = CompletionAsset.APP)

                    text("Restarting the app '${targetApp.label}'...")
                        .color(NamedTextColor.GRAY)
                        .hoverEvent(text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        .message(executor).display()

                    targetApp.requestStop()
                    targetApp.requestStart()

                    text {
                        this + text("Successfully") {
                            style(Style.style(NamedTextColor.GREEN, TextDecoration.BOLD))
                        }
                        this + text(" restarted the '") {
                            color(NamedTextColor.GRAY)
                        }
                        this + text(targetApp.label) {
                            color(NamedTextColor.GOLD)
                            hover {
                                text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY)
                            }
                        }
                        this + text("' component!") {
                            color(NamedTextColor.GRAY)
                        }
                    }.notification(Transmission.Level.APPLIED, executor).display()

                }

            }

            branch {

                addContent("info")

                concludedExecution {
                    val targetApp = getInput(slot = 1, restrictiveAsset = CompletionAsset.APP)

                    listOf(
                        "<gray>Display-Name <dark_gray>| <yellow>${targetApp.label}",
                        "<gray>Identity <dark_gray>| <yellow>${targetApp.identity}",
                        "<gray>Active since <dark_gray>| <yellow>${targetApp.activeSince}",
                        "<gray>Components <dark_gray>| <yellow>${MoltenCache.registeredComponents.filter { it.vendorIdentity == targetApp.identityObject }.size} Components",
                        "<gray>Interchanges <dark_gray>| <yellow>${MoltenCache.registeredInterchanges.filter { it.vendorIdentity == targetApp.identityObject }.size} Interchanges",
                        "<gray>Services <dark_gray>| <yellow>${MoltenCache.registeredServices.filter { it.vendorIdentity == targetApp.identityObject }.size} Services",
                        "<gray>SandBoxes <dark_gray>| <yellow>${MoltenCache.registeredSandBoxes.filter { it.vendorIdentity == targetApp.identityObject }.size} SandBoxes",
                    ).asStyledComponents.notification(Transmission.Level.INFO, executor).display()

                }

            }

            branch {

                addContent("clear-cache")

                fun cacheClear(interchangeAccess: InterchangeAccess<*>, level: CacheDepthLevel) {
                    val targetApp = interchangeAccess.getInput(slot = 1, restrictiveAsset = CompletionAsset.APP)

                    text("Starting cache-clear of app '${targetApp.label}'...")
                        .color(NamedTextColor.GRAY)
                        .hoverEvent(text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        .message(interchangeAccess.executor).display()

                    debugLog("${interchangeAccess.executor.name} is clearing cache for '${targetApp.identity}' at level '$level'...")

                    targetApp.appCache.dropEverything(level)

                    debugLog("${interchangeAccess.executor.name} cleared cache for '${targetApp.identity}' at level '$level'!")

                    text {

                        this + text("Successfully") {
                            color(NamedTextColor.GREEN)
                        }

                        this + text(" cleared cache for '") {
                            color(NamedTextColor.GRAY)
                        }

                        this + text(targetApp.label) {
                            color(NamedTextColor.GOLD)
                            hoverEvent(Component.text("App-Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        }

                        this + text("' at level '") {
                            color(NamedTextColor.GRAY)
                        }

                        this + text("$level") {
                            color(NamedTextColor.YELLOW)
                        }

                        this + text("'!") {
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

    }

})