package com.raise.practice

import android.os.Build
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 该类的构造函数中无任何参数，所以在提供绑定时，无任何关联对象；直接@Inject即可；
 * 当Hilt需要注入时，会调用其构造函数；
 *
 * @Singleton 加了这个注解，每次提供的实例就是一个，不会新创建
 */
//@Singleton 标记一个绑定，代表单例注入
@Singleton
//@Inject 这里声明一个绑定，可在其他地方直接通过@Inject注入该单例
class DeviceUtil @Inject constructor() {

    val sdk = Build.VERSION.SDK_INT
}