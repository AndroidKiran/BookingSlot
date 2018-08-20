package com.droid.bookingslot.helper

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator

class RecyclerViewConfiguration : BaseObservable() {

    companion object {
        @JvmStatic
        @BindingAdapter("recyclerBinding")
        fun bindRecyclerViewConfiguration(recyclerView: RecyclerView?, recyclerViewConfig: RecyclerViewConfiguration?) {
            recyclerView?.apply {
                setHasFixedSize(false)
                recyclerView.layoutManager = recyclerViewConfig?.layoutManager
                recyclerView.adapter = recyclerViewConfig?.recyclerAdapter
            }
        }
    }

    @get:Bindable
    var layoutManager: LinearLayoutManager? = null
        private set(value) {
            field = value
        }

    @get:Bindable
    var recyclerAdapter: RecyclerView.Adapter<*>? = null
        private set(value) {
            field = value
        }

    @get:Bindable
    var animator: GeneralItemAnimator? = null
        private set(value) {
            field = value
        }


    fun newState(recyclerAdapter: RecyclerView.Adapter<*>?): Builder = Builder(recyclerAdapter)

    inner class Builder constructor(private val recyclerAdapter: RecyclerView.Adapter<*>?) {

        private var layoutManager: LinearLayoutManager? = null

        private var animator: GeneralItemAnimator? = null

        fun setLayoutManger(layoutManager: LinearLayoutManager?): Builder {
            this.layoutManager = layoutManager
            return this@Builder
        }

        fun setAnimator(animator: GeneralItemAnimator): Builder {
            this.animator = animator
            return this@Builder
        }

        fun commit() {
            this@RecyclerViewConfiguration.setRecyclerConfig(recyclerAdapter, layoutManager, animator)
        }

    }

    fun setRecyclerConfig(recyclerAdapter: RecyclerView.Adapter<*>?, layoutManager: LinearLayoutManager?,
                          animator: GeneralItemAnimator? = RefactoredDefaultItemAnimator()) {
        this.layoutManager = layoutManager
        this.recyclerAdapter = recyclerAdapter
        this.animator = animator
        notifyChange()
    }

}

