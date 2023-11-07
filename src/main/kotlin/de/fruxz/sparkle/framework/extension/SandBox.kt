package de.fruxz.sparkle.framework.extension

import dev.fruxz.ascend.extension.data.RandomTagType.ONLY_LOWERCASE
import dev.fruxz.ascend.extension.data.buildRandomTag
import dev.fruxz.ascend.tool.time.calendar.Calendar
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.sandbox.SandBox
import de.fruxz.sparkle.framework.sandbox.SandBoxInteraction
import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.sparkle.server.SparkleCache.registeredSandBoxCalls
import de.fruxz.sparkle.server.SparkleCache.registeredSandBoxes
import dev.fruxz.stacked.extension.KeyingStrategy.CONTINUE
import dev.fruxz.stacked.extension.subKey
import net.kyori.adventure.key.Key
import java.util.logging.Level

@Suppress("NOTHING_TO_INLINE") // required, because of the Throwable
inline fun buildSandBox(vendor: App, key: Key, noinline action: suspend SandBoxInteraction.() -> Unit) =
	SandBox(vendor, key, Calendar.now(), with(Throwable().stackTrace[0]) { "$className.$methodName()" }, action)

fun registerSandBox(sandBox: SandBox) {
	registeredSandBoxes += sandBox
	sandBox.vendor.log.info("registering SandBox '${sandBox.identity}'!")
}

@Suppress("NOTHING_TO_INLINE") // required, because of the Throwable
inline fun quickSandBox(vendor: App = SparkleApp.instance, key: Key = vendor.subKey(buildRandomTag(tagType = ONLY_LOWERCASE, hashtag = false), CONTINUE), noinline action: suspend SandBoxInteraction.() -> Unit) {
	registerSandBox(buildSandBox(vendor, key, action))
}

@Suppress("NOTHING_TO_INLINE") // required, because of the Throwable
inline fun quickSandBox(identity: String, vendor: App = SparkleApp.instance, noinline action: suspend SandBoxInteraction.() -> Unit) =
	quickSandBox(vendor, vendor.subKey(identity.lowercase(), CONTINUE), action)

val allSandBoxes: Set<SandBox>
	get() = registeredSandBoxes

fun getSandBox(fullIdentity: String) = registeredSandBoxes.firstOrNull { it.identity == fullIdentity }

fun destroySandBox(fullIdentity: String) {
	registeredSandBoxes = registeredSandBoxes.filter { it.identity != fullIdentity }.toSet()
	registeredSandBoxCalls = registeredSandBoxCalls.filter { it.key.identity != fullIdentity }
	sparkle.log.info("removing SandBox '$fullIdentity'!")
}

fun destroySandBox(sandBox: SandBox) =
	destroySandBox(sandBox.identity)

fun destroyAllSandBoxes() = mainLog(Level.WARNING, "WARNING! removing every SandBox!")
	.also { registeredSandBoxes = emptySet() }