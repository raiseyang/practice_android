package com.raise.jsengine

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.raise.autorunner.WAccessibilityService
import com.raise.autorunner.filter.Filter
import com.raise.autorunner.filter.Selector
import com.raise.jsapi.IViewSelector
import com.raise.weapon_base.LLog

class ViewSelector : IViewSelector, IV8MutilInstanceInject {

    companion object {
        private const val TAG = "ViewSelector"
    }

    val selector = Selector()

    override fun injectV8(runtime: V8) {
        runtime.injectConstructor(this, "ViewSelector")
        runtime.injectProtoTypeMethod("ViewSelector") {
            injectMethod(
                    this@ViewSelector,
                    "text" to arrayOf(String::class.java),
                    "id" to arrayOf(String::class.java),
            )
            injectMethodIncludeReceiver(
                    this@ViewSelector,
            )
        }
    }

    override fun text(text: String): Any {
        LLog.d(TAG, "text() text=$text")
        selector.add(Filter.text(text))

//        val x = v8Object.runtime.executeObjectScript("""
//            var x = new ViewSelector(); x
//        """.trimIndent())
//        return v8o2
//        return x
        return this
    }

    override fun textStartsWith(text: String): Any {
        selector.add(Filter.textStartsWith(text))
        return this
    }

    override fun id(id: String): Any? {
        LLog.d(TAG, "id() id=$id")
        selector.add(Filter.id(id))
        return findOne()
    }

    override fun findOne(): Any? {
        LLog.d(TAG, "findOne() ")
        val viewNode = WAccessibilityService.instance?.rootViewNode()?.findFirst(selector)
        return viewNode?.let { View(viewNode) }
    }

    override fun findAll(): ArrayList<Any> {
        val viewNodeArray = WAccessibilityService.instance?.rootViewNode()?.findAll(selector)
//        return viewNodeArray?.let { it ->
//            val result = arrayListOf<IView>()
//            it.forEach {
//                result.add(View(it))
//            }
//            return@let result
//        } ?: arrayListOf<IView>()
        return arrayListOf<Any>()
    }

    override fun toString(): String {
        return """
            ViewSelector[$selector]
        """.trimIndent()
    }

    override fun jsConstructor(obj: V8Object) {

    }
}