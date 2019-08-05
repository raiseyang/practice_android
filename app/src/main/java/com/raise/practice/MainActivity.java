package com.raise.practice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.raise.practice.progressbtn.ProgressActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click_dagger2(View view) {
    }

    public void click_dialog(View view) {
    }

    public void click_progress(View view) {
        ProgressActivity.startActivity(this);
    }
}
