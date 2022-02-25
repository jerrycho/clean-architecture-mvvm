package com.jerry.clean_architecture_mvvm.presentation.content

import dagger.hilt.android.lifecycle.HiltViewModel

import com.jerry.clean_architecture_mvvm.others.MyResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import com.jerry.clean_architecture_mvvm.presentation.base.ViewStated
import com.jerry.clean_architecture_mvvm.domain.usecase.GetContentUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class ContentListViewModel  @Inject constructor(
    private val getContentUseCase: GetContentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ViewStated<ContentListResponse>())
    val state: StateFlow<ViewStated<ContentListResponse>> = _state.asStateFlow()

    init {
        getContent()
    }

    fun getContent() {
        getContentUseCase().onEach {
                result ->
            when (result) {
                is MyResult.Loading -> {
                    _state.value = ViewStated(isLoading = result.isLoading )
                }
                is MyResult.Success -> {
                    _state.value = ViewStated<ContentListResponse>( data = result.data )
                }
                is MyResult.Error -> {
                    _state.value = ViewStated<ContentListResponse>(
                        t = result.t
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


}