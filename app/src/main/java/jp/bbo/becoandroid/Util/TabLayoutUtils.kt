package jp.bbo.becoandroid.Util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import jp.bbo.becoandroid.R

object TabLayoutUtils {
    fun customTabBadgeSupport(
        context: Context?,
        icon_id: Int,
        name: String?
    ): View {
        val inflater = LayoutInflater.from(context)
        val view =
            inflater.inflate(R.layout.custom_badge_layout, null)
        val icon =
            view.findViewById<View>(R.id.icon) as ImageView
        val tv_name =
            view.findViewById<View>(R.id.tv_name) as TextView
        icon.setImageResource(icon_id)
        tv_name.text = name
        view.measure(
            View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            ),
            View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            )
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        return view
    }
}