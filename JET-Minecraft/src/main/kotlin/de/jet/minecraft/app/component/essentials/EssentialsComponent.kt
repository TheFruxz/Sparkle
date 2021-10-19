package de.jet.minecraft.app.component.essentials

import de.jet.minecraft.app.component.essentials.point.PointInterchange
import de.jet.minecraft.extension.system
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.minecraft.structure.component.SmartComponent

class EssentialsComponent(vendor: App = system) : SmartComponent(vendor, AUTOSTART_MUTABLE) {

	override val thisIdentity = "Essentials"

	override fun component() {
		interchange(PointInterchange(vendor))
	}

}