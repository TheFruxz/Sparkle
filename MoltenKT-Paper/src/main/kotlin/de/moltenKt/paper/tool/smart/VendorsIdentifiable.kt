package de.moltenKt.paper.tool.smart

import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.jvm.tool.smart.identification.Identity
import de.moltenKt.paper.structure.app.App

interface VendorsIdentifiable<T> : Identifiable<T> {

	val vendorIdentity: Identity<out App>

	val thisIdentity: String

	val thisIdentityObject: Identity<T>
		get() = Identity(thisIdentity)

	override val identity: String
		get() = "${vendorIdentity.identity}:$thisIdentity"

}