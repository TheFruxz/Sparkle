@file:Suppress("LeakingThis")

package de.jet.library.structure.app

import com.destroystokyo.paper.utils.PaperPluginLogger
import de.jet.app.JetCache
import de.jet.library.extension.catchException
import de.jet.library.extension.jetTry
import de.jet.library.extension.mainLog
import de.jet.library.runtime.app.RunStatus
import de.jet.library.runtime.app.RunStatus.*
import de.jet.library.runtime.sandbox.SandBox
import de.jet.library.structure.app.event.EventListener
import de.jet.library.structure.command.Interchange
import de.jet.library.structure.smart.Identifiable
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.InputStreamReader
import java.util.logging.Level
import java.util.logging.Logger

abstract class App : JavaPlugin(), Identifiable<App> {

	init {
		JetCache.registeredApplications.add(this)
	}

	// parameters

	abstract val companion: AppCompanion<*>

	/**
	 * The unique application identity
	 */
	abstract val appIdentity: String

	/**
	 * The displayName
	 */
	abstract val appLabel: String

	/**
	 * The cache of the application
	 */
	abstract val appCache: AppCache

	override val id: String
		get() = appIdentity

	// api

	/**
	 * Add Interchange
	 */
	fun add(interchange: Interchange) {
		// TODO: 11.07.2021 Add Interchanges
	}

	fun add(eventListener: EventListener) {
		if (isEnabled) {

			try {

				pluginManager.registerEvents(eventListener, this)
				mainLog(Level.INFO, "registered '${eventListener.listenerIdentity}' listener!")

			} catch (e: Exception) {

				mainLog(Level.WARNING, "Error during adding handler")
				catchException(e)

			}

		} else
			mainLog(Level.WARNING, "skipped registering '${eventListener.listenerIdentity}' listener, app disabled!")
	}

	fun add(sandBox: SandBox) {
		JetCache.registeredSandBoxes.put(
			"${sandBox.sandBoxVendor.appIdentity}//${sandBox.sandBoxIdentity}",
			sandBox
		)
		mainLog(
			Level.INFO,
			"registered FUSION sandbox '${sandBox.sandBoxIdentity}'!"
		)
	}

	// runtime

	/**
	 * The current status of app-runtime
	 */
	var runStatus: RunStatus = OFFLINE
		private set

	var appRegistrationFile = YamlConfiguration()

	val log by lazy { createLog(appIdentity) }

	protected val pluginManager = server.pluginManager

	// override base-mechanics

	abstract fun login()
	abstract fun boot()
	abstract fun logout()

	/**
	 * ***DO NOT OVERRIDE!***
	 */
	override fun onLoad() {
		jetTry {

			runStatus = PRE_LOAD
			classLoader.getResourceAsStream("plugin.yml")?.let { resource ->
				appRegistrationFile.load(InputStreamReader(resource))
			}
			login()
			runStatus = LOAD

		}
	}

	/**
	 * ***DO NOT OVERRIDE!***
	 */
	override fun onEnable() {
		jetTry {

			runStatus = PRE_ENABLE
			boot()
			runStatus = ENABLE

		}
	}

	/**
	 * ***DO NOT OVERRIDE!***
	 */
	override fun onDisable() {
		jetTry {

			runStatus = SHUTDOWN
			logout()
			runStatus = OFFLINE

		}
	}

	companion object {

		fun createLog(app: String, section: String = "main"): Logger? =
			PaperPluginLogger.getLogger("JET/$app <$section>") ?: Logger.getLogger("[!] JET/$app <$section>")

	}

}