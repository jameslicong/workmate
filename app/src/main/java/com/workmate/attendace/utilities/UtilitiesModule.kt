package com.workmate.attendace.utilities

import com.workmate.attendace.utilities.rx.RxModule
import dagger.Module

@Module(
    includes = [
        RxModule::class
    ]
)
class UtilitiesModule