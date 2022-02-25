package com.jerry.clean_architecture_mvvm.others

sealed class MyResult<T>(
    val isLoading : Boolean? = null,
    val data: T? = null,
    val t: Throwable?=null,
) {
    class Success<T>(data: T) : MyResult<T>(data = data)
    class Error<T>(t: Throwable) : MyResult<T>(t = t)
    class Loading<T>(isLoading: Boolean) : MyResult<T>(isLoading  = isLoading)
}
