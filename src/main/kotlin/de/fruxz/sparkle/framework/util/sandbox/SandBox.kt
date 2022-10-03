package de.fruxz.sparkle.framework.util.sandbox

import de.fruxz.ascend.extension.catchException
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.server.component.sandbox.SandBoxComponent
import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.framework.util.visual.message.Transmission.Level.APPLIED
import de.fruxz.sparkle.framework.util.visual.message.Transmission.Level.ERROR
import de.fruxz.sparkle.framework.util.attachment.Logging
import de.fruxz.sparkle.framework.util.extension.coroutines.pluginCoroutineDispatcher
import de.fruxz.sparkle.framework.util.extension.visual.notification
import de.fruxz.sparkle.framework.util.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.util.identification.VendorsIdentifiable
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
        SparkleApp.instance.coroutineScope.launch(context = vendor.pluginCoroutineDispatcher(true)) {

            try {
                sectionLog.log(Level.INFO, "Executor '${executor.name}' is starting SandBox '$identity'!")

                SparkleCache.registeredSandBoxCalls += identityObject to (SparkleCache.registeredSandBoxCalls[identityObject] ?: 0) + 1

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
