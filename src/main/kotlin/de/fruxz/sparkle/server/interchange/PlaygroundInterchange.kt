package de.fruxz.sparkle.server.interchange

import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredInterchange
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.util.extension.visual.notification

internal class PlaygroundInterchange : StructuredInterchange("playground", buildInterchangeStructure {

	branch {

		addContent("transmissionSound")

		branch {

			addContent(CompletionAsset.TRANSMISSION_LEVEL)

			concludedExecution {

				val level = getInput(1, CompletionAsset.TRANSMISSION_LEVEL)

				"<gradient:gray:yellow>This is the sound of the '<gold>${level.name}</gold>' Transmission!"
					.notification(level, executor)
					.display()

			}

		}

	}

})