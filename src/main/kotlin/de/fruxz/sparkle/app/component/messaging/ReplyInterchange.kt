package de.fruxz.sparkle.app.component.messaging

import de.fruxz.sparkle.extension.display.notification
import de.fruxz.sparkle.structure.command.InterchangeUserRestriction
import de.fruxz.sparkle.structure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.structure.command.completion.infiniteSubParameters
import de.fruxz.sparkle.structure.command.structured.StructuredInterchange
import de.fruxz.sparkle.tool.display.message.Transmission
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeRed
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import org.bukkit.entity.Player

internal class ReplyInterchange : StructuredInterchange("reply", userRestriction = InterchangeUserRestriction.ONLY_PLAYERS, structure = buildInterchangeStructure {

    branch {

        addContent("...")
        infiniteSubParameters()

        concludedExecution {
            val player = executor as Player

            if (!Messaging.sendReply(player, parameters.joinToString(" "))) {

                text {
                    this + text("You're").dyeGray()
                    this + text(" not ").dyeRed()
                    this + text("having an active conversation with someone online!").dyeGray()
                }.notification(Transmission.Level.FAIL, executor).display()

            }

        }

    }

})