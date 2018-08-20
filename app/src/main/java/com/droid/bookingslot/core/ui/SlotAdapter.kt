package com.droid.bookingslot.core.ui

import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import com.droid.bookingslot.R
import com.droid.bookingslot.core.base.BaseChildViewHolder
import com.droid.bookingslot.core.base.BaseViewHolder
import com.droid.bookingslot.core.repo.SlotGroup
import com.droid.bookingslot.databinding.ItemChildBinding
import com.droid.bookingslot.databinding.ItemGroupBinding
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemConstants
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemConstants.STATE_FLAG_HAS_EXPANDED_STATE_CHANGED
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemConstants.STATE_FLAG_IS_EXPANDED
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter
import kotlinx.android.synthetic.main.item_child.view.*
import kotlinx.android.synthetic.main.item_group.view.*

class SlotAdapter : AbstractExpandableItemAdapter<SlotAdapter.GroupViewHolder, SlotAdapter.SlotViewHolder>() {

    var slotData: List<SlotGroup> = emptyList()

    init {
        setHasStableIds(true)
    }

    override fun getGroupCount(): Int = slotData.size

    override fun getChildCount(groupPosition: Int): Int = slotData[groupPosition].slotList.size

    override fun getGroupId(groupPosition: Int): Long = slotData[groupPosition].groupId

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = slotData[groupPosition].slotList[childPosition].slotId

    override fun onCheckCanExpandOrCollapseGroup(holder: GroupViewHolder?, groupPosition: Int, x: Int, y: Int, expand: Boolean): Boolean {
        return true
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): GroupViewHolder {
        val groupItemBinding = ItemGroupBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
        )
        return GroupViewHolder(groupItemBinding)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): SlotViewHolder {

        val childItemBind = ItemChildBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
        )
        return SlotViewHolder(childItemBind)
    }


    override fun onBindChildViewHolder(holder: SlotViewHolder?, groupPosition: Int, childPosition: Int, viewType: Int) {
        holder?.onBind(groupPosition, childPosition)
    }


    override fun onBindGroupViewHolder(holder: GroupViewHolder?, groupPosition: Int, viewType: Int) {
        holder?.onBind(groupPosition)
    }

    fun setData(slotList: List<SlotGroup>) {
        slotData = slotList as MutableList<SlotGroup>
        notifyDataSetChanged()
    }


    inner class GroupViewHolder constructor(private val binding: ItemGroupBinding) : BaseViewHolder(binding.root) {
        override fun onBind(position: Int) {
            val groupItem = slotData[position]
            binding.apply {
                this.groupItem = groupItem
                this.root.isClickable = true
                this.executePendingBindings()
            }

            val expandState = expandStateFlags

            if (groupItem.slotList.isNotEmpty() && (expandStateFlags and ExpandableItemConstants.STATE_FLAG_IS_UPDATED != 0)) {
                val isExpanded: Boolean = (expandState and STATE_FLAG_IS_EXPANDED) != 0
                val animateIndicator = (expandState and STATE_FLAG_HAS_EXPANDED_STATE_CHANGED) != 0

                binding.root.arrow.setExpandedState(isExpanded, animateIndicator)
            } else {
                binding.root.arrow.setExpandedState(false, false)
            }
        }
    }

    inner class SlotViewHolder constructor(private val binding: ItemChildBinding) : BaseChildViewHolder(binding.root) {

        override fun onBind(groupPosition: Int, childPosition: Int) {
            val childItem = slotData[groupPosition].slotList[childPosition]
            binding.apply {
                this.childItem = childItem
                this.executePendingBindings()
            }

            if(childItem.isBooked || childItem.isExpired) {
                binding.root.child_view.background = ContextCompat.getDrawable(binding.root.context, R.color.colorGray)
                binding.root.isClickable = false
            } else {
                binding.root.isClickable = true
                binding.root.child_view.background = ContextCompat.getDrawable(binding.root.context, android.R.color.white)
            }


        }
    }
}