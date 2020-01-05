# ndk分支

java调用c
1.编写native函数接口；
2.编译生成.class文件；
3.生成.h头文件：`javah com.raise.practice.jni.JNI`
4.拷贝到/cpp下，生成.c,写具体实现；
5.配置CMakeLists.txt文件
6.配置gradle脚本
7.在app层load,并调用native方法；

c调用java
1.编写static方法
2.编辑后生成签名：`javap -p -s JNI.class`
3.
```
    //找类
    jclass cls = (*env)->FindClass(env, "com/raise/practice/jni/JNI");
    //找方法
    jmethodID jmethodId = (*env)->GetStaticMethodID(env, cls, (const char *) "sayHelloFromCPP",
                                                    "(Ljava/lang/String;)Ljava/lang/String;");
    //调用方法
    jstring jstring1 = (*env)->CallStaticObjectMethod(env, cls, jmethodId, NULL);
```
