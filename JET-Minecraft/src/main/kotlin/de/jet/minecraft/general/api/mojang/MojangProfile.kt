package de.jet.minecraft.general.api.mojang

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import de.jet.minecraft.extension.tasky.async
import kotlinx.serialization.Serializable
import org.bukkit.entity.Player

@Serializable
data class MojangProfile(
    val created_at: String?,
    val textures: Textures,
    val username: String,
    val username_history: List<UsernameHistoryEntry>,
    val uuid: String
) {

    internal fun refresh(target: Player) {
        async {
            target.playerProfile.complete(true, true)
        }
    }

    internal fun edit(target: Player, process: PlayerProfile.() -> Unit) {
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

}