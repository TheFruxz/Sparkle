package de.jet.minecraft.app.component.essentials.world

import de.jet.minecraft.extension.display.ui.buildPanel
import de.jet.minecraft.extension.display.ui.item
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.command.Interchange
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS
import org.bukkit.Material
import org.bukkit.entity.Player

class WorldInterchange(vendor: App) : Interchange(vendor, "world", requiresAuthorization = true) {

	val panel = buildPanel(lines = 5) {

		onReceive {
			placeInner(0, Material.STONE.item.apply {
				label = "You're ${it.receiver.name}"
			})
		}

	}

	override val execution = execution {

		panel.display(executor as Player)

		SUCCESS
	}

}