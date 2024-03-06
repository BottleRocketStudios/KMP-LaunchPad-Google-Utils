package com.bottlerocketstudios.launchpad.google.utils.data.network.serviceImpl.place

import android.content.Context
import com.bottlerocketstudios.launchpad.google.utils.data.model.place.LatLngDto
import com.bottlerocketstudios.launchpad.google.utils.data.model.place.PlaceAutocompleteResultModel
import com.bottlerocketstudios.launchpad.google.utils.data.model.place.PlacesException
import com.bottlerocketstudios.launchpad.google.utils.data.network.service.apiKey.ApiKeyService
import com.bottlerocketstudios.launchpad.google.utils.data.network.service.places.PlacesApiService
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlacesApiServiceImpl : PlacesApiService, KoinComponent {
    // DI
    private val context: Context by inject()
    private val apiKeyService: ApiKeyService by inject()

    init {
        if (apiKeyService.apiKey.isNotEmpty()) {
            initialize(context, apiKeyService.apiKey)
        } else {
            println("Please use the setApiKey() method to set the Google Maps API key.")
        }
    }

    /**
     * Sets the API key for the Places SDK.
     *
     * @param apiKey The API key to set.
     */
    override fun setApiKey(apiKey: String) {
        apiKeyService.apiKey = apiKey
        initialize(context, apiKey)
    }

    /**
     * Returns a flow of autocomplete results for the given text input.
     *
     * @param textInput The text input to search for.
     * @return A flow of [PlaceAutocompleteResultModel] objects.
     * @throws PlacesException.MissingApiKey if the API key is missing.
     */
    override suspend fun placesAutocomplete(textInput: String): Flow<List<PlaceAutocompleteResultModel>> {
        // Check if the API key is empty.
        if (apiKeyService.apiKey.isEmpty()) {
            // Throw an exception if the API key is missing.
            throw PlacesException.MissingApiKey
        }

        // Create a FindAutocompletePredictionsRequest object.
        val request =
            FindAutocompletePredictionsRequest
                .builder()
                .setQuery(textInput) // Set the query string.
                .build()

        // Create a Flow that emits the list of autocomplete results
        return flow {
            // Call the findAutocompletePredictions() method
            val response =
                placesClient?.findAutocompletePredictions(request)?.await()

            // Emit the list of autocomplete results.
            emit(
                response?.autocompletePredictions?.map {
                    // Convert the PlaceAutocompletePrediction object to a PlaceAutocompleteResultModel object.
                    PlaceAutocompleteResultModel(
                        it.getPrimaryText(null).toString(),
                        it.getSecondaryText(null).toString(),
                        it.placeId
                    )
                } ?: emptyList()
            )
        }
    }

    /**
     * Fetches the location of a place by its place ID.
     *
     * @param placeId The ID of the place.
     * @return A Flow that emits the location of the place.
     * @throws PlacesException.MissingApiKey if the API key is not available.
     * @throws PlacesException.PlaceNotFound if the place does not have a valid location.
     */
    override suspend fun getPlaceLocationByPlaceId(placeId: String): Flow<LatLngDto> {
        // Check if the API key is available
        if (apiKeyService.apiKey.isEmpty()) {
            throw PlacesException.MissingApiKey
        }

        // Create a FetchPlaceRequest instance
        val request = FetchPlaceRequest.newInstance(placeId, PLACE_LAT_LONG_REQUEST)

        return flow {
            val response =
                placesClient?.fetchPlace(request)?.await()

            // Check if the place has a valid location
            when {
                // If the place has a valid location, emit it
                response?.place?.latLng != null -> {
                    emit(
                        LatLngDto(
                            response.place.latLng?.latitude ?: 0.0,
                            response.place.latLng?.longitude ?: 0.0
                        )
                    )
                }

                // If the place does not have a valid location, throw an exception
                else -> {
                    throw PlacesException.PlaceNotFound
                }
            }
        }
    }

    companion object {
        private var placesClient: PlacesClient? = null

        fun initialize(context: Context, apiKey: String) {
            Places.initialize(context, apiKey)
            placesClient = Places.createClient(context)
        }

        private val PLACE_LAT_LONG_REQUEST = listOf(Place.Field.LAT_LNG)
    }
}
