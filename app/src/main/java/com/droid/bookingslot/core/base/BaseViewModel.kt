package com.droid.bookingslot.core.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel constructor(application: Application) : AndroidViewModel(application) {

    private lateinit var baseCompositeDisposable: CompositeDisposable

    fun onViewCreated() {
        baseCompositeDisposable = CompositeDisposable()
    }

    fun onDestroyView() {
        baseCompositeDisposable.dispose()
    }

    fun getCompositeDisposable(): CompositeDisposable = baseCompositeDisposable

}