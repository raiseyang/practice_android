# autojs分支

## 参考：
https://www.jianshu.com/p/048229e8d59b
官方文档：https://hyb1996.github.io/AutoJs-Docs/#/documentation

## 修改：
1. 将原项目的3个module打包成aar引入；
2. autojs module内有很多远程依赖，在gradle脚本中配置通用的依赖；
3. 打包即可；

## 使用：
1. 安装app-debug.apk;
2. 打开界面，开启读取存储卡权限；开启辅助服务；
2. 发送广播通知执行脚本：adb shell am broadcast -a com.abupdate.dsapp.action_file_script_launcher --es scriptPath "/sdcard/002"
