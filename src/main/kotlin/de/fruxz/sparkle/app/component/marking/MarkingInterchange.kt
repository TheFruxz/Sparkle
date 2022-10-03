package de.fruxz.sparkle.app.component.marking

import de.fruxz.sparkle.extension.display.notification
import de.fruxz.sparkle.extension.display.ui.addItems
import de.fruxz.sparkle.extension.tasky.asSync
import de.fruxz.sparkle.structure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.structure.command.completion.ignoreCase
import de.fruxz.sparkle.structure.command.structured.StructuredPlayerInterchange
import de.fruxz.sparkle.tool.display.message.Transmission.Level.APPLIED
import de.fruxz.stacked.extension.dyeGold
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.plus
import de.fruxz.stacked.text

internal class MarkingInterchange : StructuredPlayerInterchange(
	label = "markings",
	protectedAccess = true,
	structure = buildInterchangeStructure {

		branch {

			addContent("giveItem")
			ignoreCase()

			concludedExecution {

				asSync { executor.inventory.addItems(MarkingComponent.markingItem) }

				text {
					this + text("The ").dyeGray()
					this + text("Marking Tool").dyeGold()
					this + text(" has been added to your inventory.").dyeGray()
				}.notification(APPLIED, executor).display()

			}

		}

	}
)