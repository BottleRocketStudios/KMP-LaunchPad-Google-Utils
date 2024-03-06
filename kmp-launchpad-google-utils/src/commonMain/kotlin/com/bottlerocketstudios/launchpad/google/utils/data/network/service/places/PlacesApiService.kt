package com.bottlerocketstudios.launchpad.google.utils.data.network.service.places

import com.bottlerocketstudios.launchpad.google.utils.data.model.place.LatLngDto
import com.bottlerocketstudios.launchpad.google.utils.data.model.place.PlaceAutocompleteResultModel
import kotlinx.coroutines.flow.Flow

interface PlacesApiService {

    fun setApiKey(apiKey: String)

    suspend fun placesAutocomplete(textInput: String): Flow<List<PlaceAutocompleteResultModel>>

    suspend fun getPlaceLocationByPlaceId(placeId: String): Flow<LatLngDto>
}
