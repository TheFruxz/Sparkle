package de.fruxz.sparkle.app.component.completion

import de.fruxz.sparkle.app.SparkleCache
import de.fruxz.sparkle.extension.debugLog
import de.fruxz.sparkle.extension.interchange.getInterchange
import de.fruxz.sparkle.structure.app.event.EventListener
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority.HIGHEST
import org.bukkit.event.player.PlayerCommandSendEvent

internal class ProtectionListener : EventListener() {

    @EventHandler(priority = HIGHEST)
    fun onReceiveCommandList(event: PlayerCommandSendEvent) {
        val player = event.player
        val commands = event.commands.toList()

        debugLog("calculating protection for ${player.name}...")

        commands.forEach { recommendation ->
            var remove = false
            val interchange = getInterchange(recommendation)

            if (interchange != null) {
                if (!interchange.hiddenFromRecommendation) {
                    if (interchange.requiredApproval?.hasApproval(player) != false) {
                        if (interchange.forcedApproval?.hasApproval(player) != false) {
                            if (interchange.userRestriction.match(player)) {

                                if (SparkleCache.disabledInterchanges.any { it.identity == interchange.identity }) {
                                    remove = true
                                }

                            } else
                                remove = true
                        } else
                            remove = true
                    } else
                        remove = true
                } else
                    remove = true
            }

            if (remove) event.commands.remove(recommendation)

        }

        debugLog("protection calculated for ${player.name}!")

    }

}