package de.moltenKt.paper.extension.paper

import de.moltenKt.unfold.extension.Title
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.Title.Times

fun Title.copy(
	title: Component = this.title(),
	subtitle: Component = this.subtitle(),
	times: Times? = this.times(),
) = Title(title, subtitle, times)