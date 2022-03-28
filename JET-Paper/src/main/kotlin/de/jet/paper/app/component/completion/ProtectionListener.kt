package de.jet.paper.app.component.completion

import de.jet.paper.extension.interchange.getInterchange
import de.jet.paper.extension.paper.getPluginCommand
import de.jet.paper.structure.app.event.EventListener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerCommandSendEvent

internal class ProtectionListener : EventListener() {

    @EventHandler
    fun onCommandsReceive(event: PlayerCommandSendEvent) {
        val player = event.player

        event.commands.toList().forEach { completion ->
            val permission =
                getInterchange(completion)?.requiredApproval?.identity
                    ?: getPluginCommand(completion)?.permission

            if (permission?.let { !player.hasPermission(permission) } == true) event.commands.remove(completion)

        }

    }

}