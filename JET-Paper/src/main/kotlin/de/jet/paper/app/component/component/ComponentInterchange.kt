package de.jet.paper.app.component.component

import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.paper.app.JetCache
import de.jet.paper.app.JetData
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.interchange.InterchangeExecutor
import de.jet.paper.extension.lang
import de.jet.paper.structure.command.InterchangeResult
import de.jet.paper.structure.command.StructuredInterchange
import de.jet.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.jet.paper.structure.command.completion.buildInterchangeStructure
import de.jet.paper.structure.command.completion.component.CompletionAsset
import de.jet.paper.structure.command.completion.mustNotMatchOutput
import de.jet.paper.structure.component.Component
import de.jet.paper.tool.display.message.Transmission

internal class ComponentInterchange : StructuredInterchange("component", protectedAccess = true, structure = buildInterchangeStructure {

    fun list(page: Int) {

    }

    fun start(component: Component, executor: InterchangeExecutor) {
        if (!component.isRunning) {

            component.vendor.start(component.identityObject)

            lang("interchange.internal.component.nowRunning")
                .replace("[component]", component.identity)
                .notification(Transmission.Level.APPLIED, executor).display()

        } else
            lang("interchange.internal.component.alreadyRunning")
                .replace("[component]", component.identity)
                .notification(Transmission.Level.FAIL, executor).display()
    }

    fun stop(component: Component, executor: InterchangeExecutor) {
        if (component.isRunning) {

            component.vendor.stop(component.identityObject)

            lang("interchange.internal.component.nowStopped")
                .replace("[component]", component.identity)
                .notification(Transmission.Level.APPLIED, executor).display()

        } else
            lang("interchange.internal.component.missingRunning")
                .replace("[component]", component.identity)
                .notification(Transmission.Level.FAIL, executor).display()
    }

    fun restart(component: Component, executor: InterchangeExecutor) {
        if (component.isRunning) stop(component, executor)
        start(component, executor)
    }

    fun toggleAutostart(component: Component, executor: InterchangeExecutor) {
        if (!component.canBeAutoStartToggled) {

            JetData.autoStartComponents.let { preference ->
                val currentState = preference.content.toMutableSet()

                lang(
                    if (currentState.contains(component.identity)) {
                        currentState.remove(component.identity)
                        "interchange.internal.component.autoStartRemoved"
                    } else {
                        currentState.add(component.identity)
                        "interchange.internal.component.autoStartAdded"
                    }
                )
                    .replace("[component]", component.identity)
                    .notification(Transmission.Level.APPLIED, executor).display()

                preference.content = currentState
            }

        } else
            lang("interchange.internal.component.autoStartStatic")
                .replace("[component]", component.identity)
                .notification(Transmission.Level.FAIL, executor).display()
    }

    fun reset(component: Component, executor: InterchangeExecutor) {
        JetCache.runningComponents.apply {
            if (containsKey(component.identityObject))
                this[component.identityObject] = Calendar.now()
        }
    }

    branch {
        addContent(
            "list",
            "stopAll",
            "startAll",
            "restartAll",
            "autostartAll",
            "resetAll"
        )

        branch {

            addContent(CompletionAsset.LONG)
            mustNotMatchOutput()

            concludedExecution {

                val page = getInput(1, InterchangeStructureInputRestriction.LONG)

                list(page.toInt())

            }

        }

        concludedExecution {

            when (getInput(0)) {

                "list" -> {
                    list(1)
                }

                "stopAll" -> {

                }

                "startAll" -> {

                }

                "restartAll" -> {

                }

                "autostartAll" -> {

                }

                "resetAll" -> {

                }

            }

        }

    }

    branch {
        addContent(
            "start",
            "stop",
            "autostart",
            "restart",
            "reset",
            "info",
        )

        branch {
            addContent(CompletionAsset.COMPONENT)

            execution {
                val resultComponent = JetCache.registeredComponents.firstOrNull {
                    it.identity == getInput(1)
                }

                if (resultComponent != null) {

                    when (getInput(0)) {

                        "start" -> {

                        }

                        "stop" -> {

                        }

                        "autostart" -> {

                        }

                        "restart" -> {

                        }

                        "reset" -> {

                        }

                        "info" -> {

                        }

                    }

                    InterchangeResult.SUCCESS
                } else
                    InterchangeResult.WRONG_USAGE
            }

        }

    }

})