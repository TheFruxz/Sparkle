package de.moltenKt.paper.app

import com.destroystokyo.paper.ParticleBuilder
import de.moltenKt.core.extension.data.addMoltenJsonModuleModification
import de.moltenKt.core.extension.forceCast
import de.moltenKt.paper.app.MoltenApp.Infrastructure.SYSTEM_IDENTITY
import de.moltenKt.paper.app.component.app.AppComponent
import de.moltenKt.paper.app.component.buildMode.BuildModeComponent
import de.moltenKt.paper.app.component.chat.ChatComponent
import de.moltenKt.paper.app.component.completion.ProtectionComponent
import de.moltenKt.paper.app.component.component.ComponentComponent
import de.moltenKt.paper.app.component.events.EventsComponent
import de.moltenKt.paper.app.component.experimental.ExperimentalComponent
import de.moltenKt.paper.app.component.keeper.KeeperComponent
import de.moltenKt.paper.app.component.marking.MarkingComponent
import de.moltenKt.paper.app.component.messaging.MessagingComponent
import de.moltenKt.paper.app.component.point.PointComponent
import de.moltenKt.paper.app.component.point.asset.Point
import de.moltenKt.paper.app.component.point.asset.PointConfig
import de.moltenKt.paper.app.component.sandbox.SandBoxComponent
import de.moltenKt.paper.app.component.service.ServiceComponent
import de.moltenKt.paper.app.component.ui.actionbar.AdaptiveActionBarComponent
import de.moltenKt.paper.app.component.ui.gui.UIComponent
import de.moltenKt.paper.app.interchange.DebugModeInterchange
import de.moltenKt.paper.app.interchange.MoltenKtInterchange
import de.moltenKt.paper.app.interchange.PlaygroundInterchange
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.extension.mainLog
import de.moltenKt.paper.mojang.MojangProfile
import de.moltenKt.paper.mojang.MojangProfileCape
import de.moltenKt.paper.mojang.MojangProfileRaw
import de.moltenKt.paper.mojang.MojangProfileSkin
import de.moltenKt.paper.mojang.MojangProfileTextures
import de.moltenKt.paper.mojang.MojangProfileUsernameHistoryEntry
import de.moltenKt.paper.runtime.app.LanguageSpeaker.LanguageContainer
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.app.AppCompanion
import de.moltenKt.paper.tool.data.Preference
import de.moltenKt.paper.tool.data.json.JsonConfiguration
import de.moltenKt.paper.tool.data.json.JsonFileDataElement
import de.moltenKt.paper.tool.data.json.serializer.BoundingBoxSerializer
import de.moltenKt.paper.tool.data.json.serializer.ItemStackSerializer
import de.moltenKt.paper.tool.data.json.serializer.LocationSerializer
import de.moltenKt.paper.tool.data.json.serializer.NamespacedKeySerializer
import de.moltenKt.paper.tool.data.json.serializer.ParticleBuilderSerializer
import de.moltenKt.paper.tool.data.json.serializer.ParticleSerializer
import de.moltenKt.paper.tool.data.json.serializer.UUIDSerializer
import de.moltenKt.paper.tool.data.json.serializer.VectorSerializer
import de.moltenKt.paper.tool.data.json.serializer.WorldSerializer
import de.moltenKt.paper.tool.display.item.Modification
import de.moltenKt.paper.tool.display.world.SimpleLocation
import de.moltenKt.paper.tool.effect.particle.ParticleType.Companion
import de.moltenKt.paper.tool.effect.sound.SoundData
import de.moltenKt.paper.tool.effect.sound.SoundEffect
import de.moltenKt.paper.tool.effect.sound.SoundMelody
import de.moltenKt.paper.tool.permission.Approval
import de.moltenKt.paper.tool.position.dependent.DependentComplexShape
import de.moltenKt.paper.tool.position.dependent.DependentCubicalShape
import de.moltenKt.paper.tool.position.dependent.DependentCylindricalShape
import de.moltenKt.paper.tool.position.dependent.DependentLinearShape
import de.moltenKt.paper.tool.position.dependent.DependentPyramidalShape
import de.moltenKt.paper.tool.position.dependent.DependentShape
import de.moltenKt.paper.tool.position.dependent.DependentSphericalShape
import de.moltenKt.paper.tool.position.relative.CubicalShape
import de.moltenKt.paper.tool.position.relative.CylindricalShape
import de.moltenKt.paper.tool.position.relative.LinearShape
import de.moltenKt.paper.tool.position.relative.PyramidalShape
import de.moltenKt.paper.tool.position.relative.Shape
import de.moltenKt.paper.tool.position.relative.SphereShape
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
import de.moltenKt.core.extension.data.addJsonContextualConfiguration as jsonContextual

class MoltenApp : App() {

	override val companion: Companion = Companion

	override val appIdentity: String = SYSTEM_IDENTITY
	override val label = "Molten-Kt"
	override val appCache: MoltenCache = MoltenCache

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

		addMoltenJsonModuleModification {

			polymorphic(Any::class) {

				subclass(Point::class)
				subclass(PointConfig::class)
				subclass(MojangProfile::class)
				subclass(MojangProfileCape::class)
				subclass(MojangProfileRaw::class)
				subclass(MojangProfileSkin::class)
				subclass(MojangProfileTextures::class)
				subclass(MojangProfileUsernameHistoryEntry::class)
				subclass(LanguageContainer::class)
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

		debugMode = MoltenData.systemConfig.debugMode

		mainLog(
			Level.INFO, """
			Molten-Kt is compiled & running with the Kotlin Language made by JetBrains. Special thanks to them!
			https://www.jetbrains.com/ | https://kotlinlang.org/
		""".trimIndent()
		)

		MoltenCache.tmp_initSetupPreferences.forEach {
			fun <T : Any> proceed(default: T) {
				val preference = it.forceCast<Preference<T>>()
				preference.content = default
				mainLog(Level.INFO, "Init-Setup '${preference.identity}' with '$default'(${default::class.simpleName})")
			}
			proceed(it.default)
		}

		MoltenCache.tmp_initSetupPreferences = emptySet()

		languageSpeaker.let { languageSpeaker ->
			mainLog(Level.INFO, "Speaking langauge: ${languageSpeaker.baseLang}")
			with(languageSpeaker.languageContainer) {
				"""
					Display-Language detected:
					ID: ${this.languageId};
					Molten-Kt: ${this.moltenVersion};
					Version: ${this.languageVersion};
					Vendor: ${this.languageVendor};
					Website: ${this.languageVendorWebsite};
					Test: ${languageSpeaker.message("system.hello")};
				""".trimIndent().lines().forEach {
					mainLog(Level.INFO, it)
				}
			}
		}

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

	companion object : AppCompanion<MoltenApp>() {

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