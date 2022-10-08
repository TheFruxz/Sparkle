package de.fruxz.sparkle.framework.infrastructure.component

import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.ascend.extension.tryToCatch
import de.fruxz.ascend.extension.tryToIgnore
import de.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.sparkle.framework.extension.buildSandBox
import de.fruxz.sparkle.framework.extension.coroutines.asSync
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.destroySandBox
import de.fruxz.sparkle.framework.extension.internalCommandMap
import de.fruxz.sparkle.framework.extension.internalSyncCommands
import de.fruxz.sparkle.framework.extension.onlinePlayers
import de.fruxz.sparkle.framework.extension.server
import de.fruxz.sparkle.framework.extension.system
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.event.EventListener
import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.SUCCESS
import de.fruxz.sparkle.framework.infrastructure.command.execution
import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.DISABLED
import de.fruxz.sparkle.framework.infrastructure.service.Service
import de.fruxz.sparkle.framework.permission.Approval
import de.fruxz.sparkle.framework.sandbox.SandBox
import de.fruxz.sparkle.framework.visual.message.Transmission.Level.FAIL
import de.fruxz.sparkle.server.SparkleCache

abstract class SmartComponent(
	override val behaviour: RunType = DISABLED,
	override val isExperimental: Boolean = false,
	preferredVendor: App? = null,
) : Component(behaviour, isExperimental, preferredVendor) {

	private var selfRegister: suspend () -> Unit = {}
	private var selfStart: suspend () -> Unit = {}
	private var selfStop: suspend () -> Unit = {}


	var interchanges = setOf<Interchange>()

	fun interchange(vararg interchange: Interchange) { interchanges += interchange }

	var services = setOf<Service>()

	fun service(vararg service: Service) { services += service }

	var components = setOf<Component>()

	fun component(vararg component: Component) { components += component }

	var listeners = setOf<EventListener>()

	fun listener(vararg listener: EventListener) { listeners += listener }

	var sandboxes = setOf<SandBox>()

	fun sandbox(vararg sandbox: SandBox) { sandboxes += sandbox }

	fun additionalRegister(register: suspend () -> Unit) { selfRegister = register }

	fun additionalStart(start: suspend () -> Unit) { selfStart = start }

	fun additionalStop(stop: suspend () -> Unit) { selfStop = stop }

	private fun updateCommands() {
		onlinePlayers.forEach { it.updateCommands() }
	}

	final override suspend fun register() {
		if (isBlocked) return

		component() // register all objects

		selfRegister.invoke()

		interchanges.forEach { interchange ->
			tryToCatch {

				interchange.replaceVendor(vendor)

				asSync { _ ->
					server.internalCommandMap.apply {
						val command = server.getPluginCommand(interchange.label) ?: vendor.createCommand(interchange)

						command.name = interchange.label
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

						register(vendor.description.name, command)
					}

					server.internalSyncCommands()

					if (!isAutoStarting) vendor.replace(interchange.thisIdentityObject, disabledComponentInterchange(identityObject, tryOrNull { interchange.requiredApproval }))

				}

				SparkleCache.registeredInterchanges += interchange
				SparkleCache.disabledInterchanges += interchange.identityObject
				debugLog("Interchange '${interchange.identity}' registered through '$identity' with disabled-interchange!")

			}
		}

		updateCommands()
	}

	final override suspend fun start() {
		if (isBlocked) return

		selfStart.invoke()

		interchanges.forEach {
			tryToCatch {
				vendor.replace(it.thisIdentityObject, it)
				SparkleCache.registeredInterchanges += it
				SparkleCache.disabledInterchanges -= it.identityObject
				debugLog("Interchange '${it.identity}' replaced through '$identity' with original interchange-value!")
			}
		}

		services.forEach {
			tryToCatch {
				vendor.register(it)
				SparkleCache.registeredServices += it
				debugLog("Service '${it.identity}' registered through '$identity'!")
				vendor.start(it)
				debugLog("Service '${it.identity}' started through '$identity'!")
			}
		}

		components.forEach {
			tryToCatch {
				vendor.add(it)
				SparkleCache.registeredComponents += it
				debugLog("Component '${it.identity}' added through '$identity'!")
			}
		}

		listeners.forEach {
			tryToCatch {
				vendor.add(it)
				SparkleCache.registeredListeners += it
				debugLog("Listener '${it.identity}' added through '$identity'!")
			}
		}

		sandboxes.forEach {
			tryToCatch {
				buildSandBox(this@SmartComponent.vendor, it.identity, it.process)
			}
		}

		updateCommands()
	}

	final override suspend fun stop() {
		if (isBlocked) return

		selfStop.invoke()

		interchanges.forEach {
			tryToCatch {
				vendor.replace(it.thisIdentityObject, disabledComponentInterchange(identityObject, tryOrNull { it.requiredApproval }))
				SparkleCache.registeredInterchanges -= it
				SparkleCache.disabledInterchanges += it.identityObject
				tryToIgnore { debugLog("Interchange '${it.identity}' registered through '$identity' with disabled-interchange!") }
			}
		}

		services.forEach {
			tryToCatch {

				if (it.isRunning) {
					vendor.stop(it)
					tryToIgnore { debugLog("Service '${it.identity}' stopped through '$identity'!") }
				}

				vendor.unregister(it)

				tryToIgnore { debugLog("Service '${it.identity}' unregistered through '$identity'!") }
			}
		}

		components.forEach {
			tryToCatch {
				vendor.stop(it.identityObject)
				SparkleCache.registeredComponents -= it
				tryToIgnore { debugLog("Component '${it.identity}' stopped through '$identity'!") }
			}
		}

		listeners.forEach {
				tryToCatch {
					vendor.remove(it)
					tryToIgnore { debugLog("Service '${it.identity}' removed through '$identity'") }
				}
		}

		sandboxes.forEach {
			tryToCatch {
				destroySandBox(it.identity)
			}
		}

		updateCommands()
	}

	abstract suspend fun component()

	companion object {

		internal fun disabledComponentInterchange(identity: Identity<out Component>, requiredApproval: Approval? = null) = object : Interchange("", ignoreInputValidation = true, forcedApproval = requiredApproval) {

			override val execution = execution {

				"<red><bold>SORRY!</bold><gray> The providing component '<yellow>$identity<gray>' is currently <red>disabled<gray>!"
					.notification(FAIL, executor).display()

				SUCCESS
			}
		}.apply {
			replaceVendor(system)
		}

	}

}