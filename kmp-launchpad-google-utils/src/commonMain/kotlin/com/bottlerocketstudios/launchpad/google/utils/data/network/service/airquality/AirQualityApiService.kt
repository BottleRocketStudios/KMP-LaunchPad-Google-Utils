package com.bottlerocketstudios.launchpad.google.utils.data.network.service.airquality

import com.bottlerocketstudios.launchpad.google.utils.data.model.airquality.CurrentConditionsResponseDto

interface AirQualityApiService {
    suspend fun getCurrentConditions(latitude: Double, longitude: Double): CurrentConditionsResponseDto
}
