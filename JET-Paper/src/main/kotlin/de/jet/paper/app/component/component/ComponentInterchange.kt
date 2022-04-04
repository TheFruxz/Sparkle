package de.jet.paper.app.component.component

import de.jet.jvm.extension.container.mapToString
import de.jet.jvm.extension.container.page
import de.jet.jvm.extension.container.replaceVariables
import de.jet.jvm.extension.math.ceilToInt
import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.paper.app.JetCache
import de.jet.paper.app.JetData
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.interchange.InterchangeExecutor
import de.jet.paper.extension.lang
import de.jet.paper.extension.system
import de.jet.paper.structure.command.StructuredInterchange
import de.jet.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.jet.paper.structure.command.completion.buildInterchangeStructure
import de.jet.paper.structure.command.completion.component.CompletionAsset
import de.jet.paper.structure.command.completion.ignoreCase
import de.jet.paper.structure.command.completion.isNotRequired
import de.jet.paper.structure.component.Component
import de.jet.paper.tool.display.message.Transmission
import de.jet.paper.tool.display.message.Transmission.Level.*

internal class ComponentInterchange : StructuredInterchange("component", protectedAccess = true, structure = buildInterchangeStructure {

    val icons = listOf(
        "⭘",
        "⏻",
        "⚡",
        "⏹",
        "\uD83E\uDDEA"
    )

    fun list(page: Int, executor: InterchangeExecutor) {
        JetCache.registeredComponents.page(page-1, 6).let { (content, page, pages) ->

            buildString {

                appendLine(lang("interchange.internal.component.list.header").replaceVariables(
                    "p1" to (page + 1),
                    "p2" to pages
                ))

                appendLine(lang("interchange.internal.component.list.description").replaceVariables(
                    "1" to "${icons[0]}/${icons[1]}",
                    "2" to icons[2],
                    "3" to icons[3],
                    "4" to icons[4],
                ))

                appendLine()

                content.forEach { element ->
                    appendLine(lang("interchange.internal.component.list.line").replaceVariables(
                        "status" to if (element.isRunning) "<green>${icons[1]}<gray>" else "<gray>${icons[0]}",
                        "autoStart" to if (element.isAutoStarting) "<green>${icons[2]}<gray>" else "<gray>${icons[2]}",
                        "force" to if (!element.canBeStopped) "<yellow>${icons[3]}<gray>" else "<gray>${icons[3]}",
                        "experimental" to if (element.experimental) "<yellow>${icons[4]}<gray>" else "<gray>${icons[4]}",
                        "component" to element.identity,
                    ))
                }

            }.notification(INFO, executor)
                .display()

        }
    }

    fun start(component: Component, executor: InterchangeExecutor) {
        if (!component.isRunning) {

            component.vendor.start(component.identityObject)

            lang("interchange.internal.component.nowRunning")
                .replaceVariables("component" to component.identity)
                .notification(Transmission.Level.APPLIED, executor).display()

        } else
            lang("interchange.internal.component.alreadyRunning")
                .replaceVariables("component" to component.identity)
                .notification(Transmission.Level.FAIL, executor).display()
    }

    fun stop(component: Component, executor: InterchangeExecutor) {
        if (component.isRunning) {

            if (component.canBeStopped) {

                component.vendor.stop(component.identityObject)

                lang("interchange.internal.component.nowStopped")
                    .replace("[component]", component.identity)
                    .notification(Transmission.Level.APPLIED, executor).display()

            } else
                lang("interchange.internal.component.runningStatic")
                    .replaceVariables("component" to component.identity)
                    .notification(FAIL, executor).display()

        } else
            lang("interchange.internal.component.missingRunning")
                .replaceVariables("component" to component.identity)
                .notification(Transmission.Level.FAIL, executor).display()
    }

    fun restart(component: Component, executor: InterchangeExecutor) {
        if (component.isRunning) stop(component, executor)
        start(component, executor)
    }

    fun toggleAutostart(component: Component, executor: InterchangeExecutor) {
        if (component.canBeAutoStartToggled) {

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
                    .replaceVariables("component" to component.identity)
                    .notification(Transmission.Level.APPLIED, executor).display()

                preference.content = currentState
            }

        } else
            lang("interchange.internal.component.autoStartStatic")
                .replaceVariables("component" to component.identity)
                .notification(Transmission.Level.FAIL, executor).display()
    }

    fun reset(component: Component, executor: InterchangeExecutor) {
        JetCache.runningComponents.apply {
            if (containsKey(component.identityObject))
                this[component.identityObject] = Calendar.now()
        }
        lang("interchange.internal.component.reset")
            .replaceVariables("component" to component.identity)
            .notification(APPLIED, executor).display()
    }

    fun info(component: Component, executor: InterchangeExecutor) {
        buildString {

            appendLine(lang("interchange.internal.component.info.header").replaceVariables("component" to component.identity))

            fun Boolean.toDisplay() = if (this) lang("interchange.internal.component.info.dict.true") else lang("interchange.internal.component.info.dict.false")

            mapOf(
                lang("interchange.internal.component.info.dict.name") to component.identity,
                lang("interchange.internal.component.info.dict.running") to component.isRunning.toDisplay(),
                lang("interchange.internal.component.info.dict.vendor") to component.vendorIdentity.identity,
                lang("interchange.internal.component.info.dict.configuration") to component.behaviour.name,
                lang("interchange.internal.component.info.dict.isAutoStart") to component.isAutoStarting.toDisplay(),
                lang("interchange.internal.component.info.dict.isExperimental") to component.experimental.toDisplay(),
                lang("interchange.internal.component.info.dict.runningSince") to (component.runningSince?.durationToNow()?.toString() ?: "-/-")
            ).forEach { (key, value) ->
                append("\n- ${lang("interchange.internal.component.info.line").replaceVariables(
                    "key" to key,
                    "value" to value,
                )}")
            }
        }.notification(INFO, executor).display()
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

            addContent(CompletionAsset<Long>(system, "componentPage", true, listOf(InterchangeStructureInputRestriction.LONG), generator = {
                (1..ceilToInt(JetCache.registeredComponents.size.toDouble() / 6)).mapToString()
            }))
            isNotRequired()

            concludedExecution {

                val page = getInput(1, InterchangeStructureInputRestriction.LONG)

                list(page.toInt(), executor)

            }

        }

        concludedExecution {

            fun processAllComponents(function: (Component, InterchangeExecutor) -> Unit) {
                JetCache.registeredComponents.forEach {
                    function(it, executor)
                }
            }

            when (getInput(0)) {

                "list" -> {
                    list(1, executor)
                }

                "stopAll" -> {
                    processAllComponents(::stop)
                }

                "startAll" -> {
                    processAllComponents(::start)
                }

                "restartAll" -> {
                    processAllComponents(::restart)
                }

                "autostartAll" -> {
                    processAllComponents(::toggleAutostart)
                }

                "resetAll" -> {
                    processAllComponents(::reset)
                }

            }

        }

    }

    branch {

        addContent("at", "@")

        ignoreCase()

        branch {

            addContent(CompletionAsset.COMPONENT)

            branch {

                addContent("start")

                ignoreCase()

                concludedExecution {

                    start(getInput(1, CompletionAsset.COMPONENT), executor)

                }

            }

            branch {

                addContent("stop")

                ignoreCase()

                concludedExecution {

                    stop(getInput(1, CompletionAsset.COMPONENT), executor)

                }

            }

            branch {

                addContent("restart")

                ignoreCase()

                concludedExecution {

                    restart(getInput(1, CompletionAsset.COMPONENT), executor)

                }

            }

            branch {

                addContent("reset")

                ignoreCase()

                concludedExecution {

                    reset(getInput(1, CompletionAsset.COMPONENT), executor)

                }

            }

            branch {

                addContent("autostart")

                ignoreCase()

                concludedExecution {

                    toggleAutostart(getInput(1, CompletionAsset.COMPONENT), executor)

                }

            }

            branch {

                addContent("info")

                ignoreCase()

                concludedExecution {

                    info(getInput(1, CompletionAsset.COMPONENT), executor)

                }

            }

        }

    }

})