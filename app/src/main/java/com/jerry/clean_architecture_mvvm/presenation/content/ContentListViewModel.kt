package com.jerry.clean_architecture_mvvm.presentation.content

import dagger.hilt.android.lifecycle.HiltViewModel

import com.jerry.clean_architecture_mvvm.others.MyResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse

import com.jerry.clean_architecture_mvvm.domain.usecase.GetContentUseCase
import com.jerry.clean_architecture_mvvm.presentation.base.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContentListViewModel  @Inject constructor(
    private val getContentUseCase: GetContentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ViewState<ContentListResponse>>(ViewState.Initial)
    val state = _state.asStateFlow()

    private val _state2 = MutableStateFlow<ViewState<ContentListResponse>>(ViewState.Initial)
    val state2 = _state2.asStateFlow()


    fun getContent() {
        viewModelScope.launch {
            _state.value = ViewState.Loading
            try {
                _state.value = ViewState.Success(getContentUseCase())
            } catch (e: Exception) {
                _state.value = ViewState.Failure(e)
            }
        }

    }



}