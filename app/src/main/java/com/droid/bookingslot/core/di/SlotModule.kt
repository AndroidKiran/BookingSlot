package com.droid.bookingslot.core.di

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.droid.bookingslot.core.ui.SlotAdapter
import com.droid.bookingslot.core.ui.SlotFragment
import com.droid.bookingslot.di.qualifier.ActivityContext
import com.droid.bookingslot.di.scope.PerActivity
import com.droid.bookingslot.di.scope.PerFragment
import com.droid.bookingslot.network.ISlotApi
import com.droid.bookingslot.room.AppDatabase
import com.droid.bookingslot.room.ISlotDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class SlotModule {

    @Provides
    @PerFragment
    fun provideFragmentContext(slotFragment: SlotFragment): Context? = slotFragment.context

    @Provides
    @PerFragment
    fun provideLinearLayoutManager(context: Context?) = LinearLayoutManager(context)

    @Provides
    @PerFragment
    fun provideSlotAdapter() = SlotAdapter()

}