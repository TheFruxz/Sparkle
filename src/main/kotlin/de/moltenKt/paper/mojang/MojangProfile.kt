package de.moltenKt.paper.mojang

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import de.moltenKt.paper.extension.paper.offlinePlayer
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.extension.tasky.doAsync
import de.moltenKt.paper.tool.display.item.Item
import de.moltenKt.paper.tool.display.item.quirk.Quirk
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta

@Serializable
@Suppress("DEPRECATION")
data class MojangProfile(
	val uuid: String,
	val username: String,
	val username_history: List<MojangProfileUsernameHistoryEntry>,
	val textures: MojangProfileTextures,
	val created_at: String?,
) {

    private fun refresh(target: Player) {
        doAsync {
            target.playerProfile.complete(true, true)
        }
    }

    private fun edit(target: Player, process: PlayerProfile.() -> Unit) {
        target.playerProfile = target.playerProfile.apply(process)
    }

    fun applySkinToPlayer(target: Player) {
        edit(target) { setProperty(ProfileProperty("textures", this@MojangProfile.textures.raw.value, this@MojangProfile.textures.raw.signature)) }
        refresh(target)
    }

    fun applyNameToPlayer(target: Player) {
        edit(target) { name = this@MojangProfile.username }
        refresh(target)
    }

	fun applySkinToSkullMeta(target: SkullMeta, replaceName: Boolean = true) {

		target.owningPlayer = offlinePlayer(username)

		target.playerProfile = target.playerProfile?.apply {

			setProperty(ProfileProperty("textures", this@MojangProfile.textures.raw.value, this@MojangProfile.textures.raw.signature))

			if (replaceName) name = this@MojangProfile.username

		}

		system.coroutineScope.launch {
			target.playerProfile?.complete()
		}

	}

	fun applySkinToSkull(item: Item, replaceName: Boolean = true) = item.apply {
		quirk = Quirk.skull {
			playerProfile = playerProfile?.apply {

				setProperty(ProfileProperty("textures", this@MojangProfile.textures.raw.value, this@MojangProfile.textures.raw.signature))

				if (replaceName) name = this@MojangProfile.username

				complete(true, true)
			}
		}
	}

}