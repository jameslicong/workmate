package com.workmate.attendace.utilities.framework

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    lateinit var subscription: Disposable

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        subscription.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.addAll(disposable)
    }
}