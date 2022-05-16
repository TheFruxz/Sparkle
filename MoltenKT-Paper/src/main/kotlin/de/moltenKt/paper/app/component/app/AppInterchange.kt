package de.moltenKt.paper.app.component.app

import de.moltenKt.core.extension.container.page
import de.moltenKt.core.extension.container.replaceVariables
import de.moltenKt.core.extension.math.INT_RANGE
import de.moltenKt.core.extension.math.limitTo
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.structure.command.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset
import de.moltenKt.paper.tool.display.message.Transmission
import de.moltenKt.unfold.extension.asStyledComponents
import org.bukkit.Bukkit

internal class AppInterchange : StructuredInterchange("app", buildInterchangeStructure {

    /*
    /app list
    /app stop <app|app#component>
    /app start <app|app#component>
    /app restart <app|app#component>
    /app cache <app> info|clear (<level>)
    /app info <app|app#component>

    /app list
    /app at/@ <app|component> start/stop/restart/cache/inf
    */

    branch {

        addContent("list")

        fun displayPage(executor: InterchangeExecutor, page: Int) {

            val paged = MoltenCache.registeredApps.page(page - 1, 9)

            val message = buildList {

                add("<gray>List of all running apps: (Page [p1] of [p2])</gray>".replaceVariables(
                    "p1" to page,
                    "p2" to paged.pages,
                ))

                paged.content.forEach { app ->
                    add("<gray>- ${app.name}</gray>\uD83D\uDCE6")
                }

            }.asStyledComponents.notification(Transmission.Level.INFO)

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

            addContent("start")

            concludedExecution {

            }

        }

        branch {

            addContent("stop")

            concludedExecution {

            }

        }

        branch {

            addContent("restart")

            concludedExecution {

            }

        }

        branch {

            addContent("cache")

            branch {

                addContent("info")

                concludedExecution {

                }

            }

            branch {

                addContent("clear")

                concludedExecution {

                }

            }

        }

        branch {

            addContent("info")

            concludedExecution {

            }

        }

    }

})