package com.raise.jsengine

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.raise.autorunner.WAccessibilityService
import com.raise.autorunner.filter.Filter
import com.raise.autorunner.filter.Selector
import com.raise.jsapi.IView
import com.raise.jsapi.IViewSelector
import com.raise.weapon_base.LLog

class ViewSelector : IViewSelector, IV8Inject {

    companion object {
        private const val TAG = "ViewSelector"
    }

    val selector = Selector()

    override fun injectV8(runtime: V8) {
//        V8Object(runtime).injectJSClass("viewSelector") {
//            injectMethod("text", String::class.java)
//            injectMethod("textStarts", String::class.java)
//            injectMethod("id", String::class.java)
//            injectMethod("findOne")
//            injectMethod("findAll")
//        }
//        v8ViewSelector.registerJavaMethod(this, "text", "text", arrayOf<Class<*>>(String::class.java))
//        v8ViewSelector.registerJavaMethod(this, "textStarts", "textStarts", arrayOf<Class<*>>(String::class.java))
//        v8ViewSelector.registerJavaMethod(this, "id", "id", arrayOf<Class<*>>(String::class.java))
//        v8ViewSelector.registerJavaMethod(this, "findOne", "findOne", arrayOf<Class<*>>(String::class.java))
//        v8ViewSelector.registerJavaMethod(this, "error", "error", arrayOf<Class<*>>(String::class.java))
//        // 第一个参数是js代码中调用的域
//        runtime.add("viewSelector", v8ViewSelector)
        // 注入全局方法，创建选择器
//        runtime.injectGlobalMethodWithResult("selector") {
//            createSelector()
//        }

        runtime.registerJavaMethod(this,
                "jsConstructor",
                "ViewSelector",
                arrayOf<Class<*>>(V8Object::class.java),
                true)

        val obj = runtime.getObject("ViewSelector")
        val prototype = runtime.executeObjectScript("ViewSelector.prototype")

        prototype.registerJavaMethod(this, "text", "text",
                arrayOf<Class<*>>(String::class.java))
        prototype.registerJavaMethod(this, "id", "id",
                arrayOf<Class<*>>(String::class.java))

        obj.setPrototype(prototype)

    }

    private fun createSelector(): ViewSelector = ViewSelector()

    override fun text(text: String): IViewSelector {
        LLog.d(TAG, "text() text=$text")
        selector.add(Filter.text(text))
        return this
    }

    override fun textStartsWith(text: String): IViewSelector {
        selector.add(Filter.textStartsWith(text))
        return this
    }

    override fun id(id: String): IView? {
        selector.add(Filter.id(id))
        return findOne()
    }

    override fun findOne(): IView? {
        LLog.d(TAG, "findOne() ")
        val viewNode = WAccessibilityService.instance?.rootViewNode()?.findFirst(selector)
        return viewNode?.let { View(viewNode) }
    }

    override fun findAll(): ArrayList<IView> {
        val viewNodeArray = WAccessibilityService.instance?.rootViewNode()?.findAll(selector)
        return viewNodeArray?.let { it ->
            val result = arrayListOf<IView>()
            it.forEach {
                result.add(View(it))
            }
            return@let result
        } ?: arrayListOf<IView>()
    }

    fun jsConstructor(obj: V8Object?) {}
}