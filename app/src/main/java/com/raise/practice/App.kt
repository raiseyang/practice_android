package com.raise.practice

import android.app.Application
import android.content.Context
import com.abupdate.common.CommonVal
import kotlin.properties.Delegates

/**
 * Created by raise.yang on 17/09/28.
 */

class App : Application() {

    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        CommonVal.setContext(this)
    }

}
