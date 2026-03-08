package com.aarondeveloper.supredatard.di

import com.aarondeveloper.supredatard.data.remote.api.SupreDataRDApi
import io.ktor.client.*
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val BASE_URL_API1 = "http://api.supredatard.x10.mx"

fun provideKtorClient(): HttpClient {
    return HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
        install(HttpRedirect) {
            checkHttpMethod = false
            allowHttpsDowngrade = true
        }
    }
}


fun provideSupreDataRDApi(client: HttpClient): SupreDataRDApi {
    return SupreDataRDApi(client, BASE_URL_API1)
}