package com.energysh.activityapidemo.kotlin.ui.prevContract

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.energysh.activityapidemo.R
import kotlinx.android.synthetic.main.activity_prev_take_video.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 使用预定义的CaptureVideo() 拍摄视频
 */
class PrevCaptureVideoActivity : AppCompatActivity() {


    private var videoUri : Uri? = null

    private val takeVideoLauncher = registerForActivityResult(ActivityResultContracts.CaptureVideo()){

        Toast.makeText(this, "拍摄${if (it) "成功" else "失败"}", Toast.LENGTH_LONG).show()

        if (it){
            lifecycleScope.launch {
                delay(2000)

                video_view.setVideoURI(videoUri)
                video_view.start()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prev_take_video)

        button2.setOnClickListener {
            videoUri = createCaptureVideoUri()
            takeVideoLauncher.launch(videoUri)
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

    override fun onPause() {
        super.onPause()
        video_view?.pause()
    }
}