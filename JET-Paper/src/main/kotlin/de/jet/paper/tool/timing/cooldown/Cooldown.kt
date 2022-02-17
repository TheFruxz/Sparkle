package de.jet.paper.tool.timing.cooldown

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.jvm.tool.timing.calendar.timeUnit.TimeUnit.Companion.MILLISECOND
import de.jet.paper.app.JetCache.livingCooldowns
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

	val remainingTime: Duration
		get() = Calendar.now().durationTo(destination)

	companion object {

		internal fun sectioning(section: String) =
			if (section.isBlank()) "" else "$section:"

		fun isCooldownRunning(identity: String, section: String = CooldownSection.general()) = livingCooldowns.any {
			it.key == "${sectioning(section)}$identity" && it.value.destination.isBefore(Calendar.now())
		}

		fun getCooldown(identity: String, section: String = CooldownSection.general()) = livingCooldowns.toList().firstOrNull {
			it.first == "${sectioning(section)}$identity"
		}?.second

		fun launchCooldown(identity: String, ticks: Int, section: String = CooldownSection.general()): Cooldown {

			if (isCooldownRunning(identity, section))
				livingCooldowns.remove("${sectioning(section)}$identity")

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
