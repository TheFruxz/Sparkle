package de.jet.paper.structure.command

import de.jet.jvm.extension.catchException
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.extension.debugLog
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.interchange.InterchangeExecutor
import de.jet.paper.extension.interchange.Parameters
import de.jet.paper.extension.lang
import de.jet.paper.structure.app.App
import de.jet.paper.structure.command.InterchangeAuthorizationType.JET
import de.jet.paper.structure.command.InterchangeResult.*
import de.jet.paper.structure.command.InterchangeUserRestriction.*
import de.jet.paper.structure.command.completion.InterchangeStructure
import de.jet.paper.structure.command.live.InterchangeAccess
import de.jet.paper.tool.annotation.LegacyCraftBukkitFeature
import de.jet.paper.tool.display.message.Transmission.Level.ERROR
import de.jet.paper.tool.display.message.Transmission.Level.FAIL
import de.jet.paper.tool.permission.Approval
import de.jet.paper.tool.smart.Logging
import de.jet.paper.tool.smart.VendorsIdentifiable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.logging.Level.WARNING

abstract class Interchange(
	val label: String,
	val aliases: Set<String> = emptySet(),
	val protectedAccess: Boolean = false,
	val userRestriction: InterchangeUserRestriction = NOT_RESTRICTED,
	val accessProtectionType: InterchangeAuthorizationType = JET,
	val hiddenFromRecommendation: Boolean = false, // todo: seems to be unused, that have to be an enabled feature
	val completion: InterchangeStructure = de.jet.paper.structure.command.completion.emptyInterchangeStructure(),
	val ignoreInputValidation: Boolean = false,
) : CommandExecutor, VendorsIdentifiable<Interchange>, Logging {

	init {

		completion.identity = label

	}

	final override lateinit var vendor: App
		internal set

	val tabCompleter = TabCompleter { _, _, _, args -> completion.computeCompletion(args.toList()) }

	final override val sectionLabel = "InterchangeEngine"

	override val thisIdentity = label

	final override val vendorIdentity: Identity<App>
		get() = vendor.identityObject

	private val requiredApproval by lazy { if (protectedAccess) Approval.fromApp(vendor, "interchange.$label") else null }

	// parameters

	abstract val execution: InterchangeAccess.() -> InterchangeResult

	// runtime-functions

	fun interchangeException(exception: Exception, executor: InterchangeExecutor, executorType: InterchangeUserRestriction) {
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
	fun canExecuteBasePlate(executor: InterchangeExecutor) = setOf(
		accessProtectionType != JET,
		requiredApproval == null,
		requiredApproval?.let { approval -> executor.hasPermission(approval.identity) } ?: true,
		executor.hasPermission("${vendor.identity}.*"),
		executor.hasPermission("*"),
	).any()

	private fun wrongApprovalFeedback(
		receiver: InterchangeExecutor,
	) {
		lang("interchange.run.issue.wrongApproval")
			.replace("[approval]", "$requiredApproval")
			.notification(FAIL, receiver).display()
	}

	private fun wrongUsageFeedback(
		receiver: InterchangeExecutor,
	) {
		lang("interchange.run.issue.wrongUsage")
			.notification(FAIL, receiver).display()
		receiver.sendMessage(Component.text(completion.buildSyntax(), NamedTextColor.YELLOW))
	}

	private fun wrongClientFeedback(
		receiver: InterchangeExecutor,
	) {
		lang("interchange.run.issue.wrongClient")
			.replace("[client]", userRestriction.name)
			.notification(FAIL, receiver).display()
	}

	private fun issueFeedback(
		receiver: InterchangeExecutor
	) {
		lang("interchange.run.issue.issue")
			.replace("[interchange]", "Interchange/$label")
			.notification(ERROR, receiver).display()
	}

	// logic

	override fun onCommand(sender: InterchangeExecutor, command: Command, label: String, args: Parameters): Boolean {
		val parameters = args.toList()
		val executionProcess = this::execution

		if (canExecuteBasePlate(sender)) {

			if (
				(userRestriction == NOT_RESTRICTED)
				|| (sender is Player && userRestriction == ONLY_PLAYERS)
				|| (sender is ConsoleCommandSender && userRestriction == ONLY_CONSOLE)
			) {

				if (ignoreInputValidation || completion.validateInput(parameters)) {
					val clientType = if (sender is Player) ONLY_PLAYERS else ONLY_CONSOLE

					fun exception(exception: Exception) {
						sectionLog.log(WARNING, "Executor ${sender.name} as ${clientType.name} caused an error at execution of $label-command!")
						issueFeedback(sender)
						catchException(exception)
					}

					try {

						when (executionProcess()(InterchangeAccess(vendor, clientType, sender, this, label, parameters, emptyList()))) {

							NOT_PERMITTED -> wrongApprovalFeedback(sender)
							WRONG_CLIENT -> wrongClientFeedback(sender)
							WRONG_USAGE -> wrongUsageFeedback(sender)
							InterchangeResult.FAIL -> issueFeedback(sender)
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

				} else
					wrongUsageFeedback(sender)

			} else
				wrongClientFeedback(sender)

		} else
			wrongApprovalFeedback(sender)

		return true
	}

}

enum class InterchangeResult {

	SUCCESS, NOT_PERMITTED, WRONG_USAGE, WRONG_CLIENT, FAIL;

}

enum class InterchangeUserRestriction {

	ONLY_PLAYERS,
	ONLY_CONSOLE,
	NOT_RESTRICTED;

}

enum class InterchangeAuthorizationType {

	JET,

	@LegacyCraftBukkitFeature
	CRAFTBUKKIT,

	NONE;

}

@Suppress("unused") // todo use Interchange as context, when the kotlin context API is ready
fun Interchange.execution(execution: InterchangeAccess.() -> InterchangeResult) = execution