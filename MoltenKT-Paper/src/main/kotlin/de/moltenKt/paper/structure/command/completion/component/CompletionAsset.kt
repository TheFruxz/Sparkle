package de.moltenKt.paper.structure.command.completion.component

import de.moltenKt.core.extension.container.mapToString
import de.moltenKt.core.extension.container.withMap
import de.moltenKt.core.extension.math.isDouble
import de.moltenKt.core.extension.math.isLong
import de.moltenKt.core.extension.tryOrNull
import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.app.MoltenData
import de.moltenKt.paper.app.component.point.asset.Point
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.extension.paper.offlinePlayer
import de.moltenKt.paper.extension.paper.offlinePlayers
import de.moltenKt.paper.extension.paper.onlinePlayers
import de.moltenKt.paper.extension.paper.player
import de.moltenKt.paper.extension.paper.worlds
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.runtime.sandbox.SandBox
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.app.cache.CacheDepthLevel
import de.moltenKt.paper.structure.command.Interchange
import de.moltenKt.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.moltenKt.paper.structure.component.Component
import de.moltenKt.paper.structure.service.Service
import de.moltenKt.paper.tool.data.Preference
import de.moltenKt.paper.tool.display.color.ColorType
import de.moltenKt.paper.tool.display.color.DyeableMaterial
import de.moltenKt.paper.tool.display.message.Transmission
import de.moltenKt.paper.tool.smart.VendorsIdentifiable
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.util.*

data class CompletionAsset<T>(
	val vendor: Identifiable<out App>,
	override val thisIdentity: String,
	val refreshing: Boolean,
	val supportedInputType: List<InterchangeStructureInputRestriction<*>> = listOf(InterchangeStructureInputRestriction.STRING),
	var check: ((executor: InterchangeExecutor, input: String, ignoreCase: Boolean) -> Boolean)? = null,
	var transformer: ((executor: InterchangeExecutor, input: String) -> T?)? = null,
	val generator: CompletionAsset<T>.(InterchangeExecutor) -> Collection<String>,
) : VendorsIdentifiable<CompletionAsset<T>> {

	override val vendorIdentity = vendor.identityObject

	fun computedContent(executor: InterchangeExecutor): Set<String> = if (!refreshing && MoltenCache.registeredCompletionAssetStateCache.containsKey(identity)) {
		MoltenCache.registeredCompletionAssetStateCache[identity]!!
	} else {
		generator(this, executor).toSortedSet().apply {
			if (!refreshing) MoltenCache.registeredCompletionAssetStateCache[identity] = this
		}
	}


	fun doCheck(check: ((executor: InterchangeExecutor, input: String, ignoreCase: Boolean) -> Boolean)?) = apply {
		this.check = check
	}

	fun transformer(transformer: ((executor: InterchangeExecutor, input: String) -> T?)?) = apply {
		this.transformer = transformer
	}

	companion object {

		@JvmStatic
		val LONG = CompletionAsset<Long>(system, "LONG", false, listOf(InterchangeStructureInputRestriction.LONG)) {
			(0..99).mapToString()
		}.doCheck { _, input, _ ->
			input.isLong()
		}.transformer { _, input ->
			input.toLong()
		}

		@JvmStatic
		val DOUBLE = CompletionAsset<Double>(system, "DOUBLE", false, listOf(InterchangeStructureInputRestriction.DOUBLE)) {
			setOf(.0, .1, .2, .3, .4, .5, .6, .7, .8, .9, 1.0).mapToString()
		}.doCheck { _, input, _ ->
			input.isDouble()
		}.transformer { _, input ->
			input.toDouble()
		}

		@JvmStatic
		val ONLINE_PLAYER_NAME = CompletionAsset<Player>(system, "ONLINE_PLAYER_NAME", true, listOf(InterchangeStructureInputRestriction.ONLINE_PLAYER)) {
			onlinePlayers.withMap { name }
		}.doCheck { _, input, _ ->
			player(input) != null
		}.transformer { _, input ->
			player(input)
		}

		@JvmStatic
		val ONLINE_PLAYER_UUID = CompletionAsset<Player>(system, "ONLINE_PLAYER_UUID", true, listOf(InterchangeStructureInputRestriction.ONLINE_PLAYER)) {
			onlinePlayers.withMap { "$uniqueId" }
		}.doCheck { _, input, _ ->
			tryOrNull { UUID.fromString(input) }?.let { uuid -> return@let player(uuid) } != null
		}.transformer { _, input ->
			tryOrNull { player(UUID.fromString(input)) }
		}

		@JvmStatic
		val OFFLINE_PLAYER_NAME = CompletionAsset<OfflinePlayer>(system, "OFFLINE_PLAYER_NAME", true, listOf(InterchangeStructureInputRestriction.OFFLINE_PLAYER)) {
			offlinePlayers.withMap { name }.filterNotNull()
		}.doCheck { _, input, _ ->
			offlinePlayer(input).name != null
		}.transformer { _, input ->
			offlinePlayer(input)
		}

		@JvmStatic
		val OFFLINE_PLAYER_UUID = CompletionAsset<OfflinePlayer>(system, "OFFLINE_PLAYER_UUID", true, listOf(InterchangeStructureInputRestriction.OFFLINE_PLAYER)) {
			offlinePlayers.withMap { "$uniqueId" }
		}.doCheck { _, input, _ ->
			tryOrNull { offlinePlayer(UUID.fromString(input)).name } != null
		}.transformer { _, input ->
			tryOrNull { offlinePlayer(UUID.fromString(input)) }
		}

		@JvmStatic
		val ENTITY_TYPE = CompletionAsset<EntityType>(system, "ENTITY_TYPE", false) {
			EntityType.values().withMap { name }
		}.doCheck { _, input, ignoreCase ->
			EntityType.values().any { it.name.equals(input, ignoreCase) }
		}.transformer { _, input ->
			EntityType.valueOf(input)
		}

		@JvmStatic
		val WORLD_NAME = CompletionAsset<World>(system, "WORLD_NAME", true) {
			worlds.withMap { name }
		}.doCheck { _, input, ignoreCase ->
			worlds.any { it.name.equals(input, ignoreCase) }
		}.transformer { _, input ->
			Bukkit.getWorld(input)
		}

		@JvmStatic
		val APP = CompletionAsset<App>(system, "APP", true) {
			MoltenCache.registeredApplications.withMap { identity }
		}.doCheck { _, input, ignoreCase ->
			MoltenCache.registeredApplications.any { it.identity.equals(input, ignoreCase) }
		}.transformer { _, input ->
			MoltenCache.registeredApplications.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val INTERCHANGE = CompletionAsset<Interchange>(system, "INTERCHANGE", true) {
			MoltenCache.registeredInterchanges.withMap { identity }
		}.doCheck { _, input, ignoreCase ->
			MoltenCache.registeredInterchanges.any { it.identity.equals(input, ignoreCase) }
		}.transformer { _, input ->
			MoltenCache.registeredInterchanges.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val SERVICE = CompletionAsset<Service>(system, "SERVICE", true) {
			MoltenCache.registeredServices.withMap { identity }
		}.doCheck { _, input, ignoreCase ->
			MoltenCache.registeredServices.any { it.identity.equals(input, ignoreCase) }
		}.transformer { _, input ->
			MoltenCache.registeredServices.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val COMPONENT = CompletionAsset<Component>(system, "COMPONENT", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			MoltenCache.registeredComponents.withMap { identity }
		}.doCheck { _, input, ignoreCase ->
			MoltenCache.registeredComponents.any { it.identity.equals(input, ignoreCase) }
		}.transformer { _, input ->
			MoltenCache.registeredComponents.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val SANDBOX = CompletionAsset<SandBox>(system, "SANDBOX", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			MoltenCache.registeredSandBoxes.withMap { identity }
		}.doCheck { _, input, ignoreCase ->
			MoltenCache.registeredSandBoxes.any { it.identity.equals(input, ignoreCase) }
		}.transformer { _, input ->
			MoltenCache.registeredSandBoxes.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val PREFERENCE = CompletionAsset<Preference<*>>(system, "PREFERENCE", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			MoltenCache.registeredPreferences.keys.withMap { identity }
		}.doCheck { _, input, ignoreCase ->
			MoltenCache.registeredPreferences.keys.any { it.identity.equals(input, ignoreCase) }
		}.transformer { _, input ->
			MoltenCache.registeredPreferences.toList().firstOrNull { it.first.identity == input }?.second
		}

		@JvmStatic
		val CACHE_DEPTH_LEVEL = CompletionAsset<CacheDepthLevel>(system, "CACHE_DEPTH_LEVEL", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			CacheDepthLevel.values().withMap { name }
		}.doCheck { _, input, ignoreCase ->
			CacheDepthLevel.values().any { it.name.equals(input, ignoreCase) }
		}.transformer { _, input ->
			tryOrNull { CacheDepthLevel.valueOf(input) }
		}

		@JvmStatic
		val TRANSMISSION_LEVEL = CompletionAsset<Transmission.Level>(system, "TRANSMISSION_LEVEL", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			Transmission.Level.values().withMap { name }
		}.doCheck { _, input, ignoreCase ->
			Transmission.Level.values().any { it.name.equals(input, ignoreCase) }
		}.transformer { _, input ->
			Transmission.Level.values().firstOrNull { it.name.equals(input, true) }
		}

		@JvmStatic
		val POINT = CompletionAsset<Point>(system, "POINT", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			MoltenData.savedPoints.content.points.map(Point::identity)
		}.doCheck { _, input, ignoreCase ->
			MoltenData.savedPoints.content.points.any { it.identity.equals(input, ignoreCase) }
		}.transformer { _, input ->
			MoltenData.savedPoints.content.points.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val MATERIAL = CompletionAsset<Material>(system, "MATERIAL", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			Material.values().withMap { name }
		}.doCheck { _, input, ignoreCase ->
			Material.values().any { it.name.equals(input, ignoreCase) }
		}.transformer { _, input ->
			tryOrNull { Material.valueOf(input) }
		}

		@JvmStatic
		val MATERIAL_VARIANT = CompletionAsset<Material>(system, "MATERIAL_VARIANT", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			buildSet {
				DyeableMaterial.values().forEach { flex ->
					val key = flex.key.toString()
					add(key)
					add(flex.name)
					addAll(ColorType.values().withMap { "$key#$name" })
				}
			}
		}.doCheck { _, input, _ ->
			DyeableMaterial.materialFromMaterialCode(input) != null
		}.transformer { _, input ->
			DyeableMaterial.materialFromMaterialCode(input)
		}

		@JvmStatic
		val MATERIAL_CODE = CompletionAsset<Material>(system, "MATERIAL_CODE", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			buildSet {

				addAll(Material.values().withMap { "minecraft:$name" })

				DyeableMaterial.values().forEach { flex ->
					val key = flex.key.toString()
					add("MoltenKT:$key")
					addAll(ColorType.values().withMap { "MoltenKT:$key#$name" })
				}

			}
		}.doCheck { _, input, _ ->
			DyeableMaterial.materialFromMaterialCode(input) != null
		}.transformer { _, input ->
			DyeableMaterial.materialFromMaterialCode(input)
		}

		@JvmStatic
		val EXECUTOR_HEALTH = CompletionAsset<Double>(system, "EXECUTOR_HEALTH", true, listOf(InterchangeStructureInputRestriction.DOUBLE)) {
			if (it is Player) {
				listOf("${it.health}")
			} else
				listOf("20.0")
		}.doCheck { executor, input, _ ->
			if (executor is Player) input == "${executor.health}" else input == "20.0"
		}.transformer { _, input ->
			input.toDouble()
		}

		@JvmStatic
		fun PAGES(pages: () -> Number) = CompletionAsset<Number>(
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
