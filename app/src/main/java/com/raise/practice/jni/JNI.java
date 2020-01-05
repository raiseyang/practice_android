package com.raise.practice.jni;

import android.util.Log;

public class JNI {

    static {
        System.loadLibrary("hello-lib");
    }

    //native接口，c实现
    public static native String sayHello(String name);

    //public static java.lang.String sayHelloFromCPP(java.lang.String);
//    descriptor: (Ljava/lang/String;)Ljava/lang/String;
    public static String sayHelloFromCPP(String name) {
        Log.d("JNI", "name");
        return name + ",from java";
    }

}
