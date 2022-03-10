package de.jet.paper.app

import de.jet.jvm.extension.data.addJetJsonModuleModification
import de.jet.jvm.extension.data.buildRandomTag
import de.jet.jvm.extension.forceCast
import de.jet.jvm.extension.tryToIgnore
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.app.component.buildMode.BuildModeComponent
import de.jet.paper.app.component.chat.ChatComponent
import de.jet.paper.app.component.component.ComponentComponent
import de.jet.paper.app.component.events.EventsComponent
import de.jet.paper.app.component.experimental.ExperimentalComponent
import de.jet.paper.app.component.keeper.KeeperComponent
import de.jet.paper.app.component.linking.ContainerLinkComponent
import de.jet.paper.app.component.marking.MarkingComponent
import de.jet.paper.app.component.point.PointComponent
import de.jet.paper.app.component.point.asset.Point
import de.jet.paper.app.component.point.asset.PointConfig
import de.jet.paper.app.component.sandbox.SandBoxComponent
import de.jet.paper.app.component.service.ServiceComponent
import de.jet.paper.app.interchange.JETInterchange
import de.jet.paper.app.old_component.essentials.world.WorldConfig
import de.jet.paper.app.old_component.essentials.world.tree.WorldRenderer
import de.jet.paper.app.old_component.essentials.world.tree.WorldRenderer.RenderFolder
import de.jet.paper.app.old_component.essentials.world.tree.WorldRenderer.RenderObject
import de.jet.paper.app.old_component.essentials.world.tree.WorldRenderer.RenderWorld
import de.jet.paper.app.old_component.essentials.world.tree.WorldRenderer.WorldStructure
import de.jet.paper.extension.debugLog
import de.jet.paper.extension.display.ui.buildContainer
import de.jet.paper.extension.display.ui.buildPanel
import de.jet.paper.extension.display.ui.item
import de.jet.paper.extension.mainLog
import de.jet.paper.extension.objectBound.buildAndRegisterSandBox
import de.jet.paper.extension.objectBound.buildSandBox
import de.jet.paper.extension.paper.player
import de.jet.paper.extension.paper.worlds
import de.jet.paper.extension.tasky.sync
import de.jet.paper.general.api.mojang.MojangProfile
import de.jet.paper.general.api.mojang.MojangProfileCape
import de.jet.paper.general.api.mojang.MojangProfileRaw
import de.jet.paper.general.api.mojang.MojangProfileSkin
import de.jet.paper.general.api.mojang.MojangProfileTextures
import de.jet.paper.general.api.mojang.MojangProfileUsernameHistoryEntry
import de.jet.paper.runtime.app.LanguageSpeaker.LanguageContainer
import de.jet.paper.structure.app.App
import de.jet.paper.structure.app.AppCompanion
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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.bukkit.Material
import org.bukkit.command.CommandExecutor
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.entity.Player
import java.util.logging.Level
import kotlin.time.Duration.Companion.seconds

class JetApp : App() {

	override val companion = Companion

	override val appIdentity = "JET"
	override val appLabel = "JET"
	override val appCache = JetCache

	override suspend fun preHello() {

		addJetJsonModuleModification {

			@OptIn(ExperimentalSerializationApi::class)
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

		debugLog("DebugMode preference loaded & set from file!")

	}

	override suspend fun hello() {

		debugMode = JetData.debugMode.content

		mainLog(
			Level.INFO, """
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
		add(ContainerLinkComponent())
		add(ComponentComponent())

		add(JETInterchange())

		buildSandBox(this, "testInventory") {
			sync {
				buildPanel {

					placeInner(0, Material.STONE.item.onClickWith {
						whoClicked.sendMessage("Hello, Mr. Clicky")
					})

					placeInner(1, Material.SADDLE.item.onClickWith {
						isCancelled = true
					})

				}.display(executor as Player)
			}
		}

		buildAndRegisterSandBox(this, "importAllWorlds") {
			sync { worlds.map { it.name }.forEach(WorldRenderer.FileSystem::importWorld) }
		}

		buildAndRegisterSandBox(this, "keyboard-demo") {
			executor as Player
			sync { Keyboard.RenderEngine.renderKeyboard(executor).mainKeyboard.display(executor) }
		}

		buildAndRegisterSandBox(this, "renderAllKeys") {
			sync {
				buildContainer(lines = 6) {
					JetData.keyConfig.content.lightModeKeys.withIndex().forEach { (index, value) ->
						set(index, value.let { Keyboard.RenderEngine.renderKey(it) })
					}
				}.display(executor as Player)
			}
		}

		buildAndRegisterSandBox(this, "checkAsync") {

			executor.sendMessage("This should be async, Thread: '${Thread.currentThread().name}'")

			sync {
				executor.sendMessage("This should be sync, Thread: '${Thread.currentThread().name}'")
			}

		}

		buildAndRegisterSandBox(this, "simulateFreeze") {

			executor.sendMessage("Okay, I'm going to freeze you now!")

			delay(20000L)

			executor.sendMessage("Okay, I'm going to unfreeze you now!")

		}

		buildAndRegisterSandBox(this, "simulateAutoStartManipulation") {

			JetData.autoStartComponents.content.toMutableSet().add(buildRandomTag())

		}

		buildAndRegisterSandBox(this, "simulateAutoStartManipulation2") {

			JetData.autoStartComponents.content = JetData.autoStartComponents.content.toMutableSet().apply {
				add(buildRandomTag().also { println("adding $it") })
			}.also { println("set to $it") }

		}

		buildAndRegisterSandBox(this, "clickAction") {
			buildPanel {
				this[20] = {

					println("You clicked me!")

					delay(2.seconds)

					sync {
						executor.sendMessage("You clicked me sync!")
					}

				}
			}.display(executor as Player)
		}

		buildAndRegisterSandBox(this, "itemActions") {
			(executor as Player).inventory.addItem(Material.IRON_PICKAXE.item.apply {

				label = "MyItem"

				onClickWith {
					player.sendMessage("You clicked me!")
				}

				onInteractWith {
					player.sendMessage("You interacted me!")
				}

				onDropWith {
					player.sendMessage("You dropped me!")
				}

			}.produce())
		}

	}

	override fun bye() {

		val disabledAppExecutor = CommandExecutor { sender, _, _, _ ->
			sender.sendMessage("§cThis vendor app of this command is currenty disabled!")
			true
		}

		JetCache.registeredServices.forEach {
			if (it.vendor.identity == this.identity) {
				it.shutdown()
			}
		}

		JetCache.registeredComponents.forEach {
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
			cancel("JET is shutting down!")
		}

	}
	
	companion object : AppCompanion<JetApp>() {

		override val predictedIdentity = Identity<JetApp>("JET")

		var debugMode: Boolean = false

	}

}