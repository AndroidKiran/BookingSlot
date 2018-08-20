package com.droid.bookingslot.rx

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import com.droid.bookingslot.helper.Result
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.reactivestreams.Publisher

fun <T> Flowable<T>.getFlowableAsync(schedulerProvider: SchedulerProvider): Flowable<T> =
        this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())

fun <T> Single<T>.getSingleAsync(schedulerProvider: SchedulerProvider): Single<T> =
        this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())

fun Completable.getCompletableAsync(schedulerProvider: SchedulerProvider): Completable =
        this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())

fun <T> Publisher<T>.toLiveData() =
        LiveDataReactiveStreams.fromPublisher(this)

fun <T> LiveData<T>.toFlowable(lifecycleOwner: LifecycleOwner): Flowable<T> =
        Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, this))

fun <T> Flowable<T>.apibaseResponseToResult(): Flowable<Result<T>> =
        this.map {
            it.asResult()
        }.onErrorReturn {
            return@onErrorReturn it.asErrorResult<T>()
        }

fun <T> T.asResult(): Result<T> = Result(this, null)

fun <T> Throwable.asErrorResult(): Result<T> = Result(null, this)