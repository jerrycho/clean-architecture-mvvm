package com.jerry.clean_architecture_mvvm.presentation.base

open class ViewStated<T> (
    var isLoading: Boolean? = null,
    var t: Throwable? = null,
    var data: T? = null,
    var error: String? = ""
)


