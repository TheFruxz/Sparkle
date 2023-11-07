package de.fruxz.sparkle.framework.infrastructure.component

import dev.fruxz.ascend.extension.tryOrNull
import dev.fruxz.ascend.extension.tryOrPrint
import dev.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.sparkle.framework.context.AppComposable
import de.fruxz.sparkle.framework.extension.*
import de.fruxz.sparkle.framework.extension.coroutines.doSync
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.event.EventListener
import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeExecution
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.SUCCESS
import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.DISABLED
import de.fruxz.sparkle.framework.infrastructure.service.Service
import de.fruxz.sparkle.framework.permission.Approval
import de.fruxz.sparkle.framework.sandbox.SandBox
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import de.fruxz.sparkle.server.SparkleCache
import org.bukkit.permissions.Permission

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
		if (isBlocked) {
			debugLog("Component $label is blocked and will not be registered.")
			return
		}

		try {

			component() // register all objects

			selfRegister.invoke()

			interchanges.forEach { interchange ->
				tryOrPrint {

					interchange.replaceVendor(vendor)

					doSync { _ ->
						server.internalCommandMap.apply {
							val command = server.getPluginCommand(interchange.label) ?: vendor.createCommand(interchange)

							command.name = interchange.label
							command.tabCompleter = interchange.tabCompleter
							command.usage = interchange.completion.buildSyntax(null)
							command.aliases = interchange.commandProperties.aliases.toList()
							command.description = interchange.commandProperties.description
							interchange.commandProperties.permissionMessage?.let(command::permissionMessage)
							command.setExecutor(interchange)
							interchange.requiredApproval?.compose(interchange.vendor)
								?.let { approval ->
									command.permission = Permission(approval.identity, interchange.commandProperties.permissionDefault).name
									debugLog("Interchange '${interchange.label}' permission set to '${approval.identity}'!")
								}

							register(vendor.description.name, command)
						}

						server.internalSyncCommands()

						// TODO problematic
						// TODO if (!isAutoStarting) vendor.replace(interchange.identityObject, disabledComponentInterchange(identityObject, tryOrNull { interchange.requiredApproval?.compose(interchange.vendor) }))

					}

					SparkleCache.registeredInterchanges += interchange
					SparkleCache.disabledInterchanges += interchange.identityObject
					debugLog("Interchange '${interchange.identity}' registered through '$identity' with disabled-interchange!")

				}
			}

			updateCommands()

		} catch (e: Exception) {
			sectionLog.warning("Failed to smart-register component '$identity' due to this exception:")
			e.printStackTrace()
		}

	}

	final override suspend fun start() {
		if (isBlocked) {
			debugLog("Component $label is blocked and will not be started.")
			return
		}

		try {

			selfStart.invoke()

			interchanges.forEach {
				tryOrPrint {
					vendor.replace(it.identityObject, it)
					SparkleCache.registeredInterchanges += it
					SparkleCache.disabledInterchanges -= it.identityObject
					debugLog("Interchange '${it.identity}' replaced through '$identity' with original interchange-value!")
				}
			}

			services.forEach {
				tryOrPrint {
					vendor.register(it)
					debugLog("Service '${it.identity}' registered through '$identity'!")
					vendor.start(it)
					debugLog("Service '${it.identity}' started through '$identity'!")
				}
			}

			components.forEach {
				tryOrPrint {
					vendor.add(it)
					SparkleCache.registeredComponents += it
					debugLog("Component '${it.identity}' added through '$identity'!")
				}
			}

			listeners.forEach {
				tryOrPrint {
					vendor.add(it)
					SparkleCache.registeredListeners += it
					debugLog("Listener '${it.identity}' added through '$identity'!")
				}
			}

			sandboxes.forEach {
				tryOrPrint {
					buildSandBox(this@SmartComponent.vendor, it.key, it.process)
				}
			}

			updateCommands()

		} catch (e: Exception) {
			sectionLog.warning("Failed to smart-start component '$identity' due to this exception:")
			e.printStackTrace()
		}

	}

	final override suspend fun stop() {
		if (isBlocked) {
			debugLog("Component $label is blocked and will not be stopped.")
			return
		}

		try {

			selfStop.invoke()

			interchanges.forEach {
				tryOrPrint {
					vendor.replace(it.identityObject, disabledComponentInterchange(identityObject, tryOrNull { it.requiredApproval?.compose(vendor) }))
					SparkleCache.registeredInterchanges -= it
					SparkleCache.disabledInterchanges += it.identityObject
					debugLog { "Interchange '${it.identity}' registered through '$identity' with disabled-interchange!" }
				}
			}

			services.forEach {
				tryOrPrint {

					if (it.isRunning) {
						vendor.stop(it)
						debugLog { "Service '${it.identity}' stopped through '$identity'!" }
					}

					vendor.unregister(it)

					debugLog { "Service '${it.identity}' unregistered through '$identity'!" }
				}
			}

			components.forEach {
				tryOrPrint {
					vendor.stop(it.identityObject)
					SparkleCache.registeredComponents -= it
					debugLog { "Component '${it.identity}' stopped through '$identity'!" }
				}
			}

			listeners.forEach {
				tryOrPrint {
					vendor.remove(it)
					debugLog { "Service '${it.identity}' removed through '$identity'" }
				}
			}

			sandboxes.forEach {
				tryOrPrint {
					destroySandBox(it.identity)
				}
			}

			updateCommands()

		} catch (e: Exception) {
			sectionLog.warning("Failed to smart-stop component '$identity' due to this exception:")
			e.printStackTrace()
		}

	}

	abstract suspend fun component()

	companion object {

		internal fun disabledComponentInterchange(identity: Identity<out Component>, requiredApproval: Approval? = null) = object : Interchange("", ignoreInputValidation = true, requiredApproval = AppComposable { requiredApproval }) {

			override val execution: InterchangeExecution = {

				"<red><bold>SORRY!</bold><gray> The providing component '<yellow>$identity<gray>' is currently <red>disabled<gray>!"
					.notification(TransmissionAppearance.FAIL, executor).display()

				SUCCESS
			}
		}.apply {
			replaceVendor(sparkle)
		}

	}

}