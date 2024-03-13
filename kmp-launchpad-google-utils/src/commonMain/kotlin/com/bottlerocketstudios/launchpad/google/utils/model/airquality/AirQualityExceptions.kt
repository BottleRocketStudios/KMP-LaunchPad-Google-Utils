package com.bottlerocketstudios.launchpad.google.utils.model.airquality

/**
 * Exception class for when there is an error fetching the current conditions.
 *
 * @param message The error message.
 */
sealed class AirQualityExceptions(message: String) : Exception(message) {

    data object MissingApiKey : AirQualityExceptions(
        "Please use the setApiKey() method to set the Google Maps API key."
    )

    data class FailedToFetchData(val errorString: String?) : AirQualityExceptions(
        "Failed to fetch data from the Google Maps API: $errorString"
    )
}
