package com.raise.practice

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abupdate.common.Trace
import com.abupdate.common_ui.AbToast
import com.eclipsesource.v8.*
import com.eclipsesource.v8.utils.V8Executor
import kotlinx.android.synthetic.main.activity_main.*

import androidx.recyclerview.widget.GridLayoutManager
import com.raise.practice.adapter.ButtonAdapter
import com.raise.practice.databinding.ActivityMainBinding
import com.raise.weapon_base.LLog

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        val dataSet = arrayOf(
                "按钮1",
                "按钮2",
                "按钮3",
                "按钮4",
                "按钮5",
                "按钮6"
        )
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
    }

    private fun clickBtn1() {
        printLog("clickBtn1() start")
    }

    private fun clickBtn2() {
        printLog("clickBtn2() start")
    }

    private fun clickBtn3() {
        printLog("clickBtn3() start")
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


    @SuppressLint("SetTextI18n")
    private fun printLog(msg: String) {
        LLog.i(TAG, "printLog() msg=$msg")
        binding.tvContent.text = "${binding.tvContent.text}\n$msg"
        runOnUiThread {
            binding.svContent.fullScroll(View.FOCUS_DOWN)
        }
    }

    /**
     * 执行js代码，返回int数据
     */
    private fun helloWorld() {
        val runtime = V8.createV8Runtime()
        val result = runtime.executeIntegerScript(""
                + "var hello = 'hello, ';\n"
                + "var world = 'world!';\n"
                + "hello.concat(world).length;\n")
        Trace.d("MainActivity", "result=$result")
        runtime.release(false)
    }

    private fun callJsObject() {
        val runtime = V8.createV8Runtime()
        runtime.executeVoidScript(""
                + "var person = {};\n"
                + "var hockeyTeam = {name : 'WolfPack'};\n"
                + "person.first = 'Ian';\n"
                + "person['last'] = 'Bull';\n"
                + "person.hockeyTeam = hockeyTeam;\n")

        val person = runtime.getObject("person")
        val hockeyTeam = person.getObject("hockeyTeam")
//        person.getType()
//        person.keys

        Trace.d("MainActivity", hockeyTeam.getString("name"))
        person.release()
        hockeyTeam.release()
        runtime.release()
    }

    private fun callJsMethod() {
        val runtime = V8.createV8Runtime()
        //注意JS直接使用players.length访问数组仓库，博客示例代码有问题
        runtime.executeVoidScript("""
            var hockeyTeam = {
     name      : 'WolfPack',
     players   : [],
     addPlayer : function(player) {
                   this.players.push(player);
                   return this.players.length;
     }
}
        """.trimIndent())

        val hockeyTeam = runtime.getObject("hockeyTeam")

        val player1 = V8Object(runtime).add("name", "John")
        val player2 = V8Object(runtime).add("name", "Chris")
        val player3 = V8Object(runtime).add("name", "Raise")
        val players = V8Array(runtime).push(player1).push(player2)
        hockeyTeam.add("players", players)

        val type = hockeyTeam.getType("players")
        Trace.d("MainActivity", "players type=$type")

        val parameters = V8Array(runtime).push(player3)
        val size = hockeyTeam.executeIntegerFunction("addPlayer", parameters)
        Trace.d("MainActivity", "size=$size") // 输出3
        parameters.release()
    }

    /**
     * js调用java方法
     * receiver 作为call()第一个参数传入
     * 也可以直接调用toast("Hello world!")
     */
    private fun callJavaCallback() {
        val callback = JavaVoidCallback { receiver, parameters ->
            Trace.d(TAG, "receiver=$receiver")
            if (parameters.length() > 0) {
                val arg1 = parameters.get(0)
                toast(arg1 as String)
                if (arg1 is Releasable) {
                    arg1.release()
                }
            }
        }
        val runtime = V8.createV8Runtime()
        runtime.registerJavaMethod(callback, "toast")
        runtime.executeScript("toast.call({x:'x'},'hello, world');")
    }

    fun callThread() {
        val executor = V8Executor("1")
        executor.start()
        executor.join()
        val result = executor.result
    }

    fun toast(msg: String) {
        AbToast.show(msg)
    }
}
