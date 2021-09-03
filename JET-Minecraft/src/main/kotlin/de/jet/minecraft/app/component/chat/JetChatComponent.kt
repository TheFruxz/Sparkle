package de.jet.minecraft.app.component.chat

import de.jet.minecraft.extension.system
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.app.event.EventListener
import de.jet.minecraft.structure.component.Component
import de.jet.minecraft.structure.component.Component.RunType.DISABLED
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.event.EventHandler

internal class JetChatComponent(vendor: App = system) : Component(vendor, DISABLED /* TODO: 30.08.2021 Because has no contents! */ ) {

	override val thisIdentity = "Chat"

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
			TODO("$event would be used in the future!")
		}

	}

}