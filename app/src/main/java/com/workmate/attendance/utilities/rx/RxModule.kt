package com.workmate.attendance.utilities.rx

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RxModule {

    @Provides
    @Singleton
    internal fun provideRxSchedulerUtils(): RxSchedulerUtils {
        return DefaultRxSchedulerUtils()
    }
}