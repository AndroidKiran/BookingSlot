package com.droid.bookingslot.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.widget.FrameLayout

open class ExpandableItemIndicator : FrameLayout {

    private var mImpl: Impl? = null

    internal abstract class Impl {
        abstract fun onInit(context: Context, attrs: AttributeSet?, defStyleAttr: Int, thiz: ExpandableItemIndicator)

        abstract fun setExpandedState(isExpanded: Boolean, animate: Boolean)
    }

    constructor(context: Context) : super(context) {
        onInit(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        onInit(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        onInit(context, attrs, defStyleAttr)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun shouldUseAnimatedIndicator(context: Context, attrs: AttributeSet?, defStyleAttr: Int): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
    }

    fun onInit(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        mImpl = if (shouldUseAnimatedIndicator(context, attrs, defStyleAttr)) {
            ExpandableItemIndicatorImplAnim()
        } else {
            ExpandableItemIndicatorImplNoAnim()
        }
        mImpl?.onInit(context, attrs, defStyleAttr, this)
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        super.dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        super.dispatchThawSelfOnly(container)
    }

    fun setExpandedState(isExpanded: Boolean, animate: Boolean) {
        mImpl!!.setExpandedState(isExpanded, animate)
    }
}
