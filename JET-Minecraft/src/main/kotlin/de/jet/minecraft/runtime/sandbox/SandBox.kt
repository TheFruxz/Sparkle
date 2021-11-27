package de.jet.minecraft.runtime.sandbox

import de.jet.jvm.extension.catchException
import de.jet.jvm.extension.collection.replaceVariables
import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.extension.display.notification
import de.jet.minecraft.extension.lang
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.tool.display.message.Transmission.Level.ERROR
import de.jet.minecraft.tool.display.message.Transmission.Level.INFO
import de.jet.minecraft.tool.smart.Logging
import de.jet.minecraft.tool.smart.VendorsIdentifiable
import org.bukkit.command.CommandSender
import java.util.logging.Level

data class SandBox(
    override val vendor: App,
    override val thisIdentity: String,
    val creationTime: Calendar,
    val creationLocation: String,
    val process: SandBoxInteraction.() -> Unit,
) : VendorsIdentifiable<SandBox>, Logging {

    override val vendorIdentity = vendor.identityObject

    override val sectionLabel by lazy { identity }

    fun execute(executor: CommandSender, parameters: List<String> = emptyList()) {

        try {
            sectionLog.log(Level.INFO, "Executor '${executor.name}' is starting SandBox '$identity'!")

            JetCache.registeredSandBoxCalls[identityObject] = (JetCache.registeredSandBoxCalls[identityObject] ?: 0) + 1

            lang("interchange.internal.sandbox.run")
                .replaceVariables("sandbox" to identity)
                .notification(INFO, executor).display()

            process(SandBoxInteraction(this, executor, parameters))

            sectionLog.log(Level.INFO, "Executor '${executor.name}' finished the use of SandBox '$identity' successfully!")
        } catch (exception: Exception) {
            catchException(exception)
            lang("interchange.internal.sandbox.failed")
                .replaceVariables("sandbox" to identity)
                .notification(ERROR, executor).display()
        }

    }

}
