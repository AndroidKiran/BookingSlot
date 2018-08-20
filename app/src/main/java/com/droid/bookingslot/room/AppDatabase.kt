package com.droid.bookingslot.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.droid.bookingslot.core.repo.Slot
import com.droid.bookingslot.room.AppDatabase.Companion.DB_VERSION

@Database(entities = [(Slot::class)], version = DB_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "slot.db"
        const val DB_VERSION = 1
    }

    abstract fun slotDao(): ISlotDao

}
