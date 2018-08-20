package com.droid.bookingslot.network

import android.support.v4.util.ArrayMap
import com.droid.bookingslot.core.repo.Slot
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface ISlotApi {

    @GET("/api/v2/booking/slots/all/")
    fun getSlots(@QueryMap options: ArrayMap<String, String>): Flowable<List<Slot>>

}