package de.fruxz.sparkle.framework.util.extension.mojang

import de.fruxz.ascend.extension.data.jsonBase
import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.sparkle.framework.util.mojang.MojangProfile
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.decodeFromJsonElement
import java.util.*

/**
 * throws exception if no user is found by the [profileQuery]
 */
@Throws(NullPointerException::class)
suspend fun getMojangProfile(profileQuery: String) =
	jsonBase.decodeFromJsonElement<MojangProfile>(
		SparkleApp.instance.httpClient.request("https://api.ashcon.app/mojang/v2/user/$profileQuery").body()
	)


/**
 * throws exception if no user is found by the [uuid]
 */
suspend fun getMojangProfile(uuid: UUID) = getMojangProfile("$uuid")