package dev.fruxz.sparkle.framework.ux.inventory.item

import dev.fruxz.kojang.Kojang
import dev.fruxz.ascend.extension.data.url
import dev.fruxz.ascend.json.globalJson
import dev.fruxz.sparkle.framework.coroutine.task.asAsync
import dev.fruxz.sparkle.framework.network.sparkleHttpClient
import dev.fruxz.sparkle.framework.system.debugLog
import dev.fruxz.sparkle.framework.system.mainLogger
import dev.fruxz.sparkle.framework.system.offlinePlayer
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.bukkit.Material
import org.bukkit.inventory.meta.SkullMeta
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

// suspend

suspend fun skull(
    ownerName: String,
    httpRequest: Boolean = false,
    timeout: Duration = 4.seconds,
    process: Item.() -> Unit = {},
): Item = Material.PLAYER_HEAD.item {

    quirk<SkullMeta> { meta ->

        when {
            httpRequest -> {
                debugLog("Getting mojang profile for '$ownerName's skull")

                val ownerProfile = asAsync { withTimeoutOrNull(timeout) {
                    Kojang.getMojangUserProfile(ownerName, json = globalJson, httpClient = sparkleHttpClient)
                } }

                meta.owningPlayer = offlinePlayer("MHF_Question") // if ownerProfile is null (and make playerProfile not-null for further use)

                ownerProfile.await()?.let { profile ->

                    meta.playerProfile = meta.playerProfile?.apply {
                        setTextures(textures.apply {
                            this.skin = url(profile.textures.skin.url)
                        })
                    }

                    meta.playerProfile?.complete(true, true)

                } ?: mainLogger.warning("Could not get mojang profile for '$ownerName'")

            }
            else -> {
                meta.owningPlayer = offlinePlayer(ownerName)
            }
        }

    }

}.apply(process)

suspend fun skull(
    ownerUUID: UUID,
    httpRequest: Boolean = false,
    timeout: Duration = 4.seconds,
    process: Item.() -> Unit = {},
) = skull(ownerName = "$ownerUUID", httpRequest, timeout).apply(process)

// blocking

fun blockingSkull(
    ownerName: String,
    httpRequest: Boolean = false,
    timeout: Duration = 4.seconds,
    process: Item.() -> Unit = {},
) = runBlocking { skull(ownerName, httpRequest, timeout) }.apply(process)

fun blockingSkull(
    ownerUUID: UUID,
    httpRequest: Boolean = false,
    timeout: Duration = 4.seconds,
    process: Item.() -> Unit = {},
) = runBlocking { skull(ownerUUID, httpRequest, timeout) }.apply(process)