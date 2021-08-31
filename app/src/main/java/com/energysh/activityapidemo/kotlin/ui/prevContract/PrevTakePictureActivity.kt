package com.energysh.activityapidemo.kotlin.ui.prevContract

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.energysh.activityapidemo.R
import kotlinx.android.synthetic.main.activity_prev_take_picture.*

/**
 * 使用预定义的TakePicture() 拍摄照片，返回boolean 类型告知是否拍摄成功
 */
class PrevTakePictureActivity : AppCompatActivity() {

    private var takeImageUri : Uri? = null

    private val takePicturePreviewLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){
        if (it) {
            takeImageUri?.let {
                imageView.setImageURI(takeImageUri)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prev_take_picture)


        button.setOnClickListener {
            takeImageUri = createImageUri()
            takePicturePreviewLauncher.launch(takeImageUri)
        }
    }


    /**
     * 创建一个保存视频的uri
     */
    private fun createImageUri() : Uri?{

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.ImageColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/png")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    }
}