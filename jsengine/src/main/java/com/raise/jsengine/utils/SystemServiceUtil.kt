package com.raise.jsengine.utils

import android.content.Context
import android.os.PowerManager
import com.raise.weapon_base.ContextVal

val context = ContextVal.getContext()

fun getPowerManager(): PowerManager =
        context.getSystemService(Context.POWER_SERVICE) as PowerManager