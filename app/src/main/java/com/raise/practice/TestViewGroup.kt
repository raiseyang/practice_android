package com.raise.practice

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.abupdate.common.Trace
import kotlinx.android.synthetic.main.test_view_group.view.*
import kotlin.random.Random

/**
 * Created by raise.yang on 20/05/15.
 */
class TestViewGroup(context: Context, attributeSet: AttributeSet? = null) : LinearLayout(context, attributeSet) {

    init {
        LayoutInflater.from(context).inflate(R.layout.test_view_group, this)
    }

    private val random = Random(1)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        btn_1.setOnClickListener {
            Trace.d("TestViewGroup", "onAttachedToWindow() click 1")
            text_t1.text = "随机数字:${random.nextInt()}"
        }
    }

}