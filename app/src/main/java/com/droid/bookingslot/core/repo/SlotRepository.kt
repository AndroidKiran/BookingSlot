package com.droid.bookingslot.core.repo

import android.support.v4.util.ArrayMap
import com.droid.bookingslot.network.ISlotApi
import com.droid.bookingslot.room.ISlotDao
import com.droid.bookingslot.rx.SchedulerProvider
import com.droid.bookingslot.rx.getFlowableAsync
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class SlotRepository @Inject constructor(private val slotApi: ISlotApi, private val slotDao: ISlotDao, private val schedulerProvider: SchedulerProvider) {

    fun getSlotFromNetwork(): Flowable<List<Slot>> {

        val data = ArrayMap<String, String>().apply {
            put("expert_username", "roshini@healthifyme.com")
            put("vc", "49")
            put("username", "jasper141@example.com")
            put("api_key", "e353bade4f4bcff96946b76fb75c8f1bedb656bb")
        }
        return slotApi.getSlots(options = data)
    }


    fun insertSlotList(slotList: List<Slot>) = slotDao.insertAll(slotList)

    fun getSlots(from: Date, to: Date): Flowable<List<Slot>> = slotDao.getSlots(from, to)

    fun getSchedulerProvider() = schedulerProvider
}