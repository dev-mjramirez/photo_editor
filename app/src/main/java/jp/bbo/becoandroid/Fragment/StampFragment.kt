package jp.bbo.beco_photoeditor.Ui.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.bbo.becoandroid.Adapter.StickerViewAdapter
import jp.bbo.becoandroid.Interface.StickerListener
import jp.bbo.becoandroid.R

class StampFragment(private val mStickerListener: StickerListener) :
    Fragment() {
    private lateinit var mRvStamps: RecyclerView
    private var mStickerViewAdapter: StickerViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_stamp, container, false)
        mStickerViewAdapter = activity?.let { StickerViewAdapter(mStickerListener, it) }
        mRvStamps = view.findViewById(R.id.rvStampView)
        val llmFilters =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mRvStamps.layoutManager = llmFilters
        mRvStamps.adapter = mStickerViewAdapter
        return view
    }
}