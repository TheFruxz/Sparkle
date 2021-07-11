@file:Suppress("LeakingThis")

package io.quad.library.structure.app

import com.destroystokyo.paper.utils.PaperPluginLogger
import io.quad.app.QuadCache
import io.quad.library.extension.catchException
import io.quad.library.runtime.app.RunStatus
import io.quad.library.runtime.app.RunStatus.OFFLINE
import io.quad.library.structure.app.event.EventListener
import io.quad.library.structure.command.Interchange
import org.bukkit.plugin.java.JavaPlugin
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
	abstract val appBrand: String

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

	// runtime

	/**
	 * The current status of app-runtime
	 */
	var runStatus: RunStatus = OFFLINE
		private set

	val log = PaperPluginLogger.getLogger("QUAD/$appIdentity ($appBrand)")

	protected val pluginManager = server.pluginManager

}