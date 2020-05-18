package com.raise.practice

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abupdate.common_ui.FloatView

class MainActivity : AppCompatActivity() {

    val floatWindow = FloatWindow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun clickBtn1(view: View) {
        floatWindow.showView(this, TestViewGroup(this))
    }

    fun clickBtn2(view: View) {
        floatWindow.hideView()
    }

    fun clickBtn3(view: View) {
        val floatView = FloatView(this)
        floatView.view = TestViewGroup(this)
        floatView.show()

    }

}
