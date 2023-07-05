package com.jerry.clean_architecture_mvvm.presentation.base

sealed class ViewState<out T> where T : Any? {
    object Initial : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
    data class Success<T>(val data: T) : ViewState<T>()
    data class Failure(val t: Throwable) : ViewState<Nothing>()
}


