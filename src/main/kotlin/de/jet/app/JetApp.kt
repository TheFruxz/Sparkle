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
			println("Speaking language: ${languageSpeaker.languageId}")
			println("")
			JetFile.appFile(this, "language-demo", "json").apply {
				load()
				set("data", Json.encodeToString(LanguageContainer(languageSpeaker.cachedLanguageData.toMutableMap().apply {
					put("name", "Fruxz")
					put("colors", "§a§lHallo§7 dies ist ein§e Test§7!")
					put("numbers", "2000")
				}.map { it.key to JsonPrimitive(it.value) }.toMap())))
				save()
			}
			println(languageSpeaker.message("system.hello"))
			println(languageSpeaker.message("system.missing"))
		}

	}

	override fun logout() {
		TODO("Not yet implemented")
	}

	companion object : AppCompanion<JetApp> {
		override lateinit var instance: JetApp
	}

}