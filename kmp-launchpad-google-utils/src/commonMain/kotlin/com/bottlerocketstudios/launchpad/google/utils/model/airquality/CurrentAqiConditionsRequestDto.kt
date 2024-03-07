package com.bottlerocketstudios.launchpad.google.utils.model.airquality

import com.bottlerocketstudios.launchpad.google.utils.model.Dto
import com.bottlerocketstudios.launchpad.google.utils.model.place.LatLngDto
import kotlinx.serialization.Serializable

/**
 * Data class representing the request body for getting current AQI conditions.
 *
 * @property universalAqi Whether to return the universal AQI or not. Defaults to true.
 * @property location The latitude and longitude of the location.
 * @property extraComputations A list of extra computations to perform. Defaults to ["LOCAL_AQI"].
 * @property languageCode The language code for the response. Defaults to "en".
 */
@Serializable
data class CurrentAqiConditionsRequestDto(
    val universalAqi: Boolean = true,
    val location: LatLngDto,
    val extraComputations: List<String> = listOf("LOCAL_AQI"),
    val languageCode: String = "en"
) : Dto
