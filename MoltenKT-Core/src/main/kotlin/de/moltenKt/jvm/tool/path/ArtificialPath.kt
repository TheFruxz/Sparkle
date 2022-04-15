package de.moltenKt.jvm.tool.path

import java.io.File
import java.nio.file.Path

/**
 * This DataClass represents the routing class, that holds the
 * different kind of [extensions] at the same time.
 * It helps to easily manage and store them.
 * @author Fruxz
 * @since 1.0
 */
data class ArtificialPath(
    val extensions: MutableList<ArtificialPathProcessor>,
) {

    constructor(extensions: Array<ArtificialPathProcessor>) : this(extensions.toMutableList())

    /**
     * This function returns a real, or an artificial created
     * file using the [fullPath] and the transformers of
     * [extensions], using their [ArtificialPathProcessor.processFile]
     * functions, stored inside them.
     * @author Fruxz
     * @since 1.0
     */
    fun getFile(fullPath: String): File {
        val triggerWord = fullPath.split("/:/").firstOrNull { it.startsWith("//") }?.removePrefix("//")
        val extension = extensions.firstOrNull { it.triggerWord == triggerWord }

        return extension?.processFile?.invoke(fullPath.removePrefix("//$triggerWord/:/")) ?: Path.of(fullPath).toFile()

    }

}