package com.droid.bookingslot.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.droid.bookingslot.BookSlotApplication
import com.droid.bookingslot.di.qualifier.AppContext
import com.droid.bookingslot.di.scope.PerApplication
import com.droid.bookingslot.room.AppDatabase
import dagger.Module
import dagger.Provides

@Module(includes = [(ActivityBuilderModule::class), (NetworkModule::class)])
class AppModule constructor(val application: BookSlotApplication) {

    @Provides
    @PerApplication
    fun provideApplication(): BookSlotApplication = application

    @Provides
    @PerApplication
    @AppContext
    fun provideContext(): Context = application.applicationContext

    @Provides
    @PerApplication
    fun provideAppDb() =
            Room.databaseBuilder(application, AppDatabase::class.java, AppDatabase.DB_NAME)
                    .build()


}