package com.droid.bookingslot.di

import android.app.Application
import com.droid.bookingslot.BookSlotApplication
import com.droid.bookingslot.di.module.AppModule
import com.droid.bookingslot.di.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = [(AndroidSupportInjectionModule::class), (AppModule::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }

    fun inject(application: BookSlotApplication)

}