package de.fruxz.sparkle.framework.infrastructure.command.completion.content

import de.fruxz.ascend.extension.container.firstOrNull
import de.fruxz.ascend.extension.container.mapToString
import de.fruxz.ascend.extension.container.withMap
import de.fruxz.ascend.extension.math.isDouble
import de.fruxz.ascend.extension.math.isLong
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.server.SparkleData
import de.fruxz.sparkle.server.component.point.asset.Point
import de.fruxz.sparkle.framework.util.sandbox.SandBox
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.cache.CacheDepthLevel
import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructureInputRestriction
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.framework.infrastructure.service.Service
import de.fruxz.sparkle.framework.util.data.Preference
import de.fruxz.sparkle.framework.util.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.util.extension.offlinePlayer
import de.fruxz.sparkle.framework.util.extension.offlinePlayers
import de.fruxz.sparkle.framework.util.extension.onlinePlayers
import de.fruxz.sparkle.framework.util.extension.playerOrNull
import de.fruxz.sparkle.framework.util.extension.worlds
import de.fruxz.sparkle.framework.util.extension.system
import de.fruxz.sparkle.framework.util.visual.color.ColorType
import de.fruxz.sparkle.framework.util.visual.color.DyeableMaterial
import de.fruxz.sparkle.framework.util.visual.message.Transmission
import de.fruxz.sparkle.framework.util.effect.sound.SoundLibrary
import de.fruxz.sparkle.framework.util.identification.VendorsIdentifiable
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.block.structure.Mirror
import org.bukkit.block.structure.StructureRotation
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.structure.Structure
import java.util.*

data class CompletionAsset<T>(
	val vendor: Identifiable<out App>,
	override val thisIdentity: String,
	val refreshing: Boolean,
	val supportedInputType: List<InterchangeStructureInputRestriction<*>> = listOf(InterchangeStructureInputRestriction.STRING),
	var check: (CompletionContext.() -> Boolean)? = null,
	var transformer: (CompletionContext.() -> T?)? = null,
	val generator: CompletionContext.(CompletionAsset<T>) -> Collection<String>,
) : VendorsIdentifiable<CompletionAsset<T>> {

	data class CompletionContext(
		val executor: InterchangeExecutor,
		val fullLineInput: List<String>,
		val input: String,
		val ignoreCase: Boolean,
	)

	override val vendorIdentity = vendor.identityObject

	fun computedContent(context: CompletionContext): Set<String> = if (!refreshing && SparkleCache.registeredCompletionAssetStateCache.containsKey(identity)) {
		SparkleCache.registeredCompletionAssetStateCache[identity]!!
	} else {
		generator(context, this).toSortedSet().apply {
			if (!refreshing) SparkleCache.registeredCompletionAssetStateCache += identity to this
		}
	}

	fun doCheck(check: (CompletionContext.() -> Boolean)?) = apply {
		this.check = check
	}

	fun transformer(transformer: (CompletionContext.() -> T?)?) = apply {
		this.transformer = transformer
	}

	companion object {

		@JvmStatic
		val LONG = CompletionAsset<Long>(system, "LONG", false, listOf(InterchangeStructureInputRestriction.LONG)) {
			(0..99).mapToString()
		}.doCheck {
			input.isLong()
		}.transformer {
			input.toLong()
		}

		@JvmStatic
		val DOUBLE = CompletionAsset<Double>(system, "DOUBLE", false, listOf(InterchangeStructureInputRestriction.DOUBLE)) {
			setOf(.0, .1, .2, .3, .4, .5, .6, .7, .8, .9, 1.0).mapToString()
		}.doCheck {
			input.isDouble()
		}.transformer {
			input.toDouble()
		}

		@JvmStatic
		val ONLINE_PLAYER_NAME = CompletionAsset<Player>(system, "ONLINE_PLAYER_NAME", true, listOf(
			InterchangeStructureInputRestriction.ONLINE_PLAYER)) {
			onlinePlayers.withMap { name }
		}.doCheck {
			playerOrNull(input) != null
		}.transformer {
			playerOrNull(input)
		}

		@JvmStatic
		val ONLINE_PLAYER_UUID = CompletionAsset<Player>(system, "ONLINE_PLAYER_UUID", true, listOf(
			InterchangeStructureInputRestriction.ONLINE_PLAYER)) {
			onlinePlayers.withMap { "$uniqueId" }
		}.doCheck {
			tryOrNull { UUID.fromString(input) }?.let { uuid -> return@let playerOrNull(uuid) } != null
		}.transformer {
			tryOrNull { playerOrNull(UUID.fromString(input)) }
		}

		@JvmStatic
		val OFFLINE_PLAYER_NAME = CompletionAsset<OfflinePlayer>(system, "OFFLINE_PLAYER_NAME", true, listOf(
			InterchangeStructureInputRestriction.OFFLINE_PLAYER)) {
			offlinePlayers.withMap { name }.filterNotNull()
		}.doCheck {
			offlinePlayer(input).name != null
		}.transformer {
			offlinePlayer(input)
		}

		@JvmStatic
		val OFFLINE_PLAYER_UUID = CompletionAsset<OfflinePlayer>(system, "OFFLINE_PLAYER_UUID", true, listOf(
			InterchangeStructureInputRestriction.OFFLINE_PLAYER)) {
			offlinePlayers.withMap { "$uniqueId" }
		}.doCheck {
			tryOrNull { offlinePlayer(UUID.fromString(input)).name } != null
		}.transformer {
			tryOrNull { offlinePlayer(UUID.fromString(input)) }
		}

		@JvmStatic
		val ENTITY_TYPE = CompletionAsset<EntityType>(system, "ENTITY_TYPE", false) {
			EntityType.values().withMap { name }
		}.doCheck {
			EntityType.values().any { it.name.equals(input, ignoreCase) }
		}.transformer {
			EntityType.valueOf(input)
		}

		@JvmStatic
		val WORLD_NAME = CompletionAsset<World>(system, "WORLD_NAME", true) {
			worlds.withMap { name }
		}.doCheck {
			worlds.any { it.name.equals(input, ignoreCase) }
		}.transformer {
			Bukkit.getWorld(input)
		}

		@JvmStatic
		val APP = CompletionAsset<App>(system, "APP", true) {
			SparkleCache.registeredApps.withMap { identity }
		}.doCheck {
			SparkleCache.registeredApps.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			SparkleCache.registeredApps.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val INTERCHANGE = CompletionAsset<Interchange>(system, "INTERCHANGE", true) {
			SparkleCache.registeredInterchanges.withMap { identity }
		}.doCheck {
			SparkleCache.registeredInterchanges.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			SparkleCache.registeredInterchanges.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val SERVICE = CompletionAsset<Service>(system, "SERVICE", true) {
			SparkleCache.registeredServices.withMap { identity }
		}.doCheck {
			SparkleCache.registeredServices.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			SparkleCache.registeredServices.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val COMPONENT = CompletionAsset<Component>(system, "COMPONENT", true, listOf(
			InterchangeStructureInputRestriction.STRING)) {
			SparkleCache.registeredComponents.withMap { identity }
		}.doCheck {
			SparkleCache.registeredComponents.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			SparkleCache.registeredComponents.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val SANDBOX = CompletionAsset<SandBox>(system, "SANDBOX", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			SparkleCache.registeredSandBoxes.withMap { identity }
		}.doCheck {
			SparkleCache.registeredSandBoxes.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			SparkleCache.registeredSandBoxes.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val PREFERENCE = CompletionAsset<Preference<*>>(system, "PREFERENCE", true, listOf(
			InterchangeStructureInputRestriction.STRING)) {
			SparkleCache.registeredPreferences.keys.withMap { identity }
		}.doCheck {
			SparkleCache.registeredPreferences.keys.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			SparkleCache.registeredPreferences.toList().firstOrNull { it.first.identity == input }?.second
		}

		@JvmStatic
		val CACHE_DEPTH_LEVEL = CompletionAsset<CacheDepthLevel>(system, "CACHE_DEPTH_LEVEL", false, listOf(
			InterchangeStructureInputRestriction.STRING)) {
			CacheDepthLevel.values().withMap { name }
		}.doCheck {
			CacheDepthLevel.values().any { it.name.equals(input, ignoreCase) }
		}.transformer {
			tryOrNull { CacheDepthLevel.valueOf(input) }
		}

		@JvmStatic
		val TRANSMISSION_LEVEL = CompletionAsset<Transmission.Level>(system, "TRANSMISSION_LEVEL", false, listOf(
			InterchangeStructureInputRestriction.STRING)) {
			Transmission.Level.values().withMap { name }
		}.doCheck {
			Transmission.Level.values().any { it.name.equals(input, ignoreCase) }
		}.transformer {
			Transmission.Level.values().firstOrNull { it.name.equals(input, true) }
		}

		@JvmStatic
		val LIBRARY_SOUND_MELODY = CompletionAsset<SoundLibrary>(system, "LIBRARY_SOUND_MELODY", false, listOf(
			InterchangeStructureInputRestriction.STRING)) {
			SoundLibrary.values().withMap { name }
		}.doCheck {
			SoundLibrary.values().any { it.name.equals(input, ignoreCase) }
		}.transformer {
			SoundLibrary.values().firstOrNull { it.name == input }
		}

		@JvmStatic
		val POINT = CompletionAsset<Point>(system, "POINT", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			SparkleData.savedPoints.content.points.map(Point::identity)
		}.doCheck {
			SparkleData.savedPoints.content.points.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			SparkleData.savedPoints.content.points.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val MATERIAL = CompletionAsset<Material>(system, "MATERIAL", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			Material.values().withMap { name }
		}.doCheck {
			Material.values().any { it.name.equals(input, ignoreCase) }
		}.transformer {
			tryOrNull { Material.valueOf(input) }
		}

		@JvmStatic
		val MATERIAL_VARIANT = CompletionAsset<Material>(system, "MATERIAL_VARIANT", false, listOf(
			InterchangeStructureInputRestriction.STRING)) {
			buildSet {
				DyeableMaterial.values().forEach { flex ->
					val key = flex.key().asString()
					add(key)
					add(flex.name)
					addAll(ColorType.values().withMap { "$key#$name" })
				}
			}
		}.doCheck {
			DyeableMaterial.materialFromMaterialCode(input) != null
		}.transformer {
			DyeableMaterial.materialFromMaterialCode(input)
		}

		@JvmStatic
		val MATERIAL_CODE = CompletionAsset<Material>(system, "MATERIAL_CODE", false, listOf(
			InterchangeStructureInputRestriction.STRING)) {
			buildSet {

				addAll(Material.values().withMap { key().asString() })

				DyeableMaterial.values().forEach { dyeable ->
					add(dyeable.key().asString())
					addAll(ColorType.values().withMap { "${dyeable.key().asString()}#$name" })
				}

			}
		}.doCheck {
			DyeableMaterial.materialFromMaterialCode(input) != null
		}.transformer {
			DyeableMaterial.materialFromMaterialCode(input)
		}

		@JvmStatic
		val EXECUTOR_HEALTH = CompletionAsset<Double>(system, "EXECUTOR_HEALTH", true, listOf(
			InterchangeStructureInputRestriction.DOUBLE)) {
			if (executor is Player) {
				listOf("${executor.health}")
			} else
				listOf("20.0")
		}.doCheck {
			if (executor is Player) input == "${executor.health}" else input == "20.0"
		}.transformer {
			input.toDouble()
		}

		@JvmStatic
		val EXECUTOR_LOCATION = CompletionAsset<Location>(system, "EXECUTOR_LOCATION", true, listOf(
			InterchangeStructureInputRestriction.STRING)) {
			if (executor is Player) {
				listOf(
					"@spawn",
					"@here",
					"@eyes",
					"@looking",
					"@bed",
					"@lastDamager",
					"@highestBlock",
					"@highestBlockAbove",
				)
			} else
				listOf("@spawn")
		}.doCheck {
			if (executor !is Player) input.equals("@spawn", ignoreCase) else setOf("spawn", "here", "eyes", "looking", "bed", "lastDamager", "highestBlock", "highestBlockBelow").any { input.equals("@$it", ignoreCase) }
		}.transformer {
			if (executor !is Player && input.equals("@spawn", ignoreCase)) {
				worlds[0].spawnLocation
			} else if (executor is Player) {
				when(input.removePrefix("@").lowercase()) {
					"spawn" -> worlds[0].spawnLocation
					"here" -> executor.location
					"eyes" -> executor.eyeLocation
					"looking" -> executor.rayTraceBlocks(100.0)?.hitBlock?.location ?: executor.eyeLocation
					"bed" -> executor.bedLocation
					"lastdamager" -> executor.lastDamageCause?.entity?.location ?: executor.location
					"highestblock" -> executor.location.toHighestLocation()
					"highestblockabove" -> executor.location.toHighestLocation().add(0.0, 1.0, 0.0)
					else -> null
				}
			} else
				null
		}

		@JvmStatic
		val LOADED_STRUCTURE = CompletionAsset<Structure>(system, "LOADED_STRUCTURE", true) {
			Bukkit.getStructureManager().structures.map { it.key.asString() }
		}.doCheck {
			Bukkit.getStructureManager().structures.any { it.key.asString() == input }
		}.transformer {
			Bukkit.getStructureManager().structures.firstOrNull { it.key.asString() == input }?.value
		}

		@JvmStatic
		val STRUCTURE_ROTATION = CompletionAsset<StructureRotation>(system, "STRUCTURE_ROTATION", false) {
			StructureRotation.values().withMap { name }
		}.doCheck {
			StructureRotation.values().any { it.name.equals(input, ignoreCase) }
		}.transformer {
			StructureRotation.values().firstOrNull { it.name.equals(input, ignoreCase) }
		}

		@JvmStatic
		val STRUCTURE_MIRROR = CompletionAsset<Mirror>(system, "STRUCTURE_MIRROR", false) {
			Mirror.values().withMap { name }
		}.doCheck {
			StructureRotation.values().any { it.name.equals(input, ignoreCase) }
		}.transformer {
			Mirror.values().firstOrNull { it.name.equals(input, ignoreCase) }
		}

		@JvmStatic
		fun pageCompletion(pages: () -> Number) = CompletionAsset<Number>(
			vendor = system,
			thisIdentity = "Page",
			true,
			listOf(InterchangeStructureInputRestriction.LONG),
			generator = {
				(1..pages().toLong()).mapToString()
			},
		)

	}

}
