# WorkManager

## ByTag查找
通过byTag查找的workInfo会将之前执行的worker也查找出来； 也就是说，一个worker运行完成之后，记录不会删除；
当启动下一次worker时，因为是一个业务，所以tag一样；这样再通过这个tag查找，就会把之前的所有worker也查出来；
靠谱的方式还是通过UUID查找，但是UUID需要自己去维护；

## 参考：
https://developer.android.google.cn/topic/libraries/architecture/workmanager/basics#groovy