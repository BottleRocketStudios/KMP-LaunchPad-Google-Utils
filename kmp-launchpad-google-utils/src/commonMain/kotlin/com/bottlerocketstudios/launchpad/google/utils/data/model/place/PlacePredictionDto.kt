package com.bottlerocketstudios.launchpad.google.utils.data.model.place

import com.bottlerocketstudios.launchpad.google.utils.data.model.Dto
import kotlinx.serialization.Serializable

@Serializable
data class PlacePredictionDto(
    val place: String?,
    val placeId: String?,
    val text: TextResponseDto?,
    val structuredFormat: StructuredFormatDto?,
    val types: List<String?>
) : Dto
