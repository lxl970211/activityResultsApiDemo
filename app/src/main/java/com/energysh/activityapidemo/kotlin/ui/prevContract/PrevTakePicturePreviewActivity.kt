package com.energysh.activityapidemo.kotlin.ui.prevContract

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import com.energysh.activityapidemo.R
import kotlinx.android.synthetic.main.activity_prev_take_picture_preview.*

/**
 * 使用预定义的TakePicturePreview() 拍摄照片，返回bitmap
 */
class PrevTakePicturePreviewActivity : AppCompatActivity() {


    private val takePicturePreviewLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
        imageView.setImageBitmap(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prev_take_picture_preview)


        button.setOnClickListener {
            takePicturePreviewLauncher.launch()
        }
    }
}