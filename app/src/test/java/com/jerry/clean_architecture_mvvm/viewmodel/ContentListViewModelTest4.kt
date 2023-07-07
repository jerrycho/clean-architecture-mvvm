package com.jerry.clean_architecture_mvvm.viewmodel

import app.cash.turbine.test
import com.jerry.clean_architecture_mvvm.domain.entities.ContentListResponse
import com.jerry.clean_architecture_mvvm.domain.usecase.GetContentUseCase
import com.jerry.clean_architecture_mvvm.presentation.base.ViewState
import com.jerry.clean_architecture_mvvm.presentation.content.ContentListViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ContentListViewModelTest4 {

    private lateinit var viewModel: ContentListViewModel
    private lateinit var getContentUseCase: GetContentUseCase
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        getContentUseCase = Mockito.mock(GetContentUseCase::class.java)
        viewModel = ContentListViewModel(getContentUseCase, testDispatcher)
    }

    @Test
    fun `test expected run collected view state`() = runTest {
        // Prepare the test data
        val contentListResponse = getContentListResponse()
        Mockito.`when`(getContentUseCase()).thenReturn(contentListResponse)

        val event = mutableListOf<ViewState<ContentListResponse>>()
        viewModel.state.onEach {
            event.add(it)
        }.launchIn(
            CoroutineScope(UnconfinedTestDispatcher(testScheduler))
        )
        viewModel.getContent()

        advanceUntilIdle()

        print(event)

        Assert.assertEquals(3, event.size)
    }

    private fun getContentListResponse() : ContentListResponse {
        return ContentListResponse(
            items = emptyList()
        )
    }

}