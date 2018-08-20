package com.droid.bookingslot.core.di

import android.arch.lifecycle.ViewModel
import com.droid.bookingslot.core.presenter.BookingViewModel
import com.droid.bookingslot.core.ui.SlotFragment
import com.droid.bookingslot.di.qualifier.ViewModelKey
import com.droid.bookingslot.di.scope.PerFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class BookingProviderModule {

    @Binds
    @IntoMap
    @ViewModelKey(BookingViewModel::class)
    abstract fun bindBookingViewModel(viewModel: BookingViewModel): ViewModel

    @PerFragment
    @ContributesAndroidInjector(modules = [(SlotModule::class)])
    abstract fun bindSlotFragment(): SlotFragment
}