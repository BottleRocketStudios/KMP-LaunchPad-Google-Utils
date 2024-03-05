package com.bottlerocketstudios.launchpad.google.utils.data.model.place

import kotlinx.serialization.Serializable

@Serializable
data class StructuredFormatDto(
    val mainText: TextResponseDto?,
    val secondaryText: TextResponseDto?
)
