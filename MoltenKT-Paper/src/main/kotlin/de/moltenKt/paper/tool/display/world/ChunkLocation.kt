package de.moltenKt.paper.tool.display.world

import de.moltenKt.paper.extension.paper.worldOrNull
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bukkit.Chunk
import org.bukkit.NamespacedKey
import org.bukkit.World
import java.util.concurrent.CompletableFuture

@Serializable
data class ChunkLocation(
	@Contextual val worldKey: NamespacedKey,
	val x: Int,
	val z: Int,
) {

	val world: World by lazy {
		worldOrNull(worldKey) ?: throw IllegalStateException("World '$worldKey' not found, maybe not yet initialized?")
	}

	val chunk: Chunk
		get() = world.getChunkAt(x, z)

	val chunkAsync: CompletableFuture<Chunk>
		get() = world.getChunkAtAsync(x, z)

}
