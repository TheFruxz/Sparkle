package dev.fruxz.sparkle.framework.network

import dev.fruxz.ascend.json.globalJson
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

val sparkleHttpClient by lazy {
    HttpClient(CIO) {
        // TODO enable http cache option
        install(ContentNegotiation) {
            json(globalJson)
        }
    }
}