package com.raise.practice

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raise.practice.databinding.ActivityGrayPicBinding
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class GrayPicActivity : AppCompatActivity() {


    private lateinit var binding: ActivityGrayPicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrayPicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivSrc.setImageBitmap(BitmapFactory.decodeFile("/sdcard/p1.JPG"))

        binding.btnT1.setOnClickListener {
            // 读取原图
            val picture1 = BitmapFactory.decodeFile("/sdcard/p1.JPG")
            // 读取出图片数据
            val srcMat = Mat()
            val grayMat = Mat()
            Utils.bitmapToMat(picture1, srcMat)
            // 转换为灰度图片数据
            Imgproc.cvtColor(srcMat, grayMat, Imgproc.COLOR_BGR2GRAY)
            // 图片数据转换成图片
            val createBitmap = Bitmap.createBitmap(picture1.width, picture1.height, Bitmap.Config.ARGB_8888)
            Utils.matToBitmap(grayMat, createBitmap)
            // 显示灰度图片
            binding.ivGray.setImageBitmap(createBitmap)
        }
    }
}