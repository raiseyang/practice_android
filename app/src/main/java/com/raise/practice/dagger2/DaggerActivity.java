package com.raise.practice.dagger2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.raise.practice.R;

import javax.inject.Inject;

/**
 * http://www.jianshu.com/p/39d1df6c877d
 */
public class DaggerActivity extends AppCompatActivity implements DaggerContract.View {

    //1.声明需要注入的对象 不能为private
    @Inject
    DaggerContract.Presenter m_presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);

//        m_presenter = new DaggerPresenter(this);
        DaggerComponent
                .builder()
                .module(new Module(this))
                .build()
                .inject(this);
        m_presenter.loadData();
    }

    @Override
    public void showLoading() {

    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, DaggerActivity.class));
    }
}
