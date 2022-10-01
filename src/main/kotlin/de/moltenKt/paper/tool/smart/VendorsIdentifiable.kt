package de.moltenKt.paper.tool.smart

import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.ascend.tool.smart.identification.Identity
import de.moltenKt.paper.structure.app.App
import net.kyori.adventure.key.Key

interface VendorsIdentifiable<T> : Identifiable<T>, Key {

	val vendorIdentity: Identity<out App>

	val thisIdentity: String

	val thisIdentityObject: Identity<T>
		get() = Identity(thisIdentity)

	override val identity: String
		get() = "${vendorIdentity.identity}:$thisIdentity"

	override fun value(): String = thisIdentity

	override fun namespace(): String = vendorIdentity.identity

	override fun asString(): String = "${namespace()}:${value()}"

}