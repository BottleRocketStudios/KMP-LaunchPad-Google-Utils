package com.bottlerocketstudios.launchpad.google.utils.network.service.places

import com.bottlerocketstudios.launchpad.google.utils.model.place.AutocompleteResult
import com.bottlerocketstudios.launchpad.google.utils.model.place.LatLngDto
import com.bottlerocketstudios.launchpad.google.utils.model.place.PlaceLocationResponseDto
import com.bottlerocketstudios.launchpad.google.utils.model.place.PlacePredictionResponseDto
import com.bottlerocketstudios.launchpad.google.utils.model.place.PlacesAutocompleteRequestDto
import com.bottlerocketstudios.launchpad.google.utils.model.place.PlacesException
import com.bottlerocketstudios.launchpad.google.utils.network.ktor.HttpRoutes
import com.bottlerocketstudios.launchpad.google.utils.network.ktor.ktorClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * A class that provides access to the Google Places API.
 *
 * @param apiKey The API key for the Google Maps API.
 */
class PlacesApiService(private val apiKey: String) {

    private val client: HttpClient = ktorClient()

    /**
     * Fetches autocomplete suggestions from the Google Places API.
     *
     * Documentation: https://developers.google.com/maps/documentation/places/web-service/place-autocomplete
     *
     * @param query The user's input text.
     * @return A flow of lists of [AutocompleteResult] objects.
     */
    suspend fun autocomplete(query: String): Flow<List<AutocompleteResult>> {
        // Check if the API key is empty.
        if (apiKey.isEmpty()) {
            // Throw an exception if the API key is missing.
            throw PlacesException.MissingApiKey
        }

        try {
            // Make a POST request to the Google Places API autocomplete endpoint.
            val response: PlacePredictionResponseDto = client.post(
                HttpRoutes.PLACES_AUTOCOMPLETE_URL
            ) {
                contentType(ContentType.Application.Json)
                header(PLACES_HEADER_API_KEY, apiKey)
                setBody(
                    PlacesAutocompleteRequestDto(
                        input = query
                    )
                )
            }.body()

            // Convert the suggestions in the response to a list of PlaceAutocompleteResultModel objects.
            return flowOf(
                response.suggestions.map { suggestion ->
                    AutocompleteResult(
                        primaryText = suggestion.placePrediction?.structuredFormat?.mainText?.text.orEmpty(),
                        secondaryText = suggestion.placePrediction?.structuredFormat?.secondaryText?.text.orEmpty(),
                        placeId = suggestion.placePrediction?.placeId.orEmpty()
                    )
                }
            )
        } catch (e: Exception) {
            // Handle any exceptions that may occur during the API call.
            // In this case, return an empty list of PlaceAutocompleteResultModel objects.
            return flowOf(emptyList())
        }
    }

    /**
     * Gets the location of a place by its ID.
     *
     * Documentation: https://developers.google.com/maps/documentation/places/web-service/place-details
     *
     * @param placeId The ID of the place.
     * @return A Flow object that emits a MapsLatLng object containing the latitude and longitude of the place.
     */
    suspend fun getPlaceLocationByPlaceId(placeId: String): Flow<LatLngDto> {
        // Check if the API key is empty.
        if (apiKey.isEmpty()) {
            // Throw an exception if the API key is missing.
            throw PlacesException.MissingApiKey
        }

        try {
            // Make a GET request to the Places API to get the details of the place with the specified ID.
            val response: PlaceLocationResponseDto = client.get(
                HttpRoutes.placeDetailsUrl(placeId)
            ) {
                contentType(ContentType.Application.Json)
                header(PLACES_HEADER_API_KEY, apiKey)
                header(PLACES_HEADER_PARAMETERS, PLACES_PARAMETERS)
            }.body()

            // Create a Flow object that emits a MapsLatLng object containing the latitude and longitude of the place.
            return flowOf(
                LatLngDto(
                    latitude = response.location.latitude,
                    longitude = response.location.longitude
                )
            )
        } catch (e: Exception) {
            // Handle the error here, such as logging or throwing an exception.
            // In this case, return test coordinates.
            return flowOf(
                LatLngDto(
                    latitude = 40.75,
                    longitude = -73.99
                )
            )
        }
    }

    companion object {
        private const val PLACES_HEADER_API_KEY = "X-Goog-Api-Key"
        private const val PLACES_HEADER_PARAMETERS = "X-Goog-FieldMask"
        private const val PLACES_PARAMETERS = "id,displayName,location"
    }
}
