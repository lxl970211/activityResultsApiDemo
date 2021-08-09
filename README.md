# Activity Results Api 使用

## 背景
在Android 应用开发中，常用的从Activity 获取结果的方式为通过 **Intent** 携带数据，通过 **onActivityForResult** 来接收返回的数据，代码如下：
        
    //点击打开Activity 
    btn.setOnClickListener { 
        startActivityForResult(intent, requestCode)
    }

    //获取Activity返回的数据
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode){
                REQUEST_IMAGE_FROM_CAMERA->{
                    //从相机获取照片
                }
                REQUEST_IMAGE_FROM_APP_ALBUM->{
                    //从APP相册获取照片
                }
                REQUEST_IMAGE_FROM_SYSTEM_ALBUM->{
                    //从系统相册获取照片
                }
                // 更多。。。。
                else->{

                }
            }
        }
    }

需要维护非常多的 **requestCode** 和极长的 **onActivityResult** 方法。比如在应用的首界面通常需要申请
**存储权限**、**相机权限**以及很多功能需要先选择照片再使用功能。那么**onActivityResult** 方法会非常杂乱
<br>
<br>

## 现状
目前 **startActivityForResult()** 、**onActivityResult()** 方法都已经在 **androidx activity 1.2** 版本被弃用<br>
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

1. 定义协定
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

2. 在**activity、Fragment**中进行注册协议
```
class MainActivity : AppCompatActivity{

    /**
     * 注册打开样本图片Activity
     */
    val simpleImageLauncher = registerForActivityResult(SimpleImageContract()){ simpleImageResId->
        //返回选择的样本图片
        if (simpleImageResId != 0){
            iv_simple_image.setImageResource(simpleImageResId)
        }
    }
}
```
3. 点击按钮调用 launch()方法进行跳转
```
    btn_get_simple_image.setOnClickListener {
        //通过调用launch()放大打开选择样本图片activity
        simpleImageLauncher.launch("")
    }
```
如果需要增加跳转动画，可以调用**launch**的重载方法传入ActivityOptionsCompat定义动画
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
<br>
点击调用launch()方法和返回结果的代码是分开的，如果需要打开的页面非常多，每次都需要点击调用的位置回到注册协议的地方去查看其实也是不太方便。所以可以进行一下简单的封装，让调用更加舒适。代码如下：

```
open class BaseActivityResultLauncher<I, O>(
    caller: ActivityResultCaller,
    contract: ActivityResultContract<I, O>
) {

    private val launcher: ActivityResultLauncher<I>
    private var callback: ActivityResultCallback<O>? = null

    init {
        类初始化时对协议进行注册
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
```

依然以选择样本图协议为例，创建**SimpleImageLauncher.kt**, 继承**BaseActivityResultLauncher** 并传入之前创建的**SimpleImageContract**类
```
class SimpleImageLauncher(
    caller: ActivityResultCaller
) : BaseActivityResultLauncher<String, Int>(caller, SimpleImageContract())
```
在**activity**中就简化了其初始化,调用的地方直接获取返回结果使用更佳舒适
```
class MainActivity : AppCompatActivity() {

    //注册选择样本图协议
    val simpleImageLauncher2 = SimpleImageLauncher(this)

    btn_get_simple_image.setOnClickListener {
        simpleImageLauncher2.launch("") { simpleImageResId ->
            if (simpleImageResId != 0) {
                iv_simple_image.setImageResource(simpleImageResId)
            }
        }
    }
}
```
Activity Results Api还可以在单独的类中接收 **activity**返回的结果<br>
**CompomentActivity**和**Fragment** 类通过实现**ActivityResultCaller**接口来允许调用**registerForActivityResult()** Api，但也可以直接使用 **ActivityResultRegistry** 在未实现 **ActivityResultCaller** 的单独类中接收 **activity** 结果。 

需要实现 **LifecycleObserver** ，用于协议的注册与启动：
```
class SimpleImageLifecycleObserver(private val registry : ActivityResultRegistry) : LifecycleObserver {

    lateinit var getSimpleImage : ActivityResultLauncher<String>

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
        getSimpleImage = registry.register("key",  SimpleImageContract()){
            //获取到返回的样本图片
        }
    }

    /**
    *调用启动activity
    *
    **/
    fun selectSimpleImage(inputData : String){
        getSimpleImage.launch(inputData)
    }
}
class MainActivity : AppCompatActivity() {

    lateinit var getSimpleImageLifecycleObserver: SimpleImageLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        // ...

        //进行初始化注册协议
        getSimpleImageLifecycleObserver = SimpleImageLifecycleObserver(activityResultRegistry)
        lifecycle.addObserver(getSimpleImageLifecycleObserver)


        btn_get_simple_image.setOnClickListener {
            //在单独的类中获取样本图片
            getSimpleImageLifecycleObserver.selectSimpleImage("")
        }
    }

}
```







































