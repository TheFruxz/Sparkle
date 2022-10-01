package de.fruxz.sparkle.app.component.messaging

import de.fruxz.sparkle.structure.component.SmartComponent

internal class MessagingComponent : SmartComponent(RunType.AUTOSTART_MUTABLE, true) {

    override val label = "Messaging"

    override suspend fun component() {

        interchange(MessageInterchange())
        interchange(ReplyInterchange())

    }

}