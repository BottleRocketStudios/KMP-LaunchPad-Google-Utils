package com.bottlerocketstudios.launchpad.google.utils.data.model.airquality

import com.bottlerocketstudios.launchpad.google.utils.data.model.Dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the current conditions response when requesting only LOCAL_AQI
 *
 * @property dateTime The date and time of the current conditions.
 * @property regionCode The region code of the current conditions.
 * @property aqiConditions The air quality index conditions.
 */
@Serializable
data class CurrentConditionsResponseDto(
    val dateTime: String?,
    val regionCode: String?,
    @SerialName(value = "indexes")
    val aqiConditions: List<AqiConditionsDto>?
) : Dto
