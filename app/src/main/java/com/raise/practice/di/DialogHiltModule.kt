package com.raise.practice.di

import com.raise.practice.dialog.ADialog
import com.raise.practice.dialog.IDialog
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * 创建一个Hilt Module;这个module需要装载到Hilt组件上；
 * 因为我们IDialog 是一个接口，用在Activity中，所以他关联的容器应该是Activity；
 * 装载activity组件用ActivityComponent类,ActivityComponent预装载了@Activity
 * 该module中提供的绑定，关联到activity上
 *
 */
@InstallIn(ActivityComponent::class)
@Module
abstract class DialogHiltModule {
    /**
     * @Binds 标记的方法，提供一个返回值的绑定，具体实现则体现在方法参数中
     *
     * 注意：参数实现类也必须是一个绑定
     */
    @Binds
    abstract fun provideIDialog(dialog: ADialog): IDialog

}