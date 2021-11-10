package com.raise.practice.viewmodel

import androidx.hilt.Assisted
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @HiltViewModel 修饰viewModel
 * @Inject 提供绑定构造函数
 * @Assisted 提供一个对象；  固定写法   注意Assisted注解的包：androidx.hilt.Assisted
 */
@HiltViewModel
class SecondViewModel @Inject constructor(
    @Assisted private val stateHandle: SavedStateHandle
) : ViewModel() {
}