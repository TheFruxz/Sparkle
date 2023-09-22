package dev.fruxz.sparkle.framework.command.context

import dev.fruxz.ascend.extension.tryOrNull
import dev.fruxz.sparkle.framework.command.sparkle.BranchContent
import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch
import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch.BranchConfiguration
import dev.fruxz.sparkle.framework.marker.SparkleDSL
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.jetbrains.annotations.ApiStatus

/**
 * @param branchParameters The parameters that are passed to the branch, by default only one string contained, due to only one input per branch.
 * BUT if you use the [BranchConfiguration.openEnd] then you can pass multiple parameters to the branch.
 */
data class BranchExecutionContext(
    override val performer: CommandSender,
    override val command: Command,
    override val label: String,
    override val parameters: List<String>,
    val branch: CommandBranch,
    val branchParameters: List<String>,
) : CommandContext {

    @Deprecated("Use performer instead", ReplaceWith("performer"))
    val sender: CommandSender = performer

    val branchDepth = branch.branchDepth

    private val reversedParameters by lazy { parameters.asReversed() }

    operator fun get(index: Int = branchDepth): String = parameters[index]

    fun getOrNull(index: Int = branchDepth): String? = parameters.getOrNull(index)

    fun getReversed(reversedIndex: Int): String = reversedParameters[reversedIndex]

    fun getReversedOrNull(reversedIndex: Int): String? = reversedParameters.getOrNull(reversedIndex)

    // translated

    @ApiStatus.Internal
    @SparkleDSL
    fun <T> processContentOrNull(type: BranchContent<T>, vararg content: String): T? = tryOrNull(silent = false) { type.generateContent(this, content.toList()) }

    @ApiStatus.Internal
    @SparkleDSL
    fun <T> processContent(type: BranchContent<T>, vararg content: String): T = processContentOrNull(type, *content) ?: throw IllegalArgumentException("Could not translate content: ${content.joinToString(" ")}")

    @SparkleDSL
    fun <T> getOrNull(type: BranchContent<T>, index: Int): T? =
        processContentOrNull(type, parameters[index])

    @SparkleDSL
    operator fun <T> get(type: BranchContent<T>, index: Int): T =
        processContent(type, parameters[index])

    @SparkleDSL
    fun <T> getOrNull(type: BranchContent<T>): T? =
        processContentOrNull(type, parameters[branchDepth])

    @SparkleDSL
    operator fun <T> get(type: BranchContent<T>): T =
        processContent(type, parameters[branchDepth])

    @SparkleDSL
    fun <T> getReversedOrNull(type: BranchContent<T>, reversedIndex: Int): T? =
        processContentOrNull(type, reversedParameters[reversedIndex])

    @SparkleDSL
    fun <T> getReversed(type: BranchContent<T>, reversedIndex: Int): T =
        processContent(type, reversedParameters[reversedIndex])

}