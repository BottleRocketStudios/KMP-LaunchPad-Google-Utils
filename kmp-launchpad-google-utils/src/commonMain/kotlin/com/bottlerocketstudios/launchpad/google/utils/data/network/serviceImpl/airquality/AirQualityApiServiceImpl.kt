package com.bottlerocketstudios.launchpad.google.utils.data.network.serviceImpl.airquality

import com.bottlerocketstudios.launchpad.google.utils.data.model.airquality.AirQualityExceptions
import com.bottlerocketstudios.launchpad.google.utils.data.model.airquality.CurrentAqiConditionsRequestDto
import com.bottlerocketstudios.launchpad.google.utils.data.model.airquality.CurrentConditionsResponseDto
import com.bottlerocketstudios.launchpad.google.utils.data.model.place.LatLngDto
import com.bottlerocketstudios.launchpad.google.utils.data.network.HttpRoutes
import com.bottlerocketstudios.launchpad.google.utils.data.network.ktorClient
import com.bottlerocketstudios.launchpad.google.utils.data.network.service.airquality.AirQualityApiService
import com.bottlerocketstudios.launchpad.google.utils.data.network.service.apiKey.ApiKeyService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AirQualityApiServiceImpl : AirQualityApiService, KoinComponent {

    // DI
    private val apiKeyService: ApiKeyService by inject()
    private val client: HttpClient = ktorClient()

    /**
     * Sets the API key.
     *
     * @param apiKey The API key to set.
     */
    override fun setApiKey(apiKey: String) {
        apiKeyService.apiKey = apiKey
    }

    /**
     * Fetches the current conditions for a given latitude and longitude.
     *
     * Documentation - https://developers.google.com/maps/documentation/air-quality/current-conditions#multiple_parameters_request
     *
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @return A CurrentConditionsResponseDto object containing the current conditions.
     * @throws AirQualityExceptions If there is an error fetching the current conditions.
     */
    override suspend fun getCurrentConditions(latitude: Double, longitude: Double): CurrentConditionsResponseDto {
        // Check if the API key is empty.
        if (apiKeyService.apiKey.isEmpty()) {
            // Throw an exception if the API key is missing.
            throw AirQualityExceptions.MissingApiKey
        }

        try {
            // Make a POST request to the "airQualityCurrentConditions" endpoint.
            return client.post(
                urlString = HttpRoutes.AIR_QUALITY_CURRENT_CONDITIONS_URL
            ) {
                url {
                    parameters.append("key", apiKeyService.apiKey)
                }
                contentType(ContentType.Application.Json)

                // Set the body of the request to a CurrentConditionsRequestDto object,
                // which contains the latitude and longitude values.
                setBody(
                    CurrentAqiConditionsRequestDto(
                        location = LatLngDto(latitude, longitude)
                    )
                )
            }.body()
        } catch (e: Exception) {
            // If there is an error fetching the current conditions, throw an exception.
            throw AirQualityExceptions.FailedToFetchData(e.message)
        }
    }
}
