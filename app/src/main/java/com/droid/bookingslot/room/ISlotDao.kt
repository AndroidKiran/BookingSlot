package com.droid.bookingslot.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.droid.bookingslot.core.repo.Slot
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

@Dao
interface ISlotDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(slot: Slot)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(slots: List<Slot>)

    @Query("SELECT * FROM slot WHERE startTime BETWEEN :from AND :to")
    fun getSlots(from: Date, to: Date): Flowable<List<Slot>>

}