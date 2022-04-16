package de.moltenKt.core.tool.path

import de.moltenKt.core.extension.getResourceByteArray
import java.io.File

/**
 * This object represents the MoltenKT provided [ArtificialPathProcessor],
 * to easily get files from the resources.
 * @author Fruxz
 * @since 1.0
 */
object ArtificialReadOnlyResourcePathProcessor : ArtificialPathProcessor {

    override val triggerWord = "readOnlyResource"

    override val processFile: (String) -> File = {
        val tempFile = File.createTempFile(it.split(".").last(), null)

        tempFile.writeBytes(getResourceByteArray(it))

        tempFile
    }

}