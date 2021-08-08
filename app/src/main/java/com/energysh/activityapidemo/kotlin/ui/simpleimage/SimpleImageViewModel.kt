package com.energysh.activityapidemo.kotlin.ui.simpleimage

import androidx.lifecycle.ViewModel

class SimpleImageViewModel : ViewModel() {

    val simpleImageList = SimpleImageRepository.instance.simpleImageList

}