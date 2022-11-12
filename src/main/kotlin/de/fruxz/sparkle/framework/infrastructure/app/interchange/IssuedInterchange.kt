package de.fruxz.sparkle.framework.infrastructure.app.interchange

import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeExecution
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.SUCCESS
import de.fruxz.sparkle.framework.infrastructure.command.completion.emptyInterchangeStructure
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance.Companion.ERROR
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeRed
import de.fruxz.stacked.extension.dyeYellow
import de.fruxz.stacked.extension.style
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration.BOLD

class IssuedInterchange(
	label: String,
	aliases: Set<String>,
) : Interchange(
	label = label,
	requiredApproval = null,
	commandProperties = CommandProperties(aliases = aliases),
	completion = emptyInterchangeStructure(),
) {

	override val execution: InterchangeExecution = {

		text {
			this + text("Oops!").style(NamedTextColor.RED, BOLD)
			this + text(" This interchange ").dyeGray()
			this + text("crashed").dyeRed()
			this + text(" during the ").dyeGray()
			this + text("registration").dyeYellow()
			this + text(" process, please report this to a technician!").dyeGray()
		}.notification(ERROR, executor).display()

		SUCCESS
	}

}