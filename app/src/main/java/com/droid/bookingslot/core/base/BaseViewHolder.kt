package com.droid.bookingslot.core.base

import android.view.View
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder


abstract class BaseViewHolder(itemView: View) : AbstractExpandableItemViewHolder(itemView) {

    abstract fun onBind(position: Int)

}