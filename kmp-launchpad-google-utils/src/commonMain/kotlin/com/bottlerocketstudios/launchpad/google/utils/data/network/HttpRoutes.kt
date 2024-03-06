package com.bottlerocketstudios.launchpad.google.utils.data.network

object HttpRoutes {

    private const val BASE_URL_AIR_QUALITY = "https://airquality.googleapis.com/v1/currentConditions"
    private const val BASE_URL_PLACES = "https://places.googleapis.com/v1/places"

    // Air Quality
    const val airQualityCurrentConditions = "$BASE_URL_AIR_QUALITY:lookup?key=$MAPS_API_KEY"

    // Places
    const val placesAutocompleteUrl = "$BASE_URL_PLACES:autocomplete"
    fun placeDetailsUrl(placeId: String) = "$BASE_URL_PLACES/$placeId"
}
