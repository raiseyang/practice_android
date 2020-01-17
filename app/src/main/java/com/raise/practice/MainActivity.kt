package com.raise.practice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eclipsesource.v8.V8
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.abupdate.common.Trace
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_1.setOnClickListener {
            Trace.d("cc", "点击btn1")
            helloWorld()
        }
    }

    private fun helloWorld() {
        val runtime = V8.createV8Runtime()
        val result = runtime.executeIntegerScript(""
                + "var hello = 'hello, ';\n"
                + "var world = 'world!';\n"
                + "hello.concat(world).length;\n")
        Trace.d("cc", "result=$result")
        runtime.release(false)
    }

}
