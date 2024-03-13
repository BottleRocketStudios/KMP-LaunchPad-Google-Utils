package com.bottlerocketstudios.launchpad.google.utils.model.place

import kotlinx.serialization.Serializable

@Serializable
data class TextResponseDto(
    val text: String?,
    val matches: List<MatchDto?>?
)
