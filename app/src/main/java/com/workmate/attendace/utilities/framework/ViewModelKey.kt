package com.workmate.attendace.utilities.framework

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val viewModelClass: KClass<out ViewModel>)
