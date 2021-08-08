package com.energysh.activityapidemo.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.energysh.activityapidemo.R
import com.energysh.activityapidemo.kotlin.contracts.SimpleImageContract
import com.energysh.activityapidemo.kotlin.contracts.SimpleImageLauncher
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity Results Api示例Activity
 */
class MainActivity : AppCompatActivity() {

    /**
     * 注册打开样本图片Activity
     */
    val simpleImageLauncher = registerForActivityResult(SimpleImageContract()){
        //返回选择的样本图片
        if (it != 0){
            iv_simple_image.setImageResource(it)
        }
    }

    val simpleImageLauncher2 = SimpleImageLauncher(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_get_simple_image.setOnClickListener {
            //通过调用launch()放大打开选择样本图片activity
//            simpleImageLauncher.launch("")
            //如果需要设置打开动画 可以传入 ActivityOptionsCompat参数定义自定义动画
//            simpleImageLauncher.launch("", ActivityOptionsCompat.makeCustomAnimation(this,R.anim.anim_alpha_enter, R.anim.anim_alpha_exit))
            simpleImageLauncher2.launch(""){
                if (it != 0){
                    iv_simple_image.setImageResource(it)
                }
            }
        }

    }



}