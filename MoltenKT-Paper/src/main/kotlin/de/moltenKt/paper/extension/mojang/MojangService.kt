package de.moltenKt.paper.extension.mojang

import de.moltenKt.core.extension.data.fromJson
import de.moltenKt.paper.app.MoltenApp
import de.moltenKt.paper.mojang.MojangProfile
import io.ktor.client.call.*
import io.ktor.client.request.*
import java.util.*

@Throws(NullPointerException::class)
private fun String.badRequestCheck() = apply {
	if (contains("Not Found") && contains("404")) throw NullPointerException("Failed to get profile!")
}

/**
 * @throws NullPointerException if no user is found by the [profileQuery]
 */
@Throws(NullPointerException::class)
suspend fun getMojangProfile(profileQuery: String) = MoltenApp.instance.httpClient.get("https://api.ashcon.app/mojang/v2/user/$profileQuery").body<String>().let {
	it.badRequestCheck()
	return@let it.fromJson<MojangProfile>()
}

suspend fun getMojangProfile(uuid: UUID) = getMojangProfile("$uuid")