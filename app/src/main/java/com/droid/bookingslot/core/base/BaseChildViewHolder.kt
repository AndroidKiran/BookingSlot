package com.droid.bookingslot.core.base

import android.view.View
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder


abstract class BaseChildViewHolder(itemView: View) : AbstractExpandableItemViewHolder(itemView) {

    abstract fun onBind(groupPosition: Int, childPosition: Int)

}