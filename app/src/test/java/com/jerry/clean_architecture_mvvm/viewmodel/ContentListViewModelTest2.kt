package com.jerry.clean_architecture_mvvm.viewmodel

import app.cash.turbine.test
import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import com.jerry.clean_architecture_mvvm.domain.usecase.impl.GetContentUseCaseImpl
import com.jerry.clean_architecture_mvvm.presentation.base.ViewState
import com.jerry.clean_architecture_mvvm.presentation.content.ContentListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ContentListViewModelTest2 {

    private val dispatcher = UnconfinedTestDispatcher()
    private val mockGetContentUseCaseImpl: GetContentUseCaseImpl = mock()


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_with_success() = runBlockingTest {
        launch {
            whenever(mockGetContentUseCaseImpl()).thenReturn(getContentListResponse())

            val mockViewModel = ContentListViewModel(mockGetContentUseCaseImpl)

            mockViewModel.state.collectIndexed { index, value ->
                print(value)
            }

            mockViewModel.getContent()
        }
    }

    private fun getContentListResponse() : ContentListResponse {
        return ContentListResponse(
            items = emptyList()
        )
    }
}