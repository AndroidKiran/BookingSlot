package com.droid.bookingslot.room

import android.arch.persistence.room.TypeConverter
import com.droid.bookingslot.helper.TimeUtils
import java.util.*


object Converters {
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: String?): Date? {
        return value?.let {
            TimeUtils.toDate(it)
        }
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): String? {
        return date?.let {
            TimeUtils.toFullTime(date)
        }
    }
}