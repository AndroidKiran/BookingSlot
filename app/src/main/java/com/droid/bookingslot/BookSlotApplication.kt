package com.droid.bookingslot

import android.app.Activity
import android.support.multidex.MultiDexApplication
import com.droid.bookingslot.di.DaggerAppComponent
import com.droid.bookingslot.di.module.AppModule
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BookSlotApplication: MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .appModule(AppModule(this))
                .build()
                .inject(this)

        Stetho.initializeWithDefaults(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}