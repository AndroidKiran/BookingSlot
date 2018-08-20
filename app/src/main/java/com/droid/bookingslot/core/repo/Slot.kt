package com.droid.bookingslot.core.repo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable
import com.droid.bookingslot.helper.TimeUtils
import com.google.gson.annotations.SerializedName

@Entity(indices = [(Index(value = ["slotId"]))])
data class Slot(
        @PrimaryKey
        @SerializedName("slot_id")
        var slotId: Long = 0,
        @SerializedName("end_time")
        var endTime: String = "",
        @SerializedName("is_booked")
        var isBooked: Boolean = false,
        @SerializedName("is_expired")
        var isExpired: Boolean = false,
        @SerializedName("start_time")
        var startTime: String = "") : BaseObservable() {

    fun getSlotPeriod(): String {
        val startDate = TimeUtils.toDate(startTime)?.let {
            TimeUtils.toTimeAmPm(it)
        }
        val endDate = TimeUtils.toDate(endTime)?.let {
            TimeUtils.toTimeAmPm(it)
        }

        if (startDate == null || endDate == null) {
            return "-"
        }

        return "$startDate - $endDate"
    }
}
