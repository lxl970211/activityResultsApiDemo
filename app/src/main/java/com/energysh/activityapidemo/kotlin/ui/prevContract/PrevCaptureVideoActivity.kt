package com.energysh.activityapidemo.kotlin.ui.prevContract

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.energysh.activityapidemo.R
import kotlinx.android.synthetic.main.activity_prev_take_video.*

/**
 * 使用预定义的CaptureVideo() 拍摄视频
 */
class PrevCaptureVideoActivity : AppCompatActivity() {

    private val takeVideoLauncher = registerForActivityResult(ActivityResultContracts.CaptureVideo()){

        Toast.makeText(this, "拍摄${if (it) "成功" else "失败"}", Toast.LENGTH_LONG).show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prev_take_video)

        button2.setOnClickListener {
            takeVideoLauncher.launch(createCaptureVideoUri())
        }

    }

    /**
     * 创建一个保存视频的uri
     */
    private fun createCaptureVideoUri() : Uri?{

        val contentValues = ContentValues().apply {
            put(MediaStore.Video.VideoColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            put(MediaStore.Video.VideoColumns.MIME_TYPE, "video/mp4")
        }
        return contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)

    }
}