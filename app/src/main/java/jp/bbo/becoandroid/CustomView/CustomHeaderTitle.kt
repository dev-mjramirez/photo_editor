package jp.bbo.becoandroid.CustomView

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import jp.bbo.becoandroid.Activity.PhotoEditorActivity
import jp.bbo.becoandroid.R

open class CustomHeaderTitle : AppCompatActivity() {
    private lateinit var tv_back: TextView
    private lateinit var tv_title: TextView
    private lateinit var tv_continue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_photo)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.activity_custom_header)
        supportActionBar!!.elevation = 1f
        // add listener to button
        tv_back = findViewById(R.id.tv_back)
        tv_title = findViewById(R.id.tv_title)
        tv_continue =
            findViewById(R.id.tv_continue)
        tv_back.setOnClickListener(btnEvent)
        tv_continue.setOnClickListener(btnEvent)
    }

    private val btnEvent =
        View.OnClickListener { v ->
            when (v.id) {
                R.id.tv_back -> onBackPressed()
                R.id.tv_continue -> {
                    if (requestPermission()) {
                        PhotoEditorActivity().save()
                    }
                }
            }
            intent?.let { startActivity(it) }
        }

    fun setActionBarTitle(title: String?, textColor: Int) {
        tv_title.text = title
        if (textColor != 0) {
            tv_title.setTextColor(textColor)
        } else {
            tv_title.setTextColor(resources.getColor(android.R.color.black))
        }
    }

    fun requestPermission(): Boolean {
        val isGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        if (!isGranted) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                READ_WRITE_STORAGE
            )
        }
        return isGranted
    }

    fun isPermissionGranted(isGranted: Boolean, permission: String?) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            READ_WRITE_STORAGE -> isPermissionGranted(
                grantResults[0] == PackageManager.PERMISSION_GRANTED,
                permissions[0]
            )
        }
    }

    companion object {
        const val READ_WRITE_STORAGE = 52
    }

}