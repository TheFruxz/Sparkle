package de.fruxz.sparkle.structure

import de.fruxz.sparkle.tool.smart.KeyedIdentifiable
import de.fruxz.sparkle.tool.smart.Labeled

interface Hoster<A, B, C : Hoster<A, B, C>> : KeyedIdentifiable<C>, Labeled {

    fun requestStart(): A

    fun requestStop(): B

}