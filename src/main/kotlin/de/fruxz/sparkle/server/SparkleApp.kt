package de.fruxz.sparkle.server

import com.destroystokyo.paper.ParticleBuilder
import de.fruxz.ascend.extension.data.addAscendJsonModuleModification
import de.fruxz.ascend.extension.forceCast
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.AppCompanion
import de.fruxz.sparkle.framework.data.Preference
import de.fruxz.sparkle.framework.data.json.JsonConfiguration
import de.fruxz.sparkle.framework.data.json.JsonFileDataElement
import de.fruxz.sparkle.framework.data.json.serializer.BoundingBoxSerializer
import de.fruxz.sparkle.framework.data.json.serializer.ItemStackSerializer
import de.fruxz.sparkle.framework.data.json.serializer.LocationSerializer
import de.fruxz.sparkle.framework.data.json.serializer.NamespacedKeySerializer
import de.fruxz.sparkle.framework.data.json.serializer.ParticleBuilderSerializer
import de.fruxz.sparkle.framework.data.json.serializer.ParticleSerializer
import de.fruxz.sparkle.framework.data.json.serializer.UUIDSerializer
import de.fruxz.sparkle.framework.data.json.serializer.VectorSerializer
import de.fruxz.sparkle.framework.data.json.serializer.WorldSerializer
import de.fruxz.sparkle.framework.effect.sound.SoundData
import de.fruxz.sparkle.framework.effect.sound.SoundEffect
import de.fruxz.sparkle.framework.effect.sound.SoundMelody
import de.fruxz.sparkle.framework.extension.asPlayerOrNull
import de.fruxz.sparkle.framework.extension.coroutines.doSync
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.mainLog
import de.fruxz.sparkle.framework.extension.quickSandBox
import de.fruxz.sparkle.framework.extension.visual.ui.set
import de.fruxz.sparkle.framework.extension.visual.ui.skull
import de.fruxz.sparkle.framework.mojang.MojangProfile
import de.fruxz.sparkle.framework.mojang.MojangProfileCape
import de.fruxz.sparkle.framework.mojang.MojangProfileRaw
import de.fruxz.sparkle.framework.mojang.MojangProfileSkin
import de.fruxz.sparkle.framework.mojang.MojangProfileTextures
import de.fruxz.sparkle.framework.mojang.MojangProfileUsernameHistoryEntry
import de.fruxz.sparkle.framework.permission.Approval
import de.fruxz.sparkle.framework.positioning.dependent.DependentComplexShape
import de.fruxz.sparkle.framework.positioning.dependent.DependentCubicalShape
import de.fruxz.sparkle.framework.positioning.dependent.DependentCylindricalShape
import de.fruxz.sparkle.framework.positioning.dependent.DependentLinearShape
import de.fruxz.sparkle.framework.positioning.dependent.DependentPyramidalShape
import de.fruxz.sparkle.framework.positioning.dependent.DependentShape
import de.fruxz.sparkle.framework.positioning.dependent.DependentSphericalShape
import de.fruxz.sparkle.framework.positioning.relative.CubicalShape
import de.fruxz.sparkle.framework.positioning.relative.CylindricalShape
import de.fruxz.sparkle.framework.positioning.relative.LinearShape
import de.fruxz.sparkle.framework.positioning.relative.PyramidalShape
import de.fruxz.sparkle.framework.positioning.relative.Shape
import de.fruxz.sparkle.framework.positioning.relative.SphereShape
import de.fruxz.sparkle.framework.positioning.world.SimpleLocation
import de.fruxz.sparkle.framework.visual.canvas.Canvas
import de.fruxz.sparkle.framework.visual.canvas.buildCanvas
import de.fruxz.sparkle.framework.visual.color.ColorType
import de.fruxz.sparkle.framework.visual.color.DyeableMaterial
import de.fruxz.sparkle.framework.visual.item.Modification
import de.fruxz.sparkle.server.SparkleApp.Infrastructure.SYSTEM_IDENTITY
import de.fruxz.sparkle.server.component.app.AppComponent
import de.fruxz.sparkle.server.component.completion.ProtectionComponent
import de.fruxz.sparkle.server.component.component.ComponentComponent
import de.fruxz.sparkle.server.component.events.EventsComponent
import de.fruxz.sparkle.server.component.keeper.KeeperComponent
import de.fruxz.sparkle.server.component.marking.MarkingComponent
import de.fruxz.sparkle.server.component.sandbox.SandBoxComponent
import de.fruxz.sparkle.server.component.service.ServiceComponent
import de.fruxz.sparkle.server.component.ui.actionbar.AdaptiveActionBarComponent
import de.fruxz.sparkle.server.component.ui.gui.UIComponent
import de.fruxz.sparkle.server.interchange.DebugModeInterchange
import de.fruxz.sparkle.server.interchange.PlaygroundInterchange
import de.fruxz.sparkle.server.interchange.SparkleInterchange
import de.fruxz.stacked.color.KotlinColor
import de.fruxz.stacked.extension.asComponent
import de.fruxz.stacked.extension.dye
import kotlinx.coroutines.cancel
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.event.inventory.InventoryType.BREWING
import org.bukkit.inventory.ItemStack
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector
import java.awt.Color
import java.util.*
import java.util.logging.Level
import kotlin.time.Duration.Companion.seconds
import de.fruxz.ascend.extension.data.addJsonContextualConfiguration as jsonContextual

class SparkleApp : App() {

	override val companion: Companion = Companion

	override val appIdentity: String = SYSTEM_IDENTITY
	override val label = "Sparkle"
	override val appCache: SparkleCache = SparkleCache

	override suspend fun preHello() {

		jsonContextual(UUID::class, UUIDSerializer)
		jsonContextual(Vector::class, VectorSerializer)
		jsonContextual(Location::class, LocationSerializer)
		jsonContextual(Particle::class, ParticleSerializer)
		jsonContextual(ParticleBuilder::class, ParticleBuilderSerializer)
		jsonContextual(ItemStack::class, ItemStackSerializer)
		jsonContextual(BoundingBox::class, BoundingBoxSerializer)
		jsonContextual(World::class, WorldSerializer)
		jsonContextual(NamespacedKey::class, NamespacedKeySerializer)

		addAscendJsonModuleModification {

			polymorphic(Any::class) {

				subclass(MojangProfile::class)
				subclass(MojangProfileCape::class)
				subclass(MojangProfileRaw::class)
				subclass(MojangProfileSkin::class)
				subclass(MojangProfileTextures::class)
				subclass(MojangProfileUsernameHistoryEntry::class)
				subclass(JsonConfiguration::class)
				subclass(JsonFileDataElement::class)
				subclass(Modification::class)
				subclass(SimpleLocation::class)
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

		ConfigurationSerialization.registerClass(SimpleLocation::class.java)

		debugLog("DebugMode preference loaded & set from file!")

	}

	override suspend fun hello() {

		debugMode = SparkleData.systemConfig.debugMode

		mainLog.info("""
				Sparkle is compiled & running with the Kotlin Language made by JetBrains. Special thanks to them!
				https://www.jetbrains.com/ | https://www.kotlinlang.org/
			""".trimIndent()
		)
		mainLog.info("""
			Sparkles coroutine-system is inspired by the okkero/Skedule repository, which is licensed under the MIT License!
			Definitely check out their repo: https://www.github.com/okkero/Skedule 
			""".trimIndent()
		)

		SparkleCache.tmp_initSetupPreferences.forEach {
			fun <T : Any> proceed(default: T) {
				val preference = it.forceCast<Preference<T>>()
				preference.content = default
				mainLog(Level.INFO, "Init-Setup '${preference.identity}' with '$default'(${default::class.simpleName})")
			}
			proceed(it.default)
		}

		SparkleCache.tmp_initSetupPreferences = emptySet()

		// NEW COMPONENTS
		add(EventsComponent())
		add(SandBoxComponent())
		add(ServiceComponent())
		add(KeeperComponent())
		add(MarkingComponent())
		add(UIComponent())
		add(ComponentComponent())
		add(ProtectionComponent())
		add(AdaptiveActionBarComponent())

		add(SparkleInterchange())
		add(DebugModeInterchange())
		add(PlaygroundInterchange())

		add(AppComponent())

		quickSandBox {
			buildCanvas {
				label("Test".asComponent dye KotlinColor(Color.ORANGE.darker()))
				base(BREWING)

				setDeferred(0, Canvas.DeferredComposable {
					skull("MHF_ArrowLeft")
				})

				onRender {
					it.renderResult[1] = DyeableMaterial.STAINED_GLASS_PANE.withColor(ColorType.values().random())
				}

			}.let { canvas ->

				canvas.display(executor.asPlayerOrNull).join()

				doSync(cycleDuration = 5.seconds) {
					if (canvas.viewers.isEmpty()) it.cancel()
					debugLog("Update trigger...")
					canvas.update(executor.asPlayerOrNull)
				}

			}
		}

	}

	companion object : AppCompanion<SparkleApp>() {

		@JvmStatic
		override val predictedIdentity = SYSTEM_IDENTITY

		@JvmStatic
		var debugMode: Boolean = true

	}

	object Infrastructure {

		@JvmStatic
		val SYSTEM_IDENTITY = "sparkle"

	}

}