package com.raise.practice.dialog

import android.app.AlertDialog
import android.app.Dialog
import androidx.fragment.app.FragmentActivity
import javax.inject.Inject

/**
 * 可绑定在activity上的一个dialog对象
 *
 * IDialog在Activity容器中，他的实现也会在该容器中；
 * Activity容器预装载了activity；实例中构造函数的activity会自动注入
 * 可使用@ActivityContext获取 activity的 Context
 */
class ADialog @Inject constructor(
    val activity: FragmentActivity
) : IDialog {

    var dialog: Dialog? = null

    override fun showLoading() {
        dialog?.show() ?: AlertDialog.Builder(activity).apply {
            setMessage("正在加载中")
            setPositiveButton("确定") { dialog, _ ->
                dialog.cancel()
            }
        }.create().apply {
            dialog = this
            show()
        }
    }
}