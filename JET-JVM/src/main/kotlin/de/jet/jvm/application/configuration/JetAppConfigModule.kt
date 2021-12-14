package de.jet.jvm.application.configuration

import de.jet.jvm.tool.smart.identification.Identifiable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This data class represents the current configured
 * state of the application.
 * @param [identity] is the unique identity of the application.
 * @param appFileFolderPath is the path to the application file folder.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("JetAppModule")
data class JetAppConfigModule(
	override val identity: String,
	val appFileFolderPath: String,
) : Identifiable<JetAppConfigModule> {

	companion object {

		fun autoGenerateFromApp(app: JetApp) =
			JetAppConfigModule(app.identity, "APP_${app.identity}/")

	}

}
