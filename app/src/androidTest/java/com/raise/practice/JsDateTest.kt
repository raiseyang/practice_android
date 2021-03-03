package com.raise.practice

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eclipsesource.v8.V8
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * js内建对象[Date]测试
 */
@RunWith(AndroidJUnit4::class)
class JsDateTest {

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
    fun testDate1() {
        val result = v8.executeObjectScript(
                """
                    var date = new Date();
                    date
                """.trimIndent()
        )
        //result=Thu Feb 25 2021 17:37:46 GMT+0800 (CST)
        println("result=$result")
        assertEquals("", result)
    }

    @Test
    fun testDate2() {
        // Double 1.614246350308E12
        val result = v8.executeDoubleScript(
                """
                    var date = new Date();
                    date.getTime()
                """.trimIndent()
        )
        //result=Thu Feb 25 2021 17:37:46 GMT+0800 (CST)
        println("result=$result")
        val longResult = result.toLong()
        //1614246493631
        assertEquals(1614246493631, longResult)
    }

}