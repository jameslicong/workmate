package com.workmate.attendance.utilities

import com.workmate.attendance.utilities.framework.FrameworkModule
import com.workmate.attendance.utilities.preference.SharedPrefUtilsModule
import com.workmate.attendance.utilities.retrofit.RetrofitModule
import com.workmate.attendance.utilities.rx.RxModule
import dagger.Module

@Module(
    includes = [
        FrameworkModule::class,
        RetrofitModule::class,
        RxModule::class,
        SharedPrefUtilsModule::class
    ]
)
class UtilitiesModule