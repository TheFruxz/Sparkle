package de.fruxz.sparkle.structure.app.interchange

import de.fruxz.sparkle.extension.display.notification
import de.fruxz.sparkle.structure.command.Interchange
import de.fruxz.sparkle.structure.command.InterchangeResult.SUCCESS
import de.fruxz.sparkle.structure.command.completion.emptyInterchangeStructure
import de.fruxz.sparkle.structure.command.execution
import de.fruxz.sparkle.tool.display.message.Transmission.Level.ERROR
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
	aliases = aliases,
	protectedAccess = false,
	completion = emptyInterchangeStructure(),
) {

	override val execution = execution {

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