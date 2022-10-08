@file:Suppress("LeakingThis")

package de.fruxz.sparkle.framework.infrastructure.app

import com.destroystokyo.paper.utils.PaperPluginLogger
import de.fruxz.ascend.extension.catchException
import de.fruxz.ascend.extension.data.jsonBase
import de.fruxz.ascend.extension.empty
import de.fruxz.ascend.extension.tryToCatch
import de.fruxz.ascend.extension.tryToIgnore
import de.fruxz.ascend.extension.tryToPrint
import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.framework.app.RunStatus
import de.fruxz.sparkle.framework.app.RunStatus.*
import de.fruxz.sparkle.framework.data.file.SparklePath
import de.fruxz.sparkle.framework.exception.IllegalActionException
import de.fruxz.sparkle.framework.extension.coroutines.asSync
import de.fruxz.sparkle.framework.extension.coroutines.delayed
import de.fruxz.sparkle.framework.extension.coroutines.pluginCoroutineDispatcher
import de.fruxz.sparkle.framework.extension.coroutines.task
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.destroySandBox
import de.fruxz.sparkle.framework.extension.internalCommandMap
import de.fruxz.sparkle.framework.extension.internalSyncCommands
import de.fruxz.sparkle.framework.extension.mainLog
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.Hoster
import de.fruxz.sparkle.framework.infrastructure.app.event.EventListener
import de.fruxz.sparkle.framework.infrastructure.app.interchange.IssuedInterchange
import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.framework.infrastructure.service.Service
import de.fruxz.sparkle.framework.visual.message.Transmission.Level.ERROR
import de.fruxz.sparkle.server.SparkleApp.Infrastructure
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.server.SparkleData
import de.fruxz.stacked.text
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.InputStreamReader
import java.nio.file.Path
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KClass
import kotlin.reflect.jvm.isAccessible
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

/**
 * # `App (abstract)`
 * ## Info
 * This class structures the main class of every app; It defines
 * variables, management-containers, language and the functions
 * of the whole app.
 *
 * ## Use
 * You should use this Class as the baseplate of your app/plugin
 * instead of the [JavaPlugin], because this class extends out of
 * the [JavaPlugin] class. This class is way better, because it has
 * more extending functionality over the classic [JavaPlugin]!
 *
 * ## Base
 * The [App]-Class is abstract, so you can use it for your own class,
 * to be the base of your own app.
 *
 * This class uses these baseplates:
 * - [JavaPlugin]: Every app is a whole real Minecraft-Server-Plugin,
 * but it is heavily extended. But you can touch the original Bukkit/
 * Paper API, if you want to do so.
 *
 * - [Identifiable]<[App]>: Every app is identifiable through is unique
 * custom set [appIdentity] property. ([appIdentity] = [identity])
 *
 * @author Fruxz (@TheFruxz)
 * @since 1.0-BETA-2 (preview)
 * @constructor abstract
 */
abstract class App(
	private val systemOnLoadContext: CoroutineContext = EmptyCoroutineContext,
	private val systemOnEnableContext: CoroutineContext = EmptyCoroutineContext,
) : JavaPlugin(), Hoster<Unit, Unit, App> {

	// parameters

	/**
	 * # `App.companion (abstract-value)`
	 * ## Info
	 * This value defines the Companion-Object of this class, which
	 * holds the instance variable *(lateinit recommended)* and other
	 * app-related special variables & functions.
	 *
	 * ## Use
	 * Use this value/companion to save the app-instance, and other
	 * important things, but not too much, it is only a place for very
	 * important every-time reachable stuff! Use your App-Class(-Type)
	 * as the input for the <T> at [AppCompanion]<*>, so that the
	 * app-instance is from your class, but also use `instance = this`
	 * at the [preHello]`()` function
	 *
	 * ## Relations
	 * Because the [AppCompanion]<T> class provides the same identity
	 * ([Identifiable]<[App]>) as the app itself, you can use the
	 * [companion] instead of the app, to check equalities and more!
	 *
	 * @author Fruxz (@TheFruxz)
	 * @since 1.0-BETA-2 (preview)
	 * @constructor abstract
	 */
	abstract val companion: AppCompanion<out App>


	abstract val appIdentity: String

	/**
	 * # `App.label`
	 * ## Info
	 * This value defines the display-name of this app, which
	 * will be displayed in app-lists and information with
	 * the app included. Can be duplicated, but try to avoid
	 * duplicated display names with other apps!
	 *
	 * ## Use
	 * Use this value to tell your customers & visitors that
	 * your app is running, there some possible ways to do that:
	 *
	 * - On Minecraft-Networks: Tell the players the name of your network
	 * - On Publishing: Tell the players the name of your app (where they can find it)
	 * - On private use: Tell yourself, that this app is this app
	 *
	 * But you can use this like you like!
	 *
	 * ## Relations
	 * The label is not related to some crucial read/save system
	 * or something else, in this cases [appIdentity] is used!
	 *
	 * @author Fruxz (@TheFruxz)
	 * @since 1.0-BETA-2 (preview)
	 * @constructor abstract
	 */
	abstract override val label: String

	override val identityKey: Key
		get() = Key.key(Infrastructure.SYSTEM_IDENTITY, companion.predictedIdentity.lowercase())

	/**
	 * The cache of the application
	 */
	abstract val appCache: AppCache

	private var loadTime: Duration? = null

	private var startTime: Duration? = null

	var activeSince: Calendar? = null
		private set

	// api

	/**
	 * Interchange must not be initialized before executing this!
	 */
	internal fun replace(identity: Identity<out Interchange>, environment: Interchange) {
		val command = getCommand(identity.identity)
		debugLog("command '${identity.identity}' command is ${command?.name} and is ${command?.javaClass}")
		command?.setExecutor(environment)
		command?.tabCompleter = environment.tabCompleter
	}

	fun createCommand(interchange: Interchange): PluginCommand {
		debugLog("creating artificial command for '${interchange.label}'...")

		val constructor = PluginCommand::class.constructors.first()

		constructor.isAccessible = true

		return constructor.call(interchange.label, this as Plugin).also {
			debugLog("successfully created artificial command for '${interchange.label}'!")
		}
	}

	/**
	 * Add Interchange
	 */
	suspend fun add(interchange: Interchange) {
		val failFreeLabel = interchange::class.simpleName

		mainLog(Level.INFO, "starting register of interchange '$failFreeLabel'!")

		fun failed() {
			val label = interchange.label
			val aliases = try {
				interchange.aliases
			} catch (e: Exception) {
				emptySet()
			}
			val command = getCommand(interchange.label)

			mainLog(Level.WARNING, "FAILED! try to register fail-interchange '$label' instead...")

			if (command != null) {
				val replace = IssuedInterchange(label, aliases)

				command.setExecutor(replace)
				command.tabCompleter = replace.tabCompleter
				command.usage = replace.completion.buildSyntax(null)

			} else
				mainLog(Level.WARNING, "FAILED! failed to register fail-interchange for '$label'")

		}

		if (isEnabled) {

			try {

				asSync {

					val label = interchange.label
					val command = getCommand(interchange.label) ?: createCommand(interchange)

					interchange.replaceVendor(companion.instance)

					command.name = label
					command.tabCompleter = interchange.tabCompleter
					command.usage = interchange.completion.buildSyntax(null)
					command.aliases = interchange.aliases.toList()
					command.description = interchange.description
					interchange.permissionMessage?.let(command::permissionMessage)
					command.setExecutor(interchange)
					interchange.requiredApproval
						?.takeIf { interchange.requiresApproval }
						?.let { approval ->
							command.permission = approval.identity
							debugLog("Interchange '${interchange.label}' permission set to '${approval.identity}'!")
						}

					debugLog("registering artificial command for '${interchange.label}'...")

					server.internalCommandMap.apply {
						register(description.name, command)
					}

					server.internalSyncCommands()

					debugLog("successfully registered artificial command for '${interchange.label}'!")

					SparkleCache.registeredInterchanges += interchange

					mainLog(Level.INFO, "register of interchange '$label' succeed!")

				}

			} catch (exception: Exception) {
				catchException(exception)
				failed()
			}

		} else
			mainLog(Level.WARNING, "skipped registering '$failFreeLabel' interchange, app disabled!")
	}

	fun add(eventListener: EventListener) {
		if (isEnabled) {

			try {

				eventListener.replaceVendor(this)

				pluginManager.registerEvents(eventListener as Listener, this)
				SparkleCache.registeredListeners += eventListener
				mainLog(Level.INFO, "registered '${eventListener.listenerIdentity}' listener!")

			} catch (e: Exception) {

				mainLog(Level.WARNING, "Error during adding handler")

				tryToPrint { catchException(e) }

			}

		} else
			mainLog(Level.WARNING, "skipped registering '${eventListener.listenerIdentity}' listener, app disabled!")
	}

	fun register(service: Service) {
		if (SparkleCache.registeredServices.none { it.identity == service.identity }) {
			tryToCatch {
				SparkleCache.registeredServices += service
				mainLog(Level.INFO, "register of service '${service.identity}' succeed!")
			}
		} else
			throw IllegalStateException("The service '${service.identity}' is already registered!")
	}

	fun unregister(service: Service) {
		if (SparkleCache.registeredServices.any { it.identity == service.identity }) {
			tryToCatch {
				if (service.isRunning) {
					stop(service)
				}
				SparkleCache.registeredServices -= service
				mainLog(Level.INFO, "unregister of service '${service.identity}' succeed!")
			}
		} else
			throw IllegalStateException("The service '${service.identity}' is not registered!")
	}

	fun reset(service: Service) {
		if (SparkleCache.registeredServices.any { it.identity == service.identity }) {
			tryToCatch {
				service.controller?.attempt = 0
				mainLog(Level.INFO, "reset of service '${service.identity}' succeed!")
			}
		} else
			throw IllegalStateException("The service '${service.identity}' is not registered!")
	}

	fun start(service: Service) {
		if (SparkleCache.registeredServices.any { it.identity == service.identity }) {
			if (!service.isRunning) {
				tryToCatch {
					task(
						service.temporalAdvice,
						process = service.process,
						vendor = this,
						onStart = service.onStart,
						onStop = service.onStop,
						onCrash = service.onCrash,
						serviceVendor = service.key
					)
					mainLog(Level.INFO, "starting of service '${service.identity}' succeed!")
				}
			} else
				throw IllegalStateException("The service '${service.identity}' is already running!")
		} else
			throw IllegalStateException("The service '${service.identity}' is not registered!")
	}

	fun stop(service: Service) {
		if (service.isRunning) {
			tryToCatch {
				service.shutdown()
				mainLog(Level.INFO, "stopping of service '${service.identity}' succeed!")
			}
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

		delayed(1.seconds) {
			start(service)
		}

		mainLog(Level.INFO, "Restart of service '${service.identity}' succeed!")
		mainLog(Level.INFO, "--- --- --- --- --- --- --- --- --- --- --- ---")
	}

	fun remove(eventListener: EventListener) {
		if (isEnabled && eventListener.isVendorCurrentlySet) {

			try {

				HandlerList.unregisterAll(eventListener)
				SparkleCache.registeredListeners = SparkleCache.registeredListeners.filter { it.listenerIdentity != eventListener.listenerIdentity }.toSet()
				mainLog(Level.INFO, "unregistered '${eventListener.listenerIdentity}' listener!")

			} catch (e: Exception) {

				mainLog(Level.WARNING, "Error during removing handler")
				catchException(e)

			}

		} else
			mainLog(
				Level.WARNING,
				"skipped unregistering '${eventListener.listenerIdentity}' listener, app disabled or vendor unreachable!"
			)
	}

	fun add(component: Component) {
		tryToCatch {

			component.replaceVendor(this)

//			if (!component.isBlocked) {

			if (SparkleCache.registeredComponents.any { it.identity == component.identity })
				throw IllegalStateException("Component '${component.identity}' (${component::class.simpleName}) cannot be saved, because the component id '${component.identity}' is already in use!")

			component.firstContactHandshake()

			SparkleCache.registeredComponents += component

			coroutineScope.launch(context = component.vendor.pluginCoroutineDispatcher(true)) {

				component.register()

				mainLog(Level.INFO, "registered '${component.identity}' component!")

				if (component.isAutoStarting) {

					mainLog(Level.INFO, "### [ AUTO-START ] ### '${component.identity}' is auto-starting ### ")

					start(component.identityObject)

				}

			}

//			} else
//				mainLog.warning("Component '${component.identity}' is blocked at components.json!")
		}
	}

	fun start(componentIdentity: Identity<out Component>) = tryToCatch {
		val component = SparkleCache.registeredComponents.firstOrNull { it.identityObject == componentIdentity }

		if (component != null) {

			if (!component.isBlocked) {

				if (!SparkleCache.runningComponents.contains(componentIdentity)) {

					SparkleCache.runningComponents += componentIdentity.change<Component>() to Calendar.now()

					coroutineScope.launch(context = component.vendor.pluginCoroutineDispatcher(true)) {

						component.start()

						mainLog(Level.INFO, "started '${componentIdentity.identity}' component!")

					}

				} else
					throw IllegalStateException("The component '$componentIdentity' is already running!")

			} else
				mainLog.warning("Component '${componentIdentity.identity}' is blocked at components.json!")

		} else
			throw NoSuchElementException("The component '$componentIdentity' is currently not registered! ADD IT!")

	}

	fun stop(componentIdentity: Identity<out Component>, unregisterComponent: Boolean = false) = tryToCatch {
		val component = SparkleCache.registeredComponents.firstOrNull { it.identityObject == componentIdentity }

		if (component != null) {

			if (isEnabled && component.isVendorCurrentlySet) {

				if (component.canBeStopped) {

					if (SparkleCache.runningComponents.contains(componentIdentity)) {

						SparkleCache.runningComponents -= componentIdentity

						coroutineScope.launch(context = component.vendor.pluginCoroutineDispatcher(true)) {

							component.stop()

							if (unregisterComponent)
								unregister(componentIdentity)

							mainLog(Level.INFO, "stopped '${component.identity}' component!")

						}

					} else
						throw IllegalStateException("The component '$componentIdentity' is already not running!")

				} else
					throw IllegalActionException("The component '$componentIdentity' can't be stopped, due to its behavior '${component.behaviour}'!")

			} else
				mainLog(
					Level.WARNING,
					"skipped stopping '${component.identity}' component, app disabled or vendor unreachable!"
				)

		} else
			throw NoSuchElementException("The component '$componentIdentity' is currently not registered! ADD IT!")

	}

	fun unregister(componentIdentity: Identity<out Component>) {
		tryToCatch {
			val component = SparkleCache.registeredComponents.firstOrNull { it.identityObject == componentIdentity }

			if (component != null) {

				if (component.isVendorCurrentlySet) {
					SparkleCache.registeredComponents -= component
				} else
					mainLog(
						Level.WARNING,
						"skipped unregistering '${component.identity}' component, app disabled or vendor unreachable!"
					)

			} else
				throw NoSuchElementException("The component '$componentIdentity' is already not registered!")

		}
	}

	fun register(serializable: Class<out ConfigurationSerializable>) = tryToCatch {
		ConfigurationSerialization.registerClass(serializable)
		mainLog(Level.INFO, "successfully registered '${serializable.simpleName}' as serializable!")
	}

	fun register(serializable: KClass<out ConfigurationSerializable>) =
		register(serializable.java)

	fun unregister(serializable: Class<out ConfigurationSerializable>) = tryToCatch {
		ConfigurationSerialization.unregisterClass(serializable)
	}

	fun unregister(serializable: KClass<out ConfigurationSerializable>) =
		unregister(serializable.java)

	// runtime

	/**
	 * The current status of app-runtime
	 */
	var runStatus: RunStatus = OFFLINE
		private set

	var appRegistrationFile = YamlConfiguration()

	val coroutineScope: CoroutineScope
		get() = companion.coroutineScope

	/**
	 * This [lazy] value represents a [HttpClient] via Ktor.
	 * It's not occupied at default, so you can use it freely!
	 * @author Fruxz
	 * @since 1.0
	 */
	val httpClient by lazy {
		HttpClient(CIO) {
			if (SparkleData.systemConfig.httpClientCaching) install(HttpCache)
			install(ContentNegotiation) {
				json(jsonBase)
			}
		}
	}

	val log by lazy { createLog(appIdentity) }

	internal fun getResourceFile(path: String) =
		classLoader.getResourceAsStream(path)?.reader()?.readText()

	private val pluginManager = server.pluginManager

	val appFolder: Path
		get() = SparklePath.appPath(this)

	// override base-mechanics

	/**
	 * This function is called, when the plugin gets loaded.
	 * Representing the first stage of the plugin-lifecycle,
	 * also known as the "[onLoad]" stage.
	 * @see [onLoad]
	 * @author Fruxz
	 * @since 1.0
	 */
	open suspend fun preHello() {}

	/**
	 * This function is called, when the plugin gets enabled.
	 * Representing the second stage of the plugin-lifecycle,
	 * also known as the "[onEnable]" stage.
	 * @see [onEnable]
	 * @author Fruxz
	 * @since 1.0
	 */
	abstract suspend fun hello()

	/**
	 * This function is called, when the plugin gets disabled.
	 * Representing the last stage of the plugin-lifecycle,
	 * also known as the "[onDisable]" stage.
	 * @see [onDisable]
	 * @author Fruxz
	 * @since 1.0
	 */
	open fun bye() = empty()

	private suspend fun awaitState(waitFor: RunStatus, out: RunStatus, process: suspend () -> Unit) {

		while (runStatus != out) {

			if (runStatus != waitFor) {
				delay(.1.seconds)
				continue
			}

			process()

		}

	}

	@OptIn(ExperimentalTime::class)
	final override fun onLoad() {
		tryToCatch {
			activeSince = Calendar.now()

			SparkleCache.registeredApps += this

			coroutineScope.launch(context = systemOnLoadContext) {

				runStatus = PRE_LOAD
				classLoader.getResourceAsStream("plugin.yml")?.let { resource ->
					appRegistrationFile.load(InputStreamReader(resource))
				}
				measureTime {
					preHello()
				}.let { requiredTime ->
					log.info("Loading (::preHello) of '$identityKey' took $requiredTime!")
					loadTime = requiredTime
				}
				runStatus = LOAD

			}

		}
	}

	@OptIn(ExperimentalTime::class)
	final override fun onEnable() {
		tryToCatch {

			coroutineScope.launch(context = systemOnEnableContext) {

				awaitState(LOAD, ENABLE) {
					runStatus = PRE_ENABLE
					measureTime {
						hello()
					}.let { requiredTime ->
						startTime = requiredTime
						runStatus = ENABLE

						delay(1.seconds)
						log.info("Enabling (::hello) of '$identityKey' took $requiredTime!")

					}
				}

			}

		}
	}

	@OptIn(ExperimentalTime::class)
	final override fun onDisable() {
		tryToCatch {

			runStatus = SHUTDOWN
			measureTime {
				bye()
			}.let { requiredTime ->
				log.info("Disabling (::bye) of '$identityKey' took $requiredTime!")
			}

			with(coroutineScope) {
				coroutineContext.cancelChildren()
				cancel("App '$identityKey' is now disabled!")
			}

			// shutdown process

			val disabledAppExecutor = CommandExecutor { sender, _, _, _ ->

				text("This vendor app of this command is currently disabled!")
					.color(NamedTextColor.RED)
					.notification(ERROR, sender)
					.display()

				true
			}

			SparkleCache.registeredServices.toList().forEach {
				if (key() == it.vendor.key() && it.isRunning) {
					it.shutdown()
				}
			}

			SparkleCache.registeredComponents.toList().forEach {
				if (key() == it.vendor.key()) {
					tryToIgnore { runBlocking { it.stop() } }
				}
			}

			SparkleCache.registeredSandBoxes.toList().forEach { sandbox ->
				if (key() == sandbox.vendor.key()) {
					destroySandBox(sandbox)
				}
			}

			description.commands.keys.forEach {
				getCommand(it)?.apply {
					setExecutor(disabledAppExecutor)
					tabCompleter = null
				}

				mainLog(Level.INFO, "Command '$it' disabled")
			}

			runStatus = OFFLINE

		}
	}

	override fun requestStart() = pluginManager.enablePlugin(this)

	override fun requestStop() = pluginManager.disablePlugin(this)

	companion object {

		@JvmStatic
		fun createLog(app: String, section: String = "main"): Logger =
			(PaperPluginLogger.getLogger("$app//$section") ?: Logger.getLogger("$app//$section"))

	}

}