package de.moltenKt.paper.extension.paper

import de.moltenKt.paper.extension.tasky.doSync
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockState
import org.bukkit.block.Container
import org.bukkit.block.Sign
import org.bukkit.entity.EntityType
import org.bukkit.entity.FallingBlock
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason
import org.bukkit.inventory.Inventory

/**
 * This function casts a [BlockState] to the correct,
 * editable block state.
 * @author Fruxz
 * @since 1.0
 */
val BlockState.sign: Sign
    get() = this as Sign

/**
 * This function takes the sign's state and applies the
 * [builder] process to it.
 * @author Fruxz
 * @since 1.0
 */
fun Block.editSign(builder: Sign.() -> Unit) {
    state.sign.apply(builder).update()
}

/**
 * This function returns the inventory of the given Block,
 * or null, if the block itself does not hold any inventory.
 * @author Fruxz
 * @since 1.0
 */
val Block.inventoryOrNull: Inventory?
    get() = if (this is Container) inventory else null

/**
 * This function returns the inventory of the given Block,
 * or throws an [NoSuchElementException], if the block itself
 * does not hold any inventory.
 * @throws NoSuchElementException if the block itself does not hold any inventory.
 * @author Fruxz
 * @since 1.0
 */
val Block.inventory: Inventory
    get() = this.inventoryOrNull ?: throw NoSuchElementException("Block has no container")

/**
 * This function returns a snapshot inventory of the given Block,
 * or null, if the block itself does not hold any inventory.
 * @see Container.getSnapshotInventory
 * @author Fruxz
 * @since 1.0
 */
val Block.inventorySnapshotOrNull: Inventory?
    get() = if (this is Container) snapshotInventory else null

/**
 * This function returns a snapshot inventory of the given Block,
 * or throws an [NoSuchElementException], if the block itself
 * does not hold any inventory.
 * @throws NoSuchElementException if the block itself does not hold any inventory.
 * @see Container.getSnapshotInventory
 * @author Fruxz
 * @since 1.0
 */
val Block.inventorySnapshot: Inventory
    get() = this.inventorySnapshotOrNull ?: throw NoSuchElementException("Block has no container")

/**
 * This function replaces the block with [Material.AIR]
 * and spawns a new [FallingBlock] at the center location
 * of [this] [Block].
 * So for the player, it is like the block is falling like
 * a normal sand block with air beyond.
 * @author Fruxz
 * @since 1.0
 */
fun Block.toFallingBlock() {
    blockData.clone().let { data ->
        type = Material.AIR
        world.spawnFallingBlock(location.toCenterLocation(), data)
    }
}
