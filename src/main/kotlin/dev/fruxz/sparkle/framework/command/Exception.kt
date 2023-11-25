package dev.fruxz.sparkle.framework.command

import com.mojang.brigadier.exceptions.CommandSyntaxException
import dev.fruxz.stacked.extension.newline
import dev.fruxz.stacked.extension.style
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import kotlin.math.max
import kotlin.math.min

fun CommandSyntaxException.contextComponent() = text {
    val exception = this@contextComponent
    val cursor = min(input.length, exception.cursor)

    if (cursor > CommandSyntaxException.CONTEXT_AMOUNT) {
        this + text("...").colorIfAbsent(NamedTextColor.GRAY)
    }

    this + text(exception.input.substring(max(0, cursor - CommandSyntaxException.CONTEXT_AMOUNT), cursor)).color(NamedTextColor.GRAY)
    this + text(exception.input.substring(cursor)).style(NamedTextColor.RED, TextDecoration.UNDERLINED)
    this + Component.translatable("command.context.here").style(NamedTextColor.RED, TextDecoration.ITALIC)
}

fun CommandSyntaxException.displayMessageComponent() = text {
    val exception = this@displayMessageComponent

    this + (exception.componentMessage() ?: Component.translatable("command.unknown.command")).colorIfAbsent(NamedTextColor.RED)
    newline()
    this + exception.contextComponent()
}