package com.energysh.activityapidemo.kotlin

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.energysh.activityapidemo.R
import com.energysh.activityapidemo.kotlin.contracts.SimpleImageContract
import com.energysh.activityapidemo.kotlin.contracts.SimpleImageLauncher
import com.energysh.activityapidemo.kotlin.contracts.SimpleImageLifecycleObserver
import com.energysh.activityapidemo.kotlin.ui.prevContract.PrevStartActivityForResultActivity
import com.energysh.activityapidemo.kotlin.ui.prevContract.PrevTakePictureActivity
import com.energysh.activityapidemo.kotlin.ui.prevContract.PrevCaptureVideoActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity Results Api示例Activity
 */
class MainActivity : AppCompatActivity() {

    /**
     * 注册打开样本图片Activity
     */
    val simpleImageLauncher = registerForActivityResult(SimpleImageContract()) { simpleImageResId ->
        //返回选择的样本图片
        if (simpleImageResId != 0) {
            iv_simple_image.setImageResource(simpleImageResId)
        }
    }

    val simpleImageLauncher2 = SimpleImageLauncher(this)


    lateinit var getSimpleImageLifecycleObserver: SimpleImageLifecycleObserver


    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it) {
            Toast.makeText(this, "获取成功", Toast.LENGTH_LONG).show()
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSimpleImageLifecycleObserver = SimpleImageLifecycleObserver(activityResultRegistry)
        lifecycle.addObserver(getSimpleImageLifecycleObserver)

        btn_get_simple_image.setOnClickListener {
//            通过调用launch()放大打开选择样本图片activity
            simpleImageLauncher.launch("")

//            如果需要设置打开动画 可以传入 ActivityOptionsCompat参数定义自定义动画
//            simpleImageLauncher.launch("", ActivityOptionsCompat.makeCustomAnimation(this,R.anim.anim_alpha_enter, R.anim.anim_alpha_exit))

//            simpleImageLauncher2.launch("") { simpleImageResId ->
//                if (simpleImageResId != 0) {
//                    iv_simple_image.setImageResource(simpleImageResId)
//                }
//            }

            //在单独的类中获取样本图片
//            getSimpleImageLifecycleObserver.selectSimpleImage("")


        }

        //预定义的从activity获取结果
        btn_start_activity_for_result.setOnClickListener {
            startActivity(Intent(this, PrevStartActivityForResultActivity::class.java))
        }


        //获取权限
        btn_request_permission.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        //拍摄照片返回bitmap
        btn_take_picture.setOnClickListener {
            startActivity(Intent(this, PrevTakePictureActivity::class.java))
        }

        //拍摄视频
        btn_capture_video.setOnClickListener {
            startActivity(Intent(this, PrevCaptureVideoActivity::class.java))
        }


    }


}