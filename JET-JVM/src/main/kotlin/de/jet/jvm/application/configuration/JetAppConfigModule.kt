package de.jet.jvm.application.configuration

import de.jet.jvm.tool.smart.identification.Identifiable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("JetAppModule")
data class JetAppConfigModule(
	val appIdentity: String,
	val appFileFolderPath: String,
) : Identifiable<JetAppConfigModule> {

	override val identity = appIdentity

	companion object {

		fun autoGenerateFromApp(app: JetApp) =
			JetAppConfigModule(app.identity, "APP_${app.identity}/")

	}

}
