package de.jet.app

import de.jet.library.extension.data.div
import de.jet.library.extension.data.jetPath
import de.jet.library.runtime.app.LanguageSpeaker.LanguageContainer
import de.jet.library.structure.app.App
import de.jet.library.structure.app.AppCompanion
import de.jet.library.tool.data.DataTransformer
import de.jet.library.tool.data.JetFile
import de.jet.library.tool.data.Preference
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
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
			log.log(Level.INFO, "Speaking langauge: ${languageSpeaker.languageId}")
			with(languageSpeaker.languageContainer) {
				"""
					Display-Language detected:
					ID: ${this.languageId};
					JET: ${this.jetVersion};
					Version: ${this.languageVersion};
					Vendor: ${this.languageVendor};
					Website: ${this.languageVendorWebsite};
				""".trimIndent().lines().forEach {
					log.log(Level.INFO, it)
				}
			}
			log.log(Level.INFO, """
				Testing Language-System:
				${languageSpeaker.message("system.hello")}
			""".trimIndent())
		}

	}

	override fun logout() {
		TODO("Not yet implemented")
	}

	companion object : AppCompanion<JetApp> {
		override lateinit var instance: JetApp
	}

}