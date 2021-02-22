package com.raise.jsengine

import com.raise.autorunner.viewnode.ViewNode
import com.raise.jsapi.IView

class View(private val viewNode: ViewNode) : IView {
    override fun click() {
        viewNode.click()
    }

    override fun longClick() {
        viewNode.longClick()
    }
}