package de.fruxz.sparkle.framework.infrastructure.command

import de.fruxz.ascend.extension.catchException
import de.fruxz.ascend.extension.empty
import de.fruxz.sparkle.framework.attachment.Logging
import de.fruxz.sparkle.framework.attachment.VendorOnDemand
import de.fruxz.sparkle.framework.context.AppComposable
import de.fruxz.sparkle.framework.extension.asPlayer
import de.fruxz.sparkle.framework.extension.asPlayerOrNull
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.extension.interchange.Parameters
import de.fruxz.sparkle.framework.extension.time.getCooldown
import de.fruxz.sparkle.framework.extension.time.hasCooldown
import de.fruxz.sparkle.framework.extension.time.setCooldown
import de.fruxz.sparkle.framework.extension.visual.BOLD
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.identification.KeyedIdentifiable
import de.fruxz.sparkle.framework.identification.Labeled
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.*
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction.*
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.completion.emptyInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeAccess
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeReaction
import de.fruxz.sparkle.framework.permission.Approval
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import de.fruxz.stacked.extension.KeyingStrategy.CONTINUE
import de.fruxz.stacked.extension.asComponent
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeRed
import de.fruxz.stacked.extension.dyeYellow
import de.fruxz.stacked.extension.style
import de.fruxz.stacked.extension.subKey
import de.fruxz.stacked.hover
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.permissions.PermissionDefault
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.DurationUnit

/**
 * This class helps to define commands in a more convenient way.
 *
 * @param label is the interchange name, used as /<name> and as the identifier
 * @param requiredApproval is the permission, which is required to execute the interchange (composable, due to dynamic vendor)
 * @param requiredClient defines, which types of [InterchangeExecutor] are allowed, to execute the interchange
 * @param cooldown is the time, which has to pass, before the interchange can be executed again
 * @param ignoreInputValidation defines, if the input of the user should not be checked of correctness
 * @param completion defines the completions, that the interchange will display, during a [InterchangeExecutor]s input into console/chat
 * @param commandProperties defines the properties of the interchange, which are used to register the interchange as a command
 * @param wrongUsageReaction defines the reaction, if the user inputs the interchange wrong
 * @param wrongClientReaction defines the reaction, if the [InterchangeExecutor] is not the [requiredClient]
 * @param wrongApprovalReaction defines the reaction, if the [InterchangeExecutor] does not have the [requiredApproval]
 * @param unexpectedIssueReaction defines the reaction, if an unexpected issue occurs
 * @param cooldownReaction defines the reaction, if the [InterchangeExecutor] is on cooldown
 * @param executionContext defines the [CoroutineContext] in which the interchange is executed (composable, due to dynamic vendor)
 * @param preferredVendor if not null, this overrides the automatic extrapolated [vendor] [App] on registering
 * @author Fruxz
 * @since 1.0
 */
abstract class Interchange(
	final override val label: String,
	val requiredApproval: AppComposable<Approval?>? = defaultApproval(label),
	val requiredClient: InterchangeUserRestriction = NOT_RESTRICTED,
	val cooldown: Duration = Duration.ZERO,
	val ignoreInputValidation: Boolean = false,
	val completion: InterchangeStructure<out InterchangeExecutor> = emptyInterchangeStructure(),
	val commandProperties: CommandProperties = CommandProperties(),
	val wrongUsageReaction: InterchangeReaction = Companion.wrongUsageReaction,
	val wrongClientReaction: InterchangeReaction = Companion.wrongClientReaction,
	val wrongApprovalReaction: InterchangeReaction = Companion.wrongApprovalReaction,
	val unexpectedIssueReaction: InterchangeReaction = Companion.unexpectedIssueReaction,
	val cooldownReaction: InterchangeReaction = Companion.cooldownReaction,
	val executionContext: AppComposable<CoroutineContext> = AppComposable { it.asyncDispatcher },
	final override val preferredVendor: App? = null,
) : CommandExecutor, KeyedIdentifiable<Interchange>, VendorOnDemand, Logging, Labeled {

	init {

		completion.identity = label

		preferredVendor?.let {
			vendor = it
		}

	}

	/**
	 * The [vendor] of this interchange, represents the [App],
	 * owning / running this interchange.
	 * @author Fruxz
	 * @since 1.0
	 */
	final override lateinit var vendor: App
		private set

	override val identityKey by lazy { vendor.subKey(label, CONTINUE) }

	/**
	 * This function replaces the current [vendor] of this [Interchange]
	 * with the [newVendor].
	 * This only happens, if the current [vendor] is not set (not initialized),
	 * or if [override] is true *(default: false)*.
	 * @param newVendor the new vendor, which will be used
	 * @param override defines, if the old vendor (if set) will be replaced with [newVendor]
	 * @return If the vendor-change happens, true is returned, otherwise false is returned!
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun replaceVendor(newVendor: App, override: Boolean) = if (override || !this::vendor.isInitialized) {
		vendor = newVendor
		true
	} else
		false

	/**
	 * This value represents a completely generated / computed completion, which
	 * get used, to display the user good input recommendations, during the input.
	 * This tabCompleter is a **not** a computational value, but it contains code,
	 * that compute its recommendations adaptively to the input and to the changes
	 * of the containing assets.
	 * The TabCompleter accesses the [completion] and uses its [InterchangeStructure.computeCompletion]
	 * function, to compute the current state of adaptive and most fitting recommendations.
	 * By doing so, the TabCompleter produces new results at every tab-completion call,
	 * but the tab-completer itself, stays same, without changing it.
	 * @see InterchangeStructure.computeCompletion
	 * @author Fruxz
	 * @since 1.0
	 */
	val tabCompleter = TabCompleter { executor, _, _, args ->
		completion.computeCompletion(args.toList(), executor)
	}

	/**
	 * This value is the section-name of the logger, attached to
	 * this [Interchange]-Object.
	 * @see Logging
	 * @author Fruxz
	 * @since 1.0
	 */
	final override val sectionLabel = "InterchangeEngine"

	// parameters

	/**
	 * This abstract value defines the execution block, which will be executed, after a
	 * [InterchangeExecutor]s input, the [Approval] checks (based on [requiredApproval])
	 * and the completion-Checks (based on [completion]).
	 * This execution block is a **suspend** block, because it gets executed, from the
	 * [vendor]s [App.coroutineScope]
	 * To easily create your execution block, we recommend you, to use the [execution]
	 * function!
	 * @see execution
	 * @author Fruxz
	 * @since 1.0
	 */
	abstract val execution: InterchangeExecution

	// logic

	override fun onCommand(sender: InterchangeExecutor, command: Command, label: String, args: Parameters): Boolean {

		vendor.coroutineScope.launch(context = executionContext.compose(vendor)) {

			val parameters = args.toList()
			val executionProcess = this@Interchange::execution

			if (sender is ConsoleCommandSender || !cooldown.isPositive() || sender.asPlayerOrNull?.hasCooldown("interchange:$key") != true) {

				if (
					(requiredClient == NOT_RESTRICTED)
					|| (sender is Player && requiredClient == ONLY_PLAYERS)
					|| (sender is ConsoleCommandSender && requiredClient == ONLY_CONSOLE)
				) {

					if (ignoreInputValidation || completion.validateInput(parameters, sender)) {
						val clientType = if (sender is Player) ONLY_PLAYERS else ONLY_CONSOLE
						val access = InterchangeAccess(
							vendor = vendor,
							executorType = clientType,
							executor = sender,
							interchange = this@Interchange,
							label = label,
							parameters = parameters,
							additionalParameters = emptyList()
						)

						fun exception(exception: Exception) {
							sectionLog.warning("Executor ${sender.name} as ${clientType.name} caused an error at execution of '$label' interchange!")
							catchException(exception)
						}

						try {

							when (executionProcess()(access)) {

								NOT_PERMITTED -> with(wrongApprovalReaction) { access.reaction() }
								WRONG_CLIENT -> with(wrongClientReaction) { access.reaction() }
								WRONG_USAGE -> with(wrongUsageReaction) { access.reaction() }
								FAIL -> with(unexpectedIssueReaction) { access.reaction() }
								BRANCH_COOLDOWN -> empty()
								SUCCESS -> {
									sender.asPlayerOrNull?.setCooldown("interchange:$key", cooldown)
									debugLog("Executor ${sender.name} as ${clientType.name} successfully executed $label-interchange!")
								}

							}

						} catch (e: Exception) {
							with(unexpectedIssueReaction) { access.reaction() }
							exception(e)
						} catch (e: java.lang.Exception) {
							with(unexpectedIssueReaction) { access.reaction() }
							exception(e)
						} catch (e: NullPointerException) {
							with(unexpectedIssueReaction) { access.reaction() }
							exception(e)
						} catch (e: NoSuchElementException) {
							with(unexpectedIssueReaction) { access.reaction() }
							exception(e)
						}

					} else {
						with(wrongUsageReaction) {
							InterchangeAccess(
								vendor = vendor,
								executorType = if (sender is Player) ONLY_PLAYERS else ONLY_CONSOLE,
								executor = sender,
								interchange = this@Interchange,
								label = label,
								parameters = parameters,
								additionalParameters = emptyList()
							).reaction()
						}
					}

				} else {
					with(wrongClientReaction) {
						InterchangeAccess(
							vendor = vendor,
							executorType = if (sender is Player) ONLY_PLAYERS else ONLY_CONSOLE,
							executor = sender,
							interchange = this@Interchange,
							label = label,
							parameters = parameters,
							additionalParameters = emptyList()
						).reaction()
					}
				}

			} else with(cooldownReaction) {
				InterchangeAccess(
					vendor = vendor,
					executorType = if (sender is Player) ONLY_PLAYERS else ONLY_CONSOLE,
					executor = sender,
					interchange = this@Interchange,
					label = label,
					parameters = parameters,
					additionalParameters = emptyList()
				).reaction()
			}

		}

		return true
	}

	/**
	 * This data class represents the basic preferences of an interchange.
	 * It defines the values, which you would normally find inside the plugin.yml.
	 * @param aliases The aliases of the interchange, on which you can also call it.
	 * @param description A short description, what the interchange does.
	 * @param permissionMessage The message, which is sent to the executor, if he does not have the permission to execute the interchange, or null, if the default message should be used.
	 * @param permissionDefault The [PermissionDefault] of the interchange.
	 * @author Fruxz
	 * @since 1.0
	 */
	data class CommandProperties(
		val aliases: Set<String> = emptySet(),
		val description: String = "An command, built with sparkle!",
		val permissionMessage: Component? = null,
		val permissionDefault: PermissionDefault = PermissionDefault.TRUE,
	) {

		companion object {
			fun aliases(vararg aliases: String) = CommandProperties(aliases = aliases.toSet())
			fun description(description: String) = CommandProperties(description = description)
			fun permissionMessage(permissionMessage: Component) = CommandProperties(permissionMessage = permissionMessage)
			fun permissionDefault(permissionDefault: PermissionDefault) = CommandProperties(permissionDefault = permissionDefault)
		}

	}

	internal companion object {

		val wrongClientReaction = InterchangeReaction {
			text {
				this + text("This action ").dyeGray()
				this + text("requires").dyeRed()
				this + text(" you as a '").dyeGray()
				this + text(interchange.requiredClient.name).dyeYellow()
				this + text("', to be executed!").dyeGray()
			}.notification(TransmissionAppearance.FAIL, executor).display()
		}

		val wrongUsageReaction = InterchangeReaction {

			text {
				this + text("Follow the ").dyeGray()
				this + text("syntax").dyeRed()
				this + text(", to execute this! See:").dyeGray()
			}.notification(TransmissionAppearance.FAIL, executor).display() // first part of the message

			executor.sendMessage(Component.text(interchange.completion.buildSyntax(executor), NamedTextColor.YELLOW)) // second part of the message

		}

		val wrongApprovalReaction = InterchangeReaction {
			text {
				this + text("You currently do ").dyeGray()
				this + text("not").dyeRed()
				this + text(" have the ").dyeGray()
				this + text("required approval").dyeYellow()
				this + text(" to execute this interchange!").dyeGray()
			}.notification(TransmissionAppearance.FAIL, executor).display()
		}

		val unexpectedIssueReaction = InterchangeReaction {
			text {
				this + text("Oops!").style(NamedTextColor.RED, BOLD)
				this + text(" A").dyeGray()
				this + text(" critical error ").dyeRed()
				this + text("occurred, while executing this interchange!").dyeGray()
			}.notification(TransmissionAppearance.ERROR, executor).display()
		}

		val cooldownReaction = InterchangeReaction {
			val cooldown = executor.asPlayerOrNull?.getCooldown("interchange:${interchange.key}")

			text {
				this + "You have to wait ".asComponent.color(NamedTextColor.GRAY)
				this + ((cooldown?.remaining?.toString(DurationUnit.SECONDS, 0) ?: "") + " ").asComponent
					.style(Style.style(NamedTextColor.RED, BOLD))
					.hover {
						cooldown?.destination?.getFormatted(executor.asPlayer.locale())?.asComponent?.color(NamedTextColor.GRAY)
					}
				this + "until you can execute this (sub-)interchange again!".asComponent.color(NamedTextColor.GRAY)
			}.notification(TransmissionAppearance.FAIL, executor).display()

		}

		fun defaultApproval(label: String): AppComposable<Approval?> = AppComposable { Approval.fromHost(it, "interchange.$label") }

	}

}

enum class InterchangeResult {

	SUCCESS, NOT_PERMITTED, WRONG_USAGE, WRONG_CLIENT, FAIL, BRANCH_COOLDOWN;

}

enum class InterchangeUserRestriction {

	ONLY_PLAYERS,
	ONLY_CONSOLE,
	NOT_RESTRICTED;

	fun match(sender: InterchangeExecutor): Boolean {
		return when (this) {
			ONLY_PLAYERS -> sender is Player
			ONLY_CONSOLE -> sender is ConsoleCommandSender
			NOT_RESTRICTED -> true
		}
	}

}

typealias InterchangeExecution = suspend InterchangeAccess<out InterchangeExecutor>.() -> InterchangeResult