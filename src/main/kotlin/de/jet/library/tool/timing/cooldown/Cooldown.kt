package de.jet.library.tool.timing.cooldown

import de.jet.app.JetCache.cooldowns
import de.jet.library.tool.smart.Identifiable
import de.jet.library.tool.timing.calendar.Calendar
import de.jet.library.tool.timing.calendar.Calendar.TimeField.MILLISECOND
import org.bukkit.Server
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

data class Cooldown(
	override val identity: String,
	val ticks: Int,
) : Identifiable<Cooldown> {

	constructor(identity: String, ticks: Number) : this(identity, ticks.toInt())

	val destination = Calendar.now().add(MILLISECOND, 50 * ticks)

	fun startCooldown() {
		cooldowns[identity] = this
	}

	fun stopCooldown() {
		cooldowns.remove(identity)
	}

	val isOver: Boolean
		get() = Calendar.now().after(destination)

	companion object {

		private fun sectioning(section: String) =
			if (section.isBlank()) "" else "$section:"

		fun isCooldownRunning(identity: String, section:  String = CooldownSection.general()) = cooldowns.any {
			it.key == "${sectioning(section)}:$identity" && it.value.destination.before(Calendar.now())
		}

		fun launchCooldown(identity: String, ticks: Int, section: String = CooldownSection.general()) {

			if (isCooldownRunning(identity, section))
				cooldowns.remove("$section:$identity")

			Cooldown("${sectioning(section)}:$identity", ticks).startCooldown()

		}

		object CooldownSection {
			fun general() = "general"
			fun world(world: String) = "world/$world"
			fun entity(entity: Entity) = "entity/${entity.uniqueId}"
			fun player(player: Player) = "player/${player.uniqueId}"
			fun server(server: Server) = "server/${server.name}"
		}

	}

}
