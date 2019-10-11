package com.raise.practice.jsbridge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.CallBackFunction
import com.github.lzyzsd.jsbridge.DefaultHandler
import com.raise.practice.R
import kotlinx.android.synthetic.main.activity_js.*

class JsActivity : AppCompatActivity() {

    val TAG = "JsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_js)

        webView.setDefaultHandler(DefaultHandler())

        webView.loadUrl("file:///android_asset/demo.html");

        webView.registerHandler("submitFromWeb", object : BridgeHandler {
            override fun handler(data: String?, function: CallBackFunction?) {
                Log.i(TAG, "handler = submitFromWeb, data from web = " + data)
                function?.onCallBack("submitFromWeb exe, response data 中文 from Java")
            }
        })
    }
}
