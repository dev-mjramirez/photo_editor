package jp.bbo.becoandroid.CustomView

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoSwipeViewPager : ViewPager {
    var isSwipeEnabled = false

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        init()
    }

    private fun init() {
        isSwipeEnabled = false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (isSwipeEnabled) {
            super.onInterceptTouchEvent(event)
        } else false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (isSwipeEnabled) {
            super.onTouchEvent(event)
        } else false
    }
}