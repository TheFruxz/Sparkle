package de.jet.unfold.extension

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

val adventureSerializer = LegacyComponentSerializer
	.builder().extractUrls().hexColors().build()

val Component.legacyString: String
	get() = adventureSerializer.serialize(this)

val TextComponent.legacyString: String
	get() = adventureSerializer.serialize(this)

val Any?.adventureComponent: TextComponent
	get() = adventureSerializer.deserializeOr("$this", Component.text("FAILED", NamedTextColor.RED))!!