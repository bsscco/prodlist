package com.example.prodlist.thirdparty

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.prodlist.thirdparty.crash.CrashlyticsInitializer
import com.example.prodlist.thirdparty.log.FirebaseAnalyticsInitializer
import javax.inject.Inject

internal class DefaultThirdPartyInitializer @Inject constructor(
    private val firebaseAnalyticsInitializer: FirebaseAnalyticsInitializer,
    private val crashlyticsInitializer: CrashlyticsInitializer,
) : ThirdPartyInitializer, DefaultLifecycleObserver {

    override fun onApplicationCreate() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        firebaseAnalyticsInitializer.initSdk()
        crashlyticsInitializer.initSdk()
    }
}