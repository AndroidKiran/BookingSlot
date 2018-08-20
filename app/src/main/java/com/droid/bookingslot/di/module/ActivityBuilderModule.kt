package com.droid.bookingslot.di.module

import android.arch.lifecycle.ViewModelProvider
import com.droid.bookingslot.core.ui.BookingActivity
import com.droid.bookingslot.core.di.BookingModule
import com.droid.bookingslot.core.di.BookingProviderModule
import com.droid.bookingslot.di.scope.PerActivity
import com.droid.bookingslot.mvvm.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @PerActivity
    @ContributesAndroidInjector(modules = [(BookingModule::class), (BookingProviderModule::class)])
    abstract fun bindBookingActivity(): BookingActivity

}
