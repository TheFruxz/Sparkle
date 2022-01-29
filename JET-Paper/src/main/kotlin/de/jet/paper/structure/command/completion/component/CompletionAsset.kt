package de.jet.paper.structure.command.completion.component

import de.jet.jvm.extension.collection.mapToString
import de.jet.jvm.extension.collection.withMap
import de.jet.jvm.extension.math.isDouble
import de.jet.jvm.extension.math.isLong
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.paper.app.JetCache
import de.jet.paper.app.JetData
import de.jet.paper.app.component.essentials.point.Point
import de.jet.paper.extension.paper.getPlayer
import de.jet.paper.extension.paper.onlinePlayers
import de.jet.paper.extension.paper.worlds
import de.jet.paper.extension.system
import de.jet.paper.structure.app.App
import de.jet.paper.structure.app.cache.CacheDepthLevel
import de.jet.paper.structure.command.completion.CompletionInputType
import de.jet.paper.structure.command.completion.CompletionInputType.STRING
import de.jet.paper.tool.display.color.ColorType
import de.jet.paper.tool.display.color.DyeableMaterial
import de.jet.paper.tool.display.message.Transmission
import de.jet.paper.tool.smart.VendorsIdentifiable
import org.bukkit.Material
import org.bukkit.entity.EntityType
import java.util.*

data class CompletionAsset(
	val vendor: Identifiable<out App>,
	override val thisIdentity: String,
	val refreshing: Boolean,
	val supportedInputType: List<CompletionInputType>,
	var check: ((input:String, ignoreCase: Boolean) -> Boolean)? = null,
	val generator: CompletionAsset.() -> Collection<String>,
) : VendorsIdentifiable<CompletionAsset> {

	override val vendorIdentity = vendor.identityObject

	val computedContent: SortedSet<String>
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

	companion object {

		val LONG = CompletionAsset(system, "LONG", false, listOf(CompletionInputType.LONG)) {
			(0..99).mapToString()
		}.doCheck { input, _ ->
			input.isLong()
		}

		val DOUBLE = CompletionAsset(system, "DOUBLE", false, listOf(CompletionInputType.DOUBLE)) {
			setOf(.0, .1, .2, .3, .4, .5, .6, .7, .8, .9, 1.0).mapToString()
		}.doCheck { input, _ ->
			input.isDouble()
		}

		val ONLINE_PLAYER_NAME = CompletionAsset(system, "ONLINE_PLAYER_NAME", true, listOf(STRING)) {
			onlinePlayers.withMap { name }
		}.doCheck { input, _ ->
			getPlayer(input) != null
		}

		val ONLINE_PLAYER_UUID = CompletionAsset(system, "ONLINE_PLAYER_UUID", true, listOf(STRING)) {
			onlinePlayers.withMap { "$uniqueId" }
		}

		val ENTITY_TYPE = CompletionAsset(system, "ENTITY_TYPE", false, listOf(STRING)) {
			EntityType.values().withMap { name }
		}.doCheck { input, ignoreCase ->
			EntityType.values().any { it.name.equals(input, ignoreCase) }
		}

		val WORLD_NAME = CompletionAsset(system, "WORLD_NAME", true, listOf(STRING)) {
			worlds.withMap { name }
		}.doCheck { input, ignoreCase ->
			worlds.any { it.name.equals(input, ignoreCase) }
		}

		val APP = CompletionAsset(system, "APP", true, listOf(STRING)) {
			JetCache.registeredApplications.withMap { identity }
		}.doCheck { input, ignoreCase ->
			JetCache.registeredApplications.any { it.identity.equals(input, ignoreCase) }
		}

		val INTERCHANGE = CompletionAsset(system, "INTERCHANGE", true, listOf(STRING)) {
			JetCache.registeredInterchanges.withMap { identity }
		}.doCheck { input, ignoreCase ->
			JetCache.registeredInterchanges.any { it.identity.equals(input, ignoreCase) }
		}

		val SERVICE = CompletionAsset(system, "SERVICE", true, listOf(STRING)) {
			JetCache.registeredServices.withMap { identity }
		}.doCheck { input, ignoreCase ->
			JetCache.registeredServices.any { it.identity.equals(input, ignoreCase) }
		}

		val COMPONENT = CompletionAsset(system, "COMPONENT", true, listOf(STRING)) {
			JetCache.registeredComponents.withMap { identity }
		}.doCheck { input, ignoreCase ->
			JetCache.registeredComponents.any { it.identity.equals(input, ignoreCase) }
		}

		val SANDBOX = CompletionAsset(system, "SANDBOX", true, listOf(STRING)) {
			JetCache.registeredSandBoxes.withMap { identity }
		}.doCheck { input, ignoreCase ->
			JetCache.registeredSandBoxes.any { it.identity.equals(input, ignoreCase) }
		}

		val PREFERENCE = CompletionAsset(system, "PREFERENCE", true, listOf(STRING)) {
			JetCache.registeredPreferences.keys.withMap { identity }
		}.doCheck { input, ignoreCase ->
			JetCache.registeredPreferences.keys.any { it.identity.equals(input, ignoreCase) }
		}

		val CACHE_DEPTH_LEVEL = CompletionAsset(system, "CACHE_DEPTH_LEVEL", false, listOf(STRING)) {
			CacheDepthLevel.values().withMap { name }
		}.doCheck { input, ignoreCase ->
			CacheDepthLevel.values().any { it.name.equals(input, ignoreCase) }
		}

		val TRANSMISSION_LEVEL = CompletionAsset(system, "TRANSMISSION_LEVEL", false, listOf(STRING)) {
			Transmission.Level.values().withMap { name }
		}.doCheck { input, ignoreCase ->
			Transmission.Level.values().any { it.name.equals(input, ignoreCase) }
		}

		val POINT = CompletionAsset(system, "POINT", true, listOf(STRING)) {
			JetData.savedPoints.content.points.map(Point::identity)
		}.doCheck { input, ignoreCase ->
			JetData.savedPoints.content.points.any { it.identity.equals(input, ignoreCase) }
		}

		val MATERIAL = CompletionAsset(system, "MATERIAL", false, listOf(STRING)) {
			Material.values().withMap { name }
		}.doCheck { input, ignoreCase ->
			Material.values().any { it.name.equals(input, ignoreCase) }
		}

		val MATERIAL_VARIANT = CompletionAsset(system, "MATERIAL_VARIANT", false, listOf(STRING)) {
			buildSet {
				DyeableMaterial.values().forEach { flex ->
					val key = flex.key.toString()
					add(key)
					addAll(ColorType.values().withMap { "$key#$name" })
				}
			}
		}.doCheck { input, _ ->
			DyeableMaterial.materialFromMaterialCode(input) != null
		}

		val MATERIAL_CODE = CompletionAsset(system, "MATERIAL_CODE", false, listOf(STRING)) {
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
		}

	}

}
