package com.bottlerocketstudios.launchpad.google.utils.data.model.place

import com.bottlerocketstudios.launchpad.google.utils.data.model.Dto
import kotlinx.serialization.Serializable

@Serializable
data class PlaceLocationResponseDto(
    val id: String,
    val location: LatLngDto,
    val displayName: DisplayNameDto
) : Dto
