package com.example.prodlist.thirdparty.di

import com.example.prodlist.ktutil.log.CrashReportHelper
import com.example.prodlist.ktutil.log.EventLogHelper
import com.example.prodlist.thirdparty.DefaultThirdPartyInitializer
import com.example.prodlist.thirdparty.ThirdPartyInitializer
import com.example.prodlist.thirdparty.crash.DefaultCrashReportHelper
import com.example.prodlist.thirdparty.log.DefaultEventLogHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class ThirdPartyModule {

    @Singleton
    @Binds
    abstract fun provideThirdPartyInitializer(helper: DefaultThirdPartyInitializer): ThirdPartyInitializer

    @Singleton
    @Binds
    abstract fun provideCrashReportHelper(helper: DefaultCrashReportHelper): CrashReportHelper

    @Singleton
    @Binds
    abstract fun provideEventLogHelper(helper: DefaultEventLogHelper): EventLogHelper
}