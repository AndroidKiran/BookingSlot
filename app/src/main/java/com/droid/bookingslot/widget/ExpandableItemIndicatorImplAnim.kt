package com.droid.bookingslot.widget

import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Animatable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.LayoutInflater
import com.droid.bookingslot.R

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
internal class ExpandableItemIndicatorImplAnim : ExpandableItemIndicator.Impl() {

    private var mImageView: AppCompatImageView? = null

    override fun onInit(context: Context, attrs: AttributeSet?, defStyleAttr: Int, thiz: ExpandableItemIndicator) {
        val v = LayoutInflater.from(context).inflate(R.layout.widget_expandable_item_indicator, thiz, true)
        mImageView = v.findViewById(R.id.image_view)
    }

    override fun setExpandedState(isExpanded: Boolean, animate: Boolean) {
        if (animate) {
            @DrawableRes val resId = if (isExpanded) R.drawable.vc_expand_less else R.drawable.vc_expand
            mImageView?.setImageResource(resId)
            (mImageView?.drawable as Animatable).start()
        } else {
            @DrawableRes val resId = if (isExpanded) R.drawable.vc_expand_less else R.drawable.vc_expand
            mImageView?.setImageResource(resId)
        }
    }
}