package dev.fruxz.sparkle.framework.world

import dev.fruxz.ascend.extension.tryOrNull
import org.bukkit.Material
import org.bukkit.block.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.FallingBlock
import org.bukkit.entity.TNTPrimed
import org.bukkit.inventory.Inventory
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

/**
 * This computational value computes the exact representation
 * of blocks bounding box.
 * @author Fruxz
 * @since 1.0
 */
val Block.safeBoundingBox: BoundingBox
    get() = BoundingBox.of(location, location.clone().add(Vector(1.0, 1.0, 1.0)))

/**
 * This computational value uses this block and returns the state
 * of this block as a [Sign].
 * @author Fruxz
 * @since 1.0
 */
val Block.sign: Sign
    get() = this.state as Sign

/**
 * This computational value uses this block-state and returns
 * itself, but instead as a [Sign].
 * @author Fruxz
 * @since 1.0
 */
val BlockState.sign: Sign
    get() = this as Sign

/**
 * This computational value uses this block and returns the state
 * of this block as a [Chest].
 * @author Fruxz
 * @since 1.0
 */
val Block.chest: Chest
    get() = this.state as Chest

/**
 * This computational value uses this block-state and returns
 * itself, but instead as a [Chest]
 * @author Fruxz
 * @since 1.0
 */
val BlockState.chest: Chest
    get() = this as Chest

/**
 * This function uses [this] block and opens it, if it is a chest.
 * If it is not a chest, this function returns null instead of a [Unit].
 * @return Unit if it is a chest, or null, if it is not.
 * @receiver The block, which should be a chest
 * @author Fruxz
 * @since 1.0
 */
fun Block.openChest() = tryOrNull { chest.open() }

/**
 * This function uses [this] block state and opens it, if it is a chest.
 * If it is not a chest, this function returns null instead of a [Unit].
 * @return Unit if it is a chest, or null, if it is not.
 * @receiver The block-state, which should be a chest's block-state
 * @author Fruxz
 * @since 1.0
 */
fun BlockState.openChest() = tryOrNull { chest.close() }

/**
 * This function uses [this] block and closes it, if it is a chest.
 * If it is not a chest, this function returns null instead of a [Unit].
 * @return Unit if it is a chest, or null, if it is not.
 * @receiver The block, which should be a chest
 * @author Fruxz
 * @since 1.0
 */
fun Block.closeChest() = tryOrNull { chest.close() }

/**
 * This function uses [this] block state and closes it, if it is a chest.
 * If it is not a chest, this function returns null instead of a [Unit].
 * @return Unit if it is a chest, or null, if it is not.
 * @receiver The block-state, which should be a chest's block-state
 * @author Fruxz
 * @since 1.0
 */
fun BlockState.closeChest() = tryOrNull { chest.close() }

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
    get() = this.state.let { state ->
        when (state) {
            is Container -> state.inventory
            else -> null
        }
    }

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
val Block.snapshotInventoryOrNull: Inventory?
    get() = this.state.let { state ->
        when (state) {
            is Container -> state.snapshotInventory
            else -> null
        }
    }

/**
 * This function returns a snapshot inventory of the given Block,
 * or throws an [NoSuchElementException], if the block itself
 * does not hold any inventory.
 * @throws NoSuchElementException if the block itself does not hold any inventory.
 * @see Container.getSnapshotInventory
 * @author Fruxz
 * @since 1.0
 */
val Block.snapshotInventory: Inventory
    get() = this.snapshotInventoryOrNull ?: throw NoSuchElementException("Block has no container")

/**
 * This function replaces the block with [Material.AIR]
 * and spawns a new [FallingBlock] at the center location
 * of [this] [Block].
 * So for the player, it is like the block is falling like
 * a normal sand block with air beyond.
 * @author Fruxz
 * @since 1.0
 */
fun Block.toFallingBlock() = blockData.clone().let { data ->
    type = Material.AIR
    return@let world.spawnFallingBlock(location.toCenterLocation(), data)
}

/**
 * This function replaces the block with [Material.AIR]
 * and spawns a new [TNTPrimed] at the center location
 * of [this] [Block].
 * So for the player, it is like the block is getting
 * ignited, like a normal tnt block reacting to power.
 * @author Fruxz
 * @since 1.0
 */
fun Block.ignite(): TNTPrimed {
    type = Material.AIR
    return world.spawnEntity(location.toCenterLocation(), EntityType.PRIMED_TNT) as TNTPrimed
}