package com.raise.practice;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.abupdate.annotation.AMainThread;
import com.raise.practice.coroutines.CoroutineActivity;
import com.raise.practice.progressbtn.ProgressActivity;

@AMainThread
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @AMainThread
    public void click_dagger2(View view) {

    }

    public void click_dialog(View view) {
    }

    public void click_progress(View view) {
        ProgressActivity.startActivity(this);
    }

    public void click_coroutines(View view) {
        Intent intent = new Intent(this, CoroutineActivity.class);
        startActivity(intent);
    }
}
