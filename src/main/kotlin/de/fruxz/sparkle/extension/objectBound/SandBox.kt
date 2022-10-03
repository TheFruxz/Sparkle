package de.fruxz.sparkle.extension.objectBound

import de.fruxz.ascend.extension.data.RandomTagType.ONLY_LOWERCASE
import de.fruxz.ascend.extension.data.buildRandomTag
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.app.SparkleApp
import de.fruxz.sparkle.app.SparkleCache.registeredSandBoxCalls
import de.fruxz.sparkle.app.SparkleCache.registeredSandBoxes
import de.fruxz.sparkle.extension.mainLog
import de.fruxz.sparkle.extension.system
import de.fruxz.sparkle.runtime.sandbox.SandBox
import de.fruxz.sparkle.runtime.sandbox.SandBoxInteraction
import de.fruxz.sparkle.structure.app.App
import java.util.logging.Level

@Suppress("NOTHING_TO_INLINE") // required, because of the Throwable
inline fun buildSandBox(vendor: App, identity: String, noinline action: suspend SandBoxInteraction.() -> Unit) =
	SandBox(vendor, identity, Calendar.now(), with(Throwable().stackTrace[0]) { "$className.$methodName()" }, action)

fun registerSandBox(sandBox: SandBox) {
	registeredSandBoxes += sandBox
	sandBox.vendor.log.info("registering SandBox '${sandBox.identity}'!")
}

@Suppress("NOTHING_TO_INLINE") // required, because of the Throwable
inline fun buildAndRegisterSandBox(vendor: App = SparkleApp.instance, identity: String = buildRandomTag(tagType = ONLY_LOWERCASE, hashtag = false), noinline action: suspend SandBoxInteraction.() -> Unit) {
	registerSandBox(buildSandBox(vendor, identity, action))
}

val allSandBoxes: Set<SandBox>
	get() = registeredSandBoxes

fun getSandBox(fullIdentity: String) = registeredSandBoxes.firstOrNull { it.identity == fullIdentity }

fun destroySandBox(fullIdentity: String) {
	registeredSandBoxes = registeredSandBoxes.filter { it.identity != fullIdentity }.toSet()
	registeredSandBoxCalls = registeredSandBoxCalls.filter { it.key.identity != fullIdentity }
	system.log.info("removing SandBox '$fullIdentity'!")
}

fun destroySandBox(sandBox: SandBox) =
	destroySandBox(sandBox.identity)

fun destroyAllSandBoxes() = mainLog(Level.WARNING, "WARNING! removing every SandBox!")
	.also { registeredSandBoxes = emptySet() }