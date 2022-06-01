package de.moltenKt.paper.app.component.messaging

import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.lang
import de.moltenKt.paper.structure.command.InterchangeUserRestriction
import de.moltenKt.paper.structure.command.structured.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.infiniteSubParameters
import de.moltenKt.paper.tool.display.message.Transmission
import org.bukkit.entity.Player

internal class ReplyInterchange : StructuredInterchange("reply", userRestriction = InterchangeUserRestriction.ONLY_PLAYERS, structure = buildInterchangeStructure {

    branch {

        addContent("...")
        infiniteSubParameters()

        concludedExecution {
            val player = executor as Player

            if (!Messaging.sendReply(player, parameters.joinToString(" "))) {

                lang["interchange.internal.message.reply.failed.noChatReceiver"].notification(Transmission.Level.FAIL, executor).display()

            }

        }

    }

})