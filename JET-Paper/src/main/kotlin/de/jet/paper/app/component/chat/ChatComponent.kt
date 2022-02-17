package de.jet.paper.app.component.chat

import de.jet.paper.extension.system
import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent

internal class ChatComponent : SmartComponent(system, AUTOSTART_MUTABLE) {

	override val thisIdentity = "Chat"

	override fun component() {

		listener(ChatListener())

	}

}