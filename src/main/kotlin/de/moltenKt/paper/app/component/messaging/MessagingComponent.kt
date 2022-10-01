package de.moltenKt.paper.app.component.messaging

import de.moltenKt.paper.structure.component.SmartComponent

internal class MessagingComponent : SmartComponent(RunType.AUTOSTART_MUTABLE, true) {

    override val label = "Messaging"

    override suspend fun component() {

        interchange(MessageInterchange())
        interchange(ReplyInterchange())

    }

}