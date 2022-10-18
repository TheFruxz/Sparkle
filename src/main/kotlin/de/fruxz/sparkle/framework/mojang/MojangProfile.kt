package de.fruxz.sparkle.framework.mojang

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import de.fruxz.sparkle.framework.extension.coroutines.doAsync
import de.fruxz.sparkle.framework.extension.offlinePlayer
import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.visual.item.Item
import de.fruxz.sparkle.framework.visual.item.quirk.Quirk
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class MojangProfile constructor(
	val uuid: String,
	val username: String,
	@JsonNames("username_history") val usernameHistory: List<MojangProfileUsernameHistoryEntry>,
	val textures: MojangProfileTextures,
	@JsonNames("created_at") val createdAt: String?,
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

	fun applySkinToSkullMeta(target: SkullMeta) {

		target.owningPlayer = offlinePlayer(username)

		target.playerProfile = target.playerProfile?.apply {
			setProperty(ProfileProperty("textures", this@MojangProfile.textures.raw.value, this@MojangProfile.textures.raw.signature))
		}

		sparkle.coroutineScope.launch {
			target.playerProfile?.complete()
		}

	}

	fun applySkinToSkull(item: Item) = item.apply {
		quirk = Quirk.skull {
			playerProfile = playerProfile?.apply {
				setProperty(ProfileProperty("textures", this@MojangProfile.textures.raw.value, this@MojangProfile.textures.raw.signature))
				complete(true, true)
			}
		}
	}

}