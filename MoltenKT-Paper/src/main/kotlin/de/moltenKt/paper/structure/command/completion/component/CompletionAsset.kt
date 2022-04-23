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
import de.moltenKt.paper.tool.effect.sound.SoundLibrary
import de.moltenKt.paper.tool.smart.VendorsIdentifiable
import org.bukkit.Bukkit
import org.bukkit.Location
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

	fun computedContent(context: CompletionContext): Set<String> = if (!refreshing && MoltenCache.registeredCompletionAssetStateCache.containsKey(identity)) {
		MoltenCache.registeredCompletionAssetStateCache[identity]!!
	} else {
		generator(context, this).toSortedSet().apply {
			if (!refreshing) MoltenCache.registeredCompletionAssetStateCache[identity] = this
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
		val ONLINE_PLAYER_NAME = CompletionAsset<Player>(system, "ONLINE_PLAYER_NAME", true, listOf(InterchangeStructureInputRestriction.ONLINE_PLAYER)) {
			onlinePlayers.withMap { name }
		}.doCheck {
			player(input) != null
		}.transformer {
			player(input)
		}

		@JvmStatic
		val ONLINE_PLAYER_UUID = CompletionAsset<Player>(system, "ONLINE_PLAYER_UUID", true, listOf(InterchangeStructureInputRestriction.ONLINE_PLAYER)) {
			onlinePlayers.withMap { "$uniqueId" }
		}.doCheck {
			tryOrNull { UUID.fromString(input) }?.let { uuid -> return@let player(uuid) } != null
		}.transformer {
			tryOrNull { player(UUID.fromString(input)) }
		}

		@JvmStatic
		val OFFLINE_PLAYER_NAME = CompletionAsset<OfflinePlayer>(system, "OFFLINE_PLAYER_NAME", true, listOf(InterchangeStructureInputRestriction.OFFLINE_PLAYER)) {
			offlinePlayers.withMap { name }.filterNotNull()
		}.doCheck {
			offlinePlayer(input).name != null
		}.transformer {
			offlinePlayer(input)
		}

		@JvmStatic
		val OFFLINE_PLAYER_UUID = CompletionAsset<OfflinePlayer>(system, "OFFLINE_PLAYER_UUID", true, listOf(InterchangeStructureInputRestriction.OFFLINE_PLAYER)) {
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
			MoltenCache.registeredApplications.withMap { identity }
		}.doCheck {
			MoltenCache.registeredApplications.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			MoltenCache.registeredApplications.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val INTERCHANGE = CompletionAsset<Interchange>(system, "INTERCHANGE", true) {
			MoltenCache.registeredInterchanges.withMap { identity }
		}.doCheck {
			MoltenCache.registeredInterchanges.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			MoltenCache.registeredInterchanges.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val SERVICE = CompletionAsset<Service>(system, "SERVICE", true) {
			MoltenCache.registeredServices.withMap { identity }
		}.doCheck {
			MoltenCache.registeredServices.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			MoltenCache.registeredServices.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val COMPONENT = CompletionAsset<Component>(system, "COMPONENT", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			MoltenCache.registeredComponents.withMap { identity }
		}.doCheck {
			MoltenCache.registeredComponents.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			MoltenCache.registeredComponents.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val SANDBOX = CompletionAsset<SandBox>(system, "SANDBOX", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			MoltenCache.registeredSandBoxes.withMap { identity }
		}.doCheck {
			MoltenCache.registeredSandBoxes.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			MoltenCache.registeredSandBoxes.firstOrNull { it.identity == input }
		}

		@JvmStatic
		val PREFERENCE = CompletionAsset<Preference<*>>(system, "PREFERENCE", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			MoltenCache.registeredPreferences.keys.withMap { identity }
		}.doCheck {
			MoltenCache.registeredPreferences.keys.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			MoltenCache.registeredPreferences.toList().firstOrNull { it.first.identity == input }?.second
		}

		@JvmStatic
		val CACHE_DEPTH_LEVEL = CompletionAsset<CacheDepthLevel>(system, "CACHE_DEPTH_LEVEL", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			CacheDepthLevel.values().withMap { name }
		}.doCheck {
			CacheDepthLevel.values().any { it.name.equals(input, ignoreCase) }
		}.transformer {
			tryOrNull { CacheDepthLevel.valueOf(input) }
		}

		@JvmStatic
		val TRANSMISSION_LEVEL = CompletionAsset<Transmission.Level>(system, "TRANSMISSION_LEVEL", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			Transmission.Level.values().withMap { name }
		}.doCheck {
			Transmission.Level.values().any { it.name.equals(input, ignoreCase) }
		}.transformer {
			Transmission.Level.values().firstOrNull { it.name.equals(input, true) }
		}

		@JvmStatic
		val LIBRARY_SOUND_MELODY = CompletionAsset<SoundLibrary>(system, "LIBRARY_SOUND_MELODY", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			SoundLibrary.values().withMap { name }
		}.doCheck {
			SoundLibrary.values().any { it.name.equals(input, ignoreCase) }
		}.transformer {
			SoundLibrary.values().firstOrNull { it.name == input }
		}

		@JvmStatic
		val POINT = CompletionAsset<Point>(system, "POINT", true, listOf(InterchangeStructureInputRestriction.STRING)) {
			MoltenData.savedPoints.content.points.map(Point::identity)
		}.doCheck {
			MoltenData.savedPoints.content.points.any { it.identity.equals(input, ignoreCase) }
		}.transformer {
			MoltenData.savedPoints.content.points.firstOrNull { it.identity == input }
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
		val MATERIAL_VARIANT = CompletionAsset<Material>(system, "MATERIAL_VARIANT", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			buildSet {
				DyeableMaterial.values().forEach { flex ->
					val key = flex.key.toString()
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
		val MATERIAL_CODE = CompletionAsset<Material>(system, "MATERIAL_CODE", false, listOf(InterchangeStructureInputRestriction.STRING)) {
			buildSet {

				addAll(Material.values().withMap { "minecraft:$name" })

				DyeableMaterial.values().forEach { flex ->
					val key = flex.key.toString()
					add("MoltenKT:$key")
					addAll(ColorType.values().withMap { "MoltenKT:$key#$name" })
				}

			}
		}.doCheck {
			DyeableMaterial.materialFromMaterialCode(input) != null
		}.transformer {
			DyeableMaterial.materialFromMaterialCode(input)
		}

		@JvmStatic
		val EXECUTOR_HEALTH = CompletionAsset<Double>(system, "EXECUTOR_HEALTH", true, listOf(InterchangeStructureInputRestriction.DOUBLE)) {
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
		val EXECUTOR_LOCATION = CompletionAsset<Location>(system, "EXECUTOR_LOCATION", true, listOf(InterchangeStructureInputRestriction.STRING)) {
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
