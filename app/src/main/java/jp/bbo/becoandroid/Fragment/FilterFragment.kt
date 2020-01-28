package jp.bbo.beco_photoeditor.Ui.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.bbo.becoandroid.Adapter.FilterViewAdapter
import jp.bbo.becoandroid.Interface.FilterListener
import jp.bbo.becoandroid.R

class FilterFragment(private val filterListener: FilterListener) :
    Fragment() {
    private lateinit var mRvFilters: RecyclerView
    private var mFilterViewAdapter: FilterViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        mFilterViewAdapter = FilterViewAdapter(filterListener)
        mRvFilters = view.findViewById(R.id.rvFilterView)
        val llmFilters = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mRvFilters.layoutManager = llmFilters
        mRvFilters.adapter = mFilterViewAdapter

        return view
    }

}