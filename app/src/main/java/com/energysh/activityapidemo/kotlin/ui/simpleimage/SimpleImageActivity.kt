package com.energysh.activityapidemo.kotlin.ui.simpleimage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.energysh.activityapidemo.R
import kotlinx.android.synthetic.main.activity_simple_image.*

/**
 * 样本图activity
 */
class SimpleImageActivity : AppCompatActivity() {

    private val viewModel by viewModels<SimpleImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_image)

        recycler_view.layoutManager = GridLayoutManager(this, 3)
        recycler_view.adapter = SimpleImageAdapter(viewModel.simpleImageList)
            .apply {
                this.onClickItemListener = {
                    setResult(Activity.RESULT_OK,Intent().apply {
                        putExtra("simpleImageResId", it)
                    })
                    finish()
                }
            }


    }



}