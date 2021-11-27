package de.jet.minecraft.extension.o

import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.minecraft.app.JetCache.registeredSandBoxes
import de.jet.minecraft.runtime.sandbox.SandBox
import de.jet.minecraft.runtime.sandbox.SandBoxInteraction
import de.jet.minecraft.structure.app.App
import java.util.logging.Level

@Suppress("NOTHING_TO_INLINE") // required, because of the Throwable
inline fun buildSandBox(vendor: App, identity: String, noinline action: SandBoxInteraction.() -> Unit) =
	registeredSandBoxes.add(SandBox(vendor, identity, Calendar.now(), with(Throwable().stackTrace[0]) { "$className.$methodName()" }, action))
		.also { vendor.log.log(Level.INFO, "registering SandBox '$identity'!") }

val allSandBoxes: Set<SandBox>
	get() = registeredSandBoxes

fun getSandBox(fullIdentity: String) = registeredSandBoxes.firstOrNull { it.identity == fullIdentity }

fun destroySandBox(fullIdentity: String) = registeredSandBoxes.removeAll {
	(it.identity == fullIdentity).apply {
		if (this) {
			it.vendor.log.log(Level.INFO, "removing SandBox '$fullIdentity'!")
		}
	}
}

fun destroySandBox(sandBox: SandBox) =
	destroySandBox(sandBox.identity)

fun destroyAllSandBoxes() = registeredSandBoxes.clear()
	.also { println("WARNING! removing every SandBox!") }