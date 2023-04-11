package dev.fruxz.sparkle.framework.command.sparkle

import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeRed
import de.fruxz.stacked.extension.newline
import de.fruxz.stacked.extension.style
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import dev.fruxz.sparkle.framework.command.bukkit.TabCommandExecutor
import dev.fruxz.sparkle.framework.command.context.CommandExecutionContext
import dev.fruxz.sparkle.framework.coroutine.task.doAsync
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class Command : CommandBranch(branchDepth = -1), TabCommandExecutor {
    private var isConfigured = false

    abstract fun configure()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        runConfiguration()

        doAsync {

            try {

                val new = this.trace(CommandExecutionContext(sender, command, label, args.toList()))

                if (new is SuccessfulCommandBranchTrace) {
                    new.hit.destination.execution?.invoke(new.hit.context)
                } else {
                    val failDepth =
                        new.result[SuccessfulCommandBranchTrace.TraceResult.MATCH]?.maxOf { it.branchDepth + 1 } ?: 0
                    val successfulPart =
                        ("/${label} " + new.processedInput.subList(0, failDepth).joinToString(" ")).let {
                            if (it.length > 15) {
                                "..." + it.substring(it.length - 15)
                            } else {
                                it
                            }
                        }
                    val failedPart = when {
                        (successfulPart != "/${label} ") -> new.processedInput.subList(
                            failDepth,
                            new.processedInput.size
                        ).joinToString(" ").let {
                            if (it.length > 25) {
                                it.substring(0, 25) + "..."
                            } else {
                                it
                            }
                        }

                        else -> new.processedInput.joinToString(" ").let {
                            if (it.length > 25) {
                                "..." + it.takeLast(25)
                            } else {
                                it
                            }
                        }
                    }

                    text {
                        this + text("<red>Syntax Error: ")
                        newline()
                        this + Component.text(successfulPart).dyeGray()
                        this + Component.text(failedPart.let {
                            when {
                                !successfulPart.endsWith(" ") && !it.startsWith(" ") -> " $it"
                                else -> it
                            }
                        }).style(NamedTextColor.RED, TextDecoration.UNDERLINED)
                        this + Component.translatable("command.context.here").style(NamedTextColor.RED, TextDecoration.ITALIC)
                    }.let { sender.sendMessage(it) }

                }

            } catch (e: Throwable) {
                e.printStackTrace(System.err)
                sender.sendMessage(Component.translatable("command.failed").dyeRed()) // TODO better messages
            }

        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        runConfiguration()

        return generateTabCompletion(CommandExecutionContext(sender, command, label, args.toList()))
    }

    private fun runConfiguration() {
        if (!isConfigured) {
            this.configure()
            this.lock()
            isConfigured = true
        }
    }

}