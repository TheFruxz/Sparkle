package dev.fruxz.sparkle.framework.world.location

import dev.fruxz.sparkle.framework.coroutine.task.asSyncDeferred
import dev.fruxz.sparkle.framework.system.worldOrNull
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.future.await
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bukkit.Chunk
import org.bukkit.NamespacedKey
import org.bukkit.World

@Serializable
data class ChunkLocation(
    @Contextual val key: NamespacedKey,
    val x: Int,
    val z: Int,
) {

    constructor(
        world: World,
        x: Int,
        z: Int,
    ) : this(world.key, x, z)

    /**
     * This lazy value represents the world, where the chunk should be located at.
     * This utilizes the [key] property, to determine the world using the
     * [worldOrNull] extension function, at the first call (lazy value).
     * This value throws a [IllegalStateException], if the world with the stored
     * key is not loaded.
     * @author Fruxz
     * @since 1.0
     */
    val world: World by lazy {
        worldOrNull(key) ?: throw IllegalStateException("World '$key' not found, maybe not yet initialized?")
    }

    /**
     * This computational value returns the chunk at the stored location and world.
     * This value utilizes the [World.getChunkAt] function, to get the chunk.
     * @author Fruxz
     * @since 1.0
     */
    val chunk: Chunk
        get() = world.getChunkAt(x, z)

    /**
     * This computational value returns the chunk at the stored location and world.
     * This value utilizes the [World.getChunkAtAsync] function, to get the chunk.
     * The returned object is a [Deferred]!
     * @author Fruxz
     * @since 1.0
     */
    val chunkAsync: Deferred<Chunk>
        get() = asSyncDeferred { world.getChunkAtAsync(x, z).await() }

}
