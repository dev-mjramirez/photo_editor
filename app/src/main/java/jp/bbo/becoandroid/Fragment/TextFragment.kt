package jp.bbo.beco_photoeditor.Ui.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.bbo.becoandroid.Adapter.ColorPickerAdapter
import jp.bbo.becoandroid.Interface.TextListener
import jp.bbo.becoandroid.Model.PhotoEditor.TabEvent
import jp.bbo.becoandroid.R

class TextFragment(private val textListener: TextListener) :
    Fragment() {
    private lateinit var ll_text_add: LinearLayout
    private lateinit var ll_text_font: LinearLayout
    private lateinit var ll_text_color: LinearLayout
    private lateinit var rv_color: RecyclerView
    private var color_picker_adapter: ColorPickerAdapter? = null
    private lateinit var ll_cancel: LinearLayout
    private lateinit var ll_done: LinearLayout
    private var rl_colorview_container: RelativeLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_text, container, false)
        color_picker_adapter = activity?.let { ColorPickerAdapter(textListener,context) }
        init(view)
        return view
    }

    private fun init(view: View) {
        ll_text_add = view.findViewById(R.id.ll_text_add)
        ll_text_font = view.findViewById(R.id.ll_text_font)
        ll_text_color = view.findViewById(R.id.ll_text_color)
        rl_colorview_container = view.findViewById(R.id.rl_colorview_container)
        rv_color = view.findViewById(R.id.rvColorView)
        ll_cancel = view.findViewById(R.id.ll_cancel)
        ll_done = view.findViewById(R.id.ll_done)

        val llmFilters = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_color.layoutManager = llmFilters
        rv_color.adapter = color_picker_adapter

        ll_text_add.setOnClickListener(clickListener)
        ll_text_font.setOnClickListener(clickListener)
        ll_text_color.setOnClickListener(clickListener)
        ll_cancel.setOnClickListener(clickListener)
        ll_done.setOnClickListener(clickListener)
    }

    private val clickListener =
        View.OnClickListener { view ->
            when (view.id) {
                R.id.ll_text_add -> textListener.onTextClick("ひるくん")
                R.id.ll_text_font -> {}
                R.id.ll_text_color -> {
                    rl_colorview_container!!.visibility = View.VISIBLE
                    TabEvent.instance?.updateShowHideTab(false)
                }
                R.id.ll_cancel -> {
                    rl_colorview_container!!.visibility = View.GONE
                    TabEvent.instance?.updateShowHideTab(true)
                }
                R.id.ll_done -> {
                    rl_colorview_container!!.visibility = View.GONE
                    TabEvent.instance?.updateShowHideTab(true)
                }
            }
        }

}