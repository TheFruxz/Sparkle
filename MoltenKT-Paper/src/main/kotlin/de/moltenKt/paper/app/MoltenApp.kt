package de.moltenKt.paper.app

import de.moltenKt.core.extension.data.addMoltenJsonModuleModification
import de.moltenKt.core.extension.forceCast
import de.moltenKt.core.extension.tryToIgnore
import de.moltenKt.core.tool.smart.identification.Identity
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
import de.moltenKt.paper.app.component.ui.UIComponent
import de.moltenKt.paper.app.interchange.DebugModeInterchange
import de.moltenKt.paper.app.interchange.MoltenKtInterchange
import de.moltenKt.paper.app.interchange.PlaygroundInterchange
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.mainLog
import de.moltenKt.paper.general.api.mojang.MojangProfile
import de.moltenKt.paper.general.api.mojang.MojangProfileCape
import de.moltenKt.paper.general.api.mojang.MojangProfileRaw
import de.moltenKt.paper.general.api.mojang.MojangProfileSkin
import de.moltenKt.paper.general.api.mojang.MojangProfileTextures
import de.moltenKt.paper.general.api.mojang.MojangProfileUsernameHistoryEntry
import de.moltenKt.paper.runtime.app.LanguageSpeaker.LanguageContainer
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.app.AppCompanion
import de.moltenKt.paper.tool.data.Preference
import de.moltenKt.paper.tool.data.json.JsonConfiguration
import de.moltenKt.paper.tool.data.json.JsonFileDataElement
import de.moltenKt.paper.tool.display.item.Modification
import de.moltenKt.paper.tool.display.message.Transmission.Level.ERROR
import de.moltenKt.paper.tool.display.world.SimpleLocation
import de.moltenKt.paper.tool.effect.sound.SoundData
import de.moltenKt.paper.tool.effect.sound.SoundEffect
import de.moltenKt.paper.tool.effect.sound.SoundMelody
import de.moltenKt.paper.tool.permission.Approval
import de.moltenKt.paper.tool.position.ComplexShape
import de.moltenKt.paper.tool.position.CubicalShape
import de.moltenKt.paper.tool.position.CylindricalShape
import de.moltenKt.paper.tool.position.Shape
import de.moltenKt.paper.tool.position.SphericalShape
import de.moltenKt.unfold.text
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandExecutor
import org.bukkit.configuration.serialization.ConfigurationSerialization
import java.util.logging.Level

class MoltenApp : App() {

	override val companion: Companion = Companion

	override val appIdentity: String = "MoltenKT"
	override val appLabel: String = "MoltenKT"
	override val appCache: MoltenCache = MoltenCache

	override suspend fun preHello() {

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

					subclass(ComplexShape::class)
					subclass(CubicalShape::class)
					subclass(CylindricalShape::class)
					subclass(SphericalShape::class)

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

		debugMode = MoltenData.debugMode.content

		mainLog(
			Level.INFO, """
			MoltenKT is compiled & running with the Kotlin Language made by JetBrains. Special thanks to them!
			https://www.jetbrains.com/ | https://kotlinlang.org/
		""".trimIndent())

		MoltenCache.tmp_initSetupPreferences.forEach {
			fun <T : Any> proceed(default: T) {
				val preference = it.forceCast<Preference<T>>()
				preference.content = default
				mainLog(Level.INFO, "Init-Setup '${preference.identity}' with '$default'(${default::class.simpleName})")
			}
			proceed(it.default)
		}

		MoltenCache.tmp_initSetupPreferences.clear()

		languageSpeaker.let { languageSpeaker ->
			mainLog(Level.INFO, "Speaking langauge: ${languageSpeaker.baseLang}")
			with(languageSpeaker.languageContainer) {
				"""
					Display-Language detected:
					ID: ${this.languageId};
					MoltenKT: ${this.moltenVersion};
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

		add(MoltenKtInterchange())
		add(DebugModeInterchange())
		add(PlaygroundInterchange())

	}

	override fun bye() {

		val disabledAppExecutor = CommandExecutor { sender, _, _, _ ->

			text("This vendor app of this command is currently disabled!")
				.color(NamedTextColor.RED)
				.notification(ERROR, sender)
				.display()

			true
		}

		MoltenCache.registeredServices.forEach {
			if (it.vendor.identity == this.identity) {
				it.shutdown()
			}
		}

		MoltenCache.registeredComponents.forEach {
			if (it.vendor.identity == this.identity) {
				tryToIgnore { runBlocking { it.stop() } }
			}
		}

		description.commands.keys.forEach {
			getCommand(it)?.apply {
				setExecutor(disabledAppExecutor)
				tabCompleter = null
			}

			mainLog(Level.INFO, "Command '$it' disabled")
		}

		coroutineScope.apply {
			coroutineContext.cancelChildren()
			cancel("MoltenKT-Paper is shutting down!")
		}

	}
	
	public companion object : AppCompanion<MoltenApp>() {

		@JvmStatic
		override val predictedIdentity: Identity<MoltenApp> = Identity<MoltenApp>("MoltenKT")

		@JvmStatic
		public var debugMode: Boolean = true

	}

}