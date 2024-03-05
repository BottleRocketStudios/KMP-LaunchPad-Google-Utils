package com.bottlerocketstudios.launchpad.google.utils.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
fun ktorClient(): HttpClient =
    HttpClient {
        // Throw an exception if the response status code is not in the 200-299 range.
        expectSuccess = true

        install(Logging) {
            level = LogLevel.ALL
            logger =
                object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                    prettyPrint = true
                    encodeDefaults = true
                    isLenient = true
                }
            )
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
            }
        }
    }
