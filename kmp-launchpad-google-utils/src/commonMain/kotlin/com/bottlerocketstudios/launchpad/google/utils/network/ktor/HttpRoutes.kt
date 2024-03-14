package com.bottlerocketstudios.launchpad.google.utils.network.ktor

object HttpRoutes {
    // Air Quality
    private const val BASE_URL_AIR_QUALITY = "https://airquality.googleapis.com/v1/currentConditions"
    const val AIR_QUALITY_CURRENT_CONDITIONS_URL = "$BASE_URL_AIR_QUALITY:lookup"

    // Places
    private const val BASE_URL_PLACES = "https://places.googleapis.com/v1/places"
    const val PLACES_AUTOCOMPLETE_URL = "$BASE_URL_PLACES:autocomplete"
    fun placeDetailsUrl(placeId: String) = "$BASE_URL_PLACES/$placeId"
}
