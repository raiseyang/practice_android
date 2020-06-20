# 最小启动APK大小实践

## 使用AS创建新项目
1.默认生成APK大小：2.2M
![](pic/1.png)

很明显，androidx相关库的代码占用1.5M；考虑去掉相关依赖；

2.去除androidx库后的APK大小：1.2M
```
ConstraintLayout使用LinearLayout代替
Theme.AppCompat.Light.DarkActionBar使用android:Theme.Black.NoTitleBar代替
AppCompatActivity使用Activity代替
```
![](pic/2.png)
kotlin相关库占用大约1M；也要去除；

3.去除kotlin相关库后的APK大小：90.4K
```
去除kotlin相关gradle插件，依赖
MainActivity.kt替换为MainActivity.java
```
![](pic/3.png)
如图，资源文件目录res占用93%大小，可以全部去除；

4.去除res资源文件目录后的APK大小：7.7K
```
直接删除res目录下所有文件
setContentView(new View(this));
```
![](pic/4.png)
dex文件内，看到BuildConfig也打包到里面，可以去除；

5.去除BuildConfig后的APK大小：7.6K
```
// 在build.gradle中配置
android.applicationVariants.all { variant ->
    variant.generateBuildConfig.enabled = false
}
```
![](pic/5.png)
最后是清单文件内容，可以想办法优化；

6.优化清单文件后的APK大小：7.9K（仅供参考，主要关注APK内文件大小）
```
//将Activity申明简化，后续使用adb命令启动
//注意一定要配置android:exported="true"，不然adb不能启动界面
    <application>
        <activity
            android:exported="true"
            android:name=".MainActivity" />
    </application>
```
![](pic/6.png)

7.图6已经快接近极限，AS编程生成APK大小最小就8K左右；

## 使用Build Tools工具编译生成APK

根据上一小节经验，我们将app目录下的资源单独拷贝出来，用作源码，使用安卓的SDK构建工具集，就可以编译生成APK；
> 这个APK也包含四个部分：
> 1. resources.arsc
> 2. classes.dex
> 3. AndroidManifest.xml
> 4. META-INF

1. 编译资源文件，生成resources.arsc
`aapt2 compile --dir app/src/main/res/ -o package/res.zip`
- --dir 指定资源路径
- -o 指定输出路径

编译完成后，会在package文件夹下看到res.zip这个文件；可以尝试解压看看里面内容；

2. 连接manifest.xml文件，生成R文件，生成初步apk文件
`aapt2 link package/res.zip -I D:/android-sdk-windows/platforms/android-29/android.jar --java package/ --manifest app/src/main/AndroidManifest.xml -o package/res.apk`
- -I：必要参数，指定 android.jar 目录，因为 xml 中可能使用到了例如 android:id 等自带的 android 命名空间
- o：指定输出 apk 路径
- —java：指定生成的 R 文件的路径
- —manifest：必要参数，Manifest 文件中包含了 app 的包名和 application id

初步apk文件内容包含resources.arsc和AndroidManifest.xml

3. 编译 .java 文件为 .class 文件,在转换成.dex文件；生成classes.dex
`javac -encoding utf-8 -target 1.8 -bootclasspath D:/android-sdk-windows/platforms/android-29/android.jar app/src/main/java/com/raise/practice/*.java package/com/raise/practice/R.java -d package/`
`d8 package/com/raise/practice/*.class --classpath D:/android-sdk-windows/platforms/android-29/android.jar --output ./`

4. 将dex文件添加进apk
`aapt add package/res.apk classes.dex`

5. 对齐apk
`zipalign 4 package/res.apk package/app-unsigned-aligned.apk`

6. 签名
`apksigner sign --ks key.jks --out package/app-release.apk package/app-unsigned-aligned.apk`


参考：
[APK 的前世今生：从 Android 源码到 apk 的编译打包流程](http://www.manongjc.com/article/59926.html)