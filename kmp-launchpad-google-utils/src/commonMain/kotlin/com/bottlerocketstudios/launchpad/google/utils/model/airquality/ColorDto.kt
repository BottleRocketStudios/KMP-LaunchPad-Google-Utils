package com.bottlerocketstudios.launchpad.google.utils.model.airquality

import com.bottlerocketstudios.launchpad.google.utils.model.Dto
import kotlinx.serialization.Serializable

@Serializable
data class ColorDto(
    val red: Double?,
    val green: Double?,
    val blue: Double?,
    val alpha: Double?
) : Dto
