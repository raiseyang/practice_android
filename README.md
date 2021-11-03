# Hilt分支

## Hilt
https://developer.android.google.cn/training/dependency-injection

## codelab
https://developers.google.cn/codelabs/android-hilt#1

## 步骤
1.@HiltAndroidApp
标记应用容器，放在Application类上；
2.@AndroidEntryPoint
标记Android组件，标记后，可以使用其他注解来注入实例；
3.@Inject
用法1：在需要注入的实例前，添加此标记
用法2：在提供注入的实例的类的构造函数上添加此标记

- 我们会将 Hilt 拥有的关于如何提供不同类型实例的信息称为绑定。

4.@Singleton
单例注入：应用容器永远返回一个实例；

5.@Module 和 @InstallIn
@Module 会告诉 Hilt 这是一个模块，
而 @InstallIn 会通过指定 Hilt 组件告诉 Hilt 绑定在哪些容器中可用。
您可以将 Hilt 组件看作一个容器，在 此处可找到完整的组件列表。

Hilt 组件是一个新容器，Hilt组件和安卓容器一一关联(Application 容器与 ApplicationComponent 关联)
为什么会有不同的组件类型，因为每个组件生命周期都不一样；

- 最新版本@InstallIn(SingletonComponent::class)

6.@Provides
标记一个函数，Hilt会注入函数的返回值

7.@ApplicationContext
应用容器的默认绑定；可以通过该注解来注入应用容器实例

8.@Binds
@Binds 必须对抽象函数作出注解（因为该函数是抽象的，因此其中不包含任何代码，并且该类也必须是抽象的）

9.限定符&@EntryPoint
高级用法，需要用到的时候再研究