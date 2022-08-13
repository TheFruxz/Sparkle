package de.moltenKt.paper.structure

import de.moltenKt.paper.tool.smart.KeyedIdentifiable
import de.moltenKt.paper.tool.smart.Labeled

interface Hoster<A, B, C : Hoster<A, B, C>> : KeyedIdentifiable<C>, Labeled {

    fun requestStart(): A

    fun requestStop(): B

}