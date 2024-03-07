package com.bottlerocketstudios.launchpad.google.utils.model.place

import kotlinx.serialization.Serializable

@Serializable
data class SuggestionDto(
    val placePrediction: PlacePredictionDto?
)
