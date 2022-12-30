package de.fruxz.sparkle.framework.infrastructure.command.completion.content

import de.fruxz.ascend.extension.container.firstOrNull
import de.fruxz.ascend.extension.container.mapToString
import de.fruxz.ascend.extension.container.withMap
import de.fruxz.ascend.extension.container.withMapNotNull
import de.fruxz.ascend.extension.math.isDouble
import de.fruxz.ascend.extension.math.isLong
import de.fruxz.ascend.extension.objects.takeIfInstance
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.sparkle.framework.effect.sound.SoundLibrary
import de.fruxz.sparkle.framework.extension.*
import de.fruxz.sparkle.framework.extension.coroutines.key
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.identification.KeyedIdentifiable
import de.fruxz.sparkle.framework.infrastructure.app.cache.CacheDepthLevel
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAssetBuilder.Companion.buildAsset
import de.fruxz.sparkle.framework.visual.color.ColorType
import de.fruxz.sparkle.framework.visual.color.DyeableMaterial
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.stacked.extension.subKey
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.structure.Mirror
import org.bukkit.block.structure.StructureRotation
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.nio.file.Path
import java.util.*
import kotlin.io.path.*

data class CompletionAsset<T>(
	override val identityKey: Key,
	val isContentDynamic: Boolean,
	val validation: (CompletionContext.() -> Boolean)? = null,
	val translation: (CompletionContext.() -> T?)? = null,
	val completion: CompletionContext.(CompletionAsset<T>) -> Collection<String>,
) : KeyedIdentifiable<CompletionAsset<T>> {

	fun computedContent(context: CompletionContext): SortedSet<String> = when {
		(!isContentDynamic && SparkleCache.registeredCompletionAssetStateCache.containsKey(identity)) -> SparkleCache.registeredCompletionAssetStateCache[identity]!!
		else -> {
			completion(context, this).toSortedSet().apply {
				if (!isContentDynamic) SparkleCache.registeredCompletionAssetStateCache += identity to this
			}
		}
	}

	companion object {

		@JvmStatic
		val LONG = buildAsset(sparkle.subKey("long")) {
			complete { (0..99).mapToString() }
			validate { input.isLong() }
			translate { input.toLong() }
		}

		@JvmStatic
		val DOUBLE = buildAsset(sparkle.subKey("double")) {
			complete { setOf(.0, .1, .2, .3, .4, .5, .6, .7, .8, 1.0).mapToString() }
			validate { input.isDouble() }
			translate { input.toDouble() }
		}

        @JvmStatic
        val NUMBER = buildAsset(sparkle.subKey("number")) {
			complete { setOf(0, .5, 1, 1.5, 2).mapToString() }
			validate { input.isLong() || input.isDouble() }
			translate { input.toLongOrNull() ?: input.toDoubleOrNull() }
		}

		@JvmStatic
		val ONLINE_PLAYER_NAME = buildAsset(sparkle.subKey("online_player_name")) {
			complete { onlinePlayers.withMap { name } }
			validate { playerOrNull(input) != null }
			translate { playerOrNull(input) }
			dynamicAsset()
		}

		@JvmStatic
		val ONLINE_PLAYER_UUID = buildAsset(sparkle.subKey("online_player_uuid")) {
			complete { onlinePlayers.withMap { "$uniqueId" } }
			validate { tryOrNull { UUID.fromString(input) }?.let { uuid -> return@let playerOrNull(uuid) } != null }
			translate { tryOrNull { playerOrNull(UUID.fromString(input)) } }
			dynamicAsset()
		}

		@JvmStatic
		val OFFLINE_PLAYER_NAME = buildAsset(sparkle.subKey("offline_player_name")) {
			complete { offlinePlayers.withMapNotNull { name } }
			validate { offlinePlayer(input).name != null }
			translate { offlinePlayer(input) }
			dynamicAsset()
		}

		@JvmStatic
		val OFFLINE_PLAYER_UUID = buildAsset(sparkle.subKey("offline_player_uuid")) {
			complete { offlinePlayers.withMap { "$uniqueId" } }
			validate { tryOrNull { UUID.fromString(input) }?.let { uuid -> return@let offlinePlayer(uuid).name } != null }
			translate { tryOrNull { offlinePlayer(UUID.fromString(input)) } }
			dynamicAsset()
		}

		@JvmStatic
		val GAME_MODE = buildAsset(sparkle.subKey("gamemode")) {
			complete { GameMode.values().withMap { name } }
			validate { GameMode.values().any { it.name.equals(input, ignoreCase) } }
			translate { GameMode.values().firstOrNull { it.name.equals(input, ignoreCase) } }
		}

		@JvmStatic
		val ENTITY_TYPE = buildAsset(sparkle.subKey("entity_type")) {
			complete { EntityType.values().withMap { name } }
			validate { EntityType.values().any { it.name.equals(input, ignoreCase) } }
			translate { EntityType.valueOf(input) }
		}

		@JvmStatic
		val WORLD_NAME = buildAsset(sparkle.subKey("world_name")) {
			complete { worlds.withMap { name } }
			validate { worlds.any { it.name.equals(input, ignoreCase) } }
			translate { Bukkit.getWorld(input) }
			dynamicAsset()
		}

		@JvmStatic
		val APP = buildAsset(sparkle.subKey("app")) {
			complete { SparkleCache.registeredApps.withMap { identity } }
			validate { SparkleCache.registeredApps.any { it.identity.equals(input, ignoreCase) } }
			translate { SparkleCache.registeredApps.firstOrNull { it.identity == input } }
			dynamicAsset()
		}

		@JvmStatic
		val PLUGIN = buildAsset(sparkle.subKey("plugin")) {
			complete { plugins.withMap { key.asString() } }
			validate { plugins.any { it.name.lowercase().equals(input.split(":").lastOrNull(), ignoreCase) } }
			translate { plugins.firstOrNull { it.name.lowercase().equals(input.split(":").lastOrNull(), ignoreCase) } }
			dynamicAsset()
		}

		@JvmStatic
		val INTERCHANGE = buildAsset(sparkle.subKey("interchange")) {
			complete { SparkleCache.registeredInterchanges.withMap { identity } }
			validate { SparkleCache.registeredInterchanges.any { it.identity.equals(input, ignoreCase) } }
			translate { SparkleCache.registeredInterchanges.firstOrNull { it.identity == input } }
			dynamicAsset()
		}

		@JvmStatic
		val SERVICE = buildAsset(sparkle.subKey("service")) {
			complete { SparkleCache.services.withMap { key.asString() } }
			validate { SparkleCache.services.any { it.key.asString().equals(input, ignoreCase) } }
			translate { SparkleCache.serviceStates.firstOrNull { it.key.asString() == input }?.value?.service }
			dynamicAsset()
		}

		@JvmStatic
		val COMPONENT = buildAsset(sparkle.subKey("component")) {
			complete { SparkleCache.registeredComponents.withMap { identity } }
			validate { SparkleCache.registeredComponents.any { it.identity.equals(input, ignoreCase) } }
			translate { SparkleCache.registeredComponents.firstOrNull { it.identity == input } }
			dynamicAsset()
		}

		@JvmStatic
		val SANDBOX = buildAsset(sparkle.subKey("sandbox")) {
			complete { SparkleCache.registeredSandBoxes.withMap { identity } }
			validate { SparkleCache.registeredSandBoxes.any { it.identity.equals(input, ignoreCase) } }
			translate { SparkleCache.registeredSandBoxes.firstOrNull { it.identity == input } }
			dynamicAsset()
		}

		@JvmStatic
		val PREFERENCE = buildAsset(sparkle.subKey("preference")) {
			complete { SparkleCache.registeredPreferences.keys.withMap { identity } }
			validate { SparkleCache.registeredPreferences.keys.any { it.identity.equals(input, ignoreCase) } }
			translate { SparkleCache.registeredPreferences.firstOrNull { it.key.identity == input }?.value }
			dynamicAsset()
		}

		@JvmStatic
		val CACHE_DEPTH_LEVEL = buildAsset(sparkle.subKey("cache_depth")) {
			complete { CacheDepthLevel.values().withMap { name } }
			validate { CacheDepthLevel.values().any { it.name.equals(input, ignoreCase) } }
			translate { CacheDepthLevel.valueOf(input) }
		}

		@JvmStatic
		val TRANSMISSION_LEVEL = buildAsset(sparkle.subKey("transmission_level")) {
			complete { TransmissionAppearance.values.withMap { name } }
			validate { TransmissionAppearance.values.any { it.name.equals(input, ignoreCase) } }
			translate { TransmissionAppearance.values.firstOrNull { it.name.equals(input, ignoreCase) } }
		}

		@JvmStatic
		val LIBRARY_SOUND = buildAsset(sparkle.subKey("library_sound")) {
			complete { SoundLibrary.values().withMap { name } }
			validate { SoundLibrary.values().any { it.name.equals(input, ignoreCase) } }
			translate { SoundLibrary.values().firstOrNull { it.name == input } }
		}

		@JvmStatic
		val MATERIAL = buildAsset(sparkle.subKey("material")) {
			complete { Material.values().withMap { name } }
			validate { Material.values().any { it.name.equals(input, ignoreCase) } }
			translate { Material.valueOf(input) }
		}

		@JvmStatic
		val MATERIAL_VARIANT = buildAsset(sparkle.subKey("material_variant")) {
			complete { DyeableMaterial.values().flatMap { ColorType.values().withMap { "$key#$name" } + it.name + it.key().asString() }.toSet() }
			validate { DyeableMaterial.materialFromMaterialCode(input) != null }
			translate { DyeableMaterial.materialFromMaterialCode(input) }
		}

		@JvmStatic
		val MATERIAL_CODE = buildAsset(sparkle.subKey("material_code")) {
			complete { Material.values().withMap { key.asString() } + DyeableMaterial.values().flatMap { dyeable ->
				ColorType.values().withMap { "${dyeable.key().asString()}#$name" } + dyeable.key().asString()
			} }
			validate { DyeableMaterial.materialFromMaterialCode(input) != null }
			translate { DyeableMaterial.materialFromMaterialCode(input) }
		}

		@JvmStatic
		val EXECUTOR_HEALTH = buildAsset(sparkle.subKey("executor_health")) {
			complete { if (executor is Player) listOf("${executor.health}") else listOf("20.0") }
			validate { if (executor is Player) input == "${executor.health}" else input == "20.0" }
			translate { input.toDouble() }
			dynamicAsset()
		}

		@JvmStatic
		val EXECUTOR_LOCATION = buildAsset(sparkle.subKey("executor_location")) {
			complete {
				LocationProvider.values().withMap { "@${name.lowercase()}" }
			}
			validate { LocationProvider.values().any { it.name.equals(input.removePrefix("@"), ignoreCase) } }
			translate { LocationProvider.values().firstOrNull { it.name.equals(input.removePrefix("@"), ignoreCase) }?.compute(executor.takeIfInstance(), executor.takeIfInstance<Player>()?.world ?: worlds.firstOrNull() ) }
		}

		@JvmStatic
		val LOADED_STRUCTURE = buildAsset(sparkle.subKey("structure_list")) {
			complete { structureManager.structures.keys.map { it.asString() } }
			validate { structureManager.structures.keys.any { it.asString().equals(input, ignoreCase) } }
			translate { structureManager.structures.firstOrNull { it.key.asString().equals(input, ignoreCase) }?.value }
			dynamicAsset()
		}

		@JvmStatic
		val STRUCTURE_ROTATION = buildAsset(sparkle.subKey("structure_rotation")) {
			complete { StructureRotation.values().withMap { name } }
			validate { StructureRotation.values().any { it.name.equals(input, ignoreCase) } }
			translate { StructureRotation.values().firstOrNull { it.name.equals(input, ignoreCase) } }
		}

		@JvmStatic
		val STRUCTURE_MIRROR = buildAsset(sparkle.subKey("structure_mirror")) {
			complete { Mirror.values().withMap { name } }
			validate { Mirror.values().any { it.name.equals(input, ignoreCase) } }
			translate { Mirror.values().firstOrNull { it.name.equals(input, ignoreCase) } }
		}

		@JvmStatic
		fun pageCompletion(pages: () -> Number) = buildAsset(sparkle.subKey("page")) {
			complete { (1..pages().toLong()).mapToString() }
			validate { input.toLongOrNull()?.let { it in (1..pages().toLong()) } == true }
			translate { input.toLongOrNull()?.takeIf { it in (1..pages().toLong()) } }
			dynamicAsset()
		}

		@JvmStatic
		fun files(path: Path, filter: (Path) -> Boolean = { true }, output: (String) -> String = { it }) = buildAsset(sparkle.subKey("file")) {
			complete {
				path.listDirectoryEntries().mapNotNull {
					if (filter(it)) {
						output(it.relativeToOrSelf(path).pathString)
					} else null
				}
			}
			validate { (path / input).exists() }
			translate { path / input }
			dynamicAsset()
		}

	}

	data class CompletionContext(
		val executor: InterchangeExecutor,
		val fullLineInput: List<String>,
		val input: String,
		val ignoreCase: Boolean,
	)

}
