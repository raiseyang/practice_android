package com.raise.jsapi

/**
 * 注解js调用方法
 *
 * 注意：
 * 1. 注解的方法，返回值只能是基本类型或 [V8Object]类型
 *
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class JsApi