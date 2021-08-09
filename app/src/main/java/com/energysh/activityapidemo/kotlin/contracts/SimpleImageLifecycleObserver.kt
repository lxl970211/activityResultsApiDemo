package com.energysh.activityapidemo.kotlin.contracts

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class SimpleImageLifecycleObserver(private val registry : ActivityResultRegistry) : LifecycleObserver {

    lateinit var getSimpleImage : ActivityResultLauncher<String>

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
        getSimpleImage = registry.register("key",  SimpleImageContract()){

        }
    }

    fun selectSimpleImage(inputData : String){
        getSimpleImage.launch(inputData)
    }

}