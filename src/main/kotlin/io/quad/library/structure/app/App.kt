@file:Suppress("LeakingThis")

package io.quad.library.structure.app

import com.destroystokyo.paper.utils.PaperPluginLogger
import io.quad.app.QuadCache
import io.quad.library.extension.catchException
import io.quad.library.extension.quadTry
import io.quad.library.runtime.app.RunStatus
import io.quad.library.runtime.app.RunStatus.*
import io.quad.library.runtime.sandbox.SandBox
import io.quad.library.structure.app.event.EventListener
import io.quad.library.structure.command.Interchange
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.InputStreamReader
import java.util.logging.Level

abstract class App : JavaPlugin() {

	init {
		QuadCache.registeredApplications.add(this)
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
				log.log(Level.INFO, "registered '${eventListener.listenerIdentity}' listener!")

			} catch (e: Exception) {

				log.log(Level.WARNING, "Error during adding handler")
				catchException(e)

			}

		} else
			log.log(Level.WARNING, "skipped registering '${eventListener.listenerIdentity}' listener, app disabled!")
	}

	fun add(sandBox: SandBox) {
		QuadCache.registeredSandBoxes.put(
			"${sandBox.sandBoxVendor.appIdentity}//${sandBox.sandBoxIdentity}",
			sandBox
		)
		log.log(
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

	val log = PaperPluginLogger.getLogger("QUAD/$appIdentity ($appLabel)")

	protected val pluginManager = server.pluginManager

	// override base-mechanics

	abstract fun login()
	abstract fun boot()
	abstract fun logout()

	/**
	 * ***DO NOT OVERRIDE!***
	 */
	override fun onLoad() {
		quadTry {

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
		quadTry {

			runStatus = PRE_ENABLE
			boot()
			runStatus = ENABLE

		}
	}

	/**
	 * ***DO NOT OVERRIDE!***
	 */
	override fun onDisable() {
		quadTry {

			runStatus = SHUTDOWN
			logout()
			runStatus = OFFLINE

		}
	}

}