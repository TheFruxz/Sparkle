package de.fruxz.sparkle.app

import com.destroystokyo.paper.ParticleBuilder
import de.fruxz.ascend.extension.data.addAscendJsonModuleModification
import de.fruxz.ascend.extension.forceCast
import de.fruxz.sparkle.app.MoltenApp.Infrastructure.SYSTEM_IDENTITY
import de.fruxz.sparkle.app.component.app.AppComponent
import de.fruxz.sparkle.app.component.buildMode.BuildModeComponent
import de.fruxz.sparkle.app.component.chat.ChatComponent
import de.fruxz.sparkle.app.component.completion.ProtectionComponent
import de.fruxz.sparkle.app.component.component.ComponentComponent
import de.fruxz.sparkle.app.component.events.EventsComponent
import de.fruxz.sparkle.app.component.experimental.ExperimentalComponent
import de.fruxz.sparkle.app.component.keeper.KeeperComponent
import de.fruxz.sparkle.app.component.marking.MarkingComponent
import de.fruxz.sparkle.app.component.messaging.MessagingComponent
import de.fruxz.sparkle.app.component.point.PointComponent
import de.fruxz.sparkle.app.component.point.asset.Point
import de.fruxz.sparkle.app.component.point.asset.PointConfig
import de.fruxz.sparkle.app.component.sandbox.SandBoxComponent
import de.fruxz.sparkle.app.component.service.ServiceComponent
import de.fruxz.sparkle.app.component.ui.actionbar.AdaptiveActionBarComponent
import de.fruxz.sparkle.app.component.ui.gui.UIComponent
import de.fruxz.sparkle.app.interchange.DebugModeInterchange
import de.fruxz.sparkle.app.interchange.MoltenKtInterchange
import de.fruxz.sparkle.app.interchange.PlaygroundInterchange
import de.fruxz.sparkle.extension.debugLog
import de.fruxz.sparkle.extension.mainLog
import de.fruxz.sparkle.mojang.MojangProfile
import de.fruxz.sparkle.mojang.MojangProfileCape
import de.fruxz.sparkle.mojang.MojangProfileRaw
import de.fruxz.sparkle.mojang.MojangProfileSkin
import de.fruxz.sparkle.mojang.MojangProfileTextures
import de.fruxz.sparkle.mojang.MojangProfileUsernameHistoryEntry
import de.fruxz.sparkle.structure.app.App
import de.fruxz.sparkle.structure.app.AppCompanion
import de.fruxz.sparkle.tool.data.Preference
import de.fruxz.sparkle.tool.data.json.JsonConfiguration
import de.fruxz.sparkle.tool.data.json.JsonFileDataElement
import de.fruxz.sparkle.tool.data.json.serializer.BoundingBoxSerializer
import de.fruxz.sparkle.tool.data.json.serializer.ItemStackSerializer
import de.fruxz.sparkle.tool.data.json.serializer.LocationSerializer
import de.fruxz.sparkle.tool.data.json.serializer.NamespacedKeySerializer
import de.fruxz.sparkle.tool.data.json.serializer.ParticleBuilderSerializer
import de.fruxz.sparkle.tool.data.json.serializer.ParticleSerializer
import de.fruxz.sparkle.tool.data.json.serializer.UUIDSerializer
import de.fruxz.sparkle.tool.data.json.serializer.VectorSerializer
import de.fruxz.sparkle.tool.data.json.serializer.WorldSerializer
import de.fruxz.sparkle.tool.display.item.Modification
import de.fruxz.sparkle.tool.display.world.SimpleLocation
import de.fruxz.sparkle.tool.effect.sound.SoundData
import de.fruxz.sparkle.tool.effect.sound.SoundEffect
import de.fruxz.sparkle.tool.effect.sound.SoundMelody
import de.fruxz.sparkle.tool.permission.Approval
import de.fruxz.sparkle.tool.position.dependent.DependentComplexShape
import de.fruxz.sparkle.tool.position.dependent.DependentCubicalShape
import de.fruxz.sparkle.tool.position.dependent.DependentCylindricalShape
import de.fruxz.sparkle.tool.position.dependent.DependentLinearShape
import de.fruxz.sparkle.tool.position.dependent.DependentPyramidalShape
import de.fruxz.sparkle.tool.position.dependent.DependentShape
import de.fruxz.sparkle.tool.position.dependent.DependentSphericalShape
import de.fruxz.sparkle.tool.position.relative.CubicalShape
import de.fruxz.sparkle.tool.position.relative.CylindricalShape
import de.fruxz.sparkle.tool.position.relative.LinearShape
import de.fruxz.sparkle.tool.position.relative.PyramidalShape
import de.fruxz.sparkle.tool.position.relative.Shape
import de.fruxz.sparkle.tool.position.relative.SphereShape
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.inventory.ItemStack
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector
import java.util.*
import java.util.logging.Level
import de.fruxz.ascend.extension.data.addJsonContextualConfiguration as jsonContextual

class MoltenApp : App() {

	override val companion: de.fruxz.sparkle.app.MoltenApp.Companion = de.fruxz.sparkle.app.MoltenApp.Companion

	override val appIdentity: String = SYSTEM_IDENTITY
	override val label = "Molten-Kt"
	override val appCache: de.fruxz.sparkle.app.MoltenCache = de.fruxz.sparkle.app.MoltenCache

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

				subclass(Point::class)
				subclass(PointConfig::class)
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

		// TODO: 19.10.2021 load language files to the lang-folder

		debugLog("DebugMode preference loaded & set from file!")

	}

	override suspend fun hello() {

		de.fruxz.sparkle.app.MoltenApp.Companion.debugMode = de.fruxz.sparkle.app.MoltenData.systemConfig.debugMode

		mainLog(
			Level.INFO, """
			Molten-Kt is compiled & running with the Kotlin Language made by JetBrains. Special thanks to them!
			https://www.jetbrains.com/ | https://kotlinlang.org/
		""".trimIndent()
		)

		de.fruxz.sparkle.app.MoltenCache.tmp_initSetupPreferences.forEach {
			fun <T : Any> proceed(default: T) {
				val preference = it.forceCast<Preference<T>>()
				preference.content = default
				mainLog(Level.INFO, "Init-Setup '${preference.identity}' with '$default'(${default::class.simpleName})")
			}
			proceed(it.default)
		}

		de.fruxz.sparkle.app.MoltenCache.tmp_initSetupPreferences = emptySet()

		// NEW COMPONENTS
		add(ChatComponent())
		add(EventsComponent())
		add(ExperimentalComponent())
		add(SandBoxComponent())
		add(ServiceComponent())
		add(KeeperComponent())
		add(PointComponent())
		add(BuildModeComponent())
		add(MarkingComponent())
		add(UIComponent())
		add(ComponentComponent())
		add(ProtectionComponent())
		add(MessagingComponent())
		add(AdaptiveActionBarComponent())

		add(MoltenKtInterchange())
		add(DebugModeInterchange())
		add(PlaygroundInterchange())

		add(AppComponent())

	}

	companion object : AppCompanion<de.fruxz.sparkle.app.MoltenApp>() {

		@JvmStatic
		override val predictedIdentity = SYSTEM_IDENTITY

		@JvmStatic
		var debugMode: Boolean = true

	}

	object Infrastructure {

		@JvmStatic
		val SYSTEM_IDENTITY = "molten-kt"

	}

}