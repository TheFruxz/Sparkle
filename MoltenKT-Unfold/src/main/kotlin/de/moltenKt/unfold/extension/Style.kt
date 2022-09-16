package de.moltenKt.unfold.extension

import de.moltenKt.unfold.Unfold
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.StyleSetter
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

/**
 * This function combines [this] [TextColor] with the [decoration]
 * to produce a new [Style] object, which can be easily applied to
 * a text component as a design-property.
 * @author Fruxz
 * @since 1.0
 */
@Unfold
operator fun TextColor.plus(decoration: TextDecoration) =
	Style.style(this, decoration)

/**
 * This function combines [this] [TextDecoration] with the [color]
 * to produce a new [Style] object, which can be easily applied to
 * a text component as a design-property.
 * @author Fruxz
 * @since 1.0
 */
@Unfold
operator fun TextDecoration.plus(color: TextColor) =
	color + this

/**
 * This function applies the [color] to [this] provided [TextComponent]
 * and returns the result. To change the color of the [TextComponent]
 * the [TextComponent.color] function and the [color] parameter is used.
 * @author Fruxz
 * @since 1.0
 */
@Unfold
operator fun <T : StyleSetter<T>> T.plus(color: TextColor) =
	this.color(color)

/**
 * This function applies the [decoration] to [this] provided [TextComponent]
 * and returns the result. To change the decoration of the [TextComponent]
 * the [TextComponent.decorate] function and the [decoration] parameter is used.
 * @author Fruxz
 * @since 1.0
 */
@Unfold
operator fun <T : StyleSetter<T>> T.plus(decoration: TextDecoration) =
	this.decorate(decoration)

/**
 * This function applies the [style] to [this] provided [TextComponent]
 * and returns the result. To change the style of the [TextComponent]
 * the [TextComponent.style] function and the [style] parameter is used.
 * @author Fruxz
 * @since 1.0
 */
@Unfold
operator fun TextComponent.plus(style: Style) =
	this.style(style)

/**
 * This function applies the [style] to [this] provided [TextComponent]
 * and returns the result. To change the style of the [TextComponent]
 * the [TextComponent.style] function and the [style] parameter is used.
 * @author Fruxz
 * @since 1.0
 */
@Unfold
infix fun TextComponent.style(style: Style) =
	this + style

/**
 * This function applies the [color] to [this] provided [TextComponent]
 * and returns the result. To change the color of the [TextComponent]
 * the [TextComponent.color] function and the [color] parameter is used.
 * @author Fruxz
 * @since 1.0
 */
@Unfold
infix fun TextComponent.style(color: TextColor) =
	this + color

@Unfold
fun Component.style(color: TextColor, vararg decorations: TextDecoration) =
	style(Style.style(color, *decorations))

@Unfold
fun Component.style(vararg decorations: TextDecoration) =
	style(Style.style(*decorations))

@Unfold
fun TextComponent.Builder.style(color: TextColor, vararg decorations: TextDecoration) =
	style(Style.style(color, *decorations))

@Unfold
fun TextComponent.Builder.style(vararg decorations: TextDecoration) =
	style(Style.style(*decorations))