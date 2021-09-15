package com.raise;

/**
 * libjni_test.so 是自己通过ndk-build编译的
 */
public class Hello {
    static {
        System.loadLibrary("jni_test");
    }

    public static native String sayHello(String name);
}