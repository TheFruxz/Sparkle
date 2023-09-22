package dev.fruxz.sparkle.framework.world

import dev.fruxz.sparkle.framework.world.location.ChunkLocation
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity

/**
 * This computational value returns a new [ChunkLocation] object,
 * that contains the x, y and the world key of this [Chunk].
 * @author Fruxz
 * @since 1.0
 */
val Chunk.location: ChunkLocation
    get() = ChunkLocation(world.key, x, z)

/**
 * This function returns, if the provided [location]s chunk is the
 * same as the chunk-location of [this] [Chunk].
 * @return true if the chunks are the same, false if not
 * @author Fruxz
 * @since 1.0
 */
operator fun Chunk.contains(location: Location) = this.location == location.chunk.location

/**
 * This function returns, if the [Location] of the [entity]'s [Chunk]
 * is the same as [this] [Chunk]s location.
 * @return true, if the [Location] of the [entity]'s [Chunk] is the same as [this] [Chunk]s location
 * @author Fruxz
 * @since 1.0
 */
operator fun Chunk.contains(entity: Entity) = entity.location in this

/**
 * This function returns, if the [Location] of the [block]'s [Chunk]
 * is the same as [this] [Chunk]s location.
 * @return true if the [Location] of the [block]'s [Chunk] is the same as [this] location
 * @author Fruxz
 * @since 1.0
 */
operator fun Chunk.contains(block: Block) = block.location in this

val Chunk.positionX: IntRange
    get() = x * 16 until (x + 1) * 16

val Chunk.positionY: IntRange
    get() = world.minHeight..world.maxHeight

val Chunk.positionZ: IntRange
    get() = z * 16 until (z + 1) * 16

val Chunk.locations: Set<Location>
    get() = buildSet {
        for (x in positionX) {
            for (y in positionY) {
                for (z in positionZ) {
                    add(location(world, x, y, z))
                }
            }
        }
    }

val Chunk.blocks: Set<Block>
    get() = buildSet {
        for (x in positionX) {
            for (y in positionY) {
                for (z in positionZ) {
                    add(world.getBlockAt(x, y, z))
                }
            }
        }
    }

fun Chunk.blocks(filter: (Block) -> Boolean): Set<Block> = buildSet {
    for (x in positionX) {
        for (y in positionY) {
            for (z in positionZ) {
                val block = world.getBlockAt(x, y, z)
                if (filter(block)) {
                    add(block)
                }
            }
        }
    }
}

inline fun <reified T> Chunk.blocks(): Set<T> = buildSet {
    for (x in positionX) {
        for (y in positionY) {
            for (z in positionZ) {
                val block = world.getBlockAt(x, y, z)
                if (block is T) {
                    add(block)
                }
            }
        }
    }
}