package com.raise.jsengine

import android.graphics.Rect
import android.os.SystemClock
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.eclipsesource.v8.V8
import com.raise.autorunner.WAccessibilityService
import com.raise.autorunner.filter.Filter
import com.raise.autorunner.filter.IFilter
import com.raise.autorunner.filter.Selector
import com.raise.jsapi.IGlobalFunc
import com.raise.weapon_base.LLog

object GlobalFunc : IGlobalFunc, IV8Inject {
    const val TAG = "GlobalFunc"
    override fun injectV8(runtime: V8) {
        // 全局方法注入
        runtime.apply {
            // js代码中可以直接使用sleep(2000)
            injectGlobalMethod("sleep") {
                only1(onError = {
                    errorParams(it)
                }) {
                    sleep((it as Int).toLong())
                }
            }

            injectGlobalMethod("toast") {
                only1(onError = {
                    errorParams(it)
                }) {
                    toast(it as String)
                }
            }
            injectGlobalMethod("click") {
                only1(onError = {
                    errorParams(it)
                }) {
                    click(it as String)
                }
            }
            injectGlobalMethod("scrollForward") {
                scrollForward()
            }
            injectGlobalMethod("scrollBackward") {
                scrollBackward()
            }
            injectGlobalMethod("scrollUp") {
                scrollUp()
            }
            injectGlobalMethod("scrollDown") {
                scrollDown()
            }
        }
    }

    override fun sleep(timeInMills: Long) {
        SystemClock.sleep(timeInMills)
    }

    override fun toast(content: String) {
        LLog.d(TAG, "toast() content=$content")
//        Toast.makeText(null, content, Toast.LENGTH_SHORT).show()
    }

    override fun click(text: String) {
        WAccessibilityService.instance?.apply {
            var viewNode = rootViewNode().findFirst(Selector.addAll(
                    Filter.text(text),
                    Filter.CLICKABLE
            ))
            if (viewNode != null) {
                viewNode.click()
            } else {
                // 兼容性的去找内容描述文字
                viewNode = rootViewNode().findFirst(Selector.addAll(
                        Filter.desc(text),
                        Filter.CLICKABLE
                ))
                if (viewNode != null) {
                    viewNode.click()
                } else {
                    LLog.d(TAG, "click() 失败")
                }
            }
        }
    }

    override fun scrollForward() {
        WAccessibilityService.instance?.apply {
            val scrollableNode = rootViewNode().findFirst(Selector.addAll(
                    Filter.SCROLLABLE,
                    Filter.view("android.widget.HorizontalScrollView")
            ))
            scrollableNode?.nodeInfo!!.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.id)
        }
    }

    override fun scrollBackward() {
        WAccessibilityService.instance?.apply {
            val scrollableNode = rootViewNode().findFirst(Selector.addAll(
                    Filter.SCROLLABLE,
//                    Filter.view("android.support.v7.widget.RecyclerView"),
                    Filter.view("android.widget.HorizontalScrollView")
            ))
            scrollableNode?.nodeInfo!!.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.id)
        }
    }

    override fun scrollUp() {
        WAccessibilityService.instance?.apply {
            val scrollableNode = rootViewNode().findFirst(Selector.addAll(
                    Filter.SCROLLABLE,
                    Filter.view("android.support.v7.widget.RecyclerView"),
                    object : IFilter {
                        override fun filter(nodeInfo: AccessibilityNodeInfoCompat): Boolean {
                            val rect = Rect()
                            nodeInfo.getBoundsInScreen(rect)
                            return rect.right > 0 && rect.bottom > 0
                        }
                    }
            ))
            //不起作用，在今日头条上
//            scrollableNode?.nodeInfo!!.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.id)
            // 使用手势模拟上划
            scrollableNode?.let {
                val rect = Rect()
                it.nodeInfo.getBoundsInScreen(rect)
                rect.scrollUp()
            }
        }
    }

    override fun scrollDown() {
        WAccessibilityService.instance?.apply {
            val scrollableNode = rootViewNode().findFirst(Selector.addAll(
                    Filter.SCROLLABLE,
                    Filter.view("android.support.v7.widget.RecyclerView"),
                    object : IFilter {
                        override fun filter(nodeInfo: AccessibilityNodeInfoCompat): Boolean {
                            val rect = Rect()
                            nodeInfo.getBoundsInScreen(rect)
                            return rect.right > 0 && rect.bottom > 0
                        }
                    }
            ))
            // 使用手势模拟上划
            scrollableNode?.let {
                val rect = Rect()
                it.nodeInfo.getBoundsInScreen(rect)
                rect.scrollDown()
            }
        }
    }

    private fun errorParams(error: String) {
        LLog.e("GlobalFunc", "errorParams() $error")
    }
}