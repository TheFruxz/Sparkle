package de.moltenKt.paper.app.component.messaging

import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.structure.command.InterchangeUserRestriction
import de.moltenKt.paper.structure.command.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.infiniteSubParameters
import de.moltenKt.paper.tool.display.message.Transmission
import de.moltenKt.unfold.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

internal class ReplyInterchange : StructuredInterchange("reply", userRestriction = InterchangeUserRestriction.ONLY_PLAYERS, structure = buildInterchangeStructure {

    branch {

        addContent("...")
        infiniteSubParameters()

        concludedExecution {
            val player = executor as Player

            if (!Messaging.sendReply(player, parameters.joinToString(" "))) {

                text {
                    text("You're ") {
                        color(NamedTextColor.GRAY)
                    }
                    text("not ") {
                        color(NamedTextColor.RED)
                    }
                    text("having an active conversation with anyone.") {
                        color(NamedTextColor.GRAY)
                    }
                }.notification(Transmission.Level.FAIL, executor).display()

            }

        }

    }

})