package com.raise.jsengine

import com.eclipsesource.v8.V8

interface IV8Inject {

    fun injectV8(runtime: V8)

}