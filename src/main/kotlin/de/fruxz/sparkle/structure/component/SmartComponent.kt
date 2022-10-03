package de.fruxz.sparkle.structure.component

import de.fruxz.ascend.extension.container.mutableReplaceWith
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.ascend.extension.tryToCatch
import de.fruxz.ascend.extension.tryToIgnore
import de.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.sparkle.app.SparkleCache
import de.fruxz.sparkle.extension.debugLog
import de.fruxz.sparkle.extension.display.notification
import de.fruxz.sparkle.extension.objectBound.buildSandBox
import de.fruxz.sparkle.extension.objectBound.destroySandBox
import de.fruxz.sparkle.extension.paper.internalCommandMap
import de.fruxz.sparkle.extension.paper.internalSyncCommands
import de.fruxz.sparkle.extension.paper.onlinePlayers
import de.fruxz.sparkle.extension.paper.server
import de.fruxz.sparkle.extension.system
import de.fruxz.sparkle.extension.tasky.asSync
import de.fruxz.sparkle.runtime.sandbox.SandBox
import de.fruxz.sparkle.structure.app.App
import de.fruxz.sparkle.structure.app.event.EventListener
import de.fruxz.sparkle.structure.command.Interchange
import de.fruxz.sparkle.structure.command.InterchangeResult.SUCCESS
import de.fruxz.sparkle.structure.command.execution
import de.fruxz.sparkle.structure.component.Component.RunType.DISABLED
import de.fruxz.sparkle.structure.service.Service
import de.fruxz.sparkle.tool.display.message.Transmission.Level.FAIL
import de.fruxz.sparkle.tool.permission.Approval

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

		component() // register all objects

		selfRegister.invoke()

		interchanges.forEach {
			tryToCatch {

				it.replaceVendor(vendor)

				asSync {
					server.internalCommandMap.apply {
						val command = server.getPluginCommand(it.label) ?: vendor.createCommand(it)

						command.name = it.label
						command.tabCompleter = it.tabCompleter
						command.usage = it.completion.buildSyntax(null)
						command.aliases = emptyList()
						command.aliases.mutableReplaceWith(it.aliases)
						command.setExecutor(it)

						register(vendor.description.name, command)
					}

					server.internalSyncCommands()

					if (!isAutoStarting) vendor.replace(it.thisIdentityObject, disabledComponentInterchange(identityObject, tryOrNull { it.requiredApproval }))

				}

				SparkleCache.registeredInterchanges += it
				SparkleCache.disabledInterchanges += it.identityObject
				debugLog("Interchange '${it.identity}' registered through '$identity' with disabled-interchange!")

			}
		}

		updateCommands()
	}

	final override suspend fun start() {

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