package de.moltenKt.paper.app.component.app

import de.moltenKt.paper.structure.command.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset

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

        fun displayPage(page: Int) {

        }

        concludedExecution {

            displayPage(1)

        }

        branch {

            addContent(CompletionAsset.LONG)

            concludedExecution {

                

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