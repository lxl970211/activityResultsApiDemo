package com.energysh.activityapidemo.kotlin.contracts

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.energysh.activityapidemo.kotlin.ui.simpleimage.SimpleImageActivity

/**
 * 选择样本图片协定
 * ActivityResultContract<String, Int>. String 为输入参数，如果不需要传入参数可以填 Unit 或者 Void
 * createIntent 创建intent 将传入的参数通过intent传输
 * parseResult 根据指定的resultCode （如 Activity.RESULT_OK 或 Activity.RESULT_CANCELED） 和 Intent 生成输出内容
 */
class SimpleImageContract : ActivityResultContract<String, Int>() {

    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, SimpleImageActivity::class.java).apply {
            putExtra("inputData", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        return intent?.getIntExtra("simpleImageResId", 0)?:0
    }
}

sealed class SimpleImageContractSealed{

    class SimpleImageRequestBitmap() : SimpleImageContractSealed(){


    }

}