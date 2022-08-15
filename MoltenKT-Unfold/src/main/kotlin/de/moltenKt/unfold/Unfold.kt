package de.moltenKt.unfold

import de.moltenKt.core.extension.dump
import de.moltenKt.unfold.extension.asStyledComponent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.TextComponent.Builder
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEventSource

@Unfold fun buildComponent(base: TextComponent = Component.empty(), builder: Builder.() -> Unit): TextComponent =
	base.toBuilder().apply(builder).build()

context(Builder)
@Unfold operator fun String.unaryPlus(): Unit =
	append(this.asStyledComponent).dump()

context(Builder)
@Unfold operator fun ComponentLike.unaryPlus(): Builder =
	append(this)

context(Builder)
@Unfold operator fun <T : Iterable<ComponentLike>> T.unaryPlus(): Builder =
	append(this)

context(Builder)
@Unfold operator fun ClickEvent.unaryPlus(): Builder =
	clickEvent(this)

@Unfold
fun text(content: String, builder: Builder.() -> Unit = { }) = content.asStyledComponent(builder)

@Unfold
fun text(builder: Builder.() -> Unit) = text("", builder)

@Unfold
fun TextComponent.hover(process: () -> HoverEventSource<*>?) = this.hoverEvent(process())

@Unfold
fun Builder.hover(process: () -> HoverEventSource<*>) = this.hoverEvent(process())

@DslMarker
@PublishedApi
internal annotation class Unfold