package com.energysh.activityapidemo.kotlin.contracts

import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract

class SimpleImageLauncher(
    caller: ActivityResultCaller
) : BaseActivityResultLauncher<String, Int>(caller, SimpleImageContract())