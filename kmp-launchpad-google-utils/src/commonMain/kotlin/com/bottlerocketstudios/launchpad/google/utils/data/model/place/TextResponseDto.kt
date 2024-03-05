package com.bottlerocketstudios.launchpad.google.utils.data.model.place

import kotlinx.serialization.Serializable

@Serializable
data class TextResponseDto(
    val text: String?,
    val matches: List<MatchDto?>?
)
