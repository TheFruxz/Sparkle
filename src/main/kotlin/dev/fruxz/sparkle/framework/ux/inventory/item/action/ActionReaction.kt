package dev.fruxz.sparkle.framework.ux.inventory.item.action

import org.bukkit.event.Event

class ActionReaction<T : Event>(
    val process: (T) -> Unit,
) {

    companion object {

        fun <T : Event> of(process: (T) -> Unit): ActionReaction<T> {
            return ActionReaction(process)
        }

        fun <T : Event> onClick(process: (T) -> Unit): ActionReaction<T> {
            return ActionReaction(process)
        }

        fun <T : Event> onInteract(process: (T) -> Unit): ActionReaction<T> {
            return ActionReaction(process)
        }

        fun <T : Event> onDrop(process: (T) -> Unit): ActionReaction<T> {
            return ActionReaction(process)
        }

        fun <T : Event> onPickup(process: (T) -> Unit): ActionReaction<T> {
            return ActionReaction(process)
        }

    }

}
