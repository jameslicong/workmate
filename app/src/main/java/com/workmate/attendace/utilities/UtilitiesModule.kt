package com.workmate.attendace.utilities

import com.workmate.attendace.utilities.retrofit.RetrofitModule
import com.workmate.attendace.utilities.rx.RxModule
import dagger.Module

@Module(
    includes = [
        RetrofitModule::class,
        RxModule::class
    ]
)
class UtilitiesModule