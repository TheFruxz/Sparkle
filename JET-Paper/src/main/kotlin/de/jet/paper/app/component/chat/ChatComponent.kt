package de.jet.paper.app.component.chat

import de.jet.paper.extension.system
import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent

internal class ChatComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val thisIdentity = "Chat"

	override suspend fun component() {

		listener(ChatListener())

	}

}