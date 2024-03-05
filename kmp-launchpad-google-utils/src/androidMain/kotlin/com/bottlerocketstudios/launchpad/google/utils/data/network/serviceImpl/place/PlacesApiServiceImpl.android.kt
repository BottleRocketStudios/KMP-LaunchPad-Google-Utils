package com.bottlerocketstudios.launchpad.google.utils.data.network.serviceImpl.place

import android.content.Context
import com.bottlerocketstudios.launchpad.google.utils.data.model.place.LatLngDto
import com.bottlerocketstudios.launchpad.google.utils.data.model.place.PlaceAutocompleteResultModel
import com.bottlerocketstudios.launchpad.google.utils.data.network.MAPS_API_KEY
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

    private var placesClient: PlacesClient

    init {
        Places.initialize(context, MAPS_API_KEY)
        placesClient = Places.createClient(context)
    }

    /**
     * Finds autocomplete predictions for the given text input.
     *
     * Documentation - https://developers.google.com/maps/documentation/places/android-sdk/autocomplete
     *
     * @param textInput The text input to search for.
     * @return A Flow that emits the list of autocomplete results.
     */
    override suspend fun placesAutocomplete(textInput: String): Flow<List<PlaceAutocompleteResultModel>> {
        // Create a FindAutocompletePredictionsRequest object.
        val request =
            FindAutocompletePredictionsRequest
                .builder()
                .setQuery(textInput)
                .build()

        // Create a Flow that emits the list of autocomplete results
        return flow {
            // Call the findAutocompletePredictions() method
            val response =
                placesClient.findAutocompletePredictions(request).await()

            // Emit the list of autocomplete results
            emit(
                response.autocompletePredictions.map {
                    PlaceAutocompleteResultModel(
                        it.getPrimaryText(null).toString(),
                        it.getSecondaryText(null).toString(),
                        it.placeId
                    )
                }
            )
        }
    }

    /**
     * Fetches the location of a place by its ID.
     *
     * Documentation - https://developers.google.com/maps/documentation/places/android-sdk/place-details#get-place
     *
     * @param placeId The ID of the place.
     * @return A Flow that emits the location of the place.
     */
    override suspend fun getPlaceLocationByPlaceId(placeId: String): Flow<LatLngDto> {
        // Create a FetchPlaceRequest instance
        val request = FetchPlaceRequest.newInstance(placeId, PLACE_LAT_LONG_REQUEST)

        return flow {
            val response =
                placesClient.fetchPlace(request).await()

            // Emit the Places location
            emit(
                LatLngDto(
                    response.place.latLng?.latitude ?: 0.0,
                    response.place.latLng?.longitude ?: 0.0
                )
            )
        }
    }

    companion object {
        private val PLACE_LAT_LONG_REQUEST = listOf(Place.Field.LAT_LNG)
    }
}
