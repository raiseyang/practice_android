package com.raise.practice.dagger2;

import com.raise.trace.Trace;

import javax.inject.Inject;

/**
 * Created by raise.yang on 17/09/28.
 */

public class DaggerPresenter implements DaggerContract.Presenter {

    private final String TAG = "DaggerPresenter";

    private DaggerContract.View m_view;

    //2.声明需要实例化的构造方法
    @Inject
    public DaggerPresenter(DaggerContract.View view) {
        m_view = view;
    }

    @Override
    public void loadData() {
        m_view.showLoading();
        Trace.d(TAG, "loadData() ");
    }
}
