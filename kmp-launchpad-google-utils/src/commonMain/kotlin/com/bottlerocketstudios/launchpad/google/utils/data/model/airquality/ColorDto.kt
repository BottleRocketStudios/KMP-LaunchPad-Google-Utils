package com.bottlerocketstudios.launchpad.google.utils.data.model.airquality

import com.bottlerocketstudios.launchpad.google.utils.data.model.Dto
import kotlinx.serialization.Serializable

@Serializable
data class ColorDto(
    val red: Double?,
    val green: Double?,
    val blue: Double?,
    val alpha: Double?
) : Dto
