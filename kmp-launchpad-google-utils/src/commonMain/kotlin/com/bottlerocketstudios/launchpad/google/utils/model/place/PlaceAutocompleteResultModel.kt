package com.bottlerocketstudios.launchpad.google.utils.model.place

import kotlinx.serialization.Serializable

@Serializable
data class PlaceAutocompleteResultModel(
    val primaryText: String,
    val secondaryText: String,
    val placeId: String
)
