package de.moltenKt.paper.tool.timing.cooldown

import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.paper.app.MoltenCache.livingCooldowns
import de.moltenKt.paper.extension.timing.minecraftTicks
import org.bukkit.Server
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import kotlin.time.Duration

data class Cooldown(
	override val identity: String,
	val ticks: Int,
) : Identifiable<Cooldown> {

	constructor(identity: String, ticks: Number) : this(identity, ticks.toInt())

	val destination = Calendar.now().add(ticks.minecraftTicks)

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

		@JvmStatic
		fun isCooldownRunning(identity: String, section: String = CooldownSection.general()) = livingCooldowns.any {
			it.key == "${sectioning(section)}$identity" && it.value.destination.isBefore(Calendar.now())
		}

		@JvmStatic
		fun getCooldown(identity: String, section: String = CooldownSection.general()) = livingCooldowns.toList().firstOrNull {
			it.first == "${sectioning(section)}$identity"
		}?.second

		@JvmStatic
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
