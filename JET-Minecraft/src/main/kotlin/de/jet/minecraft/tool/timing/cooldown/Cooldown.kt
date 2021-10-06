package de.jet.minecraft.tool.timing.cooldown

import de.jet.library.tool.smart.identification.Identifiable
import de.jet.library.tool.timing.calendar.Calendar
import de.jet.library.tool.timing.calendar.Calendar.TimeField.MILLISECOND
import de.jet.minecraft.app.JetCache.livingCooldowns
import org.bukkit.Server
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import kotlin.time.Duration

data class Cooldown(
	override val identity: String,
	val ticks: Int,
) : Identifiable<Cooldown> {

	constructor(identity: String, ticks: Number) : this(identity, ticks.toInt())

	val destination = Calendar.now().add(MILLISECOND, 50 * ticks)

	fun startCooldown() {
		livingCooldowns[identity] = this
	}

	fun stopCooldown() {
		livingCooldowns.remove(identity)
	}

	val remainingCooldown: Duration
		get() = Calendar.now().durationTo(destination)

	companion object {

		internal fun sectioning(section: String) =
			if (section.isBlank()) "" else "$section:"

		fun isCooldownRunning(identity: String, section:  String = CooldownSection.general()) = livingCooldowns.any {
			it.key == "${sectioning(section)}:$identity" && it.value.destination.before(Calendar.now())
		}

		fun launchCooldown(identity: String, ticks: Int, section: String = CooldownSection.general()): Cooldown {

			if (isCooldownRunning(identity, section))
				livingCooldowns.remove("$section:$identity")

			return Cooldown("${sectioning(section)}:$identity", ticks).apply {
				startCooldown()
			}

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
