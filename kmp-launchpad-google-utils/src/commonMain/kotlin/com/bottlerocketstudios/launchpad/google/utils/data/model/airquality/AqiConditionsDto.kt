package com.bottlerocketstudios.launchpad.google.utils.data.model.airquality

import com.bottlerocketstudios.launchpad.google.utils.data.model.Dto
import kotlinx.serialization.Serializable

@Serializable
data class AqiConditionsDto(
    val code: String?,
    val displayName: String?,
    val aqi: Int?,
    val aqiDisplay: String?,
    val color: ColorDto?,
    val category: String?,
    val dominantPollutant: String?
) : Dto
