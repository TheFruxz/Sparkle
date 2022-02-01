package de.jet.paper.extension.mojang

import de.jet.jvm.extension.data.fromJson
import de.jet.paper.general.api.mojang.MojangProfile
import java.net.URL
import java.util.*

@Throws(NullPointerException::class)
private fun String.badRequestCheck() = apply {
	if (contains("Not Found") && contains("404")) throw NullPointerException("Failed to get profile!")
}

/**
 * @throws NullPointerException if no user is found by the [profileQuery]
 */
@Throws(NullPointerException::class)
fun getMojangProfile(profileQuery: String) = URL("https://api.ashcon.app/mojang/v2/user/$profileQuery").readText().let {
	it.badRequestCheck()
	return@let it.fromJson<MojangProfile>()
}

fun getMojangProfile(uuid: UUID) = getMojangProfile("$uuid")