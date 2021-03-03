package com.raise.practice

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eclipsesource.v8.V8
import com.raise.jsengine.GlobalFunc
import com.raise.jsengine.ViewSelector
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class V8Test {

    private lateinit var v8: V8

    @Before
    fun seutp() {
        v8 = V8.createV8Runtime()
    }

    @After
    fun tearDown() {
        try {
            v8.close()
            check(V8.getActiveRuntimes() == 0) { "V8Runtimes not properly released" }
        } catch (e: IllegalStateException) {
            println(e.message)
        }
    }

    @Test
    fun testXXX() {
        GlobalFunc.injectV8(v8)
        ViewSelector().injectV8(v8)
        v8.executeVoidScript(
                """
                    var vs1 = new ViewSelector();
                    var vs2 = vs1.hello();
                    var vs3 = vs1.hello2("world xxx");
                    var vs4 = vs1.hello3("world xxx");
                    
                    var vs5 = vs1.hello4("world xxx");
                    toast("运行成功" + vs5["name"]);
                    
                    vs1.hello3("world xxx 2");
                    var vs6 = vs1.hello5("world xxx");
                    toast("运行成功" + vs6.hello3("new obj"));

                    vs1.hello3("world xxx 3");
//                    vs1.findOne();
                """.trimIndent()
        )
//        assertTrue(result == )
    }


    fun testObjectMethod() {

    }

    @Test
    fun testHelloWorld() {
        val result = v8.executeIntegerScript(""
                + "var hello = 'hello, ';\n"
                + "var world = 'world!';\n"
                + "hello.concat(world).length;\n")
        assertEquals(13, result)
    }

}