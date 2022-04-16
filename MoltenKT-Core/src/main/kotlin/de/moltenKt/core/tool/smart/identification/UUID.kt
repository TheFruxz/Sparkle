package de.moltenKt.core.tool.smart.identification

import de.moltenKt.core.extension.classType.UUID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID as UtilUUID

@Serializable
@SerialName("UUID")
data class UUID(
	@SerialName("uuid") override val identity: String
) : Identifiable<UUID> {

	init {
		if (identity.length != 36) {
			throw IllegalArgumentException("UUID must be 36 characters long")
		}
	}

	val java: UtilUUID
		get() = UtilUUID.fromString(identity)

	override fun toString() =
		java.toString()

	companion object {

		fun randomUUID() =
			UUID(UtilUUID.randomUUID().toString())

		fun fromString(uuid: String) =
			UUID(UtilUUID.fromString(uuid).toString())

		fun randomString() =
			UtilUUID.randomUUID().toString()

	}

}