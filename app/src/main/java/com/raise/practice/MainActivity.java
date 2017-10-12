package com.raise.practice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.raise.practice.dagger2.DaggerActivity;
import com.raise.practice.iotdialog.DialogActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click_dagger2(View view) {
        DaggerActivity.startActivity(this);
    }

    public void click_dialog(View view) {
        DialogActivity.startActivity(this);
    }
}
