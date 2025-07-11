package dev.androidpoet.bitcoin.network

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class AppKtorLogger : io.ktor.client.plugins.logging.Logger {
    override fun log(message: String) {
        println("KTOR: $message")
    }
}

fun provideKtorClient(): HttpClient = HttpClient() {
    expectSuccess = true
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            prettyPrint = true
        })
    }

    install(Logging) {
        logger = AppKtorLogger()
        level = LogLevel.BODY
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 30_000
        connectTimeoutMillis = 30_000
        socketTimeoutMillis = 30_000
    }

}