package dev.fruxz.sparkle.framework.ux.panel

import dev.fruxz.ascend.extension.future.awaitAll
import dev.fruxz.ascend.extension.objects.takeIfInstance
import dev.fruxz.sparkle.framework.coroutine.task.doAsync
import dev.fruxz.sparkle.framework.system.debugLog
import kotlinx.coroutines.runBlocking
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.time.measureTime

class PanelListener : Listener {

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val holder = event.inventory.holder?.takeIfInstance<PanelHolder>() ?: return
        val panel = holder.panel
        val clicks = panel.clickActions[event.rawSlot]

        if (PanelFlag.DETECT_OUTSIDE_CLICKS !in panel.flags && event.rawSlot !in panel.format.slots) return // Because the slots expand to the player inventory as well

        debugLog { "Click on slot ${event.rawSlot} in panel ${panel.identifier()} - ${clicks?.size ?: 0} click-actions assigned to this slot" }

        if (clicks.isNullOrEmpty()) return

        event.isCancelled = true

        measureTime {
            runBlocking {
                when {
                    PanelFlag.PARALLEL_ACTION_PROCESSING in panel.flags -> { // TODO experimental
                        clicks.map {
                            doAsync { _ ->
                                it.action(event)
                            }
                        }.awaitAll()
                    }
                    else -> clicks.forEach { it.action(event) }
                }
            }
        }.also { time ->
            debugLog { "Click on slot ${event.rawSlot} in panel ${panel.identifier()} => took $time! (avg. ${time / clicks.size} per action)" }
        }

    }

}