package com.chatteer.core.data.remote.qualifier

import javax.inject.Qualifier

@Qualifier
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
annotation class ApiHeaderInterceptor