package dev.fruxz.sparkle.framework.command.sparkle

import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.newline
import de.fruxz.stacked.extension.style
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import dev.fruxz.sparkle.framework.command.bukkit.TabCommandExecutor
import dev.fruxz.sparkle.framework.command.context.CommandExecutionContext
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

        val new = this.trace(CommandExecutionContext(sender, command, label, args.toList()))

        println("result------------------")
        println(new.result.toList().joinToString {
            it.first.name + ": \n " + it.second.joinToString { "\t'" + it.generateBranchDisplay() + "'\n" }
        })
        println("result------------------")

        if (new is SuccessfulCommandBranchTrace) {
            new.hit.destination.execution?.invoke(new.hit.context) ?: println("no execution")
        } else {
            val failDepth = new.result[SuccessfulCommandBranchTrace.TraceResult.MATCH]?.maxOf { it.branchDepth + 1 } ?: 0
            val successfulPart = ("/${label} " + new.processedInput.subList(0, failDepth).joinToString(" ")).let {
                if (it.length > 15) {
                    "..." + it.substring(it.length - 15)
                } else {
                    it
                }
            }
            val failedPart = new.processedInput.subList(failDepth, new.processedInput.size).joinToString(" ").let {
                if (it.length > 25) {
                    it.substring(0, 25) + "..."
                } else {
                    it
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
                }).style(NamedTextColor.GRAY, TextDecoration.UNDERLINED)
                this + text("<red><i><--[HERE]") // TODO localization component here!
            }.let { sender.sendMessage(it) }

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

// TODO out of order due to new trace        return generateTabCompletion(CommandExecutionContext(sender, command, label, args.toList()))
        return listOf("input")
    }

    private fun runConfiguration() {
        if (!isConfigured) {
            this.configure()
            this.lock()
            isConfigured = true
        }
    }

}