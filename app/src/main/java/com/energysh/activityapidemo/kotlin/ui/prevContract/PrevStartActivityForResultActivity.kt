package com.energysh.activityapidemo.kotlin.ui.prevContract

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.energysh.activityapidemo.R
import com.energysh.activityapidemo.kotlin.ui.simpleimage.SimpleImageActivity
import kotlinx.android.synthetic.main.activity_prev_start_for_result.*

class PrevStartActivityForResultActivity : AppCompatActivity() {


    private val startActivityForResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        it.data?.let {
            it.getIntExtra("simpleImageResId", 0)?.let {simpleImageResId->
                if (simpleImageResId != 0) {
                    iv_simple_image.setImageResource(simpleImageResId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prev_start_for_result)

        btn_get_simple_image.setOnClickListener {

            startActivityForResultLauncher.launch(Intent(this, SimpleImageActivity::class.java))

        }

    }
}