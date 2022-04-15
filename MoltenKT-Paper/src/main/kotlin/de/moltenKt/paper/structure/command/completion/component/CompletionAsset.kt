package de.moltenKt.paper.structure.command.completion.component

import de.moltenKt.jvm.extension.container.mapToString
import de.moltenKt.jvm.extension.container.withMap
import de.moltenKt.jvm.extension.math.isDouble
import de.moltenKt.jvm.extension.math.isLong
import de.moltenKt.jvm.extension.tryOrNull
import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.app.MoltenData
import de.moltenKt.paper.app.component.point.asset.Point
import de.moltenKt.paper.extension.paper.getOfflinePlayer
import de.moltenKt.paper.extension.paper.getPlayer
import de.moltenKt.paper.extension.paper.offlinePlayers
import de.moltenKt.paper.extension.paper.onlinePlayers
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
	var check: ((input: String, ignoreCase: Boolean) -> Boolean)? = null,
	var transformer: ((input: String) -> T?)? = null,
	val generator: CompletionAsset<T>.() -> Collection<String>,
) : VendorsIdentifiable<CompletionAsset<T>> {

	override val vendorIdentity = vendor.identityObject

	val computedContent: Set<String>
		get() = if (!refreshing && MoltenCache.registeredCompletionAssetStateCache.containsKey(identity)) {
			MoltenCache.registeredCompletionAssetStateCache[identity]!!
		} else {
			generator(this).toSortedSet().apply {
				if (!refreshing) MoltenCache.registeredCompletionAssetStateCache[identity] = this
			}
		}

	fun doCheck(check: ((input: String, ignoreCase: Boolean) -> Boolean)?) = apply {
		this.check = check
	}

	fun transformer(transformer: ((input: String) -> T?)?) = apply {
		this.transformer = transformer
	}

	companion object {

		@JvmStatic
		val LONG = CompletionAsset<Long>(system, "LONG", false, listOf(InterchangeStructureInputRestriction.LONG)) {
			(0..99).mapToString()
		}.doCheck { input, _ ->
			input.isLong()
		}.transformer { input ->
			input.toLong()
		}

		@JvmStatic
		val DOUBLE = CompletionAsset<Double>(system, "DOUBLE", false, listOf(InterchangeStructureInputRestriction.DOUBLE)) {
			setOf(.0, .1, .2, .3, .4, .5, .6, .7, .8, .9, 1.0).mapToString()
		}.doCheck { input, _ ->
			input.isDouble()
		}.transformer { input ->
			input.toDouble()
		}

		@JvmStatic
		val ONLINE_PLAYER_NAME = CompletionAsset<Player>(system, "ONLINE_PLAYER_NAME", true, listOf(InterchangeStructureInputRestriction.ONLINE_PLAYER)) {
			onlinePlayers.withMap { name }
		}.doCheck { input, _ ->
			getPlayer(input) != null
		}.transformer { input ->
			getPlayer(input)
		}

		@JvmStatic
		val ONLINE_PLAYER_UUID = CompletionAsset<Player>(system, "ONLINE_PLAYER_UUID", true, listOf(InterchangeStructureInputRestriction.ONLINE_PLAYER)) {
			onlinePlayers.withMap { "$uniqueId" }
		}.doCheck { input, _ ->
			tryOrNull { UUID.fromString(input) }?.let { uuid -> return@let getPlayer(uuid) } != null
		}.transformer { input ->
			tryOrNull { getPlayer(UUID.fromString(input)) }
		}

		@JvmStatic
		val OFFLINE_PLAYER_NAME = CompletionAsset<OfflinePlayer>(system, "OFFLINE_PLAYER_NAME", true, listOf(InterchangeStructureInputRestriction.OFFLINE_PLAYER)) {
			offlinePlayers.withMap { name }.filterNotNull()
		}.doCheck { input, _ ->
			getOfflinePlayer(input).name != null
		}.transformer { input ->
			getOfflinePlayer(input)
		}

		@JvmStatic
		val OFFLINE_PLAYER_UUID = CompletionAsset<OfflinePlayer>(system, "OFFLINE_PLAYER_UUID", true, listOf(InterchangeStructureInputRestriction.OFFLINE_PLAYER)) {
			offlinePlayers.withMap { "$uniqueId" }
		}.doCheck { input, _ ->
			tryOrNull { getOfflinePlayer(UUID.fromString(input)).name } != null
		}.transformer { input ->
			tryOrNull { getOfflinePlayer(UUID.fromString(input)) }
		}

		@JvmStatic
		val ENTITY_TYPE = CompletionAsset<EntityType>(system, "ENTITY_TYPE", false) {
			EntityType.values().withMap { name }
		}.doCheck { input, ignoreCase ->
			EntityType.values().any { it.name.equals(input, ignoreCase) }
		}.transformer { input ->
			EntityType.valueOf(input)
		}

		@JvmStatic
		val WORLD_NAME = CompletionAsset<World>(system, "WORLD_NAME", true) {
			worlds.withMap { name }
		}.doCheck { input, ignoreCase ->
			worlds.any { it.name.equals(input, ignoreCase) }
		}.transformer { input ->
			Bukkit.getWorld(input)
		}

		@JvmStatic
		val APP = CompletionAsset<App>(system, "APP", true) {
			MoltenCache.registeredApplications.withMap { identity }
		}.doCheck { input, ignoreCase ->
			MoltenCache.registeredApplications.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			MoltenCache.registeredApplications.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val INTERCHANGE = CompletionAsset<Interchange>(system, "INTERCHANGE", true) {
			MoltenCache.registeredInterchanges.withMap { identity }
		}.doCheck { input, ignoreCase ->
			MoltenCache.registeredInterchanges.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			MoltenCache.registeredInterchanges.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val SERVICE = CompletionAsset<Service>(system, "SERVICE", true) {
			MoltenCache.registeredServices.withMap { identity }
		}.doCheck { input, ignoreCase ->
			MoltenCache.registeredServices.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			MoltenCache.registeredServices.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val COMPONENT = CompletionAsset<Component>(system, "COMPONENT", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			MoltenCache.registeredComponents.withMap { identity }
		}.doCheck { input, ignoreCase ->
			MoltenCache.registeredComponents.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			MoltenCache.registeredComponents.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val SANDBOX = CompletionAsset<SandBox>(system, "SANDBOX", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			MoltenCache.registeredSandBoxes.withMap { identity }
		}.doCheck { input, ignoreCase ->
			MoltenCache.registeredSandBoxes.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			MoltenCache.registeredSandBoxes.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val PREFERENCE = CompletionAsset<Preference<*>>(system, "PREFERENCE", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			MoltenCache.registeredPreferences.keys.withMap { identity }
		}.doCheck { input, ignoreCase ->
			MoltenCache.registeredPreferences.keys.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			MoltenCache.registeredPreferences.toList().firstOrNull { it.first.identity == input }?.second
		}

		@JvmStatic
		val CACHE_DEPTH_LEVEL = CompletionAsset<CacheDepthLevel>(system, "CACHE_DEPTH_LEVEL", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			CacheDepthLevel.values().withMap { name }
		}.doCheck { input, ignoreCase ->
			CacheDepthLevel.values().any { it.name.equals(input, ignoreCase) }
		}.transformer { input ->
			tryOrNull { CacheDepthLevel.valueOf(input) }
		}

		@JvmStatic
		val TRANSMISSION_LEVEL = CompletionAsset<Transmission.Level>(system, "TRANSMISSION_LEVEL", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			Transmission.Level.values().withMap { name }
		}.doCheck { input, ignoreCase ->
			Transmission.Level.values().any { it.name.equals(input, ignoreCase) }
		}.transformer { input ->
			tryOrNull { Transmission.Level.valueOf(input) }
		}

		@JvmStatic
		val POINT = CompletionAsset<Point>(system, "POINT", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			MoltenData.savedPoints.content.points.map(Point::identity)
		}.doCheck { input, ignoreCase ->
			MoltenData.savedPoints.content.points.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			MoltenData.savedPoints.content.points.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val MATERIAL = CompletionAsset<Material>(system, "MATERIAL", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			Material.values().withMap { name }
		}.doCheck { input, ignoreCase ->
			Material.values().any { it.name.equals(input, ignoreCase) }
		}.transformer { input ->
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
		}.doCheck { input, _ ->
			DyeableMaterial.materialFromMaterialCode(input) != null
		}.transformer { input ->
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
		}.doCheck { input, _ ->
			DyeableMaterial.materialFromMaterialCode(input) != null
		}.transformer { input ->
			DyeableMaterial.materialFromMaterialCode(input)
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
