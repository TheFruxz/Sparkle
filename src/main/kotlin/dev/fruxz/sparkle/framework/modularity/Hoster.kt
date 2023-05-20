package dev.fruxz.sparkle.framework.modularity

import dev.fruxz.sparkle.framework.util.Logging
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

interface Hoster : Logging {

    fun start(): Deferred<Boolean>

    fun stop(): Deferred<Boolean>

    fun restart(): Job

}