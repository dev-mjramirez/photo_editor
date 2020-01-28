package jp.bbo.becoandroid.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.bbo.becoandroid.Interface.TextListener
import jp.bbo.becoandroid.Model.PhotoEditor.Color
import jp.bbo.becoandroid.R

class ColorPickerAdapter(private val textListener: TextListener,  private var context: Context?) : RecyclerView.Adapter<ColorPickerAdapter.ViewHolder>() {

    private val mColorList = Color().color()

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var colorPickerView: View = itemView.findViewById(R.id.color_picker_view)
        init {
            itemView.setOnClickListener {
                textListener.onChangeColor(
                    mColorList[layoutPosition]
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_color_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        context?.resources?.getColor(mColorList[position])?.let {
            holder.colorPickerView.setBackgroundColor(it)
        }
    }

    override fun getItemCount(): Int {
        return mColorList.size
    }


}