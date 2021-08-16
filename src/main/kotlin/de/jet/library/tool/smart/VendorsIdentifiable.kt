package de.jet.library.tool.smart

import de.jet.library.structure.app.App

interface VendorsIdentifiable<T> : Identifiable<T> {

	val vendorIdentity: Identity<out App>

	val thisIdentity: String

	override val identity: String
		get() = "${vendorIdentity.id}:$thisIdentity"

}