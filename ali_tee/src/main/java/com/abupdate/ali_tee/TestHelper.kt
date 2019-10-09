package com.abupdate.ali_tee

import com.abupdate.common.Trace
import com.abupdate.common_ui.AbTestLayout
import com.linktee.teecap.JniTEE


/**
 * Created by raise.yang on 19/09/29.
 */
class TestHelper(val layout: AbTestLayout) : AbTestLayout.Helper {

    companion object {
        const val TAG = "TestHelper"
        const val BTN_TEXT_1 = "测试1"
        const val BTN_TEXT_2 = "测试2"
        const val BTN_TEXT_3 = "测试3"
        const val BTN_TEXT_4= "测试4"

        const val PRODUCT_KEY = "a11aP8rEonF"
        const val PRODUCT_SECRET = "i11YHIaE2ctppzvY7VL9472BD1E0EE99"
    }

    override fun getBtnTexts(): ArrayList<String> {
        return mutableListOf<String>(BTN_TEXT_1, BTN_TEXT_2, BTN_TEXT_3, BTN_TEXT_4) as ArrayList<String>
    }

    override fun onClick(s: String) {
        print("onClick() $s")
        when (s) {
            BTN_TEXT_1 -> {
                val socket_path = MainActivity.CTX.filesDir.absolutePath.toByteArray()
                val tst_path = MainActivity.CTX.filesDir.absolutePath + "/tst"
                val ret = JniTEE.config(socket_path, tst_path.toByteArray())
                Trace.i(TAG, "onClick() TEE Init Config Result=$ret")
            }
            BTN_TEXT_2 -> {
                val register = JniTEE.register(PRODUCT_KEY, PRODUCT_SECRET)
                print("onClick() register=$register")
            }
            BTN_TEXT_3 -> {
                print("onClick() sstInit start..")
                JniTEE.sstInit()

                print("onClick() sstAddItem start..")
                val result = JniTEE.sstAddItem("testFile",
                        "我是一个测试数据，写在TEE里面，麻烦看到之后，告诉我一声：15206120672".toByteArray(), true)
                print("onClick() sstAddItem [result=$result]end..")


                print("onClick() sstGetItem start..")
                val result2 = JniTEE.sstGetItem("testFile")
                print("onClick() sstGetItem [result=${String(result2)}]end..")

                print("onClick() sstDestroy start..")
                JniTEE.sstDestroy()
            }
            BTN_TEXT_4 ->{
                JniTEE.sstInit()

                val result2 = JniTEE.sstGetItem("testFile")
                print("onClick() sstGetItem [result=${String(result2)}]end..")

                val result = JniTEE.sstAddItem("testFile",
                        "2222222222222222".toByteArray(), true)
                print("onClick() sstAddItem [result=$result]end..")

                val result3 = JniTEE.sstGetItem("testFile")
                print("onClick() sstGetItem [result=${String(result3)}]end..")



            }
        }

    }

    override fun print(s: String) {
        layout.printToScreen(s)
    }
}
