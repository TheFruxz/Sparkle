package de.jet.library.structure.command

import de.jet.app.JetApp
import de.jet.library.structure.app.App
import de.jet.library.structure.command.InterchangeAuthorizationCheck.JETCHECK
import de.jet.library.structure.command.InterchangeExecutorType.BOTH
import de.jet.library.structure.smart.Identifiable
import org.bukkit.command.CommandSender
import java.util.logging.Level
import kotlin.Exception

data class Interchange(
	val vendor: Identifiable<App>,
	val label: String,
	val requiresAuthorization: Boolean = false,
	val requiredExecutorType: InterchangeExecutorType = BOTH,
	val authorizationCheck: InterchangeAuthorizationCheck = JETCHECK,
	val hiddenFromRecommendation: Boolean = false,
	val completion: Completion,
) : Identifiable<Interchange> {

	override val id = "$vendor:$label"

	val log = App.createLog(JetApp.instance.appIdentity, "Interchange")

	// runtime-functions

	fun interchangeException(exception: Exception, sender: CommandSender, executorType: InterchangeExecutorType) {
		log.log(
			Level.WARNING,
			"Executor ${sender.name} as ${executorType.name} caused an error at execution of "
		)
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