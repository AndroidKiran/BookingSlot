package com.droid.bookingslot.core.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import com.droid.bookingslot.BR
import com.droid.bookingslot.R
import com.droid.bookingslot.core.base.BaseActivity
import com.droid.bookingslot.core.presenter.BookingViewModel
import com.droid.bookingslot.databinding.ActivityBookingBinding
import com.droid.bookingslot.helper.TimeUtils
import com.droid.bookingslot.mvvm.observe
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.util.*
import javax.inject.Inject

class BookingActivity : BaseActivity<ActivityBookingBinding, BookingViewModel>(), HasSupportFragmentInjector {

    @Inject
    lateinit var viewFactory: ViewModelProvider.Factory

    lateinit var bookingViewModel: BookingViewModel

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var activityBookingBinding: ActivityBookingBinding

    private var dayPagerAdapter: BookingActivity.DayPagerAdapter? = null

    override fun getViewModel() =
            ViewModelProviders.of(this@BookingActivity, viewFactory)
                    .get(BookingViewModel::class.java).also {
                        bookingViewModel = it
                    }

    override fun getLayoutId(): Int = R.layout.activity_booking

    override fun executePendingVariablesBinding() {
        getViewDataBinding().also {
            it.setVariable(BR.bookingViewModel, bookingViewModel)
            activityBookingBinding = it
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewPager()
        subscribeToSLotListLiveData()
        bookingViewModel.queryLiveData.postValue(true)
    }

    private fun initViewPager() {
        dayPagerAdapter = DayPagerAdapter(supportFragmentManager, this@BookingActivity)
        activityBookingBinding.viewpager.apply {
            adapter = dayPagerAdapter
            offscreenPageLimit = TimeUtils.DAYS_OF_TIME
            addOnPageChangeListener(listener)
        }.also {
            activityBookingBinding.tabs.setupWithViewPager(it)
            listener.onPageSelected(it.currentItem)
        }
    }

    private val listener = object : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            val cal = TimeUtils.getDayForPosition(position).getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
            activityBookingBinding.month.text = cal
        }
    }

    inner class DayPagerAdapter(fragmentManager: FragmentManager, private val context: Context) : CachingFragmentStatePagerAdapter(fragmentManager) {

        override fun getCount() = TimeUtils.DAYS_OF_TIME

        override fun getItem(position: Int) = SlotFragment.newInstance(position)

        override fun getPageTitle(position: Int): CharSequence {
            val cal = TimeUtils.getDayForPosition(position)
            return TimeUtils.getFormattedDate(context, cal.timeInMillis)
        }
    }

    private fun subscribeToSLotListLiveData() {
        bookingViewModel.slotListLiveData.observe(this) {
            it?.let {
                if (!it.isSuccess()) {

                }
            }
        }
    }

}