package com.abupdate.compile;

import com.abupdate.annotation.AMainThread;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

//@AutoService(Processor.class)
public class AProcessor extends AbstractProcessor {

    private Filer mFiler;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        System.out.println("----------------process----------------");
        for (TypeElement element : annotations) {
            System.out.println("element=" + element);
            System.out.println("element.getQualifiedName()=" + element.getQualifiedName().toString());

            if (element.getQualifiedName().toString().equals(AMainThread.class.getCanonicalName())) {
                // 创建main方法
                MethodSpec main = MethodSpec.methodBuilder("main")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                        .build();
                // 创建HelloWorld类
                TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addMethod(main)
                        .build();

                try {
                    JavaFile javaFile = JavaFile.builder("com.abupdate", helloWorld)
                            .addFileComment(" This codes are generated automatically. Do not modify!")
                            .build();
                    //　生成文件
//                    javaFile.writeTo(System.out);
                    javaFile.writeTo(mFiler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 需要处理的注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        System.out.println("----------------getSupportedAnnotationTypes----------------");
        Set<String> annos = new LinkedHashSet<>();
        annos.add(AMainThread.class.getCanonicalName());
        return annos;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        System.out.println("----------------init----------------");
//        throw new NullPointerException("init ..............----------");
        mFiler = processingEnvironment.getFiler();
    }
}
