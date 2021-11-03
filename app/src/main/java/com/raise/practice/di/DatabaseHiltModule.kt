package com.raise.practice.di

import android.content.Context
import androidx.room.Room
import com.raise.practice.data.room.AppDatabase
import com.raise.practice.data.room.PersonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 创建一个Hilt Module;这个module需要装载到Hilt组件上；
 * 因为我们room数据库操作是全局的，和Activity,fragment等没有关系，所以装载到Application上；
 * 最新版本的Hilt，装载application组件用SingletonComponent类
 *
 * 该module中提供的绑定，关联到Application上
 *
 * 创建module的目的是提供有关联的绑定；无关联的绑定只需要加上@Inject即可
 * 有关联的绑定需要提供其关联对象的实例，在这个类中我们就额外提供了[PersonDao]
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseHiltModule {

    /**
     * @Provides 标记的方法，会提供一个[方法返回值]的绑定，这个绑定的关联对象是[方法的参数]
     * 提供一个PersonDao的绑定
     * 需要关联对象appDatabase
     */
    @Provides
    fun getPersonDao(appDatabase: AppDatabase): PersonDao {
        return appDatabase.personDao()
    }

    /**
     * @ApplicationContext 这是应用容器(Application)预装载的注解，可以提供一个Application的绑定
     */
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "person.db"
        ).build()
    }

}