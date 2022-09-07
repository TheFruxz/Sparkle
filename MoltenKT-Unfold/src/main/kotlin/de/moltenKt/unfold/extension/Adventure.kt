package de.moltenKt.unfold.extension

import de.moltenKt.core.extension.switchResult
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.TextComponent.Builder
import net.kyori.adventure.text.flattener.ComponentFlattener
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.ComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

/**
 * This value represents the [LegacyComponentSerializer] instance, which
 * is used to convert between strings/objects and [Component]s.
 * @see ComponentLike.asString
 * @see String.asComponent
 * @see String.asStyledComponent
 * @author Fruxz
 * @since 1.0
 */
val adventureSerializer = LegacyComponentSerializer
	.builder().extractUrls().hexColors().build()

val plainAdventureSerializer = PlainTextComponentSerializer.plainText()

/**
 * This value represents the [MiniMessage] instance, which
 * is used to convert between strings/objects and [Component]s.
 * This is especially adding the [String]-features like `<rainbow>`!
 * @see ComponentLike.asStyledString
 * @see String.asStyledComponent
 * @author Fruxz
 * @since 1.0
 */
val miniMessageSerializer = MiniMessage.miniMessage()

val strictMiniMessageSerializer = MiniMessage.builder().strict(true).build()

/**
 * This computational value converts this [ComponentLike]
 * into a [String] by using the [LegacyComponentSerializer],
 * provided by the [adventureSerializer] value.
 * @see adventureSerializer
 * @author Fruxz
 * @since 1.0
 */
val ComponentLike.asString: String
	get() = adventureSerializer.serialize(asComponent())

val ComponentLike.asPlainString: String
	get() = plainAdventureSerializer.serialize(asComponent())

/**
 * This computational value converts this [String] into a [TextComponent]
 * by using the [LegacyComponentSerializer], provided by the
 * [adventureSerializer] value.
 * @see adventureSerializer
 * @author Fruxz
 * @since 1.0
 */
val String.asComponent: TextComponent
	get() = adventureSerializer.deserializeOr(this, Component.text("FAILED", NamedTextColor.RED))!!

fun String.asComponent(builder: Builder.() -> Unit) =
	asComponent.toBuilder().apply(builder).build()

/**
 * This computational value converts this [String] into a [TextComponent]
 * list (every list entry is a line) by using the [LegacyComponentSerializer],
 * provided by the [adventureSerializer] value.
 * @see adventureSerializer
 * @author Fruxz
 * @since 1.0
 */
val String.asComponents: List<TextComponent>
	get() = this.lines().asComponents

/**
 * This computational value converts this [Iterable] into a [TextComponent]
 * list (every list entry is a line) by using the [LegacyComponentSerializer],
 * provided by the [adventureSerializer] value.
 * @see adventureSerializer
 * @author Fruxz
 * @since 1.0
 */
val Iterable<String>.asComponents: List<TextComponent>
	get() = map { it.asComponent }

/**
 * This computational value converts this [ComponentLike]
 * into a [String] by using the [MiniMessage], provided by the
 * [miniMessageSerializer] value.
 * This is especially adding the [String]-features like `<rainbow>`!
 * @see miniMessageSerializer
 * @author Fruxz
 * @since 1.0
 */
val ComponentLike.asStyledString: String
	get() = strictMiniMessageSerializer.serialize(asComponent())

fun ComponentLike.asStyledString(strict: Boolean = true) =
	strict.switchResult(asStyledString, miniMessageSerializer.serialize(asComponent()))

/**
 * This computational value converts this [String] into a [TextComponent]
 * by using the [MiniMessage], provided by the
 * [miniMessageSerializer] value.
 * This is especially adding the [String]-features like `<rainbow>`!
 * @see miniMessageSerializer
 * @author Fruxz
 * @since 1.0
 */
val String.asStyledComponent: TextComponent
	get() = Component.text().append(miniMessageSerializer.deserializeOr(this, Component.text("FAILED", NamedTextColor.RED))!!).build()

fun String.asStyledComponent(builder: Builder.() -> Unit) =
	asStyledComponent.toBuilder().apply(builder).build()

/**
 * This computational value converts this [String] into a [TextComponent]
 * list (every entry represents a line) by using the [MiniMessage], provided by the
 * [miniMessageSerializer] value.
 * This is especially adding the [String]-features like `<rainbow>`!
 * @see miniMessageSerializer
 * @author Fruxz
 * @since 1.0
 */
val String.asStyledComponents: List<TextComponent>
	get() = this.lines().asStyledComponents

/**
 * This computational value converts this [Iterable] into a [TextComponent]
 * list (every entry represents a line) by using the [MiniMessage], provided by the
 * [miniMessageSerializer] value.
 * This is especially adding the [String]-features like `<rainbow>`!
 * @see miniMessageSerializer
 * @author Fruxz
 * @since 1.0
 */
val Iterable<String>.asStyledComponents: List<TextComponent>
	get() = map { it.asStyledComponent }