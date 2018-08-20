package com.droid.bookingslot.core.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.droid.bookingslot.BR
import com.droid.bookingslot.R
import com.droid.bookingslot.core.base.BaseFragment
import com.droid.bookingslot.core.presenter.BookingViewModel
import com.droid.bookingslot.databinding.FragmentSlotBinding
import com.droid.bookingslot.helper.RecyclerViewConfiguration
import com.droid.bookingslot.helper.TimeUtils
import com.droid.bookingslot.mvvm.observe
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils
import javax.inject.Inject

class SlotFragment : BaseFragment<FragmentSlotBinding, BookingViewModel>() {

    @Inject
    lateinit var viewFactory: ViewModelProvider.Factory

    @Inject
    lateinit var slotAdapter: SlotAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var bookingViewModel: BookingViewModel

    private lateinit var fragmentSlotBinding: FragmentSlotBinding

    private var recyclerViewConfiguration = RecyclerViewConfiguration()

    private var recyclerViewExpandableItemManager: RecyclerViewExpandableItemManager? = null

    private var wrappedAdapter: RecyclerView.Adapter<*>? = null

    private var position: Int? = null

    override fun getViewModel() =
            ViewModelProviders.of(this@SlotFragment, viewFactory)
                    .get(BookingViewModel::class.java).also {
                        bookingViewModel = it
                    }

    override fun getLayoutId(): Int = R.layout.fragment_slot

    override fun executePendingVariablesBinding() {
        getViewDataBinding().also {
            it.setVariable(BR.bookingViewModel, bookingViewModel)
            it.setVariable(BR.recyclerViewConfig, recyclerViewConfiguration)
            fragmentSlotBinding = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = savedInstanceState?.getInt(KEY_POSITION) ?: arguments?.getInt(KEY_POSITION)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calender = TimeUtils.getDayForPosition(position!!)
        bookingViewModel.slotQueryLiveData.postValue(calender.time)

        val eimSavedState = savedInstanceState?.getParcelable<Parcelable>(SAVED_STATE_EXPANDABLE_ITEM_MANAGER)
        recyclerViewExpandableItemManager = RecyclerViewExpandableItemManager(eimSavedState).apply {
            setOnGroupExpandListener(groupExpandListener)
            attachRecyclerView(fragmentSlotBinding.recyclerView)
        }.also {
            wrappedAdapter = it.createWrappedAdapter(slotAdapter)
        }

        recyclerViewConfiguration.setRecyclerConfig(wrappedAdapter, linearLayoutManager, RefactoredDefaultItemAnimator().apply {
            supportsChangeAnimations = false
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToSlotListLiveData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_POSITION, position!!)
        recyclerViewExpandableItemManager?.let {
            outState.putParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER, it.savedState)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        recyclerViewExpandableItemManager?.release()
        recyclerViewExpandableItemManager = null

        fragmentSlotBinding.recyclerView.let {
            it.itemAnimator = null
            it.adapter = null
        }

        wrappedAdapter?.let {
            WrapperAdapterUtils.releaseAll(it)
        }

        wrappedAdapter = null
        super.onDestroyView()
    }

    private val groupExpandListener = RecyclerViewExpandableItemManager.OnGroupExpandListener { groupPosition, fromUser, payload ->
        if (fromUser) {
            adjustScrollPositionOnGroupExpanded(groupPosition)
        }
    }

    private fun adjustScrollPositionOnGroupExpanded(groupPosition: Int) {
        val childItemHeight = activity?.resources?.getDimensionPixelSize(R.dimen.child_item_height)
        val topMargin = (activity?.resources?.displayMetrics?.density!! * 16).toInt()

        recyclerViewExpandableItemManager?.scrollToGroup(groupPosition, childItemHeight!!, topMargin, topMargin)
    }

    private fun subscribeToSlotListLiveData() {
        bookingViewModel.filteredSlotListLiveData.observe(this) {
            it?.let {
                if (it.isSuccess()) {
                    slotAdapter.setData(it.value)
                }
            }
        }
    }

    companion object {

        const val KEY_POSITION = "key_position"

        private const val SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager"

        fun newInstance(position: Int): SlotFragment {
            val slotFragment = SlotFragment()
            val args = Bundle()
            args.putInt(KEY_POSITION, position)
            slotFragment.arguments = args
            return slotFragment
        }
    }

}