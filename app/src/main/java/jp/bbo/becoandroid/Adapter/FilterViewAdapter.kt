package jp.bbo.becoandroid.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.yalantis.ucrop.SharedPrefManager
import jp.bbo.becoandroid.Interface.FilterListener
import jp.bbo.becoandroid.Model.PhotoEditor.Filter
import jp.bbo.becoandroid.R
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageToneCurveFilter

class FilterViewAdapter(private val mFilterListener: FilterListener) :
    RecyclerView.Adapter<FilterViewAdapter.ViewHolder>() {
    private var context: Context? = null
    private val mPairList = Filter().filter()

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.photoEditorView)
        val mTxtFilterName: TextView = itemView.findViewById(R.id.txtStickerName)

        init {
            itemView.setOnClickListener {
                mFilterListener.onFilterSelected(
                    mPairList[layoutPosition].first
                )
            }
        }
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.adapter_filter_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val assetManager = context?.assets?.open(mPairList[position].first)
        val filter = GPUImageToneCurveFilter()
        filter.setFromCurveFileInputStream(assetManager)
        val gpuImage = GPUImage(context)
        gpuImage.setImage(BitmapFactory.decodeResource(context?.resources, R.drawable.cute_baby))
        gpuImage.setFilter(filter)
        holder.imageView.setImageBitmap(gpuImage.bitmapWithFilterApplied)
        holder.mTxtFilterName.text = mPairList[position].second.toString().replace("_", " ")
    }

    override fun getItemCount(): Int {
        return mPairList.size
    }
}