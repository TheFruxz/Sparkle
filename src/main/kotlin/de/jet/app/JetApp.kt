package de.jet.app

import de.jet.app.component.chat.JetChatComponent
import de.jet.app.component.events.JetEventsComponent
import de.jet.app.component.item.JetActionComponent
import de.jet.app.component.system.JetKeeperComponent
import de.jet.app.interchange.ComponentInterchange
import de.jet.app.interchange.JETInterchange
import de.jet.app.interchange.SandboxInterchange
import de.jet.app.interchange.ServiceInterchange
import de.jet.library.extension.debugLog
import de.jet.library.extension.forceCast
import de.jet.library.extension.mainLog
import de.jet.library.extension.obj.buildSandBox
import de.jet.library.structure.app.App
import de.jet.library.structure.app.AppCompanion
import de.jet.library.tool.data.Preference
import de.jet.library.tool.display.world.SimpleLocation
import org.bukkit.configuration.serialization.ConfigurationSerialization
import java.util.logging.Level

class JetApp : App() {

	override val companion = Companion

	override val appIdentity = "JET"
	override val appLabel = "JET"
	override val appCache = JetCache

	override fun register() {
		instance = this

		ConfigurationSerialization.registerClass(SimpleLocation::class.java)

		debugMode = JetData.debugMode.content

		debugLog("DebugMode preference loaded & set from file!")

	}

	override fun hello() {

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

		add(JETInterchange())
		add(ComponentInterchange())
		add(ServiceInterchange())
		add(SandboxInterchange())

		buildSandBox(this, "worky") {
			executor.sendMessage("worky is receiving: '${parameters.joinToString(" ")}'! NICE!")
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
	}

}