package de.moltenKt.unfold

import de.moltenKt.unfold.extension.asStyledComponent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.TextComponent.Builder
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEventSource
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor

/**
 * This function creates a [TextComponent] with the given base and modifier.
 * A new base is getting formed via the base parameter, which is by default a [Component.empty] TextComponent.
 * Then it gets converted to a [Builder] using the [TextComponent.toBuilder] function.
 * After that the [builder] parameter is called onto the builder of the [base] and the result
 * gets build and returned via the [Builder.build] function.
 * @param base The base component, which is by default a [Component.empty] TextComponent.
 * @param builder The builder function, which gets called onto the builder of the [base].
 * @return The built [TextComponent].
 * @see TextComponent
 * @see Builder
 * @see Component.empty
 * @see TextComponent.toBuilder
 * @see Builder.build
 * @author Fruxz
 * @since 1.0
 */
@Unfold fun buildComponent(base: TextComponent = Component.empty(), builder: Builder.() -> Unit): TextComponent =
	base.toBuilder().apply(builder).build()

/* TODO reintroduce if context api gets available
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
 */

/**
 * This operator function, which will be replaced with an *unaryPlus* function in the future,
 * allows you to append a [String] specified by [styledString] to the current [Builder] using
 * the conversion via the [String.asStyledComponent] value.
 * @param styledString The string, which will be converted and applied to this [Builder]
 * @return The current [Builder] instance
 * @see String.asStyledComponent
 * @see Builder.append
 * @author Fruxz
 * @since 1.0
 */
@Unfold infix operator fun Builder.plus(styledString: String): Builder =
	append(styledString.asStyledComponent)

/**
 * This operator function, which will be replaced with an *unaryPlus* function in the future,
 * allows you to append a [ComponentLike] specified by [componentLike] to the current [Builder].
 * @param component The [ComponentLike] instance, which will be applied to this [Builder]
 * @return The current [Builder] instance
 * @see Builder.append
 * @author Fruxz
 * @since 1.0
 */
@Unfold infix operator fun Builder.plus(component: ComponentLike): Builder =
	append(component)

/**
 * This operator function, which will be replaced with an *unaryPlus* function in the future,
 * allows you to append a [Iterable] of [ComponentLike] specified by [components] to the current [Builder].
 * @param components The [Iterable] of [ComponentLike] instances, which will be applied to this [Builder]
 * @return The current [Builder] instance
 * @see Builder.append
 * @author Fruxz
 * @since 1.0
 */
@Unfold infix operator fun Builder.plus(components: Iterable<ComponentLike>): Builder =
	append(components)

/**
 * This operator function, which will be replaced with an *unaryPlus* function in the future,
 * allows you to append a [Component] provided by the [component] parameter to this [I].
 * @param component The [Component] instance, which will be applied to this [I]
 * @param I Any object, based on an [Component]
 * @return The modified [I] as an [Component]
 * @see Component.append
 * @author Fruxz
 * @since 1.0
 */
@Unfold infix operator fun <I : Component> I.plus(component: Component): Component =
	append(component)

/**
 * This operator function, which will be replaced with an *unaryPlus* function in the future,
 * allows you to set the click event of this [Builder] with the provided [clickEvent].
 * @param clickEvent The [ClickEvent] instance, which will be applied to this [Builder]
 * @return The current [Builder] instance
 * @see Builder.clickEvent
 * @author Fruxz
 * @since 1.0
 */
@Unfold infix operator fun Builder.plus(clickEvent: ClickEvent?): Builder =
	clickEvent(clickEvent)

/**
 * This operator function, which will be replaced with an *unaryPlus* function in the future,
 * allows you to set the color of this [Builder] with the provided [color].
 * @param color The [TextColor] instance, which will be applied to this [Builder]
 * @return The current [Builder] instance
 * @see Builder.color
 * @author Fruxz
 * @since 1.0
 */
@Unfold infix operator fun Builder.plus(color: TextColor?): Builder =
	color(color)

/**
 * This operator function, which will be replaced with an *unaryPlus* function in the future,
 * allows you to set the style of this [Builder] with the provided [style].
 * @param style The [Style] instance, which will be applied to this [Builder]
 * @return The current [Builder] instance
 * @see Builder.style
 * @author Fruxz
 * @since 1.0
 */
@Unfold infix operator fun Builder.plus(style: Style): Builder =
	style(style)

/**
 * This function converts the [content] to an [TextComponent] using the [String.asStyledComponent].
 * MiniMessage is used to give the ability to apply colors, styles and more using only text.
 * Then, the [builder] process is applied, to modify the components state, which is performed
 * with the [String.asStyledComponent] function.
 * @param content the content, which will be converted to a component via MiniMessage
 * @param builder the process, to modify the component
 * @return the modified component as an [TextComponent]
 * @author Fruxz
 * @since 1.0
 */
@Unfold
fun text(content: String, builder: Builder.() -> Unit = { }) = content.asStyledComponent(builder)

/**
 * This function uses the [component] to apply it to an new [TextComponent.Builder]
 * and then applies the [builder] process to modify the components state.
 * @param component the base, which will be applied to the empty text component
 * @param builder the process, to modify the component
 * @return the modified component as an [TextComponent]
 * @author Fruxz
 * @since 1.0
 */
@Unfold
fun text(component: ComponentLike, builder: Builder.() -> Unit = { }) =
	Component.text().append(component).apply(builder).build()

/**
 * This function uses the [componentBuilder] and applies the [builder] process
 * to modify the components state.
 * @param componentBuilder the base, which will be used to be modified
 * @param builder the process, to modify the component
 * @return the modified component as an [TextComponent]
 * @author Fruxz
 * @since 1.0
 */
@Unfold
fun text(componentBuilder: Builder, builder: Builder.() -> Unit = { }) =
	componentBuilder.apply(builder).build()

/**
 * This function uses a new [Component.empty] component and applies the [builder] process
 * to modify the components state.
 * This function utilizes the ... function
 * @param builder the process, to modify the component
 * @return the modified component as an [TextComponent]
 * @author Fruxz
 * @since 1.0
 */
@Unfold
fun text(builder: Builder.() -> Unit) = text(Component.empty(), builder)

/**
 * This function applies the result of the [process] to [this] current
 * [TextComponent], using the [TextComponent.hoverEvent] function.
 * @param process The process, which generates the [HoverEventSource]<*>
 * @return The modified [TextComponent]
 * @author Fruxz
 * @since 1.0
 */
@Unfold
fun TextComponent.hover(process: () -> HoverEventSource<*>?) = this.hoverEvent(process())

/**
 * This function applies the result of the [process] to [this] current
 * [Builder], using the [Builder.hoverEvent] function.
 * @param process The process, which generates the [HoverEventSource]<*>
 * @return The modified [Builder]
 * @author Fruxz
 * @since 1.0
 */
@Unfold
fun Builder.hover(process: () -> HoverEventSource<*>) = this.hoverEvent(process())

@DslMarker
@PublishedApi
internal annotation class Unfold