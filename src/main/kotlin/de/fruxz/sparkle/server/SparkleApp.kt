package de.fruxz.sparkle.server

import com.destroystokyo.paper.ParticleBuilder
import de.fruxz.ascend.extension.forceCast
import de.fruxz.ascend.json.appendGlobalJsonContextual
import de.fruxz.ascend.json.appendGlobalJsonModuleModification
import de.fruxz.sparkle.framework.data.Preference
import de.fruxz.sparkle.framework.data.json.JsonConfiguration
import de.fruxz.sparkle.framework.data.json.serializer.*
import de.fruxz.sparkle.framework.effect.sound.SoundData
import de.fruxz.sparkle.framework.effect.sound.SoundEffect
import de.fruxz.sparkle.framework.effect.sound.SoundMelody
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.mainLog
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.AppCompanion
import de.fruxz.sparkle.framework.infrastructure.app.update.AppUpdater
import de.fruxz.sparkle.framework.mojang.*
import de.fruxz.sparkle.framework.permission.Approval
import de.fruxz.sparkle.framework.positioning.dependent.*
import de.fruxz.sparkle.framework.positioning.relative.*
import de.fruxz.sparkle.framework.positioning.world.LazyLocation
import de.fruxz.sparkle.framework.visual.item.Item
import de.fruxz.sparkle.framework.visual.item.Modification
import de.fruxz.sparkle.server.component.app.AppComponent
import de.fruxz.sparkle.server.component.component.ComponentComponent
import de.fruxz.sparkle.server.component.events.EventsComponent
import de.fruxz.sparkle.server.component.keeper.KeeperComponent
import de.fruxz.sparkle.server.component.marking.MarkerComponent
import de.fruxz.sparkle.server.component.sandbox.SandBoxComponent
import de.fruxz.sparkle.server.component.service.ServiceComponent
import de.fruxz.sparkle.server.component.ui.actionbar.AdaptiveActionBarComponent
import de.fruxz.sparkle.server.component.ui.gui.UIComponent
import de.fruxz.sparkle.server.component.update.UpdateComponent
import de.fruxz.sparkle.server.interchange.DebugModeInterchange
import de.fruxz.sparkle.server.interchange.PlaygroundInterchange
import de.fruxz.sparkle.server.interchange.SparkleInterchange
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import net.kyori.adventure.key.Key
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.inventory.ItemStack
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector
import java.util.*

class SparkleApp : App() {

	override val companion: Companion = Companion
	override val appCache: SparkleCache = SparkleCache
	override val updater = AppUpdater.github("TheFruxz/Sparkle") { it.contains("Runnable", true) }

	override suspend fun preHello() {

		appendGlobalJsonContextual(UUID::class, UUIDSerializer)
		appendGlobalJsonContextual(Vector::class, VectorSerializer)
		appendGlobalJsonContextual(Location::class, LocationSerializer)
		appendGlobalJsonContextual(Particle::class, ParticleSerializer)
		appendGlobalJsonContextual(ParticleBuilder::class, ParticleBuilderSerializer)
		appendGlobalJsonContextual(ItemStack::class, ItemStackSerializer)
		appendGlobalJsonContextual(Item::class, ItemSerializer)
		appendGlobalJsonContextual(BoundingBox::class, BoundingBoxSerializer)
		appendGlobalJsonContextual(World::class, WorldSerializer)
		appendGlobalJsonContextual(NamespacedKey::class, NamespacedKeySerializer)
		appendGlobalJsonContextual(Key::class, AdventureKeySerializer)

		appendGlobalJsonModuleModification {

			polymorphic(Any::class) {

				subclass(MojangProfile::class)
				subclass(MojangProfileCape::class)
				subclass(MojangProfileRaw::class)
				subclass(MojangProfileSkin::class)
				subclass(MojangProfileTextures::class)
				subclass(MojangProfileUsernameHistoryEntry::class)
				subclass(JsonConfiguration::class)
				subclass(Modification::class)
				subclass(LazyLocation::class)
				subclass(SoundData::class)
				subclass(Approval::class)

				polymorphic(Shape::class) {

					polymorphic(DependentShape::class) {

						subclass(DependentComplexShape::class)
						subclass(DependentCubicalShape::class)
						subclass(DependentCylindricalShape::class)
						subclass(DependentLinearShape::class)
						subclass(DependentPyramidalShape::class)
						subclass(DependentSphericalShape::class)

					}

					polymorphic(CubicalShape::class) { subclass(DependentCubicalShape::class) }
					polymorphic(CylindricalShape::class) { subclass(DependentCylindricalShape::class) }
					polymorphic(LinearShape::class) { subclass(DependentLinearShape::class) }
					polymorphic(PyramidalShape::class) { subclass(DependentPyramidalShape::class) }
					polymorphic(SphereShape::class) { subclass(DependentSphericalShape::class) }

				}

				polymorphic(SoundEffect::class) {

					subclass(SoundData::class)
					subclass(SoundMelody::class)

				}

			}
		}

		register(LazyLocation::class)

	}

	override suspend fun hello() {

		debugMode = SparkleData.systemConfig.debugMode.also {
			debugLog("DebugMode preference loaded & set from file!")
		}

		mainLog.info("""
			
			Sparkle is compiled & running with the Kotlin Language made by JetBrains. Special thanks to them!
			https://www.jetbrains.com/ | https://www.kotlinlang.org/
			
			Sparkles coroutine-system is inspired by the okkero/Skedule repository, which is licensed under the MIT License!
			Definitely check out their repo: https://www.github.com/okkero/Skedule
			
			""".trimIndent()
		)

		SparkleCache.tmp_initSetupPreferences.forEach {
			fun <T : Any> proceed(default: T) {
				val preference = it.forceCast<Preference<T>>()
				preference.content = default
				mainLog.info("Init-Setup '${preference.identity}' with '$default'(${default::class.simpleName})")
			}
			proceed(it.default)
		}

		SparkleCache.tmp_initSetupPreferences = emptySet()

		// NEW COMPONENTS
		add(AppComponent())
		add(ComponentComponent())
		add(ServiceComponent())
		add(EventsComponent())
		add(UIComponent())
		add(SandBoxComponent())
		add(AdaptiveActionBarComponent())
		add(KeeperComponent())
		add(MarkerComponent())
		add(UpdateComponent())

		add(SparkleInterchange())
		add(DebugModeInterchange())
		add(PlaygroundInterchange())

	}

	companion object : AppCompanion<SparkleApp>() {

		@JvmStatic
		var debugMode: Boolean = true

	}

	object Infrastructure {

		@JvmStatic
		val SYSTEM_IDENTITY = "sparkle"

		@JvmStatic
		val SPARKLE_ICON = "\uD83D\uDD25"

	}

}