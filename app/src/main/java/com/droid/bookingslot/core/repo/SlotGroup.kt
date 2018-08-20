package com.droid.bookingslot.core.repo

import android.databinding.BaseObservable

class SlotGroup(var groupId: Long) : BaseObservable() {

    var slotList: List<Slot> = emptyList()

    fun getTitle(): String  =
            when (groupId) {
                GroupId.GROUP4.id -> {
                    "Night"
                }

                GroupId.GROUP3.id -> {
                    "Evening"
                }

                GroupId.GROUP2.id -> {
                    "Afternoon"
                }

                else -> {
                    "Morning"
                }
            }

    fun getSlotCount() = slotList.size

    enum class GroupId(val id: Long) {
        GROUP1(1L),
        GROUP2(2L),
        GROUP3(3L),
        GROUP4(4L)
    }

}