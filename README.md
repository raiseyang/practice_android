# UI Automator分支

## 官网参考地址
https://developer.android.google.cn/training/testing/ui-automator?hl=zh_cn

https://codelabs.developers.google.com/codelabs/developing-android-a11y-service/#0


参考：https://blog.csdn.net/qq_28703617/article/details/85245811
### accessibilityEventTypes 监听哪些事件
哪些事件通知，比如窗口打开，滑动，焦点变化，长按等
点击：TYPE_VIEW_CLICKED
长按：TYPE_VIEW_LONG_CLICKED
滑动：TYPE_VIEW_SCROLLED
选择：TYPE_VIEW_SELECTED
获取到焦点：TYPE_VIEW_FOCUSED
可编辑框文字变化：TYPE_VIEW_TEXT_CHANGED
界面窗口改变1：TYPE_WINDOW_STATE_CHANGED
界面窗口改变2：TYPE_WINDOW_CONTENT_CHANGED
界面窗口改变3：TYPE_WINDOWS_CHANGED
通知栏改变：TYPE_NOTIFICATION_STATE_CHANGED

TYPE_VIEW_CONTEXT_CLICKED

### accessibilityFeedbackType 反馈方式
feedbackAllMask	包含全部反馈
feedbackGeneric	通用反馈
feedbackSpoken	语音反馈
feedbackAudible	音频反馈
feedbackHaptic	触觉(震动)反馈
feedbackVisual	视频反馈

### accessibilityFlags 辅助服务额外额外标记
flagIncludeNotImportantViews  包含不重要的界面




canRetrieveWindowContent 需要获取window内容


