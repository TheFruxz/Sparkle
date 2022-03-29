package de.jet.paper.app.component.completion

import de.jet.paper.extension.interchange.getInterchange
import de.jet.paper.extension.interchange.getServerCommand
import de.jet.paper.structure.app.event.EventListener
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority.HIGHEST
import org.bukkit.event.player.PlayerCommandSendEvent

internal class ProtectionListener : EventListener() {

    @EventHandler(priority = HIGHEST)
    fun onCommandsReceive(event: PlayerCommandSendEvent) {
        val player = event.player

        event.commands.toList().forEach { completion ->
            val permission =
                getInterchange(completion)?.requiredApproval?.identity
                    ?: getServerCommand(completion)?.permission

            if (permission?.let { !player.hasPermission(permission) } == true) event.commands.remove(completion)

        }

    }

}