package de.jet.paper.structure.command.completion.component

import de.jet.jvm.extension.container.mapToString
import de.jet.jvm.extension.container.withMap
import de.jet.jvm.extension.math.isDouble
import de.jet.jvm.extension.math.isLong
import de.jet.jvm.extension.tryOrNull
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.paper.app.JetCache
import de.jet.paper.app.JetData
import de.jet.paper.app.component.point.asset.Point
import de.jet.paper.extension.paper.getOfflinePlayer
import de.jet.paper.extension.paper.getPlayer
import de.jet.paper.extension.paper.offlinePlayers
import de.jet.paper.extension.paper.onlinePlayers
import de.jet.paper.extension.paper.worlds
import de.jet.paper.extension.system
import de.jet.paper.runtime.sandbox.SandBox
import de.jet.paper.structure.app.App
import de.jet.paper.structure.app.cache.CacheDepthLevel
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.jet.paper.structure.component.Component
import de.jet.paper.structure.service.Service
import de.jet.paper.tool.data.Preference
import de.jet.paper.tool.display.color.ColorType
import de.jet.paper.tool.display.color.DyeableMaterial
import de.jet.paper.tool.display.message.Transmission
import de.jet.paper.tool.smart.VendorsIdentifiable
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
		get() = if (!refreshing && JetCache.registeredCompletionAssetStateCache.containsKey(identity)) {
			JetCache.registeredCompletionAssetStateCache[identity]!!
		} else {
			generator(this).toSortedSet().apply {
				if (!refreshing) JetCache.registeredCompletionAssetStateCache[identity] = this
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
			JetCache.registeredApplications.withMap { identity }
		}.doCheck { input, ignoreCase ->
			JetCache.registeredApplications.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			JetCache.registeredApplications.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val INTERCHANGE = CompletionAsset<Interchange>(system, "INTERCHANGE", true) {
			JetCache.registeredInterchanges.withMap { identity }
		}.doCheck { input, ignoreCase ->
			JetCache.registeredInterchanges.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			JetCache.registeredInterchanges.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val SERVICE = CompletionAsset<Service>(system, "SERVICE", true) {
			JetCache.registeredServices.withMap { identity }
		}.doCheck { input, ignoreCase ->
			JetCache.registeredServices.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			JetCache.registeredServices.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val COMPONENT = CompletionAsset<Component>(system, "COMPONENT", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			JetCache.registeredComponents.withMap { identity }
		}.doCheck { input, ignoreCase ->
			JetCache.registeredComponents.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			JetCache.registeredComponents.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val SANDBOX = CompletionAsset<SandBox>(system, "SANDBOX", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			JetCache.registeredSandBoxes.withMap { identity }
		}.doCheck { input, ignoreCase ->
			JetCache.registeredSandBoxes.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			JetCache.registeredSandBoxes.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val PREFERENCE = CompletionAsset<Preference<*>>(system, "PREFERENCE", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			JetCache.registeredPreferences.keys.withMap { identity }
		}.doCheck { input, ignoreCase ->
			JetCache.registeredPreferences.keys.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			JetCache.registeredPreferences.toList().firstOrNull { it.first.identity == input }?.second
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
			JetData.savedPoints.content.points.map(Point::identity)
		}.doCheck { input, ignoreCase ->
			JetData.savedPoints.content.points.any { it.identity.equals(input, ignoreCase) }
		}.transformer { input ->
			JetData.savedPoints.content.points.firstOrNull { it.identity == input }
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
					add("jet:$key")
					addAll(ColorType.values().withMap { "jet:$key#$name" })
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
