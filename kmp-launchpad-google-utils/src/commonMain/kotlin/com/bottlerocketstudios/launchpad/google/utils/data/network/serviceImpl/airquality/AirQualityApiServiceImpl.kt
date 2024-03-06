package com.bottlerocketstudios.launchpad.google.utils.data.network.serviceImpl.airquality

import com.bottlerocketstudios.launchpad.google.utils.data.model.airquality.CurrentAqiConditionsRequestDto
import com.bottlerocketstudios.launchpad.google.utils.data.model.airquality.CurrentConditionsFetchException
import com.bottlerocketstudios.launchpad.google.utils.data.model.airquality.CurrentConditionsResponseDto
import com.bottlerocketstudios.launchpad.google.utils.data.model.place.LatLngDto
import com.bottlerocketstudios.launchpad.google.utils.data.network.HttpRoutes
import com.bottlerocketstudios.launchpad.google.utils.data.network.ktorClient
import com.bottlerocketstudios.launchpad.google.utils.data.network.service.airquality.AirQualityApiService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AirQualityApiServiceImpl : AirQualityApiService {

    private val client: HttpClient = ktorClient()

    /**
     * Fetches the current conditions for a given latitude and longitude.
     *
     * Documentation - https://developers.google.com/maps/documentation/air-quality/current-conditions#multiple_parameters_request
     *
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @return A CurrentConditionsResponseDto object containing the current conditions.
     * @throws CurrentConditionsFetchException If there is an error fetching the current conditions.
     */
    override suspend fun getCurrentConditions(latitude: Double, longitude: Double): CurrentConditionsResponseDto {
        try {
            // Make a POST request to the "airQualityCurrentConditions" endpoint.
            return client.post(
                urlString = HttpRoutes.airQualityCurrentConditions
            ) {
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
            // If an exception occurs, throw a CurrentConditionsFetchException with a message indicating the failure.
            throw CurrentConditionsFetchException(
                "Failed to fetch current conditions: ${e.message}"
            )
        }
    }
}
