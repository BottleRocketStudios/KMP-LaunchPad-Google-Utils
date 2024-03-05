package com.bottlerocketstudios.launchpad.google.utils.data.network

object HttpRoutes {

    private const val BASE_URL_PLACES = "https://places.googleapis.com/v1/places"

    val placesAutocompleteUrl = "$BASE_URL_PLACES:autocomplete"
    fun placeDetailsUrl(placeId: String) = "$BASE_URL_PLACES/$placeId"
}