package com.energysh.activityapidemo.kotlin

import androidx.activity.result.ActivityResultCaller
import androidx.appcompat.app.AppCompatActivity
import com.energysh.activityapidemo.kotlin.contracts.SimpleImageContract


inline fun AppCompatActivity.requestSimpleImage(
    inputValue : String,
    crossinline requestSimpleImage : (simpleImageResId : Int) -> Unit
){
    registerForActivityResult(SimpleImageContract()){
        requestSimpleImage.invoke(it)
    }.launch(inputValue)

}




