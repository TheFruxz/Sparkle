package de.jet.minecraft.app

import de.jet.library.extension.forceCast
import de.jet.library.tool.smart.identification.Identifiable
import de.jet.minecraft.app.component.chat.JetChatComponent
import de.jet.minecraft.app.component.essentials.EssentialsComponent
import de.jet.minecraft.app.component.essentials.point.Point
import de.jet.minecraft.app.component.essentials.point.PointConfig
import de.jet.minecraft.app.component.essentials.world.WorldConfig
import de.jet.minecraft.app.component.essentials.world.tree.WorldRenderer
import de.jet.minecraft.app.component.essentials.world.tree.WorldRenderer.RenderFolder
import de.jet.minecraft.app.component.essentials.world.tree.WorldRenderer.RenderObject
import de.jet.minecraft.app.component.essentials.world.tree.WorldRenderer.RenderWorld
import de.jet.minecraft.app.component.essentials.world.tree.WorldRenderer.WorldStructure
import de.jet.minecraft.app.component.events.JetEventsComponent
import de.jet.minecraft.app.component.feature.MarkingFeatureComponent
import de.jet.minecraft.app.component.item.JetActionComponent
import de.jet.minecraft.app.component.system.JetAssistiveInterchangesComponent
import de.jet.minecraft.app.component.system.JetKeeperComponent
import de.jet.minecraft.app.component.world.JetBuildModeComponent
import de.jet.minecraft.app.interchange.ComponentInterchange
import de.jet.minecraft.app.interchange.JETInterchange
import de.jet.minecraft.extension.debugLog
import de.jet.minecraft.extension.mainLog
import de.jet.minecraft.extension.o.buildSandBox
import de.jet.minecraft.extension.paper.worlds
import de.jet.minecraft.general.api.mojang.MojangProfile
import de.jet.minecraft.general.api.mojang.MojangProfileCape
import de.jet.minecraft.general.api.mojang.MojangProfileRaw
import de.jet.minecraft.general.api.mojang.MojangProfileSkin
import de.jet.minecraft.general.api.mojang.MojangProfileTextures
import de.jet.minecraft.general.api.mojang.MojangProfileUsernameHistoryEntry
import de.jet.minecraft.runtime.app.LanguageSpeaker.LanguageContainer
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.app.AppCompanion
import de.jet.minecraft.structure.classes.JSON
import de.jet.minecraft.tool.data.Preference
import de.jet.minecraft.tool.data.json.JsonConfiguration
import de.jet.minecraft.tool.data.json.JsonFileDataElement
import de.jet.minecraft.tool.display.item.Modification
import de.jet.minecraft.tool.display.world.SimpleLocation
import de.jet.minecraft.tool.effect.sound.SoundData
import de.jet.minecraft.tool.effect.sound.SoundMelody
import de.jet.minecraft.tool.input.Keyboard
import de.jet.minecraft.tool.input.Keyboard.RenderEngine.Key
import de.jet.minecraft.tool.input.Keyboard.RenderEngine.KeyConfiguration
import de.jet.minecraft.tool.permission.Approval
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.entity.Player
import java.util.logging.Level

class JetApp : App() {

	override val companion = Companion

	override val appIdentity = "JET"
	override val appLabel = "JET"
	override val appCache = JetCache

	override fun register() {
		instance = this

		ConfigurationSerialization.registerClass(SimpleLocation::class.java)

		// TODO: 19.10.2021 load language files to the lang-folder

		debugMode = JetData.debugMode.content

		debugLog("DebugMode preference loaded & set from file!")

	}

	override fun hello() {

		println("""
			JET is building & running on Kotlin by JetBrains, special thanks to them!
			https://www.jetbrains.com/ | https://kotlinlang.org/
		""".trimIndent())

		JetCache.tmp_initSetupPreferences.forEach {
			fun <T : Any> proceed(default: T) {
				val preference = it.forceCast<Preference<T>>()
				preference.content = default
				mainLog(Level.INFO, "Init-Setup '${preference.identity}' with '$default'(${default::class.simpleName})")
			}
			proceed(it.default)
		}

		JetCache.tmp_initSetupPreferences.clear()

		languageSpeaker.let { languageSpeaker ->
			mainLog(Level.INFO, "Speaking langauge: ${languageSpeaker.baseLang}")
			with(languageSpeaker.languageContainer) {
				"""
					Display-Language detected:
					ID: ${this.languageId};
					JET: ${this.jetVersion};
					Version: ${this.languageVersion};
					Vendor: ${this.languageVendor};
					Website: ${this.languageVendorWebsite};
					Test: ${languageSpeaker.message("system.hello")};
				""".trimIndent().lines().forEach {
					mainLog(Level.INFO, it)
				}
			}
		}

		add(JetEventsComponent())
		add(JetChatComponent())
		add(JetActionComponent())
		add(JetKeeperComponent())
		add(JetBuildModeComponent())
		add(MarkingFeatureComponent())
		add(JetAssistiveInterchangesComponent())
		add(EssentialsComponent())

		add(JETInterchange())
		add(ComponentInterchange())

		buildSandBox(this, "filesystem-live") {
			JetData.worldStructure.content.visualize()
		}

		buildSandBox(this, "importAllWorlds") {
			worlds.map { it.name }.forEach(WorldRenderer.FileSystem::importWorld)
		}

		buildSandBox(this, "keyboard-demo") {
			executor as Player
			Keyboard.RenderEngine.renderKeyboard(executor).mainKeyboard.display(executor)
		}

		buildSandBox(this, "rebuild-json") {
			executor.sendMessage("...")
			JSON.rebuildJsonInstructions()
			executor.sendMessage("done! ")
		}

	}

	override fun bye() {

		JetCache.registeredComponents.forEach {
			it.stop()
		}

	}

	companion object : AppCompanion<JetApp> {
		override lateinit var instance: JetApp
		var debugMode: Boolean = false
		val registerSerialization = {

			JSON.addExtension(Identifiable.custom("JET")) {

				polymorphic(Any::class) {

					subclass(Point::class)
					subclass(PointConfig::class)
					subclass(WorldConfig::class)
					subclass(WorldStructure::class)
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
					subclass(SoundMelody::class)
					subclass(Key::class)
					subclass(KeyConfiguration::class)
					subclass(Approval::class)

					polymorphic(RenderObject::class) {
						subclass(RenderWorld::class)
						subclass(RenderFolder::class)
					}

				}
			}
		}
	}

}