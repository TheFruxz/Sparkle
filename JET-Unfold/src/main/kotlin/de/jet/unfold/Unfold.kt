@file:Suppress("unused") // TODO use kotlin context API to avoid 'useless' seeming object extensions

package de.jet.unfold

import io.ktor.http.*
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEventSource
import net.kyori.adventure.text.minimessage.MiniMessage
import java.io.File
import java.net.URL
import java.nio.file.Path

private fun String.transform() = Component.text().append(MiniMessage.miniMessage().deserialize(this)).build()

fun text(build: TextComponent.Builder.() -> Unit) =
	Component.text().apply(build).build()

fun text(componentContent: String) =
	Component.text().append(componentContent.transform())

fun space() = Component.space()

fun empty() = Component.empty()

fun newline() = Component.newline()

fun MoltenContext<HoverEventSource<*>>.text(componentContent: String) =
	text { text(componentContent) }

fun MoltenContext<HoverEventSource<*>>.space() =
	text { text(" ") }

fun MoltenContext<HoverEventSource<*>>.empty() =
	text { text("") }

fun MoltenContext<HoverEventSource<*>>.newline() =
	text { text("\n") }

operator fun TextComponent.Builder.plus(component: Component) =
	append(component)

fun TextComponent.Builder.text(componentContent: String) =
	this + componentContent.transform()

fun TextComponent.Builder.text(componentContent: String, modify: TextComponent.Builder.() -> Unit) =
	this.append(componentContent.transform().toBuilder().apply(modify).build())

fun TextComponent.Builder.hover(eventSource: MoltenContext<HoverEventSource<*>>.() -> HoverEventSource<*>) =
	hoverEvent(eventSource(MoltenContext.contextOf()))

fun TextComponent.Builder.click(click: MoltenContext<ClickEvent>.() -> ClickEvent) =
	clickEvent(click(MoltenContext.contextOf()))

fun MoltenContext<ClickEvent>.url(url: String) = ClickEvent.openUrl(url)

fun MoltenContext<ClickEvent>.url(url: URL) = ClickEvent.openUrl(url)

fun MoltenContext<ClickEvent>.url(url: Url) = ClickEvent.openUrl(url.toString())

fun MoltenContext<ClickEvent>.file(file: String) = ClickEvent.openFile(file)

fun MoltenContext<ClickEvent>.file(path: Path) = file("$path")

fun MoltenContext<ClickEvent>.file(file: File) = file(file.toPath())

fun MoltenContext<ClickEvent>.run(command: String) = ClickEvent.runCommand(command)

fun MoltenContext<ClickEvent>.suggest(command: String) = ClickEvent.suggestCommand(command)

fun MoltenContext<ClickEvent>.toPage(page: Int) = ClickEvent.changePage(page)

fun MoltenContext<ClickEvent>.toPage(page: String) = ClickEvent.changePage(page)

fun MoltenContext<ClickEvent>.copy(text: String) = ClickEvent.copyToClipboard(text)

fun MoltenContext<ClickEvent>.click(action: ClickEvent.Action, string: String) = ClickEvent.clickEvent(action, string)