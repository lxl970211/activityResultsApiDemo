package com.energysh.activityapidemo.kotlin.contracts

import androidx.activity.result.ActivityResultCallback

import androidx.annotation.NonNull

import android.annotation.SuppressLint

import androidx.activity.result.contract.ActivityResultContract

import androidx.activity.result.ActivityResultCaller

import androidx.activity.result.ActivityResultLauncher

/**
 * ActivityResultLauncher 基类
 */
open class BaseActivityResultLauncher<I, O>(
    caller: ActivityResultCaller,
    contract: ActivityResultContract<I, O>
) {

    private val launcher: ActivityResultLauncher<I>
    private var callback: ActivityResultCallback<O>? = null

    init {
        launcher = caller.registerForActivityResult(contract) { result: O ->
            callback?.onActivityResult(result)
            callback = null
        }
    }

    fun launch(@SuppressLint("UnknownNullness") input: I, callback: ActivityResultCallback<O>) {
        this.callback = callback
        launcher.launch(input)
    }

}