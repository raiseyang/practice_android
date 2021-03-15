package com.raise.practice

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.raise.practice.adapter.ButtonAdapter
import com.raise.practice.databinding.ActivityMainBinding
import com.raise.weapon_base.LLog
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        val dataSet = arrayOf(
                "初始化opencv4",
                "对比图片相似度",
                "跳转到灰度activity",
                "按钮4",
                "按钮5",
                "按钮6"
        )
    }

    /**
     * 参考
     * https://blog.csdn.net/qq_27378951/article/details/101543122
     */
    private fun clickBtn1() {
        printLog("clickBtn1() start")
        if (OpenCVLoader.initDebug()) {
            printLog("opencv init success")
        } else {
            printLog("opencv init failure")
        }
    }

    private fun clickBtn2() {
        printLog("clickBtn2() start")
        // 请提前将两张图片push到手机中
        val picture1 = BitmapFactory.decodeFile("/sdcard/p1.JPG")
        val refPic = BitmapFactory.decodeFile("/sdcard/refPic.JPG")

        val mat1 = Mat()
        val mat2 = Mat()
        val srcMat = Mat()
        val desMat = Mat()
        Utils.bitmapToMat(picture1, mat1)
        Utils.bitmapToMat(refPic, mat2)
        Imgproc.cvtColor(mat1, srcMat, Imgproc.COLOR_BGR2GRAY)
        Imgproc.cvtColor(mat2, desMat, Imgproc.COLOR_BGR2GRAY)
        srcMat.convertTo(srcMat, CvType.CV_32F)
        desMat.convertTo(desMat, CvType.CV_32F)
        val target = Imgproc.compareHist(srcMat, desMat, Imgproc.CV_COMP_CORREL)
        printLog("clickBtn1() similarity=$target")
    }

    private fun clickBtn3() {
        printLog("clickBtn3() start")
        startActivity(Intent(this, GrayPicActivity::class.java))
    }

    private fun clickBtn4() {
        printLog("clickBtn4() start")

    }

    private fun clickBtn5() {
        printLog("clickBtn5() start")

    }

    private fun clickBtn6() {
        printLog("clickBtn6() start")

    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvBtns.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            val btnAdapter = ButtonAdapter(dataSet).apply {
                mListener = object : ButtonAdapter.OnClickButton {
                    override fun onClick(index: Int) {
                        when (index + 1) {
                            1 -> clickBtn1()
                            2 -> clickBtn2()
                            3 -> clickBtn3()
                            4 -> clickBtn4()
                            5 -> clickBtn5()
                            6 -> clickBtn6()
                        }
                    }
                }
            }
            adapter = btnAdapter
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 请求读取sd卡权限
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun printLog(msg: String) {
        LLog.i(TAG, "printLog() msg=$msg")
        binding.tvContent.text = "${binding.tvContent.text}\n$msg"
        runOnUiThread {
            binding.svContent.fullScroll(View.FOCUS_DOWN)
        }
    }
}
