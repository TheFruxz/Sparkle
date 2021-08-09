package de.jet.app

import de.jet.app.component.chat.JetChatComponent
import de.jet.app.component.events.JetEventsComponent
import de.jet.app.interchange.JETInterchange
import de.jet.library.extension.data.div
import de.jet.library.extension.data.jetPath
import de.jet.library.extension.mainLog
import de.jet.library.structure.app.App
import de.jet.library.structure.app.AppCompanion
import de.jet.library.tool.data.DataTransformer
import de.jet.library.tool.data.JetFile
import de.jet.library.tool.data.Preference
import org.bukkit.scheduler.BukkitRunnable
import java.util.logging.Level

class JetApp : App() {

	override val companion = Companion

	override val appIdentity = "JET"
	override val appLabel = "JET"
	override val appCache = JetCache

	override fun login() {
		instance = this
	}

	override fun boot() {

		object : BukkitRunnable() {
			override fun run() {
				Preference(
					file = JetFile.appFile(instance, "hey"),
					path = jetPath("this") / "is" / "the" / "path",
					default = 2,
					useCache = false
				).transformer(DataTransformer({
					"Thisisthenumber$this"
				}, {
					removePrefix("Thisisthenumber").toInt()
				}))
					.let { preference ->

					println("writing preference...")
					preference.content = 2

					Thread.sleep(3000)
					println("read saved content...")

					println("${preference.content} is stored!")

				}
			}

		}.runTaskLater(this, 20L*15)

		languageSpeaker.let { languageSpeaker ->
			mainLog(Level.INFO, "Speaking langauge: ${languageSpeaker.languageId}")
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

		regRun(JetEventsComponent(this))
		regRun(JetChatComponent(this))

		add(JETInterchange(this))

	}

	override fun logout() {

		JetCache.registeredComponents.forEach {
			it.stop()
		}

	}

	companion object : AppCompanion<JetApp> {
		override lateinit var instance: JetApp
	}

}