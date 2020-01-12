package com.raise.practice

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.abupdate.common.Trace
import com.abupdate.common_ui.AbTestLayout
import com.raise.practice.launch.FileScriptLauncher
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test_layout.loadTest(TestHelper())

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    inner class TestHelper : AbTestLayout.Helper {
        override fun onClick(p0: String?) {
            Trace.d(TAG, "onClick() 按钮：$p0")
            when (p0) {
                "1" -> {
                    FileScriptLauncher().launch("/sdcard/001")
                }
                "2" -> {
                    FileScriptLauncher().launch("/sdcard/002")

                }
                "3" -> {
                    FileScriptLauncher().launch("/sdcard/003")

                }
            }
        }

        override fun getBtnTexts(): ArrayList<String> {
            return arrayListOf("1", "2", "3")
        }

        override fun print(p0: String?) {
            test_layout.printToScreen(p0)
        }

    }

    private fun checkPermission(vararg permissions: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val requestPermissions = getRequestPermissions(permissions)
            if (requestPermissions.isNotEmpty()) {
                requestPermissions(requestPermissions, 1)
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun getRequestPermissions(permissions: Array<out String>): Array<String> {
        val list = ArrayList<String>()
        for (permission in permissions) {
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                list.add(permission)
            }
        }
        return list.toTypedArray()
    }

}
