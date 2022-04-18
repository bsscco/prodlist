package com.jeongyookgak.quickflex.thirdparty.di

import com.example.prodlist.ktutil.log.CrashReportHelper
import com.example.prodlist.ktutil.log.EventLogHelper
import com.jeongyookgak.quickflex.thirdparty.DefaultThirdPartyInitializer
import com.jeongyookgak.quickflex.thirdparty.ThirdPartyInitializer
import com.jeongyookgak.quickflex.thirdparty.crash.DefaultCrashReportHelper
import com.jeongyookgak.quickflex.thirdparty.log.DefaultEventLogHelper
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