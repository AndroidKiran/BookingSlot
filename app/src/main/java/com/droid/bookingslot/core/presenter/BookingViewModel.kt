package com.droid.bookingslot.core.presenter

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.droid.bookingslot.core.base.BaseViewModel
import com.droid.bookingslot.core.repo.Slot
import com.droid.bookingslot.core.repo.SlotGroup
import com.droid.bookingslot.core.repo.SlotRepository
import com.droid.bookingslot.helper.Result
import com.droid.bookingslot.mvvm.switchMap
import com.droid.bookingslot.rx.apibaseResponseToResult
import com.droid.bookingslot.rx.getFlowableAsync
import com.droid.bookingslot.rx.toLiveData
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Function3
import io.reactivex.functions.Function4
import java.util.*
import javax.inject.Inject

class BookingViewModel @Inject constructor(application: Application, private val slotRepository: SlotRepository) : BaseViewModel(application) {

    var queryLiveData = MutableLiveData<Boolean>()
    var slotListLiveData: LiveData<Result<List<Slot>>> = queryLiveData.switchMap {
        when (it) {
            null,
            false -> MutableLiveData()
            else -> slotRepository.getSlotFromNetwork()
                    .insertSlots()
                    .getFlowableAsync(slotRepository.getSchedulerProvider())
                    .apibaseResponseToResult()
                    .toLiveData()
        }
    }


    private fun Flowable<List<Slot>>.insertSlots() =
            flatMapSingle { result ->
                Single.create<List<Slot>> {
                    try {
                        slotRepository.insertSlotList(result)
                        it.onSuccess(result)
                    } catch (e: Exception) {
                        Single.error<Result<List<Slot>>>(e)
                    }
                }
            }


    var slotQueryLiveData = MutableLiveData<Date>()
    var filteredSlotListLiveData: LiveData<Result<List<SlotGroup>>> = slotQueryLiveData.switchMap {
        when (it) {
            null -> MutableLiveData()
            else -> getGroups(it)
        }
    }

    private fun getGroupSlot(date: Date, startTime: Int, endTime: Int) =
            Flowable.just(date)
                    .flatMapSingle {
                        val cal1 = Calendar.getInstance().apply {
                            time = date
                            set(Calendar.HOUR_OF_DAY, startTime)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                        }

                        val cal2 = Calendar.getInstance().apply {
                            time = date
                            set(Calendar.HOUR_OF_DAY, endTime)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                        }

                        val pair = Pair<Date, Date>(cal1.time, cal2.time)
                        Single.just(pair)
                    }.flatMap {
                        slotRepository.getSlots(it.first, it.second)
                    }

    private fun getGroups(date: Date) =
            Flowable.zip(getGroupSlot(date, 1, 12),
                    getGroupSlot(date, 12, 16),
                    getGroupSlot(date, 16, 24),
                    Function3<List<Slot>, List<Slot>, List<Slot>, List<SlotGroup>> { t1, t2, t3 ->
                        val groupList = mutableListOf<SlotGroup>()
                        val group1 = SlotGroup(SlotGroup.GroupId.GROUP1.id).apply {
                            slotList = t1
                        }
                        groupList.add(group1)

                        val group2 = SlotGroup(SlotGroup.GroupId.GROUP2.id).apply {
                            slotList = t2
                        }
                        groupList.add(group2)

                        val group3 = SlotGroup(SlotGroup.GroupId.GROUP3.id).apply {
                            slotList = t3
                        }
                        groupList.add(group3)
                        return@Function3 groupList
                    }).getFlowableAsync(slotRepository.getSchedulerProvider())
                    .apibaseResponseToResult()
                    .toLiveData()


}