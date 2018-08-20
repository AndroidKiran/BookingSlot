package com.droid.bookingslot.core.di

import com.droid.bookingslot.di.scope.PerActivity
import com.droid.bookingslot.network.ISlotApi
import com.droid.bookingslot.room.AppDatabase
import com.droid.bookingslot.room.ISlotDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class BookingModule {

    @Provides
    @PerActivity
    fun provideSlotApi(retrofit: Retrofit): ISlotApi = retrofit.create(ISlotApi::class.java)

    @Provides
    @PerActivity
    fun provideSlotDao(appDatabase: AppDatabase): ISlotDao = appDatabase.slotDao()
}