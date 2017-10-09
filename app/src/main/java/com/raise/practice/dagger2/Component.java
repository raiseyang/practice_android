package com.raise.practice.dagger2;

/**
 * 4. 作为Module和Presenter构造函数的纽带
 * Created by raise.yang on 17/09/28.
 */
@dagger.Component(modules = Module.class)
public interface Component {

    void inject(DaggerContract.View view);
}
