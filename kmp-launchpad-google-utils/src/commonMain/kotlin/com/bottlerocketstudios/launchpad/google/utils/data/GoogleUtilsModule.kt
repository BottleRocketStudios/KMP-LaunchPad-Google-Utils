package com.bottlerocketstudios.launchpad.google.utils.data

import com.bottlerocketstudios.launchpad.google.utils.data.network.service.airquality.AirQualityApiService
import com.bottlerocketstudios.launchpad.google.utils.data.network.service.apiKey.ApiKeyService
import com.bottlerocketstudios.launchpad.google.utils.data.network.service.places.PlacesApiService
import com.bottlerocketstudios.launchpad.google.utils.data.network.serviceImpl.airquality.AirQualityApiServiceImpl
import com.bottlerocketstudios.launchpad.google.utils.data.network.serviceImpl.apiKey.ApiKeyServiceImpl
import com.bottlerocketstudios.launchpad.google.utils.data.network.serviceImpl.place.PlacesApiServiceImpl
import org.koin.dsl.module

val GoogleUtilsModule =
    module {
        single<PlacesApiService> { PlacesApiServiceImpl() }
        single<AirQualityApiService> { AirQualityApiServiceImpl() }
        single<ApiKeyService> { ApiKeyServiceImpl() }
    }
