package de.jet.paper.structure.command

import de.jet.jvm.extension.catchException
import de.jet.paper.extension.debugLog
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.lang
import de.jet.paper.structure.app.App
import de.jet.paper.structure.command.InterchangeAuthorizationCheck.JETCHECK
import de.jet.paper.structure.command.InterchangeExecutorType.*
import de.jet.paper.structure.command.InterchangeResult.*
import de.jet.paper.structure.command.live.InterchangeAccess
import de.jet.paper.tool.annotation.LegacyCraftBukkitFeature
import de.jet.paper.tool.display.message.Transmission.Level.ERROR
import de.jet.paper.tool.display.message.Transmission.Level.FAIL
import de.jet.paper.tool.permission.Approval
import de.jet.paper.tool.smart.Logging
import de.jet.paper.tool.smart.VendorsIdentifiable
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.util.logging.Level.WARNING

abstract class Interchange(
	final override val vendor: App,
	val label: String,
	val aliases: Set<String> = emptySet(),
	val requiresAuthorization: Boolean = false,
	val requiredExecutorType: InterchangeExecutorType = BOTH,
	val authorizationCheck: InterchangeAuthorizationCheck = JETCHECK,
	val hiddenFromRecommendation: Boolean = false,
	val completion: Completion = emptyCompletion(),
) : CommandExecutor, VendorsIdentifiable<Interchange>, Logging {

	override val sectionLabel = "InterchangeEngine"

	override val thisIdentity = label

	override val vendorIdentity = vendor.identityObject

	val requiredApproval = if (requiresAuthorization) Approval.fromApp(vendor, "interchange.$label") else null

	// parameters

	abstract val execution: InterchangeAccess.() -> InterchangeResult

	// runtime-functions

	fun interchangeException(exception: Exception, executor: CommandSender, executorType: InterchangeExecutorType) {
		sectionLog.log(
			WARNING,
			"Executor ${executor.name} as ${executorType.name} caused an error at execution at ${with(exception.stackTrace[0]) { "$className:$methodName" }}!"
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
		requiredApproval?.let { approval -> executor.hasPermission(approval.identity) } ?: true,
		executor.hasPermission("${vendor.identity}.*"),
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
						sectionLog.log(WARNING, "Executor ${sender.name} as ${clientType.name} caused an error at execution of $label-command!")
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
						issueFeedback(sender)
						exception(e)
					} catch (e: java.lang.Exception) {
						issueFeedback(sender)
						exception(e)
					} catch (e: NullPointerException) {
						issueFeedback(sender)
						exception(e)
					} catch (e: NoSuchElementException) {
						issueFeedback(sender)
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

	fun Interchange.execution(execution: InterchangeAccess.() -> InterchangeResult) = execution

}

enum class InterchangeResult {

	SUCCESS, NOT_PERMITTED, WRONG_USAGE, WRONG_CLIENT, UNEXPECTED;

}

enum class InterchangeExecutorType {

	PLAYER, CONSOLE, BOTH;

}

enum class InterchangeAuthorizationCheck {

	JETCHECK,

	@LegacyCraftBukkitFeature
	CRAFTBUKKIT,

	NONE;

}