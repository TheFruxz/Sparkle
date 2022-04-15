package de.moltenKt.paper.tool.display.item.action

/**
 * This enum defines the type of actions, you can track
 * using the ItemAction system of MoltenKT-Paper.
 * @author Fruxz
 * @since 1.0
 */
enum class ItemActionType {

    /**
     * Triggered by clicking on it via the cursor (inventory)
     */
    CLICK,

    /**
     * Triggered by right-or-left clicking it via the player (use bow, attack with sword, ...)
     */
    INTERACT,

    /**
     * Triggered by dropping it via the player (pressing Q to drop, dropping it out of the inventory, ...)
     */
    DROP;

}