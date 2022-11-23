package de.fruxz.sparkle.framework.infrastructure.command.completion.content

import de.fruxz.sparkle.framework.extension.world.add
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

enum class LocationProvider(private val process: (Player?, World?) -> Location?) {

	SPAWN({ player, world ->
		(player?.world ?: world)?.spawnLocation
	}),
	HERE({ player, _ ->
		player?.location
	}),
	EYES({ player, _ ->
		player?.eyeLocation
	}),
	LOOKING({ player, _ ->
		player?.rayTraceBlocks(50.0)?.hitPosition?.toLocation(player.world, player.location.yaw, player.location.pitch)
	}),
	BED({ player, _ ->
		player?.potentialBedLocation // TODO what means potential?
	}),
	ATTACKER({ player, _ ->
		player?.lastDamageCause?.entity?.location
	}),
	HIGHEST_BLOCK({ player, _ ->
		player?.location?.toHighestLocation() // TODO does that means highest block?
	}),
	HIGHEST_BLOCK_ABOVE({ player, _ ->
		player?.location?.toHighestLocation()?.add(y = 1)
	});

	fun compute(player: Player?) = this.process.invoke(player, null)
	fun compute(world: World?) = this.process.invoke(null, world)
	fun compute(player: Player?, world: World?) = this.process.invoke(player, world)

}