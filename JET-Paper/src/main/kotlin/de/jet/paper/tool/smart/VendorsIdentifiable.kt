package de.jet.paper.tool.smart

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.structure.app.App

interface VendorsIdentifiable<T> : Identifiable<T> {

	val vendorIdentity: Identity<out App>

	val thisIdentity: String

	val thisIdentityObject: Identity<T>
		get() = Identity(thisIdentity)

	override val identity: String
		get() = "${vendorIdentity.identity}:$thisIdentity"

}