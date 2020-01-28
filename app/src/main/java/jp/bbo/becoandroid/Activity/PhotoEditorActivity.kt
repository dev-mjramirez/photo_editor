package jp.bbo.becoandroid.Activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.tabs.TabLayout
import jp.bbo.beco_photoeditor.Ui.Fragment.FilterFragment
import jp.bbo.beco_photoeditor.Ui.Fragment.StampFragment
import jp.bbo.beco_photoeditor.Ui.Fragment.TextFragment
import jp.bbo.becoandroid.CustomView.*
import jp.bbo.becoandroid.Interface.FilterListener
import jp.bbo.becoandroid.Interface.StickerListener
import jp.bbo.becoandroid.Interface.TextListener
import jp.bbo.becoandroid.Model.Common.Constant
import jp.bbo.becoandroid.R
import jp.bbo.becoandroid.Util.TabLayoutUtils
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageToneCurveFilter
import java.util.*

class PhotoEditorActivity : PhotoEditorContract, CustomHeaderTitle(), FilterListener, StickerListener,
    TextListener {

    companion object {
        private const val DEBUGTAG = "BeCo"
        private const val TAG = "PhotoEditor"
        var isTextFragmentInit = false
        var isFilterFragmentInit = false
    }

    private var imageView:ImageView? = null
    private var textStickerView:TextStickerView? = null

    @BindView(R.id.photoEditorView) lateinit var  photoEditorView: RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_photo)
        ButterKnife.bind(this)

        imageView = ImageView(this)
        imageView?.setImageDrawable(resources.getDrawable(R.drawable.cute_baby))
        photoEditorView.addView(imageView)

        init()
        onDoneCallBack()
    }

    private var mInputTextDialog: InputTextDialog? = null
    private var mCurrentView: StickerView? = null
    private var mCurrentEditTextStickerView: TextStickerView? = null
    private var mViews: ArrayList<View>? = null
    private lateinit var mViewPager: NoSwipeViewPager
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var constant = Constant()
    @BindView(R.id.tabMenu) lateinit var tabMenu:TabLayout
    private fun init(){

        mInputTextDialog = InputTextDialog(this@PhotoEditorActivity)

        tabMenu = findViewById(R.id.tabMenu)
        tabMenu.addTab(tabMenu.newTab().setCustomView(
            TabLayoutUtils.customTabBadgeSupport(this,
                R.drawable.ic_tab_filter_on, constant.tab_filter)))
        tabMenu.addTab(tabMenu.newTab().setCustomView(TabLayoutUtils.customTabBadgeSupport(this,
            R.drawable.ic_tab_stamp_off, constant.tab_stamp)))
        tabMenu.addTab(tabMenu.newTab().setCustomView(TabLayoutUtils.customTabBadgeSupport(this,
            R.drawable.ic_tab_text_off, constant.tab_text)))
        tabMenu.tabGravity = TabLayout.GRAVITY_FILL

        // PageAdapter
        mSectionsPagerAdapter =
            SectionsPagerAdapter(supportFragmentManager, tabMenu.tabCount)
        mViewPager = findViewById(R.id.viewPager)
        mViewPager.adapter = mSectionsPagerAdapter
        mViewPager.isSwipeEnabled = false
        mViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabMenu))
        tabMenu.addOnTabSelectedListener(onTabSelectedListener)
    }

    inner class SectionsPagerAdapter internal constructor(
        fm: FragmentManager?,
        var mNumOfTabs: Int
    ) : FragmentPagerAdapter(fm!!) {
        override fun getItem(position: Int): Fragment {
            var f: Fragment? = null
            when (position) {
                0 -> {
                    f = FilterFragment(this@PhotoEditorActivity)
                    isFilterFragmentInit = true
                }
                1 -> f = StampFragment(this@PhotoEditorActivity)
                2 -> {
                    f = TextFragment(this@PhotoEditorActivity)
                    isTextFragmentInit = true
                }
            }
            return f!!
        }

        override fun getCount(): Int {
            return mNumOfTabs
        }
    }

    private val onTabSelectedListener: TabLayout.OnTabSelectedListener = object :
        TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) { // setting colors
            if (tab.customView != null) {
                val tabIconColor = applicationContext.resources
                    .getColor(R.color.black)
                val tabIcon = tab.customView!!
                    .findViewById<ImageView>(R.id.icon)
                val tabName = tab.customView!!
                    .findViewById<TextView>(R.id.tv_name)
                tabIcon.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
                tabName.setTextColor(tabIconColor)
            }
            onChangeTab(tab.position)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            if (tab.customView != null) {
                val tabIconColor =
                    applicationContext.resources.getColor(android.R.color.darker_gray)
                val tabIcon = tab.customView!!
                    .findViewById<ImageView>(R.id.icon)
                val tabName = tab.customView!!
                    .findViewById<TextView>(R.id.tv_name)
                tabIcon.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
                tabName.setTextColor(tabIconColor)
            }
        }

        override fun onTabReselected(tab: TabLayout.Tab) { // don't refresh tab on tab changed
            val limit =
                if (mSectionsPagerAdapter!!.count > 1) mSectionsPagerAdapter!!.count - 1 else 1
            mViewPager.offscreenPageLimit = limit
        }
    }

    private fun onChangeTab(index: Int) {
        Log.d("onChangeTab :", index.toString())
        when (index) {
            0 -> setActionBarTitle(constant.tab_filter, 0)
            1 -> setActionBarTitle(constant.tab_stamp, 0)
            2 -> setActionBarTitle(constant.tab_text, 0)
        }
        mViewPager.setCurrentItem(index, false)
    }

    private fun onDoneCallBack(){
        mInputTextDialog?.setCompleteCallBack(object : InputTextDialog.CompleteCallBack {
            override fun onComplete(bubbleTextView: View, str: String) {
                (bubbleTextView as TextStickerView).setText(str)
            }
        })

    }

    override fun onFilterSelected(photoFilter: String) {
        val assetManager = assets.open(photoFilter)
        val filter = GPUImageToneCurveFilter()
        filter.setFromCurveFileInputStream(assetManager)
        val gpuImage = GPUImage(this)
        gpuImage.setImage(BitmapFactory.decodeResource(resources,
            R.drawable.cute_baby
        ))
        gpuImage.setFilter(filter)
        imageView?.setImageBitmap(gpuImage.bitmapWithFilterApplied)
    }

    override fun onStickerClick(bitmap: Bitmap) {
        val stickerView = StickerView(this)
        stickerView.setBitmap(bitmap)
        stickerView.setOperationListener(object : StickerView.OperationListener {
            override fun onDeleteClick() {
                mViews?.remove(stickerView)
                photoEditorView.removeView(stickerView)
            }

            override fun onEdit(stickerView: StickerView) {
                mCurrentEditTextStickerView?.setInEdit(false)
                mCurrentView?.setInEdit(false)
                mCurrentView = stickerView
                mCurrentView!!.setInEdit(true)
            }

            override fun onTop(stickerView: StickerView) {
                val position = mViews?.indexOf(stickerView)
                if (position == mViews?.size!! - 1) {
                    return
                }
                val stickerTemp: StickerView = position?.let { mViews?.removeAt(it) } as StickerView
                mViews?.add(mViews!!.size, stickerTemp)
            }
        })
        val lp = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        photoEditorView.addView(stickerView, lp)
        mViews?.add(stickerView)
        setCurrentStickerEdit(stickerView)
    }

    private fun setCurrentStickerEdit(stickerView: StickerView) {
        if (mCurrentView != null) {
            mCurrentView!!.setInEdit(false)
        }
        mCurrentEditTextStickerView?.setInEdit(false)
        mCurrentView = stickerView
        stickerView.setInEdit(true)
    }

    override fun onTextClick(inputText: String) {
        textStickerView = TextStickerView(this)
        textStickerView?.setText(inputText)
        textStickerView?.setBitmap(BitmapFactory.decodeResource(resources,
            R.drawable.text_background
        ))
        textStickerView?.setOperationListener(object : TextStickerView.OperationListener {
            override fun onDeleteClick() {
                mViews?.remove(textStickerView!!)
                photoEditorView.removeView(textStickerView)
            }

            override fun onEdit(textStickerView: TextStickerView) {
                if (mCurrentView != null) {
                    mCurrentView!!.setInEdit(false)
                }
                mCurrentEditTextStickerView!!.setInEdit(false)
                mCurrentEditTextStickerView = textStickerView
                mCurrentEditTextStickerView!!.setInEdit(true)
            }

            override fun onClick(textStickerView: TextStickerView) {
                mInputTextDialog?.setTextStickerView(textStickerView)
                mInputTextDialog?.show()
            }

            override fun onTop(textStickerView: TextStickerView) {
                val position = mViews?.indexOf(textStickerView)
                if (position == mViews?.size!! - 1) {
                    return
                }
                val textView = position?.let { mViews?.removeAt(it) } as TextStickerView
                mViews?.add(mViews!!.size, textView)
            }
        })
        val lp = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        photoEditorView.addView(textStickerView, lp)
        mViews?.add(textStickerView!!)
        setCurrentEdit(textStickerView!!)
    }

    override fun onChangeColor(colorCode: Int) {
        textStickerView?.setTextColor(colorCode)
    }

    private fun setCurrentEdit(textStickerView: TextStickerView) {
        if (mCurrentView != null) {
            mCurrentView!!.setInEdit(false)
        }
        if (mCurrentEditTextStickerView != null) {
            mCurrentEditTextStickerView?.setInEdit(false)
        }
        mCurrentEditTextStickerView = textStickerView
        mCurrentEditTextStickerView?.setInEdit(true)
    }

    @SuppressLint("SimpleDateFormat")
    override fun save() {
        val bitmap = Bitmap.createBitmap(
            photoEditorView.width,
            photoEditorView.height
            , Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        photoEditorView.draw(canvas)

    }

}
