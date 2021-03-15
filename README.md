# opencv4分支

为了方便使用，将opencv打包成aar;

注意事项：
1. CMakeLists.txt文件必须保留，里面有连接c++库的操作;如果直接使用.so库，会找不到c++库；
2. opencv4/build.gradle里面的相关ndk配置都必须添加

参考：
1. [opencv源码仓库](https://github.com/opencv/opencv)
2. [android 使用opencv4 图片相似度对比](https://blog.csdn.net/qq_27378951/article/details/101543122)