package com.bottlerocketstudios.launchpad.google.utils.data.model.airquality

/**
 * Exception class for when there is an error fetching the current conditions.
 *
 * @param message The error message.
 */
class CurrentConditionsFetchException(message: String) : Exception(message)
