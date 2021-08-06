package de.jet.library.structure.command

import de.jet.app.JetApp
import de.jet.library.extension.catchException
import de.jet.library.extension.debugLog
import de.jet.library.extension.display.message
import de.jet.library.extension.display.notification
import de.jet.library.extension.lang
import de.jet.library.structure.app.App
import de.jet.library.structure.command.InterchangeAuthorizationCheck.JETCHECK
import de.jet.library.structure.command.InterchangeExecutorType.*
import de.jet.library.structure.command.InterchangeResult.*
import de.jet.library.structure.command.live.InterchangeAccess
import de.jet.library.structure.smart.Identifiable
import de.jet.library.tool.display.message.Transmission.Level.ERROR
import de.jet.library.tool.display.message.Transmission.Level.FAIL
import de.jet.library.tool.effect.sound.SoundLibrary
import de.jet.library.tool.permission.Approval
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.logging.Level
import java.util.logging.Level.FINE
import java.util.logging.Level.WARNING
import kotlin.Exception

abstract class Interchange(
	val vendor: App,
	val label: String,
	val aliases: Set<String> = emptySet(),
	val requiresAuthorization: Boolean = false,
	val requiredExecutorType: InterchangeExecutorType = BOTH,
	val authorizationCheck: InterchangeAuthorizationCheck = JETCHECK,
	val hiddenFromRecommendation: Boolean = false,
	val completion: Completion,
) : CommandExecutor, Identifiable<Interchange> {

	override val id = "$vendor:$label"

	val requiredApproval = if (requiresAuthorization) Approval.fromApp(vendor, "interchange.$label") else null

	val log = App.createLog(JetApp.instance.appIdentity, "Interchange")

	// parameters

	abstract val execution: InterchangeAccess.() -> InterchangeResult

	// runtime-functions

	fun interchangeException(exception: Exception, executor: CommandSender, executorType: InterchangeExecutorType) {
		log.log(
			Level.WARNING,
			"Executor ${executor.name} as ${executorType.name} caused an error at execution of "
		)
	}

	/**
	 * If the [executor] can execute the base of the interchange
	 * with its approvals. (**Not looking for the parameters and
	 * its own possible approvals!**)
	 */
	fun canExecuteBasePlate(executor: CommandSender) = listOf(
		authorizationCheck != JETCHECK,
		requiredApproval == null,
		requiredApproval?.let { approval -> executor.hasPermission(approval.id) } ?: true,
		executor.hasPermission("${vendor.id}.*"),
		executor.hasPermission("*"),
	).any()

	private fun wrongApprovalFeedback(
		receiver: CommandSender,
	) {
		lang("interchange.run.issue.wrongApproval")
			.replace("[approval]", "$requiredApproval")
			.notification(FAIL, receiver).display()
	}

	private fun wrongUsageFeedback(
		receiver: CommandSender,
	) {
		lang("interchange.run.issue.wrongUsage")
			.replace("[usage]", "/$label${completion.buildDisplay().let { completion -> 
				if (completion.isNotBlank() && completion.isNotEmpty()) {
					" $completion"
				} else
					""
			}}")
			.notification(FAIL, receiver).display()
	}

	private fun wrongClientFeedback(
		receiver: CommandSender,
	) {
		lang("interchange.run.issue.wrongClient")
			.replace("[client]", requiredExecutorType.name)
			.notification(FAIL, receiver).display()
	}

	private fun issueFeedback(
		receiver: CommandSender
	) {
		lang("interchange.run.issue.issue")
			.replace("[interchange]", "Interchange/$label")
			.notification(ERROR, receiver).display()
	}

	// logic

	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
		val parameters = args.toList()
		val executionProcess = this::execution

		if (canExecuteBasePlate(sender)) {

			if (
				(requiredExecutorType == BOTH)
				|| (sender is Player && requiredExecutorType == PLAYER)
				|| (sender is ConsoleCommandSender && requiredExecutorType == CONSOLE)
			) {

				if (completionCheck(sender, this, parameters)) {
					val clientType = if (sender is Player) PLAYER else CONSOLE

					fun exception(exception: Exception) {
						log.log(WARNING, "Executor ${sender.name} as ${clientType.name} caused an error at execution of $label-command!")
						issueFeedback(sender)
						catchException(exception)
					}

					try {

						when (executionProcess()(InterchangeAccess(vendor, clientType, sender, this, label, parameters))) {

							NOT_PERMITTED -> wrongApprovalFeedback(sender)
							WRONG_CLIENT -> wrongClientFeedback(sender)
							WRONG_USAGE -> wrongUsageFeedback(sender)
							UNEXPECTED -> issueFeedback(sender)
							SUCCESS -> debugLog(
								"Executor ${sender.name} as ${clientType.name} successfully executed $label-interchange!"
							)

						}

					} catch (e: Exception) {
						exception(e)
					} catch (e: java.lang.Exception) {
						exception(e)
					} catch (e: NullPointerException) {
						exception(e)
					} catch (e: NoSuchElementException) {
						exception(e)
					}

				}

			} else
				wrongClientFeedback(sender)

		} else
			wrongApprovalFeedback(sender)

		return true
	}

	val completionEngine = completion.buildCompletion()

	val completionDisplay = completion.buildDisplay()

	val completionCheck = completion.buildCheck()

}

enum class InterchangeResult {

	SUCCESS, NOT_PERMITTED, WRONG_USAGE, WRONG_CLIENT, UNEXPECTED;

}

enum class InterchangeExecutorType {

	PLAYER, CONSOLE, BOTH;

}

enum class InterchangeAuthorizationCheck {

	JETCHECK,

	@Deprecated("This is JET, not Bukkit! If you like to use 'old' technology, why you are not using Windows NT?")
	CRAFTBUKKIT,

	NONE;

}