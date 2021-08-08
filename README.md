# Activity Results Api 使用

## 背景
在Android 应用开发中，常用的从Activity 获取结果的方式为通过 **Intent** 携带数据，通过 **onActivityForResult** 来接收返回的数据，代码如下：
        
    //点击打开Activity 
    btn.setOnClickListener { 
        startActivityForResult(Intent(),requestCode)
    }

    //获取Activity返回的数据
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode){
                REQUEST_IMAGE_FROM_CAMERA->{
                    //从相机返回
                }
                REQUEST_IMAGE_FROM_APP_ALBUM->{
                    //从相册返回
                }
                REQUEST_IMAGE_FROM_SYSTEM_ALBUM->{
                    //从系统相册返回
                }
                else->{

                }
            }
        }
    }

需要维护非常多的 **requestCode** 和极长的 **onActivityResult** 方法。比如在应用的首界面通常需要申请
存储、相机权限以及很多功能需要先选择照片再使用功能。那么**onActivityResult** 方法会非常杂乱
<br>
<br>

## 现状
目前 **startActivityForResult()** 、**onActivityResult()** 方法都已经在activity 1.2版本被弃用<br>
需要先升级 androidx activity、Fragment版本<br>
```
    implementation 'androidx.activity:activity:1.2.0'
    implementation 'androidx.fragment:fragment:1.3.0'
```
<br>

## Activity Results Api
**Activity Results Api** 是谷歌推荐的从Activity获取数据方式<br>


Activity Results Api 中有两个重要的组件<br>
- ActivityResultContract ：协议，定义打开 **Activity** 生成所需结果的输入参数与结果的输出类型
- ActivityResultLauncher ：调用launch()方法打开**Activity**
<br>
<br>

1.定义协定
新建一个Contract类，继承ActivityResultContract<I,O>,<br>
 <font color=red>**I**</font>为定义的输入参数,如果不需要传入参数可以定义为 **Unit**、**Void** 类型<br>
 <font color=red>**O**</font>为定义的输出结果类型

```
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
```

在activity、Fragment中进行注册协议
```
class MainActivity : AppCompatActivity{

    /**
     * 注册打开样本图片Activity
     */
    val simpleImageLauncher = registerForActivityResult(SimpleImageContract()){
        //返回选择的样本图片
        if (it != 0){
            iv_simple_image.setImageResource(it)
        }
    }
}
```
点击按钮调用 launch()方法进行跳转
```
    btn_get_simple_image.setOnClickListener {
        //通过调用launch()放大打开选择样本图片activity
        simpleImageLauncher.launch("")
    }
```
如果需要增加跳转动画，可以传入ActivityOptionsCompat参数定义动画
```
    btn_get_simple_image.setOnClickListener {
        simpleImageLauncher.launch(
            "", 
             ActivityOptionsCompat.makeCustomAnimation(
            this,
            R.anim.anim_alpha_enter,
            R.anim.anim_alpha_exit))
    }
```




























