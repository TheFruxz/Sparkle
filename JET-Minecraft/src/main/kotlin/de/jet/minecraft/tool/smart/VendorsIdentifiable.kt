package de.jet.minecraft.tool.smart

import de.jet.library.tool.smart.identification.Identifiable
import de.jet.library.tool.smart.identification.Identity
import de.jet.minecraft.structure.app.App

interface VendorsIdentifiable<T> : Identifiable<T> {

	val vendorIdentity: Identity<out App>

	val thisIdentity: String

	override val identity: String
		get() = "${vendorIdentity.identity}:$thisIdentity"

}