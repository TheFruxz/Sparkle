package de.fruxz.sparkle.framework.sandbox

import de.fruxz.ascend.extension.catchException
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.framework.attachment.Logging
import de.fruxz.sparkle.framework.extension.coroutines.pluginCoroutineDispatcher
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.identification.KeyedIdentifiable
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.visual.message.Transmission.Level.APPLIED
import de.fruxz.sparkle.framework.visual.message.Transmission.Level.ERROR
import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeYellow
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import kotlinx.coroutines.launch
import net.kyori.adventure.key.Key
import java.util.logging.Level

data class SandBox(
    override val vendor: App,
    override val identityKey: Key,
    val creationTime: Calendar,
    val creationLocation: String,
    val process: suspend SandBoxInteraction.() -> Unit,
) : KeyedIdentifiable<SandBox>, Logging {

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
