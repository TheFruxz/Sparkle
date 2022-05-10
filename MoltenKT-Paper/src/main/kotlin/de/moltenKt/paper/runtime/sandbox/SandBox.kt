package de.moltenKt.paper.runtime.sandbox

import de.moltenKt.core.extension.catchException
import de.moltenKt.core.extension.container.replaceVariables
import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.app.component.sandbox.SandBoxComponent
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.extension.lang
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.component.Component
import de.moltenKt.paper.tool.display.message.Transmission.Level.APPLIED
import de.moltenKt.paper.tool.display.message.Transmission.Level.ERROR
import de.moltenKt.paper.tool.smart.Logging
import de.moltenKt.paper.tool.smart.VendorsIdentifiable
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
        de.moltenKt.paper.app.MoltenApp.instance.coroutineScope.launch(Component.getInstance(SandBoxComponent::class).threadContext) {

            try {
                sectionLog.log(Level.INFO, "Executor '${executor.name}' is starting SandBox '$identity'!")

                MoltenCache.registeredSandBoxCalls += identityObject to (MoltenCache.registeredSandBoxCalls[identityObject] ?: 0) + 1

                lang["interchange.internal.sandbox.run"]
                    .replaceVariables("sandbox" to identity)
                    .notification(APPLIED, executor).display()

                process(SandBoxInteraction(this@SandBox, executor, parameters))

                sectionLog.log(Level.INFO, "Executor '${executor.name}' finished the use of SandBox '$identity' successfully!")
            } catch (exception: Exception) {
                catchException(exception)
                lang["interchange.internal.sandbox.failed"]
                    .replaceVariables("sandbox" to identity)
                    .notification(ERROR, executor).display()
            }

        }

    }

}
