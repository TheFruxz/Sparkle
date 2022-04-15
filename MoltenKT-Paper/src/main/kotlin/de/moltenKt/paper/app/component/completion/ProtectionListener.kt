package de.moltenKt.paper.app.component.completion

import de.moltenKt.paper.extension.interchange.getInterchange
import de.moltenKt.paper.extension.interchange.getServerCommand
import de.moltenKt.paper.structure.app.event.EventListener
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