package dev.fruxz.sparkle.framework.modularity

import dev.fruxz.sparkle.framework.util.Logging

interface Hoster : Logging {

    suspend fun start()

    suspend fun stop()

    suspend fun restart()

}