package jp.bbo.becoandroid.CustomView

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import jp.bbo.becoandroid.R
import jp.bbo.becoandroid.Util.CommonUtils

class InputTextDialog(context: Context) : Dialog(context, android.R.style.Theme_Translucent_NoTitleBar) {
    private val defaultStr: String
    private var et_bubble_input: EditText? = null
    private var tv_show_count: TextView? = null
    private var tv_action_done: TextView? = null
    private var mContext: Context = context
    private var textStickerView: TextStickerView? = null

    fun setTextStickerView(textStickerView: TextStickerView) {
        this.textStickerView = textStickerView
        if (defaultStr == textStickerView.getmStr()) {
            et_bubble_input!!.setText("")
        } else {
            et_bubble_input!!.setText(textStickerView.getmStr())
            et_bubble_input!!.setSelection(textStickerView.getmStr().length)
        }
    }

    private fun initView() {
        setContentView(R.layout.view_input_dialog)
        tv_action_done = findViewById<View>(R.id.tv_action_done) as TextView
        et_bubble_input = findViewById<View>(R.id.et_bubble_input) as EditText
        tv_show_count = findViewById<View>(R.id.tv_show_count) as TextView
        et_bubble_input!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                val textLength = CommonUtils.calculateLength(s)
                tv_show_count!!.text = (MAX_COUNT - textLength).toString()
                if (textLength > MAX_COUNT) {
                    tv_show_count!!.setTextColor(mContext.resources.getColor(R.color.red_A700))
                } else {
                    tv_show_count!!.setTextColor(mContext.resources.getColor(R.color.grey_600))
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        et_bubble_input!!.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                done()
                return@setOnEditorActionListener true
            }
            false
        }
        tv_action_done!!.setOnClickListener { v: View? -> done() }
    }

    override fun show() {
        super.show()
        val handler = Handler()
        handler.postDelayed({
            val m =
                et_bubble_input!!.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            m.toggleSoftInput(0, InputMethodManager.SHOW_FORCED)
        }, 500)
    }

    override fun dismiss() {
        super.dismiss()
        val m =
            et_bubble_input!!.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        m.hideSoftInputFromWindow(et_bubble_input!!.windowToken, 0)
    }

    interface CompleteCallBack {
        fun onComplete(bubbleTextView: View, str: String)
    }

    private var mCompleteCallBack: CompleteCallBack? = null

    fun setCompleteCallBack(completeCallBack: CompleteCallBack?) {
        mCompleteCallBack = completeCallBack
    }

    private fun done() {
        if (Integer.valueOf(tv_show_count!!.text.toString()) < 0) {
            Toast.makeText(
                mContext,
                mContext.getString(R.string.over_text_limit),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        dismiss()
        if (mCompleteCallBack != null) {
            val str: String = if (TextUtils.isEmpty(et_bubble_input!!.text)) {
                ""
            } else {
                et_bubble_input!!.text.toString()
            }
            textStickerView?.let { mCompleteCallBack!!.onComplete(it, str) }
        }
    }

    companion object {
        private const val MAX_COUNT = 33
    }

    init {
        defaultStr = context.getString(R.string.double_click_input_text)
        initView()
    }
}