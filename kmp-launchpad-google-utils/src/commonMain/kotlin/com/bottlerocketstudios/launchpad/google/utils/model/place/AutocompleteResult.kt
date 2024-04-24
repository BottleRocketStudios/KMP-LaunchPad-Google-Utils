package com.bottlerocketstudios.launchpad.google.utils.model.place

import kotlinx.serialization.Serializable

@Serializable
data class AutocompleteResult(
    val placeId: String,
    val primaryText: String,
    val secondaryText: String
)
