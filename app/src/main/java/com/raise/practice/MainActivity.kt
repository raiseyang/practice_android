package com.raise.practice

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun skill() {
        //1.数组和集合定义
        val names = arrayOf<String>("2", "3")
        val ages = listOf<Int>(1, 2, 3)
        //访问
        names[0]
        ages[0]
        //MutableList()和List


        //2.使用if,when表达式返回值
        val invalid = isInvalid(2)
        val age = getAge("x")

        //3.使用函数默认参数
        createUser("xx", 3)
        createUser("qq")

        //4.steam流式操作
        ages.filter { it > 3 }.map { (it * 3).toString() }.forEach { print(it) }

        //5.内部类和嵌套类
        MainInterface().fetchContext().takeIf { it == this }
        MainEntity().fetchContext().takeIf { it == null }

        //6.foreach
        ages.forEach {
            if (it > 3) {
                for (name in names) {
                    if (name == "xx") {
                        continue
                    }
                    break
                }
            }
        }
        //7.高阶函数与lambda
        AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("有新版本升级@")
                .setCancelable(true)
                .setPositiveButton("确定") { dialog, _ ->
                    //跳转页面
                    dialog.dismiss()
                }
                .create()
                .show()

        val alertDialog = showDialog {
            setTitle("提示")
            setMessage("")
            setCancelable(true)
            setPositiveButton("确定") { dialog, _ ->
                //跳转页面
                dialog.dismiss()
            }
        }
        val alertDialog1 = showDialog1 {
            it.setTitle("提示")
            it.setMessage("")
            it.setCancelable(true)
            it.setPositiveButton("确定") { dialog, _ ->
                //跳转页面
                dialog.dismiss()
            }
        }
        //8.实战1：创建jsonObject封装
        val jsonObject = JSONObject()
        jsonObject.put("key1", "value1")
        jsonObject.put("key2", "value2")

        createJson(
                "key1" to "value1",
                "key2" to 1001,
                "key2" to createJson(
                        "inner1" to ""
                )
        )
        //8.实战2：Notification封装
        val builder = NotificationCompat.Builder(this, "")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("通知")
                .setContentText("有新版本更新！")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(this).notify(1, builder.build())

    }

    private fun isInvalid(num: Int): Boolean =
            if (num > 3) {
                true
            } else if (num > 2) {
                false
            } else {
                false
            }

    private fun getAge(name: String): Int {
        return when (name) {
            "xx" -> 3
            else -> 10
        }

    }

    private fun createUser(name: String, age: Int = 18) {

    }

    //inner内部类，持有外部实例，可以调用外部方法
    inner class MainInterface {
        fun fetchContext(): Context {
            return this@MainActivity
        }
    }

    //嵌入类，只是写在一个文件里面，和另写一个文件没区别
    class MainEntity {
        fun fetchContext(): Context? {
            //报错，找不到MainActivity实例
//            return this@MainActivity
            return null
        }
    }

    private fun showDialog(block: AlertDialog.Builder.() -> Unit): AlertDialog {
        val builder = AlertDialog.Builder(this)
        block.invoke(builder)
        val alertDialog = builder.create()
        alertDialog.show()
        return alertDialog
    }

    private fun showDialog1(block: (AlertDialog.Builder) -> Unit): AlertDialog {
        val builder = AlertDialog.Builder(this)
        block.invoke(builder)
        val alertDialog = builder.create()
        alertDialog.show()
        return alertDialog
    }

    private fun <V> createJson(vararg pairs: Pair<String, V>): JSONObject =
            JSONObject().apply {
                for ((key, value) in pairs) {
                    put(key, value)
                }
            }


}
