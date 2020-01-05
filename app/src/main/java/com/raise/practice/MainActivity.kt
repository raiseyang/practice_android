package com.raise.practice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.raise.practice.jni.JNI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_1.setOnClickListener {
            Log.d("MainActivity", JNI.sayHello("name"))
        }
    }

}
