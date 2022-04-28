package de.moltenKt.paper.app.component.messaging

import de.moltenKt.paper.structure.command.InterchangeUserRestriction
import de.moltenKt.paper.structure.command.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset
import de.moltenKt.paper.structure.command.completion.infiniteSubParameters
import org.bukkit.entity.Player

internal class MessageInterchange : StructuredInterchange("message", userRestriction = InterchangeUserRestriction.ONLY_PLAYERS, structure = buildInterchangeStructure {

    branch {

        addContent(CompletionAsset.ONLINE_PLAYER_NAME)

        branch {

            addContent("...")

            infiniteSubParameters()

            concludedExecution {
                val player = executor as Player
                val receiver = getInput(0, CompletionAsset.ONLINE_PLAYER_NAME)
                val message = parameters.drop(1).joinToString(" ")

                Messaging.sendMessage(player, receiver, message)

            }

        }

    }

})