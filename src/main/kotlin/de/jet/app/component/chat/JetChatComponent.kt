package de.jet.app.component.chat

import de.jet.library.extension.display.ui.buildContainer
import de.jet.library.structure.app.App
import de.jet.library.structure.app.event.EventListener
import de.jet.library.structure.component.Component
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.event.EventHandler

class JetChatComponent(vendor: App) : Component(vendor) {

	override val id = "JETChat"

	private val handler by lazy {
		Handler(vendor)
	}

	override fun start() {
		vendor.add(handler)
	}

	override fun stop() {
		vendor.remove(handler)
	}

	class Handler(override val vendor: App) : EventListener {

		@EventHandler
		fun onChat(event: AsyncChatEvent) {

		}

	}

}