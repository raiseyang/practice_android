//
// Created by raise on 21/09/14.
//
#include <jni.h>

// JNI方法
jstring getStaticName(JNIEnv *env, jobject jobject1, jstring name) {
    return name;
}

// 静态方法数组   第一个参数是java方法名；；；   第二个参数是java方法的参数；；  第三个参数是JNI方法的指针；；；
JNINativeMethod nativeMethod[] = {
        {"getStaticName", "(Ljava/lang/String;)Ljava/lang/String;", (void *) getStaticName}
};

jint registerNative(JNIEnv *env) {
    // 获取类
    jclass clazz = (*env)->FindClass(env, "com/raise/practice/jni/JNI");
    // 注册；；；通过类注册上去，可以看出JAVA端getStaticName()是static方法
    if (((*env)->RegisterNatives(env, clazz, nativeMethod, 1)) != JNI_OK) {
        return JNI_ERR;
    }
    return JNI_OK;
}

//JNI加载的入口
jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    // 获取ENV
    if (((*vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_4)) != JNI_OK) {
        return JNI_ERR;
    }
    if (registerNative(env) != JNI_OK) {
        return JNI_ERR;
    }

    return JNI_VERSION_1_4;
}