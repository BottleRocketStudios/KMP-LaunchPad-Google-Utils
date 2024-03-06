package com.bottlerocketstudios.launchpad.google.utils.data.model.place

sealed class PlacesException(message: String) : Exception(message) {
    data object MissingApiKey : PlacesException(
        "Please use the setApiKey() method to set the Google Maps API key."
    )
    data object PlaceNotFound : PlacesException(
        "Place not found with provided Latitude and Longitude"
    )
}
