package de.jet.minecraft.extension.paper

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

val Component.legacyString: String
	get() = LegacyComponentSerializer.legacySection().serialize(this)

val TextComponent.legacyString: String
	get() = LegacyComponentSerializer.legacySection().serialize(this)

val Any?.adventureComponent: TextComponent
	get() = LegacyComponentSerializer.builder().hexColors().extractUrls().build().deserializeOr("$this", Component.text("FAILED", NamedTextColor.RED))!!