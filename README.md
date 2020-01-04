# kotlin_1分支

---
## kotlin,ktx,anko
kotlin官方中文站：http://www.kotlincn.net/docs/reference/basic-syntax.html
ktx google官网：https://developer.android.google.cn/kotlin/ktx
anko github地址：https://github.com/Kotlin/anko

## kotlin数组使用
不用使用String[]; 需要使用arrayOf()
```
//1.数组和集合定义
val names = arrayOf<String>("2", "3")
val ages = listOf<Int>(1, 2, 3)
//访问
names[0]
ages[0]
//MutableList()和List
```

## 使用if,when表达式简化代码

```
//2.使用if,when表达式返回值
val invalid = isInvalid(2)
val age = getAge("x")
private fun isInvalid(num: Int): Boolean =
        if (num > 3) {
            true
        } else if (num > 2) {
            false
        } else {
            false
        }
private fun getAge(name: String): Int {
    return when (name) {
        "xx" -> 3
        else -> 10
    }
}
```

## 减少重载方法，使用参数默认值
```
//3.使用函数默认参数
createUser("xx", 3)
createUser("qq")
private fun createUser(name: String, age: Int = 18) {
}
```
## steam式操作
```
//4.steam流式操作
ages.filter { it > 3 }.map { (it * 3).toString() }.forEach { print(it) }
```
## kotlin内部类与嵌套类
```
//5.内部类和嵌套类
MainInterface().fetchContext().takeIf { it == this }
MainEntity().fetchContext().takeIf { it == null }
//inner内部类，持有外部实例，可以调用外部方法
inner class MainInterface {
    fun fetchContext(): Context {
        return this@MainActivity
    }
}
//嵌套类，只是写在一个文件里面，和另写一个文件没区别
class MainEntity {
    fun fetchContext(): Context? {
        //报错，找不到MainActivity实例
          return this@MainActivity
        return null
    }
}
```
## foreach break
```
//6.foreach
ages.forEach {
    if (it > 3) {
        for (name in names) {
            if (name == "xx") {
                continue
            }
            break
        }
    }
}
```

## 高阶函数与lambda
```
//7.高阶函数与lambda
AlertDialog.Builder(this)
        .setTitle("提示")
        .setMessage("有新版本升级@")
        .setCancelable(true)
        .setPositiveButton("确定") { dialog, _ ->
            //跳转页面
            dialog.dismiss()
        }
        .create()
        .show()
val alertDialog = showDialog {
    setTitle("提示")
    setMessage("")
    setCancelable(true)
    setPositiveButton("确定") { dialog, _ ->
        //跳转页面
        dialog.dismiss()
    }
}
val alertDialog1 = showDialog1 {
    it.setTitle("提示")
    it.setMessage("")
    it.setCancelable(true)
    it.setPositiveButton("确定") { dialog, _ ->
        //跳转页面
        dialog.dismiss()
    }
}

private fun showDialog(block: AlertDialog.Builder.() -> Unit): AlertDialog {
    val builder = AlertDialog.Builder(this)
    block.invoke(builder)
    val alertDialog = builder.create()
    alertDialog.show()
    return alertDialog
}
private fun showDialog1(block: (AlertDialog.Builder) -> Unit): AlertDialog {
    val builder = AlertDialog.Builder(this)
    block.invoke(builder)
    val alertDialog = builder.create()
    alertDialog.show()
    return alertDialog
}
```

## 实战1
```
//8.实战1：创建jsonObject封装
val jsonObject = JSONObject()
jsonObject.put("key1", "value1")
jsonObject.put("key2", "value2")
createJson(
        "key1" to "value1",
        "key2" to 1001,
        "key2" to createJson(
                "inner1" to ""
        )
)
private fun <V> createJson(vararg pairs: Pair<String, V>): JSONObject =
        JSONObject().apply {
            for ((key, value) in pairs) {
                put(key, value)
            }
        }
```

## 实战2
```
//8.实战2：Notification封装
val builder = NotificationCompat.Builder(this, "")
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("通知")
        .setContentText("有新版本更新！")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
NotificationManagerCompat.from(this).notify(1,builder.build())
```
## 自定义View时构造函数问题
```
class ChapterListView @JvmOverloads constructor(
    val mContext: Context, attrs: AttributeSet? = null
) : LinearLayout(mContext, attrs) {}
```
[Kotlin 重载个方法,还有两幅面孔，省代码的同时也带来一个深坑 | Kotlin 原理][1]


## kotlin小技巧
1. 函数内部定义函数,缩小方法作用域
2. AbDialog 类似高阶函数 lambda

  [1]: https://www.jianshu.com/p/7282b66facd1