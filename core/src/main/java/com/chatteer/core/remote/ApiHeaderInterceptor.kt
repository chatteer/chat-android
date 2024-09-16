package com.chatteer.core.remote

import javax.inject.Qualifier

@Qualifier
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
annotation class ApiHeaderInterceptor