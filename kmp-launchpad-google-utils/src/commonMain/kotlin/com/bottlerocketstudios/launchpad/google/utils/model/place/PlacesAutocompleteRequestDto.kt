package com.bottlerocketstudios.launchpad.google.utils.model.place

import com.bottlerocketstudios.launchpad.google.utils.model.Dto
import kotlinx.serialization.Serializable

@Serializable
data class PlacesAutocompleteRequestDto(
    val input: String
) : Dto
