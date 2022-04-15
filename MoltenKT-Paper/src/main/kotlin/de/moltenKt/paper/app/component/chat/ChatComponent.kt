package de.moltenKt.paper.app.component.chat

import de.moltenKt.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.moltenKt.paper.structure.component.SmartComponent

internal class ChatComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val thisIdentity = "Chat"

	override suspend fun component() {

		listener(ChatListener())

	}

}