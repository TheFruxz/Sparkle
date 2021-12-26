package de.jet.jvm.tool.path

import java.io.File

/**
 * The interface, to easily structure a modification to the
 * [ArtificialPath]-System. It uses a trigger word, used
 * inside the //<triggerWord>/:/ to access for e.G. special
 * areas, file-systems or even produce an artificial output,
 * that is returned as the [File] format.
 * @author Fruxz
 * @since 1.0
 */
interface ArtificialPathProcessor {

    /**
     * The word, used, to trigger the procedure of this [ArtificialPathProcessor]
     * @author Fruxz
     * @since 1.0
     */
    val triggerWord: String

    /**
     * The procedure, to return the file used by the path, provided inside the lambda.
     * **NOTE: the provided String contains the path, but WITHOUT the start '//<x>/:/'
     * @author Fruxz
     * @since 1.0
     */
    val processFile: (String) -> File

}