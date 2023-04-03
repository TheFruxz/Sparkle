package dev.fruxz.sparkle.framework.command.context

import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch
import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch.BranchConfiguration
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

/**
 * @param branchParameters The parameters that are passed to the branch, by default only one string contained, due to only one input per branch.
 * BUT if you use the [BranchConfiguration.openEnd] then you can pass multiple parameters to the branch.
 */
data class BranchExecutionContext(
    override val executor: CommandSender,
    override val command: Command,
    override val label: String,
    override val parameters: List<String>,
    val branch: CommandBranch,
    val branchParameters: List<String>,
) : CommandContext