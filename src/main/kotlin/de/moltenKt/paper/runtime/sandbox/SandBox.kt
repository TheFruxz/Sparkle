package de.moltenKt.paper.runtime.sandbox

import de.fruxz.ascend.extension.catchException
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.moltenKt.paper.app.MoltenApp
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.app.component.sandbox.SandBoxComponent
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.component.Component
import de.moltenKt.paper.tool.display.message.Transmission.Level.APPLIED
import de.moltenKt.paper.tool.display.message.Transmission.Level.ERROR
import de.moltenKt.paper.tool.smart.Logging
import de.moltenKt.paper.tool.smart.VendorsIdentifiable
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeYellow
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import kotlinx.coroutines.launch
import java.util.logging.Level

data class SandBox(
    override val vendor: App,
    override val thisIdentity: String,
    val creationTime: Calendar,
    val creationLocation: String,
    val process: suspend SandBoxInteraction.() -> Unit,
) : VendorsIdentifiable<SandBox>, Logging {

    init {
    	if (thisIdentity.contains("[§: ]".toRegex())) throw IllegalArgumentException("It is not allowed, that the identity of a sandbox contains a '§', ':' or a ' '!")
    }

    override val vendorIdentity = vendor.identityObject

    override val sectionLabel by lazy { identity }

    fun execute(executor: InterchangeExecutor, parameters: List<String> = emptyList()) {
        MoltenApp.instance.coroutineScope.launch(Component.getInstance(SandBoxComponent::class).threadContext) {

            try {
                sectionLog.log(Level.INFO, "Executor '${executor.name}' is starting SandBox '$identity'!")

                MoltenCache.registeredSandBoxCalls += identityObject to (MoltenCache.registeredSandBoxCalls[identityObject] ?: 0) + 1

                text {
                    this + text("You're now running the SandBox '").dyeGray()
                    this + text(identity).dyeYellow()
                    this + text("'!").dyeGray()
                }.notification(APPLIED, executor).display()

                process(SandBoxInteraction(this@SandBox, executor, parameters))

                sectionLog.log(Level.INFO, "Executor '${executor.name}' finished the use of SandBox '$identity' successfully!")
            } catch (exception: Exception) {
                catchException(exception)

                text {
                    this + text("The SandBox '").dyeGray()
                    this + text(identity).dyeYellow()
                    this + text("' failed!").dyeGray()
                }.notification(ERROR, executor).display()

            }

        }

    }

}
