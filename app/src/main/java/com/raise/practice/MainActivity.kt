package com.raise.practice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abupdate.common.Trace
import com.abupdate.common_ui.AbToast
import com.eclipsesource.v8.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_1.setOnClickListener {
            Trace.d("cc", "点击btn1")
//            helloWorld()
//            callJsObject()
//            callJsMethod()
            callJavaCallback()
        }
    }

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

    private fun callJavaCallback() {
        val callback = JavaVoidCallback { receiver, parameters ->
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
        runtime.executeScript("toast('hello, world');")
    }

    fun toast(msg: String) {
        AbToast.show(msg)
    }
}
