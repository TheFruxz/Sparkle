package de.fruxz.sparkle.server.component.messaging

import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class MessagingComponent : SmartComponent(RunType.AUTOSTART_MUTABLE, true) {

    override val label = "Messaging"

    override suspend fun component() {

        interchange(MessageInterchange())
        interchange(ReplyInterchange())

    }

}