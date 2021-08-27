package de.jet.library.runtime.sandbox

import de.jet.app.JetCache
import de.jet.library.extension.catchException
import de.jet.library.extension.collection.replaceVariables
import de.jet.library.extension.display.notification
import de.jet.library.extension.lang
import de.jet.library.structure.app.App
import de.jet.library.tool.display.message.Transmission.Level.ERROR
import de.jet.library.tool.display.message.Transmission.Level.INFO
import de.jet.library.tool.smart.Logging
import de.jet.library.tool.smart.VendorsIdentifiable
import de.jet.library.tool.timing.calendar.Calendar
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
