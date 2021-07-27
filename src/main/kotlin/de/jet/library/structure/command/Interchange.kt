package de.jet.library.structure.command

import de.jet.app.JetApp
import de.jet.library.structure.app.App
import de.jet.library.structure.command.InterchangeAuthorizationCheck.JETCHECK
import de.jet.library.structure.command.InterchangeExecutorType.*
import de.jet.library.structure.command.InterchangeResult.*
import de.jet.library.structure.command.live.InterchangeAccess
import de.jet.library.structure.smart.Identifiable
import de.jet.library.tool.permission.Approval
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.util.logging.Level
import kotlin.Exception

abstract class Interchange(
	val vendor: Identifiable<App>,
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
		approval: Approval? = requiredApproval,
	) {

	}

	private fun wrongUsageFeedback() {}

	private fun wrongClientFeedback() {}

	private fun issueFeedback() {}

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

				if (true /*todo smart USAGE-CHECK */) {
					val clientType = if (sender is Player) PLAYER else CONSOLE

					try {

						when (executionProcess()(InterchangeAccess(vendor, clientType, sender, this, label, parameters))) {

							NOT_PERMITTED -> null;
							WRONG_CLIENT -> null;
							WRONG_USAGE -> null;
							UNEXPECTED -> null;
							SUCCESS -> null;

						}

					} catch (e: Exception) {

					} catch (e: java.lang.Exception) {

					} catch (e: NullPointerException) {

					} catch (e: NoSuchElementException) {

					}

				} else
					TODO("wrong usage")

			} else
				TODO("wrong client")

		} else
			TODO("cannot execute base plate!")

		return true
	}

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