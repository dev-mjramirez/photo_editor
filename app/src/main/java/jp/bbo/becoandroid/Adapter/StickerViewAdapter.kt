package jp.bbo.becoandroid.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.bbo.becoandroid.Interface.StickerListener
import jp.bbo.becoandroid.Model.PhotoEditor.Sticker
import jp.bbo.becoandroid.R
import java.io.IOException
import java.io.InputStream

class StickerViewAdapter(private val mStickerListener: StickerListener, private var context: Context) : RecyclerView.Adapter<StickerViewAdapter.ViewHolder>() {

    private val mPairList = Sticker().sticker()

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var mImageFilterView: ImageView = itemView.findViewById(R.id.imgStickerView)
        var mTxtFilterName: TextView = itemView.findViewById(R.id.txtStickerName)

        init {
            itemView.setOnClickListener {
                try {
                    mStickerListener.onStickerClick(BitmapFactory.decodeStream(context.assets.open(mPairList[layoutPosition].first))
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_sticker_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filterPair = mPairList[position]
        val fromAsset = getBitmapFromAsset(holder.itemView.context, filterPair.first)
        holder.mImageFilterView.setImageBitmap(fromAsset)
        holder.mTxtFilterName.text = filterPair.second.name.replace("_", " ")
    }

    override fun getItemCount(): Int {
        return mPairList.size
    }

    private fun getBitmapFromAsset(
        context: Context,
        strName: String
    ): Bitmap? {
        val assetManager = context.assets
        val istr: InputStream
        return try {
            istr = assetManager.open(strName)
            BitmapFactory.decodeStream(istr)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}