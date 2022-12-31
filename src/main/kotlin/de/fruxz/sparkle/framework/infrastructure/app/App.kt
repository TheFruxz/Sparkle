package de.fruxz.sparkle.framework.infrastructure.app

import com.destroystokyo.paper.utils.PaperPluginLogger
import de.fruxz.ascend.extension.*
import de.fruxz.ascend.extension.data.jsonBase
import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.framework.annotation.RequiresComponent.Companion.missingComponents
import de.fruxz.sparkle.framework.annotation.RequiresComponent.Companion.requiredComponents
import de.fruxz.sparkle.framework.annotation.RequiresComponent.Companion.requirementsMet
import de.fruxz.sparkle.framework.context.AppComposable
import de.fruxz.sparkle.framework.exception.IllegalActionException
import de.fruxz.sparkle.framework.extension.coroutines.doSync
import de.fruxz.sparkle.framework.extension.coroutines.pluginCoroutineDispatcher
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.destroySandBox
import de.fruxz.sparkle.framework.extension.internalCommandMap
import de.fruxz.sparkle.framework.extension.internalSyncCommands
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.Hoster
import de.fruxz.sparkle.framework.infrastructure.app.coroutine.AppConcurrency
import de.fruxz.sparkle.framework.infrastructure.app.event.EventListener
import de.fruxz.sparkle.framework.infrastructure.app.interchange.IssuedInterchange
import de.fruxz.sparkle.framework.infrastructure.app.update.AppUpdater
import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.framework.infrastructure.service.Service
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance.Companion.ERROR
import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.sparkle.server.SparkleApp.Infrastructure
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.server.SparkleData
import de.fruxz.stacked.text
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.permissions.Permission
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass
import kotlin.reflect.jvm.isAccessible
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
 * @param concurrency The default context-concurrency of the app.
 * @param systemOnLoadContext The [CoroutineContext] of the [onLoad] process, default set by the [concurrency] property.
 * @param systemOnEnableContext The [CoroutineContext] of the [onEnable] process, default set by the [concurrency] property.
 * @author Fruxz (@TheFruxz)
 * @since 1.0-BETA-2 (preview)
 * @constructor abstract
 */
abstract class App(
	private val concurrency: AppConcurrency = AppConcurrency.async,
	private val systemOnLoadContext: AppComposable<CoroutineContext> = concurrency.onLoadContext,
	private val systemOnEnableContext: AppComposable<CoroutineContext> = concurrency.onEnableContext,
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

	abstract val updater: AppUpdater?

	override val identityKey: Key
		get() = Key.key(Infrastructure.SYSTEM_IDENTITY, companion.predictedIdentity.lowercase())

	override val label: String by lazy { companion.predictedIdentity }

	/**
	 * The cache of the application
	 */
	abstract val appCache: AppCache?

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
	fun add(interchange: Interchange) {
		val failFreeLabel = interchange::class.simpleName

		@OptIn(ExperimentalTime::class)
		measureTime {

			log.info("starting register of interchange '$failFreeLabel'!")

			fun failed() {
				val label = interchange.label
				val aliases = tryOrNull { interchange.commandProperties.aliases } ?: emptySet()
				val command = getCommand(interchange.label)

				log.warning("FAILED! try to register fail-interchange '$label' instead...")

				if (command != null) {
					val replace = IssuedInterchange(label, aliases)

					command.setExecutor(replace)
					command.tabCompleter = replace.tabCompleter
					command.usage = replace.completion.buildSyntax(null)

				} else
					log.warning("FAILED! failed to register fail-interchange for '$label'")

			}

			if (isEnabled) {
				try {
					if (interchange.requirementsMet()) {
						doSync {

							val label = interchange.label
							val command = getCommand(interchange.label) ?: createCommand(interchange)

							interchange.replaceVendor(companion.instance)

							command.name = label
							command.tabCompleter = interchange.tabCompleter
							command.usage = interchange.completion.buildSyntax(null)

							with(interchange.commandProperties) {
								command.aliases = aliases.toList()
								command.description = description
								permissionMessage?.let(command::permissionMessage)
							}

							command.setExecutor(interchange)
							interchange.requiredApproval?.compose(this)
								?.let { approval ->
									command.permission = Permission(
										approval.identity,
										interchange.commandProperties.permissionDefault
									).name
									debugLog("Interchange '${interchange.label}' permission set to '${approval.identity}'!")
								}

							debugLog("registering artificial command for '${interchange.label}'...")

							server.internalCommandMap.apply {
								register(description.name, command)
							}

							server.internalSyncCommands()

							debugLog("successfully registered artificial command for '${interchange.label}'!")

							SparkleCache.registeredInterchanges += interchange

							log.info("register of interchange '$label' succeed!")
						}
					} else
						log.warning("FAILED! required components ${interchange.requiredComponents().map { it.simpleName }} are not available!")
				} catch (exception: Exception) {
					catchException(exception)
					failed()
				}
			} else
				log.warning("skipped registering '$failFreeLabel' interchange, app disabled!")

		}.also { time ->
			debugLog("registered '$failFreeLabel' interchange in ${time}!")
		}

	}

	fun add(eventListener: EventListener) {
		if (isEnabled) {

			try {

				if (eventListener.requirementsMet()) {

					eventListener.replaceVendor(this)

					pluginManager.registerEvents(eventListener as Listener, this)
					SparkleCache.registeredListeners += eventListener
					log.info("registered '${eventListener.listenerIdentity}' listener!")

				} else
					log.warning("FAILED! required components ${eventListener.requiredComponents().map { it.simpleName }} are not available!")

			} catch (e: Exception) {

				log.warning("Error during adding handler")

				tryOrPrint { catchException(e) }

			}

		} else
			log.warning("skipped registering '${eventListener.listenerIdentity}' listener, app disabled!")
	}

	fun register(service: Service) = service.requestRegister()

	fun start(service: Service) = service.requestStart()

	fun stop(service: Service) = service.requestStop()

	fun restart(service: Service) {
		stop(service)
		start(service)
	}

	fun unregister(service: Service) = service.requestUnregister()

	fun remove(eventListener: EventListener) {
		if (isEnabled && eventListener.isVendorCurrentlySet) {

			try {

				HandlerList.unregisterAll(eventListener)
				SparkleCache.registeredListeners = SparkleCache.registeredListeners.filter { it.listenerIdentity != eventListener.listenerIdentity }.toSet()
				log.info("unregistered '${eventListener.listenerIdentity}' listener!")

			} catch (e: Exception) {

				log.warning("Error during removing handler")
				catchException(e)

			}

		} else
			log.fine("skipped unregistering '${eventListener.listenerIdentity}' listener, app disabled or vendor unreachable!")
	}

	fun add(component: Component) {
		tryOrPrint {

			if (component.requirementsMet()) {

				@OptIn(ExperimentalTime::class)
				measureTime {

					component.replaceVendor(this)

					if (SparkleCache.registeredComponents.any { it.identity == component.identity })
						throw IllegalStateException("Component '${component.identity}' (${component::class.simpleName}) cannot be saved, because the component id '${component.identity}' is already in use!")

					component.firstContactHandshake()

					SparkleCache.registeredComponents += component

					coroutineScope.launch(context = component.vendor.asyncDispatcher) {

						component.register()

						log.info("registered '${component.identity}' component!")

						if (component.isAutoStarting) {

							log.info("### [ AUTO-START ] ### '${component.identity}' is auto-starting ### ")

							start(component.identityObject)

						}

					}

				}.let { time ->
					debugLog("registered '${component.identity}' component in ${time}!")
				}

			} else
				log.warning("FAILED! required components ${component.missingComponents().map { it.simpleName }} are not available!")

		}
	}

	fun start(componentIdentity: Identity<out Component>) = tryOrPrint {
		val component = SparkleCache.registeredComponents.firstOrNull { it.identityObject == componentIdentity }

		if (component != null) {

			if (component.requirementsMet()) {

				if (!component.isBlocked) {

					if (!SparkleCache.runningComponents.contains(componentIdentity)) {

						SparkleCache.runningComponents += componentIdentity.change<Component>() to Calendar.now()

						coroutineScope.launch(context = component.vendor.asyncDispatcher) {

							component.start()

							log.info("started '${componentIdentity.identity}' component!")

						}

					} else
						throw IllegalStateException("The component '$componentIdentity' is already running!")

				} else
					log.warning("Component '${componentIdentity.identity}' is blocked at components.json!")

			} else
				log.warning("FAILED! required components ${component.missingComponents().map { it.simpleName }} are not available!")

		} else
			throw NoSuchElementException("The component '$componentIdentity' is currently not registered! ADD IT!")

	}

	fun stop(componentIdentity: Identity<out Component>, unregisterComponent: Boolean = false) = tryOrPrint {
		val component = SparkleCache.registeredComponents.firstOrNull { it.identityObject == componentIdentity }

		if (component != null) {

			if (isEnabled && component.isVendorCurrentlySet) {

				if (component.canBeStopped) {

					if (SparkleCache.runningComponents.contains(componentIdentity)) {

						SparkleCache.runningComponents -= componentIdentity

						coroutineScope.launch(context = component.vendor.asyncDispatcher) {

							component.stop()

							if (unregisterComponent)
								unregister(componentIdentity)

							log.info("stopped '${component.identity}' component!")

						}

					} else
						throw IllegalStateException("The component '$componentIdentity' is already not running!")

				} else
					throw IllegalActionException("The component '$componentIdentity' can't be stopped, due to its behavior '${component.behaviour}'!")

			} else
				log.warning("skipped stopping '${component.identity}' component, app disabled or vendor unreachable!")

		} else
			throw NoSuchElementException("The component '$componentIdentity' is currently not registered! ADD IT!")

	}

	fun unregister(componentIdentity: Identity<out Component>) {
		tryOrPrint {
			val component = SparkleCache.registeredComponents.firstOrNull { it.identityObject == componentIdentity }

			if (component != null) {

				if (component.isVendorCurrentlySet) {
					SparkleCache.registeredComponents -= component
				} else
					log.fine("skipped unregistering '${component.identity}' component, app disabled or vendor unreachable!")

			} else
				throw NoSuchElementException("The component '$componentIdentity' is already not registered!")

		}
	}

	fun register(serializable: Class<out ConfigurationSerializable>) = tryOrPrint {
		ConfigurationSerialization.registerClass(serializable)
		log.info("successfully registered '${serializable.simpleName}' as serializable!")
	}

	fun register(serializable: KClass<out ConfigurationSerializable>) =
		register(serializable.java)

	fun unregister(serializable: Class<out ConfigurationSerializable>) = tryOrPrint {
		ConfigurationSerialization.unregisterClass(serializable)
	}

	fun unregister(serializable: KClass<out ConfigurationSerializable>) =
		unregister(serializable.java)

	// runtime
	val coroutineScope: CoroutineScope
		get() = companion.coroutineScope

	val syncDispatcher by lazy { pluginCoroutineDispatcher(false) }
	val asyncDispatcher by lazy { pluginCoroutineDispatcher(true) }

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

	val log by lazy { createLog(identity) }

	internal fun getResourceFile(path: String) =
		classLoader.getResourceAsStream(path)?.reader()?.readText()

	private val pluginManager = server.pluginManager

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

	private lateinit var onLoadJob: Job

	@OptIn(ExperimentalTime::class)
	final override fun onLoad() {
		tryOrPrint {

			activeSince = Calendar.now()

			SparkleCache.registeredApps += this

			onLoadJob = coroutineScope.launch(context = systemOnLoadContext.compose(this@App)) {
				log.info("Loading (::preHello) of '$identityKey' took ${measureTime { preHello() }}! (on primary-thread = ${Bukkit.isPrimaryThread()})")
			}

		}
	}

	@OptIn(ExperimentalTime::class)
	final override fun onEnable() {
		tryOrPrint {

			onLoadJob.invokeOnCompletion {

				coroutineScope.launch(context = systemOnEnableContext.compose(this@App)) {
					log.info("Enabling (::hello) of '$identityKey' took ${measureTime { hello() }}! (on primary-thread = ${Bukkit.isPrimaryThread()})")
				}

			}

		}
	}

	@OptIn(ExperimentalTime::class)
	final override fun onDisable() {
		tryOrPrint {

			log.info("Disabling (::bye) of '$identityKey' took ${measureTime { bye() }}!")

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

			SparkleCache.serviceStates.forEach { (_, state) ->
				if (state.vendor.key() == this@App.key()) {
					state.service.requestStop()
				}
			}

			SparkleCache.registeredComponents.toList().forEach {
				if (key() == it.vendor.key()) {
					tryOrIgnore({ SparkleApp.debugMode }) { runBlocking { it.stop() } }
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

				log.info("Command '$it' disabled")
			}

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