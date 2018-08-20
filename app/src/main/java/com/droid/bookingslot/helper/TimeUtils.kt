package com.droid.bookingslot.helper

import android.annotation.SuppressLint
import android.content.Context
import com.droid.bookingslot.R
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    private val FIRST_DAY_OF_TIME: Calendar = Calendar.getInstance()
    private val LAST_DAY_OF_TIME: Calendar = Calendar.getInstance()
    val DAYS_OF_TIME: Int

    init {
        LAST_DAY_OF_TIME.add(Calendar.DAY_OF_MONTH, 5)
        DAYS_OF_TIME = ((LAST_DAY_OF_TIME.timeInMillis - FIRST_DAY_OF_TIME.timeInMillis) / (24 * 60 * 60 * 1000)).toInt()
    }

    fun getPositionForDay(day: Calendar?): Int {
        return if (day != null) {
            ((day.timeInMillis - FIRST_DAY_OF_TIME.timeInMillis) / 86400000  //(24 * 60 * 60 * 1000)
                    ).toInt()
        } else 0
    }

    @Throws(IllegalArgumentException::class)
    fun getDayForPosition(position: Int): Calendar {
        if (position < 0) {
            throw IllegalArgumentException("position cannot be negative")
        }
        val cal = Calendar.getInstance()
        cal.timeInMillis = FIRST_DAY_OF_TIME.timeInMillis
        cal.add(Calendar.DAY_OF_YEAR, position)
        return cal
    }


    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(context: Context?, date: Long): String {
        val defaultPattern = "yyyy-MM-dd"

        var pattern: String? = null
        if (context != null) {
            pattern = context.getString(R.string.date_format)
        }
        if (pattern == null) {
            pattern = defaultPattern
        }

        val simpleDateFormat = try {
            SimpleDateFormat(pattern)
        } catch (e: IllegalArgumentException) {
            SimpleDateFormat(defaultPattern)
        }

        return simpleDateFormat.format(Date(date))
    }


    @SuppressLint("SimpleDateFormat")
    fun toDate(timeStamp: String): Date? {
        try {
            val defaultPattern = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return defaultPattern.parse(timeStamp)
        } catch (e: Exception) {
        }
        return null
    }

    @SuppressLint("SimpleDateFormat")
    fun toTimeAmPm(timeStamp: Date): String {
        try {
            val defaultPattern = SimpleDateFormat("hh:mm a")
            return defaultPattern.format(timeStamp)
        } catch (e: Exception) {
        }
        return ""
    }


    @SuppressLint("SimpleDateFormat")
    fun toFullTime(timeStamp: Date): String {
        try {
            val defaultPattern = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return defaultPattern.format(timeStamp)
        } catch (e: Exception) {
        }
        return ""
    }


}