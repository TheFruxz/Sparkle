@file:Suppress("LeakingThis")

package de.jet.library.structure.app

import com.destroystokyo.paper.utils.PaperPluginLogger
import de.jet.app.JetCache
import de.jet.app.JetData
import de.jet.library.extension.catchException
import de.jet.library.extension.collection.mutableReplaceWith
import de.jet.library.extension.jetTry
import de.jet.library.extension.mainLog
import de.jet.library.extension.tasky.task
import de.jet.library.extension.tasky.wait
import de.jet.library.runtime.app.LanguageSpeaker
import de.jet.library.runtime.app.RunStatus
import de.jet.library.runtime.app.RunStatus.*
import de.jet.library.runtime.sandbox.SandBox
import de.jet.library.structure.app.event.EventListener
import de.jet.library.structure.app.interchange.IssuedInterchange
import de.jet.library.structure.command.Interchange
import de.jet.library.structure.component.Component
import de.jet.library.structure.service.Service
import de.jet.library.tool.smart.Identifiable
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
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

	override val identity: String
		get() = appIdentity

	// api

	/**
	 * Add Interchange
	 */
	fun add(interchange: Interchange) {
		val failFreeLabel = interchange::class.simpleName

		mainLog(Level.INFO, "Starting register of interchange '$failFreeLabel'!")

		fun failed() {
			val label = interchange.label
			val aliases = try { interchange.aliases } catch (e: Exception) { emptySet() }
			val command = getCommand(interchange.label)

			mainLog(Level.WARNING, "FAILED! try to register fail-interchange '$label' instead...")

			if (command != null) {
				val replace = IssuedInterchange(this, label, aliases)

				command.setExecutor(replace)
				command.tabCompleter = replace.completionEngine
				command.usage = replace.completionDisplay

			} else
				mainLog(Level.WARNING, "FAILED! failed to register fail-interchange for '$label'")

		}

		if (isEnabled) {

			try {
				val label = interchange.label
				val aliases = interchange.aliases
				val command = getCommand(interchange.label)

				if (command != null) {

					command.setExecutor(interchange)
					command.tabCompleter = interchange.completionEngine
					command.usage = interchange.completionDisplay
					command.aliases.mutableReplaceWith(aliases)

					mainLog(Level.INFO, "Register of interchange '$label' succeed!")

				} else
					throw IllegalArgumentException("Cannot find interchange (command) with name '$label' in plugin.yml!")

			} catch (exception: Exception) {
				catchException(exception)
				failed()
			}

		} else
			mainLog(Level.WARNING, "skipped registering '$failFreeLabel' interchange, app disabled!")

		// TODO: 11.07.2021 Add Interchanges
	}

	fun add(eventListener: EventListener) {
		if (isEnabled) {

			try {

				pluginManager.registerEvents(eventListener as Listener, this)
				mainLog(Level.INFO, "registered '${eventListener.listenerIdentity}' listener!")

			} catch (e: Exception) {

				mainLog(Level.WARNING, "Error during adding handler")
				catchException(e)

			}

		} else
			mainLog(Level.WARNING, "skipped registering '${eventListener.listenerIdentity}' listener, app disabled!")
	}

	fun add(sandBox: SandBox) {
		JetCache.registeredSandBoxes["${sandBox.sandBoxVendor.appIdentity}//${sandBox.sandBoxIdentity}"] = sandBox
		mainLog(
			Level.INFO,
			"registered sandbox '${sandBox.sandBoxIdentity}'!"
		)
	}

	fun remove(sandBox: SandBox) {
		JetCache.registeredSandBoxes.remove("${sandBox.sandBoxVendor.appIdentity}//${sandBox.sandBoxIdentity}", sandBox)
		mainLog(
			Level.INFO,
			"unregistered sandbox '${sandBox.sandBoxIdentity}'!"
		)
	}

	fun register(service: Service) {
		if (JetCache.registeredServices.none { it.identity == service.identity }) {
			JetCache.registeredServices.add(service)
			mainLog(Level.INFO, "Register of service '${service.identity}' succeed!")
		} else
			throw IllegalStateException("The service '${service.identity}' is already registered!")
	}

	fun unregister(service: Service) {
		if (JetCache.registeredServices.any { it.identity == service.identity }) {
			stop(service)
			JetCache.registeredServices.remove(service)
			mainLog(Level.INFO, "Unregister of service '${service.identity}' succeed!")
		} else
			throw IllegalStateException("The service '${service.identity}' is not registered!")
	}

	fun reset(service: Service) {
		if (JetCache.registeredServices.any { it.identity == service.identity }) {
			service.controller?.attempt = 0
			mainLog(Level.INFO, "Reset of service '${service.identity}' succeed!")
		} else
			throw IllegalStateException("The service '${service.identity}' is not registered!")
	}

	fun start(service: Service) {
		if (JetCache.registeredServices.any { it.identity == service.identity }) {
			if (!service.isRunning) {
				task(
					service.temporalAdvice,
					process = service.process,
					vendor = this,
					onStart = service.onStart,
					onStop = service.onStop,
					onCrash = service.onCrash,
					serviceVendor = service.identityObject
				)
				mainLog(Level.INFO, "Starting of service '${service.identity}' succeed!")
			} else
				throw IllegalStateException("The service '${service.identity}' is already running!")
		} else
			throw IllegalStateException("The service '${service.identity}' is not registered!")
	}

	fun stop(service: Service) {
		if (service.isRunning) {
			service.shutdown()
			mainLog(Level.INFO, "Stopping of service '${service.identity}' succeed!")
		} else
			throw IllegalStateException("The service '${service.identity}' is not running!")
	}

	fun restart(service: Service) {
		mainLog(Level.INFO, "--- --- --- --- --- --- --- --- --- --- --- ---")
		mainLog(Level.INFO, "Attempting restart of service '${service.identity}'...")
		try {
			stop(service)
		} catch (exception: IllegalStateException) {
			mainLog(Level.WARNING, "skipped stop of service '${service.identity}', was already offline!")
		}
		mainLog(Level.INFO, "Waiting one second, let the service stop...")
		wait(20*1) {
			start(service)
		}
		mainLog(Level.INFO, "Restart of service '${service.identity}' succeed!")
		mainLog(Level.INFO, "--- --- --- --- --- --- --- --- --- --- --- ---")
	}

	fun remove(eventListener: EventListener) {
		if (isEnabled) {

			try {

				HandlerList.unregisterAll(eventListener)
				mainLog(Level.INFO, "unregistered '${eventListener.listenerIdentity}' listener!")

			} catch (e: Exception) {

				mainLog(Level.WARNING, "Error during removing handler")
				catchException(e)

			}

		} else
			mainLog(Level.WARNING, "skipped unregistering '${eventListener.listenerIdentity}' listener, app disabled!")
	}

	fun add(component: Component) {
		jetTry {
			if (JetCache.registeredComponents.any { it.identity == component.identity })
				throw IllegalStateException("Component '${component.identity}' (${component::class.simpleName}) cannot be saved, because the component id '${component.identity}' is already in use!")
			JetCache.registeredComponents.add(component)
			mainLog(Level.INFO, "registered '${component.identity}' component!")

			if (component.autoEnable || JetData.autoStartComponents.content.contains(component.identity)) {

				mainLog(Level.INFO, "### [ AUTO-START ] ### '${component.identity}' is auto-starting ### ")

				start(component)

			}

		}
	}

	fun start(component: Identifiable<Component>) {
		jetTry {
			JetCache.registeredComponents.first { it == component }.start()
			JetCache.runningComponents.add(component.identityObject)
			mainLog(Level.INFO, "started '${component.identity}' component!")
		}
	}

	fun stop(component: Identifiable<Component>) {
		jetTry {
			JetCache.registeredComponents.first { it == component }.stop()
			JetCache.runningComponents.remove(component.identityObject)
			mainLog(Level.INFO, "stopped '${component.identity}' component!")
		}
	}

	// runtime

	/**
	 * The current status of app-runtime
	 */
	var runStatus: RunStatus = OFFLINE
		private set

	var appRegistrationFile = YamlConfiguration()

	val log by lazy { createLog(appIdentity) }

	internal fun getResourceFile(path: String) =
		classLoader.getResourceAsStream(path)?.reader()?.readText()

	val languageSpeaker by lazy { LanguageSpeaker(JetData.systemLanguage.content) }

	protected val pluginManager = server.pluginManager

	// override base-mechanics

	abstract fun register()
	abstract fun hello()
	abstract fun bye()

	/**
	 * ***DO NOT OVERRIDE!***
	 */
	override fun onLoad() {
		jetTry {

			runStatus = PRE_LOAD
			classLoader.getResourceAsStream("plugin.yml")?.let { resource ->
				appRegistrationFile.load(InputStreamReader(resource))
			}
			register()
			runStatus = LOAD

		}
	}

	/**
	 * ***DO NOT OVERRIDE!***
	 */
	override fun onEnable() {
		jetTry {

			runStatus = PRE_ENABLE
			hello()
			runStatus = ENABLE

		}
	}

	/**
	 * ***DO NOT OVERRIDE!***
	 */
	override fun onDisable() {
		jetTry {

			runStatus = SHUTDOWN
			bye()
			runStatus = OFFLINE

		}
	}

	companion object {

		fun createLog(app: String, section: String = "main"): Logger =
			PaperPluginLogger.getLogger("JET/$app <$section>") ?: Logger.getLogger("[!] JET/$app <$section>")

	}

}