package de.jet.paper.general.api.mojang

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import de.jet.paper.extension.paper.getOfflinePlayer
import de.jet.paper.extension.system
import de.jet.paper.extension.tasky.async
import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.item.quirk.Quirk
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta

@Serializable
@SerialName("MojangProfile")
@Suppress("DEPRECATION")
data class MojangProfile(
	val created_at: String?,
	val textures: MojangProfileTextures,
	val username: String,
	val username_history: List<MojangProfileUsernameHistoryEntry>,
	val uuid: String
) {

    private fun refresh(target: Player) {
        async {
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

		target.owningPlayer = getOfflinePlayer(username)

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