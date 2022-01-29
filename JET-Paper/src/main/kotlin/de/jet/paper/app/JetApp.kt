package de.jet.paper.app

import de.jet.jvm.extension.data.addJetJsonModuleModification
import de.jet.jvm.extension.forceCast
import de.jet.jvm.extension.math.decimalAsPercent
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.app.component.chat.JetChatComponent
import de.jet.paper.app.component.essentials.EssentialsComponent
import de.jet.paper.app.component.essentials.point.Point
import de.jet.paper.app.component.essentials.point.PointConfig
import de.jet.paper.app.component.essentials.world.WorldConfig
import de.jet.paper.app.component.essentials.world.tree.WorldRenderer
import de.jet.paper.app.component.essentials.world.tree.WorldRenderer.RenderFolder
import de.jet.paper.app.component.essentials.world.tree.WorldRenderer.RenderObject
import de.jet.paper.app.component.essentials.world.tree.WorldRenderer.RenderWorld
import de.jet.paper.app.component.essentials.world.tree.WorldRenderer.WorldStructure
import de.jet.paper.app.component.events.JetEventsComponent
import de.jet.paper.app.component.feature.MarkingFeatureComponent
import de.jet.paper.app.component.item.JetActionComponent
import de.jet.paper.app.component.system.JetAssistiveInterchangesComponent
import de.jet.paper.app.component.system.JetKeeperComponent
import de.jet.paper.app.component.world.JetBuildModeComponent
import de.jet.paper.app.interchange.ComponentInterchange
import de.jet.paper.app.interchange.JETInterchange
import de.jet.paper.extension.debugLog
import de.jet.paper.extension.display.ui.buildContainer
import de.jet.paper.extension.mainLog
import de.jet.paper.extension.o.buildSandBox
import de.jet.paper.extension.paper.worlds
import de.jet.paper.general.api.mojang.MojangProfile
import de.jet.paper.general.api.mojang.MojangProfileCape
import de.jet.paper.general.api.mojang.MojangProfileRaw
import de.jet.paper.general.api.mojang.MojangProfileSkin
import de.jet.paper.general.api.mojang.MojangProfileTextures
import de.jet.paper.general.api.mojang.MojangProfileUsernameHistoryEntry
import de.jet.paper.runtime.app.LanguageSpeaker.LanguageContainer
import de.jet.paper.structure.app.App
import de.jet.paper.structure.app.AppCompanion
import de.jet.paper.structure.app.cache.CacheDepthLevel
import de.jet.paper.structure.command.completion.buildCompletion
import de.jet.paper.structure.command.completion.component.CompletionAsset
import de.jet.paper.structure.command.completion.component.CompletionComponent
import de.jet.paper.tool.data.Preference
import de.jet.paper.tool.data.json.JsonConfiguration
import de.jet.paper.tool.data.json.JsonFileDataElement
import de.jet.paper.tool.display.item.Modification
import de.jet.paper.tool.display.world.SimpleLocation
import de.jet.paper.tool.effect.sound.SoundData
import de.jet.paper.tool.effect.sound.SoundMelody
import de.jet.paper.tool.input.Keyboard
import de.jet.paper.tool.input.Keyboard.RenderEngine.Key
import de.jet.paper.tool.input.Keyboard.RenderEngine.KeyConfiguration
import de.jet.paper.tool.permission.Approval
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

	override fun preHello() {

		addJetJsonModuleModification {
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

		ConfigurationSerialization.registerClass(SimpleLocation::class.java)

		// TODO: 19.10.2021 load language files to the lang-folder

		debugMode = JetData.debugMode.content

		debugLog("DebugMode preference loaded & set from file!")

	}

	override fun hello() {

		println("""
			JET is compiled & running with the Kotlin Language made by JetBrains. Special thanks to them!
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

		buildSandBox(this, "renderAllKeys") {
			buildContainer(lines = 6) {
				JetData.keyConfig.content.lightModeKeys.withIndex().forEach { (index, value) ->
					set(index, value.let { Keyboard.RenderEngine.renderKey(it) })
				}
			}.display(executor as Player)
		}

		buildSandBox(this, "percentage") {
			executor.sendMessage(parameters.first().toDouble().decimalAsPercent.displayPercentageString("§a|", "§7|", 60))
		}

		buildSandBox(this, "cleaner") {
			if (parameters.size >= 2) {
				val levelDepth = when (parameters[0].uppercase()) {
					"KILL" -> CacheDepthLevel.KILL
					"CLEAN" -> CacheDepthLevel.CLEAN
					"CLEAR" -> CacheDepthLevel.CLEAR
					"DUMP" -> CacheDepthLevel.DUMP
					else -> return@buildSandBox
				}

				when (parameters[1].uppercase()) {
					"ALL" -> {
						JetCache.dropEverything(levelDepth)
						executor.sendMessage("§aCleaned all caches!")
					}
					"ENTITY" -> {
						JetCache.dropEntityData((executor as Player).uniqueId, levelDepth)
						executor.sendMessage("§aCleaned all entity caches!")
					}
				}

			}
		}

		buildSandBox(this, "printCompletionTree") {
			buildCompletion("test") {

				configure {
					isRequired = true
				}

				content(listOf(
					CompletionComponent.asset(CompletionAsset.WORLD_NAME)
				))

				branch("demo") {

					content(listOf(
						CompletionComponent.static("test1", "test2", "test3"),
					))

					addContent(
						CompletionComponent.asset(CompletionAsset.LONG)
					)

					configure {
						isRequired = false
						mustMatchOutput = false
						infiniteSubParameters = true
					}

					branch("joke") {
						content(listOf(
							CompletionComponent.static("its", "a", "joke"),
						))
					}

					branch("joke2") {
						branch("demo") {
							content(listOf(
								CompletionComponent.static("ItsJoke2 demo"),
							))
						}
						content(listOf(
							CompletionComponent.static("ItsJoke2"),
						))
					}

					branch("joke3") {
						content(listOf(
							CompletionComponent.static("ItsJoke3"),
						))
					}

				}

			}.computeCompletion(parameters).let(::println)
		}

	}

	override fun bye() {

		JetCache.registeredComponents.forEach {
			it.stop()
		}

	}

	companion object : AppCompanion<JetApp>() {

		override val predictedIdentity = Identity<JetApp>("JET")

		var debugMode: Boolean = false

	}

}