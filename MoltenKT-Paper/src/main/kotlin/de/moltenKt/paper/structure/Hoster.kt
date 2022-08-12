package de.moltenKt.paper.structure

import de.moltenKt.paper.tool.smart.KeyedIdentifiable

interface Hoster<A, B, C : Hoster<A, B, C>> : KeyedIdentifiable<C> {

    fun requestStart(): A

    fun requestStop(): B

}