package de.fruxz.sparkle.server.component.app

import dev.fruxz.ascend.extension.container.paged
import dev.fruxz.ascend.extension.math.ceilToInt
import dev.fruxz.ascend.extension.math.limitTo
import de.fruxz.sparkle.framework.extension.componentOrNull
import de.fruxz.sparkle.framework.extension.coroutines.doAsync
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.extension.visual.message
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.cache.CacheDepthLevel
import de.fruxz.sparkle.framework.infrastructure.app.update.AppUpdater.UpdateState.*
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.infrastructure.command.completion.isNotRequired
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeAccess
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredInterchange
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance.Companion.APPLIED
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance.Companion.PROCESS
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.server.SparkleData
import de.fruxz.sparkle.server.component.update.UpdateComponent
import de.fruxz.sparkle.server.component.update.UpdateComponent.Companion.updateStates
import de.fruxz.sparkle.server.component.update.UpdateManager
import de.fruxz.sparkle.server.component.update.UpdateManager.UpdateResult
import de.fruxz.sparkle.server.component.update.UpdateManager.UpdateResult.SUCCESSFUL
import de.fruxz.sparkle.server.component.update.UpdateService
import dev.fruxz.stacked.buildComponent
import dev.fruxz.stacked.extension.*
import dev.fruxz.stacked.hover
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextDecoration.BOLD
import org.bukkit.Bukkit
import de.fruxz.sparkle.framework.infrastructure.component.Component as SparkleComponent

internal class AppInterchange : StructuredInterchange("app", buildInterchangeStructure {

    branch {

        addContent("list")

        concludedExecution {

            displayList(executor, 1)

        }

        branch {

            addContent(CompletionAsset.pageCompletion { ceilToInt(SparkleCache.registeredApps.size.toDouble() / SparkleData.systemConfig.entriesPerListPage) })
            isNotRequired()

            concludedExecution {
                val page = getInput(translationAsset = CompletionAsset.LONG).limitTo(Int.MIN_VALUE.toLong()..Int.MAX_VALUE.toLong()).toInt()

                displayList(executor, page)

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
                    val targetApp = getInput(slot = 1, translationAsset = CompletionAsset.APP)

                    text("Starting the app '${targetApp.label}'...")
                        .color(NamedTextColor.GRAY)
                        .hoverEvent(text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        .message(executor).display()

                    targetApp.requestStart()

                    text {
                        this + text("Successfully") {
                            style(Style.style(NamedTextColor.GREEN, BOLD))
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
                    }.notification(TransmissionAppearance.APPLIED, executor).display()

                }

            }

            branch {

                addContent("stop")

                concludedExecution {
                    val targetApp = getInput(slot = 1, translationAsset = CompletionAsset.APP)

                    text("Stopping the app '${targetApp.label}'...").dyeGray()
                        .hoverEvent(text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        .message(executor).display()

                    targetApp.requestStop()

                    text {
                        this + text("Successfully") {
                            style(Style.style(NamedTextColor.GREEN, BOLD))
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
                    }.notification(TransmissionAppearance.APPLIED, executor).display()

                }

            }

            branch {

                addContent("restart")

                concludedExecution {
                    val targetApp = getInput(slot = 1, translationAsset = CompletionAsset.APP)

                    text("Restarting the app '${targetApp.label}'...")
                        .color(NamedTextColor.GRAY)
                        .hoverEvent(text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY))
                        .message(executor).display()

                    targetApp.requestStop()
                    targetApp.requestStart()

                    text {
                        this + text("Successfully") {
                            style(Style.style(NamedTextColor.GREEN, BOLD))
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
                    }.notification(TransmissionAppearance.APPLIED, executor).display()

                }

            }

            branch {

                addContent("info")

                concludedExecution {
                    val targetApp = getInput(slot = 1, translationAsset = CompletionAsset.APP)

                    text {
                        this + text("Information about the app '").dyeGray()
                        this + text(targetApp.label).dyeGold()
                        this + text("'").dyeGray()

                        newline()

                        this + Component.newline() + text("Display-Name: ").dyeGray() + text(targetApp.label).dyeYellow()
                        this + Component.newline() + text("Identity: ").dyeGray() + text(targetApp.identity).dyeYellow()
                        this + Component.newline() + text("Active since: ").dyeGray() + text(targetApp.activeSince.toString()).dyeYellow()
                        this + Component.newline() + text("Components: ").dyeGray() + text("${SparkleCache.registeredComponents.filter { it.vendor.key == targetApp.key }.size} Components").dyeYellow()
                        this + Component.newline() + text("Interchanges: ").dyeGray() + text("${SparkleCache.registeredInterchanges.filter { it.vendor.key == targetApp.key }.size} Interchanges").dyeYellow()
                        this + Component.newline() + text("Services: ").dyeGray() + text("${SparkleCache.serviceStates.filter { it.value.vendor.key == targetApp.key }.size} Services").dyeYellow()
                        this + Component.newline() + text("SandBoxes: ").dyeGray() + text("${SparkleCache.registeredSandBoxes.filter { it.vendor.key == targetApp.key }.size} SandBoxes").dyeYellow()

                        newlines(2)
                    }.notification(TransmissionAppearance.GENERAL, executor).display()

                }

            }

            branch {

                addContent("install-update")

                concludedExecution {
                    val targetApp = getInput(slot = 1, translationAsset = CompletionAsset.APP)

                    installUpdate(executor, listOf(targetApp))

                }

            }

            branch {

                addContent("clear-cache")

                fun cacheClear(interchangeAccess: InterchangeAccess<*>, level: CacheDepthLevel) {
                    val targetApp = interchangeAccess.getInput(slot = 1, translationAsset = CompletionAsset.APP)

                    text("Starting cache-clear of app '${targetApp.label}'...")
                        .color(NamedTextColor.GRAY)
                        .hover {
                            text("Identity: ${targetApp.identity}").color(NamedTextColor.GRAY)
                        }
                        .message(interchangeAccess.executor).display()

                    debugLog("${interchangeAccess.executor.name} is clearing cache for '${targetApp.identity}' at experience '$level'...")

                    targetApp.appCache?.let {
                        it.dropEverything(level)
                        debugLog("${interchangeAccess.executor.name} cleared cache for '${targetApp.identity}' at experience '$level'!")
                    }

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

                        this + text("' at experience '") {
                            color(NamedTextColor.GRAY)
                        }

                        this + text("$level") {
                            color(NamedTextColor.YELLOW)
                        }

                        this + text("'!") {
                            color(NamedTextColor.GRAY)
                        }

                    }.notification(APPLIED, interchangeAccess.executor)
                        .display()

                }

                concludedExecution {

                    cacheClear(this, CacheDepthLevel.CLEAR)

                }

                branch {

                    isNotRequired()

                    addContent(CompletionAsset.CACHE_DEPTH_LEVEL)

                    concludedExecution {
                        val level = getInput(translationAsset = CompletionAsset.CACHE_DEPTH_LEVEL)

                        cacheClear(this, level)

                    }

                }

            }

        }

    }

    branch {

        addContent("update")

        branch {

            addContent("all", "*")

            concludedExecution {

                installUpdate(executor, SparkleCache.registeredApps)

            }

        }

        branch {

            addContent(CompletionAsset.APP)

            concludedExecution {

                installUpdate(executor, listOf(getInput(translationAsset = CompletionAsset.APP)))

            }

        }

    }

}) {

    private companion object {

        fun installUpdate(executor: InterchangeExecutor, apps: Iterable<App>) {

            text("Installing updates for '${apps.joinToString { it.key.asString() }}'...").dyeGray().notification(
                PROCESS, executor).display()

            doAsync {
                apps.forEach { app ->
                    UpdateManager.performUpdate(app).let {
                        when (it) {
                            UpdateResult.UP_TO_DATE -> {
                                text {
                                    this + text("The app '").dyeGray()
                                    this + text(app.key.asString()).dyeYellow()
                                    this + text("' is currently running ").dyeGray()
                                    this + text("the latest").dyeLightPurple()
                                    this + text(" known version!").dyeGray()
                                }.notification(TransmissionAppearance.WARNING, executor).display()
                            }
                            UpdateResult.FAILED -> {
                                text {
                                    this + text("The update of '").dyeGray()
                                    this + text(app.key.asString()).dyeYellow()
                                    this + text("' ").dyeGray()
                                    this + text("failed").dyeRed()
                                    this + text(" to install!").dyeGray()
                                }.notification(TransmissionAppearance.ERROR, executor).display()
                            }
                            SUCCESSFUL -> {
                                text {
                                    this + text("The app '").dyeGray()
                                    this + text(app.key.asString()).dyeYellow()
                                    this + text("' got ").dyeGray()
                                    this + text("successfully").dyeGreen()
                                    this + text(" updated!").dyeGray()
                                }.notification(APPLIED, executor).display()
                            }
                        }
                    }
                }
            }

        }

        fun displayList(executor: InterchangeExecutor, page: Int) {

            val paged = SparkleCache.registeredApps.paged(page - 1, SparkleData.systemConfig.entriesPerListPage)
            val lastUpdateMessage = if (componentOrNull(UpdateComponent::class)?.updateConfiguration?.updateUpdateNotifications == true && SparkleComponent.isEnabled(UpdateComponent::class)) {
                Component.newline() + text {
                    this + text("ᴛʜᴇ ʟᴀsᴛ ᴜᴘᴅᴀᴛᴇ-ᴄʜᴇᴄᴋ ɪs ").dyeGray()
                    this + text(UpdateService.lastUpdateCheck?.durationToNow()?.toString() ?: "ɴᴇᴠᴇʀ ᴄʜᴇᴄᴋᴇᴅ").dyeLightPurple()
                    this + text(" ᴀɢᴏ!").dyeGray()
                }
            } else null

            buildComponent {

                this + text("List of all running apps").dyeGray()
                this + text(" (Page ${paged.pageNumber} of ${paged.availablePages.last})") {
                    plus(NamedTextColor.YELLOW)
                    hover {
                        text("${paged.content.size} App" + (if (paged.content.size == 1) "" else "s") + " listed").dyeGray()
                    }
                }
                this + text(":").dyeGray()

                this + Component.newline() + text("⏻ Power; ⏹ API-Compatible; ⏏ Updates").dyeGray()

                newline()

                paged.content.forEach { app ->
                    newline()
                    this + text(if (app.isEnabled) "⏻" else "⭘") {
                        plus(if (app.isEnabled) NamedTextColor.GREEN else NamedTextColor.GRAY)
                        hover {
                            buildComponent {
                                this + text("Power-Indicator: ").style(NamedTextColor.BLUE, BOLD)
                                newline()
                                this + text("Indicates, if the plugin is currently enabled & running").dyeYellow()
                                newlines(2)
                                this + text("CLICK").style(NamedTextColor.GREEN, BOLD) + text(" to toggle the state of the app").dyeGray()
                                lastUpdateMessage?.let { message ->
                                    newline()
                                    this + message
                                }
                            }
                        }
                        clickEvent(ClickEvent.runCommand(
                            if (app.isEnabled) "/app @ ${app.key()} stop" else "/app @ ${app.key()} start"
                        ))
                    }
                    this + text(" ⏹") {
                        plus(if (Bukkit.getMinecraftVersion().startsWith("" + app.description.apiVersion)) NamedTextColor.GREEN else NamedTextColor.GRAY)
                        hover {
                            buildComponent {
                                this + text("Compatibility-Indicator: ").style(NamedTextColor.BLUE, BOLD)
                                newline()
                                this + text("Indicates, if the plugins target version is compatible with the current server version").dyeYellow()
                                newlines(2)
                                this + text("Apps target version: ").dyeGray()
                                this + text(app.description.apiVersion ?: "None").dyeGreen()
                                newline()
                                this + text("Server version: ").dyeGray()
                                this + text(Bukkit.getMinecraftVersion()).dyeGreen()
                                lastUpdateMessage?.let { message ->
                                    newline()
                                    this + message
                                }
                            }
                        }
                    }
                    this + text(" ∞") {

                        when (updateStates[app]?.type) {
                            null -> {
                                dyeGray()
                                hover {
                                    text {
                                        this + text("Update-Indicator: ").style(NamedTextColor.BLUE, BOLD)
                                        newlines(2)
                                        this + text {
                                            this + text("The app '").dyeGray()
                                            this + text("${app.key}").dyeYellow()
                                            this + text("' is currently ").dyeGray()
                                            this + text("not providing").dyeRed()
                                            this + text(" a working update solution!").dyeGray()
                                        }
                                        lastUpdateMessage?.let { message ->
                                            newline()
                                            this + message
                                        }
                                    }
                                }
                            }
                            UP_TO_DATE -> {
                                dyeGreen()
                                hover {
                                    text {
                                        this + text("Update-Indicator: ").style(NamedTextColor.BLUE, BOLD)
                                        newlines(2)
                                        this + text {
                                            text("The app '").dyeGray()
                                            text("${app.key}").dyeYellow()
                                            text("' is currently reporting to be ").dyeGray()
                                            text("up-to-date").dyeGreen()
                                            text("!").dyeGray()
                                        }
                                        lastUpdateMessage?.let { message ->
                                            newline()
                                            this + message
                                        }
                                    }
                                }
                            }
                            FAILED -> {
                                dyeRed()
                                hover {
                                    text {
                                        this + text("Update-Indicator: ").style(NamedTextColor.BLUE, BOLD)
                                        newlines(2)
                                        this + text {
                                            text("The app '").dyeGray()
                                            text("${app.key}").dyeYellow()
                                            text("' is currently ").dyeGray()
                                            text("not properly reporting").dyeRed()
                                            text(" the current available updates!").dyeGray()
                                        }
                                        lastUpdateMessage?.let { message ->
                                            newline()
                                            this + message
                                        }
                                    }
                                }
                            }
                            UPDATE_AVAILABLE -> {
                                if (UpdateComponent.updateProcesses[app]?.isCompleted == true) {
                                    dyeAqua()
                                    hover {
                                        text {
                                            this + text("Update-Indicator: ").style(NamedTextColor.BLUE, BOLD)
                                            newlines(2)
                                            this + text {
                                                this + text("The app '").dyeGray()
                                                this + text("${app.key}").dyeYellow()
                                                this + text("' has been ").dyeGray()
                                                this + text("successfully updated").dyeGreen()
                                                this + text("! Only a ").dyeGray()
                                                this + text("reload/restart").dyeAqua()
                                                this + text(" is required!").dyeGray()
                                            }
                                        }
                                    }
                                } else if (UpdateComponent.updateProcesses[app] != null) {
                                    dyeDarkAqua()
                                    hover {
                                        text {
                                            this + text("Update-Indicator: ").style(NamedTextColor.BLUE, BOLD)
                                            newlines(2)
                                            this + text {
                                                this + text("The app '").dyeGray()
                                                this + text("${app.key}").dyeYellow()
                                                this + text("' is currently ").dyeGray()
                                                this + text("installing").dyeGreen()
                                                this + text(" its update!").dyeGray()
                                            }
                                        }
                                    }
                                } else {
                                    dyeYellow()
                                    hover {
                                        text {
                                            this + text("Update-Indicator: ").style(NamedTextColor.BLUE, BOLD)
                                            newlines(2)
                                            this + text {
                                                this + text("The app '").dyeGray()
                                                this + text("${app.key}").dyeYellow()
                                                this + text("' reported to Sparkle, that an update is ").dyeGray()
                                                this + text("available").dyeYellow()
                                                this + text("!").dyeGray()
                                            }
                                            newlines(2)
                                            this + text {
                                                this + text("CLICK ").style(NamedTextColor.GREEN, BOLD)
                                                this + text("to update this app now!").dyeGray()
                                            }
                                            lastUpdateMessage?.let { message ->
                                                newline()
                                                this + message
                                            }
                                        }
                                    }
                                    clickEvent(ClickEvent.runCommand("/app @ ${app.key()} install-update"))
                                }
                            }
                        }
                    }
                    this + text(" | ").dyeDarkGray()
                    this + text(app.label) {
                        plus(NamedTextColor.YELLOW)
                        hover {
                            buildComponent {
                                this + text("Label & Identity: ") {
                                    plus(Style.style(NamedTextColor.BLUE, BOLD))
                                }
                                newline()
                                this + text("The label is used to display the app in lists and information, the identity is used to identify the app in the system") {
                                    plus(NamedTextColor.YELLOW)
                                }
                                newlines(2)
                                this + text("Label: ") {
                                    plus(NamedTextColor.GRAY)
                                } + text(app.label) {
                                    plus(NamedTextColor.GREEN)
                                }
                                newline()
                                this + text("Identity: ") {
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
                }

                newlines(2)

            }.notification(TransmissionAppearance.GENERAL, executor).display()

        }

    }

}