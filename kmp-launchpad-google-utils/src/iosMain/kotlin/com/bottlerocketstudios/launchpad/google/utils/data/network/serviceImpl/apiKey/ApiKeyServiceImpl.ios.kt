package com.bottlerocketstudios.launchpad.google.utils.data.network.serviceImpl.apiKey

import com.bottlerocketstudios.launchpad.google.utils.data.network.service.apiKey.ApiKeyService
import com.bottlerocketstudios.launchpad.utils.sharedPrefs.StringPreferenceDelegate
import com.liftric.kvault.KVault
import org.koin.core.component.KoinComponent

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ApiKeyServiceImpl : ApiKeyService, KoinComponent {

    private val store = KVault(API_ENCRYPTED_KVAULT_FILENAME)

    override var apiKey: String by StringPreferenceDelegate(store)

    companion object {
        private const val API_ENCRYPTED_KVAULT_FILENAME = "api_encrypted_kvault"
    }
}
