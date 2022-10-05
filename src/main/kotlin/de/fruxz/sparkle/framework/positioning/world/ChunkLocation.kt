package de.fruxz.sparkle.framework.positioning.world

import de.fruxz.sparkle.framework.extension.worldOrNull
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bukkit.Chunk
import org.bukkit.NamespacedKey
import org.bukkit.World
import java.util.concurrent.CompletableFuture

/**
 * This class represents a location of a chunk inside a world.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
data class ChunkLocation(
	@Contextual val worldKey: NamespacedKey,
	val x: Int,
	val z: Int,
) {

	/**
	 * This lazy value represents the world, where the chunk should be located at.
	 * This utilizes the [worldKey] property, to determine the world using the
	 * [worldOrNull] extension function, at the first call (lazy value).
	 * This value throws a [IllegalStateException], if the world with the stored
	 * key is not loaded.
	 * @author Fruxz
	 * @since 1.0
	 */
	val world: World by lazy {
		worldOrNull(worldKey) ?: throw IllegalStateException("World '$worldKey' not found, maybe not yet initialized?")
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
	 * The returned object is a [CompletableFuture]!
	 * @author Fruxz
	 * @since 1.0
	 */
	val chunkAsync: CompletableFuture<Chunk>
		get() = world.getChunkAtAsync(x, z)

}
