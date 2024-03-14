package com.bottlerocketstudios.launchpad.google.utils.network.service.airquality

import com.bottlerocketstudios.launchpad.google.utils.model.airquality.AirQualityExceptions
import com.bottlerocketstudios.launchpad.google.utils.model.airquality.CurrentAqiConditionsRequestDto
import com.bottlerocketstudios.launchpad.google.utils.model.airquality.CurrentConditionsResponseDto
import com.bottlerocketstudios.launchpad.google.utils.model.place.LatLngDto
import com.bottlerocketstudios.launchpad.google.utils.network.ktor.HttpRoutes
import com.bottlerocketstudios.launchpad.google.utils.network.ktor.ktorClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * A service for interacting with the Air Quality API.
 *
 * @property apiKey The API key to use for authentication.
 */
class AirQualityApiService(
    private val apiKey: String
) {

    private val client: HttpClient = ktorClient()

    /**
     * Fetches the current conditions for a given latitude and longitude.
     *
     * Documentation - https://developers.google.com/maps/documentation/air-quality/current-conditions#multiple_parameters_request
     *
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @return A CurrentConditionsResponseDto object containing the current air quality index conditions.
     * @throws AirQualityExceptions If there is an error fetching the current conditions.
     */
    suspend fun getCurrentAqiConditions(
        latitude: Double,
        longitude: Double
    ): CurrentConditionsResponseDto {
        // Check if the API key is empty.
        if (apiKey.isEmpty()) {
            // Throw an exception if the API key is missing.
            throw AirQualityExceptions.MissingApiKey
        }

        try {
            // Make a POST request to the "airQualityCurrentConditions" endpoint.
            return client.post(
                urlString = HttpRoutes.AIR_QUALITY_CURRENT_CONDITIONS_URL
            ) {
                url {
                    parameters.append("key", apiKey)
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
