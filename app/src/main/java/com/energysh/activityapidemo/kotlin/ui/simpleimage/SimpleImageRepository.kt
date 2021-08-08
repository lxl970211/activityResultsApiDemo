package com.energysh.activityapidemo.kotlin.ui.simpleimage

import com.energysh.activityapidemo.R

class SimpleImageRepository {

    companion object{
        val instance by lazy {
            SimpleImageRepository()
        }
    }

    val simpleImageList = listOf<Int>(
        R.drawable.simple_image_1,
        R.drawable.simple_image_2,
        R.drawable.simple_image_3,
        R.drawable.simple_image_4,
        R.drawable.simple_image_5,
    )


}