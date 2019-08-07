package com.abupdate.javapoet;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.lang.model.element.Modifier;

public class Poet {

    public static void main(String... args) {
        try {
            generateHelloWorld();
//            Class<?> aClass = Class.forName("com.example.helloworld.HelloWorld");
//            System.out.println(aClass.getCanonicalName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateHelloWorld() throws IOException {
        MethodSpec main = MethodSpec.methodBuilder("main") //main代表方法名
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)//Modifier 修饰的关键字
                .addParameter(String[].class, "args") //添加string[]类型的名为args的参数
                .addStatement("$T.out.println($S)", System.class, "Hello World")//添加代码，这里$T和$S后面会讲，这里其实就是添加了System,out.println("Hello World");
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")//HelloWorld是类名
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                .addMethod(main)  //在类中添加方法
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", typeSpec)
                .build();

        javaFile.writeTo(System.out);
    }
}
