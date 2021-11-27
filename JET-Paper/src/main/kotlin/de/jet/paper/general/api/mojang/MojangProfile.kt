package de.jet.paper.general.api.mojang

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import de.jet.paper.extension.tasky.async
import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.item.quirk.Quirk
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta

@Serializable
@SerialName("MojangProfile")
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
        edit(target) { setProperty(ProfileProperty("textures", textures.raw.value, textures.raw.signature)) }
        refresh(target)
    }

    fun applyNameToPlayer(target: Player) {
        edit(target) { name = this@MojangProfile.username }
        refresh(target)
    }

	/**
	 * WARNING! owningPlayer have to be set!
	 */
	fun applySkinToSkullMeta(target: SkullMeta, replaceName: Boolean) {
		target.playerProfile?.apply {
			setProperty(ProfileProperty("textures", textures.raw.value, textures.raw.signature))
			if (replaceName)
				name = this@MojangProfile.username
			complete(true, true)
		}
	}

	/**
	 * WARNING! current quirk will be overwritten!
	 */
	fun applySkinToSkull(item: Item, replaceName: Boolean) = item.apply {
		quirk = Quirk.skull {
			playerProfile?.apply {
				setProperty(ProfileProperty("textures", textures.raw.value, textures.raw.signature))
				if (replaceName)
					name = this@MojangProfile.username
				complete(true, true)
			}
		}
	}

}