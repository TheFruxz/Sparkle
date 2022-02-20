package de.jet.paper.runtime.sandbox

import de.jet.jvm.extension.catchException
import de.jet.jvm.extension.container.replaceVariables
import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.paper.app.JetApp
import de.jet.paper.app.JetCache
import de.jet.paper.app.component.sandbox.SandBoxComponent
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.interchange.InterchangeExecutor
import de.jet.paper.extension.lang
import de.jet.paper.structure.app.App
import de.jet.paper.structure.component.Component
import de.jet.paper.tool.display.message.Transmission.Level.APPLIED
import de.jet.paper.tool.display.message.Transmission.Level.ERROR
import de.jet.paper.tool.smart.Logging
import de.jet.paper.tool.smart.VendorsIdentifiable
import kotlinx.coroutines.launch
import java.util.logging.Level

data class SandBox(
    override val vendor: App,
    override val thisIdentity: String,
    val creationTime: Calendar,
    val creationLocation: String,
    val process: suspend SandBoxInteraction.() -> Unit,
) : VendorsIdentifiable<SandBox>, Logging {

    override val vendorIdentity = vendor.identityObject

    override val sectionLabel by lazy { identity }

    fun execute(executor: InterchangeExecutor, parameters: List<String> = emptyList()) {
        JetApp.instance.coroutineScope.launch(Component.getInstance(SandBoxComponent::class).threadContext) {

            try {
                sectionLog.log(Level.INFO, "Executor '${executor.name}' is starting SandBox '$identity'!")

                JetCache.registeredSandBoxCalls[identityObject] = (JetCache.registeredSandBoxCalls[identityObject] ?: 0) + 1

                lang("interchange.internal.sandbox.run")
                    .replaceVariables("sandbox" to identity)
                    .notification(APPLIED, executor).display()

                process(SandBoxInteraction(this@SandBox, executor, parameters))

                sectionLog.log(Level.INFO, "Executor '${executor.name}' finished the use of SandBox '$identity' successfully!")
            } catch (exception: Exception) {
                catchException(exception)
                lang("interchange.internal.sandbox.failed")
                    .replaceVariables("sandbox" to identity)
                    .notification(ERROR, executor).display()
            }

        }

    }

}
