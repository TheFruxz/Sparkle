package de.moltenKt.paper.app.component.marking

import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.display.ui.addItems
import de.moltenKt.paper.extension.tasky.asSync
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.ignoreCase
import de.moltenKt.paper.structure.command.structured.StructuredPlayerInterchange
import de.moltenKt.paper.tool.display.message.Transmission.Level.APPLIED
import de.moltenKt.unfold.extension.dyeGold
import de.moltenKt.unfold.extension.dyeGray
import de.moltenKt.unfold.plus
import de.moltenKt.unfold.text

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