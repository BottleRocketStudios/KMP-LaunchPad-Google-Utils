package com.bottlerocketstudios.launchpad.google.utils.data.network.serviceImpl.place

import com.bottlerocketstudios.launchpad.google.utils.data.model.place.LatLngDto
import com.bottlerocketstudios.launchpad.google.utils.data.model.place.PlaceAutocompleteResultModel
import com.bottlerocketstudios.launchpad.google.utils.data.model.place.PlaceLocationResponseDto
import com.bottlerocketstudios.launchpad.google.utils.data.model.place.PlacePredictionResponseDto
import com.bottlerocketstudios.launchpad.google.utils.data.model.place.PlacesAutocompleteRequestDto
import com.bottlerocketstudios.launchpad.google.utils.data.network.HttpRoutes
import com.bottlerocketstudios.launchpad.google.utils.data.network.MAPS_API_KEY
import com.bottlerocketstudios.launchpad.google.utils.data.network.ktorClient
import com.bottlerocketstudios.launchpad.google.utils.data.network.service.places.PlacesApiService
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

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlacesApiServiceImpl : PlacesApiService {

    private val client: HttpClient = ktorClient()

    /**
     * Fetches autocomplete suggestions from the Google Places API.
     *
     * Documentation: https://developers.google.com/maps/documentation/places/web-service/place-autocomplete
     *
     * @param textInput The user's input text.
     * @return A flow of lists of [PlaceAutocompleteResultModel] objects.
     */
    override suspend fun placesAutocomplete(textInput: String): Flow<List<PlaceAutocompleteResultModel>> {
        try {
            // Make a POST request to the Google Places API autocomplete endpoint.
            val response: PlacePredictionResponseDto = client.post(
                HttpRoutes.placesAutocompleteUrl
            ) {
                contentType(ContentType.Application.Json)
                header("X-Goog-Api-Key", MAPS_API_KEY)
                setBody(
                    PlacesAutocompleteRequestDto(
                        input = textInput
                    )
                )
            }.body()

            // Convert the suggestions in the response to a list of PlaceAutocompleteResultModel objects.
            return flowOf(
                response.suggestions.map { suggestion ->
                    PlaceAutocompleteResultModel(
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
    override suspend fun getPlaceLocationByPlaceId(placeId: String): Flow<LatLngDto> {
        try {
            // Make a GET request to the Places API to get the details of the place with the specified ID.
            val response: PlaceLocationResponseDto = client.get(
                HttpRoutes.placeDetailsUrl(placeId)
            ) {
                contentType(ContentType.Application.Json)
                header("X-Goog-Api-Key", MAPS_API_KEY)
                header("X-Goog-FieldMask", "id,displayName,location")
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
}
