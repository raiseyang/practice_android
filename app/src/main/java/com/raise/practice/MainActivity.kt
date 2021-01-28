package com.raise.practice

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.eclipsesource.v8.*
import com.eclipsesource.v8.utils.V8Executor
import com.raise.jsengine.GlobalFunc
import com.raise.practice.adapter.ButtonAdapter
import com.raise.practice.databinding.ActivityMainBinding
import com.raise.weapon_base.LLog


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        val dataSet = arrayOf(
                "按钮1",
                "java调用JS",
                "JS调用java",
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
        callJsMethod()
    }

    private fun clickBtn3() {
        printLog("clickBtn3() start")
        callJavaCallback()
    }

    private fun clickBtn4() {
        printLog("clickBtn4() start")
        registerConsoleApi()
    }

    private fun clickBtn5() {
        printLog("clickBtn5() start")
        registerConsoleApi2()
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
        LLog.d("MainActivity", "result=$result")
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

        LLog.d("MainActivity", hockeyTeam.getString("name"))
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
        LLog.d("MainActivity", "players type=$type")

        val parameters = V8Array(runtime).push(player3)
        val size = hockeyTeam.executeIntegerFunction("addPlayer", parameters)
        LLog.d("MainActivity", "size=$size") // 输出3
        parameters.release()
    }

    /**
     * js调用java方法
     * receiver 作为call()第一个参数传入
     * 也可以直接调用toast("Hello world!")
     */
    private fun callJavaCallback() {
        val callback = JavaVoidCallback { receiver, parameters ->
//            val receiverP1Value = receiver.getString("p1")
//            LLog.d(TAG, "receiver=$receiver,p1=$receiverP1Value")
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
//        runtime.executeScript("toast.call({p1:'ppp'},'hello, world');")
        runtime.executeScript("toast('hello, world');")
    }

    private fun registerConsoleApi() {
        val v8 = V8.createV8Runtime()
        val console = Console()
        val v8Console = V8Object(v8)
        v8Console.registerJavaMethod(console, "log", "log", arrayOf<Class<*>>(String::class.java))
        v8Console.registerJavaMethod(console, "error", "error", arrayOf<Class<*>>(String::class.java))
        v8.add("console2222", v8Console)
        v8Console.release()
        v8.executeScript("console2222.log('hello, world');")
        v8.release()
    }

    // 自己写的api
    private fun registerConsoleApi2() {
        val v8 = V8.createV8Runtime()
        com.raise.jsengine.Console.injectV8(v8)
        GlobalFunc.injectV8(v8)
        v8.executeScript("""
            console.log('hello, world');
            toast("hello, toast content");
            sleep(2000);
            console.log('hello, world  2s');
        """.trimIndent())
    }

    fun callThread() {
        val executor = V8Executor("1")
        executor.start()
        executor.join()
        val result = executor.result
    }

    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    class Console {
        fun log(message: String) {
            LLog.i("Test INFO", "[INFO] $message")
        }

        fun error(message: String) {
            LLog.e("Test ERROR", "[ERROR] $message")
        }
    }
}
