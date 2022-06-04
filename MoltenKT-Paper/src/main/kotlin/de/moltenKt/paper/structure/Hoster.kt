package de.moltenKt.paper.structure

interface Hoster<A, B> {

    fun requestStart(): A

    fun requestStop(): B

}