package com.raise.practice.dagger2;

import dagger.Provides;

/**
 * 3.因为Presenter构造函数需要提供View对象做初始化，该Module作为提供者
 * Created by raise.yang on 17/09/28.
 */
@dagger.Module
public class Module {
    private DaggerContract.View m_view;

    public Module(DaggerContract.View view) {
        m_view = view;
    }

    @Provides
    public DaggerContract.View provideModule() {
        return m_view;
    }
}
