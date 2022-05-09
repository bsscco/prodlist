package com.example.prodlist.app

import android.app.Application
import com.example.prodlist.thirdparty.ThirdPartyInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ProdListApplication : Application() {

    @Inject
    lateinit var thirdPartyInitializer: ThirdPartyInitializer

    override fun onCreate() {
        super.onCreate()
        thirdPartyInitializer.onApplicationCreate()
    }
}